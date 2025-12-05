package estructuras;

import java.util.LinkedList;
import java.util.Queue;

public class RegistroBatallas {
    private Queue<String> historialBatallas;
    private final int MAX_HISTORIAL = 10;

    public RegistroBatallas() {
        historialBatallas = new LinkedList<>();
    }

    public void agregarBatalla(String resultado) {
        // Si alcanzamos el máximo, eliminar el más antiguo
        if (historialBatallas.size() >= MAX_HISTORIAL) {
            historialBatallas.poll();
        }
        historialBatallas.offer(resultado);
    }

    public Queue<String> getHistorial() {
        // Devolver copia para no modificar el original
        return new LinkedList<>(historialBatallas);
    }

    public void mostrarHistorial() {
        if (historialBatallas.isEmpty()) {
            System.out.println("No hay batallas registradas.");
            return;
        }

        System.out.println("=== HISTORIAL DE BATALLAS (Últimas 10) ===");
        int contador = 1;
        for (String batalla : historialBatallas) {
            System.out.println(contador + ". " + batalla);
            contador++;
        }
    }

    public void limpiarHistorial() {
        historialBatallas.clear();
    }

    public int getTotalBatallas() {
        return historialBatallas.size();
    }
}
