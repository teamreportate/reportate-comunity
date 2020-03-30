package bo.com.reportate.service;

import bo.com.reportate.model.MuToken;
import bo.com.reportate.model.MuUsuario;
import bo.com.reportate.model.dto.TokenDto;

import java.util.List;

/**
 * MC4 SRL
 * Santa Cruz - Bolivia
 * project: reportate
 * package: bo.com.reportate.service
 * date:    12-09-19
 * author:  fmontero
 **/

public interface TokenService {

    TokenDto registroToken(MuToken token);

    List<TokenDto> obtenerListadoTokenUsuario(Long usuarioId);
    void inhabilitarToken(MuUsuario muUsuario);
    boolean validateToken(String token);
    void inahbilitarToken(Long tokenId);
}
