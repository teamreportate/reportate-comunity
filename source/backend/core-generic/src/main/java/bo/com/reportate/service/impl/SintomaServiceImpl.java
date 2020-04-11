package bo.com.reportate.service.impl;

import bo.com.reportate.exception.NotDataFoundException;
import bo.com.reportate.exception.OperationException;
import bo.com.reportate.model.Sintoma;
import bo.com.reportate.model.dto.SintomaDto;
import bo.com.reportate.model.enums.EstadoEnum;
import bo.com.reportate.repository.SintomaRepository;
import bo.com.reportate.service.SintomaService;
import bo.com.reportate.util.ValidationUtil;
import bo.com.reportate.utils.FormatUtil;
import org.jfree.util.Log;
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
public class SintomaServiceImpl implements SintomaService {
    @Autowired
    private SintomaRepository sintomaRepository;

    @Override
    public List<SintomaDto> listAll() {
        return this.sintomaRepository.findByEstadoOrderByNombreDesc(EstadoEnum.ACTIVO);
    }

    @Override
    public Sintoma save(SintomaDto sintomaDto) {
        ValidationUtil.throwExceptionIfInvalidText("nombre",sintomaDto.getNombre(),true,100);
        ValidationUtil.throwExceptionIfInvalidText("pregunta",sintomaDto.getPregunta(),true,500);
        ValidationUtil.throwExceptionIfInvalidText("ayuda",sintomaDto.getAyuda(),false,500);
        if(this.sintomaRepository.existsByNombreIgnoreCaseAndEstado(sintomaDto.getNombre(), EstadoEnum.ACTIVO)){
            throw new OperationException("Ya existe un síntoma con el nombre de "+sintomaDto.getNombre());
        }
        Sintoma sintoma = new Sintoma();
        sintoma.setNombre(sintomaDto.getNombre());
        sintoma.setPregunta(sintomaDto.getPregunta());
        sintoma.setControlDiario(sintomaDto.getControlDiario());
        sintoma.setAyuda(sintomaDto.getAyuda());
        return sintomaRepository.save(sintoma);
    }

    @Override
    public Sintoma findById(Long sintomaId) {
        return this.sintomaRepository.findById(sintomaId).orElseThrow(()->new NotDataFoundException("No se encontro ningún síntoma con ID: "+sintomaId));
    }

    @Override
    public Sintoma update(Long id, SintomaDto sintomaDto) {
        ValidationUtil.throwExceptionIfInvalidText("nombre",sintomaDto.getNombre(),true,100);
        ValidationUtil.throwExceptionIfInvalidText("pregunta",sintomaDto.getPregunta(),true,500);
        ValidationUtil.throwExceptionIfInvalidText("ayuda",sintomaDto.getAyuda(),false,500);
        if(this.sintomaRepository.existsByIdNotAndNombreIgnoreCaseAndEstado(id,sintomaDto.getNombre(), EstadoEnum.ACTIVO)){
            throw new OperationException("Ya existe un síntoma con el nombre "+sintomaDto.getNombre());
        }
        Sintoma sintoma = this.sintomaRepository.findById(id).orElseThrow(()-> new NotDataFoundException("No se encontró ningún síntoma con ID: "+id));
        sintoma.setNombre(sintomaDto.getNombre());
        sintoma.setPregunta(sintomaDto.getPregunta());
        sintoma.setControlDiario(sintomaDto.getControlDiario());
        sintoma.setAyuda(sintomaDto.getAyuda());

        return this.sintomaRepository.save(sintoma);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean eliminar(Long id) {
        this.sintomaRepository.eliminar(id);
        return true;
    }
}
