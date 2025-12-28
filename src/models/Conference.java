package models;

public class Conference extends Evenement{
    private String intervenant; //La personne qui donne la conférence
    private String domaine;
    public Conference(String nomEvenement, String heureDebut, String heureFin, String lieu, String description,
            int capaciteMax, int nbreInscrits, String typeEvenement, String organisateur, String statut, float prix,
            String date, String intervenant, String domaine) {
        super(nomEvenement, heureDebut, heureFin, lieu, description, capaciteMax, nbreInscrits, "Conférence",
        organisateur, statut, prix, date);
        this.domaine = domaine;
            }
    public String getIntervenant() {
        return intervenant;
    }
    public void setIntervenant(String intervenant) {
        this.intervenant = intervenant;
    }
    public String getDomaine() {
        return domaine;
    }
    public void setDomaine(String domaine) {
        this.domaine = domaine;
    }
   public void afficherDetails(){
        System.out.println("Conférence donnée par: " + intervenant + " dans le domaine de: " + domaine + ".");
    }
    public boolean necessiteInscription(){
        return true; // car une conférence nécessite d'inscription
    }

}
