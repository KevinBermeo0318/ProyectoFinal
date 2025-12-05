package vista;

import javax.swing.*;
import java.awt.*;

public class GUIView extends JFrame {
    private JTextArea areaTexto;
    private JPanel panelAcciones;
    private JPanel panelEstado;

    public GUIView() {
        configurarVentana();
        inicializarComponentes();
    }

    private void configurarVentana() {
        setTitle("Dragon Quest VIII");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Look and feel personalizado
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void inicializarComponentes() {
        // Área de texto principal
        areaTexto = new JTextArea();
        areaTexto.setEditable(false);
        areaTexto.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(areaTexto);
        add(scrollPane, BorderLayout.CENTER);

        // Panel de acciones (botones)
        panelAcciones = new JPanel(new GridLayout(2, 3));
        agregarBotonesAcciones();
        add(panelAcciones, BorderLayout.SOUTH);

        // Panel de estado (estadísticas)
        panelEstado = new JPanel(new GridLayout(4, 2));
        inicializarPanelEstado();
        add(panelEstado, BorderLayout.EAST);
    }

    private void agregarBotonesAcciones() {
        String[] acciones = {"Atacar", "Habilidad", "Objeto", "Defender", "Huir", "Guardar"};

        for (String accion : acciones) {
            JButton boton = new JButton(accion);
            boton.addActionListener(e -> manejarAccion(accion));
            panelAcciones.add(boton);
        }
    }

    private void inicializarPanelEstado() {
        panelEstado.add(new JLabel("Héroe:"));
        panelEstado.add(new JLabel("100/100 HP"));

        panelEstado.add(new JLabel("Yangus:"));
        panelEstado.add(new JLabel("80/80 HP"));

        panelEstado.add(new JLabel("Jessica:"));
        panelEstado.add(new JLabel("60/60 HP"));

        panelEstado.add(new JLabel("Angelo:"));
        panelEstado.add(new JLabel("70/70 HP"));
    }

    private void manejarAccion(String accion) {
        areaTexto.append("Acción seleccionada: " + accion + "\n");
    }

    public void mostrarMensaje(String mensaje) {
        areaTexto.append(mensaje + "\n");
    }

    public void actualizarEstado(String nombre, String estado) {
        // Lógica para actualizar panel de estado
    }
}