package bo.com.reportate.model.dto;

import bo.com.reportate.model.Municipio;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Created by :Reportate
 * @Autor :Ricardo Laredo
 * @Email :rllayus@gmail.com
 * @Date :2020-03-30
 * @Project :reportate
 * @Package :bo.com.reportate.model
 * @Copyright :Reportate
 */
@Setter @Getter @NoArgsConstructor
public class MunicipioUsuarioDto implements Serializable {
    private Long id;
    private String nombre;
    private Boolean asignado;
    private Long departamentoId;
    public MunicipioUsuarioDto(Municipio municipio){
        this.id = municipio.getId();
        this.nombre = municipio.getNombre();
        this.asignado = (municipio.getMunicipioUsuarios() !=null && !municipio.getMunicipioUsuarios().isEmpty());
        this.departamentoId = municipio.getDepartamento().getId();
    }

    public MunicipioUsuarioDto(Long id, String nombre, Boolean asignado, Long departamentoId) {
        this.id = id;
        this.nombre = nombre;
        this.asignado = asignado;
        this.departamentoId = departamentoId;
    }
}
