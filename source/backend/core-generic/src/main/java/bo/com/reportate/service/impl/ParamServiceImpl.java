package bo.com.reportate.service.impl;

import bo.com.reportate.exception.NotDataFoundException;
import bo.com.reportate.exception.OperationException;
import bo.com.reportate.exception.ParameterNotFoundException;
import bo.com.reportate.model.MuGrupoParametro;
import bo.com.reportate.model.MuParametro;
import bo.com.reportate.model.dto.MuGrupoParametroDto;
import bo.com.reportate.model.dto.MuParametroDto;
import bo.com.reportate.repository.GrupoParametroRepository;
import bo.com.reportate.repository.ParametroRepository;
import bo.com.reportate.service.ParamService;
import bo.com.reportate.sign.crypto.Crypt;
import bo.com.reportate.utils.FormatUtil;
import bo.com.reportate.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;


/**
 * Created by : MC4
 * Autor      : Ricardo Laredo
 * Email      : rlaredo@mc4.com.bo
 * Date       : 12-01-19
 * Projec    : reportate
 * Package    : bo.com.reportate.service.ParamServiceImpl
 * Copyright  : MC4
 */
@Slf4j
@Service
public class ParamServiceImpl implements ParamService {

    @Autowired
    private ParametroRepository parametroRepository;

    @Autowired
    private GrupoParametroRepository grupoParametroRepository;

    @Override
    public String getValue(String codigo) {
        Optional<MuParametro> optional = this.parametroRepository.findSfeParametroByCodigo(codigo);
        return optional.map(muParametro -> (String)getValue(muParametro)).orElse("");
    }

    @Override
    public boolean getBoolean(String codigo) {
        Optional<MuParametro> optional = this.parametroRepository.findSfeParametroByCodigo(codigo);
        return optional.map(muParametro -> (Boolean)getValue(muParametro)).orElse(false);
    }

    @Override
    public BigDecimal getNumber(String codigo) {
        Optional<MuParametro> optional = this.parametroRepository.findSfeParametroByCodigo(codigo);
        return optional.map(muParametro -> ((BigDecimal)getValue(muParametro))).orElse(new BigDecimal(0));
    }

    @Override
    public Date getDate(String codigo) {
        Optional<MuParametro> optional = this.parametroRepository.findSfeParametroByCodigo(codigo);
        return optional.map(muParametro -> ((Date) getValue(muParametro))).orElse(new Date());
    }

    @Override
    public int getInt(String codigo) {
        Optional<MuParametro> optional = this.parametroRepository.findSfeParametroByCodigo(codigo);
        return optional.map(muParametro -> ((BigDecimal) getValue(muParametro)).intValue()).orElse(0);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveParametro(MuParametro muParametro) {
        if(!this.parametroRepository.findSfeParametroByCodigo(muParametro.getCodigo()).isPresent()){
            this.parametroRepository.save(muParametro);
        }
    }

    private Object getValue(MuParametro muParametro){

        switch (muParametro.getTipoDato()) {
            case CADENA:
                if(muParametro.getDatoSensible()){
                    return Crypt.getInstance().decrypt(muParametro.getValorCadena());
                }else{
                    return muParametro.getValorCadena();
                }
            case FECHA:
                return muParametro.getValorFecha();
            case BOOLEANO:
                return muParametro.getValorBooleano();
            case NUMERICO:
                return muParametro.getValorNumerico();
            case LOB:
                return muParametro.getValorLob();
            default:
                throw new ParameterNotFoundException("No se econtro el parametro: " + muParametro.getCodigo());
        }
    }

    @Override
    public List<MuParametroDto> listar(Long grupoId) {
        MuGrupoParametro muGrupoParametro = this.grupoParametroRepository.findById(grupoId).orElseThrow(() -> new OperationException(FormatUtil.noRegistrado("Grupo de Parámetro", grupoId)));
        return parametroRepository.obtenerParametros(muGrupoParametro.getId());
    }

    @Override
    public List<MuParametro> listarBusqueda(String criterioBusqueda) {
        if(StringUtil.isEmptyOrNull(criterioBusqueda)){
            criterioBusqueda = "";
        }
        criterioBusqueda="%"+criterioBusqueda+"%";
        return parametroRepository.searchAll(criterioBusqueda);
    }

    @Override
    @Transactional
    public void actualizarParametro(Long parametroId, MuParametroDto parametroDto) {

        MuParametro muParametro = this.parametroRepository.findById(parametroId).orElseThrow(() ->
                new NotDataFoundException(FormatUtil.noRegistrado("Parámetro", parametroId)));

        String campoInvalido = parametroDto.validacionActualizacion();
        if (!StringUtil.isEmptyOrNull(campoInvalido)) throw new OperationException(FormatUtil.requerido(campoInvalido));

        switch (muParametro.getTipoDato()) {
            case FECHA:
                if (parametroDto.getValorFecha() != null) muParametro.setValorFecha(parametroDto.getValorFecha());
                else throw new OperationException(FormatUtil.requerido("Valor de Fecha"));
                break;
            case CADENA:
                if (!StringUtil.isEmptyOrNull(parametroDto.getValorCadena())) muParametro.setValorCadena(parametroDto.getValorCadena().trim());
                else throw new OperationException(FormatUtil.requerido("Valor de Cadena"));
                break;
            case BOOLEANO:
                if (parametroDto.getValorBooleano() != null) muParametro.setValorBooleano(parametroDto.getValorBooleano());
                else throw new OperationException(FormatUtil.requerido("Valor Booleano"));
                break;
            case NUMERICO:
                if (parametroDto.getValorNumerico() != null) muParametro.setValorNumerico(parametroDto.getValorNumerico());
                else throw new OperationException(FormatUtil.requerido("Valor Numerico"));
                break;
            case LOB:
                if (!StringUtil.isEmptyOrNull(parametroDto.getValorCadenaLob())) muParametro.setValorLob(parametroDto.getValorCadenaLob().trim());
                else throw new OperationException(FormatUtil.requerido("Valor de Cadena Lob"));
                break;
            default:
                log.warn("Tipo de parametro no reconocido para actualizar, Codigo: {}, Tipo: {}", muParametro.getCodigo(), muParametro.getTipoDato());
                throw new OperationException(String.format("No se reconoce el tipo de dato del parametro codigo:%s , Tipo: %s", muParametro.getCodigo(), muParametro.getTipoDato()));
        }
        muParametro.setDescripcion(parametroDto.getDescripcion().trim());
        this.parametroRepository.save(muParametro);
    }

    @Override
    public MuParametroDto getParametro(String codigo) {
        MuParametro muParametro = this.parametroRepository.getByCodigo(codigo);
        if(muParametro ==null){
            throw new OperationException(FormatUtil.noRegistrado("Parámetro",codigo));
        }
        MuParametroDto dto = new MuParametroDto();
        dto.setId(muParametro.getId());
        dto.setCodigo(muParametro.getCodigo());
        dto.setDescripcion(muParametro.getDescripcion());
        dto.setValorCadena(muParametro.getValorCadena());
        dto.setValorBooleano(muParametro.getValorBooleano());
        dto.setValorNumerico(muParametro.getValorNumerico());
        dto.setValorFecha(muParametro.getValorFecha());
        dto.setTipoDato(muParametro.getTipoDato());
        dto.setDatosSensibles(muParametro.getDatoSensible());
        dto.setValorCadenaLob(muParametro.getValorLob());
        return dto;
    }

    @Override
    public Long modificar(MuParametro muParametro, Long id) {
        MuParametro muParametroRegistrado = parametroRepository.getOne(id);
        if(muParametroRegistrado ==null){
            throw new OperationException("No se encontró el párametro");
        }
        if(StringUtil.isEmptyOrNull(muParametro.getDescripcion())){
            throw new OperationException(FormatUtil.requerido("descripción"));
        }
        muParametroRegistrado.setDescripcion(muParametro.getDescripcion());
        parametroRepository.save(muParametroRegistrado);
        return muParametroRegistrado.getId();
    }

    @Override
    public List<MuGrupoParametroDto> listaGruposParametro() {
        return this.grupoParametroRepository.listaGruposParametro();
    }


}
