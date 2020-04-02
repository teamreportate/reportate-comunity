package bo.com.reportate.service.impl;

import bo.com.reportate.exception.NotDataFoundException;
import bo.com.reportate.model.Departamento;
import bo.com.reportate.model.Familia;
import bo.com.reportate.model.MuUsuario;
import bo.com.reportate.model.Municipio;
import bo.com.reportate.model.dto.FamiliaMovilResponseDto;
import bo.com.reportate.model.enums.EstadoEnum;
import bo.com.reportate.repository.DepartamentoRepository;
import bo.com.reportate.repository.FamiliaRepository;
import bo.com.reportate.repository.MunicipioRepository;
import bo.com.reportate.repository.UsuarioRepository;
import bo.com.reportate.service.FamiliaService;
import bo.com.reportate.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Principal;

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
    public FamiliaMovilResponseDto save(UserDetails user, Long departamentoId, Long municipioId, String nombre, String telefono, String direccion, Double latitud, Double longitud, String ciudad, String zona) {
        ValidationUtil.throwExceptionIfInvalidText("nombre",nombre, true,100);
        ValidationUtil.throwExceptionIfInvalidText("teléfono",telefono,true,8);
        ValidationUtil.throwExceptionIfInvalidText("dirección",direccion,true,200);
        ValidationUtil.throwExceptionIfInvalidText("zona",ciudad,true,100);
        ValidationUtil.throwExceptionIfInvalidText("ciudad",ciudad,false,100);

        Departamento departamento = this.departamentoRepository.findById(departamentoId).orElseThrow(()->new NotDataFoundException("No se encontró ningún departamento con id: "+departamentoId));
        Municipio municipio = null;
        if(municipioId > 0) {
            municipio = this.municipioRepository.findById(municipioId).orElseThrow(() -> new NotDataFoundException("No se encontró ningún municipio con id: " + municipioId));
        }
        MuUsuario usuario = this.usuarioRepository.findByEstadoAndUsername(EstadoEnum.ACTIVO, user.getUsername()).orElseThrow(()->new NotDataFoundException("No se encontró ningún usuario logueado"));
        Familia familia = this.familiaRepository.save(Familia.builder()
                .nombre(nombre)
                .direccion(direccion)
                .telefono(telefono)
                .latitud(latitud)
                .longitud(longitud)
                .ciudad(ciudad)
                .zona(zona)
                .municipio(municipio)
                .departamento(departamento)
                .usuario(usuario).build());
        return new FamiliaMovilResponseDto(familia);
    }
}
