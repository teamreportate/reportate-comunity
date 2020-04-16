package bo.com.reportate.model.dto.response;

import bo.com.reportate.utils.DateUtil;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter @Setter @NoArgsConstructor @Data
public class NivelValoracionDto implements Serializable{
	private String registrado;
	private Long alto;
	private Long medio;
	private Long bajo;
	
	public NivelValoracionDto(Date registrado, Long alto, Long medio, Long bajo) {
		super();
		this.registrado = DateUtil.toString(DateUtil.FORMAT_DATE,registrado);
		this.alto = alto;
		this.medio = medio;
		this.bajo = bajo;
	}

	public NivelValoracionDto(String registrado, Long alto, Long medio, Long bajo) {
		super();
		this.registrado = registrado;
		this.alto = alto;
		this.medio = medio;
		this.bajo = bajo;
	}
	
	
}
