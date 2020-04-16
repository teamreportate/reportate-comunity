package bo.com.reportate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "DIAGNOSTICO_RESUMEN_DIARIO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "DIAGN_RESUMEN_ID_GENERATOR", sequenceName = "SEQ_DIAGN_RESUMEN_ID", allocationSize = 1)
public class DiagnosticosResumenDiario extends AbstractAuditableEntity {
	private static final long serialVersionUID = -8678159184568578404L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DIAGN_RESUMEN_ID_GENERATOR")
	@Column(name = "ID")
	private Long id;

	@JsonIgnore
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_ENFERMEDAD", referencedColumnName = "ID")
	private Enfermedad enfermedad;

	@JsonIgnore
	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_DEPARTAMENTO", referencedColumnName = "ID")
	private Departamento departamento;

	@JsonIgnore
	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_MUNICIPIO", referencedColumnName = "ID")
	private Municipio municipio;

	@JsonIgnore
	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CENTRO_SALUD", referencedColumnName = "ID")
	private CentroSalud centroSalud;

	@Column(name = "SOSPECHOSO", nullable = false)
	private Long sospechoso;
	@Column(name = "NEGATIVO", nullable = false)
	private Long negativo;
	@Column(name = "CONFIRMADO", nullable = false)
	private Long confirmado;
	@Column(name = "CURADO", nullable = false)
	private Long curado;
	@Column(name = "FALLECIDO", nullable = false)
	private Long fallecido;
	public DiagnosticosResumenDiario(Enfermedad enfermedad, Departamento departamento, Municipio municipio,
			CentroSalud centroSalud, Long sospechoso, Long negativo, Long confirmado, Long curado, Long fallecido) {
		super();
		this.enfermedad = enfermedad;
		this.departamento = departamento;
		this.municipio = municipio;
		this.centroSalud = centroSalud;
		this.sospechoso = sospechoso;
		this.negativo = negativo;
		this.confirmado = confirmado;
		this.curado = curado;
		this.fallecido = fallecido;
	}
	
	

}
