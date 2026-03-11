package fr.campus.dndgame.test.model.characters;

import fr.campus.dndgame.main.model.characters.Warrior;
import fr.campus.dndgame.main.model.equipments.offensives.Sword;
import fr.campus.dndgame.main.model.equipments.offensives.Weapon;
import fr.campus.dndgame.test.model.reports.TestReport;

public class WarriorTest {
    private static TestReport report;

    public static void main(String[] args) {
        report = new TestReport("WARRIOR");
        testWarriorStats();
        testWeaponEquipment();
        testOffensiveInfo();
        report.printSummary();
    }

    private static void testWarriorStats() {
        try {
            Warrior w = new Warrior("TestWarriorStats");
            if (w.getHealth() != 10)
                throw new AssertionError("Warrior doit avoir 10 PV");
            if (w.getAttack() != 5)
                throw new AssertionError("Warrior doit avoir 5 d'attaque");
            if (!"Warrior".equals(w.getType()))
                throw new AssertionError("Type doit être Warrior");

            report.logSuccess("testWarriorStats");
        } catch (Throwable e) {
            report.logFailed("testWarriorStats", e);
        }
    }

    private static void testWeaponEquipment() {
        try {
            Warrior w = new Warrior("TestWeapon");
            if (w.getWeapon() != null)
                throw new AssertionError("Le Guerrier ne doit pas avoir d'arme au départ !");

            Weapon sword = new Sword();
            w.setWeapon(sword);
            if (w.getWeapon() != sword)
                throw new AssertionError("Setter Weapon échoué");

            report.logSuccess("testWeaponEquipment");
        } catch (Throwable e) {
            report.logFailed("testWeaponEquipment", e);
        }
    }

    private static void testOffensiveInfo() {
        try {
            Warrior w = new Warrior("TestOffensiveInfo");
            if (!w.getOffensiveInfo().equals("Aucune arme équipée")) {
                throw new AssertionError("getOffensiveInfo doit indiquer 'Aucune arme équipée' si null");
            }
            w.setWeapon(new Sword());
            String info = w.getOffensiveInfo();
            if (info == null || !info.contains("Épée")) {
                throw new AssertionError("L'épée doit être indiquée");
            }
            report.logSuccess("testOffensiveInfo");
        } catch (Throwable e) {
            report.logFailed("testOffensiveInfo", e);
        }
    }
}
