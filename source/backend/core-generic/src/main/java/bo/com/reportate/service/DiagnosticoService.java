package bo.com.reportate.service;

import bo.com.reportate.model.dto.response.DiagnosticoResponseDto;
import bo.com.reportate.model.enums.EstadoDiagnosticoEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.util.Date;

/**
 * @Created by :MC4
 * @Autor :Ricardo Laredo
 * @Email :rlaredo@mc4.com.bo
 * @Date :2020-04-02
 * @Project :reportate
 * @Package :bo.com.reportate.service
 * @Copyright :MC4
 */
public interface DiagnosticoService {
    Page<DiagnosticoResponseDto> listarDiagnostico(Authentication authentication, Date from, Date to, Long departamentoId, Long municipioId, Long centroSaludId, String nomprePaciente, EstadoDiagnosticoEnum estadoDiagnostico, Long enfermedadId, Pageable pageable);
}
