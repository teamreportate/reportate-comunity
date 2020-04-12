package bo.com.reportate.service;

import java.util.Date;
import java.util.List;

import bo.com.reportate.model.dto.response.MapResponse;
import org.springframework.security.core.Authentication;

import bo.com.reportate.model.dto.response.ResumenDto;
import bo.com.reportate.model.dto.response.TablaResponse;

public interface DiagnosticoResumenTotalEstadoService {
    List<ResumenDto> cantidadDiagnosticoPorFiltros(Authentication authentication,Date from, Date to, Long departamentoId,Long municipioId, Long centroSaludId, Long enfermedadId);
    

}
