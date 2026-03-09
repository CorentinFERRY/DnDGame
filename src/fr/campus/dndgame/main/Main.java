package fr.campus.dndgame.main;

import fr.campus.dndgame.main.game.Game;

/**
 * Classe principale pour lancer le jeu Donjons et Dragons.
 * Cette classe initialise et démarre une partie du jeu.
 * 
 * @author CorentinFERRY
 * @version 1.0
 */
public class Main {
    /**
     * Méthode principale qui sert de point d'entrée au programme.
     * Crée une nouvelle instance du jeu et démarre la partie.
     *
     * @param args Arguments de la ligne de commande (non utilisés)
     */
    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }
}