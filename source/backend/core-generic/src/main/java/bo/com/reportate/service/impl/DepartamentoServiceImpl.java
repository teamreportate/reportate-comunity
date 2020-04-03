package bo.com.reportate.service.impl;

import bo.com.reportate.exception.NotDataFoundException;
import bo.com.reportate.exception.OperationException;
import bo.com.reportate.model.Departamento;
import bo.com.reportate.model.MuUsuario;
import bo.com.reportate.model.dto.DepartamentoDto;
import bo.com.reportate.model.dto.DepartamentoUsuarioDto;
import bo.com.reportate.repository.DepartamentoRepository;
import bo.com.reportate.repository.DepartamentoUsuarioRepository;
import bo.com.reportate.service.DepartamentoService;
import bo.com.reportate.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Created by :MC4
 * @Autor :Ricardo Laredo
 * @Email :rlaredo@mc4.com.bo
 * @Date :2020-03-30
 * @Project :reportate
 * @Package :bo.com.reportate.service.impl
 * @Copyright :MC4
 */
@Service
public class DepartamentoServiceImpl implements DepartamentoService {
    @Autowired private DepartamentoRepository departamentoRepository;
    @Autowired private DepartamentoUsuarioRepository departamentoUsuarioRepository;

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
    public Departamento save(String nombre, Double latitud, Double longitud) {
        ValidationUtil.throwExceptionIfInvalidText("nombre",nombre,true,100);
        if(departamentoRepository.existsByNombreIgnoreCase(nombre)){
            throw new OperationException("Ya existe un departamento con el nombre: "+nombre);
        }
        return departamentoRepository.save(Departamento.builder().nombre(nombre).latitud(latitud).longitud(longitud).build());
    }

    @Override
    public Departamento update(Long departamentoId, String nombre, Double latitud, Double longitud) {
        ValidationUtil.throwExceptionIfInvalidText("nombre",nombre,true,100);
        if(departamentoRepository.existsByIdIsNotAndNombreIgnoreCase(departamentoId, nombre)){
            throw new OperationException("Ya existe un departamento con el nombre: "+nombre);
        }
        Departamento departamento = this.departamentoRepository.findById(departamentoId).orElseThrow(()-> new NotDataFoundException("No se encontró ningún departamento con ID: "+departamentoId));
        departamento.setNombre(nombre);
        departamento.setLatitud(latitud);
        departamento.setLongitud(longitud);
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
    public List<DepartamentoUsuarioDto> listarDepartamentosAsignados(Long userId) {
        List<DepartamentoUsuarioDto> departamentoUsuarioDtos = departamentoUsuarioRepository.listarDepartamentoAsignados(userId);
        departamentoUsuarioDtos.addAll(departamentoUsuarioRepository.listarDepartamentoNoAsignados(userId));
        return departamentoUsuarioDtos;
    }

    @Override
    public List<DepartamentoUsuarioDto> listarAsignados(Authentication userDetails) {
        MuUsuario usuario = (MuUsuario) userDetails;
        return departamentoUsuarioRepository.listarAsignados(usuario);
    }
}
