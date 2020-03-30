package bo.com.reportate.model.dto;

import bo.com.reportate.model.enums.EstadoEnum;
import bo.com.reportate.model.enums.UserStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioBasicDto {

    private Long id;
    private String nombre;
    private String username;
    private String email;
    private UserStatusEnum estadoUsuario;
    private EstadoEnum estado;

}
