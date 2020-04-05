package bo.com.reportate.model.dto.response;

import bo.com.reportate.model.Familia;
import bo.com.reportate.model.Sintoma;
import bo.com.reportate.model.dto.DepartamentoDto;
import bo.com.reportate.model.dto.MunicipioDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Created by :MC4
 * @Autor :Ricardo Laredo
 * @Email :rlaredo@mc4.com.bo
 * @Date :2020-04-01
 * @Project :reportate
 * @Package :bo.com.reportate.model
 * @Copyright :MC4
 */
@Getter @Setter @NoArgsConstructor
public class FamiliaResponse implements Serializable {
    private Long id;
    private String nombre;
    private String telefono;
    private String direccion;
    private String zona;
    private String ciudad;
    private DepartamentoResponse departamento;
    private MunicipioResponse municipio;
    private List<PacienteResponse> pacientes = new ArrayList<>();


    public FamiliaResponse(Familia familia){
        this.id = familia.getId();
        this.nombre = familia.getNombre();
        this.telefono = familia.getTelefono();
        this.direccion = familia.getDireccion();
        this.zona = familia.getZona();
        this.ciudad = familia.getCiudad();
        this.departamento = new DepartamentoResponse();
        BeanUtils.copyProperties(familia.getDepartamento(),this.departamento);
        this.municipio = new MunicipioResponse();
        if(familia.getMunicipio() != null){
            BeanUtils.copyProperties(familia.getMunicipio(),this.municipio);
        }
        if(familia.getPacientes() != null && !familia.getPacientes().isEmpty()){
            familia.getPacientes().forEach(paciente -> {
                assert paciente.getControlDiarios() != null;
                pacientes.add(new PacienteResponse(paciente.getId(),
                        paciente.getNombre(), paciente.getEdad(), paciente.getGenero(), paciente.getGestacion(),
                        paciente.getTiempoGestacion(), (paciente.getControlDiarios() != null && !paciente.getControlDiarios().isEmpty())));
            });
        }

    }

}
