package bo.com.reportate.cache;

import bo.com.reportate.model.MuParametro;
import bo.com.reportate.repository.ParametroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class CacheLoader {

    @Autowired
    ParametroRepository parametroRepository;

    @PostConstruct
    public List<MuParametro> load(){
        List<MuParametro> parametroList = parametroRepository.findAll();
        return parametroList;
    }
}
