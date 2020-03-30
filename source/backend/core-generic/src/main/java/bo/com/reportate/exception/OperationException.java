/**
 * Banco Central De Bolivia
 * La Paz - Bolivia
 * bcb-mld-ejb
 * gob.bcb.mld.util.AppInicio
 * 06/04/2015 - 08:30:00
 * Creado por fbotello-MC4
 */
package bo.com.reportate.exception;

/**
 * Clase que realiza el manejo de las excepciones para el proyecto
 *
 *
 *
 */
public class OperationException extends RuntimeException
{

	private static final long serialVersionUID = 10316048604864L;

	private ErrorCode codigoError;

	public ErrorCode getCodigoError()
	{
		return codigoError;
	}

	public OperationException(OperationException e)
	{
		super(e);
		this.codigoError = e.codigoError;
	}

	public OperationException(String mensaje)
	{
		super(mensaje);
	}

	public OperationException(String mensaje, ErrorCode codigoError)
	{
		super(mensaje);
		this.codigoError = codigoError;
	}

	public OperationException(String mensaje, Throwable e)
	{
		super(mensaje, e);
	}

	public OperationException(String mensaje, Throwable e, ErrorCode codigoError)
	{
		super(mensaje, e);
		this.codigoError = codigoError;
	}
}
