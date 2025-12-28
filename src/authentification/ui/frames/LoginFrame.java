package authentification.ui.frames;

import authentification.model.*;
import authentification.ui.components.*;
import frames.DashboardFrame;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import services.gestionnaire;

/**
 * LoginFrame - Interface de connexion pour admins et clubs
 * Classe fille de JFrame
 */
public class LoginFrame extends JFrame {
    private final Authentication auth;
    private gestionnaire gestionnairePartage;
    private ModernTextField usernameField;
    private ModernPasswordField passwordField;
    private JLabel statusLabel;
    private RoundedPanel loginCard;

    public LoginFrame(gestionnaire gest) { // 👈 AJOUTE LE PARAMÈTRE
    this.gestionnairePartage = gest; // 👈 AJOUTE CETTE LIGNE
    auth = new Authentication();
    initializeUI();
}

    private void initializeUI() {
        setTitle("IHEC Carthage - Connexion Professionnelle");
        setSize(550, 700);
       setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

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

        // Bouton retour
        JButton backButton = new JButton("← Retour");
        backButton.setBounds(20, 20, 100, 35);
        backButton.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        backButton.setForeground(Color.WHITE);
        backButton.setBackground(new Color(255, 255, 255, 30));
        backButton.setBorderPainted(false);
        backButton.setFocusPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(e -> {
        dispose();
        new WelcomeFrame(); // 👈 PAS BESOIN DE PASSER LE GESTIONNAIRE ICI
        });
        mainPanel.add(backButton);

        // Logo
        JLabel logoLabel = new JLabel("🎓");
        logoLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 70));
        logoLabel.setBounds(225, 70, 100, 100);
        mainPanel.add(logoLabel);

        // Titre
        JLabel titleLabel = new JLabel("Connexion Professionnelle");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(0, 170, 550, 35);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(titleLabel);

        JLabel subtitleLabel = new JLabel("Administration & Clubs IHEC");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        subtitleLabel.setForeground(new Color(255, 255, 255, 180));
        subtitleLabel.setBounds(0, 210, 550, 25);
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(subtitleLabel);

        // Card de login
        loginCard = new RoundedPanel(25, new Color(255, 255, 255, 20));
        loginCard.setBounds(75, 270, 400, 300);
        loginCard.setLayout(null);

        // Username
        JLabel userIconLabel = new JLabel("👤");
        userIconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20));
        userIconLabel.setBounds(30, 30, 30, 30);
        loginCard.add(userIconLabel);

        JLabel userLabel = new JLabel("Nom d'utilisateur");
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        userLabel.setForeground(new Color(255, 255, 255, 220));
        userLabel.setBounds(65, 30, 200, 30);
        loginCard.add(userLabel);

        usernameField = new ModernTextField(20);
        usernameField.setBounds(30, 65, 340, 45);
        loginCard.add(usernameField);

        // Password
        JLabel passIconLabel = new JLabel("🔒");
        passIconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20));
        passIconLabel.setBounds(30, 125, 30, 30);
        loginCard.add(passIconLabel);

        JLabel passLabel = new JLabel("Mot de passe");
        passLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        passLabel.setForeground(new Color(255, 255, 255, 220));
        passLabel.setBounds(65, 125, 200, 30);
        loginCard.add(passLabel);

        passwordField = new ModernPasswordField(20);
        passwordField.setBounds(30, 160, 340, 45);
        loginCard.add(passwordField);

        // Login button
        ModernButton loginButton = new ModernButton(
                "Se connecter",
                new Color(46, 204, 113),
                new Color(39, 174, 96),
                new Color(32, 154, 76));
        loginButton.setBounds(30, 225, 340, 50);
        loginButton.addActionListener(e -> handleLogin());
        loginCard.add(loginButton);

        mainPanel.add(loginCard);

        // Status label
        statusLabel = new JLabel("");
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setBounds(75, 585, 400, 30);
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(statusLabel);

        // Info utilisateurs
        JLabel infoLabel = new JLabel(
                "<html><center>Utilisateurs autorisés:<br>Admin: adminUni / Clubs: hecfa, artrev, lions, enactus, aiesec, mmt, libertad, ihecnews</center></html>");
        infoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        infoLabel.setForeground(new Color(255, 255, 255, 120));
        infoLabel.setBounds(50, 620, 450, 40);
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(infoLabel);

        // Enter key
        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    handleLogin();
                }
            }
        });

        add(mainPanel);
        setVisible(true);
    }

    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            statusLabel.setForeground(new Color(231, 76, 60));
            statusLabel.setText("✗ Veuillez remplir tous les champs");
            return;
        }

        User user = auth.login(username, password);

        if (user != null) {
            statusLabel.setForeground(new Color(46, 204, 113));
            statusLabel.setText("✓ Connexion réussie!");

            // Disable buttons during transition
            loginCard.setEnabled(false);
            
            Timer timer = new Timer(800, e -> {
            dispose();
            DashboardFrame dashboard = new DashboardFrame(gestionnairePartage); // 👈 UTILISE LE GESTIONNAIRE PARTAGÉ
            dashboard.setVisible(true);
            });
            timer.setRepeats(false);
            timer.start();
        } else {
            statusLabel.setForeground(new Color(231, 76, 60));
            statusLabel.setText("✗ Identifiants incorrects");
            passwordField.setText("");
        }
    }
}
