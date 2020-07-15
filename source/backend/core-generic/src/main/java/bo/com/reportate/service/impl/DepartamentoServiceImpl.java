package bo.com.reportate.service.impl;

import bo.com.reportate.exception.NotDataFoundException;
import bo.com.reportate.exception.OperationException;
import bo.com.reportate.model.Departamento;
import bo.com.reportate.model.MuUsuario;
import bo.com.reportate.model.dto.DepartamentoDto;
import bo.com.reportate.model.dto.DepartamentoUsuarioDto;
import bo.com.reportate.model.dto.response.ObjetoResponseDto;
import bo.com.reportate.repository.*;
import bo.com.reportate.service.DepartamentoService;
import bo.com.reportate.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Created by :Reportate
 * @Autor :Ricardo Laredo
 * @Email :rllayus@gmail.com
 * @Date :2020-03-30
 * @Project :reportate
 * @Package :bo.com.reportate.service.impl
 * @Copyright :Reportate
 */
@Service
public class DepartamentoServiceImpl implements DepartamentoService {
    @Autowired private DepartamentoRepository departamentoRepository;
    @Autowired private DepartamentoUsuarioRepository departamentoUsuarioRepository;
    @Autowired private MunicipioRepository municipioRepository;
    @Autowired private MunicipioUsuarioRepository municipioUsuarioRepository;
    @Autowired private CentroSaludRepository centroSaludRepository;
    @Autowired private CentroSaludUsuarioRepository centroSaludUsuarioRepository;

    @Override
    public Departamento findById(Long departamentoId) {
        return this.departamentoRepository.findById(departamentoId).orElseThrow(()-> new NotDataFoundException("No se encontro ningún departamento con ID: "+departamentoId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<DepartamentoDto> findAllConMunicipio() {
        return departamentoRepository.cargarConMunicipio();
    }


    @Override
    public Departamento save(Departamento departamento) {
        ValidationUtil.throwExceptionIfInvalidText("nombre",departamento.getNombre(),true,100);
        if(departamentoRepository.existsByNombreIgnoreCase(departamento.getNombre())){
            throw new OperationException("Ya existe un departamento con el nombre: "+departamento.getNombre());
        }
        return departamentoRepository.save(departamento);
    }
    @Override
    public Departamento save(String nombre, Double latitud, Double longitud, String telefono) {
        ValidationUtil.throwExceptionIfInvalidText("nombre",nombre,true,100);
        ValidationUtil.throwExceptionIfInvalidText("teléfono",telefono,true,9);
        if(departamentoRepository.existsByNombreIgnoreCase(nombre)){
            throw new OperationException("Ya existe un departamento con el nombre: "+nombre);
        }
        return departamentoRepository.save(Departamento.builder().nombre(nombre).latitud(latitud).longitud(longitud).telefono(telefono).build());
    }

    @Override
    public Departamento update(Long departamentoId, String nombre, Double latitud, Double longitud, String telefono) {
        ValidationUtil.throwExceptionIfInvalidText("nombre",nombre,true,100);
        ValidationUtil.throwExceptionIfInvalidText("teléfono",telefono,true,9);
        if(departamentoRepository.existsByIdIsNotAndNombreIgnoreCase(departamentoId, nombre)){
            throw new OperationException("Ya existe un departamento con el nombre: "+nombre);
        }
        Departamento departamento = this.departamentoRepository.findById(departamentoId).orElseThrow(()-> new NotDataFoundException("No se encontró ningún departamento con ID: "+departamentoId));
        departamento.setNombre(nombre);
        departamento.setLatitud(latitud);
        departamento.setLongitud(longitud);
        departamento.setTelefono(telefono);
        return this.departamentoRepository.save(departamento);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void eliminar(Long departamentoId) {
        this.departamentoRepository.eliminar(departamentoId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DepartamentoUsuarioDto> listarDepartamentosAsignados(String username) {
        List<DepartamentoUsuarioDto> departamentoUsuarioDtos = departamentoUsuarioRepository.listarDepartamentoAsignados(username);
        departamentoUsuarioDtos.addAll(departamentoUsuarioRepository.listarDepartamentoNoAsignados(username));
        return departamentoUsuarioDtos;
    }

    @Override
    public List<DepartamentoUsuarioDto> listarAsignados(Authentication userDetails) {
        MuUsuario user = (MuUsuario) userDetails.getPrincipal();
        return departamentoUsuarioRepository.listarAsignados(user);
    }

    @Override
    @Transactional(readOnly = true)
    public ObjetoResponseDto listarDepartamentoMunicipioCentroSalud() {
        ObjetoResponseDto response = new ObjetoResponseDto();
        response.setDepartamentos(this.departamentoRepository.listarDepartamento());
        response.setMunicipios(this.municipioRepository.listaMunicipios());
        response.setCentrosSalud(this.centroSaludRepository.listaCentrosSalud());
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public ObjetoResponseDto listarDepartamentoMunicipioCentroSaludAsignados(Authentication userDetails) {
        MuUsuario usuario = (MuUsuario)userDetails.getPrincipal();
        ObjetoResponseDto response = new ObjetoResponseDto();
        response.setDepartamentos(this.departamentoUsuarioRepository.listarAsignados(usuario));
        response.setMunicipios(this.municipioUsuarioRepository.listarMunicipiosAsignados(usuario));
        response.setCentrosSalud(this.centroSaludUsuarioRepository.listarCentrosSaludAsignados(usuario));
        return response;
    }
}
