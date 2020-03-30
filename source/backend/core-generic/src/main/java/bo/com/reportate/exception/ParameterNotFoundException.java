package bo.com.reportate.exception;

/**
 * Created by :MC4
 * Autor      :Ricardo Laredo
 * Email      :rlaredo@mc4.com.bo
 * Date       :15-11-18
 * Project    :unicolegio
 * Package    :bo.com.mc4.unicolegio.service.exception
 * Copyright  : MC4
 */

public class ParameterNotFoundException extends RuntimeException {
    public ParameterNotFoundException(String message, Throwable cause) {
        super(message,cause);
    }
    public ParameterNotFoundException(String message) {
        super(message);
    }
}
