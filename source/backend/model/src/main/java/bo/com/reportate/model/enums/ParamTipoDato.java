package bo.com.reportate.model.enums;

import lombok.Getter;

/**
 * Created by :Reportate
 * Autor      :Ricardo Laredo
 * Email      :rllayus@gmail.com
 * Date       :12-01-19
 * Project    :reportate
 * Package    :bo.com.reportate.model.enums
 * Copyright  : Reportate
 */
@Getter
public enum ParamTipoDato {
    CADENA("CADENA"),NUMERICO("NUMERICO"),BOOLEANO("BOOLEANO"),FECHA("FECHA"),LOB("LOB"),UNDEFINID("UNDEFINID");
    String tipoDato;
    ParamTipoDato(String tipoDato){
        this.tipoDato = tipoDato;
    }
}
