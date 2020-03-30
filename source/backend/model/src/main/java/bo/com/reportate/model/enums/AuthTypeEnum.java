package bo.com.reportate.model.enums;

import lombok.Getter;

@Getter
public enum AuthTypeEnum {
    SISTEMA("sistema"),
    FACEBOOK("facebook"),
    GOOGLE("google");
    private String fuente;
    AuthTypeEnum(String fuente){
        this.fuente = fuente;
    }
}

