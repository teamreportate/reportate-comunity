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
public class EnfermedadServiceImpl implements EnfermedadService {
    @Autowired
    private EnfermedadRepository enfermedadRepository;

    @Override
    public List<EnfermedadResponse> list() {
        return enfermedadRepository.listarEnfermedadesActivos();
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
        Enfermedad enfermedad = this.enfermedadRepository.findById(id).orElseThrow(()-> new NotDataFoundException("No se encontró ningún enfermedad con ID: "+id));
        enfermedad.setNombre(enfermedadDto.getNombre());
        enfermedad.setEnfermedadBase(enfermedadDto.getEnfermedadBase());
        enfermedad.setMensajeDiagnostico(enfermedadDto.getMensajeDiagnostico());

        return this.enfermedadRepository.save(enfermedad);
    }

    @Override
    public boolean cambiarEstado(Long id) {
        Enfermedad enfermedad = enfermedadRepository.getOne(id);
        if (enfermedad == null) {
            throw new OperationException(FormatUtil.noRegistrado("Enfermedad", id));
        }
        Log.info("Persistiendo el objeto");
        EstadoEnum estado = enfermedad.getEstado() == EstadoEnum.ACTIVO ? EstadoEnum.INACTIVO : EstadoEnum.ACTIVO;
        enfermedad.setEstado(estado);

        enfermedadRepository.save(enfermedad);
        return enfermedad.getEstado() == EstadoEnum.ACTIVO;
    }
}
