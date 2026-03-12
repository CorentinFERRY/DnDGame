package fr.campus.dndgame.main.game;

import fr.campus.dndgame.main.dao.impl.*;
import fr.campus.dndgame.main.model.board.Board;
import fr.campus.dndgame.main.model.board.Cell;
import fr.campus.dndgame.main.model.characters.Character;
import fr.campus.dndgame.main.model.characters.Warrior;
import fr.campus.dndgame.main.model.characters.Wizard;
import fr.campus.dndgame.main.model.enemies.Enemy;
import fr.campus.dndgame.main.model.equipments.Equipment;
import fr.campus.dndgame.main.model.equipments.SurpriseBox;
import fr.campus.dndgame.main.model.equipments.defensives.DefensiveEquipment;

import java.sql.SQLException;
import java.util.List;

/**
 * Service responsable de la persistance des parties du jeu.
 * Gère la sauvegarde, le chargement, la liste et la suppression des parties
 * en orchestrant les différents DAO (Character, Board, Cell, Enemy, Equipment,
 * SurpriseBox).
 * L'ordre des opérations respecte les contraintes de clés étrangères de la base
 * de données.
 * 
 */
public class SaveService {
    private final CharacterDaoImpl characterDao = new CharacterDaoImpl();
    private final BoardDaoImpl boardDao = new BoardDaoImpl();
    private final CellDaoImpl cellDao = new CellDaoImpl();
    private final EnemyDaoImpl enemyDao = new EnemyDaoImpl();
    private final OffensiveEquipmentDaoImpl offensiveEquipmentDao = new OffensiveEquipmentDaoImpl();
    private final DefensiveEquipmentDaoImpl defensiveEquipmentDao = new DefensiveEquipmentDaoImpl();
    private final SurpriseBoxDaoImpl surpriseBoxDao = new SurpriseBoxDaoImpl();

    /**
     * Sauvegarde l'état complet d'une partie (joueur, plateau et contenu des
     * cases).
     * L'ordre de sauvegarde respecte les dépendances de clés étrangères :
     * équipements → plateau → contenu des cases → cases → joueur.
     *
     * @param player le personnage du joueur à sauvegarder
     * @param board  le plateau de jeu à sauvegarder
     * @throws SQLException en cas d'erreur lors de l'accès à la base de données
     */
    public void saveGame(Character player, Board board) throws SQLException {
        // Ordre important : les dépendances d'abord (Ordre de la BDD)
        saveOffensiveEquipment(player); // 1. équipement offensif du joueur
        saveDefensiveEquipment(player); // 2. équipement défensif du joueur
        saveBoard(board); // 3. le plateau

        for (Cell cell : board.getCells()) {
            saveCellContents(cell); // 4. ennemis et boîtes de chaque case
            cell.setBoardId(board.getId());
            saveCell(cell); // 5. la case elle-même
        }

        player.setBoardId(board.getId()); // 6. lier le joueur au plateau
        saveCharacter(player); // 7. le joueur en dernier
    }

    /**
     * Insère ou met à jour le personnage joueur en base de données.
     *
     * @param player le personnage à persister
     * @throws SQLException en cas d'erreur lors de l'accès à la base de données
     */
    private void saveCharacter(Character player) throws SQLException {
        if (player.getId() > 0)
            characterDao.update(player);
        else
            characterDao.add(player);
    }

    /**
     * Insère ou met à jour le plateau de jeu en base de données.
     *
     * @param board le plateau à persister
     * @throws SQLException en cas d'erreur lors de l'accès à la base de données
     */
    private void saveBoard(Board board) throws SQLException {
        if (board.getId() > 0)
            boardDao.update(board);
        else
            boardDao.add(board);
    }

    /**
     * Insère ou met à jour une case du plateau en base de données.
     *
     * @param cell la case à persister
     * @throws SQLException en cas d'erreur lors de l'accès à la base de données
     */
    private void saveCell(Cell cell) throws SQLException {
        if (cell.getId() > 0)
            cellDao.update(cell);
        else
            cellDao.add(cell);
    }

    /**
     * Insère ou met à jour l'équipement offensif du joueur en base de données.
     * Récupère l'arme du {@link Warrior} ou le sort du {@link Wizard} selon le type
     * du joueur.
     * 
     *
     * @param player le personnage dont l'équipement offensif est à persister
     * @throws SQLException en cas d'erreur lors de l'accès à la base de données
     */
    private void saveOffensiveEquipment(Character player) throws SQLException {
        Equipment equip = null;
        if (player instanceof Warrior warrior && warrior.getWeapon() != null)
            equip = warrior.getWeapon();
        else if (player instanceof Wizard wizard && wizard.getSpell() != null)
            equip = wizard.getSpell();

        if (equip != null) {
            if (equip.getId() > 0)
                offensiveEquipmentDao.update(equip);
            else
                offensiveEquipmentDao.add(equip);
        }
    }

    /**
     * Insère ou met à jour l'équipement défensif du joueur en base de données.
     *
     * @param player le personnage dont l'équipement défensif est à persister
     * @throws SQLException en cas d'erreur lors de l'accès à la base de données
     */
    private void saveDefensiveEquipment(Character player) throws SQLException {
        DefensiveEquipment equip = player.getDefensiveEquipment();
        if (equip != null) {
            if (equip.getId() > 0)
                defensiveEquipmentDao.update(equip);
            else
                defensiveEquipmentDao.add(equip);
        }
    }

    /**
     * Insère ou met à jour le contenu d'une case (ennemi et/ou boîte surprise) en
     * base de données.
     * Si la case contient une {@link SurpriseBox}, l'équipement qu'elle renferme
     * est également
     * persisté avant la boîte elle-même, afin de respecter la contrainte de clé
     * étrangère.
     * 
     *
     * @param cell la case dont le contenu est à persister
     * @throws SQLException en cas d'erreur lors de l'accès à la base de données
     */
    private void saveCellContents(Cell cell) throws SQLException {
        // Sauvegarder l'ennemi si présent
        if (cell.getEnemy() != null) {
            Enemy enemy = cell.getEnemy();
            if (enemy.getId() > 0)
                enemyDao.update(enemy);
            else
                enemyDao.add(enemy);
        }
        // Sauvegarder la boîte et son équipement si présente
        if (cell.getBox() != null) {
            SurpriseBox box = cell.getBox();
            Equipment equip = box.getEquipment();
            if (equip.isOffensive()) {
                if (equip.getId() > 0)
                    offensiveEquipmentDao.update(equip);
                else
                    offensiveEquipmentDao.add(equip);
            } else {
                if (equip.getId() > 0)
                    defensiveEquipmentDao.update(equip);
                else
                    defensiveEquipmentDao.add(equip);
            }
            if (box.getId() > 0)
                surpriseBoxDao.update(box);
            else
                surpriseBoxDao.add(box);
        }
    }

    /**
     * Retourne la liste de tous les personnages sauvegardés, utilisée pour afficher
     * le menu de chargement de partie.
     *
     * @return la liste des personnages présents en base de données
     * @throws SQLException en cas d'erreur lors de l'accès à la base de données
     */
    public List<Character> listSaves() throws SQLException {
        return characterDao.getCharacters();
    }

    /**
     * Charge une partie complète à partir de l'identifiant du personnage choisi.
     * Reconstruit le joueur avec son équipement, le plateau associé et toutes ses
     * cases.
     *
     * @param characterId l'identifiant du personnage à charger
     * @return un tableau {@code Object[]} contenant le {@link Character} en index 0
     *         et le {@link Board} en index 1
     * @throws SQLException si le personnage ou le plateau est introuvable,
     *                      ou en cas d'erreur d'accès à la base de données
     */
    public Object[] loadGame(int characterId) throws SQLException {
        Character player = characterDao.getCharacterWithEquipment(characterId);
        if (player == null)
            throw new SQLException("Personnage introuvable : " + characterId);
        Board board = boardDao.getBoard(player.getBoardId());
        if (board == null)
            throw new SQLException("Plateau introuvable : " + player.getBoardId());
        List<Cell> cells = cellDao.getCellsByBoardId(board.getId());
        board.setCells(cells);
        return new Object[] { player, board };
    }

    /**
     * Supprime une partie complète de la base de données.
     * L'ordre de suppression respecte les contraintes de clés étrangères :
     * équipements → ennemis → boîtes → cases → plateau → joueur.
     *
     * @param player le personnage de la partie à supprimer
     * @param board  le plateau de la partie à supprimer
     * @throws SQLException en cas d'erreur lors de l'accès à la base de données
     */
    public void deleteGame(Character player, Board board) throws SQLException {
        // Supprimer l'équipement offensif du joueur
        if (player instanceof Warrior warrior && warrior.getWeapon() != null)
            offensiveEquipmentDao.delete(warrior.getWeapon().getId());
        else if (player instanceof Wizard wizard && wizard.getSpell() != null)
            offensiveEquipmentDao.delete(wizard.getSpell().getId());

        // Supprimer l'équipement défensif du joueur
        if (player.getDefensiveEquipment() != null)
            defensiveEquipmentDao.delete(player.getDefensiveEquipment().getId());

        // Supprimer les ennemis, boîtes et cells
        for (Cell cell : board.getCells()) {
            if (cell.getEnemy() != null)
                enemyDao.delete(cell.getEnemy().getId());
            if (cell.getBox() != null) {
                if (cell.getBox().getEquipment().isOffensive())
                    offensiveEquipmentDao.delete(cell.getBox().getEquipment().getId());
                else
                    defensiveEquipmentDao.delete(cell.getBox().getEquipment().getId());
                surpriseBoxDao.delete(cell.getBox().getId());
            }
            if (cell.getId() > 0)
                cellDao.delete(cell.getId());
        }

        // Supprimer le board
        if (board.getId() > 0)
            boardDao.delete(board.getId());

        // Supprimer le personnage en dernier
        if (player.getId() > 0)
            characterDao.delete(player.getId());
    }
}
