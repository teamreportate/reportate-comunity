package bo.com.reportate.model.enums;

import lombok.Getter;

/**
 * Created by :Reportate
 * Autor      :Ricardo Laredo
 * Email      :rllayus@gmail.com
 * Date       :13-01-19
 * Project    :reportate
 * Package    :bo.com.reportate.model.enums
 * Copyright  : Reportate
 */
@Getter
public enum Process {
    // Administracion...
    SESION("SESION"),
    ADMINISTRACION("ADMINISTRACION"),
    // Grupo
    GRUPO("PROCESS"),
    REGISTRO_FAMILIA("REGISTRO_FAMILIA"),
    REGISTRO_PACIENTE("REGISTRO_PACIENTE"),
    CONTROL_DIARIO("CONTROL_DIARIO"),
    DIAGNOSTICO("DIAGNOSTICO");

    private String proceso;
    Process(String proceso){
        this.proceso = proceso;
    }
}
