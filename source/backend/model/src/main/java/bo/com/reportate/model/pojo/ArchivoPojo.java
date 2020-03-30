package bo.com.reportate.model.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArchivoPojo {

    private String nombre;
    private long longitud;
    private String tipo;
    private String tipoMime;
    private String archivoBase64;
}
