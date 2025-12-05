package principal;

import controlador.*;
import vista.*;
import modelo.*;
import persistencia.*;
import estructuras.*;
import java.util.*;

public class DragonQuestVIII {
    private BattleController battleController;
    private GremioController gremioController;
    private InventoryController inventoryController;
    private SistemaPersistencia persistencia;
    private ConsolaView vista;
    private List<Heroe> heroes;
    private List<Enemigo> enemigos;
    private boolean partidaEnCurso;

    public DragonQuestVIII() {
        inicializarSistema();
    }

    private void inicializarSistema() {
        // Inicializar componentes
        vista = new ConsolaView();
        battleController = new BattleController();
        gremioController = new GremioController();
        inventoryController = new InventoryController();
        persistencia = new SistemaPersistencia();

        // Cargar o crear héroes
        heroes = crearHeroesPorDefecto();
        enemigos = new ArrayList<>();

        partidaEnCurso = false;

        System.out.println("=== SISTEMA DRAGON QUEST VIII INICIALIZADO ===");
    }

    private List<Heroe> crearHeroesPorDefecto() {
        List<Heroe> listaHeroes = new ArrayList<>();

        // Crear héroes con estadísticas balanceadas
        listaHeroes.add(new Heroe("Héroe", 100, 50, 20, 15, 10));
        listaHeroes.add(new Heroe("Yangus", 120, 20, 25, 20, 8));
        listaHeroes.add(new Heroe("Jessica", 80, 80, 15, 10, 12));
        listaHeroes.add(new Heroe("Angelo", 90, 60, 18, 12, 11));

        // Configurar inventarios iniciales
        configurarInventariosIniciales(listaHeroes);

        return listaHeroes;
    }

    private void configurarInventariosIniciales(List<Heroe> heroes) {
        for (Heroe heroe : heroes) {
            try {
                heroe.getInventario().agregarObjeto("Poción de Curación", 2);
                heroe.getInventario().agregarObjeto("Antídoto", 1);
            } catch (Exception e) {
                System.err.println("Error configurando inventario: " + e.getMessage());
            }
        }
    }

    private List<Enemigo> crearEnemigosPorDefecto() {
        List<Enemigo> listaEnemigos = new ArrayList<>();

        // Enemigos normales
        listaEnemigos.add(new Enemigo("Slime", 50, 10, 10, 5, 5, "normal"));
        listaEnemigos.add(new Enemigo("Goblin", 70, 15, 15, 8, 6, "agresivo"));
        listaEnemigos.add(new Enemigo("Fantasma", 60, 30, 12, 6, 7, "defensivo"));

        // MiniJefe
        listaEnemigos.add(new MiniJefe("Dragón Negro", 150, 50, 25, 20, 9));

        return listaEnemigos;
    }

    public void ejecutar() {
        boolean ejecutando = true;

        while (ejecutando) {
            vista.limpiarPantalla();
            vista.mostrarMenuPrincipal();

            try {
                int opcion = vista.pedirEntero("");

                switch (opcion) {
                    case 1:
                        iniciarNuevaPartida();
                        break;
                    case 2:
                        cargarPartida();
                        break;
                    case 3:
                        iniciarBatalla();
                        break;
                    case 4:
                        menuGremio();
                        break;
                    case 5:
                        menuInventarios();
                        break;
                    case 6:
                        verHistorial();
                        break;
                    case 7:
                        guardarPartida();
                        break;
                    case 8:
                        ejecutando = false;
                        vista.mostrarMensaje("¡Gracias por jugar Dragon Quest VIII!");
                        break;
                    default:
                        vista.mostrarError("Opción no válida");
                }

                vista.pedirEntrada("\nPresione Enter para continuar...");

            } catch (Exception e) {
                vista.mostrarError("Error: " + e.getMessage());
            }
        }

        vista.cerrar();
    }

    private void iniciarNuevaPartida() {
        heroes = crearHeroesPorDefecto();
        partidaEnCurso = true;
        vista.mostrarMensaje("¡Nueva partida iniciada!");
        vista.mostrarMensaje("Héroes creados con inventarios básicos.");
    }

    private void cargarPartida() {
        EstadoPartida estado = persistencia.cargarEstadoPartida();
        if (estado != null) {
            heroes = estado.getHeroes();
            partidaEnCurso = true;
            vista.mostrarMensaje("Partida cargada: " + estado.getNombrePartida());
        }
    }

    private void guardarPartida() {
        if (partidaEnCurso) {
            EstadoPartida estado = new EstadoPartida("Partida Guardada", heroes);
            persistencia.guardarEstadoPartida(estado);
        } else {
            vista.mostrarError("No hay partida en curso para guardar");
        }
    }

    private void iniciarBatalla() {
        if (!partidaEnCurso) {
            vista.mostrarError("Debe iniciar una partida primero");
            return;
        }

        enemigos = crearEnemigosPorDefecto();
        battleController.iniciarBatalla(heroes, enemigos);

        boolean batallaEnCurso = true;
        Scanner scanner = new Scanner(System.in);

        while (batallaEnCurso && battleController.hayBatallaEnCurso()) {
            vista.limpiarPantalla();
            vista.mostrarMenuBatalla();

            try {
                int accion = vista.pedirEntero("");

                // Aquí manejarías la acción del jugador
                switch (accion) {
                    case 1: // Atacar
                        manejarAtaque();
                        break;
                    case 2: // Usar Habilidad
                        manejarHabilidad();
                        break;
                    case 3: // Usar Objeto
                        manejarObjeto();
                        break;
                    case 4: // Defender
                        vista.mostrarMensaje("Defendiendo...");
                        break;
                    case 5: // Huir
                        vista.mostrarMensaje("Huyendo de la batalla...");
                        batallaEnCurso = false;
                        break;
                    default:
                        vista.mostrarError("Acción no válida");
                }

                // Ejecutar turno solo si el jugador no huyó
                if (batallaEnCurso && accion != 5) {
                    try {
                        battleController.ejecutarTurno();
                    } catch (excepciones.BatallaNoIniciadaException e) {
                        vista.mostrarError("Error en batalla: " + e.getMessage());
                        batallaEnCurso = false;
                    }
                }

                if (accion != 5) {
                    vista.pedirEntrada("\nPresione Enter para continuar...");
                }

            } catch (Exception e) {
                vista.mostrarError("Error: " + e.getMessage());
            }
        }
    }

    private void manejarAtaque() {
        vista.mostrarMensaje("Seleccione enemigo para atacar:");
        for (int i = 0; i < enemigos.size(); i++) {
            Enemigo enemigo = enemigos.get(i);
            if (enemigo.estaVivo()) {
                vista.mostrarMensaje((i+1) + ". " + enemigo.getNombre() +
                        " (HP: " + enemigo.getHp() + "/" + enemigo.getHpMax() + ")");
            }
        }

        try {
            int seleccion = vista.pedirEntero("Número de enemigo") - 1;
            if (seleccion >= 0 && seleccion < enemigos.size()) {
                Enemigo objetivo = enemigos.get(seleccion);
                if (objetivo.estaVivo()) {
                    // Aquí implementarías la lógica de ataque
                    vista.mostrarMensaje("Atacando a " + objetivo.getNombre() + "...");
                } else {
                    vista.mostrarError("Enemigo ya está derrotado");
                }
            } else {
                vista.mostrarError("Selección inválida");
            }
        } catch (Exception e) {
            vista.mostrarError("Error: " + e.getMessage());
        }
    }

    private void manejarHabilidad() {
        vista.mostrarMensaje("Funcionalidad de habilidades en desarrollo...");
    }

    private void manejarObjeto() {
        vista.mostrarMensaje("Funcionalidad de objetos en desarrollo...");
    }

    private void menuGremio() {
        boolean enGremio = true;

        while (enGremio) {
            vista.limpiarPantalla();
            vista.mostrarMenuGremio();

            try {
                int opcion = vista.pedirEntero("");

                switch (opcion) {
                    case 1:
                        registrarNuevoAventurero();
                        break;
                    case 2:
                        gremioController.mostrarAventurerosRegistrados();
                        break;
                    case 3:
                        gremioController.mostrarColaAtencion();
                        break;
                    case 4:
                        gremioController.atenderSiguiente();
                        break;
                    case 5:
                        enGremio = false;
                        break;
                    default:
                        vista.mostrarError("Opción no válida");
                }

                if (opcion != 5) {
                    vista.pedirEntrada("\nPresione Enter para continuar...");
                }

            } catch (Exception e) {
                vista.mostrarError("Error: " + e.getMessage());
            }
        }
    }

    private void registrarNuevoAventurero() {
        String nombre = vista.pedirEntrada("Nombre del aventurero");
        String clase = vista.pedirEntrada("Clase del aventurero");
        int prioridad = vista.pedirEntero("Prioridad (1=alta, 5=baja)");

        modelo.Aventurero nuevo = new modelo.Aventurero(nombre, clase, prioridad);
        gremioController.registrarAventurero(nuevo);
    }

    private void menuInventarios() {
        boolean enInventarios = true;

        while (enInventarios) {
            vista.limpiarPantalla();
            vista.mostrarMenuInventario();

            try {
                int opcion = vista.pedirEntero("");

                switch (opcion) {
                    case 1:
                        verInventarioHeroe();
                        break;
                    case 2:
                        usarObjeto();
                        break;
                    case 3:
                        transferirObjeto();
                        break;
                    case 4:
                        enInventarios = false;
                        break;
                    default:
                        vista.mostrarError("Opción no válida");
                }

                if (opcion != 4) {
                    vista.pedirEntrada("\nPresione Enter para continuar...");
                }

            } catch (Exception e) {
                vista.mostrarError("Error: " + e.getMessage());
            }
        }
    }

    private void verInventarioHeroe() {
        vista.mostrarMensaje("Seleccione héroe:");
        for (int i = 0; i < heroes.size(); i++) {
            vista.mostrarMensaje((i+1) + ". " + heroes.get(i).getNombre());
        }

        try {
            int seleccion = vista.pedirEntero("Número de héroe") - 1;
            if (seleccion >= 0 && seleccion < heroes.size()) {
                inventoryController.mostrarInventario(heroes.get(seleccion));
            } else {
                vista.mostrarError("Selección inválida");
            }
        } catch (Exception e) {
            vista.mostrarError("Error: " + e.getMessage());
        }
    }

    private void usarObjeto() {
        vista.mostrarMensaje("Seleccione héroe:");
        for (int i = 0; i < heroes.size(); i++) {
            vista.mostrarMensaje((i+1) + ". " + heroes.get(i).getNombre());
        }

        try {
            int seleccionHeroe = vista.pedirEntero("Número de héroe") - 1;
            if (seleccionHeroe >= 0 && seleccionHeroe < heroes.size()) {
                Heroe heroe = heroes.get(seleccionHeroe);
                inventoryController.mostrarInventario(heroe);

                String objeto = vista.pedirEntrada("Nombre del objeto a usar");
                inventoryController.usarObjeto(heroe, objeto);
            } else {
                vista.mostrarError("Selección inválida");
            }
        } catch (Exception e) {
            vista.mostrarError("Error: " + e.getMessage());
        }
    }

    private void transferirObjeto() {
        vista.mostrarMensaje("Transferencia entre héroes:");

        // Seleccionar héroe origen
        vista.mostrarMensaje("Seleccione héroe ORIGEN:");
        for (int i = 0; i < heroes.size(); i++) {
            vista.mostrarMensaje((i+1) + ". " + heroes.get(i).getNombre());
        }
        int origenIndex = vista.pedirEntero("Número de héroe origen") - 1;

        // Seleccionar héroe destino
        vista.mostrarMensaje("Seleccione héroe DESTINO:");
        for (int i = 0; i < heroes.size(); i++) {
            vista.mostrarMensaje((i+1) + ". " + heroes.get(i).getNombre());
        }
        int destinoIndex = vista.pedirEntero("Número de héroe destino") - 1;

        if (origenIndex >= 0 && origenIndex < heroes.size() &&
                destinoIndex >= 0 && destinoIndex < heroes.size() &&
                origenIndex != destinoIndex) {

            Heroe origen = heroes.get(origenIndex);
            Heroe destino = heroes.get(destinoIndex);

            inventoryController.mostrarInventario(origen);
            String objeto = vista.pedirEntrada("Nombre del objeto a transferir");
            int cantidad = vista.pedirEntero("Cantidad a transferir");

            inventoryController.transferirObjeto(origen, destino, objeto, cantidad);
        } else {
            vista.mostrarError("Selección inválida");
        }
    }

    private void verHistorial() {
        battleController.mostrarHistorial();
    }

    public static void main(String[] args) {
        DragonQuestVIII juego = new DragonQuestVIII();
        juego.ejecutar();
    }
}