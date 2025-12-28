package services;
import java.io.*;
import java.util.ArrayList;
import models.*;

public class gestionnaire  { 
    private ArrayList<Evenement> listevenements;
    
    public gestionnaire(){
        listevenements = new ArrayList<Evenement>();
        chargerEvenements("historique.json");
    }

    public boolean ajouterEvenement(Evenement e) {
        if (e == null) {
            System.out.println("Evenement invalide, impossible de l'ajouter.");
            return false;
        }
        if (!e.validerEvenement()) {
            System.out.println("Evenement invalide, impossible de l'ajouter.");
            return false;
        }

        for(Evenement evt : listevenements) {
            if(evt.getNomEvenement().equals(e.getNomEvenement())) { //pour comparer entre l'evnt et les autres evenements
                System.out.println("Evenement avec le meme nom existe deja, impossible de l'ajouter.");
                return false;
            }
        }

        if (e.getStatut().equals("complet") && e.getNbreInscrits() < e.getCapaciteMax()) {
            System.out.println("Statut 'complet' invalide avec le nombre d'inscrits actuel, impossible de l'ajouter.");
            return false;
        }
        if(e.getStatut().equals("ouvert") && e.getNbreInscrits() >= e.getCapaciteMax()) {
            System.out.println("Statut 'ouvert' invalide avec le nombre d'inscrits actuel, impossible de l'ajouter.");
            return false;
        }
        
        if(e.getTypeEvenement().equals("Conférence") && e.getCapaciteMax() < 50) {
            System.out.println("Les conférences doivent avoir une capacité minimale de 50, impossible de l'ajouter.");
            return false;
        }
        if(e.getTypeEvenement().equals("Atelier") && e.getCapaciteMax() < 20) {
            System.out.println("Les ateliers doivent avoir une capacité minimale de 20, impossible de l'ajouter.");
            return false;
        }
        if(e.getTypeEvenement().equals("Evenement social") && e.getCapaciteMax() < 30) {
            System.out.println("Les Evénements sociaux doivent avoir une capacité minimale de 30, impossible de l'ajouter.");
            return false;
        }
        
        listevenements.add(e);
        System.out.println("Événement "+ e.getNomEvenement()+" ajouté avec succès.");
        sauvegarderEvenements("historique.json");
        return true;    
    }
    
    public boolean supprimerEvenement(String nomEvenement) {
        for(int i=0; i < listevenements.size(); i++) {
            if(listevenements.get(i).getNomEvenement().equals(nomEvenement)) {
                listevenements.remove(i);
                System.out.println("Evenement avec le nom = " + nomEvenement + " supprimé avec succès.");
                sauvegarderEvenements("historique.json");
                return true;
            }
        }
        System.out.println("Aucun evenement trouvé avec le nom = " + nomEvenement);
        return false;
    }
    
    public boolean modifierEvenement(String nomEvenement, Evenement nouvelEvenement) {
        if(nouvelEvenement == null || !nouvelEvenement.validerEvenement()) {
            System.out.println("Nouvel evenement invalide, impossible de modifier.");
            return false;
        }
        
        for(int i=0; i < listevenements.size(); i++) {
            if(listevenements.get(i).getNomEvenement().equals(nomEvenement)) {
                listevenements.set(i, nouvelEvenement);
                System.out.println("Evenement avec le nom = " + nomEvenement + " modifié avec succès.");
                sauvegarderEvenements("historique.json");
                return true;
            }
        }
        System.out.println("Aucun evenement trouvé avec le nom = " + nomEvenement);
        return false;
    }

    public Evenement consulterEvenement(String nomEvenement) {
        for(Evenement e : listevenements) {
            if(e.getNomEvenement().equals(nomEvenement)) {
                System.out.println("Evenement trouvé : ");
                e.afficherDetails();
                return e;
            }
        }
        System.out.println("Aucun evenement trouvé avec le nom = " + nomEvenement);
        return null;
    }
    
    public void afficherTousEvenements() {
        if(listevenements.isEmpty()) {
            System.out.println("Aucun evenement disponible.");
            return;
        }
        System.out.println("\n========================================");
        System.out.println("📋 LISTE DE TOUS LES ÉVÉNEMENTS (" + listevenements.size() + ")");
        System.out.println("========================================\n");
        for(int i=0; i< listevenements.size(); i++) {
            System.out.println("Événement " + (i + 1) + " :");
            listevenements.get(i).afficherDetails();
            System.out.println("----------------------------------------\n");
        }
    }
    
    public ArrayList<Evenement> getListeEvenements() {
        return listevenements;
    }

    public void setListeEvenements(ArrayList<Evenement> liste) {
        this.listevenements = liste;
    }
    
    public void sauvegarderEvenements(String fichier) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fichier))) {
            writer.println("[");
            
            for (int i = 0; i < listevenements.size(); i++) {
                Evenement e = listevenements.get(i);
                writer.println("  {");
                
                writer.println("    \"nom\": \"" + escapeJson(e.getNomEvenement()) + "\",");
                writer.println("    \"heureDebut\": \"" + escapeJson(e.getHeureDebut()) + "\",");
                writer.println("    \"heureFin\": \"" + escapeJson(e.getHeureFin()) + "\",");
                writer.println("    \"lieu\": \"" + escapeJson(e.getLieu()) + "\",");
                writer.println("    \"description\": \"" + escapeJson(e.getDescription()) + "\",");
                writer.println("    \"capaciteMax\": " + e.getCapaciteMax() + ",");
                writer.println("    \"nbreInscrits\": " + e.getNbreInscrits() + ",");
                writer.println("    \"typeEvenement\": \"" + escapeJson(e.getTypeEvenement()) + "\",");
                writer.println("    \"organisateur\": \"" + escapeJson(e.getOrganisateur()) + "\",");
                writer.println("    \"statut\": \"" + escapeJson(e.getStatut()) + "\",");
                writer.println("    \"prix\": " + e.getPrix() + ",");
                writer.println("    \"dateEvenement\": \"" + escapeJson(e.getDateEvenement()) + "\",");
                
                if (e instanceof Conference) {
                    Conference conf = (Conference) e;
                    writer.println("    \"type\": \"Conference\",");
                    writer.println("    \"intervenant\": \"" + escapeJson(conf.getIntervenant()) + "\",");
                    writer.println("    \"domaine\": \"" + escapeJson(conf.getDomaine()) + "\"");
                } else if (e instanceof Atelier) {
                    Atelier atelier = (Atelier) e;
                    writer.println("    \"type\": \"Atelier\",");
                    writer.println("    \"materielNecessaire\": \"" + escapeJson(atelier.getMaterielNecessaire()) + "\",");
                    writer.println("    \"niveau\": \"" + escapeJson(atelier.getNiveau()) + "\"");
                } else if (e instanceof evenementSocial) {
                    evenementSocial social = (evenementSocial) e;
                    writer.println("    \"type\": \"EvenementSocial\",");
                    writer.println("    \"theme\": \"" + escapeJson(social.getTheme()) + "\",");
                    writer.println("    \"refreshments\": \"" + escapeJson(social.getRefreshments()) + "\"");
                }
                
                if (i < listevenements.size() - 1) {
                    writer.println("  },");
                } else {
                    writer.println("  }");
                }
            }
            
            writer.println("]");
            System.out.println("✅ Sauvegardé dans " + fichier);
        } catch (IOException e) {
            System.out.println("❌ Erreur sauvegarde : " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void chargerEvenements(String fichier) {
        try {
            File file = new File(fichier);
            if (!file.exists()) {
                System.out.println("📁 " + fichier + " n'existe pas encore");
                return;
            }
            
            BufferedReader reader = new BufferedReader(new FileReader(fichier));
            StringBuilder jsonContent = new StringBuilder();
            String ligne;
            
            while ((ligne = reader.readLine()) != null) {
                jsonContent.append(ligne.trim());
            }
            reader.close();
            
            String json = jsonContent.toString();
            
            if (json.startsWith("[") && json.endsWith("]")) {
                json = json.substring(1, json.length() - 1);
                
                listevenements.clear();
                
                int braceCount = 0;
                int startIndex = 0;
                
                for (int i = 0; i < json.length(); i++) {
                    char c = json.charAt(i);
                    
                    if (c == '{') {
                        if (braceCount == 0) startIndex = i;
                        braceCount++;
                    } else if (c == '}') {
                        braceCount--;
                        if (braceCount == 0) {
                            String objetJson = json.substring(startIndex, i + 1);
                            Evenement evt = parseEvenement(objetJson);
                            if (evt != null) {
                                listevenements.add(evt);
                            }
                        }
                    }
                }
                
                System.out.println("✅ " + listevenements.size() + " événements chargés");
            }
            
        } catch (IOException e) {
            System.out.println("❌ Erreur chargement : " + e.getMessage());
            e.printStackTrace();
        }
    }

    private Evenement parseEvenement(String json) {
        try {
            String nom = extraireValeur(json, "nom");
            String heureDebut = extraireValeur(json, "heureDebut");
            String heureFin = extraireValeur(json, "heureFin");
            String lieu = extraireValeur(json, "lieu");
            String description = extraireValeur(json, "description");
            
            String capaciteMaxStr = extraireValeur(json, "capaciteMax");
            String nbreInscritsStr = extraireValeur(json, "nbreInscrits");
            String prixStr = extraireValeur(json, "prix");
            
            int capaciteMax = Integer.parseInt(capaciteMaxStr);
            int nbreInscrits = Integer.parseInt(nbreInscritsStr);
            float prix = Float.parseFloat(prixStr);
            
            String typeEvenement = extraireValeur(json, "typeEvenement");
            String organisateur = extraireValeur(json, "organisateur");
            String statut = extraireValeur(json, "statut");
            String dateEvenement = extraireValeur(json, "dateEvenement");
            String type = extraireValeur(json, "type");
            
            if ("Conference".equals(type)) {
                String intervenant = extraireValeur(json, "intervenant");
                String domaine = extraireValeur(json, "domaine");
                return new Conference(nom, heureDebut, heureFin, lieu, description,
                    capaciteMax, nbreInscrits, typeEvenement, organisateur, statut, 
                    prix, dateEvenement, intervenant, domaine);
                    
            } else if ("Atelier".equals(type)) {
                String materielNecessaire = extraireValeur(json, "materielNecessaire");
                String niveau = extraireValeur(json, "niveau");
                return new Atelier(nom, heureDebut, heureFin, lieu, description,
                    capaciteMax, nbreInscrits, typeEvenement, organisateur, statut, 
                    prix, dateEvenement, materielNecessaire, niveau);
                    
            } else if ("EvenementSocial".equals(type)) {
                String theme = extraireValeur(json, "theme");
                String refreshments = extraireValeur(json, "refreshments");
                return new evenementSocial(nom, heureDebut, heureFin, lieu, description,
                    capaciteMax, nbreInscrits, typeEvenement, organisateur, statut, 
                    prix, dateEvenement, theme, refreshments);
            }
            
        } catch (Exception e) {
            System.out.println("❌ Erreur parsing événement : " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    private String extraireValeur(String json, String cle) {
        try {
            String pattern = "\"" + cle + "\":";
            int index = json.indexOf(pattern);
            if (index == -1) return "";
            
            int valueStart = index + pattern.length();
            
            // Skip whitespace
            while (valueStart < json.length() && Character.isWhitespace(json.charAt(valueStart))) {
                valueStart++;
            }
            
            // Check if it's a string value (starts with quote)
            if (json.charAt(valueStart) == '\"') {
                // String value
                int startQuote = valueStart;
                int endQuote = startQuote + 1;
                
                // Find closing quote (handle escaped quotes)
                while (endQuote < json.length()) {
                    if (json.charAt(endQuote) == '\"' && json.charAt(endQuote - 1) != '\\') {
                        break;
                    }
                    endQuote++;
                }
                
                return json.substring(startQuote + 1, endQuote);
            } else {
                // Numeric value or boolean
                int endValue = valueStart;
                while (endValue < json.length()) {
                    char c = json.charAt(endValue);
                    if (c == ',' || c == '}' || Character.isWhitespace(c)) {
                        break;
                    }
                    endValue++;
                }
                
                return json.substring(valueStart, endValue).trim();
            }
        } catch (Exception e) {
            System.out.println("❌ Erreur extraction valeur '" + cle + "': " + e.getMessage());
            return "";
        }
    }

    private String escapeJson(String str) {
        if (str == null) return "";
        return str.replace("\\", "\\\\")
                  .replace("\"", "\\\"")
                  .replace("\n", "\\n")
                  .replace("\r", "\\r")
                  .replace("\t", "\\t");
    }
}