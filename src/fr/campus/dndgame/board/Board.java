package fr.campus.dndgame.board;

/**
 * Classe représentant le plateau de jeu.
 * Le plateau est composé d'une série de cases que les personnages doivent traverser.
 * 
 * @author CorentinFERRY
 * @version 1.0
 */
public class Board {
    private final int size;
    private Cell[] cells;

    /**
     * Constructeur pour créer un plateau de jeu.
     *
     * @param size Nombre de cases du plateau
     */
    public Board(int size){
        this.size = size;

        cells = new Cell[size];
        for (int i =0 ; i < size; i++){
            cells[i] = new Cell(i+1);
        }
    }
    
    /**
     * Retourne le nombre total de cases du plateau.
     *
     * @return La taille du plateau
     */
    public int getSize() {
        return size;
    }

    /**
     * Retourne la case à une position donnée.
     * Si la position est hors limites, retourne la première ou dernière case.
     *
     * @param position La position demandée
     * @return La case à cette position
     */
    public Cell getCell(int position) {
        if (position < 1) {
            return cells[0];
        }
        if (position > size) {
            return cells[size - 1];
        }
        return cells[position - 1];
    }

    /**
     * Vérifie si une position correspond à la dernière case du plateau.
     *
     * @param position La position à vérifier
     * @return true si la position atteint ou dépasse la fin, false sinon
     */
    public boolean isLastCell(int position) {
        return position >= size;
    }

    /**
     * Retourne une représentation textuelle du plateau.
     *
     * @return Une chaîne décrivant le plateau
     */
    public String toString() {
        return "Le plateau à " + size + " cases.";
    }
}
