package fr.campus.dndgame.main.game;

import fr.campus.dndgame.main.factory.CharacterFactory;
import fr.campus.dndgame.main.model.board.Board;
import fr.campus.dndgame.main.model.board.Cell;
import fr.campus.dndgame.main.model.characters.Character;
import fr.campus.dndgame.main.model.enemies.Enemy;
import fr.campus.dndgame.main.utils.Dice;
import fr.campus.dndgame.main.utils.Menu;
import java.sql.SQLException;
import java.util.List;
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
    private final SaveService saveService;
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
        fightService = new FightService(menu);
        saveService = new SaveService();
    }

    // ========== GESTION PRINCIPALE ==========
    
    /**
     * Démarre le jeu en affichant le menu principal.
     * Boucle jusqu'à ce que l'utilisateur quitte.
     */
    public void start() {
        menu.showMessage("Bienvenue sur mon jeu DnD !");
        boolean exit = false;

        while (!exit) {

            String[] mainOptions = {
                    "Créer un personnage",
                    "Modifier le personnage",
                    "Nouvelle partie",
                    "Recommencer une partie",
                    "Sauvegarder la partie",
                    "Charger une partie",
                    "Quitter le jeu"
            };

            int choice = menu.displayMenu("Menu Principal", mainOptions);

            switch (choice) {
                case 1 -> createCharacter();
                case 2 -> updateCharacter();
                case 3 -> startGame();
                case 4 -> restartGame();
                case 5 -> saveGame();
                case 6 -> loadGame();
                case 7 -> {
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
        board.initBoard();
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
     * Permet le choix de garder ou changer de plateau
     * Garde les stats et équipements du personnage
     * Vérifie qu'un personnage existe avant de recommencer.
     */
    private void restartGame() {

        if (isPlayerNotReady()) {
            menu.showMessage("Aucun personnage. Créez-en un d'abord !");
            return;
        }
        String [] restartOptions = {"Garder le plateau","Nouveau plateau"};
        int choice = menu.displayMenu("Recommencer",restartOptions);
        switch (choice) {
            case 1 -> launchGame();
            case 2 -> {
                board.initBoard();
                launchGame();
            }
        }
    }

    /**
     * Lance réellement la partie
     *
     */
    private void launchGame(){
        player.setPosition(1);
        gameFinished = false;
        runGameLoop();
    }

    private void runGameLoop() {
        menu.showMessage("Position actuelle : " + player.getPosition() + " / " + board.getSize());

        // Rejouer tour par tour
        while (!gameFinished && player.isAlive()) {
            String[] turnOptions = {"Jouer", "Sauvegarder", "Quitter au menu"};
            int choice = menu.displayMenu("Que voulez-vous faire ?", turnOptions);
            switch (choice) {
                case 1 -> playTurn();
                case 2 -> saveGame();
                case 3 -> { return; } // retour au menu principal
            }
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
                String[] winOptions = {"Rejouer avec un nouveau plateau", "Rejouer sur le même plateau"};
                int choice = menu.displayMenu("Que voulez-vous faire ?", winOptions);
                switch (choice) {
                    case 1 -> restartGame(); // nouveau plateau
                    case 2 -> startGame();   // même plateau, position réinitialisée
                }
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
                    if(!enemy.isAlive()){
                        cell.setEnemy(null);
                        menu.showMessage("L'ennemi est vaincu !");
                        fightFinished = true;
                    }
                    break;
                case 2 :
                    int newPos = Math.max(1, player.getPosition() - 2);
                    menu.showMessage(player.getName() + " recule de " + 2 + " cases !");
                    player.setPosition(newPos);
                    pendingInteraction = true;
                    fightFinished = true;
                    break;
            }
        }

        if (!player.isAlive()){
            menu.showMessage("Game Over !");
            gameFinished = true;
            deleteCurrentGame();
            this.player = null;
        }
    }

    private void saveGame() {
        if (isPlayerNotReady()) {
            menu.showMessage("Aucune partie en cours à sauvegarder !");
            return;
        }
        if (player.getPosition() == 0) {
            menu.showMessage("Vous devez démarrer la partie avant de sauvegarder !");
            return;
        }
        try {
            saveService.saveGame(player, board);
            menu.showMessage("Partie sauvegardée avec succès !");
        } catch (SQLException e) {
            menu.showMessage("Erreur lors de la sauvegarde : " + e.getMessage());
        }
    }

    private void loadGame() {
        try {
            List<Character> saves = saveService.listSaves();
            if (saves.isEmpty()) {
                menu.showMessage("Aucune sauvegarde disponible.");
                return;
            }

            // Construire le menu avec les sauvegardes disponibles
            String[] options = new String[saves.size()+1];
            for (int i = 0; i < saves.size(); i++) {
                Character c = saves.get(i);
                options[i] = c.getType() + " '" + c.getName() + "'"
                        + " — Case " + c.getPosition()
                        + " — HP: " + c.getHealth() + "/" + c.getMaxHealth();
            }
            options[saves.size()] = "Retour";
            int choice = menu.displayMenu("Choisissez une sauvegarde", options);
            if (choice == options.length) {
                return;
            }
            Character chosen = saves.get(choice - 1);

            // Charger le personnage et le plateau
            Object[] result = saveService.loadGame(chosen.getId());
            player = (Character) result[0];
            Board loadedBoard = (Board) result[1];

            // Mettre à jour le board courant
            board.setId(loadedBoard.getId());
            board.setSize(loadedBoard.getSize());
            board.setName(loadedBoard.getName());
            board.setCells(loadedBoard.getCells());

            menu.showMessage("Partie chargée ! Bienvenue de retour, "
                    + player.getName() + " ! (Case " + player.getPosition() + ")");

            // Reprendre la partie là où elle était
            gameFinished = false;
            pendingInteraction = false;
            runGameLoop();

        } catch (SQLException e) {
            menu.showMessage("Erreur lors du chargement : " + e.getMessage());
        }
    }

    private void deleteCurrentGame() {
        if (player.getId() == 0) return; // jamais sauvegardé, rien à supprimer
        try {
            saveService.deleteGame(player, board);
            menu.showMessage("Sauvegarde supprimée.");
        } catch (SQLException e) {
            menu.showMessage("Erreur lors de la suppression : " + e.getMessage());
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