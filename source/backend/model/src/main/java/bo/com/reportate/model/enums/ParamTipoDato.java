package bo.com.reportate.model.enums;

import lombok.Getter;

/**
 * Created by :MC4
 * Autor      :Ricardo Laredo
 * Email      :rlaredo@mc4.com.bo
 * Date       :12-01-19
 * Project    :reportate
 * Package    :bo.com.reportate.model.enums
 * Copyright  : MC4
 */
@Getter
public enum ParamTipoDato {
    CADENA("CADENA"),NUMERICO("NUMERICO"),BOOLEANO("BOOLEANO"),FECHA("FECHA"),LOB("LOB"),UNDEFINID("UNDEFINID");
    String tipoDato;
    ParamTipoDato(String tipoDato){
        this.tipoDato = tipoDato;
    }
}
