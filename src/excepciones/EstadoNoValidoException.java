package excepciones;

public class EstadoNoValidoException extends Exception {
    public EstadoNoValidoException(String mensaje) {
        super(mensaje);
    }

    public EstadoNoValidoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}