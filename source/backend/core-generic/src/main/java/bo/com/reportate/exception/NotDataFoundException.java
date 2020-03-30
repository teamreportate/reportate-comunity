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

public class NotDataFoundException extends RuntimeException {
    public NotDataFoundException(String message, Throwable cause) {
        super(message,cause);
    }
    public NotDataFoundException(String message) {
        super(message);
    }
}
