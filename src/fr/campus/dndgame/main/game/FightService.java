package fr.campus.dndgame.main.game;

import fr.campus.dndgame.main.model.characters.Character;
import fr.campus.dndgame.main.model.enemies.Enemy;
import fr.campus.dndgame.main.utils.Dice;
import fr.campus.dndgame.main.utils.Menu;

/**
 * Service de gestion des combats dans le jeu.
 * Gère les interactions entre un personnage joueur et un ennemi,
 * notamment les attaques, les coups critiques et les échecs.
 * 
 * @author CorentinFERRY
 * @version 1.0
 */
public class FightService {

    Menu menu;
    Dice d20 = new Dice(20);

    /**
     * Constructeur permettant de gérer les combats
     * @param menu le menu de Game
     */
    public FightService (Menu menu){
        this.menu = menu;
    }
    /**
     * Gère un tour de combat entre un personnage et un ennemi.
     * Le personnage attaque en premier, l'ennemi riposte s'il reste vivant.
     * Les résultats du dé déterminent :
     * - Echec critique (1) : l'attaque échoue
     * - Coup critique (20) : dégâts doublés
     * - Normal : dégâts normaux
     * 
     * @param character Le personnage du joueur qui attaque
     * @param enemy L'ennemi qui se défend et riposte
     */
    public void fight(Character character, Enemy enemy){
            int diceResult = d20.roll();
            menu.showMessage("Vous lancez un dé 20: " + diceResult + "/20");
            //Attaque du joueur
            if (diceResult == 1) {
                menu.showMessage("Echec critique ! ");
            } else if (diceResult == 20) {
                menu.showMessage("Coup critique !");
                enemy.takeDamage(character.getAttack() * 2);
            } else {
                enemy.takeDamage(character.getAttack());
            }
            menu.showMessage("L'ennemi a maintenant " + enemy.getHealth() + " HP.");
            //Vérification si l'ennemi est toujours en vie ou non
            if (!enemy.isAlive()) return;

            //Riposte de l'ennemi
            character.takeDamage(enemy.getAttack());
            menu.showMessage(character.getName() + " a maintenant " + character.getHealth() + " HP");
    }
}
