package bo.com.reportate.service.impl;

import bo.com.reportate.model.Sintoma;
import bo.com.reportate.model.dto.SintomaDto;
import bo.com.reportate.repository.SintomaRepository;
import bo.com.reportate.service.SintomaService;
import bo.com.reportate.util.ValidationUtil;
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
//        ValidationUtil.throwExceptionIfInvalidText("nombre",sintomaDto.getNombre(),true,100);
//        // TODO: VALIDAR NOMBRE DEL PAIS
////        if(municipioRepository.existsByNombreIgnoreCaseAndDepartamento(municipio.getNombre(), municipio.getDepartamento())){
////            throw new OperationException("Ya existe un municipio con el nombre: "+municipio.getNombre());
////        }
//        Sintoma sintoma = new Sintoma();
//        sintoma.setNombre(sintoma.getNombre());
//        return sintomaRepository.save(sintoma);
        return null;
    }

    @Override
    public Sintoma findById(Long sintomaId) {
        return null;
    }

    @Override
    public Sintoma update(Long id, SintomaDto sintomaDto) {
        return null;
    }

    @Override
    public boolean cambiarEstado(Long id) {
        return false;
    }
}
