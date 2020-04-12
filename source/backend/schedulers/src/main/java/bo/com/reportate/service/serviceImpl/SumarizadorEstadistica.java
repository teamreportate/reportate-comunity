package bo.com.reportate.service.serviceImpl;

import bo.com.reportate.exception.NotDataFoundException;
import bo.com.reportate.model.DiagnosticosResumenDiario;
import bo.com.reportate.model.DiagnosticosResumenTotalDiario;
import bo.com.reportate.model.enums.EstadoDiagnosticoEnum;
import bo.com.reportate.model.enums.EstadoEnum;
import bo.com.reportate.repository.DiagnosticoResumenDiarioRepository;
import bo.com.reportate.repository.DiagnosticoResumenTotalDiarioRepository;
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
public class SumarizadorEstadistica {
	
	@Autowired
    private PacienteRepository pacienteRepository;
	@Autowired
	private DiagnosticoResumenDiarioRepository diagnosticoResumenDiarioRepository;
	@Autowired
	private DiagnosticoResumenTotalDiarioRepository diagnosticoResumenTotalDiarioRepository;
	
    @Scheduled(fixedRate = 100000)
    public void updateTime() {
        try {
        	Date dateSystem = new Date();
            List<DiagnosticosResumenDiario> diagnosticosResumenDiarios= pacienteRepository.resumenPorPacienteEstadoDiagnostico(DateUtil.formatToStart(dateSystem), DateUtil.formatToEnd(dateSystem), EstadoDiagnosticoEnum.SOSPECHOSO, EstadoDiagnosticoEnum.NEGATIVO, EstadoDiagnosticoEnum.CONFIRMADO, EstadoDiagnosticoEnum.CURADO, EstadoDiagnosticoEnum.FALLECIDO);
            for (DiagnosticosResumenDiario diagnosticosResumenDiario : diagnosticosResumenDiarios) {
            	diagnosticosResumenDiario.setEstado(EstadoEnum.ACTIVO);
            	try {
				DiagnosticosResumenDiario diagnosticosResumenDiarioAux = diagnosticoResumenDiarioRepository.buscarPorFiltros(dateSystem,
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
            
            diagnosticosResumenDiarios= pacienteRepository.resumenPorPacienteEstadoDiagnostico(DateUtil.formatToStart(DateUtil.toDate("dd/MM/yyyy", "10/04/2020")), DateUtil.formatToEnd(dateSystem), EstadoDiagnosticoEnum.SOSPECHOSO, EstadoDiagnosticoEnum.NEGATIVO, EstadoDiagnosticoEnum.CONFIRMADO, EstadoDiagnosticoEnum.CURADO, EstadoDiagnosticoEnum.FALLECIDO);
            for (DiagnosticosResumenDiario diagnosticosResumenDiario : diagnosticosResumenDiarios) {
            	diagnosticosResumenDiario.setEstado(EstadoEnum.ACTIVO);
            	try {
            		DiagnosticosResumenTotalDiario diagnosticosResumenTotalDiarioAux = diagnosticoResumenTotalDiarioRepository.buscarPorFiltros(dateSystem,
						diagnosticosResumenDiario.getDepartamento(),diagnosticosResumenDiario.getMunicipio(),diagnosticosResumenDiario.getCentroSalud(),diagnosticosResumenDiario.getEnfermedad());
				if(diagnosticosResumenTotalDiarioAux!=null) {
					diagnosticosResumenTotalDiarioAux.setCentroSalud(diagnosticosResumenDiario.getCentroSalud());
					diagnosticosResumenTotalDiarioAux.setEnfermedad(diagnosticosResumenDiario.getEnfermedad());
					diagnosticosResumenTotalDiarioAux.setDepartamento(diagnosticosResumenDiario.getDepartamento());
					diagnosticosResumenTotalDiarioAux.setMunicipio(diagnosticosResumenDiario.getMunicipio());
					diagnosticosResumenTotalDiarioAux.setConfirmado(diagnosticosResumenDiario.getConfirmado());
					diagnosticosResumenTotalDiarioAux.setCurado(diagnosticosResumenDiario.getCurado());
					diagnosticosResumenTotalDiarioAux.setFallecido(diagnosticosResumenDiario.getFallecido());
					diagnosticosResumenTotalDiarioAux.setNegativo(diagnosticosResumenDiario.getNegativo());
					diagnosticosResumenTotalDiarioAux.setSospechoso(diagnosticosResumenDiario.getSospechoso());
					diagnosticoResumenTotalDiarioRepository.save(diagnosticosResumenTotalDiarioAux);
				}else {
					diagnosticosResumenTotalDiarioAux = new DiagnosticosResumenTotalDiario();
					diagnosticosResumenTotalDiarioAux.setCentroSalud(diagnosticosResumenDiario.getCentroSalud());
					diagnosticosResumenTotalDiarioAux.setEnfermedad(diagnosticosResumenDiario.getEnfermedad());
					diagnosticosResumenTotalDiarioAux.setDepartamento(diagnosticosResumenDiario.getDepartamento());
					diagnosticosResumenTotalDiarioAux.setMunicipio(diagnosticosResumenDiario.getMunicipio());
					diagnosticosResumenTotalDiarioAux.setConfirmado(diagnosticosResumenDiario.getConfirmado());
					diagnosticosResumenTotalDiarioAux.setCurado(diagnosticosResumenDiario.getCurado());
					diagnosticosResumenTotalDiarioAux.setFallecido(diagnosticosResumenDiario.getFallecido());
					diagnosticosResumenTotalDiarioAux.setNegativo(diagnosticosResumenDiario.getNegativo());
					diagnosticosResumenTotalDiarioAux.setSospechoso(diagnosticosResumenDiario.getSospechoso());
					diagnosticoResumenTotalDiarioRepository.save(diagnosticosResumenTotalDiarioAux);
				}
            	}catch (NotDataFoundException e) {
            		log.error("Error al sumarizar",e);
            		DiagnosticosResumenTotalDiario diagnosticosResumenTotalDiarioAux = new DiagnosticosResumenTotalDiario();
            		diagnosticosResumenTotalDiarioAux.setCentroSalud(diagnosticosResumenDiario.getCentroSalud());
					diagnosticosResumenTotalDiarioAux.setEnfermedad(diagnosticosResumenDiario.getEnfermedad());
					diagnosticosResumenTotalDiarioAux.setDepartamento(diagnosticosResumenDiario.getDepartamento());
					diagnosticosResumenTotalDiarioAux.setMunicipio(diagnosticosResumenDiario.getMunicipio());
					diagnosticosResumenTotalDiarioAux.setConfirmado(diagnosticosResumenDiario.getConfirmado());
					diagnosticosResumenTotalDiarioAux.setCurado(diagnosticosResumenDiario.getCurado());
					diagnosticosResumenTotalDiarioAux.setFallecido(diagnosticosResumenDiario.getFallecido());
					diagnosticosResumenTotalDiarioAux.setNegativo(diagnosticosResumenDiario.getNegativo());
					diagnosticosResumenTotalDiarioAux.setSospechoso(diagnosticosResumenDiario.getSospechoso());
            		diagnosticoResumenTotalDiarioRepository.save(diagnosticosResumenTotalDiarioAux);
				}
			}
        } catch (Exception e) {
            log.error("Error al Totalizar los datos estadisticos:", e);
        }
    }
}
