package bo.com.reportate.cache;

import java.math.BigDecimal;
import java.util.Date;

public interface CacheService {

    String getStringParam(String codigo);

    Boolean getBooleanParam(String codigo);

    Date getDateParam(String codigo);

    Integer getIntegerParam(String codigo);

    BigDecimal getNumberParam(String codigo);
}
