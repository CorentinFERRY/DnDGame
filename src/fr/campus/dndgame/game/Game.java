package fr.campus.dndgame.game;

import fr.campus.dndgame.board.Board;
import fr.campus.dndgame.characters.Character;
import fr.campus.dndgame.characters.Warrior;
import fr.campus.dndgame.characters.Wizard;
import fr.campus.dndgame.utils.Dice;
import fr.campus.dndgame.utils.Menu;

public class Game {

    private final Menu menu;
    private final Board board;
    private final Dice dice;
    private Character player;
    private boolean gameFinished = false;

    public Game() {
        menu = new Menu();
        board = new Board(64);
        dice = new Dice(6);
    }

    // -------------------------
    // Menu principal
    // -------------------------
    public void start() {
        menu.showMessage("Bienvenu sur mon jeu DnD !");
        boolean exit = false;

        while (!exit) {

            String[] mainOptions = {
                    "Créer un personnage",
                    "Démarrer la partie",
                    "Recommencer la partie",
                    "Quitter le jeu"
            };

            int choice = menu.displayMenu("Menu Principal", mainOptions);

            switch (choice) {
                case 1 -> createCharacter();
                case 2 -> startGame();
                case 3 -> restartGame();
                case 4 -> {
                    menu.showMessage("Au revoir !");
                    exit = true;
                }
            }
        }
    }

    // -------------------------
    // Commencer une nouvelle partie
    // -------------------------
    private void startGame() {
        if (player == null) {
            menu.showMessage("Vous devez créer un personnage avant de commencer !");
            return;
        }

        player.setPosition(1);
        gameFinished = false;
        menu.showMessage("\nLa partie commence !");
        menu.showMessage("Position actuelle : " + player.getPosition() + " / " + board.getSize());

        // Boucle tour par tour
        while (!gameFinished) {
            menu.getStringInput("\nAppuyez sur Entrée pour lancer le tour...");
            playTurn();
        }
    }

    // -------------------------
    // Recommencer la partie
    // -------------------------
    private void restartGame() {
        if (player == null) {
            menu.showMessage("Aucun personnage. Créez-en un d'abord !");
            return;
        }
        player.setPosition(1);
        gameFinished = false;
        menu.showMessage("\nPartie recommencée !");
        menu.showMessage("Position actuelle : " + player.getPosition() + " / " + board.getSize());

        // Rejouer tour par tour
        while (!gameFinished) {
            menu.getStringInput("\nAppuyez sur Entrée pour lancer le tour...");
            playTurn();
        }
    }

    // -------------------------
    // Un tour : lancer le dé et avancer case par case
    // -------------------------
    public void playTurn() {
        if (player == null) {
            menu.showMessage("Aucun personnage créé !");
            return;
        }
        if (gameFinished) {
            menu.showMessage("La partie est terminée. Choisissez Recommencer depuis le menu.");
            return;
        }
        int diceResult = dice.roll();
        menu.showMessage("\nVous lancez le dé : " + diceResult);

        // Déplacement case par case
        for (int i = 1; i <= diceResult; i++) {
            // Avancer d'une case
            player.move();
            // Vérifier si on atteint la fin
            if (board.isLastCell(player.getPosition())) {
                player.setPosition(board.getSize());
                menu.showMessage("Vous êtes en case " + player.getPosition() + " / " + board.getSize());
                menu.showMessage("\nVous avez atteint la fin du plateau !");
                gameFinished = true;
                return;
            }
            // Afficher la position après chaque case
            menu.showMessage(player.getName() + " avance d'un case, nouvelle position : "
                    + player.getPosition() + " / " + board.getSize());
        }
    }

    // -------------------------
    // Créer le personnage
    // -------------------------
    private void createCharacter() {
        String[] types = {"Warrior", "Wizard"};
        int typeChoice = menu.displayMenu("Choisissez votre type de personnage", types);

        String name = menu.getStringInput("Entrez le nom de votre personnage :");

        switch (typeChoice) {
            case 1:
                player = new Warrior(name);
                break;
            case 2:
                player = new Wizard(name);
                break;
        }

        menu.showMessage("Personnage créé avec succès !");
    }

    public Character getPlayer() {
        return player;
    }
    public boolean isGameFinished() {
        return gameFinished;
    }
}