package bo.com.reportate.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Created by :MC4
 * @Autor :Ricardo Laredo
 * @Email :rlaredo@mc4.com.bo
 * @Date :2020-04-01
 * @Project :reportate
 * @Package :bo.com.reportate.web
 * @Copyright :MC4
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class FamiliaRequest implements Serializable {
    private Long id;
    @NotNull
    private String nombre;
    @NotNull
    private String telefono;
    @NotNull
    private Double latitud;
    @NotNull
    private Double longitud;
    @NotNull
    private String direccion;
    @NotNull
    private String zona;

    @NotNull
    private Long departamentoId;
    @NotNull
    private Long municipioId;
    @NotNull
    private Long centroSaludId;

}
