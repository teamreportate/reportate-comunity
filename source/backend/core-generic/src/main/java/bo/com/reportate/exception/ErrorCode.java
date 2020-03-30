package bo.com.reportate.exception;

/**
 * MC4 SRL
 * La Paz - Bolivia
 * 09/11/2016 - 12:33 PM
 * Created by: aticona
 */

public enum ErrorCode {

	E900_INESPERADO("Error inesperado"),
	E910_FALTANTE("Parametro requerido faltante"),
	E920_INVALIDO("Tipo de dato invalido"),
	E930_NOK_ENCONTRADO("Objeto no encontrado"),
	E940_NOK_ESTADO("Estado no permitido"),
	E950_NOK_USUARIO("Usuario no autorizado para la operacion"),
	E960_TIMEOUT("Timeout de la operacion");

	ErrorCode(String descripcionError) {
		this.descripcionError = descripcionError;
	}

	String descripcionError;

	public String getDescripcionError() {
		return descripcionError;
	}
}
