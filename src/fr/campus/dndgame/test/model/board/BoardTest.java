package fr.campus.dndgame.test.model.board;

import fr.campus.dndgame.main.model.board.Board;
import fr.campus.dndgame.main.model.board.Cell;
import fr.campus.dndgame.test.model.rapports.TestReport;

public class BoardTest {

    private static TestReport report;

    public static void main(String[] args) {
        report = new TestReport("BOARD");
        // Exécution des tests
        testConstructor();
        testSetName();
        testInitBoardLogic();
        testGetCellBounds();
        testIsLastCell();
        // Affichage du rapport de tests
        report.printSummary();
    }

    private static void testConstructor(){
        try{
            Board b1 = new Board(50);
            if (b1.getSize() != 50) throw new AssertionError("Taille incorrecte");
            if (!b1.getCells().isEmpty()) throw new AssertionError("Les cases doivent être vides au début");

            Board b2 = new Board(123, 20, "Donjon");
            if (b2.getId() != 123) throw new AssertionError("ID incorrect");
            if (!"Donjon".equals(b2.getName())) throw new AssertionError("Nom incorrect");

            report.logSuccess("testConstructeur");
        } catch (Exception e) {
            report.logFailed("testContructor", e);
        }
    }

    private static void testSetName(){
        try {
            Board b1 = new Board(10);

            b1.setName("TestBoard");
            if (!"TestBoard".equals(b1.getName())) throw new AssertionError("Nom incorrect");
            report.logSuccess("testSetBoard");
        } catch (Exception e) {
            report.logFailed("testSetBoard", e);
        }
    }


    private static void testInitBoardLogic() {
        try {
            Board b = new Board(50);
            b.initBoard();
            if (b.getCells().size() != 50) throw new AssertionError("La taille ne correspond pas");
            int enemyCount = 0;
            int boxCount = 0;
            for (Cell c : b.getCells()) {
                if (c.getEnemy() != null) enemyCount++;
                if (c.getBox() != null) boxCount++;
            }
            // Règles : 24 ennemis, 24 boîtes (défini dans l'initBoard)
            if (enemyCount != 24) throw new AssertionError("Compte ennemi incorrect: " + enemyCount);
            if (boxCount != 24) throw new AssertionError("Compte box incorrect: " + boxCount);

            report.logSuccess("testInitBoardLogic");
        } catch (Exception e) {
            report.logFailed("testInitBoardLogic", e);
        }
    }

    private static void testGetCellBounds() {
        try {
            Board b = new Board(10);
            b.initBoard();
            // Cas normal
            if (b.getCell(5).getNumber() != 5) throw new AssertionError("Case 5 incorrecte");
            // Cas limite bas (doit renvoyer case 1)
            if (b.getCell(0).getNumber() != 1) throw new AssertionError("Gestion limite basse échouée");
            // Cas limite haut (doit renvoyer case 10)
            if (b.getCell(15).getNumber() != 10) throw new AssertionError("Gestion limite haute échouée");

            report.logSuccess("testGetCellBounds");
        } catch (Exception e) {
            report.logFailed("testGetCellBounds", e);
        }
    }

    private static void testIsLastCell() {
        try {
            Board b = new Board(10);
            if (b.isLastCell(9)) throw new AssertionError("9 n'est pas la fin");
            if (!b.isLastCell(10)) throw new AssertionError("10 doit être la fin");
            if (!b.isLastCell(12)) throw new AssertionError("12 doit être considéré comme fin");
            report.logSuccess("testIsLastCell");
        } catch (Exception e) {
            report.logFailed("testIsLastCell", e);
        }
    }

}
