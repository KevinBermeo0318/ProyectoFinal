package estructuras;

import java.util.Stack;

public class SistemaDeshacer {
    private Stack<Memento> historialDeshacer;
    private Stack<Memento> historialRehacer;

    public SistemaDeshacer() {
        historialDeshacer = new Stack<>();
        historialRehacer = new Stack<>();
    }

    public void guardarEstado(Memento estado) {
        historialDeshacer.push(estado);
        historialRehacer.clear(); // Limpiar rehacer al hacer nueva acción
    }

    public Memento deshacer() {
        if (!historialDeshacer.isEmpty()) {
            Memento estado = historialDeshacer.pop();
            historialRehacer.push(estado);
            return estado;
        }
        return null;
    }

    public Memento rehacer() {
        if (!historialRehacer.isEmpty()) {
            Memento estado = historialRehacer.pop();
            historialDeshacer.push(estado);
            return estado;
        }
        return null;
    }

    public boolean sePuedeDeshacer() {
        return !historialDeshacer.isEmpty();
    }

    public boolean sePuedeRehacer() {
        return !historialRehacer.isEmpty();
    }

    public void limpiarHistorial() {
        historialDeshacer.clear();
        historialRehacer.clear();
    }
}

// Clase Memento para guardar estados
class Memento {
    private String estado;
    private String accion;

    public Memento(String estado, String accion) {
        this.estado = estado;
        this.accion = accion;
    }

    public String getEstado() {
        return estado;
    }

    public String getAccion() {
        return accion;
    }
}
