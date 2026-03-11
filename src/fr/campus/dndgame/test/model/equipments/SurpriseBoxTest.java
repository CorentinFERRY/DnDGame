package fr.campus.dndgame.test.model.equipments;

import fr.campus.dndgame.main.model.equipments.SurpriseBox;
import fr.campus.dndgame.main.model.equipments.offensives.Weapon;
import fr.campus.dndgame.main.model.equipments.offensives.Spell;
import fr.campus.dndgame.main.model.equipments.defensives.Potion;
import fr.campus.dndgame.test.model.reports.TestReport;

public class SurpriseBoxTest {
    private static TestReport report;

    public static void main(String[] args) {
        report = new TestReport("SURPRISE BOX");
        testConstructorWithEquipmentOnly();
        testConstructorWithIdAndEquipment();
        testIdGetterSetter();
        testNameGetterSetter();
        testEquipmentGetterSetter();
        testEquipmentCanBeOffensive();
        testEquipmentCanBeDefensive();
        testToString();
        report.printSummary();
    }

    private static void testConstructorWithEquipmentOnly() {
        try {
            Weapon w = new Weapon("Épée", 5);
            SurpriseBox box = new SurpriseBox(w);

            if (box.getEquipment() == null)
                throw new AssertionError("L'équipement ne doit pas être null après construction");
            if (box.getEquipment() != w)
                throw new AssertionError("L'équipement retourné doit être celui passé au constructeur");
            if (box.getId() != 0)
                throw new AssertionError("L'id doit être 0 par défaut (constructeur sans id)");

            report.logSuccess("testConstructorWithEquipmentOnly");
        } catch (Throwable e) {
            report.logFailed("testConstructorWithEquipmentOnly", e);
        }
    }

    private static void testConstructorWithIdAndEquipment() {
        try {
            Potion p = new Potion("Potion", 10);
            SurpriseBox box = new SurpriseBox(42, p);

            if (box.getId() != 42)
                throw new AssertionError("L'id doit être 42 après construction avec id");
            if (box.getEquipment() == null)
                throw new AssertionError("L'équipement ne doit pas être null après construction");
            if (box.getEquipment() != p)
                throw new AssertionError("L'équipement retourné doit être celui passé au constructeur");

            report.logSuccess("testConstructorWithIdAndEquipment");
        } catch (Throwable e) {
            report.logFailed("testConstructorWithIdAndEquipment", e);
        }
    }

    private static void testIdGetterSetter() {
        try {
            SurpriseBox box = new SurpriseBox(new Weapon("Dague", 2));

            box.setId(99);
            if (box.getId() != 99)
                throw new AssertionError("Setter/Getter ID échoué");

            report.logSuccess("testIdGetterSetter");
        } catch (Throwable e) {
            report.logFailed("testIdGetterSetter", e);
        }
    }

    private static void testNameGetterSetter() {
        try {
            SurpriseBox box = new SurpriseBox(new Weapon("Épée", 3));
            if (box.getName() != null)
                throw new AssertionError("Le nom doit être null par défaut");
            box.setName("Boîte mystère");
            if (!box.getName().equals("Boîte mystère"))
                throw new AssertionError("Setter/Getter Name échoué");

            report.logSuccess("testNameGetterSetter");
        } catch (Throwable e) {
            report.logFailed("testNameGetterSetter", e);
        }
    }

    private static void testEquipmentGetterSetter() {
        try {
            Weapon w = new Weapon("Épée", 5);
            SurpriseBox box = new SurpriseBox(w);

            Potion p = new Potion("Potion", 10);
            box.setEquipment(p);

            if (box.getEquipment() != p)
                throw new AssertionError("Setter/Getter Equipment échoué");
            if (box.getEquipment() == w)
                throw new AssertionError("L'ancien équipement ne doit plus être retourné après setEquipment()");

            report.logSuccess("testEquipmentGetterSetter");
        } catch (Throwable e) {
            report.logFailed("testEquipmentGetterSetter", e);
        }
    }

    private static void testEquipmentCanBeOffensive() {
        try {
            Weapon w = new Weapon("Hache", 6);
            SurpriseBox box = new SurpriseBox(w);

            if (!box.getEquipment().isOffensive())
                throw new AssertionError("La boîte doit pouvoir contenir un équipement offensif");

            Spell s = new Spell("Foudre", 7);
            box.setEquipment(s);

            if (!box.getEquipment().isOffensive())
                throw new AssertionError("La boîte doit pouvoir contenir un sort offensif");

            report.logSuccess("testEquipmentCanBeOffensive");
        } catch (Throwable e) {
            report.logFailed("testEquipmentCanBeOffensive", e);
        }
    }

    private static void testEquipmentCanBeDefensive() {
        try {
            Potion p = new Potion("Potion de vie", 12);
            SurpriseBox box = new SurpriseBox(p);

            if (box.getEquipment().isOffensive())
                throw new AssertionError("La boîte doit pouvoir contenir un équipement défensif");

            report.logSuccess("testEquipmentCanBeDefensive");
        } catch (Throwable e) {
            report.logFailed("testEquipmentCanBeDefensive", e);
        }
    }

    private static void testToString() {
        try {
            Weapon w = new Weapon("Épée courte", 3);
            SurpriseBox box = new SurpriseBox(w);

            String boxStr = box.toString();
            String equipmentStr = w.toString();
            if (!boxStr.equals(equipmentStr))
                throw new AssertionError("toString() de SurpriseBox doit déléguer à l'équipement : attendu '"
                        + equipmentStr + "', obtenu '" + boxStr + "'");

            Potion p = new Potion("Grande Potion", 15);
            box.setEquipment(p);
            boxStr = box.toString();
            equipmentStr = p.toString();
            if (!boxStr.equals(equipmentStr))
                throw new AssertionError(
                        "toString() doit refléter le nouvel équipement après setEquipment() : attendu '" + equipmentStr
                                + "', obtenu '" + boxStr + "'");

            report.logSuccess("testToString");
        } catch (Throwable e) {
            report.logFailed("testToString", e);
        }
    }
}