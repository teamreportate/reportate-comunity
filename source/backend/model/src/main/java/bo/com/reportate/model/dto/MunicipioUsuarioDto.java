package bo.com.reportate.model.dto;

import bo.com.reportate.model.Municipio;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Created by :MC4
 * @Autor :Ricardo Laredo
 * @Email :rlaredo@mc4.com.bo
 * @Date :2020-03-30
 * @Project :reportate
 * @Package :bo.com.reportate.model
 * @Copyright :MC4
 */
@Setter @Getter
public class MunicipioUsuarioDto implements Serializable {
    private Long id;
    private String nombre;
    private Double latitud;
    private Double longitud;
    private Boolean asignado;
    public MunicipioUsuarioDto(Municipio municipio){
        this.id = municipio.getId();
        this.nombre = municipio.getNombre();
        this.latitud = municipio.getLatitud();
        this.longitud = municipio.getLongitud();
        this.asignado = (municipio.getMunicipioUsuarios() !=null && !municipio.getMunicipioUsuarios().isEmpty());
    }
}
