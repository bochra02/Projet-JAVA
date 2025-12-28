package authentification.model;

import java.util.ArrayList;

/**
 * Classe Authentication - Gère l'authentification des utilisateurs
 */
public class Authentication {
    private final ArrayList<User> authorizedUsers;

    public Authentication() {
        authorizedUsers = new ArrayList<>();
        initializeUsers();
    }

    private void initializeUsers() {
        // Admin Université
        authorizedUsers.add(new User("adminUni", "ihec2025", "ADMIN_UNI", null));

        // Clubs IHEC Carthage
        authorizedUsers.add(new User("hecfa", "hecfa2025", "CLUB", "HECFA"));
        authorizedUsers.add(new User("artrev", "artrev2025", "CLUB", "Art Revolution"));
        authorizedUsers.add(new User("lions", "lions2025", "CLUB", "Lions Club"));
        authorizedUsers.add(new User("enactus", "enactus2025", "CLUB", "Enactus IHEC"));
        authorizedUsers.add(new User("aiesec", "aiesec2025", "CLUB", "AIESEC Carthage"));
        authorizedUsers.add(new User("mmt", "mmt2025", "CLUB", "MMT"));
        authorizedUsers.add(new User("libertad", "libertad2025", "CLUB", "Libertad"));
        authorizedUsers.add(new User("ihecnews", "ihecnews2025", "CLUB", "IHEC News"));
    }

    /**
     * Authentifie un utilisateur
     * 
     */
    public User login(String username, String password) {
        if (username == null || password == null) {
            return null;
        }

        for (User user : authorizedUsers) {
            if (user.getUsername().equals(username) && user.verifyPassword(password)) {
                return user;
            }
        }
        return null;
    }
}
