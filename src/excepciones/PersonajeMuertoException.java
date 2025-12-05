package excepciones;

public class PersonajeMuertoException extends RuntimeException {
    public PersonajeMuertoException(String mensaje) {
        super(mensaje);
    }

    public PersonajeMuertoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
