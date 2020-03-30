package bo.com.reportate.model.pojo;

import bo.com.reportate.model.MuRecurso;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuPojo implements Serializable {

    private static final long serialVersionUID = -8319728475821096336L;

    private MuRecurso muRecursoPadre;

    private List<MuRecurso> listMuRecursoHijos;

}
