package fr.campus.dndgame.main.game;

import fr.campus.dndgame.main.model.characters.Character;
import fr.campus.dndgame.main.model.enemies.Enemy;
import fr.campus.dndgame.main.utils.Dice;
import fr.campus.dndgame.main.utils.Menu;

public class CombatService {

    Menu menu = new Menu();
    Dice d20 = new Dice(20);

    public void fight(Character character, Enemy enemy){
        int diceResult = d20.roll();
        menu.showMessage("Vous lancez le dé : ");
        menu.showMessage("Résultat : " + diceResult);
        //Attaque du joueur
        if(diceResult == 1){
            menu.showMessage("Echec critique ! ");
        } else if (diceResult == 20) {
            menu.showMessage("Coup critique !");
            enemy.takeDamage(character.getAttack()*2);
        }
        else {
            enemy.takeDamage(character.getAttack());
            menu.showMessage("L'ennemi a maintenant " + enemy.getHealth() + " HP.");
        }
        //Vérification si l'ennemi est toujours en vie ou non
        if (!enemy.isAlive()) {
            menu.showMessage("L'ennemi est mort !");
            return;
        }
        //Riposte de l'ennemi
        character.takeDamage(enemy.getAttack());
        menu.showMessage(character.getName() + " a maintenant " + character.getHealth() + " HP");
        menu.showMessage("L'ennemi s'enfuit !");

    }
}
