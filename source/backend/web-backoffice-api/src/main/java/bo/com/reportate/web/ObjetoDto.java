package bo.com.reportate.web;

import bo.com.reportate.model.dto.CentroSaludUsuarioDto;
import bo.com.reportate.model.dto.DepartamentoUsuarioDto;
import bo.com.reportate.model.dto.MunicipioUsuarioDto;
import lombok.AllArgsConstructor;
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
 * @Date :2020-04-05
 * @Project :reportate
 * @Package :bo.com.reportate.web
 * @Copyright :Reportate
 */
@Setter @Getter @NoArgsConstructor @AllArgsConstructor
public class ObjetoDto implements Serializable {
    private List<DepartamentoUsuarioDto> departamentos = new ArrayList<>();
    private List<MunicipioUsuarioDto> municipios = new ArrayList<>();
    private List<CentroSaludUsuarioDto> centrosSalud = new ArrayList<>();
}
