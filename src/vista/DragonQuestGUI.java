package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

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

        // Configurar look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }



        setVisible(true);
    }

        try {
            }
        } catch (Exception e) {
        }
    }


        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Fondo circular
        g2d.setColor(color);
        g2d.fillOval(2, 2, width - 4, height - 4);

        // Borde
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(3));
        g2d.drawOval(2, 2, width - 4, height - 4);

        // Texto
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Serif", Font.BOLD, 24));
        FontMetrics fm = g2d.getFontMetrics();

        g2d.dispose();
    }

        mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                if (backgroundImage != null) {
                    g2d.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
                } else {
                    g2d.setPaint(gradient);
                    g2d.fillRect(0, 0, getWidth(), getHeight());

                    }
                }
            }
        };


        mainPanel.add(battlePanel, BorderLayout.CENTER);
        mainPanel.add(statsPanel, BorderLayout.EAST);
        mainPanel.add(dialoguePanel, BorderLayout.SOUTH);
        mainPanel.add(menuPanel, BorderLayout.WEST);

        add(mainPanel);
    }

        battlePanel = new JPanel(new GridBagLayout());
        battlePanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

                BorderFactory.createLineBorder(DQ_GOLD, 3),
                "HÉROES",
                javax.swing.border.TitledBorder.CENTER,
                javax.swing.border.TitledBorder.TOP,
                new Font("Serif", Font.BOLD, 16),
                DQ_GOLD
        ));


        for (int i = 0; i < 4; i++) {

            heroLabels[i].setFont(new Font("Serif", Font.BOLD, 14));
            heroLabels[i].setForeground(Color.WHITE);

            heroHpBars[i] = new JProgressBar(0, 100);
            heroHpBars[i].setValue(100);
            heroHpBars[i].setBackground(new Color(100, 0, 0));
            heroHpBars[i].setStringPainted(true);
            heroHpBars[i].setString("HP: 100/100");

            heroMpBars[i] = new JProgressBar(0, 100);
            heroMpBars[i].setValue(100);
            heroMpBars[i].setBackground(new Color(0, 0, 100));
            heroMpBars[i].setStringPainted(true);
            heroMpBars[i].setString("MP: 50/50");




        }

                BorderFactory.createLineBorder(Color.RED, 3),
                "ENEMIGOS",
                javax.swing.border.TitledBorder.CENTER,
                javax.swing.border.TitledBorder.TOP,
                new Font("Serif", Font.BOLD, 16),
                Color.RED
        ));


        for (int i = 0; i < 4; i++) {

            enemyLabels[i].setFont(new Font("Serif", Font.BOLD, 14));
            enemyLabels[i].setForeground(Color.WHITE);

            enemyHpBars[i] = new JProgressBar(0, 100);
            enemyHpBars[i].setValue(100);
            enemyHpBars[i].setBackground(new Color(100, 0, 0));
            enemyHpBars[i].setStringPainted(true);



        }

        // Agregar paneles al battle panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.4;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;

        gbc.gridx = 2;


        JLabel vsLabel = new JLabel("⚔️ VS ⚔️");
        vsLabel.setFont(new Font("Serif", Font.BOLD, 36));
        vsLabel.setForeground(DQ_GOLD);
        vsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);



        gbc.gridx = 1;
        gbc.weightx = 0.2;
    }

        statsPanel.setPreferredSize(new Dimension(200, 0));
        statsPanel.setBackground(new Color(0, 0, 0, 200));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Título

        };

            JLabel statLabel = new JLabel(stat);
            statLabel.setFont(new Font("Monospaced", Font.PLAIN, 12));
            statLabel.setForeground(Color.WHITE);
            statsPanel.add(statLabel);
        }
    }

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
                "Los monstruos amenazan el reino de Trodain.\n" +
                "¡Héroes, defiendan nuestro hogar!");

        JScrollPane scrollPane = new JScrollPane(dialogueArea);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        dialoguePanel.add(scrollPane, BorderLayout.CENTER);


    }

        menuPanel.setPreferredSize(new Dimension(150, 0));
        menuPanel.setBackground(new Color(0, 0, 0, 200));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));



        menuPanel.add(attackButton);
        menuPanel.add(skillButton);
        menuPanel.add(itemButton);
        menuPanel.add(defendButton);
        menuPanel.add(fleeButton);
        menuPanel.add(inventoryButton);
    }

                BorderFactory.createLineBorder(Color.BLACK, 2),
        ));

        // Efecto hover
            @Override
            public void mouseEntered(MouseEvent e) {
                        BorderFactory.createLineBorder(Color.YELLOW, 2),
                ));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                        BorderFactory.createLineBorder(Color.BLACK, 2),
                ));
            }
        });

        }


                break;
                break;
                break;
        timer.setRepeats(false);
        timer.start();
                        break;
                }
                break;
            }
        }
    }



        // Efecto visual
        timer.setRepeats(false);
        timer.start();
            timer.setRepeats(false);
            timer.start();

    }


        timer.setRepeats(false);
        timer.start();
    }

            }


        }
    }

    }

                "Yangus: ¡No te preocupes, jefe! ¡Yo los tengo!",
                "Jessica: ¡Mi magia los reducirá a cenizas!",
                "Angelo: ¡La luz nos protegerá!",
                "Goblin: ¡Grrr! ¡Destruiremos a todos!",
                "El Dragón Negro ruge ferozmente...",
        };

    }


    }

    }

    }

    }

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