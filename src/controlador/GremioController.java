package controlador;

import estructuras.SistemaTurnosGremio;
import modelo.Aventurero;
import persistencia.SistemaPersistencia;
import java.util.ArrayList;
import java.util.List;

public class GremioController {
    private SistemaTurnosGremio sistemaTurnos;
    private List<Aventurero> aventurerosRegistrados;
    private SistemaPersistencia persistencia;

    public GremioController() {
        this.sistemaTurnos = new SistemaTurnosGremio();
        this.persistencia = new SistemaPersistencia();
        this.aventurerosRegistrados = cargarAventureros();
    }

    private List<Aventurero> cargarAventureros() {
        List<Aventurero> cargados = persistencia.cargarAventureros();
        if (cargados.isEmpty()) {
            // Crear algunos aventureros por defecto
            return crearAventurerosPorDefecto();
        }
        return cargados;
    }

    private List<Aventurero> crearAventurerosPorDefecto() {
        List<Aventurero> porDefecto = new ArrayList<>();
        porDefecto.add(new Aventurero("Héroe", "Guerrero", 1));
        porDefecto.add(new Aventurero("Yangus", "Ladrón", 2));
        porDefecto.add(new Aventurero("Jessica", "Maga", 3));
        porDefecto.add(new Aventurero("Angelo", "Sacerdote", 4));
        return porDefecto;
    }

    public void registrarAventurero(Aventurero aventurero) {
        aventurerosRegistrados.add(aventurero);
        sistemaTurnos.agregarAventurero(aventurero);
        persistencia.guardarAventureros(aventurerosRegistrados);
        System.out.println("Aventurero registrado: " + aventurero.getNombre());
    }

    public void atenderSiguiente() {
        if (sistemaTurnos.hayAventurerosEnEspera()) {
            Aventurero atendido = sistemaTurnos.atenderSiguiente();
            System.out.println("Atendiendo a: " + atendido.getNombre());
        } else {
            System.out.println("No hay aventureros en espera.");
        }
    }

    public void mostrarAventurerosRegistrados() {
        System.out.println("=== AVENTUREROS REGISTRADOS ===");
        for (int i = 0; i < aventurerosRegistrados.size(); i++) {
            Aventurero a = aventurerosRegistrados.get(i);
            System.out.println((i+1) + ". " + a.getNombre() +
                    " - " + a.getClase() +
                    " (Prioridad: " + a.getPrioridad() + ")");
        }
    }

    public void mostrarColaAtencion() {
        sistemaTurnos.mostrarCola();
    }
}