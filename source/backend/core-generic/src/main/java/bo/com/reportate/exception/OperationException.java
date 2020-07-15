/**
 * Create by: Reportate
 * La Paz - Bolivia
 * bcb-mld-ejb
 * @Package :bo.com.reportate.service.impl
 * 06/04/2015 - 08:30:00
 * Creado por fbotello-Reportate
 */
package bo.com.reportate.exception;

/**
 * Clase que realiza el manejo de las excepciones para el proyecto
 *
 *
 *
 */
public class OperationException extends RuntimeException {
	private static final long serialVersionUID = 10316048604864L;
	public OperationException(String mensaje) {
		super(mensaje);
	}

	public OperationException(String mensaje, Throwable e)
	{
		super(mensaje, e);
	}
}
