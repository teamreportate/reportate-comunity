package bo.com.reportate.service.impl;

import bo.com.reportate.exception.NotDataFoundException;
import bo.com.reportate.model.*;
import bo.com.reportate.model.dto.response.ItemDto;
import bo.com.reportate.model.dto.response.ResumenDto;
import bo.com.reportate.model.dto.response.TablaResponse;
import bo.com.reportate.model.enums.EstadoEnum;
import bo.com.reportate.repository.*;
import bo.com.reportate.service.DiagnosticoResumenTotalEstadoService;
import bo.com.reportate.util.ValidationUtil;
import bo.com.reportate.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
			centroSaluds.add(this.centroSaludRepository.findByIdAndEstado(centroSaludId, EstadoEnum.ACTIVO)
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
	
	@Override
	@Transactional(readOnly = true)
	public TablaResponse cantidadDiagnosticoPorLugar(Authentication authentication,Date from, Date to, Long departamentoId,
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
			centroSaluds.add(this.centroSaludRepository.findByIdAndEstado(centroSaludId, EstadoEnum.ACTIVO)
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
		TablaResponse tabla= new TablaResponse();
		//si el usuario tiene varios departamentos, debera agrupar por departamento
		//si el usuario tiene un departamento y tiene varios municipios, debera agupar por municipos
		//si el usuario tiene un municipio y tiene varios centros de salud, debera agrupar por centros de salud
		List<ItemDto> items = null;
		if(departamentos.size()>1) {
			items=diagnosticoResumenTotalDiarioRepository.listarPorRangoFechasDepartamento(DateUtil.formatToStart(from), DateUtil.formatToEnd(to), departamentos, municipios, centroSaluds, enfermedads);
			tabla.setNivelLugar("Departamento");
		}else if(municipios.size()>1) {
			items=diagnosticoResumenTotalDiarioRepository.listarPorRangoFechasMunicipio(DateUtil.formatToStart(from), DateUtil.formatToEnd(to), departamentos, municipios, centroSaluds, enfermedads);
			tabla.setNivelLugar("Municipio");
		}else {
			items=diagnosticoResumenTotalDiarioRepository.listarPorRangoFechasCentroSalud(DateUtil.formatToStart(from), DateUtil.formatToEnd(to), departamentos, municipios, centroSaluds, enfermedads);
			tabla.setNivelLugar("Centro de Salud");
		}
		tabla.setItems(items);
		return tabla;
	}

}
