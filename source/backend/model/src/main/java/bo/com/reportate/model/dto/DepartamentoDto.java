package bo.com.reportate.model.dto;

import bo.com.reportate.model.Departamento;
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
public class DepartamentoDto implements Serializable {
    private Long id;
    private String nombre;
    private Double latitud;
    private Double longitud;
    private List<MunicipioDto> municipios = new ArrayList<>();

    public DepartamentoDto(Departamento departamento){
        this.id = departamento.getId();
        this.nombre = departamento.getNombre();
        this.latitud = departamento.getLatitud();
        this.longitud = departamento.getLongitud();
        departamento.getMunicipios().forEach(municipio -> municipios.add(new MunicipioDto(municipio)) );

    }
    public DepartamentoDto(Long id, String nombre){
        this.id = id;
        this.nombre = nombre;
    }

}
