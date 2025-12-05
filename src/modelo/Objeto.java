package modelo;

public class Objeto {
    private String nombre;
    private String descripcion;
    private String tipo;

    public Objeto(String nombre, String descripcion, String tipo) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipo = tipo;
    }

    public void usar(Personaje objetivo) {
        switch(tipo) {
            case "curacion":
                objetivo.curar(50);
                break;
            case "restauracion_mp":
                objetivo.restaurarMp(30);
                break;
            case "antidoto":
                // Cura envenenamiento - implementar si tienes sistema de estados
                System.out.println("Curando envenenamiento de " + objetivo.getNombre());
                break;
            case "despertar":
                // Despierta personaje - implementar si tienes sistema de estados
                System.out.println("Despertando a " + objetivo.getNombre());
                break;
            case "daño_area":
                // Daño a todos los enemigos - implementar lógica de batalla
                System.out.println("Usando " + nombre + " en área");
                break;
            default:
                System.out.println("Usando objeto: " + nombre);
        }
    }

    // Getters
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public String getTipo() { return tipo; }

    @Override
    public String toString() {
        return nombre + " - " + descripcion;
    }
}