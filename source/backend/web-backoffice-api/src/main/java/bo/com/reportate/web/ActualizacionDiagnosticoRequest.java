package bo.com.reportate.web;

import bo.com.reportate.model.enums.EstadoDiagnosticoEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Created by :Reportate
 * @Autor :Ricardo Laredo
 * @Email :rllayus@gmail.com
 * @Date :2020-04-11
 * @Project :reportate
 * @Package :bo.com.reportate.web
 * @Copyright :Reportate
 */
@Getter @Setter @NoArgsConstructor
public class ActualizacionDiagnosticoRequest implements Serializable {
    private EstadoDiagnosticoEnum  estadoDiagnostico;
    private String observacion;
}
