package fr.campus.dndgame.test.model.characters;

import fr.campus.dndgame.main.model.characters.Character;
import fr.campus.dndgame.main.model.characters.Warrior;
import fr.campus.dndgame.main.model.equipments.defensives.DefensiveEquipment;
import fr.campus.dndgame.main.model.equipments.defensives.StandardPotion;
import fr.campus.dndgame.test.model.rapports.TestReport;

public class CharacterTest {
    private static TestReport report;

    public static void main(String[] args) {
        report = new TestReport("CHARACTER (Base)");
        testConstructorAndStats();
        testMove();
        testDefensiveEquipment();
        testToString();
        report.printSummary();
    }

    private static void testConstructorAndStats() {
        try {
            // On utilise Warrior pour instancier la classe abstraite
            Character c = new Warrior("HeroTest");
            if (!"HeroTest".equals(c.getName()))
                throw new AssertionError("Nom incorrect");
            if (!"Warrior".equals(c.getType()))
                throw new AssertionError("Type incorrect");
            if (c.getHealth() != 10)
                throw new AssertionError("PV initiaux incorrects (Warrior=10)");
            if (c.getAttack() != 5)
                throw new AssertionError("Attaque initiale incorrecte (Warrior=5)");
            if (c.getPosition() != 0)
                throw new AssertionError("Position initiale doit être 0");
            if (c.getMaxHealth() != 10)
                throw new AssertionError("MaxHealth incorrect");

            // Test Setters de base
            c.setName("NewName");
            if (!"NewName".equals(c.getName()))
                throw new AssertionError("Setter Name échoué");
            c.setHealth(20);
            if (c.getHealth() != 20)
                throw new AssertionError("Setter Health echoué");
            c.setAttack(99);
            if (c.getAttack() != 99)
                throw new AssertionError("Setter Attack échoué");
            c.setDefense(10);
            if (c.getDefense() != 10)
                throw new AssertionError("Setter Defense échoué");

            report.logSuccess("testConstructorAndStats");
        } catch (Throwable e) {
            report.logFailed("testConstructorAndStats", e);
        }
    }

    private static void testMove() {
        try {
            Character c = new Warrior("MoveTester");
            c.setPosition(5);
            c.move(); // Doit ajouter 1
            if (c.getPosition() != 6)
                throw new AssertionError("Move échoué : attendu 6, obtenu " + c.getPosition());
            c.move();
            if (c.getPosition() != 7)
                throw new AssertionError("Move multiple échoué");
            report.logSuccess("testMove");
        } catch (Throwable e) {
            report.logFailed("testMove", e);
        }
    }

    private static void testDefensiveEquipment() {
        try {
            Character c = new Warrior("EquipmentTester");
            if (!c.getDefensiveInfo().equals("Aucun équipement défensif")) {
                throw new AssertionError("Pas d'équipements défensif au début");
            }

            DefensiveEquipment testItem = new StandardPotion();
            c.setDefensiveEquipment(testItem);

            if (c.getDefensiveEquipment() != testItem) {
                throw new AssertionError("L'ajout de l'équipement défensif à echoué");
            }
            if (!c.getDefensiveInfo().contains("Potion")) {
                throw new AssertionError("L'info textuelle ne reflète pas le nouvel équipement");
            }

            report.logSuccess("testDefensiveEquipment");
        } catch (Throwable e) {
            report.logFailed("testDefensiveEquipment", e);
        }
    }

    private static void testToString() {
        try {
            Character c = new Warrior("Conan");
            String s = c.toString();

            if (!s.contains("Warrior"))
                throw new AssertionError("ToString manque le type");
            if (!s.contains("Conan"))
                throw new AssertionError("ToString manque le nom");
            if (!s.contains("Vie"))
                throw new AssertionError("ToString manque la vie");

            report.logSuccess("testToString");
        } catch (Throwable e) {
            report.logFailed("testToString", e);
        }
    }
}
