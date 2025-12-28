package authentification.ui.frames;

import frames.GradientBackgroundPanel;
import frames.RechercherFrame;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import models.Evenement;
import services.gestionnaire;

/**
 * VisitorFrame - Interface pour les visiteurs
 * Affiche tous les événements disponibles triés par date
 */
public class VisitorFrame extends JFrame {
    private gestionnaire gestionnaireEvenements;
    private JTable eventTable;
    private DefaultTableModel tableModel;
    private JButton btnRechercher;
    private JButton btnRafraichir;
    private JButton btnRetour;
    
    // Couleurs du thème
    private final Color BACKGROUND_COLOR = new Color(240, 242, 245);
    private final Color CARD_COLOR = Color.WHITE;
    private final Color PRIMARY_COLOR = new Color(37, 99, 235);
    private final Color PRIMARY_HOVER = new Color(29, 78, 216);
    private final Color SUCCESS_COLOR = new Color(16, 185, 129);
    private final Color SUCCESS_HOVER = new Color(5, 150, 105);
    private final Color DANGER_COLOR = new Color(239, 68, 68);
    private final Color DANGER_HOVER = new Color(220, 38, 38);
    private final Color TEXT_COLOR = new Color(31, 41, 55);
    private final Color BORDER_COLOR = new Color(209, 213, 219);
    
    /**
     * Constructeur avec gestionnaire
     */
    public VisitorFrame(gestionnaire gestionnaire) {
    this.gestionnaireEvenements = gestionnaire;
    
    // Force reload from JSON file to get latest events
    System.out.println("🔄 Loading events from historique.json...");
    this.gestionnaireEvenements.chargerEvenements("historique.json");
    
    // Debug: show how many events loaded
    ArrayList<Evenement> events = this.gestionnaireEvenements.getListeEvenements();
    System.out.println("📊 Total events loaded: " + (events == null ? 0 : events.size()));
    
    initComponents();
    loadEvents();
}
    
    /**
     */
    
    private void initComponents() {
        setTitle("Visitor Page - Liste des Événements");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setContentPane(new GradientBackgroundPanel());
        setLayout(new BorderLayout(20, 20));
        
        // Panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setOpaque(false);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        
        // Titre
        JLabel titleLabel = new JLabel("📋 Liste des Événements");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        // Sous-titre
        JLabel subtitleLabel = new JLabel("Tous les événements disponibles, triés par date");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitleLabel.setForeground(new Color(255, 255, 255, 200));
        subtitleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(subtitleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        
        // Card contenant la table
        JPanel tableCard = createTableCard();
        mainPanel.add(tableCard);
        
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
    
    private JPanel createTableCard() {
        JPanel card = new JPanel(new BorderLayout(10, 10));
        card.setBackground(CARD_COLOR);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 450));
        
        // Création de la table
        String[] columnNames = {"Nom", "Type", "Date", "Horaire", "Lieu", "Capacité", "Statut"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        eventTable = new JTable(tableModel);
        eventTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        eventTable.setRowHeight(35);
        eventTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        eventTable.getTableHeader().setBackground(PRIMARY_COLOR);
        eventTable.getTableHeader().setForeground(Color.BLACK);
        eventTable.setSelectionBackground(new Color(37, 99, 235, 100));
        eventTable.setSelectionForeground(TEXT_COLOR);
        eventTable.setGridColor(BORDER_COLOR);
        
        // Centrer le contenu des cellules
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < eventTable.getColumnCount(); i++) {
            eventTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        
        // Ajuster la largeur des colonnes
        eventTable.getColumnModel().getColumn(0).setPreferredWidth(180); // Nom
        eventTable.getColumnModel().getColumn(1).setPreferredWidth(120); // Type
        eventTable.getColumnModel().getColumn(2).setPreferredWidth(100); // Date
        eventTable.getColumnModel().getColumn(3).setPreferredWidth(120); // Horaire
        eventTable.getColumnModel().getColumn(4).setPreferredWidth(150); // Lieu
        eventTable.getColumnModel().getColumn(5).setPreferredWidth(80);  // Capacité
        eventTable.getColumnModel().getColumn(6).setPreferredWidth(100); // Statut
        
        JScrollPane scrollPane = new JScrollPane(eventTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1));
        scrollPane.getViewport().setBackground(Color.WHITE);
        
        card.add(scrollPane, BorderLayout.CENTER);
        
        return card;
    }
    
    private JPanel createButtonsPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        panel.setOpaque(false);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        
        // Bouton Rechercher
        btnRechercher = createStyledButton("🔍 Rechercher un Événement", PRIMARY_COLOR, PRIMARY_HOVER);
        btnRechercher.setPreferredSize(new Dimension(250, 50));
        btnRechercher.addActionListener(e -> ouvrirRechercherFrame());
        
        // Bouton Rafraîchir
        btnRafraichir = createStyledButton("🔄 Rafraîchir", SUCCESS_COLOR, SUCCESS_HOVER);
        btnRafraichir.setPreferredSize(new Dimension(180, 50));
        btnRafraichir.addActionListener(e -> {
            loadEvents();
            JOptionPane.showMessageDialog(this, 
                "✅ Liste des événements rafraîchie!", 
                "Succès", 
                JOptionPane.INFORMATION_MESSAGE);
        });
        
        // Bouton Retour - Retourne vers WelcomeFrame
        btnRetour = createStyledButton("← Retour", DANGER_COLOR, DANGER_HOVER);
        btnRetour.setPreferredSize(new Dimension(150, 50));
        btnRetour.addActionListener(e -> {
            dispose();
            WelcomeFrame welcomeFrame = new WelcomeFrame();
            welcomeFrame.setVisible(true);
        });
        
        panel.add(btnRechercher);
        panel.add(btnRafraichir);
        panel.add(btnRetour);
        
        return panel;
    }
    
    private JButton createStyledButton(String text, Color bgColor, Color hoverColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 15));
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
     * Charge et affiche tous les événements depuis le gestionnaire
     * Les événements sont triés par date
     */
    private void loadEvents() {
        // Effacer les données existantes
        tableModel.setRowCount(0);
         // ⭐ ADD THESE DEBUG LINES:
        System.out.println("=== LOADING EVENTS IN VISITOR FRAME ===");
        System.out.println("Gestionnaire is null? " + (gestionnaireEvenements == null));
        
        // Récupérer les événements depuis le gestionnaire
        ArrayList<Evenement> events = gestionnaireEvenements.getListeEvenements();
        System.out.println("Number of events: " + (events == null ? "NULL" : events.size()));
        if (events == null || events.isEmpty()) {
            // Aucun événement disponible
            System.out.println("ℹ️ Aucun événement disponible pour le moment.");
            return;
        }
        
        // Trier les événements par date
        ArrayList<Evenement> sortedEvents = new ArrayList<>(events);
        Collections.sort(sortedEvents, new Comparator<Evenement>() {
            @Override
            public int compare(Evenement e1, Evenement e2) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    Date date1 = sdf.parse(e1.getDateEvenement());
                    Date date2 = sdf.parse(e2.getDateEvenement());
                    return date1.compareTo(date2);
                } catch (ParseException ex) {
                    // En cas d'erreur de parsing, on compare les chaînes directement
                    return e1.getDateEvenement().compareTo(e2.getDateEvenement());
                }
            }
        });
        
        // Ajouter les événements à la table
        for (Evenement event : sortedEvents) {
            String horaire = event.getHeureDebut() + " - " + event.getHeureFin();
            String capacite = event.getNbreInscrits() + " / " + event.getCapaciteMax();
            
            Object[] row = {
                event.getNomEvenement(),
                event.getTypeEvenement(),
                event.getDateEvenement(),
                horaire,
                event.getLieu(),
                capacite,
                event.getStatut().toUpperCase()
            };
            tableModel.addRow(row);
        }
        
        // Afficher un message avec le nombre d'événements
        System.out.println("✓ " + sortedEvents.size() + " événement(s) chargé(s)");
    }
    
    /**
     * Ouvre la frame de recherche en mode VISITEUR
     * CHANGEMENT ICI : Passe true pour indiquer que c'est un visiteur
     */
    private void ouvrirRechercherFrame() {
        dispose();
        // Passer true pour indiquer le mode visiteur
        RechercherFrame rechercherFrame = new RechercherFrame(gestionnaireEvenements, true);
        rechercherFrame.setVisible(true);
    }
    
    /**
     * Main pour tester
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Créer un gestionnaire avec quelques événements de test
            gestionnaire gest = new gestionnaire();
            
            // Vous pouvez ajouter des événements de test ici si nécessaire
            // Conference conf = new Conference(...);
            // gest.ajouterEvenement(conf);
            
            VisitorFrame frame = new VisitorFrame(gest);
            frame.setVisible(true);
        });
    }
}                      