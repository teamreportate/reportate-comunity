package bo.com.reportate.model.dto.response;

import bo.com.reportate.model.Familia;
import bo.com.reportate.model.dto.DiagnosticoDto;
import bo.com.reportate.model.dto.PacienteDto;
import bo.com.reportate.model.dto.PaisDto;
import bo.com.reportate.model.dto.PaisVisitadoDto;
import bo.com.reportate.model.dto.request.EnfermedadRequest;
import bo.com.reportate.model.dto.request.PaisRequest;
import bo.com.reportate.model.dto.request.SintomaRequest;
import bo.com.reportate.model.enums.GeneroEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @Created by :MC4
 * @Autor :Diana Mejia
 * @Date :2020-04-04
 * @Project :reportate
 * @Package :bo.com.reportate.web
 * @Copyright :MC4
 */
@Getter @Setter @NoArgsConstructor
public class FichaEpidemiologicaResponse implements Serializable {
    private Long id;
    private String nombre;
    private Integer edad;
    private GeneroEnum genero;
    private String telefono;
    private String cedulaIdentidad;
    private String departamento;
    private String municipio;
    private String zona;
    private String direccion;
    private String Ubicacion;

    private List<PaisVisitadoDto> paisesVisitados;
    private List<EnfermedadResponse> enfermedadesBase;
    private List<DiagnosticoDto> diagnosticos;
    private List<PacienteDto> contactos;

}
