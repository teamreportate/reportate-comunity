package bo.com.reportate.service;

import bo.com.reportate.model.dto.response.ResumenDto;
import bo.com.reportate.model.dto.response.TablaResponse;
import org.springframework.security.core.Authentication;

import java.util.Date;
import java.util.List;

public interface DiagnosticoResumenTotalEstadoService {
    List<ResumenDto> cantidadDiagnosticoPorFiltros(Authentication authentication,Date from, Date to, Long departamentoId,Long municipioId, Long centroSaludId, Long enfermedadId);
    TablaResponse cantidadDiagnosticoPorLugar(Authentication authentication,Date from, Date to, Long departamentoId,
			Long municipioId, Long centroSaludId, Long enfermedadId);
}
