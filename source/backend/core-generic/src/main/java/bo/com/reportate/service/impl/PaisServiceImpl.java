package bo.com.reportate.service.impl;

import bo.com.reportate.exception.NotDataFoundException;
import bo.com.reportate.exception.OperationException;

import bo.com.reportate.model.Pais;
import bo.com.reportate.model.dto.PaisDto;
import bo.com.reportate.model.enums.EstadoEnum;
import bo.com.reportate.repository.PaisRepository;
import bo.com.reportate.service.PaisService;
import bo.com.reportate.util.ValidationUtil;
import bo.com.reportate.utils.FormatUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
@Slf4j
public class PaisServiceImpl implements PaisService {
    @Autowired
    private PaisRepository paisRepository;

    @Override
    @Transactional(readOnly = true)
    public List<PaisDto> listAll() {
        return this.paisRepository.findByEstadoOrderByNombreAsc(EstadoEnum.ACTIVO);
    }

    @Override
    public Pais save(PaisDto paisDto) {
        ValidationUtil.throwExceptionIfInvalidText("nombre",paisDto.getNombre(),true,100);
        if(this.paisRepository.existsByNombreIgnoreCaseAndEstado(paisDto.getNombre(), EstadoEnum.ACTIVO)){
            throw new OperationException("Ya existe un país con el nombre de "+paisDto.getNombre());
        }
        Pais pais = new Pais();
        pais.setNombre(paisDto.getNombre());
        return paisRepository.save(pais);
    }

    @Override
    public Pais findById(Long paisId) {
        return this.paisRepository.findById(paisId).orElseThrow(()->new NotDataFoundException("No se encontro ningún país con ID: "+paisId));
    }

    @Override
    public Pais update(Long id, PaisDto paisDto) {
        ValidationUtil.throwExceptionIfInvalidText("nombre",paisDto.getNombre(),true,100);

        if(this.paisRepository.existsByIdNotAndNombreIgnoreCaseAndEstado(id,paisDto.getNombre(), EstadoEnum.ACTIVO)){
            throw new OperationException("Ya existe un país con el nombre "+paisDto.getNombre());
        }

        Pais pais = this.paisRepository.findById(id).orElseThrow(()-> new NotDataFoundException("No se encontró ningún Pais con ID: "+id));
        pais.setNombre(paisDto.getNombre());

        return this.paisRepository.save(pais);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean eliminar(Long id) {
        this.paisRepository.eliminar(id);
        return true;
    }
}
