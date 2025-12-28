package authentification.ui.frames;
import authentification.ui.components.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import services.gestionnaire;

/**
 * WelcomeFrame - Interface d'accueil principale
 * Classe fille de JFrame
 */
public class WelcomeFrame extends JFrame {
    private gestionnaire gestionnaireUnique; // 👈 AJOUTE CETTE LIGNE
    
    public WelcomeFrame() {
        this.gestionnaireUnique = new gestionnaire(); // 👈 AJOUTE CETTE LIGNE
        initializeUI();
    }

    private void initializeUI() {
        setTitle("IHEC Carthage - Gestion des Événements");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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

                // Gradient bleu IHEC
                Color color1 = new Color(0, 51, 102);
                Color color2 = new Color(0, 102, 204);
                GradientPaint gp = new GradientPaint(0, 0, color1, w, h, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);

                // Cercles décoratifs
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f));
                g2d.setColor(new Color(255, 255, 255));
                g2d.fillOval(-100, -100, 400, 400);
                g2d.fillOval(w - 300, h - 300, 400, 400);

                g2d.dispose();
            }
        };
        mainPanel.setLayout(null);

        // Logo IHEC avec image réelle (sans bordure visible, coins légèrement arrondis)
        RoundedPanel logoPanel = new RoundedPanel(25, new Color(255, 255, 255, 250));
        logoPanel.setBounds(325, 50, 250, 250);
        logoPanel.setLayout(new GridBagLayout()); // Meilleur centrage

        // Créer et ajouter le logo
        JLabel logoLabel = createLogoLabel();
        logoPanel.add(logoLabel);

        mainPanel.add(logoPanel);

        // Titre de bienvenue
        JLabel welcomeTitle = new JLabel("Bienvenue");
        welcomeTitle.setFont(new Font("Segoe UI", Font.BOLD, 42));
        welcomeTitle.setForeground(Color.WHITE);
        welcomeTitle.setBounds(0, 320, 900, 50);
        welcomeTitle.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(welcomeTitle);

        JLabel welcomeSubtitle = new JLabel("Système de Gestion des Événements");
        welcomeSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        welcomeSubtitle.setForeground(new Color(255, 255, 255, 200));
        welcomeSubtitle.setBounds(0, 375, 900, 30);
        welcomeSubtitle.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(welcomeSubtitle);

        // Card pour les options
        RoundedPanel optionsCard = new RoundedPanel(25, new Color(255, 255, 255, 20));
        optionsCard.setBounds(150, 440, 600, 230);
        optionsCard.setLayout(null);

        JLabel chooseLabel = new JLabel("Choisissez votre profil:");
        chooseLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        chooseLabel.setForeground(Color.WHITE);
        chooseLabel.setBounds(0, 20, 600, 30);
        chooseLabel.setHorizontalAlignment(SwingConstants.CENTER);
        optionsCard.add(chooseLabel);

        // Bouton Visiteur
        ModernButton visitorButton = new ModernButton(
                "👥 Visiteur Simple",
                new Color(52, 152, 219),
                new Color(41, 128, 185),
                new Color(32, 108, 165));
        visitorButton.setBounds(50, 70, 500, 60);
        visitorButton.setFont(new Font("Segoe UI", Font.BOLD, 20));
        visitorButton.setHorizontalAlignment(SwingConstants.CENTER);
        visitorButton.setVerticalAlignment(SwingConstants.CENTER);
        visitorButton.addActionListener(e -> openVisitorInterface());
        optionsCard.add(visitorButton);

        JLabel visitorDesc = new JLabel("Consulter les événements disponibles");
        visitorDesc.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        visitorDesc.setForeground(new Color(255, 255, 255, 180));
        visitorDesc.setBounds(0, 135, 600, 20);
        visitorDesc.setHorizontalAlignment(SwingConstants.CENTER);
        optionsCard.add(visitorDesc);

        // Bouton Admin/Club
        ModernButton adminButton = new ModernButton(
                "🔐 Administrateur / Club",
                new Color(46, 204, 113),
                new Color(39, 174, 96),
                new Color(32, 154, 76));
        adminButton.setBounds(50, 165, 500, 60);
        adminButton.setFont(new Font("Segoe UI", Font.BOLD, 20));
        adminButton.setHorizontalAlignment(SwingConstants.CENTER);
        adminButton.setVerticalAlignment(SwingConstants.CENTER);
        adminButton.addActionListener(e -> openLoginInterface());
        optionsCard.add(adminButton);

        mainPanel.add(optionsCard);

        // Footer avec fond pour meilleure visibilité
        JPanel footerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(new Color(0, 0, 0, 80));
                g2d.fillRect(0, 0, getWidth(), getHeight());
                g2d.dispose();
            }
        };
        footerPanel.setOpaque(false);
        footerPanel.setBounds(0, 675, 900, 25);
        footerPanel.setLayout(new BorderLayout());

        JLabel footerLabel = new JLabel("© 2025 IHEC Carthage - Institut des Hautes Études Commerciales");
        footerLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        footerLabel.setForeground(Color.WHITE);
        footerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        footerPanel.add(footerLabel, BorderLayout.CENTER);

        mainPanel.add(footerPanel);

        add(mainPanel);
        setVisible(true);
    }

    /**
     * Crée le label du logo en essayant de charger l'image
     */
    private JLabel createLogoLabel() {
        JLabel logoLabel;

        // Chemins possibles pour le logo
        String[] possiblePaths = {
                "resources/images/ihec_logo.png",
                "resources/images/ihec_logo.webp",
                "resources/images/ihec_logo.jpg",
                "resources/images/logo.webp",
                "resources/images/logo.png",
                "evenement_IHEC/resources/images/ihec_logo.png",
                "images/ihec_logo.webp",
                "ihec_logo.webp",
                "logo.webp"
        };

        Image logoImage = null;

        // Essayer de charger l'image depuis différents chemins
        for (String path : possiblePaths) {
            try {
                File imageFile = new File(path);
                if (imageFile.exists()) {
                    logoImage = ImageIO.read(imageFile);
                    System.out.println("✓ Logo chargé depuis: " + path);
                    break;
                }
            } catch (IOException | SecurityException e) {
                // Continuer avec le prochain chemin
            }
        }

        // Si l'image est trouvée, l'afficher
        if (logoImage != null) {
            // Redimensionner l'image pour qu'elle rentre dans le panel
            Image scaledImage = logoImage.getScaledInstance(180, 180, Image.SCALE_SMOOTH);
            logoLabel = new JLabel(new ImageIcon(scaledImage));
            logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        } else {
            // Sinon, utiliser du texte comme fallback
            System.out.println("⚠ Logo non trouvé, utilisation du texte par défaut");
            logoLabel = new JLabel(
                    "<html><div style='text-align: center;'><span style='font-size: 48px; color: #003366;'>IHEC</span><br><span style='font-size: 20px; color: #0066cc;'>Carthage</span></div></html>");
            logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        }

        return logoLabel;
    }

    private void openVisitorInterface() {
    dispose();
    VisitorFrame visitorFrame = new VisitorFrame(gestionnaireUnique); // 👈 PASSE LE GESTIONNAIRE
    visitorFrame.setVisible(true);
}

    private void openLoginInterface() {
    dispose();
    LoginFrame loginFrame = new LoginFrame(gestionnaireUnique); // 👈 PASSE LE GESTIONNAIRE
    loginFrame.setVisible(true);
}}