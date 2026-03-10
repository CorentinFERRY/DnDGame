package fr.campus.dndgame.main.game;

import fr.campus.dndgame.main.factory.CharacterFactory;
import fr.campus.dndgame.main.model.board.Board;
import fr.campus.dndgame.main.model.board.Cell;
import fr.campus.dndgame.main.model.characters.Character;
import fr.campus.dndgame.main.model.enemies.Enemy;
import fr.campus.dndgame.main.utils.Dice;
import fr.campus.dndgame.main.utils.Menu;

/**
 * Classe principale gérant la logique du jeu Donjon et Dragons.
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
    private final FightService fightService;
    private boolean gameFinished = false;
    private boolean pendingInteraction = false;
    /**
     * Constructeur pour initialiser une nouvelle partie.
     * Crée le menu, le plateau et le dé.
     */
    public Game() {
        menu = new Menu();
        board = new Board(64);
        dice = new Dice(6);
        fightService = new FightService();
    }

    // ========== GESTION PRINCIPALE ==========
    
    /**
     * Démarre le jeu en affichant le menu principal.
     * Boucle jusqu'à ce que l'utilisateur quitte.
     */
    public void start() {
        menu.showMessage("Bienvenue sur mon jeu DnD !");
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
        player.setHealth(player.getMaxHealth());
        player.disarm();
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
        while (!gameFinished && player.isAlive()) {
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
        if (pendingInteraction) {
            pendingInteraction = false;
            menu.showMessage("Vous arrivez sur la case " + player.getPosition() + "...");
            interactWithCell();
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
                menu.showMessage("\nVous avez atteint la fin du plateau !");
                gameFinished = true;
                return;
            }
        }
        interactWithCell();

    }

    /**
     * Crée un nouveau personnage en permettant au joueur de choisir le type et le nom.
     * Types disponibles : Guerrier ou Magicien.
     */
    private void createCharacter() {
        String[] types = {"Warrior", "Wizard"};
        int typeChoice = menu.displayMenu("Choisissez votre type de personnage", types);

        String name = menu.getStringInput("Entrez le nom de votre personnage :");

        player = CharacterFactory.createNewCharacter(types[typeChoice-1],name);
        menu.showMessage(player + " " + player.getOffensiveInfo() + ", " + player.getDefensiveInfo());
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
     * Gère un combat entre le personnage joueur et un ennemi.
     * Affiche le menu de combat permettant au joueur d'attaquer ou de fuir.
     * Le combat se termine si l'ennemi est vaincu ou si le joueur fuit ou meurt.
     * 
     * @param player Le personnage du joueur
     * @param cell La case contenant l'ennemi
     * @param fightService Le service de combat à utiliser
     */
    public void startFight(Character player, Cell cell, FightService fightService){
        Enemy enemy = cell.getEnemy();
        boolean fightFinished = false;

        menu.showMessage("Un ennemi apparaît : " + enemy);

        while(!fightFinished && player.isAlive()){

            String[] fightOptions = {"Attaque", "Fuite"};

            int choice = menu.displayMenu("Combat",fightOptions);
            switch (choice){
                case 1 :
                    fightService.fight(player,enemy);
                    fightFinished = true;
                    break;
                case 2 :
                    int diceResult = dice.roll();
                    int newPos = Math.max(1, player.getPosition() - diceResult);
                    menu.showMessage(player.getName() + " recule de " + diceResult + " cases !");
                    player.setPosition(newPos);
                    pendingInteraction = true;
                    fightFinished = true;
                    break;
            }
        }
        if(!enemy.isAlive()){
            cell.setEnemy(null);
            menu.showMessage("L'ennemi est vaincu !");
        }
        if (!player.isAlive()){
            menu.showMessage("You lose !");
            gameFinished = true;
            this.player = null;
        }
    }

    /**
     * Gère l'interaction du joueur avec une case du plateau.
     *
     */
    private void interactWithCell(){
        Cell cell = board.getCell(player.getPosition());
        menu.showMessage(cell.toString());
        cell.interact(player, fightService, this);
        menu.showMessage(player.toString() + " " + player.getOffensiveInfo());


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