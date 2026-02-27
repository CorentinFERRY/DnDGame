package fr.campus.dndgame.game;

import fr.campus.dndgame.characters.Character;
import fr.campus.dndgame.characters.Warrior;
import fr.campus.dndgame.characters.Wizard;
import fr.campus.dndgame.utils.Menu;

public class Game {

    private final Menu menu;
    private Character player;

    public Game() {
        menu = new Menu();
    }

    public void start() {
        menu.showMessage("Bienvenu sur mon jeu DnD !");

        String[] mainOptions = {"Créer un personnage", "Quitter le jeu"};
        int choice = menu.displayMenu("Menu Principal",mainOptions);

        switch (choice){
            case 1 :
                createCharacter();
                break;
            case 2 :
                menu.showMessage("Au revoir !");
                return;
        }

        menu.showMessage("\nVotre personnage a été créé :");
        menu.showMessage(player.toString() + " : " + player.getOffensiveInfo());
    }

    private void createCharacter(){
        String[] types = {"Warrior", "Wizard"};
        int typeChoice = menu.displayMenu("Choisissez votre type de personnage", types);

        String name = menu.getStringInput("Entrez le nom de votre personnage :");

        switch (typeChoice){
            case 1 :
                player = new Warrior(name);
                break;
            case 2 :
                player = new Wizard(name);
                break;
        }

        menu.showMessage("Personnage créé avec succès !");
    }

    public Character getPlayer() {
        return player;
    }
}
