package bo.com.reportate.service.impl;

import bo.com.reportate.exception.NotDataFoundException;
import bo.com.reportate.exception.OperationException;
import bo.com.reportate.model.CentroSalud;
import bo.com.reportate.model.Municipio;
import bo.com.reportate.model.dto.CentroSaludDto;
import bo.com.reportate.model.dto.CentroSaludUsuarioDto;
import bo.com.reportate.model.enums.EstadoEnum;
import bo.com.reportate.repository.CentroSaludRepository;
import bo.com.reportate.repository.CentroSaludUsuarioRepository;
import bo.com.reportate.repository.MunicipioRepository;
import bo.com.reportate.service.CentroSaludService;
import bo.com.reportate.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
public class CentroSaludServiceImpl implements CentroSaludService {
    @Autowired private CentroSaludRepository centroSaludRepository;
    @Autowired private MunicipioRepository municipioRepository;
    @Autowired private CentroSaludUsuarioRepository centroSaludUsuarioRepository;

    @Override
    public CentroSalud findById(Long centroSaludId) {
        return this.centroSaludRepository.findById(centroSaludId).orElseThrow(()->new NotDataFoundException("No se encontró ningún centro de salud con ID: "+centroSaludId));
    }

    @Override
    public List<CentroSalud> findAll() {
        return centroSaludRepository.findAll();
    }

    @Override
    public List<CentroSaludDto> findByMunicipio(Long idMunicipio) {
        return centroSaludRepository.findByMunicipioIdAndEstadoOrderByIdDesc(idMunicipio, EstadoEnum.ACTIVO);
    }

    @Override
    public CentroSalud save(CentroSalud centroSalud) {
        ValidationUtil.throwExceptionIfInvalidText("nombre",centroSalud.getNombre(),true,100);
        ValidationUtil.throwExceptionIfInvalidText("dirección",centroSalud.getDireccion(),true,200);
        ValidationUtil.throwExceptionIfInvalidText("zona",centroSalud.getDireccion(),true,100);
        ValidationUtil.throwExceptionIfInvalidText("teléfono",centroSalud.getTelefono(),true,9);
        if(centroSaludRepository.existsByMunicipioAndNombreIgnoreCase(centroSalud.getMunicipio(),centroSalud.getNombre())){
            throw new OperationException("Ya existe un centro de salud con el nombre: "+centroSalud.getNombre()+" en el municipio: "+centroSalud.getMunicipio().getNombre());
        }
        return centroSaludRepository.save(centroSalud);
    }

    @Override
    public CentroSalud save(Long idMunicipio, String nombre, String direccion, String zona, String telefono, Double latitud, Double longitud) {
        ValidationUtil.throwExceptionIfInvalidText("nombre",nombre,true,100);
        ValidationUtil.throwExceptionIfInvalidText("zona",zona,true,100);
        ValidationUtil.throwExceptionIfInvalidText("dirección",direccion,true,200);
        ValidationUtil.throwExceptionIfInvalidText("teléfono",telefono,true,9);
        Municipio municipio = this.municipioRepository.findById(idMunicipio).orElseThrow(()->new NotDataFoundException("No se encontró ningún municipio con el id: "+idMunicipio));
        if(centroSaludRepository.existsByMunicipioAndNombreIgnoreCase(municipio,nombre)){
            throw new OperationException("Ya existe un centro de salud con el nombre: "+nombre+" en el municipio: "+municipio.getNombre());
        }
        return centroSaludRepository.save(CentroSalud.builder().nombre(nombre).zona(zona).direccion(direccion).telefono(telefono).latitud(latitud).longitud(longitud).municipio(municipio).build());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CentroSaludUsuarioDto> listarCentroSaludAsignados(String username) {
        List<CentroSaludUsuarioDto> list = this.centroSaludUsuarioRepository.listarCentrosSaludAsignados(username);
        list.addAll(this.centroSaludUsuarioRepository.listarCentrosSaludNoAsignados(username));
        return list;
    }

    @Override
    public CentroSalud update(Long centroSaludId, String nombre, String direccion, String zona, String telefono, Double latitud, Double longitud) {
        ValidationUtil.throwExceptionIfInvalidText("nombre",nombre,true,100);
        ValidationUtil.throwExceptionIfInvalidText("dirección",direccion,true,200);
        ValidationUtil.throwExceptionIfInvalidText("zona",zona,true,100);
        ValidationUtil.throwExceptionIfInvalidText("teléfono",telefono,true,9);
        if(centroSaludRepository.existsByIdIsNotAndNombreIgnoreCase(centroSaludId, nombre)){
            throw new OperationException("Ya existe un centro de salud con el nombre: "+nombre+" en el municipio");
        }
        CentroSalud centroSalud = this.centroSaludRepository.findById(centroSaludId).orElseThrow(()-> new NotDataFoundException("No se encontró ningún departamento con ID: "+centroSaludId));
        centroSalud.setNombre(nombre);
        centroSalud.setDireccion(direccion);
        centroSalud.setZona(zona);
        centroSalud.setTelefono(telefono);
        centroSalud.setLatitud(latitud);
        centroSalud.setLongitud(longitud);
        return this.centroSaludRepository.save(centroSalud);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void eliminar(Long centroSaludId) {
        this.centroSaludRepository.eliminar(centroSaludId);
    }

}
