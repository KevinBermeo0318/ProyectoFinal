package excepciones;

public class BatallaNoIniciadaException extends Exception {
    public BatallaNoIniciadaException(String mensaje) {
        super(mensaje);
    }

    public BatallaNoIniciadaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
