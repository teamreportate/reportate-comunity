package bo.com.reportate.web;

import bo.com.reportate.model.Departamento;
import bo.com.reportate.model.dto.MunicipioDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Created by :MC4
 * @Autor :Ricardo Laredo
 * @Email :rlaredo@mc4.com.bo
 * @Date :2020-03-30
 * @Project :reportate
 * @Package :bo.com.reportate.model
 * @Copyright :MC4
 */
@Getter
@Setter
@NoArgsConstructor
public class DepartamentoRequestDto implements Serializable {
    private Long id;
    private String nombre;
    private Double latitud;
    private Double longitud;

}
