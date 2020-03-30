package bo.com.reportate.model.enums;

import lombok.Getter;

import javax.persistence.Enumerated;
import java.util.HashMap;
import java.util.Map;

/**
 * MC4
 * Santa Cruz - Bolivia
 * project: musers
 * package: bo.com.reportate.model.enums
 * date:    11/14/2019
 * author:  Toshiba
 **/
@Getter
public enum EstadoEnum {
    INACTIVO(0),
    ACTIVO(1),
    ELIMINADO(2),
    cerrado(3);
    private int value;
    private static Map map = new HashMap<>();

    EstadoEnum(int value) {
        this.value = value;
    }

    static {
        for (EstadoEnum estadoEnum : EstadoEnum.values()) {
            map.put(estadoEnum.value, estadoEnum);
        }
    }

    public static EstadoEnum valueOf(int estadoEnum) {
        return (EstadoEnum) map.get(estadoEnum);
    }

    @Enumerated
    public int getValue() {
        return value;
    }
}
