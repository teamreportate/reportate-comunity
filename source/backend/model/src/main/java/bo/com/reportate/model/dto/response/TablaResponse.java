package bo.com.reportate.model.dto.response;

import lombok.*;

import java.io.Serializable;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TablaResponse implements Serializable{

	private static final long serialVersionUID = -7048312631903674131L;
	private String nivelLugar;
	private List<ItemDto> items; 
}
