package bo.com.reportate.service;

import bo.com.reportate.model.enums.EstadoEnum;
import bo.com.reportate.repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SecurityUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.usuarioRepository.findByEstadoAndUsername(EstadoEnum.ACTIVO, username)
                .orElseThrow(() -> new UsernameNotFoundException("Credenciales incorrectos."));
    }
    
}
