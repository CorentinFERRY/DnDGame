package fr.campus.dndgame.test.model.equipments.defensives;

import fr.campus.dndgame.main.model.characters.Character;
import fr.campus.dndgame.main.model.characters.Warrior;
import fr.campus.dndgame.main.model.characters.Wizard;
import fr.campus.dndgame.main.model.equipments.defensives.Potion;
import fr.campus.dndgame.test.model.rapports.TestReport;

public class DefensiveEquipmentTest {
    private static TestReport report;

    public static void main(String[] args) {
        report = new TestReport("EQUIPMENT (Défensif)");
        testPotionConstructorAndStats();
        testIdGetterSetter();
        testHealAmountGetterSetter();
        testGetEffect();
        testSetEffect();
        testIsOffensive();
        testUseRestoresHealth();
        testUseCannotExceedMaxHealth();
        testToString();
        report.printSummary();
    }

    private static void testPotionConstructorAndStats() {
        try {
            Potion p = new Potion("Potion de soin", 10);

            if (p.getName() == null || p.getName().isEmpty())
                throw new AssertionError("Le nom de la Potion ne doit pas être vide");
            if (!p.getName().equals("Potion de soin"))
                throw new AssertionError("Le nom de la Potion est incorrect");
            if (!p.getType().equals("Potion"))
                throw new AssertionError("Le type doit être 'Potion'");
            if (p.getHealAmount() != 10)
                throw new AssertionError("Le montant de soin doit être 10");

            report.logSuccess("testPotionConstructorAndStats");
        } catch (Exception e) {
            report.logFailed("testPotionConstructorAndStats", e);
        }
    }

    private static void testIdGetterSetter() {
        try {
            Potion p = new Potion("Potion mineure", 5);
            p.setId(99);
            if (p.getId() != 99)
                throw new AssertionError("Setter/Getter ID échoué sur Potion");
            report.logSuccess("testIdGetterSetter");
        } catch (Exception e) {
            report.logFailed("testIdGetterSetter", e);
        }
    }

    private static void testHealAmountGetterSetter() {
        try {
            Potion p = new Potion("Potion", 5);
            p.setHealAmount(20);
            if (p.getHealAmount() != 20)
                throw new AssertionError("Setter/Getter HealAmount échoué");
            report.logSuccess("testHealAmountGetterSetter");
        } catch (Exception e) {
            report.logFailed("testHealAmountGetterSetter", e);
        }
    }

    private static void testGetEffect() {
        try {
            Potion p = new Potion("Potion de vie", 12);
            if (p.getEffect() != 12)
                throw new AssertionError("getEffect() doit retourner healAmount");
            report.logSuccess("testGetEffect");
        } catch (Exception e) {
            report.logFailed("testGetEffect", e);
        }
    }

    private static void testSetEffect() {
        try {
            // setEffect() doit modifier healAmount ET être cohérent avec getHealAmount()
            Potion p = new Potion("Potion", 5);
            p.setEffect(25);
            if (p.getEffect() != 25)
                throw new AssertionError("setEffect() n'a pas mis à jour getEffect()");
            if (p.getHealAmount() != 25)
                throw new AssertionError("setEffect() n'est pas cohérent avec getHealAmount()");
            report.logSuccess("testSetEffect");
        } catch (Exception e) {
            report.logFailed("testSetEffect", e);
        }
    }

    private static void testIsOffensive() {
        try {
            Potion p = new Potion("Potion", 10);
            if (p.isOffensive())
                throw new AssertionError("Une Potion ne doit pas être offensive");
            report.logSuccess("testIsOffensive");
        } catch (Exception e) {
            report.logFailed("testIsOffensive", e);
        }
    }

    private static void testUseRestoresHealth() {
        try {
            // On crée un stub minimal de Character pour tester use()
            Character character = new Wizard("Héros");
            character.setHealth(1);
            Potion p = new Potion("Potion de soin", 4);
            p.use(character);
            if (character.getHealth() != 5)
                throw new AssertionError("use() doit restaurer " + 4 + " PV : attendu 5, obtenu " + character.getHealth());
            report.logSuccess("testUseRestoresHealth");
        } catch (Exception e) {
            report.logFailed("testUseRestoresHealth", e);
        }
    }

    private static void testUseCannotExceedMaxHealth() {
        try {

            Character character = new Warrior("Héros");
            character.setMaxHealth(15);
            Potion p = new Potion("Grande Potion", 10);
            p.use(character);
            if (character.getHealth() != 15)
                throw new AssertionError("use() ne doit pas dépasser les PV max : attendu 15, obtenu " + character.getHealth());
            report.logSuccess("testUseCannotExceedMaxHealth");
        } catch (Exception e) {
            report.logFailed("testUseCannotExceedMaxHealth", e);
        }
    }

    private static void testToString() {
        try {
            Potion p = new Potion("Grande Potion", 15);
            String ps = p.toString();
            if (!ps.contains("Potion"))
                throw new AssertionError("toString() doit contenir le type : " + ps);
            if (!ps.contains("Grande Potion"))
                throw new AssertionError("toString() doit contenir le nom : " + ps);
            if (!ps.contains("15"))
                throw new AssertionError("toString() doit contenir le montant de soin : " + ps);

            report.logSuccess("testToString");
        } catch (Exception e) {
            report.logFailed("testToString", e);
        }
    }
}