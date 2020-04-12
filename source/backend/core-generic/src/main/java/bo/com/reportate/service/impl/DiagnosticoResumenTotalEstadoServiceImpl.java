package bo.com.reportate.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import bo.com.reportate.model.dto.response.MapResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bo.com.reportate.exception.NotDataFoundException;
import bo.com.reportate.model.CentroSalud;
import bo.com.reportate.model.Departamento;
import bo.com.reportate.model.Enfermedad;
import bo.com.reportate.model.MuUsuario;
import bo.com.reportate.model.Municipio;
import bo.com.reportate.model.dto.response.ItemDto;
import bo.com.reportate.model.dto.response.ResumenDto;
import bo.com.reportate.model.dto.response.TablaResponse;
import bo.com.reportate.model.enums.EstadoDiagnosticoEnum;
import bo.com.reportate.model.enums.EstadoEnum;
import bo.com.reportate.repository.CentroSaludRepository;
import bo.com.reportate.repository.CentroSaludUsuarioRepository;
import bo.com.reportate.repository.DepartamentoRepository;
import bo.com.reportate.repository.DepartamentoUsuarioRepository;
import bo.com.reportate.repository.DiagnosticoResumenDiarioRepository;
import bo.com.reportate.repository.DiagnosticoResumenTotalDiarioRepository;
import bo.com.reportate.repository.EnfermedadRepository;
import bo.com.reportate.repository.MunicipioRepository;
import bo.com.reportate.repository.MunicipioUsuarioRepository;
import bo.com.reportate.service.DiagnosticoResumenEstadoService;
import bo.com.reportate.service.DiagnosticoResumenTotalEstadoService;
import bo.com.reportate.util.ValidationUtil;
import bo.com.reportate.utils.DateUtil;

@Service
public class DiagnosticoResumenTotalEstadoServiceImpl implements DiagnosticoResumenTotalEstadoService{

	@Autowired
	private DiagnosticoResumenTotalDiarioRepository diagnosticoResumenTotalDiarioRepository;
	@Autowired
	private DepartamentoRepository departamentoRepository;
	@Autowired
	private MunicipioRepository municipioRepository;
	@Autowired
	private EnfermedadRepository enfermedadRepository;
	@Autowired
	private DepartamentoUsuarioRepository departamentoUsuarioRepository;
	@Autowired
	private MunicipioUsuarioRepository municipioUsuarioRepository;
	@Autowired
	private CentroSaludUsuarioRepository centroSaludUsuarioRepository;
	@Autowired
	private CentroSaludRepository centroSaludRepository;
	
	@Override
	@Transactional(readOnly = true)
	public List<ResumenDto> cantidadDiagnosticoPorFiltros(Authentication authentication,Date from, Date to, Long departamentoId,
			Long municipioId, Long centroSaludId, Long enfermedadId) {
		MuUsuario usuario = (MuUsuario) authentication.getPrincipal();
		ValidationUtil.throwExceptionIfInvalidNumber("departamento", departamentoId, true, -1L);
		ValidationUtil.throwExceptionIfInvalidNumber("municipio", municipioId, true, -1L);
		ValidationUtil.throwExceptionIfInvalidNumber("centro de salud", centroSaludId, true, -1L);
		ValidationUtil.throwExceptionIfInvalidNumber("enfermedad", enfermedadId, true, -1L);
		List<Departamento> departamentos = new ArrayList<>();
		List<Municipio> municipios = new ArrayList<>();
		List<CentroSalud> centroSaluds = new ArrayList<>();
		List<Enfermedad> enfermedads = new ArrayList<>();
		if (departamentoId > 0L) {
			departamentos.add(this.departamentoRepository.findByIdAndEstado(departamentoId, EstadoEnum.ACTIVO)
					.orElseThrow(() -> new NotDataFoundException("No se encontró el departamento seleccionado")));
		} else {
			departamentos = this.departamentoUsuarioRepository.listarDepartamentoAsignados(usuario);
		}

		if (municipioId > 0L) {
			municipios.add(this.municipioRepository.findByIdAndEstado(municipioId, EstadoEnum.ACTIVO)
					.orElseThrow(() -> new NotDataFoundException("No se encontro el municipio seleccionado")));

		} else {
			municipios.addAll(this.municipioUsuarioRepository.listarMunicipiosAsignados(usuario, departamentos));
		}
		if (centroSaludId > 0L) {
			centroSaluds.add(this.centroSaludRepository.findByIdAndEstado(municipioId, EstadoEnum.ACTIVO)
					.orElseThrow(() -> new NotDataFoundException("No se encontro el centro de salud seleccionad")));
		} else {
			centroSaluds.addAll(this.centroSaludUsuarioRepository.listarCentrosSaludAsignados(usuario, municipios));
		}
		if (enfermedadId > 0L) {
			enfermedads.add(this.enfermedadRepository.findByIdAndEstado(enfermedadId, EstadoEnum.ACTIVO)
					.orElseThrow(() -> new NotDataFoundException("No se encontro la enfermedad seleccionada")));
		} else {
			enfermedads.addAll(enfermedadRepository.findByEnfermedadBaseFalseAndEstado(EstadoEnum.ACTIVO));
		}
		
		List<ResumenDto> resumen= diagnosticoResumenTotalDiarioRepository.listarPorRangoFechas(DateUtil.formatToStart(from), DateUtil.formatToEnd(to), departamentos, municipios, centroSaluds, enfermedads);
		List<ResumenDto> resumenAux = new ArrayList<>();
		for (ResumenDto resumenDto : resumen) {
			if(!resumenAux.contains(resumenDto)) {
				resumenAux.add(resumenDto);
			}else {
				ResumenDto resumenDtoAux = resumenAux.get(resumenAux.indexOf(resumenDto));
				resumenDtoAux.setConfirmado(resumenDto.getConfirmado()+resumenDtoAux.getConfirmado());
				resumenDtoAux.setCurado(resumenDto.getCurado()+resumenDtoAux.getCurado());
				resumenDtoAux.setFallecido(resumenDto.getFallecido()+resumenDtoAux.getFallecido());
				resumenDtoAux.setNegativo(resumenDto.getNegativo()+resumenDtoAux.getNegativo());
				resumenDtoAux.setSospechoso(resumenDto.getSospechoso()+resumenDtoAux.getSospechoso());
			}
		}
		return resumenAux;
	}
}
