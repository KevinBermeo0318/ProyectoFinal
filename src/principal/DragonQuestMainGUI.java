package principal;

import vista.DragonQuestGUI;
import javax.swing.*;

public class DragonQuestMainGUI {
    public static void main(String[] args) {
        // Ejecutar en el Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            try {
                // Opcional: establecer un look and feel específico
                UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            } catch (Exception e) {
                // Usar el look and feel por defecto
            }

            // Crear y mostrar la GUI
            DragonQuestGUI gui = new DragonQuestGUI();

            // Configurar ejemplo inicial
            gui.updateHeroStats(0, 100, 100, 50, 50);
            gui.updateHeroStats(1, 120, 120, 20, 20);
            gui.updateHeroStats(2, 80, 80, 80, 80);
            gui.updateHeroStats(3, 90, 90, 60, 60);

            gui.updateEnemyStats(0, 50, 50);
            gui.updateEnemyStats(1, 70, 70);
            gui.updateEnemyStats(2, 60, 60);
            gui.updateEnemyStats(3, 150, 150);

            gui.addDialogue("¡Bienvenido a Dragon Quest VIII!\n" +
                    "Usa los botones para controlar a tus héroes.\n" +
                    "¡Buena suerte en tu aventura!");
        });
    }
}