package fr.campus.dndgame.game;

import fr.campus.dndgame.board.Board;
import fr.campus.dndgame.characters.Character;
import fr.campus.dndgame.characters.Warrior;
import fr.campus.dndgame.characters.Wizard;
import fr.campus.dndgame.utils.Dice;
import fr.campus.dndgame.utils.Menu;

/**
 * Classe principale gérant la logique du jeu Dond et Dragons.
 * Gère le création des personnages, le déroulement des tours et la progression du jeu.
 * 
 * @author CorentinFERRY
 * @version 1.0
 */
public class Game {

    private final Menu menu;
    private final Board board;
    private final Dice dice;
    private Character player;
    private boolean gameFinished = false;

    /**
     * Constructeur pour initialiser une nouvelle partie.
     * Crée le menu, le plateau et le dé.
     */
    public Game() {
        menu = new Menu();
        board = new Board(64);
        dice = new Dice(6);
    }

    // ========== GESTION PRINCIPALE ==========
    
    /**
     * Démarre le jeu en affichant le menu principal.
     * Boucle jusqu'à ce que l'utilisateur quitte.
     */
    public void start() {
        menu.showMessage("Bienvenu sur mon jeu DnD !");
        board.initBoard();
        boolean exit = false;

        while (!exit) {

            String[] mainOptions = {
                    "Créer un personnage",
                    "Modifier le personnage",
                    "Démarrer la partie",
                    "Recommencer la partie",
                    "Quitter le jeu"
            };

            int choice = menu.displayMenu("Menu Principal", mainOptions);

            switch (choice) {
                case 1 -> createCharacter();
                case 2 -> updateCharacter();
                case 3 -> startGame();
                case 4 -> restartGame();
                case 5 -> {
                    menu.showMessage("Au revoir !");
                    menu.closeScanner();
                    exit = true;
                }
            }
        }
    }

    // ========== DÉMARRAGE ET RELANCE DE LA PARTIE ==========
    /**
     * Démarre une nouvelle partie avec le personnage actuel.
     * Vérifie qu'un personnage a été créé avant de commencer.
     */
    private void startGame() {
        if (isPlayerNotReady()) {
            menu.showMessage("Vous devez créer un personnage avant de commencer !");
            return;
        }
        menu.showMessage("\nDébut de la partie !");
        launchGame();
    }
    /**
     * Recommence une partie avec le personnage actuel et sa position initiale.
     * Vérifie qu'un personnage existe avant de recommencer.
     */
    private void restartGame() {
        if (isPlayerNotReady()) {
            menu.showMessage("Aucun personnage. Créez-en un d'abord !");
            return;
        }
        menu.showMessage("\nPartie recommencée !");
        launchGame();
    }

    /**
     * Lance réellement la partie
     *
     */
    private void launchGame(){
        player.setPosition(1);
        gameFinished = false;
        menu.showMessage("Position actuelle : " + player.getPosition() + " / " + board.getSize());

        // Rejouer tour par tour
        while (!gameFinished) {
            menu.getStringInput("\nAppuyez sur Entrée pour lancer le tour...");
            playTurn();
        }
    }

    /**
     * Exécute un tour de jeu : lance le dé et avance le personnage.
     * Affiche les cases spéciales et vérifie si le plateau est terminé.
     */
    public void playTurn() {
        if (isPlayerNotReady()) {
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
        // Affiche si la case contient un ennemi ou une boite surprise
        menu.showMessage(board.getCell(player.getPosition()).toString());
    }

    /**
     * Crée un nouveau personnage en permettant au joueur de choisir le type et le nom.
     * Types disponibles : Guerrier ou Magicien.
     */
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

        menu.showMessage(player.toString() + " " + player.getOffensiveInfo() + ", " + player.getDefensiveInfo());
        menu.showMessage("Personnage créé avec succès !");

    }

    /**
     * Modifie le nom d'un personnage créé après vérification d'un personnage existant
     *
     */
    private void updateCharacter(){
        if (isPlayerNotReady()) {
            menu.showMessage("Aucun personnage. Créez-en un d'abord !");
            return;
        }
        String name = menu.getStringInput("Entrez le nouveau nom de votre personnage :");
        player.setName(name);
        menu.showMessage(player.toString() + " " + player.getOffensiveInfo() + ", " + player.getDefensiveInfo());
        menu.showMessage("Nom modifié avec succès !");
    }
    /**
     * Retourne le personnage actuel du jeu.
     *
     * @return Le personnage joueur
     */
    public Character getPlayer() {
        return player;
    }
    
    /**
     * Vérifie si la partie est terminée.
     *
     * @return true si la partie est finie, false sinon
     */
    public boolean isGameFinished() {
        return gameFinished;
    }

    /**
     * Vérifie qu'un personnage est crée
     *
     * @return true si le personnage n'existe pas sinon false
     */
    private boolean isPlayerNotReady(){
        return player == null;
    }
}