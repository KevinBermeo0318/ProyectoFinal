package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;  // AÑADIR ESTE IMPORT
import javax.sound.sampled.*;
import java.io.*;

public class DragonQuestGUI extends JFrame {
    // Paneles principales
    private JPanel mainPanel;
    private JPanel battlePanel;
    private JPanel menuPanel;
    private JPanel statsPanel;
    private JPanel dialoguePanel;

    // Componentes de batalla
    private JLabel[] heroLabels = new JLabel[4];
    private JLabel[] enemyLabels = new JLabel[4];
    private JProgressBar[] heroHpBars = new JProgressBar[4];
    private JProgressBar[] heroMpBars = new JProgressBar[4];
    private JProgressBar[] enemyHpBars = new JProgressBar[4];

    // Botones de acción
    private JButton attackButton;
    private JButton skillButton;
    private JButton itemButton;
    private JButton defendButton;
    private JButton fleeButton;

    // Área de texto para diálogos
    private JTextArea dialogueArea;

    // Imágenes
    private ImageIcon heroIcon;
    private ImageIcon enemyIcon;
    private ImageIcon backgroundImage;

    // Colores estilo Dragon Quest
    private final Color DQ_BROWN = new Color(139, 69, 19);
    private final Color DQ_GOLD = new Color(255, 215, 0);
    private final Color DQ_RED = new Color(178, 34, 34);
    private final Color DQ_BLUE = new Color(30, 144, 255);
    private final Color DQ_GREEN = new Color(50, 205, 50);
    private final Color DQ_BEIGE = new Color(245, 245, 220);

    public DragonQuestGUI() {
        setTitle("Dragon Quest VIII - La Maldición del Rey");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1024, 768);
        setResizable(false);
        setLocationRelativeTo(null);

        // Cargar imágenes
        loadImages();

        // Configurar look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        initComponents();
        setupBattlePanel();

        // Reproducir música de batalla (opcional)
        // playBattleMusic();

        setVisible(true);
    }

    private void loadImages() {
        try {
            // Iconos básicos (puedes reemplazar con tus propias imágenes)
            heroIcon = createIcon("/images/hero.png", 64, 64);
            enemyIcon = createIcon("/images/enemy.png", 64, 64);
            backgroundImage = createIcon("/images/background.jpg", 1024, 768);
        } catch (Exception e) {
            // Si no hay imágenes, usar íconos por defecto
            System.out.println("No se encontraron imágenes, usando íconos por defecto");
            heroIcon = createDefaultIcon(Color.BLUE, 64, 64, "H");
            enemyIcon = createDefaultIcon(Color.RED, 64, 64, "E");
        }
    }

    private ImageIcon createIcon(String path, int width, int height) {
        try {
            // Intentar cargar desde recursos
            InputStream is = getClass().getResourceAsStream(path);
            if (is != null) {
                ImageIcon original = new ImageIcon(javax.imageio.ImageIO.read(is));
                Image scaled = original.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
                return new ImageIcon(scaled);
            }
        } catch (Exception e) {
            System.out.println("No se pudo cargar imagen: " + path);
        }
        return null;
    }

    private ImageIcon createDefaultIcon(Color color, int width, int height, String text) {
        // CREAR BufferedImage
        java.awt.image.BufferedImage image = new java.awt.image.BufferedImage(width, height,
                java.awt.image.BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();

        // Configurar renderizado de alta calidad
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // Fondo circular
        g2d.setColor(color);
        g2d.fillOval(2, 2, width - 4, height - 4);

        // Borde
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(3));
        g2d.drawOval(2, 2, width - 4, height - 4);

        // Sombra interior
        g2d.setColor(new Color(0, 0, 0, 50));
        g2d.drawOval(3, 3, width - 6, height - 6);

        // Texto
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Serif", Font.BOLD, 24));
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getHeight();
        g2d.drawString(text, (width - textWidth) / 2, (height + textHeight) / 2 - 5);

        // Sombra del texto
        g2d.setColor(new Color(0, 0, 0, 100));
        g2d.drawString(text, (width - textWidth) / 2 + 1, (height + textHeight) / 2 - 4);

        g2d.dispose();
        return new ImageIcon(image);
    }

    private void initComponents() {
        mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                if (backgroundImage != null) {
                    g2d.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
                } else {
                    // Fondo degradado estilo Dragon Quest
                    GradientPaint gradient = new GradientPaint(0, 0, new Color(0, 0, 100),
                            getWidth(), getHeight(), new Color(0, 0, 50));
                    g2d.setPaint(gradient);
                    g2d.fillRect(0, 0, getWidth(), getHeight());

                    // Patrón de estrellas
                    g2d.setColor(new Color(255, 255, 255, 100));
                    for (int i = 0; i < 50; i++) {
                        int x = (int)(Math.random() * getWidth());
                        int y = (int)(Math.random() * getHeight());
                        int size = (int)(Math.random() * 3) + 1;
                        g2d.fillOval(x, y, size, size);
                    }
                }
            }
        };

        // Configurar paneles
        setupBattlePanel();
        setupStatsPanel();
        setupDialoguePanel();
        setupMenuPanel();

        // Agregar paneles al main panel
        mainPanel.add(battlePanel, BorderLayout.CENTER);
        mainPanel.add(statsPanel, BorderLayout.EAST);
        mainPanel.add(dialoguePanel, BorderLayout.SOUTH);
        mainPanel.add(menuPanel, BorderLayout.WEST);

        add(mainPanel);
    }

    private void setupBattlePanel() {
        battlePanel = new JPanel(new GridBagLayout());
        battlePanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Panel para héroes (lado izquierdo)
        JPanel heroesPanel = new JPanel(new GridLayout(4, 1, 10, 20));
        heroesPanel.setOpaque(false);
        heroesPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(DQ_GOLD, 3),
                "HÉROES",
                javax.swing.border.TitledBorder.CENTER,
                javax.swing.border.TitledBorder.TOP,
                new Font("Serif", Font.BOLD, 16),
                DQ_GOLD
        ));

        // Crear héroes
        String[] heroNames = {"HÉROE", "YANGUS", "JESSICA", "ANGELO"};
        Color[] heroColors = {DQ_BLUE, DQ_RED, Color.PINK, DQ_GREEN};

        for (int i = 0; i < 4; i++) {
            JPanel heroPanel = new JPanel(new BorderLayout(5, 5));
            heroPanel.setOpaque(false);

            // Icono del héroe
            JLabel iconLabel = new JLabel(createDefaultIcon(heroColors[i], 48, 48, String.valueOf(i + 1)));
            heroLabels[i] = new JLabel(heroNames[i]);
            heroLabels[i].setFont(new Font("Serif", Font.BOLD, 14));
            heroLabels[i].setForeground(Color.WHITE);

            // Barras de HP y MP
            heroHpBars[i] = new JProgressBar(0, 100);
            heroHpBars[i].setValue(100);
            heroHpBars[i].setForeground(Color.RED);
            heroHpBars[i].setBackground(new Color(100, 0, 0));
            heroHpBars[i].setStringPainted(true);
            heroHpBars[i].setString("HP: 100/100");

            heroMpBars[i] = new JProgressBar(0, 100);
            heroMpBars[i].setValue(100);
            heroMpBars[i].setForeground(Color.BLUE);
            heroMpBars[i].setBackground(new Color(0, 0, 100));
            heroMpBars[i].setStringPainted(true);
            heroMpBars[i].setString("MP: 50/50");

            JPanel barsPanel = new JPanel(new GridLayout(2, 1, 2, 2));
            barsPanel.setOpaque(false);
            barsPanel.add(heroHpBars[i]);
            barsPanel.add(heroMpBars[i]);

            JPanel infoPanel = new JPanel(new BorderLayout());
            infoPanel.setOpaque(false);
            infoPanel.add(heroLabels[i], BorderLayout.NORTH);
            infoPanel.add(barsPanel, BorderLayout.CENTER);

            heroPanel.add(iconLabel, BorderLayout.WEST);
            heroPanel.add(infoPanel, BorderLayout.CENTER);

            heroesPanel.add(heroPanel);
        }

        // Panel para enemigos (lado derecho)
        JPanel enemiesPanel = new JPanel(new GridLayout(4, 1, 10, 20));
        enemiesPanel.setOpaque(false);
        enemiesPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.RED, 3),
                "ENEMIGOS",
                javax.swing.border.TitledBorder.CENTER,
                javax.swing.border.TitledBorder.TOP,
                new Font("Serif", Font.BOLD, 16),
                Color.RED
        ));

        // Crear enemigos
        String[] enemyNames = {"SLIME", "GOBLIN", "FANTASMA", "DRAGÓN NEGRO"};
        Color[] enemyColors = {Color.GREEN, DQ_BROWN, Color.GRAY, Color.BLACK};

        for (int i = 0; i < 4; i++) {
            JPanel enemyPanel = new JPanel(new BorderLayout(5, 5));
            enemyPanel.setOpaque(false);

            // Icono del enemigo
            JLabel iconLabel = new JLabel(createDefaultIcon(enemyColors[i], 48, 48, "E"));
            enemyLabels[i] = new JLabel(enemyNames[i]);
            enemyLabels[i].setFont(new Font("Serif", Font.BOLD, 14));
            enemyLabels[i].setForeground(Color.WHITE);

            // Barra de HP
            enemyHpBars[i] = new JProgressBar(0, 100);
            enemyHpBars[i].setValue(100);
            enemyHpBars[i].setForeground(Color.RED);
            enemyHpBars[i].setBackground(new Color(100, 0, 0));
            enemyHpBars[i].setStringPainted(true);

            enemyPanel.add(iconLabel, BorderLayout.EAST);

            JPanel infoPanel = new JPanel(new BorderLayout());
            infoPanel.setOpaque(false);
            infoPanel.add(enemyLabels[i], BorderLayout.NORTH);
            infoPanel.add(enemyHpBars[i], BorderLayout.CENTER);

            enemyPanel.add(infoPanel, BorderLayout.CENTER);

            enemiesPanel.add(enemyPanel);
        }

        // Agregar paneles al battle panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.4;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        battlePanel.add(heroesPanel, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0.4;
        battlePanel.add(enemiesPanel, gbc);

        // Panel central para mensajes de batalla
        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        JLabel vsLabel = new JLabel("⚔️ VS ⚔️");
        vsLabel.setFont(new Font("Serif", Font.BOLD, 36));
        vsLabel.setForeground(DQ_GOLD);
        vsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Efecto de chispas
        JLabel sparkLabel = new JLabel("✨ ✨ ✨");
        sparkLabel.setFont(new Font("Serif", Font.PLAIN, 24));
        sparkLabel.setForeground(Color.YELLOW);
        sparkLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(vsLabel);
        centerPanel.add(sparkLabel);
        centerPanel.add(Box.createVerticalGlue());

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.2;
        battlePanel.add(centerPanel, gbc);
    }

    private void setupStatsPanel() {
        statsPanel = new JPanel(new GridLayout(5, 1, 5, 10));
        statsPanel.setPreferredSize(new Dimension(200, 0));
        statsPanel.setBackground(new Color(0, 0, 0, 200));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Título
        JLabel titleLabel = new JLabel("ESTADÍSTICAS");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 18));
        titleLabel.setForeground(DQ_GOLD);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statsPanel.add(titleLabel);

        // Estadísticas de batalla
        String[] stats = {
                "Turno: 1",
                "Enemigos vivos: 4",
                "Héroes vivos: 4",
                "Estado: En batalla"
        };

        for (String stat : stats) {
            JLabel statLabel = new JLabel(stat);
            statLabel.setFont(new Font("Monospaced", Font.PLAIN, 12));
            statLabel.setForeground(Color.WHITE);
            statsPanel.add(statLabel);
        }
    }

    private void setupDialoguePanel() {
        dialoguePanel = new JPanel(new BorderLayout());
        dialoguePanel.setPreferredSize(new Dimension(0, 150));
        dialoguePanel.setBackground(new Color(0, 0, 0, 220));
        dialoguePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(DQ_GOLD, 2),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        dialogueArea = new JTextArea();
        dialogueArea.setEditable(false);
        dialogueArea.setLineWrap(true);
        dialogueArea.setWrapStyleWord(true);
        dialogueArea.setFont(new Font("Serif", Font.PLAIN, 14));
        dialogueArea.setForeground(Color.WHITE);
        dialogueArea.setBackground(new Color(0, 0, 0, 0));
        dialogueArea.setText("¡Una batalla épica comienza!\n" +
                "Los monstruos amenazan el reino de Trodain.\n" +
                "¡Héroes, defiendan nuestro hogar!");

        JScrollPane scrollPane = new JScrollPane(dialogueArea);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        dialoguePanel.add(scrollPane, BorderLayout.CENTER);

        // Botón para avanzar diálogo
        JButton nextButton = new JButton("▶ Siguiente");
        nextButton.setFont(new Font("Serif", Font.BOLD, 12));
        nextButton.setBackground(DQ_BROWN);
        nextButton.setForeground(Color.WHITE);
        nextButton.setFocusPainted(false);
        nextButton.addActionListener(e -> advanceDialogue());

        dialoguePanel.add(nextButton, BorderLayout.EAST);
    }

    private void setupMenuPanel() {
        menuPanel = new JPanel(new GridLayout(6, 1, 5, 10));
        menuPanel.setPreferredSize(new Dimension(150, 0));
        menuPanel.setBackground(new Color(0, 0, 0, 200));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Título del menú
        JLabel menuTitle = new JLabel("MENÚ");
        menuTitle.setFont(new Font("Serif", Font.BOLD, 18));
        menuTitle.setForeground(DQ_GOLD);
        menuTitle.setHorizontalAlignment(SwingConstants.CENTER);
        menuPanel.add(menuTitle);

        // Crear botones de acción
        attackButton = createMenuButton("⚔️ ATACAR", DQ_RED);
        skillButton = createMenuButton("🔮 HABILIDAD", DQ_BLUE);
        itemButton = createMenuButton("🎒 OBJETO", DQ_GREEN);
        defendButton = createMenuButton("🛡️ DEFENDER", DQ_BROWN);
        fleeButton = createMenuButton("🏃 HUIR", Color.GRAY);

        // Agregar botones al panel
        menuPanel.add(attackButton);
        menuPanel.add(skillButton);
        menuPanel.add(itemButton);
        menuPanel.add(defendButton);
        menuPanel.add(fleeButton);

        // Agregar listeners a los botones
        attackButton.addActionListener(e -> performAction("attack"));
        skillButton.addActionListener(e -> performAction("skill"));
        itemButton.addActionListener(e -> performAction("item"));
        defendButton.addActionListener(e -> performAction("defend"));
        fleeButton.addActionListener(e -> performAction("flee"));

        // Botón de inventario
        JButton inventoryButton = createMenuButton("📦 INVENTARIO", new Color(139, 69, 19));
        inventoryButton.addActionListener(e -> openInventory());
        menuPanel.add(inventoryButton);
    }

    private JButton createMenuButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Serif", Font.BOLD, 12));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2),
                BorderFactory.createEmptyBorder(10, 5, 10, 5)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Efecto hover
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(color.brighter());
                button.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.YELLOW, 2),
                        BorderFactory.createEmptyBorder(10, 5, 10, 5)
                ));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(color);
                button.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.BLACK, 2),
                        BorderFactory.createEmptyBorder(10, 5, 10, 5)
                ));
            }
        });

        return button;
    }

    private void performAction(String action) {
        String message = "";

        switch (action) {
            case "attack":
                message = "¡El Héroe ataca al enemigo más cercano!";
                updateEnemyHP(0, -20); // Ejemplo: daño a primer enemigo
                break;
            case "skill":
                message = "¡Jessica usa Hechizo de Fuego!";
                updateHeroMP(2, -15); // Consume MP
                updateEnemyHP(1, -30); // Daño a segundo enemigo
                break;
            case "item":
                message = "¡Usas una Poción de Curación!";
                updateHeroHP(0, 30); // Cura al primer héroe
                break;
            case "defend":
                message = "¡Yangus se defiende!";
                // Efecto visual de defensa
                heroLabels[1].setForeground(Color.YELLOW);
                Timer timer = new Timer(1000, ev -> heroLabels[1].setForeground(Color.WHITE));
                timer.setRepeats(false);
                timer.start();
                break;
            case "flee":
                message = "¡Intentas huir de la batalla!";
                int chance = (int)(Math.random() * 100);
                if (chance > 50) {
                    message += "\n¡Logras escapar!";
                    dialogueArea.setText(message + "\n\nPresiona ESC para salir.");
                } else {
                    message += "\n¡Los enemigos te bloquean el paso!";
                }
                break;
        }

        dialogueArea.setText(message + "\n\n" + dialogueArea.getText());

        // Simular ataque enemigo después de acción del jugador
        if (!action.equals("flee")) {
            simulateEnemyAttack();
        }
    }

    private void openInventory() {
        // Aquí podrías abrir una ventana de inventario
        JOptionPane.showMessageDialog(this,
                "Inventario:\n" +
                        "- Poción de Curación x3\n" +
                        "- Éter x2\n" +
                        "- Antídoto x1\n" +
                        "- Hoja de Despertar x1",
                "Inventario",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void updateHeroHP(int heroIndex, int change) {
        int current = heroHpBars[heroIndex].getValue();
        int newValue = Math.max(0, Math.min(100, current + change));
        heroHpBars[heroIndex].setValue(newValue);
        heroHpBars[heroIndex].setString("HP: " + newValue + "/100");

        // Efecto visual
        if (change > 0) {
            heroLabels[heroIndex].setForeground(Color.GREEN);
            Timer timer = new Timer(500, ev -> heroLabels[heroIndex].setForeground(Color.WHITE));
            timer.setRepeats(false);
            timer.start();
        } else if (change < 0) {
            heroLabels[heroIndex].setForeground(Color.RED);
            Timer timer = new Timer(500, ev -> heroLabels[heroIndex].setForeground(Color.WHITE));
            timer.setRepeats(false);
            timer.start();
        }
    }

    private void updateHeroMP(int heroIndex, int change) {
        int current = heroMpBars[heroIndex].getValue();
        int newValue = Math.max(0, Math.min(100, current + change));
        heroMpBars[heroIndex].setValue(newValue);
        heroMpBars[heroIndex].setString("MP: " + newValue + "/100");
    }

    private void updateEnemyHP(int enemyIndex, int change) {
        int current = enemyHpBars[enemyIndex].getValue();
        int newValue = Math.max(0, Math.min(100, current + change));
        enemyHpBars[enemyIndex].setValue(newValue);

        // Efecto visual de daño
        if (change < 0) {
            enemyLabels[enemyIndex].setForeground(Color.RED);
            Timer timer = new Timer(300, ev -> enemyLabels[enemyIndex].setForeground(Color.WHITE));
            timer.setRepeats(false);
            timer.start();
        }

        if (newValue == 0) {
            enemyLabels[enemyIndex].setForeground(Color.GRAY);
            enemyLabels[enemyIndex].setText(enemyLabels[enemyIndex].getText() + " (Derrotado)");
        }
    }

    private void simulateEnemyAttack() {
        // Encontrar un héroe vivo para atacar
        for (int i = 0; i < 4; i++) {
            if (heroHpBars[i].getValue() > 0) {
                int damage = (int)(Math.random() * 15) + 10;
                updateHeroHP(i, -damage);

                String enemyName = enemyLabels[0].getText().split(" ")[0];
                dialogueArea.setText(enemyName + " ataca a " + heroLabels[i].getText() +
                        " por " + damage + " puntos de daño!\n\n" +
                        dialogueArea.getText());
                break;
            }
        }

        // Verificar si algún héroe murió
        for (int i = 0; i < 4; i++) {
            if (heroHpBars[i].getValue() == 0 && !heroLabels[i].getText().contains("(Derrotado)")) {
                heroLabels[i].setText(heroLabels[i].getText() + " (Derrotado)");
                heroLabels[i].setForeground(Color.GRAY);
                dialogueArea.setText("¡" + heroLabels[i].getText() + " ha caído en batalla!\n\n" + dialogueArea.getText());
            }
        }
    }

    private void advanceDialogue() {
        String[] dialogues = {
                "Yangus: ¡No te preocupes, jefe! ¡Yo los tengo!",
                "Jessica: ¡Mi magia los reducirá a cenizas!",
                "Angelo: ¡La luz nos protegerá!",
                "Slime: ¡Blub blub!",
                "Goblin: ¡Grrr! ¡Destruiremos a todos!",
                "El Dragón Negro ruge ferozmente...",
                "El destino de Trodain está en tus manos...",
                "¡No te rindas, la batalla apenas comienza!"
        };

        int randomIndex = (int)(Math.random() * dialogues.length);
        dialogueArea.setText(dialogues[randomIndex] + "\n\n" + dialogueArea.getText());
    }

    // Métodos públicos para actualizar desde el controlador
    public void updateHeroStats(int index, int hp, int maxHp, int mp, int maxMp) {
        heroHpBars[index].setMaximum(maxHp);
        heroHpBars[index].setValue(hp);
        heroHpBars[index].setString("HP: " + hp + "/" + maxHp);

        heroMpBars[index].setMaximum(maxMp);
        heroMpBars[index].setValue(mp);
        heroMpBars[index].setString("MP: " + mp + "/" + maxMp);
    }

    public void updateEnemyStats(int index, int hp, int maxHp) {
        enemyHpBars[index].setMaximum(maxHp);
        enemyHpBars[index].setValue(hp);
        enemyHpBars[index].setString("HP: " + hp + "/" + maxHp);
    }

    public void addDialogue(String message) {
        dialogueArea.setText(message + "\n\n" + dialogueArea.getText());
    }

    public void setBattleEnabled(boolean enabled) {
        attackButton.setEnabled(enabled);
        skillButton.setEnabled(enabled);
        itemButton.setEnabled(enabled);
        defendButton.setEnabled(enabled);
        fleeButton.setEnabled(enabled);
    }

    public static void main(String[] args) {
        // Ejecutar en el Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            try {
                // Establecer look and feel
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new DragonQuestGUI();
        });
    }
}