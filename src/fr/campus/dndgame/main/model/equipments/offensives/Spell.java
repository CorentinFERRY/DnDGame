package fr.campus.dndgame.main.model.equipments.offensives;
import fr.campus.dndgame.main.model.characters.Character;
import fr.campus.dndgame.main.model.characters.Wizard;
import fr.campus.dndgame.main.utils.Menu;

/**
 * Classe représentant un sort, un équipement offensif.
 * Un sort augmente la force d'attaque du magicien.
 *
 * @author CorentinFERRY
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

    @Override
    public void use(Character character, Menu menu) {
        if(character instanceof Wizard wizard){
            wizard.equip(this);
        } else {
            menu.showMessage("Seul une mage peut utiliser ce sort.");
        }
    }

}
