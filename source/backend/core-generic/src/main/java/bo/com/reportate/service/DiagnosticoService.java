package bo.com.reportate.service;

import bo.com.reportate.model.dto.response.DiagnosticoResponseDto;
import bo.com.reportate.model.dto.response.NivelValoracionDto;
import bo.com.reportate.model.enums.EstadoDiagnosticoEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
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
public interface DiagnosticoService {
    Page<DiagnosticoResponseDto> listarDiagnostico(Date from, Date to, Long departamentoId, EstadoDiagnosticoEnum estadoDiagnostico, Long enfermedadId, Pageable pageable);
    Integer countDiagnosticoByResultadoValoracion(BigDecimal valoracionInicio, BigDecimal valoracionFin, Long departamentoId,Long municipioId,String genero, Integer edadInicial, Integer edadFinal);
    List<NivelValoracionDto> listarByNivelValoracion(Date from,Date to);
}
