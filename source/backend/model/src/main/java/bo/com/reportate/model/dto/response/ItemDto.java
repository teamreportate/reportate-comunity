package bo.com.reportate.model.dto.response;

import java.io.Serializable;

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
public class ItemDto implements Serializable {

	private static final long serialVersionUID = -1173856019299370037L;

	private String nombre;
	private Long sospechoso;
	private Long negativo;
	private Long confirmado;
	private Long curado;
	private Long fallecido;
}
