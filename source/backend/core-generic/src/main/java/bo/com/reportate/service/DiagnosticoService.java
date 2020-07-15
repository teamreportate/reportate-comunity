package bo.com.reportate.service;

import bo.com.reportate.model.dto.DiagnosticoDto;
import bo.com.reportate.model.dto.response.DiagnosticoResponseDto;
import bo.com.reportate.model.dto.response.DiagnosticoSintomaResponse;
import bo.com.reportate.model.dto.response.MapResponse;
import bo.com.reportate.model.dto.response.NivelValoracionDto;
import bo.com.reportate.model.enums.EstadoDiagnosticoEnum;
import bo.com.reportate.model.enums.GeneroEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
public interface DiagnosticoService {
    Integer cantidadDiagnosticoPorFiltros(Authentication authentication, Long departamentoId,Long municipioId, Long centroSaludId,GeneroEnum genero, Integer edadInicial, Integer edadFinal,EstadoDiagnosticoEnum estadoDiagnostico, Long enfermedadId);
    List<NivelValoracionDto> listarPorNivelValoracion(Date from,Date to);
    Page<DiagnosticoResponseDto> listarDiagnostico(Authentication authentication, Date from, Date to, Long departamentoId, Long municipioId, Long centroSaludId, String nomprePaciente, EstadoDiagnosticoEnum estadoDiagnostico, Long enfermedadId, Pageable pageable);
    List<DiagnosticoSintomaResponse> listarSintomas(Long diagnosticoId);
    List<MapResponse> listarPacientesPor(Authentication authentication, Date from, Date to, Long departamentoId,
                                         Long municipioId, Long centroSaludId, Long enfermedadId,EstadoDiagnosticoEnum estadoDiagnostico);

    DiagnosticoDto actualizarDiagnostico(Authentication authentication, Long diagnosticoId, EstadoDiagnosticoEnum estadoDiagnosticoEnum, String observacion);
}
