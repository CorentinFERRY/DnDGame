package fr.campus.dndgame.model.enemies;

/**
 * Classe représentant un Dragon dans le jeu.
 * Un Dragon est un monstre fort qui a beaucoup de points de vie.
 * Caractéristiques : 15 points de vie, 4 points d'attaque.
 *
 * @author CorentinFERRY
 * @version 1.0
 */
public class Dragon extends Enemy{
    /**
     * Constructeur pour créer un Dragon.
     *
     */
    public Dragon(){
        super("Dragon",15,4);
    }
}
