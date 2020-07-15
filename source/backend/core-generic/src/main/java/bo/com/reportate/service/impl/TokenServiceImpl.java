package bo.com.reportate.service.impl;

import bo.com.reportate.exception.NotDataFoundException;
import bo.com.reportate.model.MuToken;
import bo.com.reportate.model.MuUsuario;
import bo.com.reportate.model.dto.TokenDto;
import bo.com.reportate.model.enums.EstadoEnum;
import bo.com.reportate.repository.TokenRepository;
import bo.com.reportate.repository.UsuarioRepository;
import bo.com.reportate.service.TokenService;
import bo.com.reportate.utils.FormatUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Reportate SRL
 * Santa Cruz - Bolivia
 * project: reportate
 * package: bo.com.reportate.service.impl
 * date:    12-09-19
 * author:  fmontero
 **/

@Slf4j
@Service("tokenService")
public class TokenServiceImpl implements TokenService {

    @Autowired private TokenRepository tokenRepository;
    @Autowired private UsuarioRepository usuarioRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public TokenDto registroToken(MuToken token) {
        this.tokenRepository.inhabilitarToken(token.getIdUsuario());
        return new TokenDto(this.tokenRepository.save(token));
    }

    @Override
    public List<TokenDto> obtenerListadoTokenUsuario(Long usuarioId) {
        MuUsuario muUsuario = this.usuarioRepository.findById(usuarioId).orElseThrow(() -> new NotDataFoundException(FormatUtil.noRegistrado("Usuario", usuarioId)));
        return this.tokenRepository.obtenerListadoTokenUsuario(muUsuario);
    }
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void inhabilitarToken(MuUsuario muUsuario) {
        this.tokenRepository.deleteByIdUsuario(muUsuario);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean validateToken(String token) {
        return this.tokenRepository.existsByEstadoAndToken(EstadoEnum.ACTIVO, token);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void inahbilitarToken(Long tokenId) {
        this.tokenRepository.inhabilitarToken(tokenId);
    }
}
