package fr.campus.dndgame.main.model.equipments;

/**
 * Classe représentant une boite surprise dans le jeu.
 * Une boite surprise peut contenir un equipement aléatoire.
 * (Offensif ou Defensif)
 *
 * @author CorentinFERRY
 * @version 1.0
 */
public class SurpriseBox {
    private int id;
    private String name;
    private Equipment equipment;


    /**
     * Constructeur permettant de créer une boite surprise
     *
     * @param equipment équipement contenu dans la boite
     */
    public SurpriseBox(Equipment equipment){
        this.equipment = equipment;
    }

    /**
     * Constructeur permettant de créer une boite surprise
     *
     * @param equipment équipement contenu dans la boite
     */
    public SurpriseBox(int id,Equipment equipment){
        this.id = id;
        this.equipment = equipment;
    }

    // ========== GETTERS & SETTERS ==========

    /**
     * Définit le nom de la boîte surprise.
     *
     * @param name nom de la boîte
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retourne le nom de la boîte surprise.
     *
     * @return nom de la boîte
     */
    public String getName() {
        return name;
    }

    /**
     * Retourne l'identifiant de la boîte surprise.
     *
     * @return identifiant de la boîte
     */
    public int getId() {
        return id;
    }

    /**
     * Définit l'identifiant de la boîte surprise.
     *
     * @param id identifiant en base de données
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retourne l'équipement contenu dans la boite'.
     *
     * @return L'équipement
     */
    public Equipment getEquipment() {
        return equipment;
    }

    /**
     * Définit l'équipement contenu dans la boite'.
     *
     * @param equipment nouveau contenu de la boite
     */
    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }
    /**
     * Retourne une représentation du contenu de la boite.
     * Utilise la méthode toString() de l'équipement contenu.
     *
     * @return Une chaîne de caractères décrivant le contenu de la boite
     */
    @Override
    public String toString() {
        return equipment.toString();
    }
}
