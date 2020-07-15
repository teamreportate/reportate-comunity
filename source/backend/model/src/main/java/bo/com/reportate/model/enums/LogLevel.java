package bo.com.reportate.model.enums;

import lombok.Getter;

/**
 * Created by :Reportate
 * Autor      :Ricardo Laredo
 * Email      :rllayus@gmail.com
 * Date       :07-01-19
 * Project    :reportate
 * Package    :bo.com.reportate.model.enums
 * Copyright  : Reportate
 */
@Getter
public enum LogLevel {
    INFO("INFO"),WARNING("WARNING"),ERROR("ERROR");
    private String level;
    LogLevel(String level){
        this.level = level;
    }

}
