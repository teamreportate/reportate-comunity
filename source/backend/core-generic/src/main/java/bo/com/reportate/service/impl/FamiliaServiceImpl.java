package bo.com.reportate.service.impl;

import bo.com.reportate.exception.NotDataFoundException;
import bo.com.reportate.model.Departamento;
import bo.com.reportate.model.Familia;
import bo.com.reportate.model.MuUsuario;
import bo.com.reportate.model.Municipio;
import bo.com.reportate.model.dto.DepartamentoDto;
import bo.com.reportate.model.dto.FamiliaMovilResponseDto;
import bo.com.reportate.model.dto.MunicipioDto;
import bo.com.reportate.model.enums.EstadoEnum;
import bo.com.reportate.repository.DepartamentoRepository;
import bo.com.reportate.repository.FamiliaRepository;
import bo.com.reportate.repository.MunicipioRepository;
import bo.com.reportate.repository.UsuarioRepository;
import bo.com.reportate.service.FamiliaService;
import bo.com.reportate.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Created by :MC4
 * @Autor :Ricardo Laredo
 * @Email :rlaredo@mc4.com.bo
 * @Date :2020-04-01
 * @Project :reportate
 * @Package :bo.com.reportate.service.impl
 * @Copyright :MC4
 */
@Service
public class FamiliaServiceImpl implements FamiliaService {
    @Autowired private FamiliaRepository familiaRepository;
    @Autowired private MunicipioRepository municipioRepository;
    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private DepartamentoRepository departamentoRepository;

    @Override
    public void save(Familia familia) {
        this.familiaRepository.save(familia);
    }

    @Override
    public FamiliaMovilResponseDto save(Authentication  authentication, Long departamentoId, Long municipioId, String nombre, String telefono, String direccion, Double latitud, Double longitud, String ciudad, String zona) {
        ValidationUtil.throwExceptionIfInvalidText("nombre",nombre, true,100);
        ValidationUtil.throwExceptionIfInvalidText("teléfono",telefono,true,8);
        ValidationUtil.throwExceptionIfInvalidText("dirección",direccion,true,200);
        ValidationUtil.throwExceptionIfInvalidText("zona",ciudad,true,100);
        ValidationUtil.throwExceptionIfInvalidText("ciudad",ciudad,false,100);
        MuUsuario user = (MuUsuario) authentication.getPrincipal();
        Departamento departamento = this.departamentoRepository.findById(departamentoId).orElseThrow(()->new NotDataFoundException("No se encontró ningún departamento con id: "+departamentoId));
        Municipio municipio = null;
        if(municipioId > 0) {
            municipio = this.municipioRepository.findById(municipioId).orElseThrow(() -> new NotDataFoundException("No se encontró ningún municipio con id: " + municipioId));
        }
        MuUsuario usuario = this.usuarioRepository.findByEstadoAndUsername(EstadoEnum.ACTIVO, user.getUsername()).orElseThrow(()->new NotDataFoundException("No se encontró ningún usuario logueado"));

        Familia familia = this.familiaRepository.findFirstByUsuarioIdAndEstadoOrderByIdDesc(user.getId(), EstadoEnum.ACTIVO).orElse(new Familia());
        familia.setNombre(nombre);
        familia.setDireccion(direccion);
        familia.setTelefono(telefono);
        familia.setZona(zona);
        familia.setCiudad(ciudad);
        familia.setDepartamento(departamento);
        familia.setMunicipio(municipio);
        familia.setLatitud(latitud);
        familia.setUsuario(usuario);
        familia.setLongitud(longitud);
        this.familiaRepository.save(familia);

        FamiliaMovilResponseDto responseDto = new FamiliaMovilResponseDto();
        responseDto.setId(familia.getId());
        responseDto.setNombre(familia.getNombre());
        responseDto.setTelefono(familia.getTelefono());
        responseDto.setDireccion(familia.getDireccion());
        responseDto.setZona(familia.getZona());
        responseDto.setCiudad(familia.getCiudad());
        if(municipio!=null) {
            responseDto.setMunicipio(new MunicipioDto(municipio.getId(), municipio.getNombre()));
        }
        responseDto.setDepartamento(new DepartamentoDto(departamento.getId(), departamento.getNombre()));
        return responseDto;
    }

    @Override
    public FamiliaMovilResponseDto update( Long departamentoId, Long municipioId, Long familiaId, String nombre, String telefono, String direccion, String ciudad, String zona) {
        ValidationUtil.throwExceptionIfInvalidNumber("familiaId",familiaId,true,0L);
        ValidationUtil.throwExceptionIfInvalidText("nombre",nombre, true,100);
        ValidationUtil.throwExceptionIfInvalidText("teléfono",telefono,true,8);
        ValidationUtil.throwExceptionIfInvalidText("dirección",direccion,true,200);
        ValidationUtil.throwExceptionIfInvalidText("zona",ciudad,true,100);
        ValidationUtil.throwExceptionIfInvalidText("ciudad",ciudad,false,100);
        Familia familia = this.familiaRepository.findById(familiaId).orElseThrow(()->new NotDataFoundException("No se encontró ninguna familia para actualizar"));
        Departamento departamento = this.departamentoRepository.findById(departamentoId).orElseThrow(()->new NotDataFoundException("No se encontró ningún departamento con id: "+departamentoId));
        Municipio municipio = null;
        if(municipioId > 0) {
            municipio = this.municipioRepository.findById(municipioId).orElseThrow(() -> new NotDataFoundException("No se encontró ningún municipio con id: " + municipioId));
        }
        familia.setNombre(nombre);
        familia.setDireccion(direccion);
        familia.setZona(zona);
        familia.setTelefono(telefono);
        familia.setCiudad(ciudad);
        this.familiaRepository.save(familia);
        FamiliaMovilResponseDto responseDto = new FamiliaMovilResponseDto();
        responseDto.setId(familia.getId());
        responseDto.setNombre(familia.getNombre());
        responseDto.setTelefono(familia.getTelefono());
        responseDto.setDireccion(familia.getDireccion());
        responseDto.setZona(familia.getZona());
        responseDto.setCiudad(familia.getCiudad());
        if(municipio!=null) {
            responseDto.setMunicipio(new MunicipioDto(municipio.getId(), municipio.getNombre()));
        }
        responseDto.setDepartamento(new DepartamentoDto(departamento.getId(), departamento.getNombre()));
        return responseDto;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void delete(Long familiaId) {
        this.familiaRepository.eliminarFamilia(familiaId);
    }
}
