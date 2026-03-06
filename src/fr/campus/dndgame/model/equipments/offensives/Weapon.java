package fr.campus.dndgame.model.equipments.offensives;

/**
 * Classe représentant une arme, un équipement offensif.
 * Une arme augmente la force d'attaque du guerrier.
 * 
 * @author CorentinFERRY
 * @version 1.0
 */
public class Weapon extends OffensiveEquipment {
    /**
     * Constructeur pour créer une arme.
     *
     * @param name Le nom de l'arme
     * @param attackLevel Le niveau d'attaque de l'arme
     */
    public Weapon(String name, int attackLevel) {
        super("Weapon", name, attackLevel);
    }
}
