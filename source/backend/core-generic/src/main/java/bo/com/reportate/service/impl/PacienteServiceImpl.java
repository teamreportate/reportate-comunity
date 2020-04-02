package bo.com.reportate.service.impl;

import bo.com.reportate.exception.NotDataFoundException;
import bo.com.reportate.exception.OperationException;
import bo.com.reportate.model.*;
import bo.com.reportate.model.dto.PacienteDto;
import bo.com.reportate.model.enums.EstadoEnum;
import bo.com.reportate.model.enums.GeneroEnum;
import bo.com.reportate.repository.FamiliaRepository;
import bo.com.reportate.repository.PacienteRepository;
import bo.com.reportate.service.PacienteService;
import bo.com.reportate.util.ValidationUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

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
public class PacienteServiceImpl implements PacienteService {
    @Autowired private FamiliaRepository familiaRepository;
    @Autowired private PacienteRepository pacienteRepository;
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
    public String controlDiario(Long pacienteId, List<Enfermedad> enfermedadesBase, List<Pais> paisesVisitados, List<Sintoma> sintomas) {
        return "";
    }
}
