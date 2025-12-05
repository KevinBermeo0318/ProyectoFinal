// En archivo: excepciones/InventarioLlenoException.java
package excepciones;

public class InventarioLlenoException extends Exception {
    public InventarioLlenoException(String mensaje) {
        super(mensaje);
    }

    public InventarioLlenoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}