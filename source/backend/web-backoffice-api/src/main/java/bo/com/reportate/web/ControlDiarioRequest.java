package bo.com.reportate.web;

import bo.com.reportate.model.Enfermedad;
import bo.com.reportate.model.Pais;
import bo.com.reportate.model.Sintoma;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @Created by :MC4
 * @Autor :Ricardo Laredo
 * @Email :rlaredo@mc4.com.bo
 * @Date :2020-04-02
 * @Project :reportate
 * @Package :bo.com.reportate.web
 * @Copyright :MC4
 */
@Getter @Setter @NoArgsConstructor
public class ControlDiarioRequest implements Serializable {
    private List<Pais> paises;
    private List<Enfermedad> enfermedadesBase;
    private List<Sintoma> sintomas;
}
