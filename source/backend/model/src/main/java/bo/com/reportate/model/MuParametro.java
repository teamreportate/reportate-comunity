package bo.com.reportate.model;

import bo.com.reportate.model.enums.ParamTipoDato;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


@Entity
@Table(name = "MU_PARAMETRO")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SequenceGenerator(name = "MU_PARAMETRO_ID_GENERATOR", sequenceName = "SEQ_MU_PARAMETRO_ID", allocationSize = 1)
public class MuParametro extends AbstractAuditableEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MU_PARAMETRO_ID_GENERATOR")
    @Column(name = "ID")
    private Long id;
    @Column(name = "CODIGO", nullable = false)
    private String codigo;
    @Column(name = "DESCRIPCION", nullable = false)
    private String descripcion;
    @Column(name = "VALOR_CADENA")
    private String valorCadena;
    @Column(name = "VALOR_BOOLEANO")
    private Boolean valorBooleano;
    @Column(name = "VALOR_NUMERICO")
    private BigDecimal valorNumerico;
    @Column(name = "VALOR_FECHA")
    private Date valorFecha;
    @Column(name = "TIPO_DATO", nullable = false)
    @Enumerated(EnumType.STRING)
    private ParamTipoDato tipoDato;
    @Column(name = "DATO_SENSICBLE", nullable = false)
    private Boolean datoSensible;
    @Lob
    @Type(type = "text")
    @Column(name = "VALOR_LOB")
    private String valorLob;

    @JoinColumn(name = "ID_MU_GRUPO_PARAMETRO", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private MuGrupoParametro idMuGrupoParametro;

    public MuParametro(String codigo, String valor) {
        this.codigo = codigo;
        this.valorCadena = valor;
        this.tipoDato = ParamTipoDato.CADENA;
    }

    public MuParametro(String codigo, Boolean valorBooleano) {
        this.codigo = codigo;
        this.valorBooleano = valorBooleano;
        this.tipoDato = ParamTipoDato.BOOLEANO;
    }

    public MuParametro(String codigo, BigDecimal bigDecimal) {
        this.codigo = codigo;
        this.valorNumerico = bigDecimal;
        this.tipoDato = ParamTipoDato.NUMERICO;
    }

    public MuParametro(String codigo, Date valorFecha) {
        this.codigo = codigo;
        this.valorFecha = valorFecha;
        this.tipoDato = ParamTipoDato.FECHA;
    }

    public MuParametro(String codigo) {
        this.codigo = codigo;
        this.tipoDato = ParamTipoDato.UNDEFINID;
    }

    public MuParametro(String codigo, String descripcion, String valorCadena, Boolean valorBooleano, BigDecimal valorNumerico, Date valorFecha, ParamTipoDato tipoDato, Boolean datoSensible, String valorLob, MuGrupoParametro muGrupoParametro) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.valorCadena = valorCadena;
        this.valorBooleano = valorBooleano;
        this.valorNumerico = valorNumerico;
        this.valorFecha = valorFecha;
        this.tipoDato = tipoDato;
        this.datoSensible = datoSensible;
        this.valorLob = valorLob;
        this.idMuGrupoParametro = muGrupoParametro;
    }
}
