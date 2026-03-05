package fr.campus.dndgame.utils;

import java.util.Random;

/**
 * Classe représentant un dé pour générer des nombres aléatoires.
 * Le dé simule les lancements utilisés dans le jeu.
 * 
 * @author CorentinFERRY
 * @version 1.0
 */
public class Dice {
    private final Random random;
    private final int nbrFaces;

    /**
     * Constructeur pour créer un dé avec un nombre de faces spécifié.
     *
     * @param faces Le nombre de faces du dé
     */
    public Dice(int faces){
        this.nbrFaces = faces;
        this.random = new Random();
    }

    /**
     * Lance le dé et retourne un nombre aléatoire.
     *
     * @return Un nombre entre 1 et le nombre de faces inclus
     */
    public int roll(){
        return random.nextInt(nbrFaces) + 1;
    }

    // ========== GETTERS ==========
    
    /**
     * Retourne le nombre de faces du dé.
     *
     * @return Le nombre de faces
     */
    public int getNbrFaces() {
        return nbrFaces;
    }
    
    /**
     * Retourne une représentation textuelle du dé.
     *
     * @return Une chaîne décrivant le dé
     */
    public String toString() {
        return "Dé à " + nbrFaces + " faces.";
    }
}
