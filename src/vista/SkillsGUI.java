package vista;

import javax.swing.*;
import java.awt.*;

public class SkillsGUI extends JDialog {
    private final JButton[] skillButtons = new JButton[6];

    public SkillsGUI(JFrame parent) {
        super(parent, "Habilidades - Dragon Quest VIII", true);
        setSize(500, 400);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        initComponents();
    }

    private void initComponents() {
        // Título
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(30, 144, 255));
        JLabel titleLabel = new JLabel("🔮 HABILIDADES ESPECIALES");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        // Panel de habilidades
        JPanel skillsPanel = new JPanel(new GridLayout(2, 3, 15, 15));
        skillsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        skillsPanel.setBackground(new Color(245, 245, 220));

        String[] skillNames = {"Curación", "Fuego", "Hielo", "Rayo", "Defensa", "Ataque Doble"};
        String[] skillIcons = {"❤️", "🔥", "❄️", "⚡", "🛡️", "⚔️"};
        Color[] skillColors = {Color.RED, Color.ORANGE, Color.CYAN, Color.YELLOW, Color.GRAY, Color.MAGENTA};

        for (int i = 0; i < 6; i++) {
            // Crear copias finales de las variables para usar en la lambda
            final int index = i;
            final String skillName = skillNames[i];

            skillButtons[i] = new JButton("<html><center>" + skillIcons[i] + "<br>" + skillNames[i] +
                    "<br><small>MP: " + (i * 5 + 10) + "</small></center></html>");
            skillButtons[i].setFont(new Font("Serif", Font.BOLD, 14));
            skillButtons[i].setBackground(skillColors[i]);
            skillButtons[i].setForeground(Color.WHITE);
            skillButtons[i].setFocusPainted(false);

            // Usar la copia final en la lambda
            skillButtons[i].addActionListener(e -> useSkill(skillName));

            // Otra opción: usar el array directamente
            // skillButtons[i].addActionListener(e -> useSkill(skillNames[index]));

            skillsPanel.add(skillButtons[i]);
        }

        add(skillsPanel, BorderLayout.CENTER);

        // Panel inferior
        JPanel bottomPanel = new JPanel(new BorderLayout());
        JLabel descLabel = new JLabel("Selecciona una habilidad para usarla en batalla");
        descLabel.setFont(new Font("Serif", Font.ITALIC, 12));
        descLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        bottomPanel.add(descLabel, BorderLayout.CENTER);

        JButton closeButton = new JButton("Cerrar");
        closeButton.addActionListener(e -> dispose());
        bottomPanel.add(closeButton, BorderLayout.EAST);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void useSkill(String skillName) {
        JOptionPane.showMessageDialog(this,
                "¡Usaste " + skillName + "!\n\n" +
                        "La habilidad se está ejecutando en batalla...",
                "Habilidad Activada",
                JOptionPane.INFORMATION_MESSAGE);
    }
}