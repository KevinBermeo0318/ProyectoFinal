package persistencia;

import modelo.Aventurero;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SistemaPersistencia {
    private static final String ARCHIVO_BATALLAS = "datos/batallas_guardadas.txt";
    private static final String ARCHIVO_ESTADO = "datos/estado_partida.txt";
    private static final String ARCHIVO_AVENTUREROS = "datos/aventureros_registrados.txt";

    public SistemaPersistencia() {
        // Crear directorio de datos si no existe
        File directorio = new File("datos");
        if (!directorio.exists()) {
            directorio.mkdir();
        }
    }

    // Método para guardar resultado de batalla
    public void guardarBatalla(String resultado) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO_BATALLAS, true))) {
            String timestamp = java.time.LocalDateTime.now().toString();
            writer.write(timestamp + " | " + resultado);
            writer.newLine();
            System.out.println("Batalla guardada en el historial.");
        } catch (IOException e) {
            System.err.println("Error al guardar la batalla: " + e.getMessage());
        }
    }

    // Método para cargar historial de batallas
    public List<String> cargarHistorialBatallas() {
        List<String> historial = new ArrayList<>();
        File archivo = new File(ARCHIVO_BATALLAS);

        if (!archivo.exists()) {
            return historial; // Lista vacía si no existe el archivo
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                historial.add(linea);
            }
        } catch (IOException e) {
            System.err.println("Error al cargar historial: " + e.getMessage());
        }
        return historial;
    }

    // Método para guardar estado de partida (serialización)
    public void guardarEstadoPartida(EstadoPartida estado) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO_ESTADO))) {
            oos.writeObject(estado);
            System.out.println("Partida guardada exitosamente.");
        } catch (IOException e) {
            System.err.println("Error al guardar la partida: " + e.getMessage());
        }
    }

    // Método para cargar estado de partida
    public EstadoPartida cargarEstadoPartida() {
        File archivo = new File(ARCHIVO_ESTADO);

        if (!archivo.exists()) {
            System.out.println("No se encontró partida guardada.");
            return null;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            EstadoPartida estado = (EstadoPartida) ois.readObject();
            System.out.println("Partida cargada exitosamente.");
            return estado;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al cargar la partida: " + e.getMessage());
            return null;
        }
    }

    // Método para guardar lista de aventureros registrados
    public void guardarAventureros(List<Aventurero> aventureros) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO_AVENTUREROS))) {
            for (Aventurero a : aventureros) {
                writer.write(a.toCSV());
                writer.newLine();
            }
            System.out.println("Aventureros guardados exitosamente.");
        } catch (IOException e) {
            System.err.println("Error al guardar aventureros: " + e.getMessage());
        }
    }

    // Método para cargar lista de aventureros
    public List<Aventurero> cargarAventureros() {
        List<Aventurero> aventureros = new ArrayList<>();
        File archivo = new File(ARCHIVO_AVENTUREROS);

        if (!archivo.exists()) {
            return aventureros;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length >= 3) {
                    Aventurero a = new Aventurero(datos[0], datos[1], Integer.parseInt(datos[2]));
                    aventureros.add(a);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al cargar aventureros: " + e.getMessage());
        }
        return aventureros;
    }
}