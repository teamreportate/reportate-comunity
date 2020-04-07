package bo.com.reportate.service.impl;

import bo.com.reportate.exception.NotDataFoundException;
import bo.com.reportate.exception.OperationException;
import bo.com.reportate.model.*;
import bo.com.reportate.model.dto.EnfermedadDto;
import bo.com.reportate.model.dto.PacienteDto;
import bo.com.reportate.model.dto.PaisDto;
import bo.com.reportate.model.dto.PaisVisitadoDto;
import bo.com.reportate.model.dto.request.EnfermedadRequest;
import bo.com.reportate.model.dto.request.PaisRequest;
import bo.com.reportate.model.dto.request.SintomaRequest;
import bo.com.reportate.model.dto.response.*;
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
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

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
    @Autowired private UsuarioRepository usuarioRepository;

    @Override
    public PacienteDto save(Authentication userDetails, String nombre, Integer edad, GeneroEnum genero, Boolean gestacion, Integer tiempoGestacion, String ocupacion) {
        ValidationUtil.throwExceptionIfInvalidText("nombre",nombre, true,100);
        ValidationUtil.throwExceptionIfInvalidNumber("edad",edad,true,-1,120);
        ValidationUtil.throwExceptionIfInvalidNumber("tiempo de gestación",tiempoGestacion,false,-1,41);
        ValidationUtil.throwExceptionIfInvalidText("ocupación",ocupacion,true,50);
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
                .ocupacion(ocupacion)
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



        ControlDiario controlDiario = ControlDiario.builder()
                .paciente(paciente)
                .primerControl(this.controlDiarioRepository.existsByPrimerControlTrueAndPaciente(paciente))
                .build();
        this.controlDiarioRepository.save(controlDiario);
        log.info("Registrando sintomas..");
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
            log.info("Registrando enfermedades..");
            for (EnfermedadRequest enferAux : enfermedadesBase) {
                ValidationUtil.throwExceptionIfInvalidNumber("enfermedad",enferAux.getId(),true,0L);
                Enfermedad enfermedad = this.enfermedadRepository.findByIdAndEstado(enferAux.getId(),EstadoEnum.ACTIVO).orElseThrow(()-> new NotDataFoundException("No se encontro la enfermedad que quiere reportar"));
                this.controlDiarioEnfermedadRepository.save(ControlDiarioEnfermedad.builder().controlDiario(controlDiario).enfermedad(enfermedad).build());
            }
        }

        if(paisesVisitados != null && !paisesVisitados.isEmpty()){
            log.info("Registrando paises..");
            for (PaisRequest paisAux : paisesVisitados) {
                ValidationUtil.throwExceptionIfInvalidNumber("pais",paisAux.getId(),true,0L);
                Pais pais = this.paisRepository.findByIdAndEstado(paisAux.getId(),EstadoEnum.ACTIVO).orElseThrow(()-> new NotDataFoundException("No se encontro el pais que quiere reportar"));
                this.controlDiarioPaisRepository.save(ControlDiarioPais.builder().controlDiario(controlDiario).pais(pais).build());
            }
        }

        log.info("Calculando diagnostico ...");
        List<Enfermedad> enfermedades = this.matrizDiagnosticoRepository.listarEnfermedades();
        for(Enfermedad enfermedad: enfermedades){
            List<MatrizDiagnostico> matrizDiagnosticos = this.matrizDiagnosticoRepository.findByEnfermedadAndEstado(enfermedad,EstadoEnum.ACTIVO);
            List<String> sintomasMail = new ArrayList<>();
            BigDecimal resultadoPeso = BigDecimal.ZERO;
            for (MatrizDiagnostico matrizDiagnostico: matrizDiagnosticos){
                if(sintomasRecibidos.contains(matrizDiagnostico.getSintoma())){
                    resultadoPeso = resultadoPeso.add(matrizDiagnostico.getPeso());
                    sintomasMail.add(matrizDiagnostico.getSintoma().getNombre());
                }
            }
            if(resultadoPeso.compareTo(BigDecimal.ZERO) > 0) {// Sintomas de la enfermedad
                log.info("Resultado enfermedad:{}  del calculo:{}",enfermedad.getNombre(), resultadoPeso);
                if(resultadoPeso.compareTo(new BigDecimal("5")) > 0){
                    List<MuUsuario> medicos = this.usuarioRepository.obtenerMedicoPordepartamento(controlDiario.getPaciente().getFamilia().getDepartamento());
                    for (MuUsuario medico: medicos) {
                        notificacionService.notificacionSospechosoSintomas("Dr. "+ medico.getNombre(), medico.getEmail(),"Caso sospechoso " + enfermedad.getNombre(),"Existe un nuevo caso sospechoso de " + enfermedad.getNombre() + " con una valoración de " + resultadoPeso.toPlainString() , sintomasMail);
                        log.info("Enviando notificacion");
                    }
                }
                this.diagnosticoRepository.save(Diagnostico.builder()
                        .controlDiario(controlDiario)
                        .enfermedad(enfermedad)
                        .resultadoValoracion(resultadoPeso)
                        .departamento(paciente.getFamilia().getDepartamento())
                        .municipio(paciente.getFamilia().getMunicipio())
                        .centroSalud(paciente.getFamilia().getCentroSalud())
                        .estadoDiagnostico(EstadoDiagnosticoEnum.SOSPECHOSO).build());
            }
        }
        log.info("Se registro los sintomas correctamente");
        return enfermedades.get(0).getMensajeDiagnostico();
    }

    @Transactional(readOnly = true)
    public FichaEpidemiologicaResponse getFichaEpidemiologica(Long pacienteId){
        FichaEpidemiologicaResponse fichaEpidemiologicaResponse = new FichaEpidemiologicaResponse();
        log.info("iniciando busqueda del paciente");
        Paciente paciente = this.pacienteRepository.findByIdAndEstado(pacienteId, EstadoEnum.ACTIVO).orElseThrow(()->new NotDataFoundException("No existe ningún paciente registrado con el id: "+pacienteId));
        BeanUtils.copyProperties(paciente,fichaEpidemiologicaResponse);

        fichaEpidemiologicaResponse.setDepartamento(paciente.getFamilia().getDepartamento().getNombre());
        fichaEpidemiologicaResponse.setMunicipio(paciente.getFamilia().getMunicipio().getNombre());
        fichaEpidemiologicaResponse.setCiudad(paciente.getFamilia().getCiudad());
        fichaEpidemiologicaResponse.setZona(paciente.getFamilia().getZona());
        fichaEpidemiologicaResponse.setDireccion(paciente.getFamilia().getDireccion());
        fichaEpidemiologicaResponse.setTelefono(paciente.getFamilia().getTelefono());
        fichaEpidemiologicaResponse.setUbicacion("https://maps.google.com/?q="+paciente.getFamilia().getLatitud()+","+paciente.getFamilia().getLongitud());
        try {
            log.info("iniciando busqueda de paises visitados");
            List<PaisVisitadoDto> paisesViajados = this.controlDiarioPaisRepository.listarPaisesVisitados(paciente);
            fichaEpidemiologicaResponse.setPaisesVisitados(paisesViajados);

            log.info("obteniendo enfermedades base");
            List<EnfermedadResponse> enfermedadesBase = this.controlDiarioEnfermedadRepository.listarEnfermedadesByPaciente(paciente);
            fichaEpidemiologicaResponse.setEnfermedadesBase(enfermedadesBase);

            log.info("obteniendo diagnosticos");
            List<DiagnosticoResponseDto> diagnosticos = this.diagnosticoRepository.listarDiagnosticoByPaciente(paciente);
            fichaEpidemiologicaResponse.setDiagnosticos(diagnosticos);

            log.info("obteniendo contactos");
            List<PacienteDto> contactos = this.pacienteRepository.listarPacienteByFamilia(paciente.getFamilia(), paciente.getId());
            fichaEpidemiologicaResponse.setContactos(contactos);

        }catch (Exception e){
            log.error(e.getMessage());
            throw new OperationException("Búusqueda finalizada con errores");
        }
        log.info("busquedas finalizadas");
        return  fichaEpidemiologicaResponse;
    }

    @Override
    public EnfermedadResponse agregarEnfermedadBase(Long pacienteId, Long enfermedadId) {
        Paciente paciente = this.pacienteRepository.findByIdAndEstado(pacienteId,EstadoEnum.ACTIVO).orElseThrow(()->new NotDataFoundException("No se encontró el paciente"));
        Enfermedad enfermedad = this.enfermedadRepository.findByIdAndEstado(enfermedadId,EstadoEnum.ACTIVO).orElseThrow(()->new NotDataFoundException("No se encontró la enfermedad seleccionada"));
        ControlDiario  controlDiario = this.controlDiarioRepository.findByPrimerControlTrueAndPacienteAndEstado(paciente, EstadoEnum.ACTIVO).orElseThrow(()->new OperationException("No se encontró el primer control del paciente"));
        if(this.controlDiarioEnfermedadRepository.existsByControlDiarioAndEnfermedadAndEstado(controlDiario,enfermedad,EstadoEnum.ACTIVO)){
            throw new OperationException("El paciente ya tiene agregado la enfermedad: "+enfermedad.getNombre());
        }
        this.controlDiarioEnfermedadRepository.save(ControlDiarioEnfermedad.builder().controlDiario(controlDiario).enfermedad(enfermedad).build());
        return new EnfermedadResponse(enfermedad);
    }

    @Override
    public PaisVisitadoDto agregarPais(Long pacienteId, Long paisId, Date fechaViaje, String ciudades) {
        ValidationUtil.throwExceptionIfInvalidText("ciudad",ciudades,true,500);
        Paciente paciente = this.pacienteRepository.findByIdAndEstado(pacienteId,EstadoEnum.ACTIVO).orElseThrow(()->new NotDataFoundException("No se encontró el paciente"));
        Pais pais = this.paisRepository.findByIdAndEstado(paisId, EstadoEnum.ACTIVO).orElseThrow(()->new NotDataFoundException("No se encontro el país seleccionado"));
        ControlDiario  controlDiario = this.controlDiarioRepository.findByPrimerControlTrueAndPacienteAndEstado(paciente, EstadoEnum.ACTIVO).orElseThrow(()->new OperationException("No se encontró el primer control del paciente"));
        if(this.controlDiarioPaisRepository.existsByControlDiarioAndPaisAndEstado(controlDiario, pais, EstadoEnum.ACTIVO)){
            throw new OperationException("El paciente ya tiene agregado el país: "+pais.getNombre());
        }
        ControlDiarioPais controlDiarioPais = ControlDiarioPais.builder()
                .pais(pais)
                .controlDiario(controlDiario)
                .fechaViaje(fechaViaje)
                .ciudades(ciudades)
                .build();
        this.controlDiarioPaisRepository.save(controlDiarioPais);
        return new PaisVisitadoDto(controlDiarioPais);
    }
}
