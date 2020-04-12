package bo.com.reportate.model.dto.response;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
