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

import java.util.Date;
import java.util.List;

/**
 * @Created by :Reportate
 * @Autor :Ricardo Laredo
 * @Email :rllayus@gmail.com
 * @Date :2020-04-09
 * @Project :reportate
 * @Package :bo.com.reportate.service.impl
 * @Copyright :Reportate
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
            List<DiagnosticosResumenDiario> diagnosticosResumenDiarios= pacienteRepository.resumenPorPacienteEstadoDiagnostico(DateUtil.formatToStart(dateSystem), DateUtil.formatToEnd(dateSystem), EstadoDiagnosticoEnum.SOSPECHOSO, EstadoDiagnosticoEnum.DESCARTADO, EstadoDiagnosticoEnum.POSITIVO, EstadoDiagnosticoEnum.RECUPERADO, EstadoDiagnosticoEnum.DECESO);
            for (DiagnosticosResumenDiario diagnosticosResumenDiario : diagnosticosResumenDiarios) {
            	diagnosticosResumenDiario.setEstado(EstadoEnum.ACTIVO);
            	try {
				DiagnosticosResumenDiario diagnosticosResumenDiarioAux = diagnosticoResumenDiarioRepository.buscarPorFiltros(dateSystem,
						diagnosticosResumenDiario.getDepartamento(),diagnosticosResumenDiario.getMunicipio(),diagnosticosResumenDiario.getCentroSalud(),diagnosticosResumenDiario.getEnfermedad());
				if(diagnosticosResumenDiarioAux!=null) {
				diagnosticosResumenDiarioAux.setPositivo(diagnosticosResumenDiario.getPositivo());
				diagnosticosResumenDiarioAux.setRecuperado(diagnosticosResumenDiario.getRecuperado());
				diagnosticosResumenDiarioAux.setDeceso(diagnosticosResumenDiario.getDeceso());
				diagnosticosResumenDiarioAux.setDescartado(diagnosticosResumenDiario.getDescartado());
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
            
            diagnosticosResumenDiarios= pacienteRepository.resumenPorPacienteEstadoDiagnostico(DateUtil.formatToStart(DateUtil.toDate("dd/MM/yyyy", "10/04/2020")), DateUtil.formatToEnd(dateSystem), EstadoDiagnosticoEnum.SOSPECHOSO, EstadoDiagnosticoEnum.DESCARTADO, EstadoDiagnosticoEnum.POSITIVO, EstadoDiagnosticoEnum.RECUPERADO, EstadoDiagnosticoEnum.DECESO);
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
					diagnosticosResumenTotalDiarioAux.setPositivo(diagnosticosResumenDiario.getPositivo());
					diagnosticosResumenTotalDiarioAux.setRecuperado(diagnosticosResumenDiario.getRecuperado());
					diagnosticosResumenTotalDiarioAux.setDeceso(diagnosticosResumenDiario.getDeceso());
					diagnosticosResumenTotalDiarioAux.setDescartado(diagnosticosResumenDiario.getDescartado());
					diagnosticosResumenTotalDiarioAux.setSospechoso(diagnosticosResumenDiario.getSospechoso());
					diagnosticoResumenTotalDiarioRepository.save(diagnosticosResumenTotalDiarioAux);
				}else {
					diagnosticosResumenTotalDiarioAux = new DiagnosticosResumenTotalDiario();
					diagnosticosResumenTotalDiarioAux.setCentroSalud(diagnosticosResumenDiario.getCentroSalud());
					diagnosticosResumenTotalDiarioAux.setEnfermedad(diagnosticosResumenDiario.getEnfermedad());
					diagnosticosResumenTotalDiarioAux.setDepartamento(diagnosticosResumenDiario.getDepartamento());
					diagnosticosResumenTotalDiarioAux.setMunicipio(diagnosticosResumenDiario.getMunicipio());
					diagnosticosResumenTotalDiarioAux.setPositivo(diagnosticosResumenDiario.getPositivo());
					diagnosticosResumenTotalDiarioAux.setRecuperado(diagnosticosResumenDiario.getRecuperado());
					diagnosticosResumenTotalDiarioAux.setDeceso(diagnosticosResumenDiario.getDeceso());
					diagnosticosResumenTotalDiarioAux.setDescartado(diagnosticosResumenDiario.getDescartado());
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
					diagnosticosResumenTotalDiarioAux.setPositivo(diagnosticosResumenDiario.getPositivo());
					diagnosticosResumenTotalDiarioAux.setRecuperado(diagnosticosResumenDiario.getRecuperado());
					diagnosticosResumenTotalDiarioAux.setDeceso(diagnosticosResumenDiario.getDeceso());
					diagnosticosResumenTotalDiarioAux.setDescartado(diagnosticosResumenDiario.getDescartado());
					diagnosticosResumenTotalDiarioAux.setSospechoso(diagnosticosResumenDiario.getSospechoso());
            		diagnosticoResumenTotalDiarioRepository.save(diagnosticosResumenTotalDiarioAux);
				}
			}
        } catch (Exception e) {
            log.error("Error al Totalizar los datos estadisticos:", e);
        }
    }
}
