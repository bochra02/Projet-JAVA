package frames;
import java.awt.*;
import javax.swing.*;
import models.*;
import services.gestionnaire;

public class AjouterEvenementFrame extends JFrame {
    private gestionnaire gestionnaireEvenements;
    
    // Couleurs du thème
    private final Color BACKGROUND_COLOR = new Color(240, 242, 245);
    private final Color CARD_COLOR = Color.WHITE;
    private final Color PRIMARY_COLOR = new Color(37, 99, 235);
    private final Color SUCCESS_COLOR = new Color(16, 185, 129);
    private final Color TEXT_COLOR = new Color(31, 41, 55);
    private final Color BORDER_COLOR = new Color(209, 213, 219);
    
    // Champs du formulaire
    private JTextField txtNom, txtLieu, txtDate, txtHeureDebut, txtHeureFin;
    private JTextArea txtDescription;
    private JTextField txtOrganisateur, txtPrix, txtCapacite, txtInscrits;
    private JComboBox<String> comboType, comboStatut;
    
    // Champs spécifiques Conference
    private JTextField txtIntervenant, txtDomaine;
    
    // Champs spécifiques Atelier
    private JTextField txtMateriel;
    private JComboBox<String> comboNiveau;
    
    // Champs spécifiques EvenementSocial
    private JTextField txtTheme, txtRefreshments;
    
    public AjouterEvenementFrame(gestionnaire gestionnaire) {
        this.gestionnaireEvenements = gestionnaire;
        initializeUI();
    }
    
    private void initializeUI() {
        setTitle("Ajouter un Événement");
        setSize(700, 900);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setContentPane(new GradientBackgroundPanel());
        setLayout(new BorderLayout(20, 20));
        
        // Panel principal avec scroll
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setOpaque(false);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        // Titre
        JLabel titleLabel = new JLabel("📝 Nouvel Événement");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        // Sous-titre
        JLabel subtitleLabel = new JLabel("Complétez le formulaire pour créer un nouvel événement");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(255, 255, 255, 200));
        subtitleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(subtitleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Card contenant le formulaire
        JPanel formCard = createFormCard();
        mainPanel.add(formCard);
        
        // Panel pour les boutons
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        JPanel buttonsPanel = createButtonsPanel();
        mainPanel.add(buttonsPanel);
        
        // Scroll pane
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private JPanel createFormCard() {
        JPanel card = new JPanel();
        card.setLayout(new GridBagLayout());
        card.setBackground(CARD_COLOR);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        int row = 0;
        
        // Nom de l'événement
        addFormField(card, gbc, row++, "Nom de l'événement *", 
            txtNom = createTextField());
        
        // Type d'événement
        String[] types = {"Conférence", "Atelier", "Evenement social"};
        addFormField(card, gbc, row++, "Type d'événement *", 
            comboType = new JComboBox<>(types));
        styleComboBox(comboType);
        
        // Lieu
        addFormField(card, gbc, row++, "Lieu *", 
            txtLieu = createTextField());
        
        // Date
        addFormField(card, gbc, row++, "Date (format: JJ/MM/AAAA) *", 
            txtDate = createTextField());
        
        // Heure début
        addFormField(card, gbc, row++, "Heure début (format: HH:MM) *", 
            txtHeureDebut = createTextField());
        
        // Heure fin
        addFormField(card, gbc, row++, "Heure fin (format: HH:MM) *", 
            txtHeureFin = createTextField());
        
        // Description
        gbc.gridx = 0;
        gbc.gridy = row;
        JLabel lblDesc = new JLabel("Description *");
        lblDesc.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblDesc.setForeground(TEXT_COLOR);
        card.add(lblDesc, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = row++;
        txtDescription = new JTextArea(4, 20);
        txtDescription.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtDescription.setLineWrap(true);
        txtDescription.setWrapStyleWord(true);
        txtDescription.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        JScrollPane scrollDesc = new JScrollPane(txtDescription);
        scrollDesc.setPreferredSize(new Dimension(300, 80));
        card.add(scrollDesc, gbc);
        
        // Organisateur
        addFormField(card, gbc, row++, "Organisateur *", 
            txtOrganisateur = createTextField());
        
        // ========== CHAMPS SPÉCIFIQUES CONFÉRENCE ==========
        addFormField(card, gbc, row++, "Intervenant (si Conférence)", 
            txtIntervenant = createTextField());
        
        addFormField(card, gbc, row++, "Domaine (si Conférence)", 
            txtDomaine = createTextField());
        
        // ========== CHAMPS SPÉCIFIQUES ATELIER ==========
        addFormField(card, gbc, row++, "Matériel nécessaire (si Atelier)", 
            txtMateriel = createTextField());
        
        String[] niveaux = {"débutant", "intermédiaire", "avancé"};
        addFormField(card, gbc, row++, "Niveau (si Atelier)", 
            comboNiveau = new JComboBox<>(niveaux));
        styleComboBox(comboNiveau);
        
        // ========== CHAMPS SPÉCIFIQUES ÉVÉNEMENT SOCIAL ==========
        addFormField(card, gbc, row++, "Thème (si Événement social)", 
            txtTheme = createTextField());
        
        addFormField(card, gbc, row++, "Rafraîchissements (si Événement social)", 
            txtRefreshments = createTextField());
        
        // Prix
        addFormField(card, gbc, row++, "Prix (DT) *", 
            txtPrix = createTextField());
        
        // Capacité maximale
        addFormField(card, gbc, row++, "Capacité maximale *", 
            txtCapacite = createTextField());
        
        // Nombre d'inscrits
        addFormField(card, gbc, row++, "Nombre d'inscrits *", 
            txtInscrits = createTextField());
        
        // Statut
        String[] statuts = {"ouvert", "complet", "annulé", "terminé"};
        addFormField(card, gbc, row++, "Statut *", 
            comboStatut = new JComboBox<>(statuts));
        styleComboBox(comboStatut);
        
        return card;
    }
    
    private JTextField createTextField() {
        JTextField field = new JTextField(20);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        return field;
    }
    
    private void styleComboBox(JComboBox<String> combo) {
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        combo.setBackground(Color.WHITE);
    }
    
    private void addFormField(JPanel panel, GridBagConstraints gbc, int row, 
                              String label, JComponent field) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lbl.setForeground(TEXT_COLOR);
        panel.add(lbl, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = row;
        panel.add(field, gbc);
    }
    
    private JPanel createButtonsPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panel.setBackground(BACKGROUND_COLOR);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        
        // Bouton Retour vers le Dashboard
        JButton btnRetour = createButton("← Retour", new Color(107, 114, 128), 
                                         new Color(75, 85, 99));
        btnRetour.addActionListener(e -> {
            dispose();
            DashboardFrame dash = new DashboardFrame(gestionnaireEvenements);
            dash.setVisible(true);
        });

        // Bouton Annuler
        JButton btnAnnuler = createButton("Annuler", new Color(107, 114, 128), 
                                          new Color(75, 85, 99));
        btnAnnuler.addActionListener(e -> dispose());
        
        // Bouton Ajouter
        JButton btnAjouter = createButton("Ajouter l'événement", SUCCESS_COLOR, 
                                          new Color(5, 150, 105));
        btnAjouter.addActionListener(e -> ajouterEvenement());
        
        panel.add(btnRetour);
        panel.add(btnAnnuler);
        panel.add(btnAjouter);
        
        return panel;
    }
    
    private JButton createButton(String text, Color bgColor, Color hoverColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(180, 40));
        
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
    
    private void ajouterEvenement() {
        try {
            // Récupérer les valeurs communes
            String nom = txtNom.getText().trim();
            String type = (String) comboType.getSelectedItem();
            String lieu = txtLieu.getText().trim();
            String date = txtDate.getText().trim();
            String heureDebut = txtHeureDebut.getText().trim();
            String heureFin = txtHeureFin.getText().trim();
            String description = txtDescription.getText().trim();
            String organisateur = txtOrganisateur.getText().trim();
            float prix = Float.parseFloat(txtPrix.getText().trim());
            int capacite = Integer.parseInt(txtCapacite.getText().trim());
            int inscrits = Integer.parseInt(txtInscrits.getText().trim());
            String statut = (String) comboStatut.getSelectedItem();
            
            // Créer l'événement selon le type
            Evenement evt = creerEvenementSelonType(type, nom, heureDebut, heureFin, 
                lieu, description, capacite, inscrits, organisateur, statut, prix, date);
            
            if (evt == null) {
                return; // L'erreur a déjà été affichée dans creerEvenementSelonType
            }
            
            // Ajouter via le gestionnaire
            boolean success = gestionnaireEvenements.ajouterEvenement(evt);
            
            if (success) {
                JOptionPane.showMessageDialog(this, 
                    "✅ Événement ajouté avec succès !", 
                    "Succès", 
                    JOptionPane.INFORMATION_MESSAGE);
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "❌ Impossible d'ajouter l'événement. Vérifiez les informations.", 
                    "Erreur", 
                    JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, 
                "❌ Veuillez entrer des valeurs numériques valides pour le prix, la capacité et les inscrits.", 
                "Erreur de saisie", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private Evenement creerEvenementSelonType(String type, String nom, String heureDebut, 
            String heureFin, String lieu, String description, int capacite, int inscrits, 
            String organisateur, String statut, float prix, String date) {
        
        if (type.equals("Conférence")) {
            String intervenant = txtIntervenant.getText().trim();
            String domaine = txtDomaine.getText().trim();
            
            if (intervenant.isEmpty() || domaine.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "❌ Veuillez remplir l'intervenant et le domaine pour une conférence.", 
                    "Erreur", 
                    JOptionPane.ERROR_MESSAGE);
                return null;
            }
            
            return new Conference(nom, heureDebut, heureFin, lieu, description, 
                                  capacite, inscrits, type, organisateur, statut, prix, date,
                                  intervenant, domaine);
                                  
        } else if (type.equals("Atelier")) {
            String materiel = txtMateriel.getText().trim();
            String niveau = (String) comboNiveau.getSelectedItem();
            
            if (materiel.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "❌ Veuillez remplir le matériel nécessaire pour un atelier.", 
                    "Erreur", 
                    JOptionPane.ERROR_MESSAGE);
                return null;
            }
            
            return new Atelier(nom, heureDebut, heureFin, lieu, description, 
                               capacite, inscrits, type, organisateur, statut, prix, date,
                               materiel, niveau);
                               
        } else if (type.equals("Evenement social")) {
            String theme = txtTheme.getText().trim();
            String refreshments = txtRefreshments.getText().trim();
            
            if (theme.isEmpty() || refreshments.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "❌ Veuillez remplir le thème et les rafraîchissements pour un événement social.", 
                    "Erreur", 
                    JOptionPane.ERROR_MESSAGE);
                return null;
            }
            
            return new evenementSocial(nom, heureDebut, heureFin, lieu, description, 
                                       capacite, inscrits, type, organisateur, statut, prix, date,
                                       theme, refreshments);
        } else {
            JOptionPane.showMessageDialog(this, 
                "❌ Type d'événement non supporté.", 
                "Erreur", 
                JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    
   private void clearForm() {
     txtNom.setText("");
     txtLieu.setText("");
     txtDate.setText("");
     txtHeureDebut.setText("");
     txtHeureFin.setText("");
     txtDescription.setText("");
     txtOrganisateur.setText("");
     txtIntervenant.setText("");
     txtDomaine.setText("");
     txtMateriel.setText("");
     txtTheme.setText("");
     txtRefreshments.setText("");
     txtPrix.setText("");
     txtCapacite.setText("");
     txtInscrits.setText("");
     comboType.setSelectedIndex(0);
     comboStatut.setSelectedIndex(0);
     comboNiveau.setSelectedIndex(0);
    } 
} 