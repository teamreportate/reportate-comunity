package bo.com.reportate.model.dto.response;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Data
public class TotalResponse implements Serializable{
	
	private static final long serialVersionUID = 8896814117952763756L;
	private Integer sospechosos;
	private Integer descartados;
	private Integer confirmados;
	private Integer recuperados;
	private Integer fallecidos;
	private Integer total;
	
}
