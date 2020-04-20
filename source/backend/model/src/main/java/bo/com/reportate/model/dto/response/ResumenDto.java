package bo.com.reportate.model.dto.response;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Data
public class ResumenDto implements Serializable{

	private static final long serialVersionUID = -1309913681813311507L;
	private Date nombreGrafico;
	private Long sospechoso;
	private Long descartado;
	private Long positivo;
	private Long recuperado;
	private Long deceso;
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
