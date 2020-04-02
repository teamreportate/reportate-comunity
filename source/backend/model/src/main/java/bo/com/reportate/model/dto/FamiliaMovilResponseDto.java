package bo.com.reportate.model.dto;

import bo.com.reportate.model.Familia;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

/**
 * @Created by :MC4
 * @Autor :Ricardo Laredo
 * @Email :rlaredo@mc4.com.bo
 * @Date :2020-04-01
 * @Project :reportate
 * @Package :bo.com.reportate.model
 * @Copyright :MC4
 */
public class FamiliaMovilResponseDto implements Serializable {
    private Long id;
    private String nombre;
    private String telefono;
    private String direccion;
    private String zona;
    private String ciudad;
    private DepartamentoDto departamento;
    private MunicipioDto municipio;

    public FamiliaMovilResponseDto(Familia familia){
        this.id = familia.getId();
        this.nombre = familia.getNombre();
        this.telefono = familia.getTelefono();
        this.direccion = familia.getDireccion();
        this.zona = familia.getZona();
        this.ciudad = familia.getCiudad();
        this.departamento = new DepartamentoDto();
        BeanUtils.copyProperties(familia.getDepartamento(),this.departamento);
        this.municipio = new MunicipioDto();
        if(familia.getMunicipio() != null){
            BeanUtils.copyProperties(familia.getMunicipio(),this.municipio);
        }

    }

}
