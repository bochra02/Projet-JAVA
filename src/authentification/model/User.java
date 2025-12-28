package authentification.model;

/**
 * Classe User
 * Représente un utilisateur du système
 * Le mot de passe est stocké en clair (version simplifiée)
 */
public class User {

    // Nom d'utilisateur
    private final String username;

    // Mot de passe stocké en clair (NON sécurisé)
    private final String password;

    // Rôle de l'utilisateur : ADMIN_UNI ou CLUB
    private final String role;

    // Nom du club (null si admin université)
    private final String clubName;

    /**
     * Constructeur
     * Stocke directement le mot de passe sans transformation
     */
    public User(String username, String password, String role, String clubName) {

        // Initialisation du nom d'utilisateur
        this.username = username;

        // Stockage direct du mot de passe
        this.password = password;

        // Initialisation du rôle
        this.role = role;

        // Initialisation du nom du club
        this.clubName = clubName;
    }

    // Retourne le nom d'utilisateur
    public String getUsername() {
        return username;
    }

    // Retourne le rôle de l'utilisateur
    public String getRole() {
        return role;
    }

    // Retourne le nom du club
    public String getClubName() {
        return clubName;
    }

    /**
     * Vérifie si le mot de passe saisi correspond au mot de passe enregistré
     */
    public boolean verifyPassword(String inputPassword) {

        // Comparaison directe des deux mots de passe
        return this.password.equals(inputPassword);
    }
}
