package bo.com.reportate.model.dto.response;

import bo.com.reportate.model.Pais;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Created by :Reportate
 * @Autor :Ricardo Laredo
 * @Email :rllayus@gmail.com
 * @Date :2020-04-02
 * @Project :reportate
 * @Package :bo.com.reportate.model.dto.request
 * @Copyright :Reportate
 */
@Getter @Setter @NoArgsConstructor
public class PaisResponse implements Serializable {
    private Long id;
    private String nombre;
    public PaisResponse(Pais pais){
        this.id = pais.getId();
        this.nombre = pais.getNombre();
    }
}
