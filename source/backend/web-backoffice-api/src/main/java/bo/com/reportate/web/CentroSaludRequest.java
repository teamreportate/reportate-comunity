package bo.com.reportate.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Created by :MC4
 * @Autor :Ricardo Laredo
 * @Email :rlaredo@mc4.com.bo
 * @Date :2020-03-31
 * @Project :reportate
 * @Package :bo.com.reportate.web
 * @Copyright :MC4
 */
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class CentroSaludRequest implements Serializable {
    private Long id;
    private String nombre;
    private Double latitud;
    private Double longitud;
    private String direccion;
    private String zona;
    private String ciudad;
    private Long municipioId;
}
