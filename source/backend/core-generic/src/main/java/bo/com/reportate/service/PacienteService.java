package bo.com.reportate.service;

import bo.com.reportate.model.Enfermedad;
import bo.com.reportate.model.Paciente;
import bo.com.reportate.model.Pais;
import bo.com.reportate.model.Sintoma;
import bo.com.reportate.model.dto.EnfermedadDto;
import bo.com.reportate.model.dto.PacienteDto;
import bo.com.reportate.model.dto.PaisVisitadoDto;
import bo.com.reportate.model.dto.request.EnfermedadRequest;
import bo.com.reportate.model.dto.request.PaisRequest;
import bo.com.reportate.model.dto.request.SintomaRequest;
import bo.com.reportate.model.dto.response.EnfermedadResponse;
import bo.com.reportate.model.dto.response.FichaEpidemiologicaResponse;
import bo.com.reportate.model.dto.response.PaisResponse;
import bo.com.reportate.model.enums.GeneroEnum;
import org.springframework.security.core.Authentication;

import java.util.Date;
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
    PacienteDto save(Authentication userDetails, String nombre, Integer edad, GeneroEnum genero, Boolean gestacion, Integer tiempoGestacion, String ocupacion );
    PacienteDto agregarContacto(Long pacienteId, String nombre, Integer edad, GeneroEnum genero, Boolean gestacion, Integer tiempoGestacion, String ocupacion,String ci, String fechaNacimiento, String seguro, String codigoSeguro );
    PacienteDto update(Authentication userDetails,Long id, String nombre, Integer edad, GeneroEnum genero, Boolean gestacion, Integer tiempoGestacion,String ocupacion, String ci, String fechaNacimiento, String seguro, String codigoSeguro);
    PacienteDto update(Long id, String nombre, Integer edad, GeneroEnum genero, Boolean gestacion, Integer tiempoGestacion,String ocupacion, String ci, String fechaNacimiento, String seguro, String codigoSeguro);
    String controlDiario(Long pacienteId, List<EnfermedadRequest> enfermedadesBase, List<PaisRequest> paisesVisitados, List<SintomaRequest> sintomas);
    FichaEpidemiologicaResponse getFichaEpidemiologica(Long pacienteId);
    EnfermedadResponse agregarEnfermedadBase(Long pacienteId, Long enfermedadId);
    void eliminarEnfermedadBase(Long pacienteId, Long enfermedadId);
    PaisVisitadoDto agregarPais(Long pacienteId, Long paisId, Date fechaLlegada, Date fechaSalida, String ciudades);
    PaisVisitadoDto editarPaisesVisitados(Long controlPaisId, Date fechaLlegada, Date fechaSalida, String ciudades);
    void eliminarPais(Long pacienteId, Long paisId);
    void eliminarContacto(Long contactoId);
}
