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
	private List<Long> negativos;
	private List<Long> confirmados;
	private List<Long> curados;
	private List<Long> fallecidos;
	
	public void add(String dia,Long sospechoso, Long negativo, Long confirmado, Long curado, Long fallecido) {
		if(dias == null) {
			dias = new ArrayList<>();
			sospechosos = new ArrayList<>();
			negativos = new ArrayList<>();
			confirmados = new ArrayList<>();
			curados = new ArrayList<>();
			fallecidos = new ArrayList<>();
		}
		dias.add(dia);
		sospechosos.add(sospechoso);
		negativos.add(negativo);
		confirmados.add(confirmado);
		curados.add(curado);
		fallecidos.add(fallecido);
	}
}
