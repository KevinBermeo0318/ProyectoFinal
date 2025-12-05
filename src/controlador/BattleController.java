package controlador;

import modelo.*;
import excepciones.*;
import estructuras.RegistroBatallas;
import persistencia.SistemaPersistencia;
import java.util.List;
import java.util.ArrayList;

public class BattleController {
    private Batalla batallaActual;
    private final RegistroBatallas registro;
    private final SistemaPersistencia persistencia;

    public BattleController() {
        this.registro = new RegistroBatallas();
        this.persistencia = new SistemaPersistencia();
        cargarHistorial();
    }

    private void cargarHistorial() {
        List<String> historial = persistencia.cargarHistorialBatallas();
        for (String batalla : historial) {
            registro.agregarBatalla(batalla);
        }
    }

    public void iniciarBatalla(List<Heroe> heroes, List<Enemigo> enemigos) {
        this.batallaActual = new Batalla(heroes, enemigos);
        System.out.println("¡Batalla iniciada!");
    }

    public void ejecutarTurno() throws BatallaNoIniciadaException {
        if (batallaActual == null) {
            throw new BatallaNoIniciadaException("No hay batalla en curso");
        }

        List<Personaje> orden = batallaActual.getOrdenTurnos();
        for (Personaje personaje : orden) {
            if (personaje.estaVivo()) {
                ejecutarAccionPersonaje(personaje);
            }
        }

        batallaActual.verificarFinBatalla();

        if (!batallaActual.isEnCurso()) {
            finalizarBatalla();
        }
    }

    private void ejecutarAccionPersonaje(Personaje personaje) {
        if (personaje instanceof Heroe) {
            // Lógica para héroe (controlado por jugador)
            System.out.println("Turno de " + personaje.getNombre());
        } else if (personaje instanceof Enemigo) {
            // Lógica para enemigo (IA)
            ejecutarAccionEnemigo((Enemigo) personaje);
        }
    }

    private void ejecutarAccionEnemigo(Enemigo enemigo) {
        // IA simple: atacar al héroe con menos HP
        if (batallaActual == null) return;

        // Obtener héroes de la batalla usando el nuevo método
        List<Heroe> heroes = batallaActual.getHeroes();
        Heroe objetivo = null;

        for (Heroe heroe : heroes) {
            if (heroe.estaVivo()) {
                if (objetivo == null || heroe.getHp() < objetivo.getHp()) {
                    objetivo = heroe;
                }
            }
        }

        if (objetivo != null) {
            enemigo.atacar(objetivo);
            System.out.println(enemigo.getNombre() + " ataca a " + objetivo.getNombre());
        }
    }

    // Método para obtener héroes de la batalla actual
    private List<Heroe> getHeroesDeBatalla() {
        List<Heroe> heroes = new ArrayList<>();
        if (batallaActual != null) {
            // Necesitamos acceder a los héroes de la batalla
            // Esto depende de cómo esté implementada la clase Batalla
            // Si Batalla tiene un método getHeroes(), úsalo
            // Si no, necesitamos modificar la clase Batalla
            heroes = obtenerHeroesDeBatallaInterno();
        }
        return heroes;
    }

    // Método temporal - necesitarás implementar el acceso real
    private List<Heroe> obtenerHeroesDeBatallaInterno() {
        // Esta es una implementación temporal
        // Necesitas modificar la clase Batalla para que tenga getHeroes()
        return new ArrayList<>();
    }

    private void finalizarBatalla() {
        String resultado = batallaActual.getResultado();
        registro.agregarBatalla(resultado);
        persistencia.guardarBatalla(resultado);

        System.out.println("¡Batalla finalizada! Resultado: " + resultado);
        batallaActual = null;
    }

    public boolean hayBatallaEnCurso() {
        return batallaActual != null && batallaActual.isEnCurso();
    }

    public void mostrarHistorial() {
        registro.mostrarHistorial();
    }

    // Método para obtener héroes (para pruebas)
    public List<Heroe> getHeroesDePrueba() {
        List<Heroe> heroes = new ArrayList<>();
        heroes.add(new Heroe("Héroe", 100, 50, 20, 15, 10));
        heroes.add(new Heroe("Yangus", 120, 20, 25, 20, 8));
        heroes.add(new Heroe("Jessica", 80, 80, 15, 10, 12));
        heroes.add(new Heroe("Angelo", 90, 60, 18, 12, 11));
        return heroes;
    }

    // Método para obtener enemigos (para pruebas)
    public List<Enemigo> getEnemigosDePrueba() {
        List<Enemigo> enemigos = new ArrayList<>();
        enemigos.add(new Enemigo("Slime", 50, 10, 10, 5, 5, "normal"));
        enemigos.add(new Enemigo("Goblin", 70, 15, 15, 8, 6, "agresivo"));
        enemigos.add(new Enemigo("Fantasma", 60, 30, 12, 6, 7, "defensivo"));
        enemigos.add(new MiniJefe("Dragón Negro", 150, 50, 25, 20, 9));
        return enemigos;
    }
}