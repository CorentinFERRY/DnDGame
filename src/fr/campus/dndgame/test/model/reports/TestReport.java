package fr.campus.dndgame.test.model.reports;

/**
 * Permet l'affichage du rapport de test d'une méthode
 */
public class TestReport {
    private final String className;
    private int passed = 0;
    private int failed = 0;

    /**
     * @param className Nom de la classe testé
     */
    public TestReport(String className) {
        this.className = className;
        System.out.println("=== Lancement des tests pour " + className.toUpperCase() + " ===\n");
    }

    /**
     * Enregistre un succès et l'affiche.
     * @param testName Nom de la classe testé
     */
    public void logSuccess(String testName) {
        System.out.println("[OK] " + testName);
        passed++;
    }

    /**
     * Enregistre un échec, affiche l'erreur et incrémente le compteur.
     * @param testName Nom de la classe testé
     * @param e Exception levée
     */
    public void logFailed(String testName, Throwable e) {
        System.out.println("[ÉCHEC] " + testName);
        System.out.println("       > Erreur: " + e.getMessage());
        e.printStackTrace();
        failed++;
    }

    /**
     * Affiche le résumé final et renvoie le nombre d'échecs (utile pour le main).
     */
    public void printSummary() {
        System.out.println("\n--- RAPPORT FINAL : " + className + " ---");
        System.out.println("Tests exécutés : " + (passed + failed));
        System.out.println("Succès         : " + passed);
        System.out.println("Échecs         : " + failed);

        if (failed == 0) {
            System.out.println("STATUS : ✅ TOUS LES TESTS SONT PASSÉS\n");
        } else {
            System.out.println("STATUS : ❌ DES TESTS ONT ÉCHOUÉ\n");
        }
    }
}
