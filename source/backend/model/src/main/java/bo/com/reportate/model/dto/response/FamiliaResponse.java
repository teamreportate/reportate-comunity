package bo.com.reportate.model.dto.response;

import bo.com.reportate.model.Departamento;
import bo.com.reportate.model.Familia;
import bo.com.reportate.model.Municipio;
import bo.com.reportate.model.Paciente;
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
    private DepartamentoResponse departamento;
    private MunicipioResponse municipio;
    private List<PacienteResponse> pacientes = new ArrayList<>();


    public FamiliaResponse(Familia familia, Departamento departamento, Municipio municipio){
        this.id = familia.getId();
        this.nombre = familia.getNombre();
        this.telefono = familia.getTelefono();
        this.direccion = familia.getDireccion();
        this.zona = familia.getZona();

        this.departamento = new DepartamentoResponse();
        this.departamento.setId(departamento.getId());
        this.departamento.setNombre(departamento.getNombre());
        this.departamento.setTelefono(departamento.getTelefono());

        this.municipio = new MunicipioResponse();
        this.municipio.setId(municipio.getId());
        this.municipio.setNombre(municipio.getNombre());
        this.municipio.setTelefono(municipio.getTelefono());
        familia.getPacientes().forEach(paciente -> {
            assert paciente.getControlDiarios() != null;
            pacientes.add(new PacienteResponse(paciente.getId(),
                    paciente.getNombre(), paciente.getEdad(), paciente.getGenero(), paciente.getGestacion(),
                    paciente.getTiempoGestacion(), (paciente.getControlDiarios() != null && !paciente.getControlDiarios().isEmpty()), paciente.getOcupacion()));
        });

    }
    public FamiliaResponse(Familia familia){
        this.id = familia.getId();
        this.nombre = familia.getNombre();
        this.telefono = familia.getTelefono();
        this.direccion = familia.getDireccion();
        this.zona = familia.getZona();
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
                        paciente.getTiempoGestacion(), (paciente.getControlDiarios() != null && !paciente.getControlDiarios().isEmpty()), paciente.getOcupacion()));
            });
        }

    }

}
