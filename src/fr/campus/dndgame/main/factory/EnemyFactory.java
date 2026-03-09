package fr.campus.dndgame.main.factory;

import fr.campus.dndgame.main.model.enemies.*;

/**
 * Classe factory pour la création d'ennemis.
 * Fournit des méthodes statiques pour créer des ennemis à partir de leurs noms
 * prédéfinis
 * ou à partir de données provenant de la base de données.
 * 
 * @author CorentinFERRY
 * @version 1.0
 */
public class EnemyFactory {

    /**
     * Crée un nouvel ennemi basé sur le nom spécifié.
     * 
     * @param name Le nom de l'ennemi ("Dragon", "Sorcerer" ou "Goblin")
     * @return Un nouvel objet Enemy correspondant au nom spécifié
     * @throws IllegalArgumentException si le nom de l'ennemi est inconnu
     */
    public static Enemy createNewEnemy(String name) {

        return switch (name) {
            case "Dragon" -> new Dragon();
            case "Sorcerer" -> new Sorcerer();
            case "Goblin" -> new Goblin();
            default -> throw new IllegalArgumentException("Nom inconnu : " + name);
        };
    }

    /**
     * Crée un ennemi à partir des données provenant de la base de données.
     * Initialise toutes les propriétés de l'ennemi avec les valeurs fournies.
     * 
     * @param id        L'identifiant unique de l'ennemi en base de données
     * @param name      Le nom de l'ennemi ("Dragon", "Sorcerer" ou "Goblin")
     * @param health    La santé actuelle de l'ennemi
     * @param maxHealth La santé maximale de l'ennemi
     * @param attack    La force d'attaque de l'ennemi
     * @param defense   La valeur de défense de l'ennemi
     * @return Un objet Enemy initialisé avec les données fournies
     * @throws IllegalArgumentException si le nom de l'ennemi est inconnu
     */
    public static Enemy createFromDatabase(
            int id,
            String name,
            int health,
            int maxHealth,
            int attack,
            int defense) {
        Enemy enemy = switch (name) {
            case "Dragon" -> new Dragon();
            case "Sorcerer" -> new Sorcerer();
            case "Goblin" -> new Goblin();
            default -> throw new IllegalArgumentException("Nom inconnu : " + name);
        };
        // Injection des données BDD
        enemy.setId(id);
        enemy.setHealth(health);
        enemy.setMaxHealth(maxHealth);
        enemy.setAttack(attack);
        enemy.setDefense(defense);
        return enemy;
    }
}
