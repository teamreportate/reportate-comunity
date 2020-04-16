package bo.com.reportate.model.dto.response;

import lombok.*;

import java.io.Serializable;

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
