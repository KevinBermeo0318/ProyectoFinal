package modelo;

public abstract class Habilidad {
    protected String nombre;
    protected int costoMp;
    protected String descripcion;

    public Habilidad(String nombre, int costoMp, String descripcion) {
        this.nombre = nombre;
        this.costoMp = costoMp;
        this.descripcion = descripcion;
    }

    public abstract void ejecutar(Personaje usuario, Personaje objetivo);

    // Getters
    public String getNombre() { return nombre; }
    public int getCostoMp() { return costoMp; }
    public String getDescripcion() { return descripcion; }
}

// Habilidades concretas
class Curacion extends Habilidad {
    private int cantidadCuracion;

    public Curacion() {
        super("Curación", 10, "Cura 50 HP a un aliado");
        this.cantidadCuracion = 50;
    }

    @Override
    public void ejecutar(Personaje usuario, Personaje objetivo) {
        objetivo.curar(cantidadCuracion);
    }
}

class AtaqueMagico extends Habilidad {
    private int danioMagico;

    public AtaqueMagico() {
        super("Ataque Mágico", 15, "Inflige 40 puntos de daño mágico");
        this.danioMagico = 40;
    }

    @Override
    public void ejecutar(Personaje usuario, Personaje objetivo) {
        objetivo.recibirDanio(danioMagico);
    }
}

class BuffDefensa extends Habilidad {
    public BuffDefensa() {
        super("Fortificar", 8, "Aumenta la defensa en 20% por 3 turnos");
    }

    @Override
    public void ejecutar(Personaje usuario, Personaje objetivo) {
        // Implementar lógica de buff temporal
    }
}