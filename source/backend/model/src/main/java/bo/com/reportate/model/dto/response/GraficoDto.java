package bo.com.reportate.model.dto.response;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @Data
public class GraficoDto implements Serializable{
	private String nombreGrafico;
	private Integer cantidadGrafico;
	private Integer cantidadMaximaGrafico;
}
