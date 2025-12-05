package modelo;

public class MiniJefe extends Enemigo {
    private boolean furiaActivada;

    public MiniJefe(String nombre, int hp, int mp, int ataque, int defensa, int velocidad) {
        super(nombre, hp, mp, ataque, defensa, velocidad, "minijefe");
        this.furiaActivada = false;
    }

    @Override
    public void atacar(Personaje objetivo) {
        int danio = this.getAtaque();

        // MiniJefe se vuelve más fuerte al tener poca vida
        if (!furiaActivada && getHp() < getHpMax() * 0.3) {
            furiaActivada = true;
            danio *= 1.5; // 50% más de daño
        }

        objetivo.recibirDanio(danio);
    }
}