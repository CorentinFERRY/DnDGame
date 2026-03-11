package fr.campus.dndgame.test.utils;

import fr.campus.dndgame.main.utils.Dice;
import fr.campus.dndgame.test.model.reports.TestReport;

public class DiceTest {
    private static TestReport report;

    public static void main(String[] args) {
        report = new TestReport("DICE");
        testConstructorAndGetter();
        testRollBounds();
        testToString();
        report.printSummary();
    }

    public static void testConstructorAndGetter() {
        try {
            // Test valeur de dé classique
            Dice d6 = new Dice(6);
            if (d6.getNbrFaces() != 6)
                throw new AssertionError("Getter faces échoué");
            Dice d20 = new Dice(20);
            if (d20.getNbrFaces() != 20)
                throw new AssertionError("Getter faces échoué");
            // Test valeur de dé bizarre
            Dice d100 = new Dice(100);
            if (d100.getNbrFaces() != 100)
                throw new AssertionError("Getter faces échoué");
            report.logSuccess("testConstructorAndGetters");
        } catch (Throwable e) {
            report.logFailed("testConstructorAndGetters", e);
        }
    }

    public static void testRollBounds() {
        try {
            Dice d6 = new Dice(6);
            int iterations = 1000;
            // On test le d6 1000 fois pour être sur que la valeur est toujours entre 1 et 6
            for (int i = 0; i < iterations; i++) {
                int result = d6.roll();
                if (result < 1 || result > 6)
                    throw new AssertionError("Le dé a retourné " + result + "(inférieur à 1) au lancer n°" + i);
            }
            // On test le d20 de la même manière
            Dice d20 = new Dice(20);
            for (int i = 0; i < iterations; i++) {
                int result = d20.roll();
                if (result < 1 || result > 20)
                    throw new AssertionError("Le dé a retourné " + result + "(inférieur à 1) au lancer n°" + i);
            }
            report.logSuccess("testRollBounds");
        } catch (Throwable e) {
            report.logFailed("testRollBounds", e);
        }
    }

    private static void testToString() {
        try {
            Dice d6 = new Dice(6);
            String s = d6.toString();

            if (!s.contains("6"))
                throw new AssertionError("ToString ne contient pas le nombre de faces");
            if (!s.contains("Dé"))
                throw new AssertionError("ToString ne contient pas 'Dé'");
            if (!s.contains("faces"))
                throw new AssertionError("ToString ne contient pas 'faces'");

            report.logSuccess("testToString");
        } catch (Throwable e) {
            report.logFailed("testToString", e);
        }
    }

}
