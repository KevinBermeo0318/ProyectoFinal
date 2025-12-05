package controlador;

import modelo.Heroe;
import modelo.Objeto;
import estructuras.Inventario;
import excepciones.InventarioLlenoException;
import excepciones.ObjetoNoEncontradoException;
import java.util.Map;

public class InventoryController {

    public void agregarObjetoAInventario(Heroe heroe, String nombreObjeto, int cantidad) {
        Inventario inventario = heroe.getInventario();
        try {
            inventario.agregarObjeto(nombreObjeto, cantidad);
            System.out.println("Objeto agregado al inventario de " + heroe.getNombre());
        } catch (InventarioLlenoException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void usarObjeto(Heroe heroe, String nombreObjeto) {
        Inventario inventario = heroe.getInventario();
        try {
            if (inventario.usarObjeto(nombreObjeto)) {
                // Crear objeto y aplicar efecto
                Objeto objeto = crearObjeto(nombreObjeto);
                objeto.usar(heroe);
                System.out.println(heroe.getNombre() + " usó " + nombreObjeto);
            }
        } catch (ObjetoNoEncontradoException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private Objeto crearObjeto(String nombre) {
        switch(nombre) {
            case "Poción de Curación":
                return new Objeto(nombre, "Restaura 50 HP", "curacion");
            case "Éter":
                return new Objeto(nombre, "Restaura 30 MP", "restauracion_mp");
            case "Antídoto":
                return new Objeto(nombre, "Cura envenenamiento", "antidoto");
            case "Hoja de Despertar":
                return new Objeto(nombre, "Despierta personajes", "despertar");
            case "Piedra de Luz":
                return new Objeto(nombre, "Daño sagrado a todos", "daño_area");
            default:
                return new Objeto(nombre, "Objeto desconocido", "misc");
        }
    }

    public void mostrarInventario(Heroe heroe) {
        Inventario inventario = heroe.getInventario();
        Map<String, Integer> objetos = inventario.getObjetos();

        System.out.println("=== INVENTARIO DE " + heroe.getNombre().toUpperCase() + " ===");
        System.out.println("Espacio disponible: " + inventario.getEspacioDisponible() + "/5");

        if (objetos.isEmpty()) {
            System.out.println("Inventario vacío.");
        } else {
            for (Map.Entry<String, Integer> entry : objetos.entrySet()) {
                System.out.println("- " + entry.getKey() + " x" + entry.getValue());
            }
        }
    }

    public void transferirObjeto(Heroe origen, Heroe destino, String nombreObjeto, int cantidad) {
        try {
            // Verificar que el origen tenga el objeto
            if (!origen.getInventario().contieneObjeto(nombreObjeto)) {
                throw new ObjetoNoEncontradoException("Objeto no encontrado en inventario de origen");
            }

            // Verificar que el destino tenga espacio
            if (destino.getInventario().getEspacioDisponible() <= 0 &&
                    !destino.getInventario().contieneObjeto(nombreObjeto)) {
                throw new InventarioLlenoException("Inventario de destino lleno");
            }

            // Usar objeto del origen
            for (int i = 0; i < cantidad; i++) {
                origen.getInventario().usarObjeto(nombreObjeto);
            }

            // Agregar al destino
            destino.getInventario().agregarObjeto(nombreObjeto, cantidad);

            System.out.println("Transferencia completada: " + cantidad + " " + nombreObjeto +
                    " de " + origen.getNombre() + " a " + destino.getNombre());

        } catch (InventarioLlenoException | ObjetoNoEncontradoException e) {
            System.err.println("Error en transferencia: " + e.getMessage());
        }
    }
}