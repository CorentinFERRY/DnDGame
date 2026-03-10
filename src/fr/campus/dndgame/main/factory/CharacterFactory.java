package fr.campus.dndgame.main.factory;

import fr.campus.dndgame.main.model.characters.Warrior;
import fr.campus.dndgame.main.model.characters.Wizard;
import fr.campus.dndgame.main.model.characters.Character;

/**
 * Classe factory pour la création de personnages.
 * Fournit des méthodes statiques pour créer des personnages à partir de types
 * prédéfinis
 * ou à partir de données provenant de la base de données.
 * 
 * @author CorentinFERRY
 * @version 1.0
 */
public class CharacterFactory {

    /**
     * Crée un nouveau personnage basé sur le type spécifié.
     * 
     * @param type Le type de personnage ("Warrior" ou "Wizard")
     * @param name Le nom du personnage
     * @return Un nouvel objet Character du type spécifié
     * @throws IllegalArgumentException si le type de personnage est inconnu
     */
    public static Character createNewCharacter(String type, String name) {

        return switch (type) {
            case "Warrior" -> new Warrior(name);
            case "Wizard" -> new Wizard(name);
            default -> throw new IllegalArgumentException("Type inconnu : " + type);
        };
    }

    /**
     * Crée un personnage à partir des données provenant de la base de données.
     * Initialise toutes les propriétés du personnage avec les valeurs fournies.
     * 
     * @param id        L'identifiant unique du personnage en base de données
     * @param type      Le type de personnage ("Warrior" ou "Wizard")
     * @param name      Le nom du personnage
     * @param health    La santé actuelle du personnage
     * @param maxHealth La santé maximale du personnage
     * @param attack    La force d'attaque du personnage
     * @param defense   La valeur de défense du personnage
     * @param position  La position actuelle du personnage sur le plateau
     * @return Un objet Character initialisé avec les données fournies
     * @throws IllegalArgumentException si le type de personnage est inconnu
     */
    public static Character createFromDatabase(
            int id,
            String type,
            String name,
            int health,
            int maxHealth,
            int attack,
            int defense,
            int position,
            int boardId) {
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
        character.setBoardId(boardId);

        return character;
    }
}