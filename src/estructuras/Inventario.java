package estructuras;

import excepciones.InventarioLlenoException;
import excepciones.ObjetoNoEncontradoException;
import java.util.HashMap;
import java.util.Map;

public class Inventario {
    private Map<String, Integer> objetos;
    private final int CAPACIDAD_MAXIMA = 5;

    public Inventario() {
        objetos = new HashMap<>();
    }

    public boolean agregarObjeto(String nombre, int cantidad) throws InventarioLlenoException {
        // Si no existe el objeto y el inventario está lleno
        if (!objetos.containsKey(nombre) && objetos.size() >= CAPACIDAD_MAXIMA) {
            throw new InventarioLlenoException("Inventario lleno. Capacidad máxima: " + CAPACIDAD_MAXIMA);
        }

        // Agregar objeto
        int cantidadActual = objetos.getOrDefault(nombre, 0);
        objetos.put(nombre, cantidadActual + cantidad);
        return true;
    }

    public boolean usarObjeto(String nombre) throws ObjetoNoEncontradoException {
        if (!objetos.containsKey(nombre) || objetos.get(nombre) <= 0) {
            throw new ObjetoNoEncontradoException("Objeto no disponible: " + nombre);
        }

        int cantidadActual = objetos.get(nombre);
        if (cantidadActual == 1) {
            objetos.remove(nombre);
        } else {
            objetos.put(nombre, cantidadActual - 1);
        }
        return true;
    }

    public Map<String, Integer> getObjetos() {
        return new HashMap<>(objetos);
    }

    public int getCantidadObjeto(String nombre) {
        return objetos.getOrDefault(nombre, 0);
    }

    public boolean contieneObjeto(String nombre) {
        return objetos.containsKey(nombre) && objetos.get(nombre) > 0;
    }

    public int getEspacioDisponible() {
        return CAPACIDAD_MAXIMA - objetos.size();
    }

    public boolean estaLleno() {
        return objetos.size() >= CAPACIDAD_MAXIMA;
    }

    public void limpiar() {
        objetos.clear();
    }

    public int getCapacidadMaxima() {
        return CAPACIDAD_MAXIMA;
    }
}
