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
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
public class PaisServiceImpl implements PaisService {
    @Autowired
    private PaisRepository paisRepository;

    @Override
    @Transactional(readOnly = true)
    public List<PaisDto> listAll() {
        List<PaisDto> paisesDto = new ArrayList<>();
        List<Pais> paises = this.paisRepository.listAllActivos();

        for (Pais pais: paises) {
            PaisDto paisDto = new PaisDto(pais);
            paisesDto.add(paisDto);
        }
        return paisesDto;
    }

    @Override
    public Pais save(PaisDto paisDto) {
        ValidationUtil.throwExceptionIfInvalidText("nombre",paisDto.getNombre(),true,100);
        // TODO: VALIDAR NOMBRE DEL PAIS
//        if(municipioRepository.existsByNombreIgnoreCaseAndDepartamento(municipio.getNombre(), municipio.getDepartamento())){
//            throw new OperationException("Ya existe un municipio con el nombre: "+municipio.getNombre());
//        }
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
        // TODO: VALIDAR NOMBRE PAIS
//        if(municipioRepository.existsByIdIsNotAndNombreIgnoreCase(municipioId, nombre)){
//            throw new OperationException("Ya existe un municipio con el nombre: "+nombre);
//        }
        Pais pais = this.paisRepository.findById(id).orElseThrow(()-> new NotDataFoundException("No se encontró ningún Pais con ID: "+id));
        pais.setNombre(paisDto.getNombre());

        return this.paisRepository.save(pais);
    }

    @Override
    public boolean cambiarEstado(Long id) {
        Log.info("Validaciones");
        Pais pais = paisRepository.getOne(id);
        if (pais == null) {
            throw new OperationException(FormatUtil.noRegistrado("País", id));
        }
        Log.info("Persistiendo el objeto");
        EstadoEnum estado = pais.getEstado() == EstadoEnum.ACTIVO ? EstadoEnum.INACTIVO : EstadoEnum.ACTIVO;
        pais.setEstado(estado);

        paisRepository.save(pais);
        return pais.getEstado() == EstadoEnum.ACTIVO;
    }
}
