package fr.campus.dndgame.test.model.equipments;

import fr.campus.dndgame.main.model.equipments.defensives.Potion;
import fr.campus.dndgame.main.model.equipments.offensives.Weapon;
import fr.campus.dndgame.test.model.rapports.TestReport;

public class EquipmentTest {
    private static TestReport report;

    public static void main (String[] args){
        report = new TestReport("EQUIPMENT (Base)");
        testNameGetterSetter();
        testTypeGetterSetter();
        testToString();
        report.printSummary();
    }

    private static void testNameGetterSetter() {
        try {
            Weapon w = new Weapon("Épée", 5);
            if (!w.getName().equals("Épée"))
                throw new AssertionError("getName() incorrect après construction");

            w.setName("Dague");
            if (!w.getName().equals("Dague"))
                throw new AssertionError("Setter/Getter Name échoué");

            Potion p = new Potion("Potion de soin", 10);
            if (!p.getName().equals("Potion de soin"))
                throw new AssertionError("getName() incorrect après construction (Potion)");

            p.setName("Grande Potion");
            if (!p.getName().equals("Grande Potion"))
                throw new AssertionError("Setter/Getter Name échoué (Potion)");

            report.logSuccess("testNameGetterSetter");
        } catch (Exception e) {
            report.logFailed("testNameGetterSetter", e);
        }
    }

    private static void testTypeGetterSetter() {
        try {
            Weapon w = new Weapon("Épée", 5);
            if (!w.getType().equals("Weapon"))
                throw new AssertionError("getType() incorrect après construction");

            w.setType("CustomType");
            if (!w.getType().equals("CustomType"))
                throw new AssertionError("Setter/Getter Type échoué");

            Potion p = new Potion("Potion", 10);
            if (!p.getType().equals("Potion"))
                throw new AssertionError("getType() incorrect après construction (Potion)");

            report.logSuccess("testTypeGetterSetter");
        } catch (Exception e) {
            report.logFailed("testTypeGetterSetter", e);
        }
    }

    private static void testToString() {
        try {
            Weapon w = new Weapon("Épée courte", 3);
            String ws = w.toString();
            if (!ws.contains("Weapon"))
                throw new AssertionError("toString() doit contenir le type : " + ws);
            if (!ws.contains("Épée courte"))
                throw new AssertionError("toString() doit contenir le nom : " + ws);

            Potion p = new Potion("Potion mineure", 5);
            String ps = p.toString();
            if (!ps.contains("Potion"))
                throw new AssertionError("toString() doit contenir le type : " + ps);
            if (!ps.contains("Potion mineure"))
                throw new AssertionError("toString() doit contenir le nom : " + ps);

            report.logSuccess("testToString");
        } catch (Exception e) {
            report.logFailed("testToString", e);
        }
    }

}
