package fr.campus.dndgame.main.model.equipments.offensives;

/**
 * Classe représentant un sort, un équipement offensif.
 * Un sort augmente la force d'attaque du magicien.
 *
 * @author CorentinFEERY
 * @version 1.0
 */

public class Spell extends OffensiveEquipment {
    /**
     * Constructeur pour créer un sort.
     *
     * @param name Le nom du sort
     * @param attackLevel Le niveau d'attaque du sort
     */
    public Spell(String name, int attackLevel) {
        super("Spell", name, attackLevel);
    }
}
