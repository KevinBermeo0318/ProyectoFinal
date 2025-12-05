package modelo;

public class Enemigo extends Personaje {
    private String tipo;

    public Enemigo(String nombre, int hp, int mp, int ataque, int defensa, int velocidad, String tipo) {
        super(nombre, hp, mp, ataque, defensa, velocidad);
        this.tipo = tipo;
    }

    @Override
    public void atacar(Personaje objetivo) {
        int danio = this.ataque;

        // Comportamientos diferenciados según tipo
        if (tipo.equals("agresivo")) {
            danio = (int)(danio * 1.2); // 20% más de daño
        } else if (tipo.equals("defensivo")) {
            danio = (int)(danio * 0.8); // 20% menos de daño
        }

        objetivo.recibirDanio(danio);
    }

    public String getTipo() {
        return tipo;
    }
}
