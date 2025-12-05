package modelo;

public class EstadoAlterado {
    public enum Tipo {
        NORMAL,
        DORMIDO,
        ENVENENADO,
        PARALIZADO
    }

    private Tipo tipo;
    private int duracion;

    public EstadoAlterado(Tipo tipo, int duracion) {
        this.tipo = tipo;
        this.duracion = duracion;
    }

    public boolean estaActivo() {
        return duracion > 0;
    }

    public void reducirDuracion() {
        if (duracion > 0) {
            duracion--;
        }
    }

    // Getters
    public Tipo getTipo() { return tipo; }
    public int getDuracion() { return duracion; }

    // Efectos según tipo
    public double getModificadorAtaque() {
        switch(tipo) {
            case DORMIDO:
                return 0.0; // No puede atacar
            case PARALIZADO:
                return 0.5; // Mitad de daño
            default:
                return 1.0; // Normal
        }
    }

    public void aplicarEfectoPorTurno(Personaje personaje) {
        switch(tipo) {
            case ENVENENADO:
                personaje.recibirDanio(5); // Daño por veneno
                break;
            case DORMIDO:
                // 50% de probabilidad de despertar
                if (Math.random() < 0.5) {
                    tipo = Tipo.NORMAL;
                    duracion = 0;
                }
                break;
        }
    }
}