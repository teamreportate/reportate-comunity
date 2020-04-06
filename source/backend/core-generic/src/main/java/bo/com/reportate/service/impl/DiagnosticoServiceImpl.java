package bo.com.reportate.service.impl;

import bo.com.reportate.exception.NotDataFoundException;
import bo.com.reportate.model.*;
import bo.com.reportate.model.dto.response.DiagnosticoResponseDto;
import bo.com.reportate.model.enums.EstadoDiagnosticoEnum;
import bo.com.reportate.model.enums.EstadoEnum;
import bo.com.reportate.repository.*;
import bo.com.reportate.service.DiagnosticoService;
import bo.com.reportate.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
public class DiagnosticoServiceImpl implements DiagnosticoService {
    @Autowired
    private DiagnosticoRepository diagnosticoRepository;
    @Autowired private DepartamentoRepository departamentoRepository;
    @Autowired private EnfermedadRepository enfermedadRepository;
    @Autowired private DepartamentoUsuarioRepository departamentoUsuarioRepository;
    @Autowired private MunicipioRepository municipioRepository;
    @Autowired private CentroSaludRepository centroSaludRepository;
    @Override
    @Transactional(readOnly = true)
    public Page<DiagnosticoResponseDto>     listarDiagnostico(
            Authentication authentication,
            Date from, Date to, Long departamentoId, Long municipioId, Long centroSaludId, String nomprePaciente,
            EstadoDiagnosticoEnum estadoDiagnostico, Long enfermedadId, Pageable pageable) {

        MuUsuario usuario = (MuUsuario)authentication.getPrincipal();
        ValidationUtil.throwExceptionIfInvalidNumber("departamento",departamentoId,true,-1L);
        ValidationUtil.throwExceptionIfInvalidNumber("municipio", municipioId,true,-1L);
        ValidationUtil.throwExceptionIfInvalidNumber("centro de salud", centroSaludId, true, -1L);
        ValidationUtil.throwExceptionIfInvalidNumber("enfermedad",enfermedadId, true,-1L);
        ValidationUtil.throwExceptionIfInvalidText("nombre paciente", nomprePaciente, false,160);
        List<Departamento> departamentos = new ArrayList<>();
        List<Municipio> municipios = new ArrayList<>();
        List<CentroSalud> centroSaluds = new ArrayList<>();
        List<Enfermedad> enfermedads = new ArrayList<>();
        if(departamentoId > 0L) {
            departamentos.add(this.departamentoRepository.findByIdAndEstado(departamentoId, EstadoEnum.ACTIVO).orElseThrow(() -> new NotDataFoundException("No se encontró el departamento seleccionado")));
        }else{
            departamentos = this.departamentoUsuarioRepository.listarDepartamentoAsignados(usuario);
        }

        if(municipioId > 0L){
            municipios.add(this.municipioRepository.findByIdAndEstado(municipioId,EstadoEnum.ACTIVO).orElseThrow(()->new NotDataFoundException("No se encontro el municipio seleccionado")));

        }else{
            municipios.addAll(this.municipioRepository.findByDepartamentoContainsAndEstado(departamentos, EstadoEnum.ACTIVO));
        }
        if(centroSaludId > 0L){
            centroSaluds.add(this.centroSaludRepository.findByIdAndEstado(municipioId,EstadoEnum.ACTIVO).orElseThrow(()-> new NotDataFoundException("No se encontro el centro de salud seleccionad")));
        }else {
            centroSaluds.addAll(this.centroSaludRepository.findByMunicipioContainsAndEstado(municipios,EstadoEnum.ACTIVO));
        }
        if(enfermedadId > 0L){
            enfermedads.add(this.enfermedadRepository.findByIdAndEstado(enfermedadId,EstadoEnum.ACTIVO).orElseThrow(()->new NotDataFoundException("No se encontro la enfermedad seleccionada")));
        }else{
            enfermedads.addAll(enfermedadRepository.findByEnfermedadBaseFalseAndEstado(EstadoEnum.ACTIVO));
        }
        List<EstadoDiagnosticoEnum> diagnosticoEnums = new ArrayList<>();
        if(estadoDiagnostico.equals(EstadoDiagnosticoEnum.TODOS)){
            diagnosticoEnums.addAll(Arrays.asList(EstadoDiagnosticoEnum.values()));
        }else{
            diagnosticoEnums.add(estadoDiagnostico);
        }
        nomprePaciente = "%"+nomprePaciente.trim()+"%";
        return diagnosticoRepository.listarDiagnostico(from, to,departamentos, municipios, centroSaluds,diagnosticoEnums,enfermedads, nomprePaciente.toLowerCase(), pageable );
    }
}
