package bo.com.reportate.service.impl;

import bo.com.reportate.exception.NotDataFoundException;
import bo.com.reportate.model.DiagnosticosResumenDiario;
import bo.com.reportate.model.enums.EstadoDiagnosticoEnum;
import bo.com.reportate.model.enums.EstadoEnum;
import bo.com.reportate.repository.DiagnosticoResumenDiarioRepository;
import bo.com.reportate.repository.PacienteRepository;
import bo.com.reportate.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;

/**
 * @Created by :MC4
 * @Autor :Ricardo Laredo
 * @Email :rlaredo@mc4.com.bo
 * @Date :2020-04-09
 * @Project :reportate
 * @Package :bo.com.reportate.service.impl
 * @Copyright :MC4
 */
@Component
@Slf4j
public class TotalizarTimer {
	
	@Autowired
    private PacienteRepository pacienteRepository;
	@Autowired
	private DiagnosticoResumenDiarioRepository diagnosticoResumenDiarioRepository;
	
    @Scheduled(fixedRate = 100000)
    public void updateTime() {
        try {
            log.info("Actualizando hora en nodo:{} ", InetAddress.getLocalHost().getHostAddress());
            List<DiagnosticosResumenDiario> diagnosticosResumenDiarios= pacienteRepository.resumenPorPacienteEstadoDiagnostico(DateUtil.formatToStart(new Date()), DateUtil.formatToEnd(new Date()), EstadoDiagnosticoEnum.SOSPECHOSO, EstadoDiagnosticoEnum.NEGATIVO, EstadoDiagnosticoEnum.CONFIRMADO, EstadoDiagnosticoEnum.CURADO, EstadoDiagnosticoEnum.FALLECIDO);
            for (DiagnosticosResumenDiario diagnosticosResumenDiario : diagnosticosResumenDiarios) {
            	diagnosticosResumenDiario.setEstado(EstadoEnum.ACTIVO);
            	try {
				DiagnosticosResumenDiario diagnosticosResumenDiarioAux = diagnosticoResumenDiarioRepository.buscarPorFiltros(new Date(),
						diagnosticosResumenDiario.getDepartamento(),diagnosticosResumenDiario.getMunicipio(),diagnosticosResumenDiario.getCentroSalud(),diagnosticosResumenDiario.getEnfermedad());
				if(diagnosticosResumenDiarioAux!=null) {
				diagnosticosResumenDiarioAux.setConfirmado(diagnosticosResumenDiario.getConfirmado());
				diagnosticosResumenDiarioAux.setCurado(diagnosticosResumenDiario.getCurado());
				diagnosticosResumenDiarioAux.setFallecido(diagnosticosResumenDiario.getFallecido());
				diagnosticosResumenDiarioAux.setNegativo(diagnosticosResumenDiario.getNegativo());
				diagnosticosResumenDiarioAux.setSospechoso(diagnosticosResumenDiario.getSospechoso());
				diagnosticoResumenDiarioRepository.save(diagnosticosResumenDiarioAux);
				}else {
					diagnosticoResumenDiarioRepository.save(diagnosticosResumenDiario);
				}
            	}catch (NotDataFoundException e) {
            		log.error(e.getMessage(),e);
            		diagnosticoResumenDiarioRepository.save(diagnosticosResumenDiario);
				}
			}
        } catch (UnknownHostException e) {
            log.error("Error Actualizando hora en nodo:", e);
        }
    }
}
