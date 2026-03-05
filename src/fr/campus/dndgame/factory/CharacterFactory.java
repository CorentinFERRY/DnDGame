package fr.campus.dndgame.factory;

import fr.campus.dndgame.model.characters.Warrior;
import fr.campus.dndgame.model.characters.Wizard;
import fr.campus.dndgame.model.characters.Character;

public class CharacterFactory {

    public static Character createNewCharacter(String type, String name) {

        return switch (type) {
            case "Warrior" -> new Warrior(name);
            case "Wizard" -> new Wizard(name);
            default -> throw new IllegalArgumentException("Type inconnu : " + type);
        };
    }

    public static Character createFromDatabase(
            int id,
            String type,
            String name,
            int health,
            int maxHealth,
            int attack,
            int defense,
            int position
    ) {
        Character character = switch (type) {
            case "Warrior" -> new Warrior(name);
            case "Wizard" -> new Wizard(name);
            default -> throw new IllegalArgumentException("Type inconnu : " + type);
        };
        // Injection des données BDD
        character.setId(id);
        character.setHealth(health);
        character.setAttack(attack);
        character.setMaxHealth(maxHealth);
        character.setDefense(defense);
        character.setPosition(position);

        return character;
    }
}