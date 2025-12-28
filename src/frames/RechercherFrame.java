package frames;

import authentification.ui.frames.VisitorFrame;
import java.awt.*;
import javax.swing.*;
import models.*;
import services.gestionnaire;

/**
 * Frame de recherche d'événements
 * Utilise le POLYMORPHISME : on manipule des objets Evenement (classe mère)
 * qui peuvent être Conference, Atelier ou EvenementSocial (classes filles)
 */
public class RechercherFrame extends JFrame {
    // ========== ENCAPSULATION ==========
    // Attributs privés : les données sont protégées et accessibles uniquement via cette classe
    private gestionnaire gestionnaireEvenements;  // Référence au gestionnaire (service métier)
    private boolean isVisitorMode;  // NOUVEAU : pour savoir si c'est un visiteur ou admin
    
    // Couleurs du thème
    private final Color BACKGROUND_COLOR = new Color(240, 242, 245);
    private final Color CARD_COLOR = Color.WHITE;
    private final Color PRIMARY_COLOR = new Color(37, 99, 235);
    private final Color PRIMARY_HOVER = new Color(29, 78, 216);
    private final Color TEXT_COLOR = new Color(31, 41, 55);
    private final Color BORDER_COLOR = new Color(209, 213, 219);
    private final Color SUCCESS_COLOR = new Color(16, 185, 129);
    
    // Composants de l'interface
    private JTextField txtRecherche;
    private JTextArea txtResultat;
    private JButton btnRechercher;
    
    /**
     * Constructeur pour Admin/Dashboard : injection de dépendance du gestionnaire
     * @param gestionnaire - Service de gestion des événements
     */
    public RechercherFrame(gestionnaire gestionnaire) {
        this.gestionnaireEvenements = gestionnaire;
        this.isVisitorMode = false;  // Mode Admin par défaut
        initializeUI();
    }
    
    /**
     * NOUVEAU Constructeur pour Visiteur avec flag
     * @param gestionnaire - Service de gestion des événements
     * @param isVisitor - true si appelé depuis VisitorFrame
     */
    public RechercherFrame(gestionnaire gestionnaire, boolean isVisitor) {
        this.gestionnaireEvenements = gestionnaire;
        this.isVisitorMode = isVisitor;
        initializeUI();
    }
    
    /**
     * Initialise l'interface utilisateur
     * Méthode privée : ENCAPSULATION de la logique d'initialisation
     */
    private void initializeUI() {
        setTitle("Rechercher un Événement");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setContentPane(new GradientBackgroundPanel());
        setLayout(new BorderLayout(20, 20));
        
        // Top bar with Retour button
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(BACKGROUND_COLOR);
        topBar.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));

        JButton retourBtn = new JButton("← Retour");
        retourBtn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        retourBtn.setBackground(new Color(255,255,255,30));
        retourBtn.setForeground(TEXT_COLOR);
        retourBtn.setBorderPainted(false);
        retourBtn.setFocusPainted(false);
        retourBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        retourBtn.addActionListener(e -> {
            dispose();
            // CHANGEMENT ICI : Vérifier le mode
            if (isVisitorMode) {
                // Si visiteur, retourner vers VisitorFrame
                VisitorFrame visitorFrame = new VisitorFrame(gestionnaireEvenements);
                visitorFrame.setVisible(true);
            } else {
                // Si admin, retourner vers DashboardFrame
                DashboardFrame dash = new DashboardFrame(gestionnaireEvenements);
                dash.setVisible(true);
            }
        });

        topBar.add(retourBtn, BorderLayout.WEST);
        add(topBar, BorderLayout.NORTH);
        
        // Panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setOpaque(false);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        
        // Titre
        JLabel titleLabel = new JLabel("🔍 Rechercher un Événement");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        // Sous-titre
        JLabel subtitleLabel = new JLabel("Entrez le nom exact de l'événement");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(255, 255, 255, 200));
        subtitleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(subtitleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        
        // Panel de recherche
        JPanel searchPanel = createSearchPanel();
        mainPanel.add(searchPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Panel des résultats
        JPanel resultPanel = createResultPanel();
        mainPanel.add(resultPanel);
        
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    /**
     * Crée le panel de recherche avec champ de saisie et bouton
     */
    private JPanel createSearchPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(CARD_COLOR);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        
        // Label
        JLabel lblRecherche = new JLabel("Nom de l'événement");
        lblRecherche.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblRecherche.setForeground(TEXT_COLOR);
        lblRecherche.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(lblRecherche);
        panel.add(Box.createRigidArea(new Dimension(0, 8)));
        
        // Panel pour le champ et le bouton
        JPanel inputPanel = new JPanel(new BorderLayout(10, 0));
        inputPanel.setBackground(CARD_COLOR);
        inputPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        
        // Champ de texte
        txtRecherche = new JTextField();
        txtRecherche.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtRecherche.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));
        txtRecherche.addActionListener(e -> rechercherEvenement());
        
        // Bouton rechercher
        btnRechercher = createStyledButton("🔍 Rechercher", PRIMARY_COLOR, PRIMARY_HOVER);
        btnRechercher.setPreferredSize(new Dimension(150, 45));
        btnRechercher.addActionListener(e -> rechercherEvenement());
        
        inputPanel.add(txtRecherche, BorderLayout.CENTER);
        inputPanel.add(btnRechercher, BorderLayout.EAST);
        
        panel.add(inputPanel);
        
        return panel;
    }
    
    /**
     * Crée le panel d'affichage des résultats
     */
    private JPanel createResultPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(CARD_COLOR);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        
        // Label titre
        JLabel lblResultat = new JLabel("📋 Résultat de la recherche");
        lblResultat.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblResultat.setForeground(TEXT_COLOR);
        panel.add(lblResultat, BorderLayout.NORTH);
        
        // Zone de texte pour le résultat
        txtResultat = new JTextArea();
        txtResultat.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtResultat.setEditable(false);
        txtResultat.setLineWrap(true);
        txtResultat.setWrapStyleWord(true);
        txtResultat.setBackground(new Color(249, 250, 251));
        txtResultat.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        txtResultat.setText("Aucune recherche effectuée.\n\nEntrez le nom d'un événement et cliquez sur Rechercher.");
        txtResultat.setForeground(new Color(107, 114, 128));
        
        JScrollPane scrollPane = new JScrollPane(txtResultat);
        scrollPane.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1));
        scrollPane.setPreferredSize(new Dimension(600, 300));
        
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(CARD_COLOR);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        
        panel.add(contentPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Crée un bouton stylisé avec effet hover
     */
    private JButton createStyledButton(String text, Color bgColor, Color hoverColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
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
    
    /**
     * ========== APPEL AU GESTIONNAIRE (SERVICE MÉTIER) ==========
     * Recherche un événement via le gestionnaire
     * Démonstration du POLYMORPHISME : evt peut être Conference, Atelier ou EvenementSocial
     */
    private void rechercherEvenement() {
        String nomRecherche = txtRecherche.getText().trim();
        
        // Validation de la saisie
        if (nomRecherche.isEmpty()) {
            txtResultat.setForeground(new Color(239, 68, 68));
            txtResultat.setText("❌ Erreur\n\nVeuillez entrer le nom d'un événement.");
            return;
        }
        
        // ========== APPEL À LA COUCHE SERVICE ==========
        // On délègue la logique métier au gestionnaire
        // Le gestionnaire utilise l'ENCAPSULATION : il cache la complexité de la recherche
        Evenement evt = gestionnaireEvenements.consulterEvenement(nomRecherche);
        
        // ========== POLYMORPHISME ==========
        // evt est de type Evenement (classe mère) mais peut contenir
        // une instance de Conference, Atelier ou EvenementSocial (classes filles)
        
        if (evt == null) {
            // Événement non trouvé
            txtResultat.setForeground(new Color(239, 68, 68));
            txtResultat.setText("❌ Événement introuvable\n\n" +
                "Aucun événement trouvé avec le nom : \"" + nomRecherche + "\"\n\n" +
                "Vérifiez l'orthographe et réessayez.");
        } else {
            // Événement trouvé : affichage des détails
            afficherDetailsEvenement(evt);
        }
    }
    
    /**
     * ========== POLYMORPHISME ET ENCAPSULATION ==========
     * Affiche les détails d'un événement
     * @param evt - Objet Evenement (peut être Conference, Atelier ou EvenementSocial)
     * 
     * POLYMORPHISME : On utilise les getters de la classe mère Evenement
     * qui sont accessibles pour toutes les classes filles
     */
    private void afficherDetailsEvenement(Evenement evt) {
        txtResultat.setForeground(TEXT_COLOR);
        
        StringBuilder details = new StringBuilder();
        details.append("✅ Événement trouvé !\n");
        details.append("════════════════════════════════════════\n\n");
        
        // ========== ENCAPSULATION ==========
        // On accède aux attributs via les getters (méthodes publiques)
        // Les attributs sont protégés (protected/private) dans la classe Evenement
        
        details.append("📌 NOM : ").append(evt.getNomEvenement()).append("\n\n");
        details.append("🏷️  TYPE : ").append(evt.getTypeEvenement()).append("\n\n");
        details.append("📅 DATE : ").append(evt.getDateEvenement()).append("\n\n");
        details.append("🕐 HORAIRE : ").append(evt.getHeureDebut())
               .append(" - ").append(evt.getHeureFin()).append("\n\n");
        details.append("📍 LIEU : ").append(evt.getLieu()).append("\n\n");
        details.append("👤 ORGANISATEUR : ").append(evt.getOrganisateur()).append("\n\n");
        details.append("📝 DESCRIPTION : ").append(evt.getDescription()).append("\n\n");
        details.append("💰 PRIX : ").append(evt.getPrix()).append(" DT\n\n");
        details.append("👥 CAPACITÉ : ").append(evt.getNbreInscrits())
               .append(" / ").append(evt.getCapaciteMax()).append(" inscrits\n\n");
        details.append("📊 STATUT : ").append(evt.getStatut().toUpperCase()).append("\n\n");
        
        // ========== POLYMORPHISME AVANCÉ ==========
        // Récupération des informations spécifiques selon le type réel de l'objet
        String typeSpecifique = getInformationsSpecifiques(evt);
        if (!typeSpecifique.isEmpty()) {
            details.append("════════════════════════════════════════\n");
            details.append(typeSpecifique);
        }
        
        txtResultat.setText(details.toString());
    }
    
    /**
     * ========== POLYMORPHISME : DOWNCASTING ==========
     * Récupère les informations spécifiques selon le type d'événement
     * 
     * @param evt - Objet de type Evenement (classe mère)
     * @return String - Informations spécifiques formatées
     * 
     * POLYMORPHISME : On cast l'objet Evenement vers son type réel
     * (Conference, Atelier ou EvenementSocial) pour accéder aux attributs spécifiques
     * 
     * Ceci est possible grâce à l'HÉRITAGE : Conference, Atelier et EvenementSocial
     * héritent tous d'Evenement
     */
    private String getInformationsSpecifiques(Evenement evt) {
        String type = evt.getTypeEvenement();
        StringBuilder info = new StringBuilder();
        
        // ========== DOWNCASTING : Conversion de la classe mère vers la classe fille ==========
        
        if (type.equals("Conférence")) {
            // Cast vers Conference pour accéder aux attributs spécifiques
            Conference conf = (Conference) evt;  // DOWNCASTING
            info.append("🎤 INFORMATIONS CONFÉRENCE\n\n");
            // ENCAPSULATION : Accès via getters
            info.append("Intervenant : ").append(conf.getIntervenant()).append("\n");
            info.append("Domaine : ").append(conf.getDomaine()).append("\n");
            
        } else if (type.equals("Atelier")) {
            // Cast vers Atelier pour accéder aux attributs spécifiques
            Atelier atelier = (Atelier) evt;  // DOWNCASTING
            info.append("🔧 INFORMATIONS ATELIER\n\n");
            // ENCAPSULATION : Accès via getters
            info.append("Niveau : ").append(atelier.getNiveau()).append("\n");
            info.append("Matériel nécessaire : ").append(atelier.getMaterielNecessaire()).append("\n");
            
        } else if (type.equals("Evenement social")) {
            // Cast vers EvenementSocial pour accéder aux attributs spécifiques
            evenementSocial social = (evenementSocial) evt;  // DOWNCASTING
            info.append("🎉 INFORMATIONS ÉVÉNEMENT SOCIAL\n\n");
            // ENCAPSULATION : Accès via getters
            info.append("Thème : ").append(social.getTheme()).append("\n");
            info.append("Rafraîchissements : ").append(social.getRefreshments()).append("\n");
        }
        
        return info.toString();
    }
}
