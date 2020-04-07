package bo.com.reportate.jwt;

import bo.com.reportate.exception.InvalidJwtAuthenticationException;
import bo.com.reportate.exception.NotDataFoundException;
import bo.com.reportate.model.MuToken;
import bo.com.reportate.model.MuUsuario;
import bo.com.reportate.service.SecurityUserDetailsService;
import bo.com.reportate.service.TokenService;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {
    @Value("${security.jwt.token.secret-key}")
    private String secretKey;

    @Value("${security.jwt.token.expire-length}")
    private long validityInMilliseconds; // 8h
    @Autowired
    private SecurityUserDetailsService securityUserDetailsService;

    @Autowired
    private TokenService tokenService;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String username, MuUsuario usuario) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("parametro", username);
        claims.put("cambiar-password",usuario.getPasswordGenerado());
        claims.put("tipoUsuario",usuario.getTipoUsuario());
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        String token=  Jwts.builder()//
                .setClaims(claims)//
                .setIssuedAt(now)//
                .setExpiration(validity)//
                .signWith(SignatureAlgorithm.HS512, secretKey)//
                .compact();
        this.tokenService.registroToken(MuToken.builder().fechaInicio(now).fechaExpiracion(validity).token(token).idUsuario(usuario).indefinido(false).build());
        return token;
    }

    public String createTokenApi(String username, Date fechaInicio, Date fechaExpiracion) {
        Claims claims = Jwts.claims().setSubject(username);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(fechaInicio)
                .setExpiration(fechaExpiracion)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    Authentication getAuthentication(String token) {
        UserDetails userDetails = this.securityUserDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            try {
                return bearerToken.substring(7, bearerToken.length());
            }catch (NotDataFoundException e) {
                log.error("Usuario no encontrado, UsuarioId[{}]", req.getHeader("username"));
                return null;
            }
        }
        return null;
    }

    boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            Date fechaActual=new Date();
            if(claims.getBody().getExpiration().after(fechaActual)){
                return this.tokenService.validateToken(token);
            }
            return false;
        } catch (ExpiredJwtException e){
            log.warn("La sesión del usuario a expirado");
            throw e;
        } catch (JwtException | IllegalArgumentException e) {
            log.error("Error al validar el TOKEN",e);
            throw new InvalidJwtAuthenticationException("Token JWT caducado o no válido.");
        }
    }

}
