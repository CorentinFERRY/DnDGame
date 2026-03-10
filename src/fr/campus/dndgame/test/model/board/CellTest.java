package fr.campus.dndgame.test.model.board;

import fr.campus.dndgame.main.model.board.Cell;
import fr.campus.dndgame.main.model.characters.Warrior;
import fr.campus.dndgame.main.model.enemies.Goblin;
import fr.campus.dndgame.main.model.equipments.SurpriseBox;
import fr.campus.dndgame.main.model.equipments.offensives.Mace;
import fr.campus.dndgame.test.model.rapports.TestReport;

public class CellTest {
    // Compteur de tests passés ou echoués
    private static TestReport report;

    public static void main(String[] args) {

        report = new TestReport("CELL");
        // Exécution des tests
        testConstructor();
        testSetEnemy();
        testSetBox();
        testGettersSettersBDD();
        testIsEmpty();
        testInteraction();

        // Affichage du rapport de tests
        report.printSummary();
    }

    // --- TEST DES METHODES ---
    private static void testConstructor() {
        try {
            Cell c = new Cell(5);

            if (c.getNumber() != 5)
                throw new AssertionError("Number incorrect");
            if (!c.isEmpty())
                throw new AssertionError("Doit être vide au début");
            if (c.getEnemy() != null)
                throw new AssertionError("Enemy doit etre null");

            report.logSuccess("TestConstructor");
        } catch (Throwable e) {
            report.logFailed("testConstructor", e);
        }
    }

    private static void testSetEnemy() {
        try {
            Cell c = new Cell(10);
            Goblin g = new Goblin();

            c.setEnemy(g);

            if (c.getEnemy() != g)
                throw new AssertionError("Enemy non défini");
            if (c.isEmpty())
                throw new AssertionError("Ne doit plus être vide");

            report.logSuccess("testSetEnemy");
        } catch (Throwable e) {
            report.logFailed("testSetEnemy", e);
        }
    }

    private static void testSetBox() {
        try {
            Cell c = new Cell(10);
            SurpriseBox box = new SurpriseBox(new Mace());

            c.setBox(box);

            if (c.getBox() != box)
                throw new AssertionError("Surprise box non défini");
            if (c.isEmpty())
                throw new AssertionError("Ne doit plus être vide");

            report.logSuccess("testSetBox");
        } catch (Throwable e) {
            report.logFailed("testSetBox", e);
        }
    }

    private static void testGettersSettersBDD() {
        try {
            // 1. Test via le constructeur BDD
            Cell c = new Cell(999, 5); // id=999, number=5

            if (c.getId() != 999)
                throw new AssertionError("L'ID n'est pas initialisé par le constructeur");
            if (c.getNumber() != 5)
                throw new AssertionError("Le numéro est incorrect");

            // 2. Test du setter boardId (qui n'est pas dans le constructeur)
            c.setBoardId(42);
            if (c.getBoardId() != 42)
                throw new AssertionError("Le boardId n'est pas sauvegardé");

            // 3. Test de modification dynamique (Simule une mise à jour BDD)
            c.setId(777);
            if (c.getId() != 777)
                throw new AssertionError("Le setter setId ne fonctionne pas");

            c.setNumber(10);
            if (c.getNumber() != 10)
                throw new AssertionError("Le setter setNumber ne fonctionne pas");

            report.logSuccess("testGettersSettersBDD");
        } catch (Throwable e) {
            report.logFailed("testGettersSettersBDD", e);
        }
    }

    private static void testIsEmpty() {
        try {
            Cell c = new Cell(1);
            if (!c.isEmpty())
                throw new AssertionError("Devrait être vide initialement");

            c.setBox(new SurpriseBox(new Mace()));
            if (c.isEmpty())
                throw new AssertionError("Ne devrait plus être vide avec une box");

            report.logSuccess("testIsEmpty");
        } catch (Throwable e) {
            report.logFailed("testIsEmpty", e);
        }
    }

    private static void testInteraction() {
        try {
            Cell c = new Cell(3);
            Goblin goblin = new Goblin();
            c.setEnemy(goblin);
            Warrior w = new Warrior("Test");

            if (c.isEmpty())
                throw new AssertionError("La cellule avec un ennemi ne doit pas être vide");
            if (c.getEnemy() != goblin)
                throw new AssertionError("L'ennemi doit être accessible depuis la cellule");
            // interact(w, combatService, game) requiert un Game actif — non testable
            // unitairement sans mock.

            report.logSuccess("testInteraction (Pré-conditions)");
        } catch (Throwable e) {
            report.logFailed("testInteraction", e);
        }
    }

}
