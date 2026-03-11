package fr.campus.dndgame.main.model.enemies;

/**
 * Classe représentant un Gobelin dans le jeu.
 * Un Gobelin est un monstre faible.
 * Caractéristiques : 6 points de vie, 1 points d'attaque.
 *
 * @author CorentinFERRY
 * @version 1.0
 */
public class Goblin extends Enemy{

    /**
     * Constructeur pour créer un Gobelin.
     *
     */
    public Goblin(){
        super("Goblin",6,1);
    }
}
