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

public class NotDataFoundException extends RuntimeException {
    public NotDataFoundException(String message, Throwable cause) {
        super(message,cause);
    }
    public NotDataFoundException(String message) {
        super(message);
    }
}
