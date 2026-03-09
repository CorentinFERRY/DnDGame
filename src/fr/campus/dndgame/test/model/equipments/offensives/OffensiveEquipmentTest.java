package fr.campus.dndgame.test.model.equipments.offensives;

import fr.campus.dndgame.main.model.equipments.offensives.Weapon;
import fr.campus.dndgame.main.model.equipments.offensives.Spell;
import fr.campus.dndgame.test.model.rapports.TestReport;

public class OffensiveEquipmentTest {
    private static TestReport report;

    public static void main(String[] args) {
        report = new TestReport("EQUIPMENT (Offensif)");
        testWeaponConstructorAndStats();
        testSpellConstructorAndStats();
        testIdGetterSetter();
        testAttackBonusGetterSetter();
        testGetEffect();
        testSetEffect();
        testIsOffensive();
        testToString();
        report.printSummary();
    }

    private static void testWeaponConstructorAndStats() {
        try {
            Weapon w = new Weapon("Épée longue", 5);

            if (w.getName() == null || w.getName().isEmpty())
                throw new AssertionError("Le nom de la Weapon ne doit pas être vide");
            if (!w.getName().equals("Épée longue"))
                throw new AssertionError("Le nom de la Weapon est incorrect");
            if (!w.getType().equals("Weapon"))
                throw new AssertionError("Le type doit être 'Weapon'");
            if (w.getAttackBonus() != 5)
                throw new AssertionError("Le bonus d'attaque doit être 5");

            report.logSuccess("testWeaponConstructorAndStats");
        } catch (Exception e) {
            report.logFailed("testWeaponConstructorAndStats", e);
        }
    }

    private static void testSpellConstructorAndStats() {
        try {
            Spell s = new Spell("Boule de feu", 8);

            if (s.getName() == null || s.getName().isEmpty())
                throw new AssertionError("Le nom du Spell ne doit pas être vide");
            if (!s.getName().equals("Boule de feu"))
                throw new AssertionError("Le nom du Spell est incorrect");
            if (!s.getType().equals("Spell"))
                throw new AssertionError("Le type doit être 'Spell'");
            if (s.getAttackBonus() != 8)
                throw new AssertionError("Le bonus d'attaque doit être 8");

            report.logSuccess("testSpellConstructorAndStats");
        } catch (Exception e) {
            report.logFailed("testSpellConstructorAndStats", e);
        }
    }

    private static void testIdGetterSetter() {
        try {
            Weapon w = new Weapon("Masse", 3);
            w.setId(42);
            if (w.getId() != 42)
                throw new AssertionError("Setter/Getter ID échoué sur Weapon");

            Spell s = new Spell("Éclair", 6);
            s.setId(77);
            if (s.getId() != 77)
                throw new AssertionError("Setter/Getter ID échoué sur Spell");

            report.logSuccess("testIdGetterSetter");
        } catch (Exception e) {
            report.logFailed("testIdGetterSetter", e);
        }
    }

    private static void testAttackBonusGetterSetter() {
        try {
            Weapon w = new Weapon("Hache", 4);
            w.setAttackBonus(10);
            if (w.getAttackBonus() != 10)
                throw new AssertionError("Setter/Getter AttackBonus échoué sur Weapon");

            Spell s = new Spell("Foudre", 5);
            s.setAttackBonus(12);
            if (s.getAttackBonus() != 12)
                throw new AssertionError("Setter/Getter AttackBonus échoué sur Spell");

            report.logSuccess("testAttackBonusGetterSetter");
        } catch (Exception e) {
            report.logFailed("testAttackBonusGetterSetter", e);
        }
    }

    private static void testGetEffect() {
        try {
            Weapon w = new Weapon("Dague", 6);
            if (w.getEffect() != 6)
                throw new AssertionError("getEffect() doit retourner attackBonus sur Weapon");

            Spell s = new Spell("Foudre", 9);
            if (s.getEffect() != 9)
                throw new AssertionError("getEffect() doit retourner attackBonus sur Spell");

            report.logSuccess("testGetEffect");
        } catch (Exception e) {
            report.logFailed("testGetEffect", e);
        }
    }

    private static void testSetEffect() {
        try {
            // setEffect() doit modifier attackBonus ET être cohérent avec getAttackBonus()
            Weapon w = new Weapon("Dague", 2);
            w.setEffect(10);
            if (w.getEffect() != 10)
                throw new AssertionError("setEffect() n'a pas mis à jour getEffect() sur Weapon");
            if (w.getAttackBonus() != 10)
                throw new AssertionError("setEffect() n'est pas cohérent avec getAttackBonus() sur Weapon");

            Spell s = new Spell("Glace", 3);
            s.setEffect(15);
            if (s.getEffect() != 15)
                throw new AssertionError("setEffect() n'a pas mis à jour getEffect() sur Spell");
            if (s.getAttackBonus() != 15)
                throw new AssertionError("setEffect() n'est pas cohérent avec getAttackBonus() sur Spell");

            report.logSuccess("testSetEffect");
        } catch (Exception e) {
            report.logFailed("testSetEffect", e);
        }
    }

    private static void testIsOffensive() {
        try {
            Weapon w = new Weapon("Épée", 4);
            if (!w.isOffensive())
                throw new AssertionError("Une Weapon doit être offensive");

            Spell s = new Spell("Éclair", 6);
            if (!s.isOffensive())
                throw new AssertionError("Un Spell doit être offensif");

            report.logSuccess("testIsOffensive");
        } catch (Exception e) {
            report.logFailed("testIsOffensive", e);
        }
    }

    private static void testToString() {
        try {
            Weapon w = new Weapon("Épée courte", 3);
            String ws = w.toString();
            if (!ws.contains("Weapon"))
                throw new AssertionError("toString() de Weapon doit contenir le type : " + ws);
            if (!ws.contains("Épée courte"))
                throw new AssertionError("toString() de Weapon doit contenir le nom : " + ws);
            if (!ws.contains("3"))
                throw new AssertionError("toString() de Weapon doit contenir le bonus d'attaque : " + ws);

            Spell s = new Spell("Boule de feu", 7);
            String ss = s.toString();
            if (!ss.contains("Spell"))
                throw new AssertionError("toString() de Spell doit contenir le type : " + ss);
            if (!ss.contains("Boule de feu"))
                throw new AssertionError("toString() de Spell doit contenir le nom : " + ss);
            if (!ss.contains("7"))
                throw new AssertionError("toString() de Spell doit contenir le bonus d'attaque : " + ss);

            report.logSuccess("testToString");
        } catch (Exception e) {
            report.logFailed("testToString", e);
        }
    }
}
