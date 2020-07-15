package bo.com.reportate.exception;

/**
 * Created by :Reportate
 * Autor      :Ricardo Laredo
 * Email      :rllayus@gmail.com
 * Date       :15-11-18
 * Project    :unicolegio
 * Package    :bo.com.Reportate.unicolegio.service.exception
 * Copyright  : Reportate
 */

public class ParameterNotFoundException extends RuntimeException {
    public ParameterNotFoundException(String message, Throwable cause) {
        super(message,cause);
    }
    public ParameterNotFoundException(String message) {
        super(message);
    }
}
