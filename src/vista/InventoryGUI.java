package vista;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class InventoryGUI extends JDialog {
    private JList<String> itemList;
    private DefaultListModel<String> listModel;
    private JLabel descriptionLabel;

    public InventoryGUI(JFrame parent) {
        super(parent, "Inventario - Dragon Quest VIII", true);
        setSize(400, 500);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        initComponents();
    }

    private void initComponents() {
        // Panel superior con título
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(139, 69, 19));
        JLabel titleLabel = new JLabel("🎒 INVENTARIO");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        // Panel central con lista de objetos
        listModel = new DefaultListModel<>();
        itemList = new JList<>(listModel);
        itemList.setFont(new Font("Monospaced", Font.PLAIN, 14));
        itemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        itemList.setBackground(new Color(245, 245, 220));

        JScrollPane scrollPane = new JScrollPane(itemList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Objetos"));
        add(scrollPane, BorderLayout.CENTER);

        // Panel de descripción
        JPanel descPanel = new JPanel(new BorderLayout());
        descPanel.setBorder(BorderFactory.createTitledBorder("Descripción"));

        descriptionLabel = new JLabel("Selecciona un objeto para ver su descripción");
        descriptionLabel.setFont(new Font("Serif", Font.PLAIN, 12));
        descriptionLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        descPanel.add(descriptionLabel, BorderLayout.CENTER);

        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton useButton = new JButton("Usar");
        useButton.setBackground(new Color(50, 205, 50));
        useButton.setForeground(Color.WHITE);
        useButton.addActionListener(e -> useSelectedItem());

        JButton closeButton = new JButton("Cerrar");
        closeButton.setBackground(new Color(178, 34, 34));
        closeButton.setForeground(Color.WHITE);
        closeButton.addActionListener(e -> dispose());

        buttonPanel.add(useButton);
        buttonPanel.add(closeButton);

        descPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(descPanel, BorderLayout.SOUTH);

        // Listener para selección de items
        itemList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                updateDescription();
            }
        });
    }

    public void loadInventory(Map<String, Integer> items) {
        listModel.clear();
        for (Map.Entry<String, Integer> entry : items.entrySet()) {
            listModel.addElement(String.format("%-20s x%d", entry.getKey(), entry.getValue()));
        }
    }

    private void updateDescription() {
        String selected = itemList.getSelectedValue();
        if (selected != null) {
            String itemName = selected.split("x")[0].trim();
            String description = getItemDescription(itemName);
            descriptionLabel.setText("<html><b>" + itemName + "</b><br>" + description + "</html>");
        }
    }

    private String getItemDescription(String itemName) {
        switch(itemName) {
            case "Poción de Curación": return "Restaura 50 HP a un aliado";
            case "Éter": return "Restaura 30 MP a un aliado";
            case "Antídoto": return "Cura el estado de envenenamiento";
            case "Hoja de Despertar": return "Despierta a un personaje dormido";
            case "Piedra de Luz": return "Inflige daño sagrado a todos los enemigos";
            default: return "Objeto misterioso";
        }
    }

    private void useSelectedItem() {
        String selected = itemList.getSelectedValue();
        if (selected != null) {
            String itemName = selected.split("x")[0].trim();
            JOptionPane.showMessageDialog(this,
                    "Usaste: " + itemName + "\n\n¡Efecto aplicado!",
                    "Objeto Usado",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
}