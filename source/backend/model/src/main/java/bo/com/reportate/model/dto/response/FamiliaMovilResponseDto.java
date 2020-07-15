package bo.com.reportate.model.dto.response;

import bo.com.reportate.model.Familia;
import bo.com.reportate.model.dto.CentroSaludDto;
import bo.com.reportate.model.dto.DepartamentoDto;
import bo.com.reportate.model.dto.MunicipioDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

/**
 * @Created by :Reportate
 * @Autor :Ricardo Laredo
 * @Email :rllayus@gmail.com
 * @Date :2020-04-01
 * @Project :reportate
 * @Package :bo.com.reportate.model
 * @Copyright :Reportate
 */
@Getter @Setter @NoArgsConstructor
public class FamiliaMovilResponseDto implements Serializable {
    private Long id;
    private String nombre;
    private String telefono;
    private String direccion;
    private String zona;
    private DepartamentoDto departamento;
    private MunicipioDto municipio;
    private CentroSaludDto centroSalud;

    public FamiliaMovilResponseDto(Familia familia){
        this.id = familia.getId();
        this.nombre = familia.getNombre();
        this.telefono = familia.getTelefono();
        this.direccion = familia.getDireccion();
        this.zona = familia.getZona();
        this.departamento = new DepartamentoDto();
        BeanUtils.copyProperties(familia.getDepartamento(),this.departamento);
        this.municipio = new MunicipioDto();
        if(familia.getMunicipio() != null){
            BeanUtils.copyProperties(familia.getMunicipio(),this.municipio);
        }
        this.centroSalud = new CentroSaludDto();
        if(familia.getCentroSalud() != null){
            BeanUtils.copyProperties(familia.getCentroSalud(),this.centroSalud);
        }

    }

}
