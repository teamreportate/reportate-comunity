package bo.com.reportate.model.enums;

import lombok.Getter;

/**
 * MC4 SRL
 * Santa Cruz - Bolivia
 * project: reportate
 * package: bo.com.reportate.model.enums
 * date:    22-07-19
 * author:  fmontero
 **/
@Getter
public enum UserStatusEnum {
    ACTIVO("ACTIVO"),
    BLOQUEADO("BLOQUEADO");
    private String estado;
    UserStatusEnum(String estado){
        this.estado = estado;
    }
}
