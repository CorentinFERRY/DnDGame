package fr.campus.dndgame.test.model.enemies;

import fr.campus.dndgame.main.model.enemies.Dragon;
import fr.campus.dndgame.main.model.enemies.Enemy;
import fr.campus.dndgame.main.model.enemies.Goblin;
import fr.campus.dndgame.main.model.enemies.Sorcerer;
import fr.campus.dndgame.test.model.rapports.TestReport;

public class EnemyTest {
    private static TestReport report;

    public static void main(String[] args) {
        report = new TestReport("ENEMY (Base)");
        testConstructorAndStats();
        testGettersSetters();
        testSpecificEnemiesStats();
        testToString();
        report.printSummary();
    }

    private static void testConstructorAndStats() {
        try {
            Enemy g = new Goblin();
            if (g.getHealth() <= 0) throw new AssertionError("Les PV doivent être > 0");
            if (g.getAttack() <= 0) throw new AssertionError("L'attaque doit être > 0");
            if (g.getName() == null || g.getName().isEmpty()) throw new AssertionError("Le nom ne doit pas être vide");
            if (g.getMaxHealth() != g.getHealth()) throw new AssertionError("PV actuels doivent être égaux aux PV max au départ");
            if (g.getDefense() != 0) throw new AssertionError("La défense initiale doit être 0");
            report.logSuccess("testConstructorAndStats");
        } catch (Exception e) {
            report.logFailed("testConstructorAndStats", e);
        }
    }

    private static void testGettersSetters() {
        try {
            Enemy e = new Goblin();

            // Test ID (BDD)
            e.setId(123);
            if (e.getId() != 123) throw new AssertionError("Setter/Getter ID échoué");

            // Test Attack
            e.setAttack(99);
            if (e.getAttack() != 99) throw new AssertionError("Setter/Getter Attack échoué");

            // Test Health
            e.setHealth(1);
            if (e.getHealth() != 1) throw new AssertionError("Setter/Getter Health échoué");

            // Test MaxHealth
            e.setMaxHealth(50);
            if (e.getMaxHealth() != 50) throw new AssertionError("Setter/Getter MaxHealth échoué");

            // Test Defense
            e.setDefense(10);
            if (e.getDefense() != 10) throw new AssertionError("Setter/Getter Defense échoué");

            report.logSuccess("testGettersSetters");
        } catch (Exception e) {
            report.logFailed("testGettersSetters", e);
        }
    }
    private static void testSpecificEnemiesStats() {
        try {
            // Test Goblin
            Enemy g = new Goblin();
            if (g.getHealth() != 6 || g.getAttack() != 1) {
                throw new AssertionError("Stats du Goblin incorrectes");
            }
            // Test Dragon
            Enemy d = new Dragon();
            if (d.getHealth() != 15 || d.getAttack() != 4) {
                throw new AssertionError("Stats du Dragon incorrectes");
            }
            // Test Sorcerer
            Enemy s = new Sorcerer();
            if (s.getHealth() != 9 || s.getAttack() != 2) {
                throw new AssertionError("Stats du Sorcier incorrectes");
            }
            report.logSuccess("testSpecificEnemiesStats");
        } catch (Exception e) {
            report.logFailed("testSpecificEnemiesStats", e);
        }
    }

    private static void testToString() {
        try {
            Enemy e = new Goblin();
            String s = e.toString();

            if (s == null) throw new AssertionError("toString ne doit pas être null");
            if (!s.contains(e.getName())) throw new AssertionError("toString doit contenir le nom");
            if (!s.contains("HP")) throw new AssertionError("toString doit contenir 'HP'");
            if (!s.contains("attaque")) throw new AssertionError("toString doit contenir 'attaque'");

            report.logSuccess("testToString");
        } catch (Exception e) {
            report.logFailed("testToString", e);
        }
    }
}