package bo.com.reportate.model.dto.response;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter @NoArgsConstructor @Data
public class GraficoLineaResponse implements Serializable{
	
	private static final long serialVersionUID = 4333588897253727564L;
	
	private List<String> dias;
	private List<Long> sospechosos;
	private List<Long> descartados;
	private List<Long> positivos;
	private List<Long> recuperados;
	private List<Long> decesos;
	private List<Long> confirmados;
	
	public void add(String dia,Long sospechoso, Long descartado, Long positivo, Long recuperado, Long deceso) {
		if(dias == null) {
			dias = new ArrayList<>();
			sospechosos = new ArrayList<>();
			descartados = new ArrayList<>();
			positivos = new ArrayList<>();
			recuperados = new ArrayList<>();
			decesos = new ArrayList<>();
			confirmados = new ArrayList<>();
		}
		dias.add(dia);
		sospechosos.add(sospechoso);
		descartados.add(descartado);
		positivos.add(positivo);
		recuperados.add(recuperado);
		decesos.add(deceso);
		confirmados.add(positivo + recuperado + deceso);
	}
}
