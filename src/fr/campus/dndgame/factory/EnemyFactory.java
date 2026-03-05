package fr.campus.dndgame.factory;



import fr.campus.dndgame.model.enemies.*;


public class EnemyFactory {

    public static Enemy createNewEnemy(String name) {

        return switch (name) {
            case "Dragon" -> new Dragon();
            case "Sorcerer" -> new Sorcerer();
            case "Goblin" -> new Goblin();
            default -> throw new IllegalArgumentException("Nom inconnu : " + name);
        };
    }

    public static Enemy createFromDatabase(
            int id,
            String name,
            int health,
            int maxHealth,
            int attack,
            int defense
    ) {
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
