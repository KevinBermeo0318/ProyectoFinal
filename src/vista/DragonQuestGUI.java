package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

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

    // Componentes de estadísticas
    private JLabel levelLabel;
    private JLabel expLabel;
    private JLabel goldLabel;
    private JLabel turnLabel;

    // Área de texto para diálogos
    private JTextArea dialogueArea;

    // Botones de acción
    private JButton attackButton;
    private JButton skillButton;
    private JButton itemButton;
    private JButton defendButton;
    private JButton fleeButton;
    private JButton inventoryButton;

    // Variables de estado
    private int currentTurn = 1;
    private int gold = 2500;
    private int experience = 1250;
    private int level = 15;

    // Colores estilo Dragon Quest
    private final Color DQ_BROWN = new Color(101, 67, 33);
    private final Color DQ_GOLD = new Color(255, 204, 0);
    private final Color DQ_RED = new Color(204, 0, 0);
    private final Color DQ_BLUE = new Color(0, 102, 204);
    private final Color DQ_GREEN = new Color(0, 153, 0);
    private final Color DQ_PURPLE = new Color(102, 0, 153);
    private final Color DQ_DARK = new Color(34, 34, 34);

    // Nombres de personajes
    private String[] heroNames = {"HÉROE", "YANGUS", "JESSICA", "ANGELO"};
    private String[] enemyNames = {"SLIME", "DRAGÓN NEGRO", "GOBLIN", "ESPECTRO"};

    public DragonQuestGUI() {
        setTitle("DRAGON QUEST VIII - La Maldición del Rey");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setResizable(false);
        setLocationRelativeTo(null);

        // Configurar look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        initializeComponents();
        setupLayout();
        setupListeners();

        setVisible(true);
    }

    // ========== MÉTODOS PÚBLICOS PARA ACTUALIZAR ESTADÍSTICAS ==========

    /**
     * Actualiza las estadísticas de un héroe
     * @param heroIndex Índice del héroe (0-3)
     * @param hpActual HP actual
     * @param hpMax HP máximo
     * @param mpActual MP actual
     * @param mpMax MP máximo
     */
    public void updateHeroStats(int heroIndex, int hpActual, int hpMax, int mpActual, int mpMax) {
        if (heroIndex >= 0 && heroIndex < 4) {
            heroHpBars[heroIndex].setMaximum(hpMax);
            heroHpBars[heroIndex].setValue(hpActual);
            heroHpBars[heroIndex].setString("HP: " + hpActual + "/" + hpMax);

            heroMpBars[heroIndex].setMaximum(mpMax);
            heroMpBars[heroIndex].setValue(mpActual);
            heroMpBars[heroIndex].setString("MP: " + mpActual + "/" + mpMax);
        }
    }

    /**
     * Actualiza las estadísticas de un enemigo
     * @param enemyIndex Índice del enemigo (0-3)
     * @param hpActual HP actual
     * @param hpMax HP máximo
     */
    public void updateEnemyStats(int enemyIndex, int hpActual, int hpMax) {
        if (enemyIndex >= 0 && enemyIndex < 4) {
            enemyHpBars[enemyIndex].setMaximum(hpMax);
            enemyHpBars[enemyIndex].setValue(hpActual);
            enemyHpBars[enemyIndex].setString("HP: " + hpActual + "/" + hpMax);
        }
    }

    /**
     * Agrega un mensaje al área de diálogo
     * @param message Mensaje a mostrar
     */
    public void addDialogue(String message) {
        String current = dialogueArea.getText();
        if (!current.isEmpty()) {
            dialogueArea.setText(current + "\n> " + message);
        } else {
            dialogueArea.setText("> " + message);
        }
        dialogueArea.setCaretPosition(dialogueArea.getDocument().getLength());
    }

    // ========== MÉTODOS PRIVADOS ==========

    private void initializeComponents() {
        // Inicializar etiquetas de héroes
        for (int i = 0; i < 4; i++) {
            heroLabels[i] = new JLabel(heroNames[i]);
            heroLabels[i].setFont(new Font("Arial", Font.BOLD, 16));
            heroLabels[i].setForeground(Color.WHITE);
            heroLabels[i].setHorizontalAlignment(SwingConstants.CENTER);

            heroHpBars[i] = new JProgressBar(0, 100);
            heroHpBars[i].setValue(100);
            heroHpBars[i].setForeground(new Color(0, 200, 0));
            heroHpBars[i].setBackground(new Color(50, 0, 0));
            heroHpBars[i].setStringPainted(true);
            heroHpBars[i].setFont(new Font("Arial", Font.BOLD, 10));
            heroHpBars[i].setString("HP: 100/100");

            heroMpBars[i] = new JProgressBar(0, 100);
            heroMpBars[i].setValue(100);
            heroMpBars[i].setForeground(new Color(100, 100, 255));
            heroMpBars[i].setBackground(new Color(0, 0, 50));
            heroMpBars[i].setStringPainted(true);
            heroMpBars[i].setFont(new Font("Arial", Font.BOLD, 10));
            heroMpBars[i].setString("MP: 100/100");
        }

        // Inicializar etiquetas de enemigos
        for (int i = 0; i < 4; i++) {
            enemyLabels[i] = new JLabel(enemyNames[i]);
            enemyLabels[i].setFont(new Font("Arial", Font.BOLD, 16));
            enemyLabels[i].setForeground(Color.WHITE);
            enemyLabels[i].setHorizontalAlignment(SwingConstants.CENTER);

            enemyHpBars[i] = new JProgressBar(0, 100);
            enemyHpBars[i].setValue(100);
            enemyHpBars[i].setForeground(new Color(255, 100, 100));
            enemyHpBars[i].setBackground(new Color(50, 0, 0));
            enemyHpBars[i].setStringPainted(true);
            enemyHpBars[i].setFont(new Font("Arial", Font.BOLD, 10));
            enemyHpBars[i].setString("HP: 100/100");
        }

        // Inicializar botones
        attackButton = createStyledButton("⚔️ ATACAR", DQ_RED);
        skillButton = createStyledButton("✨ HABILIDAD", DQ_BLUE);
        itemButton = createStyledButton("💊 OBJETO", DQ_GREEN);
        defendButton = createStyledButton("🛡️ DEFENDER", DQ_GOLD);
        fleeButton = createStyledButton("🏃‍♂️ HUIR", DQ_PURPLE);
        inventoryButton = createStyledButton("🎒 INVENTARIO", DQ_BROWN);

        // Inicializar etiquetas de estadísticas
        levelLabel = new JLabel("Nivel: " + level);
        expLabel = new JLabel("Exp: " + experience + "/2800");
        goldLabel = new JLabel("Oro: " + gold);
        turnLabel = new JLabel("Turno: " + currentTurn);

        // Inicializar área de diálogo
        dialogueArea = new JTextArea();
        dialogueArea.setEditable(false);
        dialogueArea.setLineWrap(true);
        dialogueArea.setWrapStyleWord(true);
        dialogueArea.setFont(new Font("Courier New", Font.PLAIN, 14));
        dialogueArea.setForeground(Color.WHITE);
        dialogueArea.setBackground(new Color(0, 0, 0, 200));
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2),
                BorderFactory.createEmptyBorder(8, 5, 8, 5)
        ));
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(140, 45));
        button.setMaximumSize(new Dimension(140, 45));

        // Usar variable final para la lambda
        final Color finalColor = color;

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(finalColor.brighter());
                button.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.YELLOW, 2),
                        BorderFactory.createEmptyBorder(8, 5, 8, 5)
                ));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(finalColor);
                button.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.BLACK, 2),
                        BorderFactory.createEmptyBorder(8, 5, 8, 5)
                ));
            }
        });

        return button;
    }

    private void setupLayout() {
        mainPanel = new JPanel(new BorderLayout(5, 5));
        mainPanel.setBackground(DQ_DARK);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel de batalla principal
        battlePanel = createBattlePanel();

        // Panel de menú lateral
        menuPanel = createMenuPanel();

        // Panel de información
        statsPanel = createStatsPanel();

        // Panel de diálogo y controles
        dialoguePanel = createDialoguePanel();

        // Agregar paneles al panel principal
        mainPanel.add(battlePanel, BorderLayout.CENTER);
        mainPanel.add(menuPanel, BorderLayout.WEST);
        mainPanel.add(statsPanel, BorderLayout.EAST);
        mainPanel.add(dialoguePanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel createBattlePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.BOTH;

        // Panel de héroes - Lado izquierdo
        JPanel heroPanel = new JPanel(new GridLayout(4, 1, 0, 15));
        heroPanel.setOpaque(false);
        heroPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(DQ_BLUE, 3),
                " HÉROES ",
                javax.swing.border.TitledBorder.CENTER,
                javax.swing.border.TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 18),
                DQ_BLUE
        ));

        for (int i = 0; i < 4; i++) {
            JPanel charPanel = new JPanel(new BorderLayout(10, 5));
            charPanel.setOpaque(false);
            charPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

            JPanel rightPanel = new JPanel(new GridLayout(3, 1, 2, 2));
            rightPanel.setOpaque(false);

            heroLabels[i].setFont(new Font("Arial", Font.BOLD, 16));
            heroLabels[i].setForeground(DQ_GOLD);
            rightPanel.add(heroLabels[i]);
            rightPanel.add(heroHpBars[i]);
            rightPanel.add(heroMpBars[i]);

            charPanel.add(rightPanel, BorderLayout.CENTER);

            heroPanel.add(charPanel);
        }

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.35;
        gbc.weighty = 1.0;
        panel.add(heroPanel, gbc);

        // VS Label en el centro
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);

        JLabel vsLabel = new JLabel("⚔️ VS ⚔️");
        vsLabel.setFont(new Font("Arial", Font.BOLD, 48));
        vsLabel.setForeground(DQ_GOLD);
        vsLabel.setHorizontalAlignment(SwingConstants.CENTER);

        centerPanel.add(vsLabel);

        gbc.gridx = 1;
        gbc.weightx = 0.3;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(centerPanel, gbc);

        // Panel de enemigos - Lado derecho
        JPanel enemyPanel = new JPanel(new GridLayout(4, 1, 0, 15));
        enemyPanel.setOpaque(false);
        enemyPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(DQ_RED, 3),
                " ENEMIGOS ",
                javax.swing.border.TitledBorder.CENTER,
                javax.swing.border.TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 18),
                DQ_RED
        ));

        for (int i = 0; i < 4; i++) {
            JPanel charPanel = new JPanel(new BorderLayout(10, 5));
            charPanel.setOpaque(false);
            charPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

            JPanel leftPanel = new JPanel(new GridLayout(2, 1, 2, 2));
            leftPanel.setOpaque(false);

            enemyLabels[i].setFont(new Font("Arial", Font.BOLD, 16));
            enemyLabels[i].setForeground(new Color(255, 150, 150));
            leftPanel.add(enemyLabels[i]);
            leftPanel.add(enemyHpBars[i]);

            charPanel.add(leftPanel, BorderLayout.CENTER);

            enemyPanel.add(charPanel);
        }

        gbc.gridx = 2;
        gbc.weightx = 0.35;
        panel.add(enemyPanel, gbc);

        return panel;
    }

    private JPanel createMenuPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(40, 40, 40, 230));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(DQ_GOLD, 2),
                BorderFactory.createEmptyBorder(15, 10, 15, 10)
        ));
        panel.setPreferredSize(new Dimension(180, 0));

        JLabel title = new JLabel("MENÚ");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(DQ_GOLD);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(title);

        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Agregar botones
        panel.add(attackButton);
        panel.add(Box.createRigidArea(new Dimension(0, 12)));
        panel.add(skillButton);
        panel.add(Box.createRigidArea(new Dimension(0, 12)));
        panel.add(itemButton);
        panel.add(Box.createRigidArea(new Dimension(0, 12)));
        panel.add(defendButton);
        panel.add(Box.createRigidArea(new Dimension(0, 12)));
        panel.add(fleeButton);
        panel.add(Box.createRigidArea(new Dimension(0, 12)));
        panel.add(inventoryButton);

        panel.add(Box.createVerticalGlue());

        // Controles
        JPanel controlsPanel = new JPanel();
        controlsPanel.setLayout(new BoxLayout(controlsPanel, BoxLayout.Y_AXIS));
        controlsPanel.setOpaque(false);
        controlsPanel.setBorder(BorderFactory.createEmptyBorder(20, 5, 5, 5));

        JLabel controlsTitle = new JLabel("CONTROLES");
        controlsTitle.setFont(new Font("Arial", Font.BOLD, 14));
        controlsTitle.setForeground(Color.WHITE);
        controlsTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        controlsPanel.add(controlsTitle);

        controlsPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        String[] controls = {
                "Flechas: Seleccionar",
                "Enter: Confirmar",
                "Espacio: Cancelar",
                "Esc: Menú"
        };

        for (String control : controls) {
            JLabel controlLabel = new JLabel(control);
            controlLabel.setFont(new Font("Arial", Font.PLAIN, 11));
            controlLabel.setForeground(Color.LIGHT_GRAY);
            controlLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            controlsPanel.add(controlLabel);
        }

        panel.add(controlsPanel);

        return panel;
    }

    private JPanel createStatsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(40, 40, 40, 230));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(DQ_BLUE, 2),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        panel.setPreferredSize(new Dimension(220, 0));

        JLabel title = new JLabel("ESTADO");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(DQ_GOLD);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(title);

        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Estadísticas detalladas
        String[][] stats = {
                {"Nivel:", "15"},
                {"Exp:", "1250/2800"},
                {"Oro:", "2,500"},
                {"Turno:", "3"},
                {"Atq:", "85"},
                {"Def:", "72"},
                {"Agi:", "68"},
                {"Mag:", "92"},
                {"Res:", "75"}
        };

        for (String[] stat : stats) {
            JPanel statPanel = new JPanel(new BorderLayout());
            statPanel.setOpaque(false);
            statPanel.setMaximumSize(new Dimension(200, 25));

            JLabel nameLabel = new JLabel(stat[0]);
            nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
            nameLabel.setForeground(Color.LIGHT_GRAY);

            JLabel valueLabel = new JLabel(stat[1]);
            valueLabel.setFont(new Font("Arial", Font.BOLD, 14));
            valueLabel.setForeground(Color.WHITE);
            valueLabel.setHorizontalAlignment(SwingConstants.RIGHT);

            statPanel.add(nameLabel, BorderLayout.WEST);
            statPanel.add(valueLabel, BorderLayout.EAST);

            panel.add(statPanel);
            panel.add(Box.createRigidArea(new Dimension(0, 8)));
        }

        panel.add(Box.createVerticalGlue());

        // Efectos de estado
        JPanel statusPanel = new JPanel();
        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.Y_AXIS));
        statusPanel.setOpaque(false);
        statusPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(DQ_GREEN, 1),
                " ESTADOS ",
                javax.swing.border.TitledBorder.CENTER,
                javax.swing.border.TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 12),
                DQ_GREEN
        ));

        String[] statuses = {"Normal", "Protegido", "Concentrado"};
        for (String status : statuses) {
            JLabel statusLabel = new JLabel("• " + status);
            statusLabel.setFont(new Font("Arial", Font.PLAIN, 12));
            statusLabel.setForeground(Color.WHITE);
            statusLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            statusPanel.add(statusLabel);
        }

        panel.add(statusPanel);

        return panel;
    }

    private JPanel createDialoguePanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setPreferredSize(new Dimension(0, 120));
        panel.setBackground(new Color(0, 0, 0, 220));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(DQ_GOLD, 2),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        // Panel superior: información de turno
        JPanel turnPanel = new JPanel(new BorderLayout());
        turnPanel.setOpaque(false);

        JLabel turnLabel = new JLabel("TURNO: Héroe (Seleccionado: Atacar)");
        turnLabel.setFont(new Font("Arial", Font.BOLD, 14));
        turnLabel.setForeground(DQ_GOLD);
        turnPanel.add(turnLabel, BorderLayout.WEST);

        JLabel enemyInfo = new JLabel("Enemigos: 4 vivos | Héroes: 4 vivos");
        enemyInfo.setFont(new Font("Arial", Font.PLAIN, 12));
        enemyInfo.setForeground(Color.LIGHT_GRAY);
        turnPanel.add(enemyInfo, BorderLayout.EAST);

        panel.add(turnPanel, BorderLayout.NORTH);

        // Área de diálogo
        JScrollPane scrollPane = new JScrollPane(dialogueArea);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100), 1));
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));

        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private void setupListeners() {
        attackButton.addActionListener(e -> performAttack());
        skillButton.addActionListener(e -> showSkillMenu());
        itemButton.addActionListener(e -> showItemMenu());
        defendButton.addActionListener(e -> defendAction());
        fleeButton.addActionListener(e -> fleeAction());
        inventoryButton.addActionListener(e -> showInventory());
    }

    private void performAttack() {
        Random rand = new Random();
        int damage = rand.nextInt(30) + 20;
        int target = rand.nextInt(4);

        // Usar variables final para la lambda
        final int finalTarget = target;
        final int finalDamage = damage;
        final String enemyName = enemyNames[target];
        final String heroName = heroNames[0];

        int currentHp = enemyHpBars[target].getValue();
        int maxHp = enemyHpBars[target].getMaximum();
        int newHp = currentHp - damage;
        if (newHp < 0) newHp = 0;
        enemyHpBars[target].setValue(newHp);
        enemyHpBars[target].setString("HP: " + newHp + "/" + maxHp);

        addDialogue("¡" + heroName + " ataca a " + enemyName + " causando " + finalDamage + " puntos de daño!");

        // Contrataque enemigo
        if (newHp > 0) {
            Timer timer = new Timer(1000, ev -> {
                int enemyDamage = rand.nextInt(20) + 10;
                int heroTarget = rand.nextInt(4);

                // Usar variables final para la lambda interna
                final int finalHeroTarget = heroTarget;
                final int finalEnemyDamage = enemyDamage;
                final String attackingEnemyName = enemyNames[finalTarget];
                final String attackedHeroName = heroNames[finalHeroTarget];

                int heroCurrentHp = heroHpBars[heroTarget].getValue();
                int heroMaxHp = heroHpBars[heroTarget].getMaximum();
                int heroNewHp = heroCurrentHp - enemyDamage;
                if (heroNewHp < 0) heroNewHp = 0;
                heroHpBars[heroTarget].setValue(heroNewHp);
                heroHpBars[heroTarget].setString("HP: " + heroNewHp + "/" + heroMaxHp);

                addDialogue("¡" + attackingEnemyName + " contraataca a " +
                        attackedHeroName + " causando " +
                        finalEnemyDamage + " puntos de daño!");
            });
            timer.setRepeats(false);
            timer.start();
        }
    }

    private void showSkillMenu() {
        String[] skills = {"Curar", "Bola de Fuego", "Rayo", "Aumentar Defensa", "Disminuir Ataque"};

        SwingUtilities.invokeLater(() -> {
            // Usar variable final
            final JFrame parentFrame = this;

            String skill = (String) JOptionPane.showInputDialog(
                    parentFrame,
                    "Selecciona una habilidad:",
                    "HABILIDADES",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    skills,
                    skills[0]
            );

            if (skill != null) {
                addDialogue("¡Usando habilidad: " + skill + "!");
            }
        });
    }

    private void showItemMenu() {
        String[] items = {"Poción (x5)", "Éter (x3)", "Antídoto (x2)", "Revivir (x1)", "Bomba (x4)"};

        SwingUtilities.invokeLater(() -> {
            // Usar variable final
            final JFrame parentFrame = this;

            String item = (String) JOptionPane.showInputDialog(
                    parentFrame,
                    "Selecciona un objeto:",
                    "INVENTARIO RÁPIDO",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    items,
                    items[0]
            );

            if (item != null) {
                addDialogue("¡Usando: " + item + "!");
            }
        });
    }

    private void defendAction() {
        addDialogue("¡El equipo se defiende! Defensa aumentada por 1 turno.");
    }

    private void fleeAction() {
        SwingUtilities.invokeLater(() -> {
            // Usar variable final
            final JFrame parentFrame = this;

            int result = JOptionPane.showConfirmDialog(
                    parentFrame,
                    "¿Intentar huir de la batalla?",
                    "HUIR",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (result == JOptionPane.YES_OPTION) {
                Random rand = new Random();
                if (rand.nextBoolean()) {
                    addDialogue("¡Escaparon exitosamente!");
                } else {
                    addDialogue("¡No pudieron escapar!");
                }
            }
        });
    }

    private void showInventory() {
        String inventory = "INVENTARIO COMPLETO\n\n" +
                "➤ Consumibles:\n" +
                "   Pociones: 5\n" +
                "   Éteres: 3\n" +
                "   Antídotos: 2\n" +
                "   Revivir: 1\n" +
                "   Bombas: 4\n\n" +
                "➤ Equipo:\n" +
                "   Espada de Acero\n" +
                "   Armadura de Cuero\n" +
                "   Escudo de Madera\n\n" +
                "➤ Clave:\n" +
                "   Llave Antigua\n" +
                "   Gema Azul";

        SwingUtilities.invokeLater(() -> {
            // Usar variable final
            final JFrame parentFrame = this;

            JTextArea inventoryArea = new JTextArea(inventory);
            inventoryArea.setFont(new Font("Courier New", Font.PLAIN, 12));
            inventoryArea.setEditable(false);

            JScrollPane scrollPane = new JScrollPane(inventoryArea);
            scrollPane.setPreferredSize(new Dimension(400, 300));

            JOptionPane.showMessageDialog(parentFrame, scrollPane, "INVENTARIO", JOptionPane.INFORMATION_MESSAGE);
        });
    }

    // ========== MÉTODOS ADICIONALES PÚBLICOS ==========

    /**
     * Actualiza el nivel del jugador
     * @param newLevel Nuevo nivel
     */
    public void updateLevel(int newLevel) {
        level = newLevel;
        levelLabel.setText("Nivel: " + newLevel);
    }

    /**
     * Actualiza la experiencia del jugador
     * @param currentExp Experiencia actual
     * @param maxExp Experiencia máxima para el nivel
     */
    public void updateExperience(int currentExp, int maxExp) {
        experience = currentExp;
        expLabel.setText("Exp: " + currentExp + "/" + maxExp);
    }

    /**
     * Actualiza el oro del jugador
     * @param newGold Cantidad de oro
     */
    public void updateGold(int newGold) {
        gold = newGold;
        goldLabel.setText("Oro: " + newGold);
    }

    /**
     * Actualiza el turno actual
     * @param turn Número de turno
     */
    public void updateTurn(int turn) {
        currentTurn = turn;
        turnLabel.setText("Turno: " + turn);
    }

    /**
     * Limpia el área de diálogo
     */
    public void clearDialogue() {
        dialogueArea.setText("");
    }

    /**
     * Muestra un mensaje en el área de diálogo
     * @param message Mensaje a mostrar
     */
    public void showMessage(String message) {
        addDialogue(message);
    }

    // ========== MÉTODO MAIN PARA PRUEBAS ==========

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new DragonQuestGUI();
        });
    }
}