package bo.com.reportate.model.dto.response;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
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
}
