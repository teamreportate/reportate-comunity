package bo.com.reportate.cache;

import bo.com.reportate.service.ParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

@Component
public class CacheServiceImpl implements CacheService{

    @Autowired
    ParamService paramService;

    @Override
    @Cacheable(value = "parametrosCache")
    public String getStringParam(String codigo) {
        return paramService.getValue(codigo);
    }

    @Override
    @Cacheable(value = "parametrosCache")
    public Boolean getBooleanParam(String codigo) {
        return paramService.getBoolean(codigo);
    }

    @Override
    @Cacheable(value = "parametrosCache")
    public Date getDateParam(String codigo) {
        return paramService.getDate(codigo);
    }

    @Override
    @Cacheable(value = "parametrosCache")
    public Integer getIntegerParam(String codigo) {
        return paramService.getInt(codigo);
    }

    @Override
    @Cacheable(value = "parametrosCache")
    public BigDecimal getNumberParam(String codigo) {
        return paramService.getNumber(codigo);
    }
}
