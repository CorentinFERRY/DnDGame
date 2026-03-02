package fr.campus.dndgame.board;

/**
 * Classe représentant une case du plateau de jeu.
 * Chaque case a un numéro qui l'identifie.
 * 
 * @author CorentinFERRY
 * @version 1.0
 */
public class Cell {
    private int number;

    /**
     * Constructeur pour créer une case.
     *
     * @param number Le numéro identifiant la case
     */
    public Cell(int number){
        this.number = number;
    }
    
    /**
     * Retourne le numéro de la case.
     *
     * @return Le numéro de la case
     */
    public int getNumber() {
        return number;
    }
    
    /**
     * Définit le numéro de la case.
     *
     * @param number Le nouveau numéro
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * Retourne une représentation textuelle de la case.
     *
     * @return Une chaîne décrivant la case
     */
    public String toString() {
        return "Case n°: " + number;
    }
}
