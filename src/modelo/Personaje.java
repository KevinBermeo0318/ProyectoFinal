package modelo;

public abstract class Personaje {
    protected String nombre;
    protected int hp;
    protected int hpMax;
    protected int mp;
    protected int mpMax;
    protected int ataque;
    protected int defensa;
    protected int velocidad;
    protected boolean vivo;

    public Personaje(String nombre, int hp, int mp, int ataque, int defensa, int velocidad) {
        this.nombre = nombre;
        this.hp = hp;
        this.hpMax = hp;
        this.mp = mp;
        this.mpMax = mp;
        this.ataque = ataque;
        this.defensa = defensa;
        this.velocidad = velocidad;
        this.vivo = true;
    }

    public abstract void atacar(Personaje objetivo);

    // Getters y setters
    public String getNombre() { return nombre; }
    public int getHp() { return hp; }
    public int getHpMax() { return hpMax; }
    public int getMp() { return mp; }
    public int getMpMax() { return mpMax; }
    public int getAtaque() { return ataque; }
    public int getDefensa() { return defensa; }
    public int getVelocidad() { return velocidad; }
    public boolean estaVivo() { return vivo; }

    public void recibirDanio(int danio) {
        hp -= danio;
        if (hp <= 0) {
            hp = 0;
            vivo = false;
        }
    }

    public void curar(int cantidad) {
        hp += cantidad;
        if (hp > hpMax) hp = hpMax;
    }

    public void usarMp(int cantidad) {
        mp -= cantidad;
        if (mp < 0) mp = 0;
    }

    public void restaurarMp(int cantidad) {
        mp += cantidad;
        if (mp > mpMax) mp = mpMax;
    }
}