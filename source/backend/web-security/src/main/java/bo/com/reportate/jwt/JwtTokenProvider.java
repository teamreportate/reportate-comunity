package bo.com.reportate.jwt;

import bo.com.reportate.cache.CacheService;
import bo.com.reportate.exception.InvalidJwtAuthenticationException;
import bo.com.reportate.exception.NotDataFoundException;
import bo.com.reportate.model.Constants;
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
    private String secretKey;
    private static final long _1_HORA_MILESEGUNDOS = 3600000L;
    private static final long _1_DIA_MILESEGUNDOS = 86400000L;
    @Autowired
    private SecurityUserDetailsService securityUserDetailsService;

    @Autowired
    private TokenService tokenService;
    @Autowired
    private CacheService cacheService;

    @PostConstruct
    protected void init() {
        log.info("SECRET [{}]",cacheService.getStringParam(Constants.Parameters.JWT_LLAVE_PRIVADA));
        secretKey = Base64.getEncoder().encodeToString(cacheService.getStringParam(Constants.Parameters.JWT_LLAVE_PRIVADA).getBytes());
    }
//dqf5rhyhe8PfMkkWx4pd5g-dRBXnzAyZT3UxPkxN9wdg
    public String createToken(String username, MuUsuario usuario) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("parametro", username);
        claims.put("cambiar-password",usuario.getPasswordGenerado());
        claims.put("tipoUsuario",usuario.getTipoUsuario());
        Date now = new Date();
        long tiempoValidezHora = cacheService.getNumberParam(Constants.Parameters.JWT_TIEMPO_VALIDES_BACKEND).longValue();
        Date validity = new Date(now.getTime() + (tiempoValidezHora * _1_HORA_MILESEGUNDOS));
        String token=  Jwts.builder()//
                .setClaims(claims)//
                .setIssuedAt(now)//
                .setExpiration(validity)//
                .signWith(SignatureAlgorithm.HS512, secretKey)//
                .compact();
        this.tokenService.registroToken(MuToken.builder().fechaInicio(now).fechaExpiracion(validity).token(token).idUsuario(usuario).indefinido(false).build());
        return token;
    }

    public String createTokenMovil(String username, MuUsuario usuario) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("parametro", username);
        claims.put("cambiar-password",usuario.getPasswordGenerado());
        claims.put("tipoUsuario",usuario.getTipoUsuario());
        Date now = new Date();
        long tiempoValidezDia = cacheService.getNumberParam(Constants.Parameters.JWT_TIEMPO_VALIDES_FRONTEND).longValue();
        Date validity = new Date(now.getTime() + (tiempoValidezDia * _1_DIA_MILESEGUNDOS));
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
                return bearerToken.substring(7);
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
