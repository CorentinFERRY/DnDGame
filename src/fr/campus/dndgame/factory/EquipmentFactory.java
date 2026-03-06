package fr.campus.dndgame.factory;


import fr.campus.dndgame.model.equipments.*;
import fr.campus.dndgame.model.equipments.defensives.Potion;
import fr.campus.dndgame.model.equipments.defensives.Shield;
import fr.campus.dndgame.model.equipments.offensives.Spell;
import fr.campus.dndgame.model.equipments.offensives.Weapon;

public class EquipmentFactory {
    public static Equipment createNewEquipment(String type,String name,int effect) {

        return switch (type) {
            case "Weapon" -> new Weapon(name,effect);
            case "Spell" -> new Spell(name,effect);
            case "Potion" -> new Potion(name,effect);
            case "Shield" -> new Shield(name,effect);
            default -> throw new IllegalArgumentException("Nom inconnu : " + name);
        };
    }

    public static Equipment createFromDatabase(
            int id,
            String type,
            String name,
            int effect) {
        Equipment equip = switch (type) {
            case "Weapon" -> new Weapon(name,effect);
            case "Spell" -> new Spell(name,effect);
            case "Potion" -> new Potion(name,effect);
            case "Shield" -> new Shield(name,effect);
            default -> throw new IllegalArgumentException("Nom inconnu : " + name);
        };
        // Injection des données BDD
        equip.setId(id);
        equip.setType(type);
        equip.setName(name);
        equip.setEffect(effect);

        return equip;
    }
}

