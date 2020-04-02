package bo.com.reportate.model.dto;

import bo.com.reportate.model.Municipio;
import lombok.*;

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
@Setter @Getter @AllArgsConstructor @NoArgsConstructor
public class MunicipioDto implements Serializable {
    private Long id;
    private String nombre;
    public MunicipioDto(Municipio municipio){
        this.id = municipio.getId();
        this.nombre = municipio.getNombre();
    }
}
