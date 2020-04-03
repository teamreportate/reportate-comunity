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
        List<SintomaDto> sintomasDtos = new ArrayList<>();
        List<Sintoma> sintomas = this.sintomaRepository.listAll();

        for (Sintoma sintoma: sintomas) {
            SintomaDto sintomaDto = new SintomaDto(sintoma);
            sintomasDtos.add(sintomaDto);
        }
        return sintomasDtos;
    }

    @Override
    public Sintoma save(SintomaDto sintomaDto) {
        ValidationUtil.throwExceptionIfInvalidText("nombre",sintomaDto.getNombre(),true,100);
        ValidationUtil.throwExceptionIfInvalidText("pregunta",sintomaDto.getPregunta(),true,500);
        ValidationUtil.throwExceptionIfInvalidText("ayuda",sintomaDto.getAyuda(),false,500);

        // TODO: VALIDAR NOMBRE DEL SINTOMA
//        if(municipioRepository.existsByNombreIgnoreCaseAndDepartamento(municipio.getNombre(), municipio.getDepartamento())){
//            throw new OperationException("Ya existe un municipio con el nombre: "+municipio.getNombre());
//        }
        Sintoma sintoma = new Sintoma();
        sintoma.setNombre(sintomaDto.getNombre());
        sintoma.setPregunta(sintomaDto.getPregunta());
        sintoma.setControlDiario(sintomaDto.getControlDiario());
        sintoma.setAyuda(sintomaDto.getAyuda());
        return sintomaRepository.save(sintoma);
    }

    @Override
    public Sintoma findById(Long sintomaId) {
        return this.sintomaRepository.findById(sintomaId).orElseThrow(()->new NotDataFoundException("No se encontro ningún sintoma con ID: "+sintomaId));
    }

    @Override
    public Sintoma update(Long id, SintomaDto sintomaDto) {
        ValidationUtil.throwExceptionIfInvalidText("nombre",sintomaDto.getNombre(),true,100);
        ValidationUtil.throwExceptionIfInvalidText("pregunta",sintomaDto.getPregunta(),true,500);
        ValidationUtil.throwExceptionIfInvalidText("ayuda",sintomaDto.getAyuda(),false,500);
        // TODO: VALIDAR NOMBRE SINTOMA
//        if(municipioRepository.existsByIdIsNotAndNombreIgnoreCase(municipioId, nombre)){
//            throw new OperationException("Ya existe un municipio con el nombre: "+nombre);
//        }
        Sintoma sintoma = this.sintomaRepository.findById(id).orElseThrow(()-> new NotDataFoundException("No se encontró ningún sintoma con ID: "+id));
        sintoma.setNombre(sintomaDto.getNombre());
        sintoma.setPregunta(sintomaDto.getPregunta());
        sintoma.setControlDiario(sintomaDto.getControlDiario());
        sintoma.setAyuda(sintomaDto.getAyuda());

        return this.sintomaRepository.save(sintoma);
    }

    @Override
    public boolean cambiarEstado(Long id) {
        Sintoma sintoma = sintomaRepository.getOne(id);
        if (sintoma == null) {
            throw new OperationException(FormatUtil.noRegistrado("Sintoma", id));
        }
        Log.info("Persistiendo el objeto");
        EstadoEnum estado = sintoma.getEstado() == EstadoEnum.ACTIVO ? EstadoEnum.INACTIVO : EstadoEnum.ACTIVO;
        sintoma.setEstado(estado);

        sintomaRepository.save(sintoma);
        return sintoma.getEstado() == EstadoEnum.ACTIVO;
    }
}
