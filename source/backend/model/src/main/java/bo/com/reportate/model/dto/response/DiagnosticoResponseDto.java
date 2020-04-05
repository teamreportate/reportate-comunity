package bo.com.reportate.model.dto.response;

import bo.com.reportate.model.Diagnostico;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Created by :MC4
 * @Autor :Ricardo Laredo
 * @Email :rlaredo@mc4.com.bo
 * @Date :2020-04-02
 * @Project :reportate
 * @Package :bo.com.reportate.model.dto.response
 * @Copyright :MC4
 */
@Getter @Setter @NoArgsConstructor @Data
public class DiagnosticoResponseDto implements Serializable {
    private Long id;
    private Date fechaRegistro;
    private String nombrePaciente;
    private String telefono;
    private String direccion;
    private String zona;
    private String ciudad;
    private String urlCoordenada;
    private String urlWhatsApp;
    private String departamento;
    private String municipio;
    private String clasificacion;
    private String enfermedad;
    private BigDecimal valoracion;

    public DiagnosticoResponseDto(Diagnostico diagnostico){
        this.id = diagnostico.getId();
        this.fechaRegistro = diagnostico.getCreatedDate();
        this.nombrePaciente = diagnostico.getControlDiario().getPaciente().getNombre();
        this.telefono = diagnostico.getControlDiario().getPaciente().getFamilia().getTelefono();
        this.direccion = diagnostico.getControlDiario().getPaciente().getFamilia().getDireccion();
        this.zona = diagnostico.getControlDiario().getPaciente().getFamilia().getZona();
        this.ciudad = diagnostico.getControlDiario().getPaciente().getFamilia().getCiudad();
        this.urlCoordenada = "https://maps.google.com/?q="+diagnostico.getControlDiario().getPaciente().getFamilia().getLatitud()+","+diagnostico.getControlDiario().getPaciente().getFamilia().getLongitud();
        if(telefono.length() == 8){
            this.urlWhatsApp =  "https://api.whatsapp.com/send?phone="+this.telefono+"&text=Buenos días.";
        }
        this.departamento = diagnostico.getControlDiario().getPaciente().getFamilia().getDepartamento().getNombre();
        if(diagnostico.getControlDiario().getPaciente().getFamilia().getMunicipio() != null){
            this.municipio = diagnostico.getControlDiario().getPaciente().getFamilia().getMunicipio().getNombre();
        }
        this.clasificacion = diagnostico.getEstadoDiagnostico().name();

        this.enfermedad=diagnostico.getEnfermedad().getNombre();
        this.valoracion=diagnostico.getResultadoValoracion();
    }
}
