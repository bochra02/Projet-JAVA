package frames;

import java.awt.*;
import javax.swing.*;
import models.*;
import services.gestionnaire;

public class SupprimerFrame extends JFrame {
    private gestionnaire gestionnaireEvenements;
    
    // Couleurs du thème
    private final Color BACKGROUND_COLOR = new Color(240, 242, 245);
    private final Color CARD_COLOR = Color.WHITE;
    private final Color PRIMARY_COLOR = new Color(37, 99, 235);
    private final Color PRIMARY_HOVER = new Color(29, 78, 216);
    private final Color TEXT_COLOR = new Color(31, 41, 55);
    private final Color BORDER_COLOR = new Color(209, 213, 219);
    private final Color DANGER_COLOR = new Color(239, 68, 68);
    private final Color DANGER_HOVER = new Color(220, 38, 38);
    
    // Composants de l'interface
    private JTextField txtRecherche;
    private JTextArea txtResultat;
    private JButton btnRechercher;
    private JButton btnSupprimer;
    
    // Événement trouvé
    private Evenement evenementTrouve;
    
    public SupprimerFrame(gestionnaire gestionnaire) {
        this.gestionnaireEvenements = gestionnaire;
        initializeUI();
    }
    
    private void initializeUI() {
        setTitle("Supprimer un Événement");
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
            DashboardFrame dash = new DashboardFrame(gestionnaireEvenements);
            dash.setVisible(true);
        });

        topBar.add(retourBtn, BorderLayout.WEST);
        add(topBar, BorderLayout.NORTH);
        
        // Panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setOpaque(false);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        
        // Titre
        JLabel titleLabel = new JLabel("🗑️ Supprimer un Événement");
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
        scrollPane.setPreferredSize(new Dimension(600, 250));
        
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(CARD_COLOR);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Bouton Supprimer (initialement caché)
        btnSupprimer = createStyledButton("🗑️ Supprimer cet événement", DANGER_COLOR, DANGER_HOVER);
        btnSupprimer.setPreferredSize(new Dimension(220, 45));
        btnSupprimer.setVisible(false);
        btnSupprimer.addActionListener(e -> supprimerEvenement());
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(CARD_COLOR);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        buttonPanel.add(btnSupprimer);
        
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        panel.add(contentPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
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
    
    private void rechercherEvenement() {
        String nomRecherche = txtRecherche.getText().trim();
        
        // Validation de la saisie
        if (nomRecherche.isEmpty()) {
            txtResultat.setForeground(new Color(239, 68, 68));
            txtResultat.setText("❌ Erreur\n\nVeuillez entrer le nom d'un événement.");
            btnSupprimer.setVisible(false);
            return;
        }
        
        // Recherche de l'événement
        Evenement evt = gestionnaireEvenements.consulterEvenement(nomRecherche);
        
        if (evt == null) {
            // Événement non trouvé
            txtResultat.setForeground(new Color(239, 68, 68));
            txtResultat.setText("❌ Événement introuvable\n\n" +
                "Aucun événement trouvé avec le nom : \"" + nomRecherche + "\"\n\n" +
                "Vérifiez l'orthographe et réessayez.");
            btnSupprimer.setVisible(false);
            evenementTrouve = null;
        } else {
            // Événement trouvé : affichage des détails
            evenementTrouve = evt;
            afficherDetailsEvenement(evt);
            btnSupprimer.setVisible(true);
        }
    }
    
    private void afficherDetailsEvenement(Evenement evt) {
        txtResultat.setForeground(TEXT_COLOR);
        
        StringBuilder details = new StringBuilder();
        details.append("✅ Événement trouvé !\n");
        details.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n\n");
        
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
        
        // Informations spécifiques selon le type
        String typeSpecifique = getInformationsSpecifiques(evt);
        if (!typeSpecifique.isEmpty()) {
            details.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
            details.append(typeSpecifique);
        }
        
        details.append("\n⚠️ ATTENTION : Cette action est irréversible !");
        
        txtResultat.setText(details.toString());
    }
    
    private String getInformationsSpecifiques(Evenement evt) {
        String type = evt.getTypeEvenement();
        StringBuilder info = new StringBuilder();
        
        if (type.equals("Conférence")) {
            Conference conf = (Conference) evt;
            info.append("🎤 INFORMATIONS CONFÉRENCE\n\n");
            info.append("Intervenant : ").append(conf.getIntervenant()).append("\n");
            info.append("Domaine : ").append(conf.getDomaine()).append("\n");
            
        } else if (type.equals("Atelier")) {
            Atelier atelier = (Atelier) evt;
            info.append("🔧 INFORMATIONS ATELIER\n\n");
            info.append("Niveau : ").append(atelier.getNiveau()).append("\n");
            info.append("Matériel nécessaire : ").append(atelier.getMaterielNecessaire()).append("\n");
            
        } else if (type.equals("Evenement social")) {
            evenementSocial social = (evenementSocial) evt;
            info.append("🎉 INFORMATIONS ÉVÉNEMENT SOCIAL\n\n");
            info.append("Thème : ").append(social.getTheme()).append("\n");
            info.append("Rafraîchissements : ").append(social.getRefreshments()).append("\n");
        }
        
        return info.toString();
    }
    
    private void supprimerEvenement() {
        if (evenementTrouve == null) {
            JOptionPane.showMessageDialog(this, 
                "❌ Aucun événement sélectionné.", 
                "Erreur", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Confirmation de suppression
        int confirmation = JOptionPane.showConfirmDialog(this, 
            "⚠️ Êtes-vous sûr de vouloir supprimer l'événement :\n\n\"" + 
            evenementTrouve.getNomEvenement() + "\" ?\n\n" +
            "Cette action est irréversible.", 
            "Confirmation de suppression", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (confirmation == JOptionPane.YES_OPTION) {
            boolean success = gestionnaireEvenements.supprimerEvenement(evenementTrouve.getNomEvenement());
            
            if (success) {
                JOptionPane.showMessageDialog(this, 
                    "✅ Événement supprimé avec succès !", 
                    "Succès", 
                    JOptionPane.INFORMATION_MESSAGE);
                
                // Réinitialiser l'interface
                txtRecherche.setText("");
                txtResultat.setText("Aucune recherche effectuée.\n\nEntrez le nom d'un événement et cliquez sur Rechercher.");
                txtResultat.setForeground(new Color(107, 114, 128));
                btnSupprimer.setVisible(false);
                evenementTrouve = null;
            } else {
                JOptionPane.showMessageDialog(this, 
                    "❌ Impossible de supprimer l'événement.", 
                    "Erreur", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}