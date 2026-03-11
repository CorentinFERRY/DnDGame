package fr.campus.dndgame.test.model.characters;

import fr.campus.dndgame.main.model.characters.Wizard;
import fr.campus.dndgame.main.model.equipments.offensives.Lightning;
import fr.campus.dndgame.main.model.equipments.offensives.Spell;
import fr.campus.dndgame.test.model.reports.TestReport;

public class WizardTest {
    private static TestReport report;

    public static void main(String[] args) {
        report = new TestReport("WIZARD");
        testWizardStats();
        testSpellEquipment();
        testOffensiveInfo();
        report.printSummary();
    }

    private static void testWizardStats() {
        try {
            Wizard w = new Wizard("TestWizardStats");
            if (w.getHealth() != 6)
                throw new AssertionError("Wizard doit avoir 6 PV");
            if (w.getAttack() != 8)
                throw new AssertionError("Wizard doit avoir 8 d'attaque");
            if (!"Wizard".equals(w.getType()))
                throw new AssertionError("Type doit être Wizard");

            report.logSuccess("testWizardStats");
        } catch (Throwable e) {
            report.logFailed("testWizardStats", e);
        }
    }

    private static void testSpellEquipment() {
        try {
            Wizard w = new Wizard("TestSpell");
            if (w.getSpell() != null)
                throw new AssertionError("Le Magicien ne doit pas avoir de sorts au départ !");

            Spell spell = new Lightning();
            w.setSpell(spell);
            if (w.getSpell() != spell)
                throw new AssertionError("Setter Spell échoué");
            report.logSuccess("testSpellEquipment");
        } catch (Throwable e) {
            report.logFailed("testSpellEquipment", e);
        }
    }

    private static void testOffensiveInfo() {
        try {
            Wizard w = new Wizard("TestOffensiveInfo");
            if (!w.getOffensiveInfo().equals("Aucun sort équipée")) {
                throw new AssertionError("getOffensiveInfo doit indiquer 'Aucun sort équipée' si null");
            }
            w.setSpell(new Lightning());
            String info = w.getOffensiveInfo();
            if (info == null || !info.contains("Éclair")) {
                throw new AssertionError("Le sort éclair doit être indiquée");
            }
            report.logSuccess("testOffensiveInfo");
        } catch (Throwable e) {
            report.logFailed("testOffensiveInfo", e);
        }
    }
}
