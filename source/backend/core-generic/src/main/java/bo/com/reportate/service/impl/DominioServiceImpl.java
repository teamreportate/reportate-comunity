package bo.com.reportate.service.impl;

import bo.com.reportate.exception.NotDataFoundException;
import bo.com.reportate.exception.OperationException;
import bo.com.reportate.model.MuDominio;
import bo.com.reportate.model.MuValorDominio;
import bo.com.reportate.model.dto.DominioDto;
import bo.com.reportate.model.dto.ValorDominioDto;
import bo.com.reportate.repository.DominioRepository;
import bo.com.reportate.repository.ValorDominioRepository;
import bo.com.reportate.service.DominioService;
import bo.com.reportate.utils.FormatUtil;
import bo.com.reportate.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Slf4j
public class DominioServiceImpl implements DominioService {

    @Autowired
    private ValorDominioRepository valorDominioRepository;
    @Autowired
    private DominioRepository dominioRepository;


    @Override
    public List<DominioDto> findAll() {
        return this.dominioRepository.findAllByOrderByCodigoAsc();
    }

    @Override
    public List<MuValorDominio> listaValores(Long dominioId) {
        MuDominio dominio = this.dominioRepository.findById(dominioId).orElseThrow(() -> new NotDataFoundException(FormatUtil.noRegistrado("Dominio", dominioId)));
        return this.valorDominioRepository.listaValores(dominio.getId());
    }

    @Override
    public List<DominioDto> findByCodigo(String codigo) {
        return this.dominioRepository.findAllByCodigo(codigo);
    }

    @Override
    public List<ValorDominioDto> listarBusqueda(String criterioBusqueda) {
        return valorDominioRepository.findByDescripcionContainingOrValorContainingAllIgnoreCase (criterioBusqueda, criterioBusqueda,  ValorDominioDto.class);
    }

    @Override
    public MuValorDominio obtener(Long id) {
        MuValorDominio sfeValorDominio= valorDominioRepository.getOne(id);
        if(sfeValorDominio==null){
            throw new OperationException(FormatUtil.noRegistrado("ValorDominio",id));
        }
        return sfeValorDominio;
    }

    @Override
    public Long crear(MuDominio muDominio, String valor, String descripcion) {
        if(StringUtil.isEmptyOrNull(valor)){
            throw new OperationException(FormatUtil.requerido("valor"));
        }
        if(StringUtil.isEmptyOrNull(descripcion)){
            throw new OperationException(FormatUtil.requerido("descripción"));
        }
        if(this.valorDominioRepository.findByDominioAndValor(muDominio, valor).isPresent()){
            throw new OperationException(FormatUtil.yaRegistrado("ValorDominio","valor",valor));
        }
        return this.valorDominioRepository.save(MuValorDominio.builder().descripcion(descripcion).valor(valor).build()).getId();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class, OperationException.class })
    public Long modificar(ValorDominioDto valorDominioDto, Long id) {
        if(StringUtil.isEmptyOrNull(valorDominioDto.getValor())){
            throw new OperationException(FormatUtil.requerido("valor"));
        }
        if(StringUtil.isEmptyOrNull(valorDominioDto.getDescripcion())){
            throw new OperationException(FormatUtil.requerido("descripcion"));
        }
        MuValorDominio dominio = valorDominioRepository.getOne(id);
        if(dominio == null){
            throw new OperationException(FormatUtil.noRegistrado("ValorDominio",id));
        }
        valorDominioRepository.updateValorDominio(valorDominioDto.getDescripcion(),valorDominioDto.getValor(), id);
        return dominio.getId();
    }


    @Override
    public void saveDominio(MuDominio muDominio) {
        this.dominioRepository.save(muDominio);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DominioDto> findDominioByCodigo(String codigo) {
        return this.dominioRepository.findAllByCodigo(codigo);
    }

    @Override
    @Transactional(readOnly = true)
    public MuValorDominio findByDominioIdAndValue(Long id, String valor) {
        MuDominio muDominio = this.dominioRepository.findById(id).orElseThrow(() -> new OperationException(FormatUtil.noRegistrado("Dominio", id)));
        return this.valorDominioRepository.findAllByDominioId(muDominio.getId(), valor);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveValorDominio(MuValorDominio valorDominio) {
        MuDominio dominio = this.dominioRepository.findByCodigo(valorDominio.getDominio().getCodigo()).orElseThrow(()->new NotDataFoundException("No se encontró ningun dominio con codigo: " + valorDominio.getDominio().getCodigo()));
        if(!this.valorDominioRepository.findByDominioAndValor(dominio, valorDominio.getValor()).isPresent()){
            this.valorDominioRepository.save(valorDominio);
        }
    }

    @Override
    public ValorDominioDto saveValorDominio(String codigo, ValorDominioDto valorDominio) {
        if(StringUtil.isEmptyOrNull(codigo)){
            throw new OperationException(FormatUtil.requerido("codigo"));
        }
        if(StringUtil.isEmptyOrNull(valorDominio.getValor())){
            throw new OperationException(FormatUtil.requerido("valor"));
        }
        if(StringUtil.isEmptyOrNull(valorDominio.getDescripcion())){
            throw new OperationException(FormatUtil.requerido("descripción"));
        }
        MuDominio dominio = this.dominioRepository.findByCodigo(codigo).orElseThrow(()->new NotDataFoundException("No se encontró ningun dominio con codigo: " + valorDominio.getDominio().getCodigo()));
        if(this.valorDominioRepository.findByDominioAndValor(dominio, valorDominio.getValor()).isPresent()){
            throw new OperationException(FormatUtil.yaRegistrado("ValorDominio","valor",valorDominio.getValor()));
        }
        return new ValorDominioDto(this.valorDominioRepository.save(MuValorDominio.builder().descripcion(valorDominio.getDescripcion()).valor(valorDominio.getValor()).dominio(dominio).build()));
    }

    @Override
    public List<ValorDominioDto> findAllByCodigo(String codigo) {
        return this.valorDominioRepository.findAllByCodigoDominio(codigo, ValorDominioDto.class);
    }

    @Override
    public List<ValorDominioDto> findByCodigoAndDescripcionOrValue(String codigo, String criterio) {
        return null;
    }

    @Override
    public void deleteValorDominio(Long id) {
        this.valorDominioRepository.deleteById(id);
    }


}
