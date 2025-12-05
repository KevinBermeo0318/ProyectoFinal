package estructuras;

import modelo.Aventurero;
import java.util.PriorityQueue;

public class SistemaTurnosGremio {
    private PriorityQueue<Aventurero> colaAtencion;

    public SistemaTurnosGremio() {
        // PriorityQueue ordena automáticamente por prioridad (menor número = mayor prioridad)
        colaAtencion = new PriorityQueue<>(
                (a1, a2) -> Integer.compare(a1.getPrioridad(), a2.getPrioridad())
        );
    }

    public void agregarAventurero(Aventurero aventurero) {
        colaAtencion.offer(aventurero);
    }

    public Aventurero atenderSiguiente() {
        return colaAtencion.poll();
    }

    public boolean hayAventurerosEnEspera() {
        return !colaAtencion.isEmpty();
    }

    public void mostrarCola() {
        if (colaAtencion.isEmpty()) {
            System.out.println("No hay aventureros en espera.");
            return;
        }

        System.out.println("=== AVENTUREROS EN ESPERA ===");
        int posicion = 1;
        // Crear copia para no modificar la cola original
        PriorityQueue<Aventurero> copia = new PriorityQueue<>(colaAtencion);

        while (!copia.isEmpty()) {
            Aventurero a = copia.poll();
            System.out.println(posicion + ". " + a.getNombre() +
                    " (Prioridad: " + a.getPrioridad() + ")");
            posicion++;
        }
    }

    public int getCantidadEnEspera() {
        return colaAtencion.size();
    }
}
