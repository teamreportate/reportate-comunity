package bo.com.reportate.service;

import bo.com.reportate.model.dto.PacienteDto;
import bo.com.reportate.model.dto.PaisVisitadoDto;
import bo.com.reportate.model.dto.request.DiagnosticoRequest;
import bo.com.reportate.model.dto.request.EnfermedadRequest;
import bo.com.reportate.model.dto.request.PaisRequest;
import bo.com.reportate.model.dto.request.SintomaRequest;
import bo.com.reportate.model.dto.response.EnfermedadResponse;
import bo.com.reportate.model.dto.response.FichaEpidemiologicaResponse;
import bo.com.reportate.model.dto.response.MovilControlDiario;
import bo.com.reportate.model.enums.GeneroEnum;
import org.springframework.security.core.Authentication;

import java.util.Date;
import java.util.List;

/**
 * @Created by :Reportate
 * @Autor :Ricardo Laredo
 * @Email :rllayus@gmail.com
 * @Date :2020-04-02
 * @Project :reportate
 * @Package :bo.com.reportate.service
 * @Copyright :Reportate
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
    List<MovilControlDiario> getControlDiario(Long pacienteId);

    void agregarDiagnosticoMedico(Authentication userDetails,Long pacienteId, DiagnosticoRequest diagnosticoRequest);
}
