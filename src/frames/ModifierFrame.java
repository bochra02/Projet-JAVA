package frames;

import java.awt.*;
import javax.swing.*;
import models.*;
import services.gestionnaire;

public class ModifierFrame extends JFrame {
    private gestionnaire gestionnaireEvenements;
    
    // Couleurs du thème
    private final Color BACKGROUND_COLOR = new Color(240, 242, 245);
    private final Color CARD_COLOR = Color.WHITE;
    private final Color PRIMARY_COLOR = new Color(37, 99, 235);
    private final Color PRIMARY_HOVER = new Color(29, 78, 216);
    private final Color SUCCESS_COLOR = new Color(16, 185, 129);
    private final Color TEXT_COLOR = new Color(31, 41, 55);
    private final Color BORDER_COLOR = new Color(209, 213, 219);
    
    // Composants de recherche
    private JTextField txtRechercheNom;
    private JButton btnRechercher;
    
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
    
    // Panel du formulaire
    private JPanel formCard;
    private JPanel buttonsPanel;
    
    // Événement actuel
    private Evenement evenementActuel;
    
    public ModifierFrame(gestionnaire gestionnaire) {
        this.gestionnaireEvenements = gestionnaire;
        initializeUI();
    }
    
    private void initializeUI() {
        setTitle("Modifier un Événement");
        setSize(700, 900);
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
        
        // Panel principal avec scroll
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setOpaque(false);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        // Titre
        JLabel titleLabel = new JLabel("✏️ Modifier un Événement");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        // Sous-titre
        JLabel subtitleLabel = new JLabel("Recherchez l'événement à modifier");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(255, 255, 255, 200));
        subtitleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(subtitleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Panel de recherche
        JPanel searchPanel = createSearchPanel();
        mainPanel.add(searchPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Card contenant le formulaire (initialement caché)
        formCard = createFormCard();
        formCard.setVisible(false);
        mainPanel.add(formCard);
        
        // Panel pour les boutons (initialement caché)
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        buttonsPanel = createButtonsPanel();
        buttonsPanel.setVisible(false);
        mainPanel.add(buttonsPanel);
        
        // Scroll pane
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
        txtRechercheNom = new JTextField();
        txtRechercheNom.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtRechercheNom.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));
        txtRechercheNom.addActionListener(e -> rechercherEvenement());
        
        // Bouton rechercher
        btnRechercher = createStyledButton("🔍 Rechercher", PRIMARY_COLOR, PRIMARY_HOVER);
        btnRechercher.setPreferredSize(new Dimension(150, 45));
        btnRechercher.addActionListener(e -> rechercherEvenement());
        
        inputPanel.add(txtRechercheNom, BorderLayout.CENTER);
        inputPanel.add(btnRechercher, BorderLayout.EAST);
        
        panel.add(inputPanel);
        
        return panel;
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

        // Bouton Modifier
        JButton btnModifier = createButton("Modifier l'événement", SUCCESS_COLOR, 
                                          new Color(5, 150, 105));
        btnModifier.addActionListener(e -> modifierEvenement());

        panel.add(btnRetour);
        panel.add(btnAnnuler);
        panel.add(btnModifier);
        
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
        String nomRecherche = txtRechercheNom.getText().trim();
        
        if (nomRecherche.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "❌ Veuillez entrer le nom d'un événement.", 
                "Erreur", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Evenement evt = gestionnaireEvenements.consulterEvenement(nomRecherche);
        
        if (evt == null) {
            JOptionPane.showMessageDialog(this, 
                "❌ Aucun événement trouvé avec le nom : \"" + nomRecherche + "\"", 
                "Événement introuvable", 
                JOptionPane.ERROR_MESSAGE);
            formCard.setVisible(false);
            buttonsPanel.setVisible(false);
        } else {
            evenementActuel = evt;
            remplirFormulaire(evt);
            formCard.setVisible(true);
            buttonsPanel.setVisible(true);
            revalidate();
            repaint();
        }
    }
    
    private void remplirFormulaire(Evenement evt) {
        txtNom.setText(evt.getNomEvenement());
        txtLieu.setText(evt.getLieu());
        txtDate.setText(evt.getDateEvenement());
        txtHeureDebut.setText(evt.getHeureDebut());
        txtHeureFin.setText(evt.getHeureFin());
        txtDescription.setText(evt.getDescription());
        txtOrganisateur.setText(evt.getOrganisateur());
        txtPrix.setText(String.valueOf(evt.getPrix()));
        txtCapacite.setText(String.valueOf(evt.getCapaciteMax()));
        txtInscrits.setText(String.valueOf(evt.getNbreInscrits()));
        
        comboType.setSelectedItem(evt.getTypeEvenement());
        comboStatut.setSelectedItem(evt.getStatut());
        
        // Remplir les champs spécifiques
        String type = evt.getTypeEvenement();
        
        if (type.equals("Conférence")) {
            Conference conf = (Conference) evt;
            txtIntervenant.setText(conf.getIntervenant());
            txtDomaine.setText(conf.getDomaine());
        } else if (type.equals("Atelier")) {
            Atelier atelier = (Atelier) evt;
            txtMateriel.setText(atelier.getMaterielNecessaire());
            comboNiveau.setSelectedItem(atelier.getNiveau());
        } else if (type.equals("Evenement social")) {
            evenementSocial social = (evenementSocial) evt;
            txtTheme.setText(social.getTheme());
            txtRefreshments.setText(social.getRefreshments());
        }
    }
    
    private void modifierEvenement() {
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
            
            // Créer le nouvel événement avec les modifications
            Evenement evt = creerEvenementSelonType(type, nom, heureDebut, heureFin, 
                lieu, description, capacite, inscrits, organisateur, statut, prix, date);
            
            if (evt == null) {
                return;
            }
            
            // Modifier via le gestionnaire
            boolean success = gestionnaireEvenements.modifierEvenement(evenementActuel.getNomEvenement(), evt);
            
            if (success) {
                JOptionPane.showMessageDialog(this, 
                    "✅ Événement modifié avec succès !", 
                    "Succès", 
                    JOptionPane.INFORMATION_MESSAGE);
                //dispose();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "❌ Impossible de modifier l'événement.", 
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
}