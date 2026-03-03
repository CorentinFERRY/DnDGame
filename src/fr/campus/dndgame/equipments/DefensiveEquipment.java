package fr.campus.dndgame.equipments;

import fr.campus.dndgame.characters.Character;

/**
 * Classe abstraite représentant un équipement défensif.
 * Un équipement défensif peut être utilisé pour protéger ou soigner un personnage.
 * 
 * @author CorentinFERRY
 * @version 1.0
 */
public abstract class DefensiveEquipment extends Equipment{

    /**
     * Constructeur protégé pour initialiser un équipement défensif.
     *
     * @param type Le type d'équipement
     * @param name Le nom de l'équipement
     */
    protected DefensiveEquipment(String type,String name){
        super(type,name);
    }

    /**
     * Utilise l'équipement défensif sur un personnage.
     * Cette méthode doit être implémentée par les sous-classes.
     *
     * @param character Le personnage qui utilise l'équipement
     */
    public abstract void use(Character character);

}
