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

    public void setAttackBonus(int attackBonus) {
        this.attackBonus = attackBonus;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public int getEffect() {
        return attackBonus;
    }

    @Override
    public void setEffect(int effect) {
        this.attackBonus = effect;
    }

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
