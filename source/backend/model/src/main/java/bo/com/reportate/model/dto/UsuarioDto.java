package bo.com.reportate.model.dto;

import bo.com.reportate.model.CentroSalud;
import bo.com.reportate.model.Departamento;
import bo.com.reportate.model.MuUsuario;
import bo.com.reportate.model.Municipio;
import bo.com.reportate.model.enums.AuthTypeEnum;
import bo.com.reportate.model.enums.EstadoEnum;
import bo.com.reportate.model.enums.TipoUsuarioEnum;
import bo.com.reportate.model.enums.UserStatusEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioDto  implements Serializable {
    private Long id;
    private String nombre;
    private String username;
    private String email;
    private Boolean tokenExpired = false;
    private String tokenHeader;
    private AuthTypeEnum authType;
    private UserStatusEnum estadoUsuario;
    private EstadoEnum estado;
    private Boolean usuarioFrontOffice = false;
    private Boolean passwordGenerado = false;
    private String token;
    private String password;
    private TipoUsuarioEnum tipoUsuario;

    private List<GrupoDto> grupos = new ArrayList<>();
    private List<DepartamentoDto> departamentos = new ArrayList<>();
    private List<MunicipioDto> municipios = new ArrayList<>();
    private List<CentroSaludDto> centroSaluds = new ArrayList<>();

    public UsuarioDto(MuUsuario userx){
        this.id= userx.getId();
        this.nombre = userx.getNombre();
        this.username = userx.getUsername();
        this.email = userx.getEmail();
        this.authType= userx.getAuthType();
        this.estadoUsuario = userx.getEstadoUsuario();
        this.estado = userx.getEstado();
        this.passwordGenerado = userx.getPasswordGenerado();
        this.token = userx.getToken();
        this.tipoUsuario = userx.getTipoUsuario();
    }

}
