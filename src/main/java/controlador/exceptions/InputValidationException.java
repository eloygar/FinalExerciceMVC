package controlador.exceptions;

public class InputValidationException extends Exception {

	public InputValidationException() {
        super();
    }

    public InputValidationException(String mensaje) {
        super(mensaje);
    }

    public InputValidationException(String mensaje, Throwable cause) {
        super(mensaje, cause);
    }

    public InputValidationException(Throwable cause) {
        super(cause);
    }
}
