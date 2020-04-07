package bo.com.reportate.model.dto.response;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @Data
public class NivelValoracionListDto implements Serializable{
	private List<NivelValoracionDto> datas;

	public NivelValoracionListDto(List<NivelValoracionDto> datas) {
		super();
		this.datas = datas;
	}
	
	
}
