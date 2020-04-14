package bo.com.reportate.model.dto.response;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Data
public class ResumenDto implements Serializable{

	private static final long serialVersionUID = -1309913681813311507L;
	private Date nombreGrafico;
	private Long sospechoso;
	private Long negativo;
	private Long confirmado;
	private Long curado;
	private Long fallecido;
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ResumenDto other = (ResumenDto) obj;
		if (nombreGrafico == null) {
			if (other.nombreGrafico != null)
				return false;
		} else if (!nombreGrafico.equals(other.nombreGrafico))
			return false;
		return true;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nombreGrafico == null) ? 0 : nombreGrafico.hashCode());
		return result;
	}
	
	
	
}
