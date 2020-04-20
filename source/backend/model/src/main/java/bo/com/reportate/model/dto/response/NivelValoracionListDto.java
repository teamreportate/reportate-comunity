package bo.com.reportate.model.dto.response;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter @Setter @NoArgsConstructor @Data
public class NivelValoracionListDto implements Serializable{
	private List<NivelValoracionDto> datas;

	public NivelValoracionListDto(List<NivelValoracionDto> datas) {
		super();
		this.datas = datas;
	}
	
	
}
