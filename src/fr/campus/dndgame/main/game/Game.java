package fr.campus.dndgame.main.game;

import fr.campus.dndgame.main.db.DatabaseMaintenance;
import fr.campus.dndgame.main.factory.CharacterFactory;
import fr.campus.dndgame.main.model.board.Board;
import fr.campus.dndgame.main.model.board.Cell;
import fr.campus.dndgame.main.model.characters.Character;
import fr.campus.dndgame.main.model.enemies.Enemy;
import fr.campus.dndgame.main.model.equipments.SurpriseBox;
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
        try {
            new DatabaseMaintenance().purgeOrphans();
        } catch (SQLException e) {
            menu.showMessage("Avertissement nettoyage BDD : " + e.getMessage());
        }
        boolean exit = false;
        while (!exit) {

            String[] mainOptions = {
                    "Nouvelle partie",
                    "Recommencer une partie",
                    "Charger une partie",
                    "Créer un personnage",
                    "Modifier un personnage",
                    "Quitter le jeu"
            };

            int choice = menu.displayMenu("Menu Principal", mainOptions);

            switch (choice) {
                case 1 -> startGame();
                case 2 -> restartGame();
                case 3 -> loadGame();
                case 4 -> createCharacter();
                case 5 -> manageCharacter();
                case 6 -> {
                    menu.showMessage("Au revoir !");
                    menu.closeScanner();
                    exit = true;
                }
            }
        }
    }

    // ========== GESTION DE LA PARTIE ==========
    /**
     * Démarre une nouvelle partie avec le personnage actuel ou un personnage récupéré depuis la BDD.
     * Vérifie qu'un personnage a été créé avant de commencer.
     */
    private void startGame() {
        try {
            selectOrCreateCharacter();
        } catch (SQLException e) {
            menu.showMessage("Erreur lors du chargement des personnages : " + e.getMessage());
            return;
        }
        if (isPlayerNotReady()) return;
        board.initBoard();
        board.setId(0);
        player.setHealth(player.getMaxHealth());
        player.disarm();
        menu.showMessage("Début de la partie !");
        launchGame();
    }

    /**
     * Recommence une partie avec le personnage actuel et sa position initiale.
     * Permet le choix de garder ou changer de plateau
     * Garde les stats et équipements du personnage
     * Vérifie qu'un personnage existe avant de recommencer.
     */
    private void restartGame() {
        try {
            selectCharacter();
        } catch (SQLException e) {
            menu.showMessage("Erreur lors du chargement des personnages : " + e.getMessage());
            return;
        }
        if (isPlayerNotReady()) return;
        if (board.getCells() == null || board.getCells().isEmpty()) {
            // Aucun plateau en mémoire, on en génère un nouveau
            board.initBoard();
            board.setId(0);
            player.setHealth(player.getMaxHealth());
            player.setDefense(0);
            player.disarm();
        }
        launchGame();
    }

    /**
     * Lance la partie et la boucle de jeu
     *
     */
    private void launchGame(){
        player.setPosition(1);
        gameFinished = false;
        runGameLoop();
    }

    /**
     * Boucle de jeu tour par tour avec affichage des options à chaque tour
     */
    private void runGameLoop() {
        menu.showMessage("Position actuelle : " + player.getPosition() + " / " + board.getSize());
        // Rejouer tour par tour
        while (!gameFinished && !isPlayerNotReady() && player.isAlive()) {
            String[] turnOptions = {"Jouer le tour", "Sauvegarder", "Quitter au menu"};
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
        menu.showMessage("Vous lancez le dé : " + diceResult);

        // Déplacement case par case
        for (int i = 1; i <= diceResult; i++) {
            // Avancer d'une case
            player.move();
            // Vérifier si on atteint la fin
            if (board.isLastCell(player.getPosition())) {
                player.setPosition(board.getSize());
                handleVictory();
                return;
            }
        }
        interactWithCell();
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
            menu.showMessage(player + " " + player.getOffensiveInfo() + " " + player.getDefensiveInfo());
            int choice = menu.displayMenu("Combat",fightOptions);
            switch (choice){
                case 1 :
                    fightService.fight(player,enemy);
                    if(!enemy.isAlive()){
                        cell.setEnemy(null);
                        menu.showMessage("L'ennemi est vaincu !");
                        if (enemy.getId() > 0) {
                            try { saveService.deleteEnemy(enemy); }
                            catch (SQLException e) { menu.showMessage("Erreur suppression ennemi : " + e.getMessage()); }
                        }
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

    /**
     * Gère l'interaction du joueur avec une case du plateau.
     *
     */
    private void interactWithCell(){
        if (isPlayerNotReady()) return;
        Cell cell = board.getCell(player.getPosition());
        menu.showMessage(cell.toString());
        SurpriseBox box = cell.getBox();
        cell.interact(player, fightService, this, menu);
        // Si une box existait avant et a été ramassée (cell.getBox() == null après)
        if (box != null && cell.getBox() == null) {
            try {
                saveService.deleteBox(box);
            } catch (SQLException e) {
                menu.showMessage("Erreur suppression boîte : " + e.getMessage());
            }
        }
        menu.showMessage(player.toString() + " " + player.getOffensiveInfo() + " " + player.getDefensiveInfo());
    }

    /**
     * Fonction qui permet la gestion de la victoire et l'affichage des options possible
     */
    private void handleVictory() {
        menu.showMessage("Vous avez atteint la fin du plateau !");
        gameFinished = true;
        String[] winOptions = {"Rejouer sur ce plateau", "Nouvelle partie", "Menu Principal"};
        int choice = menu.displayMenu("Que voulez-vous faire ?", winOptions);
        switch (choice) {
            case 1 -> {
                // On garde le plateau, on sauvegarde juste le personnage sans supprimer le board
                try { saveService.saveCharacterOnly(player); }
                catch (SQLException e) { menu.showMessage("Erreur : " + e.getMessage()); }
                restartGame();
            }
            case 2 -> {
                // On supprime le plateau et on repart sur une nouvelle partie
                try { saveService.saveVictory(player, board); }
                catch (SQLException e) { menu.showMessage("Erreur : " + e.getMessage()); }
                startGame();
            }
            case 3 -> {
                // On supprime le plateau et on repart sur le menu pricipal
                try { saveService.saveVictory(player, board); }
                catch (SQLException e) { menu.showMessage("Erreur : " + e.getMessage()); }
            }
        }
    }


    // ========== CRÉATION, MODIFICATION OU SELECTION DE PERSONNAGE ==========

    /**
     * Crée un nouveau personnage en permettant au joueur de choisir le type et le nom.
     * Types disponibles : Guerrier ou Magicien.
     */
    private void createCharacter() {
        String[] types = {"Warrior", "Wizard"};
        int typeChoice = menu.displayMenu("Choisissez votre type de personnage", types);
        String name = menu.getStringInput("Entrez le nom de votre personnage :");

        player = CharacterFactory.createNewCharacter(types[typeChoice-1],name);
        try {
            saveService.saveCharacterOnly(player);
            menu.showMessage("Personnage créé et sauvegardé avec succès !");
        } catch (SQLException e) {
            menu.showMessage("Personnage créé mais erreur lors de la sauvegarde : " + e.getMessage());
        }
    }

    /**
     * Permet de sélectionner un personnage sans partie en cours ou d'en créer un au lancement de la partie
     *
     * @throws SQLException en cas d'erreur lors de l'accès à la base de données
     */
    private void selectOrCreateCharacter() throws SQLException {
        List<Character> available = saveService.listCharactersWithoutBoard();
        String[] options = new String[available.size() + 1];
        for (int i = 0; i < available.size(); i++) {
            options[i] = available.get(i).getType() + " — " + available.get(i).getName();
        }
        options[available.size()] = "Créer un nouveau personnage";

        int choice = menu.displayMenu("Choisir un personnage", options);
        if (choice <= available.size()) {
            player = available.get(choice - 1);
        } else {
            createCharacter();
        }
    }

    /**
     * Permet de sélectionner un personnage de la BDD parmis la liste de tous les personnages et
     * d'en créer un si nécessaire
     *
     * @throws SQLException en cas d'erreur lors de l'accès à la base de données
     */
    private void selectCharacter() throws SQLException {
        Character selected = pickCharacter("Choisir un personnage", true);
        if (selected != null) player = selected;
    }

    /**
     * Ouvre un menu permettant la modification d'un personnage existant
     * Modification du nom pour l'instant
     */
    private void manageCharacter() {
        try {
            Character target = pickCharacter("Quel personnage modifier ?", false);
            if (target == null) return;
            // Menu des actions disponibles (Peut s'agrandir)
            String[] actions = {"Modifier le nom", "Retour"};
            int action = menu.displayMenu("Que voulez-vous modifier ?", actions);
            switch (action) {
                case 1 -> updateCharacterName(target);
                case 2 -> {}
            }

        } catch (SQLException e) {
            menu.showMessage("Erreur : " + e.getMessage());
        }
    }

    /**
     * Permet de selectionner un personnage existant dans la BDD
     * @param title Titre du menu à afficher
     * @param withCreateOption Boolean permettant de savoir si on ajoute la possibilité
     * de créer un personnage en plus ou non
     * @return Le Character selectionné
     * @throws SQLException en cas d'erreur lors de l'accès à la base de données
     */
    private Character pickCharacter(String title, boolean withCreateOption) throws SQLException {
        List<Character> all = saveService.listAllCharacters();
        if (all.isEmpty() && !withCreateOption) {
            menu.showMessage("Aucun personnage en base. Créez-en un d'abord !");
            return null;
        }

        String[] options = new String[all.size() + 1];
        for (int i = 0; i < all.size(); i++) {
            options[i] = all.get(i).getType() + " — " + all.get(i).getName();
        }
        options[all.size()] = withCreateOption ? "Créer un nouveau personnage" : "Retour";

        int choice = menu.displayMenu(title, options);
        if (choice == options.length) {
            if (withCreateOption) createCharacter();
            return null; // Retour ou création gérée dans createCharacter()
        }
        return all.get(choice - 1);
    }

    /**
     * Modifie le nom d'un personnage créé après vérification d'un personnage existant
     *
     */
    private void updateCharacterName(Character target) throws SQLException {
        String name = menu.getStringInput("Entrez le nouveau nom de votre personnage :");
        target.setName(name);
        saveService.saveCharacterOnly(target);
        if (!isPlayerNotReady() && player.getId() == target.getId()) {
            player.setName(name);
        }
        menu.showMessage("Nom modifié avec succès !");
    }

    // ========== SAUVEGARDE ET CHARGEMENT  ==========

    /**
     * Fonction permettant la sauvegarde en BDD d'un personnage seul ou d'une partie
     *
     */
    private void saveGame() {
        if (isPlayerNotReady()) {
            menu.showMessage("Aucune partie en cours à sauvegarder !");
            return;
        }
        try{
            if (player.getPosition() == 0) {
                saveService.saveCharacterOnly(player);
                menu.showMessage("Personnage sauvegardé avec succès !");
            }
            else {
                saveService.saveGame(player, board);
                menu.showMessage("Partie sauvegardée avec succès !");
            }
        } catch (SQLException e) {
            menu.showMessage("Erreur lors de la sauvegarde : " + e.getMessage());
        }
    }

    /**
     *  Fonction qui permet de charger une partie depuis la BDD
     */
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

    /**
     * Permet la suppression d'une sauvegarde
     */
    private void deleteCurrentGame() {
        if (player.getId() == 0) return; // jamais sauvegardé, rien à supprimer
        try {
            saveService.deleteGame(board);
            menu.showMessage("Sauvegarde supprimée.");
        } catch (SQLException e) {
            menu.showMessage("Erreur lors de la suppression : " + e.getMessage());
        }
    }

    // ========== UTILITAIRES ==========

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