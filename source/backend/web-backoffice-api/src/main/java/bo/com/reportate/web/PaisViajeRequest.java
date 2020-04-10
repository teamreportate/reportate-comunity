package bo.com.reportate.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @Created by :MC4
 * @Autor :Ricardo Laredo
 * @Email :rlaredo@mc4.com.bo
 * @Date :2020-04-06
 * @Project :reportate
 * @Package :bo.com.reportate.web
 * @Copyright :MC4
 */
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class PaisViajeRequest implements Serializable {
    private Long paisId;
    private Date fechaLlegada;
    private Date fechaSalida;
    private String ciudad;
}
