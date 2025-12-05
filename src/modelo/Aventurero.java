package modelo;

public class Aventurero {
    private String nombre;
    private String clase;
    private int prioridad;

    public Aventurero(String nombre, String clase, int prioridad) {
        this.nombre = nombre;
        this.clase = clase;
        this.prioridad = prioridad;
    }

    // Getters
    public String getNombre() { return nombre; }
    public String getClase() { return clase; }
    public int getPrioridad() { return prioridad; }

    // Método para guardar en CSV
    public String toCSV() {
        return nombre + "," + clase + "," + prioridad;
    }

    @Override
    public String toString() {
        return nombre + " (" + clase + ") - Prioridad: " + prioridad;
    }
}