package fr.campus.dndgame.main.model.enemies;

/**
 * Classe représentant un Sorcier dans le jeu.
 * Un Sorcier est un monstre de niveau moyen.
 * Caractéristiques : 9 points de vie, 2 points d'attaque.
 *
 * @author CorentinFERRY
 * @version 1.0
 */
public class Sorcerer extends Enemy{
    /**
     * Constructeur pour créer un Sorcier.
     *
     */
    public Sorcerer(){
        super("Sorcier",9,2);
    }
}
