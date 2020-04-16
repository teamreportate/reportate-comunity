package bo.com.reportate.service.impl;

import bo.com.reportate.exception.NotDataFoundException;
import bo.com.reportate.exception.OperationException;
import bo.com.reportate.model.*;
import bo.com.reportate.model.dto.DiagnosticoDto;
import bo.com.reportate.model.dto.response.DiagnosticoResponseDto;
import bo.com.reportate.model.dto.response.DiagnosticoSintomaResponse;
import bo.com.reportate.model.dto.response.MapResponse;
import bo.com.reportate.model.dto.response.NivelValoracionDto;
import bo.com.reportate.model.enums.Process;
import bo.com.reportate.model.enums.*;
import bo.com.reportate.repository.*;
import bo.com.reportate.service.DiagnosticoService;
import bo.com.reportate.service.LogService;
import bo.com.reportate.util.ValidationUtil;
import bo.com.reportate.utils.DateUtil;
import bo.com.reportate.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Created by :MC4
 * @Autor :Ricardo Laredo
 * @Email :rlaredo@mc4.com.bo
 * @Date :2020-04-02
 * @Project :reportate
 * @Package :bo.com.reportate.service.impl
 * @Copyright :MC4
 */
@Service
@Slf4j
public class DiagnosticoServiceImpl implements DiagnosticoService {
	@Autowired
	private DiagnosticoRepository diagnosticoRepository;
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
	@Autowired
	private DiagnosticoSintomaRepository diagnosticoSintomaRepository;
	@Autowired
	private LogService logService;

	@Override
	@Transactional(readOnly = true)
	public Page<DiagnosticoResponseDto> listarDiagnostico(Authentication authentication, Date from, Date to,
			Long departamentoId, Long municipioId, Long centroSaludId, String nomprePaciente,
			EstadoDiagnosticoEnum estadoDiagnostico, Long enfermedadId, Pageable pageable) {

		MuUsuario usuario = (MuUsuario) authentication.getPrincipal();
		ValidationUtil.throwExceptionIfInvalidNumber("departamento", departamentoId, true, -1L);
		ValidationUtil.throwExceptionIfInvalidNumber("municipio", municipioId, true, -1L);
		ValidationUtil.throwExceptionIfInvalidNumber("centro de salud", centroSaludId, true, -1L);
		ValidationUtil.throwExceptionIfInvalidNumber("enfermedad", enfermedadId, true, -1L);
		ValidationUtil.throwExceptionIfInvalidText("nombre paciente", nomprePaciente, false, 160);
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
			if(!departamentos.isEmpty())
			municipios.addAll(this.municipioUsuarioRepository.listarMunicipiosAsignados(usuario, departamentos));
		}
		if (centroSaludId > 0L) {
			centroSaluds.add(this.centroSaludRepository.findByIdAndEstado(municipioId, EstadoEnum.ACTIVO)
					.orElseThrow(() -> new NotDataFoundException("No se encontro el centro de salud seleccionad")));
		} else {
			if(!municipios.isEmpty())
			centroSaluds.addAll(this.centroSaludUsuarioRepository.listarCentrosSaludAsignados(usuario, municipios));
		}
		if (enfermedadId > 0L) {
			enfermedads.add(this.enfermedadRepository.findByIdAndEstado(enfermedadId, EstadoEnum.ACTIVO)
					.orElseThrow(() -> new NotDataFoundException("No se encontro la enfermedad seleccionada")));
		} else {
			enfermedads.addAll(enfermedadRepository.findByEnfermedadBaseFalseAndEstado(EstadoEnum.ACTIVO));
		}
		List<EstadoDiagnosticoEnum> diagnosticoEnums = new ArrayList<>();
		if (estadoDiagnostico.equals(EstadoDiagnosticoEnum.TODOS)) {
			diagnosticoEnums.addAll(Arrays.asList(EstadoDiagnosticoEnum.values()));
		} else {
			diagnosticoEnums.add(estadoDiagnostico);
		}
		if(departamentos.isEmpty()){
			throw new OperationException("No tiene permisos para acceder a la información solicitada");
		}
		if(municipios.isEmpty()){
			throw new OperationException("No tiene permisos para acceder a la información solicitada");
		}

		if(centroSaluds.isEmpty()){
			throw new OperationException("No tiene permisos para acceder a la información solicitada");
		}
		if(enfermedads.isEmpty()){
			throw new OperationException("No existen enfermedades activos para buscar");
		}
		if(diagnosticoEnums.isEmpty()){
			throw new OperationException("No existen criterios de clasificación de diagnosticos para buscar");
		}

		if (!StringUtil.isEmptyOrNull(nomprePaciente)) {
			return diagnosticoRepository.listarDiagnostico(from, to, departamentos, municipios, centroSaluds,
					diagnosticoEnums, enfermedads, nomprePaciente.toLowerCase(), pageable);
		} else {
			return diagnosticoRepository.listarDiagnostico(from, to, departamentos, municipios, centroSaluds,
					diagnosticoEnums, enfermedads, pageable);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<DiagnosticoSintomaResponse> listarSintomas(Long diagnosticoId) {
		Diagnostico diagnostico = this.diagnosticoRepository.findByIdAndEstado(diagnosticoId, EstadoEnum.ACTIVO).orElseThrow(()->new NotDataFoundException("No se encontró el diagnostico"));
		return this.diagnosticoSintomaRepository.listarSintomas(diagnostico);
	}

	@Override
	@Transactional(readOnly = true)
	public Integer cantidadDiagnosticoPorFiltros(Authentication authentication,Long departamentoId, Long municipioId, Long centroSaludId, GeneroEnum genero,
			Integer edadInicial, Integer edadFinal, EstadoDiagnosticoEnum estadoDiagnostico, Long enfermedadId) {

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
			if(!departamentos.isEmpty())
			municipios.addAll(this.municipioUsuarioRepository.listarMunicipiosAsignados(usuario, departamentos));
		}
		if (centroSaludId > 0L) {
			centroSaluds.add(this.centroSaludRepository.findByIdAndEstado(municipioId, EstadoEnum.ACTIVO)
					.orElseThrow(() -> new NotDataFoundException("No se encontro el centro de salud seleccionad")));
		} else {
			if(!municipios.isEmpty())
			centroSaluds.addAll(this.centroSaludUsuarioRepository.listarCentrosSaludAsignados(usuario, municipios));
		}
		if (enfermedadId > 0L) {
			enfermedads.add(this.enfermedadRepository.findByIdAndEstado(enfermedadId, EstadoEnum.ACTIVO)
					.orElseThrow(() -> new NotDataFoundException("No se encontro la enfermedad seleccionada")));
		} else {
			enfermedads.addAll(enfermedadRepository.findByEnfermedadBaseFalseAndEstado(EstadoEnum.ACTIVO));
		}
		List<EstadoDiagnosticoEnum> diagnosticoEnums = new ArrayList<>();
		if (estadoDiagnostico.equals(EstadoDiagnosticoEnum.TODOS)) {
			diagnosticoEnums.addAll(Arrays.asList(EstadoDiagnosticoEnum.values()));
		} else {
			diagnosticoEnums.add(estadoDiagnostico);
		}
		List<GeneroEnum> generoEnums = new ArrayList<>();
		if (genero == null) {
			generoEnums.addAll(Arrays.asList(GeneroEnum.values()));
		} else {
			generoEnums.add(genero);
		}
		if(departamentos.isEmpty()){
			throw new OperationException("No tiene permisos para acceder a la información solicitada");
		}
		if(municipios.isEmpty()){
			throw new OperationException("No tiene permisos para acceder a la información solicitada");
		}

		if(centroSaluds.isEmpty()){
			throw new OperationException("No tiene permisos para acceder a la información solicitada");
		}
		if(enfermedads.isEmpty()){
			throw new OperationException("No existen enfermedades activos para buscar");
		}
		if(diagnosticoEnums.isEmpty()){
			throw new OperationException("No existen criterios de clasificación de diagnosticos para buscar");
		}
		return diagnosticoRepository.cantidadDiagnosticoPorFiltros(departamentos,
				municipios, centroSaluds, diagnosticoEnums, enfermedads, generoEnums, edadInicial, edadFinal);
	}

	@Override
	public List<NivelValoracionDto> listarPorNivelValoracion(Date from, Date to) {
		List<NivelValoracionDto> list = diagnosticoRepository.listarPorNivelValoracion(DateUtil.formatToStart(from), DateUtil.formatToEnd(to));
		Set<String> deptSet = new HashSet<>();
		list.removeIf(p -> !deptSet.add(p.getRegistrado()));
		return list;
	}


    @Override
    public List<MapResponse> listarPacientesPor(Authentication authentication, Date from, Date to, Long departamentoId, Long municipioId, Long centroSaludId, Long enfermedadId, EstadoDiagnosticoEnum estadoDiagnostico) {

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
        List<EstadoDiagnosticoEnum> diagnosticoEnums = new ArrayList<>();
        if (estadoDiagnostico.equals(EstadoDiagnosticoEnum.TODOS)) {
            diagnosticoEnums.addAll(Arrays.asList(EstadoDiagnosticoEnum.values()));
        } else {
            diagnosticoEnums.add(estadoDiagnostico);
        }
        if(departamentos.isEmpty()){
        	throw new OperationException("No tiene permisos para acceder a la información solicitada");
		}
		if(municipios.isEmpty()){
			throw new OperationException("No tiene permisos para acceder a la información solicitada");
		}

		if(centroSaluds.isEmpty()){
			throw new OperationException("No tiene permisos para acceder a la información solicitada");
		}
		if(enfermedads.isEmpty()){
			throw new OperationException("No existen enfermedades activos para buscar");
		}
		if(diagnosticoEnums.isEmpty()){
			throw new OperationException("No existen criterios de clasificación de diagnosticos para buscar");
		}
        return diagnosticoRepository.listarPacientesParaMapa(from, to, departamentos, municipios, centroSaluds, diagnosticoEnums, enfermedads);
    }

	@Override
	public DiagnosticoDto actualizarDiagnostico(Authentication authentication, Long diagnosticoId, EstadoDiagnosticoEnum estadoDiagnosticoEnum, String observacion) {
		MuUsuario usuario = (MuUsuario) authentication.getPrincipal();
		ValidationUtil.throwExceptionIfInvalidNumber("diagnostico",diagnosticoId,true ,0L);
		ValidationUtil.throwExceptionIfInvalidText("observación", observacion, false, 4000);
		if(!usuario.getTipoUsuario().equals(TipoUsuarioEnum.MEDICO)){
			log.error("El usuario {} no es medico para actualizar el estado de un diagnostico", usuario.getUsername());
			throw new OperationException("Esta opción sólo está permitido para médicos.");
		}
		Diagnostico diagnostico = this.diagnosticoRepository.findByIdAndEstado(diagnosticoId,EstadoEnum.ACTIVO).orElseThrow(()->new OperationException("No se encontró el diagnóstico"));
		log.info("El usuario [{}] esta procediendo a actualizar diagnostico del paciente [{}] a estado {} ", usuario.getUsername(), diagnostico.getControlDiario().getPaciente().getNombre(), estadoDiagnosticoEnum);
		logService.info(Process.DIAGNOSTICO,"El usuario [{}] esta procediendo a actualizar diagnostico del paciente [{}] a estado {} ", usuario.getUsername(), diagnostico.getControlDiario().getPaciente().getNombre(), estadoDiagnosticoEnum);
		diagnostico.setEstadoDiagnostico(estadoDiagnosticoEnum);
		diagnostico.setObservacion(observacion);
		diagnostico.setMedico(usuario);
		this.diagnosticoRepository.save(diagnostico);
		log.info("El usuario [{}] esta procediendo a actualo correctamente el diagnostico del paciente [{}] a estado {} ", usuario.getUsername(), diagnostico.getControlDiario().getPaciente().getNombre(), estadoDiagnosticoEnum);
		logService.info(Process.DIAGNOSTICO,"El usuario [{}] esta procediendo a actualo correctamente el diagnostico del paciente [{}] a estado {} ", usuario.getUsername(), diagnostico.getControlDiario().getPaciente().getNombre(), estadoDiagnosticoEnum);
		return new DiagnosticoDto(diagnostico);
	}
}
