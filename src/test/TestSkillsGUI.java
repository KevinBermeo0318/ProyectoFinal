package test;

import vista.SkillsGUI;
import javax.swing.*;

public class TestSkillsGUI {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Test");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(300, 200);
            frame.setVisible(true);

            // Crear y mostrar SkillsGUI
            SkillsGUI skills = new SkillsGUI(frame);
            skills.setVisible(true);
        });
    }
}