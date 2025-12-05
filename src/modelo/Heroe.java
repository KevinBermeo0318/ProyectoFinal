package modelo;
import estructuras.Inventario;
public class Heroe extends Personaje {
    private Inventario inventario;

    public Heroe(String nombre, int hp, int mp, int ataque, int defensa, int velocidad) {
        super(nombre, hp, mp, ataque, defensa, velocidad);
        this.inventario = new Inventario();
    }

    @Override
    public void atacar(Personaje objetivo) {
        int danio = this.ataque;
        objetivo.recibirDanio(danio);
    }

    // GETTER PARA INVENTARIO
    public Inventario getInventario() {
        return inventario;
    }

    public void setInventario(Inventario inventario) {
        this.inventario = inventario;
    }

    public void usarHabilidad(Habilidad habilidad, Personaje objetivo) {
        if (mp >= habilidad.getCostoMp()) {
            usarMp(habilidad.getCostoMp());
            habilidad.ejecutar(this, objetivo);
        }
    }
}
