package bo.com.reportate.model.enums;

import lombok.Getter;

/**
 * Created by :MC4
 * Autor      :Ricardo Laredo
 * Email      :rlaredo@mc4.com.bo
 * Date       :07-01-19
 * Project    :reportate
 * Package    :bo.com.reportate.model.enums
 * Copyright  : MC4
 */
@Getter
public enum LogLevel {
    INFO("INFO"),WARNING("WARNING"),ERROR("ERROR");
    private String level;
    LogLevel(String level){
        this.level = level;
    }

}
