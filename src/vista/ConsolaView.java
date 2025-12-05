package vista;

import modelo.*;
import java.util.Scanner;

public class ConsolaView {
    private Scanner scanner;

    public ConsolaView() {
        this.scanner = new Scanner(System.in);
    }

    public void mostrarMenuPrincipal() {
        System.out.println("\n=== DRAGON QUEST VIII - MENÚ PRINCIPAL ===");
        System.out.println("1. Nueva Partida");
        System.out.println("2. Cargar Partida");
        System.out.println("3. Iniciar Batalla");
        System.out.println("4. Sistema del Gremio");
        System.out.println("5. Gestionar Inventarios");
        System.out.println("6. Ver Historial");
        System.out.println("7. Guardar Partida");
        System.out.println("8. Salir");
        System.out.print("Seleccione una opción: ");
    }

    public void mostrarMenuBatalla() {
        System.out.println("\n=== MENÚ DE BATALLA ===");
        System.out.println("1. Atacar");
        System.out.println("2. Usar Habilidad");
        System.out.println("3. Usar Objeto");
        System.out.println("4. Defender");
        System.out.println("5. Huir");
        System.out.print("Seleccione una acción: ");
    }

    public void mostrarMenuGremio() {
        System.out.println("\n=== SISTEMA DEL GREMIO ===");
        System.out.println("1. Registrar nuevo aventurero");
        System.out.println("2. Ver aventureros registrados");
        System.out.println("3. Ver cola de atención");
        System.out.println("4. Atender siguiente aventurero");
        System.out.println("5. Volver al menú principal");
        System.out.print("Seleccione una opción: ");
    }

    public void mostrarMenuInventario() {
        System.out.println("\n=== GESTIÓN DE INVENTARIOS ===");
        System.out.println("1. Ver inventario de héroe");
        System.out.println("2. Usar objeto");
        System.out.println("3. Transferir objeto entre héroes");
        System.out.println("4. Volver al menú principal");
        System.out.print("Seleccione una opción: ");
    }

    public void mostrarEstadoPersonaje(Personaje personaje) {
        System.out.println("\n=== " + personaje.getNombre().toUpperCase() + " ===");
        System.out.println("HP: " + personaje.getHp() + "/" + personaje.getHpMax());
        System.out.println("MP: " + personaje.getMp() + "/" + personaje.getMpMax());
        System.out.println("ATQ: " + personaje.getAtaque() + " | DEF: " + personaje.getDefensa());
        System.out.println("VEL: " + personaje.getVelocidad() + " | VIVO: " + personaje.estaVivo());
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public void mostrarError(String error) {
        System.err.println("ERROR: " + error);
    }

    public String pedirEntrada(String mensaje) {
        System.out.print(mensaje + ": ");
        return scanner.nextLine();
    }

    public int pedirEntero(String mensaje) {
        System.out.print(mensaje + ": ");
        while (!scanner.hasNextInt()) {
            System.out.print("Por favor, ingrese un número válido: ");
            scanner.next();
        }
        int valor = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer
        return valor;
    }

    public void limpiarPantalla() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            // Si falla, simplemente imprimir líneas vacías
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }

    public void cerrar() {
        scanner.close();
    }
}