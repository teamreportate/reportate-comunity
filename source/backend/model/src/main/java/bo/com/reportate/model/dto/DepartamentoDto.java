package bo.com.reportate.model.dto;

import bo.com.reportate.model.Departamento;
import bo.com.reportate.model.enums.EstadoEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Created by :Reportate
 * @Autor :Ricardo Laredo
 * @Email :rllayus@gmail.com
 * @Date :2020-03-30
 * @Project :reportate
 * @Package :bo.com.reportate.model
 * @Copyright :Reportate
 */
@Getter
@Setter
@NoArgsConstructor
public class DepartamentoDto implements Serializable {
    private Long id;
    private String nombre;
    private Double latitud;
    private Double longitud;
    private EstadoEnum estado;
    private String telefono;
    private List<MunicipioDto> municipios = new ArrayList<>();

    public DepartamentoDto(Departamento departamento){
        this.id = departamento.getId();
        this.nombre = departamento.getNombre();
        this.latitud = departamento.getLatitud();
        this.longitud = departamento.getLongitud();
        this.estado = departamento.getEstado();
        this.telefono = departamento.getTelefono();

        departamento.getMunicipios().forEach( municipio -> {
            if(municipio.getEstado().equals(EstadoEnum.ACTIVO)) {
                municipios.add(new MunicipioDto(municipio));
            }
        });

    }
    public DepartamentoDto(Long id, String nombre){
        this.id = id;
        this.nombre = nombre;
    }

}
