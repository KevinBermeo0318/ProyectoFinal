package modelo;

import java.util.ArrayList;
import java.util.List;

public class Batalla {
    private List<Heroe> heroes;
    private List<Enemigo> enemigos;
    private List<Personaje> ordenTurnos;
    private boolean enCurso;
    private String resultado;

    public Batalla(List<Heroe> heroes, List<Enemigo> enemigos) {
        this.heroes = new ArrayList<>(heroes);
        this.enemigos = new ArrayList<>(enemigos);
        this.ordenTurnos = new ArrayList<>();
        this.enCurso = true;
        calcularOrdenTurnos();
    }

    private void calcularOrdenTurnos() {
        // Combinar todos los personajes
        List<Personaje> todos = new ArrayList<>();
        todos.addAll(heroes);
        todos.addAll(enemigos);

        // Ordenar por velocidad (más alto primero)
        todos.sort((p1, p2) -> Integer.compare(p2.getVelocidad(), p1.getVelocidad()));

        // En caso de empate, usar orden aleatorio
        ordenTurnos = todos;
    }

    // MÉTODO NUEVO: Obtener héroes
    public List<Heroe> getHeroes() {
        return new ArrayList<>(heroes);
    }

    // MÉTODO NUEVO: Obtener enemigos
    public List<Enemigo> getEnemigos() {
        return new ArrayList<>(enemigos);
    }

    public boolean isEnCurso() {
        return enCurso;
    }

    public List<Personaje> getOrdenTurnos() {
        return new ArrayList<>(ordenTurnos);
    }

    public void verificarFinBatalla() {
        // Verificar si todos los héroes están muertos
        boolean heroesMuertos = true;
        for (Heroe heroe : heroes) {
            if (heroe.estaVivo()) {
                heroesMuertos = false;
                break;
            }
        }

        // Verificar si todos los enemigos están muertos
        boolean enemigosMuertos = true;
        for (Enemigo enemigo : enemigos) {
            if (enemigo.estaVivo()) {
                enemigosMuertos = false;
                break;
            }
        }

        if (heroesMuertos) {
            enCurso = false;
            resultado = "Derrota";
        } else if (enemigosMuertos) {
            enCurso = false;
            resultado = "Victoria";
        }
    }

    public String getResultado() {
        return resultado;
    }
}