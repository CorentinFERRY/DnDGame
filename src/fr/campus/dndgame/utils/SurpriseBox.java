package fr.campus.dndgame.utils;

import fr.campus.dndgame.equipments.Equipment;

/**
 * Classe représentant une boite surprise dans le jeu.
 * Une boite surprise peut contenir un equipement aléatoire.
 * (Offensif ou Defensif)
 *
 * @author CorentinFERRY
 * @version 1.0
 */
public class SurpriseBox {
    private Equipment equipment;

    /**
     * Constructeur permettant de créer une boite surprise
     *
     * @param equipment équipement contenu dans la boite
     */
    public SurpriseBox(Equipment equipment){
        this.equipment = equipment;
    }

    // ========== GETTERS & SETTERS ==========
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
