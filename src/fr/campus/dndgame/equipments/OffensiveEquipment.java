package fr.campus.dndgame.equipments;

/**
 * Classe abstraite représentant un équipement offensif.
 * Un équipement offensif ajoute un bonus d'attaque au personnage.
 * 
 * @author CorentinFERRY
 * @version 1.0
 */
public abstract class OffensiveEquipment extends Equipment{

    private final int attackBonus;

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
     * Retourne une représentation textuelle de l'équipement offensif.
     *
     * @return Une chaîne décrivant le bonus d'attaque
     */
    public String toString(){
        return super.toString() + " (Attaque: " + attackBonus + ")";
    }
}
