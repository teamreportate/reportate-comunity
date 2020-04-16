package bo.com.reportate.service.impl;

import bo.com.reportate.exception.NotDataFoundException;
import bo.com.reportate.exception.OperationException;
import bo.com.reportate.model.Enfermedad;
import bo.com.reportate.model.dto.EnfermedadDto;
import bo.com.reportate.model.dto.response.EnfermedadResponse;
import bo.com.reportate.model.enums.EstadoEnum;
import bo.com.reportate.repository.EnfermedadRepository;
import bo.com.reportate.service.EnfermedadService;
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
public class EnfermedadServiceImpl implements EnfermedadService {
    @Autowired
    private EnfermedadRepository enfermedadRepository;

    @Override
    public List<EnfermedadResponse> list() {
        return enfermedadRepository.listarEnfermedadesActivos();
    }

    @Override
    public List<EnfermedadResponse> listNoBase() {
        return this.enfermedadRepository.findByEnfermedadBaseFalseAndEstadoOrderByNombreAsc(EstadoEnum.ACTIVO);
    }

    @Override
    public List<EnfermedadResponse> listBase() {
        return this.enfermedadRepository.findByEnfermedadBaseTrueAndEstadoOrderByNombreAsc(EstadoEnum.ACTIVO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EnfermedadDto> listActivos() {
        return this.enfermedadRepository.findByEstadoOrderByNombreAsc(EstadoEnum.ACTIVO);
    }

    @Override
    public Enfermedad save(EnfermedadDto enfermedadDto) {
        ValidationUtil.throwExceptionIfInvalidText("nombre",enfermedadDto.getNombre(),true,100);
        ValidationUtil.throwExceptionIfInvalidText("diagnostico",enfermedadDto.getMensajeDiagnostico(),false,4000);
        if(this.enfermedadRepository.existsByNombreIgnoreCaseAndEstado(enfermedadDto.getNombre(), EstadoEnum.ACTIVO)){
            throw new OperationException("Ya existe una enfermedad con el nombre de "+enfermedadDto.getNombre());
        }
        Enfermedad enfermedad = new Enfermedad();
        enfermedad.setNombre(enfermedadDto.getNombre());
        enfermedad.setEnfermedadBase(enfermedadDto.getEnfermedadBase());
        enfermedad.setMensajeDiagnostico(enfermedadDto.getMensajeDiagnostico());
        return enfermedadRepository.save(enfermedad);
    }

    @Override
    public Enfermedad findById(Long enfermedadId) {
        return this.enfermedadRepository.findById(enfermedadId).orElseThrow(()->new NotDataFoundException("No se encontro ninguna enfermedad con ID: "+enfermedadId));
    }

    @Override
    public Enfermedad update(Long id, EnfermedadDto enfermedadDto) {
        ValidationUtil.throwExceptionIfInvalidText("nombre",enfermedadDto.getNombre(),true,100);
        ValidationUtil.throwExceptionIfInvalidText("diagnostico",enfermedadDto.getMensajeDiagnostico(),false,4000);
        if(this.enfermedadRepository.existsByIdNotAndNombreIgnoreCaseAndEstado(id,enfermedadDto.getNombre(), EstadoEnum.ACTIVO)){
            throw new OperationException("Ya existe una enfermedad con el nombre "+enfermedadDto.getNombre());
        }
        Enfermedad enfermedad = this.enfermedadRepository.findById(id).orElseThrow(()-> new NotDataFoundException("No se encontró ningún enfermedad con ID: "+id));
        enfermedad.setNombre(enfermedadDto.getNombre());
        enfermedad.setEnfermedadBase(enfermedadDto.getEnfermedadBase());
        enfermedad.setMensajeDiagnostico(enfermedadDto.getMensajeDiagnostico());

        return this.enfermedadRepository.save(enfermedad);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean eliminar(Long id) {
        this.enfermedadRepository.eliminar(id);
        return true;
    }
}
