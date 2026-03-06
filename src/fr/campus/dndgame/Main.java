package fr.campus.dndgame;

import fr.campus.dndgame.model.characters.Character;
import fr.campus.dndgame.model.characters.Warrior;
import fr.campus.dndgame.dao.impl.CharacterDaoImpl;
import fr.campus.dndgame.db.DatabaseConnection;
import fr.campus.dndgame.game.Game;

import java.sql.SQLException;

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