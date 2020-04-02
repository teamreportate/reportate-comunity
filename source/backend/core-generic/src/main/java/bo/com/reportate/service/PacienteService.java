package bo.com.reportate.service;

import bo.com.reportate.model.Enfermedad;
import bo.com.reportate.model.Paciente;
import bo.com.reportate.model.Pais;
import bo.com.reportate.model.Sintoma;
import bo.com.reportate.model.dto.PacienteDto;
import bo.com.reportate.model.enums.GeneroEnum;
import org.springframework.security.core.Authentication;

import java.util.List;

/**
 * @Created by :MC4
 * @Autor :Ricardo Laredo
 * @Email :rlaredo@mc4.com.bo
 * @Date :2020-04-02
 * @Project :reportate
 * @Package :bo.com.reportate.service
 * @Copyright :MC4
 */
public interface PacienteService {
    PacienteDto save(Authentication userDetails, String nombre, Integer edad, GeneroEnum genero, Boolean gestacion, Integer tiempoGestacion );
    PacienteDto update(Authentication userDetails,Long id, String nombre, Integer edad, GeneroEnum genero, Boolean gestacion, Integer tiempoGestacion );
    String controlDiario(Long pacienteId, List<Enfermedad> enfermedadesBase, List<Pais> paisesVisitados, List<Sintoma> sintomas);

}
