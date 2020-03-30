package bo.com.reportate.model.dto;

import bo.com.reportate.model.MuParametro;
import bo.com.reportate.model.enums.ParamTipoDato;
import bo.com.reportate.utils.StringUtil;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MuParametroDto implements Serializable {
    private Long id;
    private Long version;
    private Date createdDate;
    private Date lastModifiedDate;
    private Long createdBy;
    private Long lastModifiedBy;
    private Boolean estado;

    private String codigo;
    private String descripcion;
    private String valorCadena;
    private Boolean valorBooleano;
    private BigDecimal valorNumerico;
    private Date valorFecha;
    private ParamTipoDato tipoDato;
    private Boolean datosSensibles;
    private String valorCadenaLob;
    private MuGrupoParametroDto idMuGrupoParametro;

    public String validacionActualizacion() {
        if (StringUtil.isEmptyOrNull(this.descripcion)) return "Descripción";
        return null;
    }

    public MuParametroDto(MuParametro muParametro) {
        this.id = muParametro.getId();
        this.version = muParametro.getVersion();
        this.createdDate = muParametro.getCreatedDate();
        this.lastModifiedDate = muParametro.getLastModifiedDate();

        this.codigo = muParametro.getCodigo();
        this.descripcion = muParametro.getDescripcion();
        this.valorCadena = muParametro.getValorCadena();
        this.valorBooleano = muParametro.getValorBooleano();
        this.valorNumerico = muParametro.getValorNumerico();
        this.valorFecha = muParametro.getValorFecha();
        this.tipoDato = muParametro.getTipoDato();
        this.datosSensibles = muParametro.getDatoSensible();
        this.valorCadenaLob = muParametro.getValorLob();
        this.idMuGrupoParametro =   new MuGrupoParametroDto(muParametro.getIdMuGrupoParametro());
    }
}

