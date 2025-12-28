package App;

import authentification.ui.frames.WelcomeFrame;
import java.awt.*;
import javax.swing.*;

/**
 * * Main - Point d'entrée de l'application
 * Système de Gestion des Événements IHEC Carthage
 * 
 * Cette application permet:
 * - Aux visiteurs de consulter les événements
 * - Aux administrateurs et clubs de gérer les événements
 */
public class Main {
    public static void main(String[] args) {
        // Configuration du Look and Feel pour une meilleure apparence
        configureLookAndFeel();

        // Configuration des propriétés système pour l'affichage
        configureSystemProperties();

        // Lancer l'application sur le thread de gestion des événements Swing (EDT)
        SwingUtilities.invokeLater(() -> {
            try {
                // Message de démarrage dans la console
                printWelcomeMessage();

                // Créer et afficher la fenêtre d'accueil
                new WelcomeFrame().setVisible(true);

                System.out.println("✓ Application lancée avec succès!");

            } catch (Exception e) {
                // Gérer les erreurs lors du lancement
                handleStartupError(e);
            }
        });
    }

    /**
     * Configure le Look and Feel de l'application
     */
    private static void configureLookAndFeel() {
        try {
            // Tenter d'utiliser le Look and Feel du système pour une apparence native
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            // Configuration supplémentaire pour améliorer l'apparence
            UIManager.put("Button.arc", 10);
            UIManager.put("Component.arc", 10);
            UIManager.put("ProgressBar.arc", 10);
            UIManager.put("TextComponent.arc", 10);

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            // Si ça échoue, utiliser le Look and Feel par défaut
            System.err.println("⚠ Impossible de définir le Look and Feel natif: " + e.getMessage());
            System.err.println("  Utilisation du Look and Feel par défaut.");

            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException ex) {
                // Ignorer et utiliser le Look and Feel par défaut de Java
                System.err.println("⚠ Utilisation du Look and Feel Java par défaut.");
            }
        }
    }

    /**
     * Configure les propriétés système pour améliorer le rendu
     */
    private static void configureSystemProperties() {
        // Active l'anti-aliasing pour un meilleur rendu du texte
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");

        // Améliore le rendu graphique
        System.setProperty("sun.java2d.opengl", "true");

        // Configure l'accélération matérielle si disponible
        Toolkit.getDefaultToolkit().setDynamicLayout(true);
    }

    /**
     * Affiche un message de bienvenue dans la console
     */
    private static void printWelcomeMessage() {
        System.out.println("╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║                                                           ║");
        System.out.println("║       IHEC CARTHAGE - GESTION DES ÉVÉNEMENTS             ║");
        System.out.println("║       Institut des Hautes Études Commerciales            ║");
        System.out.println("║                                                           ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝");
        System.out.println();
        System.out.println("🚀 Démarrage de l'application...");
        System.out.println();
        System.out.println("📋 Utilisateurs disponibles:");
        System.out.println("   • Administrateur: adminUni / ihec2024");
        System.out.println("   • Clubs:");
        System.out.println("     - hecfa / hecfa2024");
        System.out.println("     - artrev / artrev2024");
        System.out.println("     - lions / lions2024");
        System.out.println("     - enactus / enactus2024");
        System.out.println("     - aiesec / aiesec2024");
        System.out.println("     - mmt / mmt2024");
        System.out.println("     - libertad / libertad2024");
        System.out.println("     - ihecnews / ihecnews2024");
        System.out.println();
    }

    /**
     * Gère les erreurs lors du démarrage de l'application
     */
    private static void handleStartupError(Exception e) {
        System.err.println("╔═══════════════════════════════════════════════════════════╗");
        System.err.println("║                    ERREUR CRITIQUE                        ║");
        System.err.println("╚═══════════════════════════════════════════════════════════╝");
        System.err.println();
        System.err.println("❌ Erreur lors du lancement de l'application:");
        System.err.println("   " + e.getMessage());
        System.err.println();
        System.err.println("📝 Stack trace complète:");
        System.err.println();

        // Afficher un message d'erreur graphique à l'utilisateur
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(
                    null,
                    "<html><body style='width: 300px;'>" +
                            "<h2>❌ Erreur de Démarrage</h2>" +
                            "<p>L'application n'a pas pu démarrer correctement.</p>" +
                            "<p><b>Erreur:</b> " + e.getMessage() + "</p>" +
                            "<br>" +
                            "<p>Veuillez vérifier:</p>" +
                            "<ul>" +
                            "<li>Que tous les fichiers sont présents</li>" +
                            "<li>Les logs de la console pour plus de détails</li>" +
                            "<li>Votre version de Java (Java 8 ou supérieur requis)</li>" +
                            "</ul>" +
                            "</body></html>",
                    "Erreur - IHEC Carthage",
                    JOptionPane.ERROR_MESSAGE);
        });

        // Arrêter l'application
        System.exit(1);
    }
}