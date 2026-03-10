package fr.campus.dndgame.test.model.rapports;

public class TestReport {
    private final String className;
    private int passed = 0;
    private int failed = 0;

    public TestReport(String className) {
        this.className = className;
        System.out.println("=== Lancement des tests pour " + className.toUpperCase() + " ===\n");
    }

    /**
     * Enregistre un succès et l'affiche.
     */
    public void logSuccess(String testName) {
        System.out.println("[OK] " + testName);
        passed++;
    }

    /**
     * Enregistre un échec, affiche l'erreur et incrémente le compteur.
     */
    public void logFailed(String testName, Throwable e) {
        System.out.println("[ÉCHEC] " + testName);
        System.out.println("       > Erreur: " + e.getMessage());
        // e.printStackTrace(); // Décommente pour voir la pile d'appel complète en cas
        // de bug complexe
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

    public int getFailedCount() {
        return failed;
    }

    public int getPassedCount() {
        return passed;
    }
}
