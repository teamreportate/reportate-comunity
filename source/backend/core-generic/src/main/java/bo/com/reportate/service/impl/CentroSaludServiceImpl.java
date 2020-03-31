package bo.com.reportate.service.impl;

import bo.com.reportate.model.CentroSalud;
import bo.com.reportate.exception.NotDataFoundException;
import bo.com.reportate.exception.OperationException;
import bo.com.reportate.model.Municipio;
import bo.com.reportate.model.dto.CentroSaludUsuarioDto;
import bo.com.reportate.repository.CentroSaludRepository;
import bo.com.reportate.repository.CentroSaludUsuarioRepository;
import bo.com.reportate.repository.MunicipioRepository;
import bo.com.reportate.service.CentroSaludService;
import bo.com.reportate.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    public List<CentroSalud> findAll() {
        return centroSaludRepository.findAll();
    }

    @Override
    public List<CentroSalud> findByMunicipio(Long idMunicipio) {
        return centroSaludRepository.findByMunicipioIdOrderByIdDesc(idMunicipio);
    }

    @Override
    public CentroSalud save(CentroSalud centroSalud) {
        ValidationUtil.throwExceptionIfInvalidText("nombre",centroSalud.getNombre(),true,100);
        ValidationUtil.throwExceptionIfInvalidText("direccion",centroSalud.getDireccion(),true,200);
        ValidationUtil.throwExceptionIfInvalidText("zona",centroSalud.getDireccion(),true,100);
        ValidationUtil.throwExceptionIfInvalidText("ciudad",centroSalud.getDireccion(),true,100);
        if(centroSaludRepository.existsByMunicipioAndNombreIgnoreCase(centroSalud.getMunicipio(),centroSalud.getNombre())){
            throw new OperationException("Ya existe un centro de salud con el nombre: "+centroSalud.getNombre()+" en el municipio: "+centroSalud.getMunicipio().getNombre());
        }
        return centroSaludRepository.save(centroSalud);
    }

    @Override
    public CentroSalud save(Long idMunicipio, String nombre, String direccion, String zona, String ciudad, Double latitud, Double longitud) {
        ValidationUtil.throwExceptionIfInvalidText("nombre",nombre,true,100);
        ValidationUtil.throwExceptionIfInvalidText("direccion",direccion,true,200);
        ValidationUtil.throwExceptionIfInvalidText("zona",zona,true,100);
        ValidationUtil.throwExceptionIfInvalidText("ciudad",ciudad,true,100);

        Municipio municipio = this.municipioRepository.findById(idMunicipio).orElseThrow(()->new NotDataFoundException("No se encontro ningún municipio con el id: "+idMunicipio));
        if(centroSaludRepository.existsByMunicipioAndNombreIgnoreCase(municipio,nombre)){
            throw new OperationException("Ya existe un centro de salud con el nombre: "+nombre+" en el municipio: "+municipio.getNombre());
        }
        return centroSaludRepository.save(CentroSalud.builder().nombre(nombre).zona(zona).direccion(direccion).ciudad(ciudad).latitud(latitud).longitud(longitud).municipio(municipio).build());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CentroSaludUsuarioDto> listarCentroSaludAsignados(String username) {
        return this.centroSaludUsuarioRepository.listarCentrosSaludAsignados(username);
    }

}
