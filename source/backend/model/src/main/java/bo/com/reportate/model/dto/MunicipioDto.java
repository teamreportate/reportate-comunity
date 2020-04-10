package bo.com.reportate.model.dto;

import bo.com.reportate.model.Municipio;
import bo.com.reportate.model.enums.EstadoEnum;
import lombok.*;

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
@Setter @Getter @AllArgsConstructor @NoArgsConstructor
public class MunicipioDto implements Serializable {
    private Long id;
    private String nombre;
    private Double latitud;
    private Double longitud;
    private EstadoEnum estado;
    private String telefono;
    private List<CentroSaludDto> centroSaluds = new ArrayList<>();
    public MunicipioDto(Municipio municipio){
        this.id = municipio.getId();
        this.nombre = municipio.getNombre();
        this.latitud = municipio.getLatitud();
        this.longitud = municipio.getLongitud();
        this.estado = municipio.getEstado();
        this.telefono = municipio.getTelefono();
        municipio.getCentroSaluds().forEach( centroSalud -> {
            if(centroSalud.getEstado().equals(EstadoEnum.ACTIVO)) {
                centroSaluds.add(new CentroSaludDto(centroSalud.getId(), centroSalud.getNombre()));
            }
        });
    }

    public MunicipioDto(Long id, String nombre){
        this.id = id;
        this.nombre = nombre;
    }
}
