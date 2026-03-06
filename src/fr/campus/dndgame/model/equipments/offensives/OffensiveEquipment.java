package fr.campus.dndgame.model.equipments.offensives;

import fr.campus.dndgame.model.equipments.Equipment;

/**
 * Classe abstraite représentant un équipement offensif.
 * Un équipement offensif ajoute un bonus d'attaque au personnage.
 * 
 * @author CorentinFERRY
 * @version 1.0
 */
public abstract class OffensiveEquipment extends Equipment {

    private int attackBonus;
    private int id;

    /**
     * Constructeur protégé pour initialiser un équipement offensif.
     *
     * @param type Le type d'équipement
     * @param name Le nom de l'équipement
     * @param attackBonus Le bonus d'attaque fourni par cet équipement
     */
    protected OffensiveEquipment(String type,String name, int attackBonus){
        super(type,name);
        this.attackBonus = attackBonus;
    }

    // ========== GETTERS & SETTERS ==========


    /**
     * Retourne le bonus d'attaque de cet équipement.
     *
     * @return Le bonus d'attaque
     */
    public int getAttackBonus(){
        return attackBonus;
    }

    /**
     * Définit le bonus d'attaque de l'équipement.
     *
     * @param attackBonus nouvelle valeur du bonus d'attaque
     */
    public void setAttackBonus(int attackBonus) {
        this.attackBonus = attackBonus;
    }

    /**
     * Définit l'identifiant de persistance de l'équipement offensif.
     *
     * @param id identifiant en base de données
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retourne l'identifiant de persistance de l'équipement offensif.
     *
     * @return identifiant en base de données
     */
    public int getId() {
        return id;
    }

    /**
     * Retourne l'effet de l'équipement offensif.
     *
     * @return bonus d'attaque
     */
    @Override
    public int getEffect() {
        return attackBonus;
    }

    /**
     * Définit l'effet de l'équipement offensif.
     *
     * @param effect bonus d'attaque
     */
    @Override
    public void setEffect(int effect) {
        this.attackBonus = effect;
    }

    /**
     * Indique qu'il s'agit d'un équipement offensif.
     *
     * @return toujours {@code true}
     */
    @Override
    public boolean isOffensive() {
        return true;
    }

    /**
     * Retourne une représentation textuelle de l'équipement offensif.
     *
     * @return Une chaîne décrivant le bonus d'attaque
     */
    public String toString(){
        return super.toString() + " (Attaque: " + attackBonus + ")";
    }
}
