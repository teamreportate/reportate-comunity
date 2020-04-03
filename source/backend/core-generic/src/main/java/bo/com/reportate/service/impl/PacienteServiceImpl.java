package bo.com.reportate.service.impl;

import bo.com.reportate.exception.NotDataFoundException;
import bo.com.reportate.exception.OperationException;
import bo.com.reportate.model.*;
import bo.com.reportate.model.dto.PacienteDto;
import bo.com.reportate.model.dto.request.EnfermedadRequest;
import bo.com.reportate.model.dto.request.PaisRequest;
import bo.com.reportate.model.dto.request.SintomaRequest;
import bo.com.reportate.model.enums.EstadoDiagnosticoEnum;
import bo.com.reportate.model.enums.EstadoEnum;
import bo.com.reportate.model.enums.GeneroEnum;
import bo.com.reportate.repository.*;
import bo.com.reportate.service.LogService;
import bo.com.reportate.service.NotificacionService;
import bo.com.reportate.service.PacienteService;
import bo.com.reportate.util.ValidationUtil;
import bo.com.reportate.utils.BigDecimalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @Created by :MC4
 * @Autor :Ricardo Laredo
 * @Email :rlaredo@mc4.com.bo
 * @Date :2020-04-02
 * @Project :reportate
 * @Package :bo.com.reportate.service.impl
 * @Copyright :MC4
 */
@Service
@Slf4j
public class PacienteServiceImpl implements PacienteService {
    @Autowired private FamiliaRepository familiaRepository;
    @Autowired private PacienteRepository pacienteRepository;
    @Autowired private LogService logService;
    @Autowired private ControlDiarioRepository controlDiarioRepository;
    @Autowired private ControlDiarioSintomaRepository controlDiarioSintomaRepository;
    @Autowired private ControlDiarioPaisRepository controlDiarioPaisRepository;
    @Autowired private ControlDiarioEnfermedadRepository controlDiarioEnfermedadRepository;
    @Autowired private SintomaRepository sintomaRepository;
    @Autowired private PaisRepository paisRepository;
    @Autowired private EnfermedadRepository enfermedadRepository;
    @Autowired private DiagnosticoRepository diagnosticoRepository;
    @Autowired private MatrizDiagnosticoRepository matrizDiagnosticoRepository;
    @Autowired private NotificacionService notificacionService;

    @Override
    public PacienteDto save(Authentication userDetails, String nombre, Integer edad, GeneroEnum genero, Boolean gestacion, Integer tiempoGestacion) {
        ValidationUtil.throwExceptionIfInvalidText("nombre",nombre, true,100);
        ValidationUtil.throwExceptionIfInvalidNumber("edad",edad,true,-1,120);
        ValidationUtil.throwExceptionIfInvalidNumber("tiempo de gestación",tiempoGestacion,false,-1,41);
        MuUsuario user = (MuUsuario) userDetails.getPrincipal();
        Familia familia = this.familiaRepository.findFirstByUsuarioIdAndEstadoOrderByIdDesc(user.getId(), EstadoEnum.ACTIVO).orElseThrow(()->new OperationException("No existe ningún registro de familia para el usuario"));

        if(this.pacienteRepository.existsByFamiliaAndNombreIgnoreCaseAndEstado(familia, nombre,EstadoEnum.ACTIVO)){
            throw new OperationException("Ya existe un miembro de tu familia con el nombre: "+nombre);
        }
        Paciente paciente = Paciente.builder()
                .nombre(nombre)
                .edad(edad)
                .genero(genero)
                .gestacion(gestacion)
                .tiempoGestacion(tiempoGestacion)
                .familia(familia)
                .build();
        this.pacienteRepository.save(paciente);
        PacienteDto pacienteDto = new PacienteDto();
        BeanUtils.copyProperties(paciente,pacienteDto);
        return pacienteDto;
    }

    @Override
    public PacienteDto update(Authentication userDetails,Long id, String nombre, Integer edad, GeneroEnum genero, Boolean gestacion, Integer tiempoGestacion) {
        ValidationUtil.throwExceptionIfInvalidNumber("pacienteId", id,true,0L);
        ValidationUtil.throwExceptionIfInvalidText("nombre",nombre, true,100);
        ValidationUtil.throwExceptionIfInvalidNumber("edad",edad,true,-1,120);
        ValidationUtil.throwExceptionIfInvalidNumber("tiempo de gestación",tiempoGestacion,false,-1,41);
        MuUsuario user = (MuUsuario) userDetails.getPrincipal();
        Familia familia = this.familiaRepository.findFirstByUsuarioIdAndEstadoOrderByIdDesc(user.getId(), EstadoEnum.ACTIVO).orElseThrow(()->new OperationException("No existe ningún registro de familia para el usuario"));

        if(this.pacienteRepository.existsByFamiliaAndIdNotAndNombreIgnoreCaseAndEstado(familia, id, nombre,EstadoEnum.ACTIVO)){
            throw new OperationException("Ya existe un miembro de tu familia con el nombre: "+nombre);
        }
        Paciente paciente = this.pacienteRepository.findByIdAndEstado(id, EstadoEnum.ACTIVO).orElseThrow(()->new NotDataFoundException("No existe ningún paciente registrado con el id: "+id));
        paciente.setNombre(nombre);
        paciente.setEdad(edad);
        paciente.setGenero(genero);
        paciente.setGestacion(gestacion);
        paciente.setTiempoGestacion(tiempoGestacion);
        this.pacienteRepository.save(paciente);
        PacienteDto pacienteDto = new PacienteDto();
        BeanUtils.copyProperties(paciente,pacienteDto);
        return pacienteDto;
    }

    @Override
    public String controlDiario(Long pacienteId, List<EnfermedadRequest> enfermedadesBase, List<PaisRequest> paisesVisitados, List<SintomaRequest> sintomas) {
        log.info("Inician el registro del control diario.");
        ValidationUtil.throwExceptionIfInvalidNumber("paciente",pacienteId,true,0L);
        if(sintomas == null || sintomas.isEmpty()){
            log.error("No existe sintomas para registrar en el control diario");
            throw new OperationException("No existe sintomas para registrar en el control diario");
        }

        Paciente paciente = this.pacienteRepository.findByIdAndEstado(pacienteId,EstadoEnum.ACTIVO).orElseThrow(()->new NotDataFoundException("No existe el paciente registrado"));
        ControlDiario controlDiario = ControlDiario.builder().paciente(paciente).build();
        this.controlDiarioRepository.save(controlDiario);
        List<Sintoma> sintomasRecibidos = new ArrayList<>();
        for (SintomaRequest sintAux : sintomas) {
            ValidationUtil.throwExceptionIfInvalidNumber("sintoma",sintAux.getId(),true,0L);
            Sintoma sintoma = this.sintomaRepository.findByIdAndEstado(sintAux.getId(),EstadoEnum.ACTIVO).orElseThrow(()-> new NotDataFoundException("No se encontro el sintoma que quiere registrar"));
            this.controlDiarioSintomaRepository.save(ControlDiarioSintoma.builder()
                    .controlDiario(controlDiario)
                    .respuesta(sintAux.getRespuesta())
                    .observacion(sintAux.getObservacion())
                    .sintoma(sintoma).build());
            sintomasRecibidos.add(sintoma);
        }

        if(enfermedadesBase != null && !enfermedadesBase.isEmpty()){
            for (EnfermedadRequest enferAux : enfermedadesBase) {
                ValidationUtil.throwExceptionIfInvalidNumber("enfermedad",enferAux.getId(),true,0L);
                Enfermedad enfermedad = this.enfermedadRepository.findByIdAndEstado(enferAux.getId(),EstadoEnum.ACTIVO).orElseThrow(()-> new NotDataFoundException("No se encontro la enfermedad que quiere reportar"));
                this.controlDiarioEnfermedadRepository.save(ControlDiarioEnfermedad.builder().controlDiario(controlDiario).enfermedad(enfermedad).build());
            }
        }

        if(paisesVisitados != null && !paisesVisitados.isEmpty()){
            for (PaisRequest paisAux : paisesVisitados) {
                ValidationUtil.throwExceptionIfInvalidNumber("pais",paisAux.getId(),true,0L);
                Pais pais = this.paisRepository.findByIdAndEstado(paisAux.getId(),EstadoEnum.ACTIVO).orElseThrow(()-> new NotDataFoundException("No se encontro el pais que quiere reportar"));
                this.controlDiarioPaisRepository.save(ControlDiarioPais.builder().controlDiario(controlDiario).pais(pais).build());
            }
        }

        List<Enfermedad> enfermedades = this.matrizDiagnosticoRepository.listarEnfermedades();
        for(Enfermedad enfermedad: enfermedades){
            List<MatrizDiagnostico> matrizDiagnosticos = this.matrizDiagnosticoRepository.findByEnfermedadAndEstado(enfermedad,EstadoEnum.ACTIVO);
            BigDecimal resultadoPeso = BigDecimal.ZERO;
            for (MatrizDiagnostico matrizDiagnostico: matrizDiagnosticos){
                if(sintomasRecibidos.contains(matrizDiagnostico.getSintoma())){
                    resultadoPeso = resultadoPeso.add(matrizDiagnostico.getPeso());
                }
            }
            if(resultadoPeso.compareTo(BigDecimal.ZERO) > 0) {// Sintomas de la enfermedad
                if(resultadoPeso.compareTo(new BigDecimal("5")) > 0){
                    //NOTIFICAR
                    notificacionService.notificacionSospechoso("rllayus@gmail.com","Caso sospechoso","Existe un nuevo caso sospechoso de "+enfermedad.getNombre() +" con una valorción de "+resultadoPeso.toPlainString());
                }
                this.diagnosticoRepository.save(Diagnostico.builder()
                        .controlDiario(controlDiario)
                        .enfermedad(enfermedad)
                        .resultadoValoracion(resultadoPeso)
                        .estadoDiagnostico(EstadoDiagnosticoEnum.SOSPECHOSO).build());
            }
        }

        log.info("Se registro los sintomas correctamente");
        return "Se registro los sintomas correctamente";
    }
}
