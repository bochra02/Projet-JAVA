package frames;
import authentification.ui.frames.LoginFrame;
import java.awt.*;
import javax.swing.*;
import services.gestionnaire;

public class DashboardFrame extends JFrame {
    private gestionnaire gestionnaireEvenements;
    
    // Couleurs du thème moderne
    private final Color BACKGROUND_COLOR = new Color(240, 242, 245);
    private final Color PRIMARY_COLOR = new Color(37, 99, 235);
    private final Color PRIMARY_HOVER = new Color(29, 78, 216);
    private final Color SECONDARY_COLOR = new Color(16, 185, 129);
    private final Color SECONDARY_HOVER = new Color(5, 150, 105);
    private final Color WARNING_COLOR = new Color(245, 158, 11);
    private final Color WARNING_HOVER = new Color(217, 119, 6);
    private final Color DANGER_COLOR = new Color(239, 68, 68);
    private final Color DANGER_HOVER = new Color(220, 38, 38);
    private final Color TEXT_COLOR = new Color(31, 41, 55);
    
    public DashboardFrame(gestionnaire gestionnaire) {
        this.gestionnaireEvenements = gestionnaire;
        initializeUI();
    }
    
    private void initializeUI() {
        // Configuration de la fenêtre
        setTitle("Gestion des Événements - Dashboard");
        setSize(800, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setContentPane(new GradientBackgroundPanel());
        setLayout(new BorderLayout(20, 20));

        // Top bar with logout button
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(BACKGROUND_COLOR);
        topBar.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));

        JButton logoutBtn = new JButton("← Déconnexion");
        logoutBtn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        logoutBtn.setBackground(new Color(255,255,255,30));
        logoutBtn.setForeground(TEXT_COLOR);
        logoutBtn.setBorderPainted(false);
        logoutBtn.setFocusPainted(false);
        logoutBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutBtn.addActionListener(e -> {
            dispose();
           new LoginFrame(gestionnaireEvenements);
        });

        topBar.add(logoutBtn, BorderLayout.WEST);
        add(topBar, BorderLayout.NORTH);
        
        // Panel principal avec marges
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setOpaque(false);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        
        // Titre
        JLabel titleLabel = new JLabel("Gestion des Événements");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Sous-titre
        JLabel subtitleLabel = new JLabel("Tableau de bord");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        subtitleLabel.setForeground(new Color(255, 255, 255, 200));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Espacement
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(subtitleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 60)));
        
        // Panel pour les boutons
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(4, 1, 0, 20));
        buttonsPanel.setOpaque(false);
        buttonsPanel.setMaximumSize(new Dimension(400, 340));
        
        // Bouton Ajouter un événement
        JButton btnAjouter = createStyledButton(
            "➕ Ajouter un événement",
            PRIMARY_COLOR,
            PRIMARY_HOVER
        );
        btnAjouter.addActionListener(e -> ouvrirAjouterEvenement());
        
        // Bouton Rechercher
        JButton btnRechercher = createStyledButton(
            "🔍 Rechercher un événement",
            SECONDARY_COLOR,
            SECONDARY_HOVER
        );
        btnRechercher.addActionListener(e -> ouvrirRechercher());
        
        // Bouton Modifier
        JButton btnModifier = createStyledButton(
            "✏️ Modifier un événement",
            WARNING_COLOR,
            WARNING_HOVER
        );
        btnModifier.addActionListener(e -> ouvrirModifier());
        
        // Bouton Supprimer
        JButton btnSupprimer = createStyledButton(
            "🗑️ Supprimer un événement",
            DANGER_COLOR,
            DANGER_HOVER
        );
        btnSupprimer.addActionListener(e -> ouvrirSupprimer());
        
        buttonsPanel.add(btnAjouter);
        buttonsPanel.add(btnRechercher);
        buttonsPanel.add(btnModifier);
        buttonsPanel.add(btnSupprimer);
        
        mainPanel.add(buttonsPanel);
        
        // Footer
        mainPanel.add(Box.createRigidArea(new Dimension(0, 60)));
        JLabel footerLabel = new JLabel("Sélectionnez une action pour continuer");
        footerLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        footerLabel.setForeground(new Color(156, 163, 175));
        footerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(footerLabel);
        
        add(mainPanel, BorderLayout.CENTER);
    }
    
    private JButton createStyledButton(String text, Color bgColor, Color hoverColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 18));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(400, 70));
        
        // Effet hover
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hoverColor);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });
        
        return button;
    }
    
    private void ouvrirAjouterEvenement() {
        dispose();
        AjouterEvenementFrame ajouterFrame = new AjouterEvenementFrame(gestionnaireEvenements);
        ajouterFrame.setVisible(true);
    }
    
    private void ouvrirRechercher() {
        dispose();
        RechercherFrame rechercherFrame = new RechercherFrame(gestionnaireEvenements);
        rechercherFrame.setVisible(true);
    }
    
    private void ouvrirModifier() {
        dispose();
        ModifierFrame modifierFrame = new ModifierFrame(gestionnaireEvenements);
        modifierFrame.setVisible(true);
    }
    
    private void ouvrirSupprimer() {
        dispose();
        SupprimerFrame supprimerFrame = new SupprimerFrame(gestionnaireEvenements);
        supprimerFrame.setVisible(true);
    }
    
    // Main pour tester
    public static void main(String[] args) {
        gestionnaire gest = new gestionnaire();
        SwingUtilities.invokeLater(() -> {
            DashboardFrame frame = new DashboardFrame(gest);
            frame.setVisible(true);
        });
    }
}