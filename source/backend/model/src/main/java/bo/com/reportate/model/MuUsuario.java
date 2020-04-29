package bo.com.reportate.model;

import bo.com.reportate.model.enums.AuthTypeEnum;
import bo.com.reportate.model.enums.EstadoEnum;
import bo.com.reportate.model.enums.TipoUsuarioEnum;
import bo.com.reportate.model.enums.UserStatusEnum;
import bo.com.reportate.model.pojo.MenuPojo;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "MU_USUARIO")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "MU_USUARIO_ID_GENERATOR", sequenceName = "SEQ_MU_USUARIO_ID", allocationSize = 1)
public class MuUsuario extends AbstractAuditableEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MU_USUARIO_ID_GENERATOR")
    @Column(name = "ID")
    private Long id;

    @Column(name = "NOMBRE", nullable = false,length = 255)
    private String nombre;

    @Column(name = "USERNAME", nullable = false, length = 500)
    private String username;

    @Column(name = "PASSWORD",nullable = false, length = 500)
    private String password;

    @Column(name = "EMAIL", length = 255)
    private String email;

    @Enumerated(EnumType.STRING)
    private AuthTypeEnum authType;

    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO_USUARIO", nullable = false)
    private UserStatusEnum estadoUsuario;

    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO_USUARIO")
    private TipoUsuarioEnum tipoUsuario;

    @Column(name = "PASSWORD_GENERADO")
    private Boolean passwordGenerado = false;

    @Column(name = "CONTADOR_INTENTO_AUTENTICACION", nullable = true)
    private Integer contadorIntentoAutenticacion = 0;

    @Transient
    private String token;
    @Transient
    private MuGrupo grupo;
    @Transient
    private List<MuPermiso> permisos=new ArrayList<>();
    @Transient
    private List<MuRecurso> recursosPadres=new ArrayList<>();
    @Transient
    private List<MuRecurso> recursosHijos=new ArrayList<>();
    @Transient
    private List<MenuPojo> menu=new ArrayList<>();



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return  null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return (this.estadoUsuario == UserStatusEnum.ACTIVO);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return super.estado == EstadoEnum.ACTIVO;
    }

}




