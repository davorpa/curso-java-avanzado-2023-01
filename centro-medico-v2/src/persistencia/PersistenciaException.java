package persistencia;

/**
 * Encapsula los errores producidos en la capa de persistencia.
 */
public class PersistenciaException extends RuntimeException {
    public PersistenciaException(String message) {
        super(message);
    }

    public PersistenciaException(String message, Throwable cause) {
        super(message, cause);
    }

    public PersistenciaException(Throwable cause) {
        super(cause);
    }
}
