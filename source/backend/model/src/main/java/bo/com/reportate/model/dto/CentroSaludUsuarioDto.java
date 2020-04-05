package bo.com.reportate.model.dto;

import bo.com.reportate.model.CentroSalud;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@Getter @Setter @NoArgsConstructor
public class CentroSaludUsuarioDto implements Serializable {
    private Long id;
    private String nombre;
    private Boolean asignado;
    private Long municipioId;

    public CentroSaludUsuarioDto(CentroSalud centroSalud){
        this.id = centroSalud.getId();
        this.nombre = centroSalud.getNombre();
        this.asignado =(centroSalud.getCentroSaludUsuarios() != null && !centroSalud.getCentroSaludUsuarios().isEmpty());
        this.municipioId = centroSalud.getMunicipio().getId();
    }

    public CentroSaludUsuarioDto(Long id, String nombre, Boolean asignado, Long municipioId) {
        this.id = id;
        this.nombre = nombre;
        this.asignado = asignado;
        this.municipioId = municipioId;
    }
}
