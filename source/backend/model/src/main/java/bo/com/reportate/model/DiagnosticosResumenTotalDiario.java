package bo.com.reportate.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "DIAGNOSTICO_RESUMEN_TOTAL_DIARIO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "DIAGN_RESUMEN_TOTAL_ID_GENERATOR", sequenceName = "SEQ_DIAGN_RESUMEN_TOTAL_ID", allocationSize = 1)
public class DiagnosticosResumenTotalDiario extends AbstractAuditableEntity {
	private static final long serialVersionUID = -8678159184568578404L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DIAGN_RESUMEN_TOTAL_ID_GENERATOR")
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
	public DiagnosticosResumenTotalDiario(Enfermedad enfermedad, Departamento departamento, Municipio municipio,
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
