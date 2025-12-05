package excepciones;

public class MPInsuficienteException extends Exception {
    public MPInsuficienteException(String mensaje) {
        super(mensaje);
    }

    public MPInsuficienteException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
