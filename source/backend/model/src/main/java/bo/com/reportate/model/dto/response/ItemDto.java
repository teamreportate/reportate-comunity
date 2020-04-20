package bo.com.reportate.model.dto.response;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Data
public class ItemDto implements Serializable {

	private static final long serialVersionUID = -1173856019299370037L;

	private String nombre;
	private Long sospechoso;
	private Long descartado;
	private Long positivo;
	private Long recuperado;
	private Long deceso;
	public ItemDto(String nombre, Long sospechoso, Long descartado, Long positivo, Long recuperado, Long deceso) {
		super();
		this.nombre = nombre;
		this.sospechoso = sospechoso;
		this.descartado = descartado;
		this.positivo = positivo;
		this.recuperado = recuperado;
		this.deceso = deceso;
	}
	
	
}
