package authentification.ui.frames;

import authentification.model.User;
import javax.swing.*;
import java.awt.*;

public class AdminDashboard extends JFrame {
    private User currentUser;

    public AdminDashboard(User user) {
        this.currentUser = user;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("IHEC Carthage - Dashboard");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Panel principal avec gradient
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

                int w = getWidth();
                int h = getHeight();

                Color color1 = new Color(0, 51, 102);
                Color color2 = new Color(0, 102, 204);
                GradientPaint gp = new GradientPaint(0, 0, color1, w, h, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);

                g2d.dispose();
            }
        };
        mainPanel.setLayout(null);

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(255, 255, 255, 20));
        headerPanel.setBounds(0, 0, 1000, 80);
        headerPanel.setLayout(null);

        JLabel welcomeLabel = new JLabel("Bienvenue, " + currentUser.getUsername());
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setBounds(30, 20, 400, 40);
        headerPanel.add(welcomeLabel);

        JLabel roleLabel = new JLabel("Rôle: " + currentUser.getRole());
        roleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        roleLabel.setForeground(new Color(255, 255, 255, 200));
        roleLabel.setBounds(30, 50, 200, 25);
        headerPanel.add(roleLabel);

        // Bouton déconnexion
        JButton logoutButton = new JButton("🚪 Déconnexion");
        logoutButton.setBounds(850, 20, 130, 40);
        logoutButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setBackground(new Color(231, 76, 60));
        logoutButton.setBorderPainted(false);
        logoutButton.setFocusPainted(false);
        logoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutButton.addActionListener(e -> {
            dispose();
            new WelcomeFrame();
        });
        headerPanel.add(logoutButton);

        mainPanel.add(headerPanel);

        // Content Area
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(new Color(255, 255, 255, 15));
        contentPanel.setBounds(50, 120, 900, 520);
        contentPanel.setLayout(null);

        JLabel titleLabel = new JLabel("Tableau de Bord - Gestion des Événements");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(0, 30, 900, 40);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPanel.add(titleLabel);

        // Options Grid
        int buttonWidth = 250;
        int buttonHeight = 80;
        int startY = 120;
        int gap = 30;

        String[][] options = {
                { "📅 Créer un Événement", "Ajouter un nouvel événement" },
                { "📋 Liste des Événements", "Voir tous les événements" },
                { "👥 Gérer les Participants", "Consulter les inscriptions" },
                { "📊 Statistiques", "Voir les statistiques" },
                { "⚙️ Paramètres", "Configuration du compte" },
                { "📧 Notifications", "Gérer les notifications" }
        };

        for (int i = 0; i < options.length; i++) {
            int row = i / 2;
            int col = i % 2;

            JPanel optionCard = createOptionCard(options[i][0], options[i][1]);
            optionCard.setBounds(
                    75 + col * (buttonWidth + gap),
                    startY + row * (buttonHeight + gap),
                    buttonWidth,
                    buttonHeight);
            contentPanel.add(optionCard);
        }

        mainPanel.add(contentPanel);

        // Footer
        JLabel footerLabel = new JLabel("© 2024 IHEC Carthage - Système de Gestion des Événements");
        footerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        footerLabel.setForeground(new Color(255, 255, 255, 150));
        footerLabel.setBounds(0, 665, 1000, 20);
        footerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(footerLabel);

        add(mainPanel);
        setVisible(true);
    }

    private JPanel createOptionCard(String title, String description) {
        JPanel card = new JPanel() {
            private Color bgColor = new Color(255, 255, 255, 25);
            private Color hoverColor = new Color(255, 255, 255, 40);
            private Color currentColor = bgColor;

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(currentColor);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                g2d.dispose();
            }

            {
                addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseEntered(java.awt.event.MouseEvent e) {
                        currentColor = hoverColor;
                        setCursor(new Cursor(Cursor.HAND_CURSOR));
                        repaint();
                    }

                    @Override
                    public void mouseExited(java.awt.event.MouseEvent e) {
                        currentColor = bgColor;
                        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                        repaint();
                    }

                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent e) {
                        JOptionPane.showMessageDialog(
                                AdminDashboard.this,
                                "Fonctionnalité: " + title + "\n" + description,
                                "Information",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                });
            }
        };

        card.setLayout(null);
        card.setOpaque(false);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(10, 15, 230, 25);
        card.add(titleLabel);

        JLabel descLabel = new JLabel(description);
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        descLabel.setForeground(new Color(255, 255, 255, 180));
        descLabel.setBounds(10, 45, 230, 20);
        card.add(descLabel);

        return card;
    }
}
