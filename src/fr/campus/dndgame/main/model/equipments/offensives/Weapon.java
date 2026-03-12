package fr.campus.dndgame.main.model.equipments.offensives;

import fr.campus.dndgame.main.model.characters.Character;
import fr.campus.dndgame.main.model.characters.Warrior;
import fr.campus.dndgame.main.utils.Menu;

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

    @Override
    public void use(Character character, Menu menu) {
        if(character instanceof Warrior warrior){
            warrior.equip(this);
        } else {
            menu.showMessage("Seul un guerrier peut utiliser cette arme.");
        }
    }
}
