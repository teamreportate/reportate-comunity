package bo.com.reportate.model.dto;

import bo.com.reportate.model.CentroSalud;
import bo.com.reportate.model.enums.EstadoEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Created by :Reportate
 * @Autor :Ricardo Laredo
 * @Email :rllayus@gmail.com
 * @Date :2020-03-30
 * @Project :reportate
 * @Package :bo.com.reportate.model
 * @Copyright :Reportate
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CentroSaludDto implements Serializable {
    private Long id;
    private String nombre;
    private String direccion;
    private String zona;
    private String telefono;
    private Double latitud;
    private Double longitud;
    private EstadoEnum estado;


    public CentroSaludDto(CentroSalud centroSalud){
        this.id = centroSalud.getId();
        this.nombre = centroSalud.getNombre();
        this.direccion = centroSalud.getDireccion();
        this.zona = centroSalud.getZona();
        this.telefono = centroSalud.getTelefono();
        this.estado = centroSalud.getEstado();
        this.longitud = centroSalud.getLongitud();
        this.latitud = centroSalud.getLatitud();
    }
    public CentroSaludDto(Long id, String nombre){
        this.id = id;
        this.nombre = nombre;
    }
}
