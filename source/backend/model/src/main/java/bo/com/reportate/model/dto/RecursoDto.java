package bo.com.reportate.model.dto;

import bo.com.reportate.model.MuRecurso;
import bo.com.reportate.model.enums.EstadoEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by    : Vinx J. Guzman Martinez.
 * Date          :15/11/2019
 * Project       :reportate
 * Package       :bo.com.Reportate.reportate.model.dto
 **/

@Data
@Builder
@AllArgsConstructor
public class RecursoDto implements Serializable {
    private Long id;
    private RecursoDto idRecursoPadre;
    private String nombre;
    private String url;
    private String icon;
    private String descripcion;
    private EstadoEnum estado;
    private Integer ordenMenu;
    private String componenteFront;
    private boolean asignado ;

    @Transient
    private List<RecursoDto> listaRecursos;
    public RecursoDto(){
        this.listaRecursos = new ArrayList<>();
    }

    public RecursoDto(MuRecurso authRecurso){
        this.listaRecursos = new ArrayList<>();
        if(authRecurso.getIdRecursoPadre() != null) {
            idRecursoPadre = new RecursoDto();
            BeanUtils.copyProperties(authRecurso.getIdRecursoPadre(),this.idRecursoPadre);
        }
        this.asignado = authRecurso.getPermisos() != null && authRecurso.getPermisos().size() > 0;
        this.id = authRecurso.getId();
        this.nombre = authRecurso.getNombre();
        this.url = authRecurso.getUrl();
        this.icon = authRecurso.getIcon();
        this.descripcion = authRecurso.getDescripcion();
        this.estado = authRecurso.getEstado();
        this.ordenMenu = authRecurso.getOrdenMenu();
        this.componenteFront = authRecurso.getComponenteFront();

    }


}
