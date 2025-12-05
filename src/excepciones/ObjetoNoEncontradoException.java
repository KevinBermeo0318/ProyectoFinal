package excepciones;

public class ObjetoNoEncontradoException extends Exception {
    public ObjetoNoEncontradoException(String mensaje) {
        super(mensaje);
    }

    public ObjetoNoEncontradoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}