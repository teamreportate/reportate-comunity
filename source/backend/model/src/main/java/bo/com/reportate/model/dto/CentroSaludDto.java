package bo.com.reportate.model.dto;

import bo.com.reportate.model.CentroSalud;
import bo.com.reportate.model.enums.EstadoEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Created by :MC4
 * @Autor :Ricardo Laredo
 * @Email :rlaredo@mc4.com.bo
 * @Date :2020-03-30
 * @Project :reportate
 * @Package :bo.com.reportate.model
 * @Copyright :MC4
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CentroSaludDto implements Serializable {
    private Long id;
    private String nombre;
    private String direccion;
    private String zona;
    private String ciudad;
    private Double latitud;
    private Double longitud;
    private EstadoEnum estado;


    public CentroSaludDto(CentroSalud centroSalud){
        this.id = centroSalud.getId();
        this.nombre = centroSalud.getNombre();
        this.direccion = centroSalud.getDireccion();
        this.zona = centroSalud.getZona();
        this.ciudad = centroSalud.getCiudad();
        this.estado = centroSalud.getEstado();
        this.longitud = centroSalud.getLongitud();
        this.latitud = centroSalud.getLatitud();
    }
}
