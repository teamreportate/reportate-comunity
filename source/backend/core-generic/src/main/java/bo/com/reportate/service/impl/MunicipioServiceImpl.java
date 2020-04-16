package bo.com.reportate.service.impl;

import bo.com.reportate.exception.NotDataFoundException;
import bo.com.reportate.exception.OperationException;
import bo.com.reportate.model.Departamento;
import bo.com.reportate.model.Municipio;
import bo.com.reportate.model.dto.MunicipioDto;
import bo.com.reportate.model.dto.MunicipioUsuarioDto;
import bo.com.reportate.model.enums.EstadoEnum;
import bo.com.reportate.repository.DepartamentoRepository;
import bo.com.reportate.repository.MunicipioRepository;
import bo.com.reportate.repository.MunicipioUsuarioRepository;
import bo.com.reportate.service.MunicipioService;
import bo.com.reportate.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
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
public class MunicipioServiceImpl implements MunicipioService {
    @Autowired private MunicipioRepository municipioRepository;
    @Autowired private DepartamentoRepository departamentoRepository;
    @Autowired private MunicipioUsuarioRepository municipioUsuarioRepository;

    @Override
    @Transactional(readOnly = true)
    public  List<Municipio> findAll() {
        return municipioRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MunicipioDto> findByDepartamento(Long idDepartamento) {
        return municipioRepository.findByEstadoInAndDepartamentoIdOrderByNombreAsc(Arrays.asList(EstadoEnum.ACTIVO, EstadoEnum.INACTIVO), idDepartamento);
    }

    @Override
    public Municipio save(Municipio municipio) {
        ValidationUtil.throwExceptionIfInvalidText("nombre",municipio.getNombre(),true,100);
        if(municipioRepository.existsByNombreIgnoreCaseAndDepartamento(municipio.getNombre(), municipio.getDepartamento())){
            throw new OperationException("Ya existe un municipio con el nombre: "+municipio.getNombre());
        }
        return municipioRepository.save(municipio);
    }
    @Override
    public Municipio save(Long idDepartamento, String nombre, Double latitud, Double longitud, String telefono) {
        Departamento departamento = this.departamentoRepository.findById(idDepartamento).orElseThrow(()->new NotDataFoundException("No se encontró ningún departamento con el ID: "+idDepartamento));
        ValidationUtil.throwExceptionIfInvalidText("nombre",nombre,true,100);
        ValidationUtil.throwExceptionIfInvalidText("teléfono",telefono,true,9);
        if(municipioRepository.existsByNombreIgnoreCaseAndDepartamento(nombre, departamento)){
            throw new OperationException("Ya existe un municipio con el nombre: "+nombre);
        }
        return municipioRepository.save(Municipio.builder().nombre(nombre).latitud(latitud).longitud(longitud).telefono(telefono).departamento(departamento).build());
    }

    @Override
    @Transactional(readOnly = true)
    public List<MunicipioUsuarioDto> listarMuniciposAsignados(String username) {
        List<MunicipioUsuarioDto> list = this.municipioUsuarioRepository.listarMunicipiosAsignados(username);
        list.addAll(this.municipioUsuarioRepository.listarMunicipiosNoAsignados(username));
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public Municipio findById(Long municipioId) {
        return this.municipioRepository.findById(municipioId).orElseThrow(()->new NotDataFoundException("No se encontro ningún municipio con ID: "+municipioId));
    }

    @Override
    public Municipio update(Long municipioId, String nombre, Double latitud, Double longitud, String telefono) {
        ValidationUtil.throwExceptionIfInvalidText("nombre",nombre,true,100);
        ValidationUtil.throwExceptionIfInvalidText("teléfono",telefono,true,9);
        if(municipioRepository.existsByIdIsNotAndNombreIgnoreCase(municipioId, nombre)){
            throw new OperationException("Ya existe un municipio con el nombre: "+nombre);
        }
        Municipio municipio = this.municipioRepository.findById(municipioId).orElseThrow(()-> new NotDataFoundException("No se encontró ningún departamento con ID: "+municipioId));
        municipio.setNombre(nombre);
        municipio.setLatitud(latitud);
        municipio.setLongitud(longitud);
        municipio.setTelefono(telefono);
        return this.municipioRepository.save(municipio);

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void eliminar(Long municipioId) {
        this.municipioRepository.eliminar(municipioId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void inactivar(Long municipioId) {
        this.municipioRepository.inactivar(municipioId);
    }
}
