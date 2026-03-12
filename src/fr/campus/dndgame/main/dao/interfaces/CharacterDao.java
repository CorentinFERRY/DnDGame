package fr.campus.dndgame.main.dao.interfaces;

import fr.campus.dndgame.main.model.characters.Character;

import java.sql.SQLException;
import java.util.List;

/**
 * Interface de données pour les opérations CRUD sur les personnages.
 * Définit les méthodes pour ajouter, récupérer, mettre à jour et supprimer des
 * personnages
 * dans la base de données.
 * 
 * @author CorentinFERRY
 * @version 1.0
 */
public interface CharacterDao {

    /**
     * Récupère un personnage par son identifiant.
     * 
     * @param id L'identifiant du personnage
     * @return Le personnage correspondant, ou null s'il n'existe pas
     * @throws SQLException en cas d'erreur lors de l'accès à la base de données
     */
    Character getCharacter(int id)
            throws SQLException;

    /**
     * Récupère tous les personnages de la base de données.
     *
     * @return Une liste contenant tous les personnages
     * @throws SQLException en cas d'erreur lors de l'accès à la base de données
     */
    List<Character> getAllCharacters()
            throws SQLException;

    /**
     * Récupère tous les personnages de la base de données associé à un plateau.
     * 
     * @return Une liste contenant tous les personnages
     * @throws SQLException en cas d'erreur lors de l'accès à la base de données
     */
    List<Character> getCharactersInGame()
            throws SQLException;

    /**
     * Récupère tous les personnages de la base de données qui n'ont pas de partie en cours.
     *
     * @return Une liste contenant tous les personnages
     * @throws SQLException en cas d'erreur lors de l'accès à la base de données
     */
    List<Character> getCharactersWithoutBoard()
            throws SQLException;
    /**
     * Ajoute un nouveau personnage à la base de données.
     * 
     * @param character Le personnage à ajouter
     * @return L'identifiant généré du personnage ajouté
     * @throws SQLException en cas d'erreur lors de l'accès à la base de données
     */
    int add(Character character)
            throws SQLException;

    /**
     * Supprime un personnage de la base de données.
     * 
     * @param id L'identifiant du personnage à supprimer
     * @throws SQLException en cas d'erreur lors de l'accès à la base de données
     */
    void delete(int id)
            throws SQLException;

    /**
     * Met à jour un personnage existant dans la base de données.
     * 
     * @param character Le personnage avec les nouvelles données
     * @throws SQLException en cas d'erreur lors de l'accès à la base de données
     */
    void update(Character character)
            throws SQLException;

    /**
     * Met à jour la santé d'un personnage dans la base de données.
     * 
     * @param character Le personnage avec la nouvelle valeur de santé
     * @throws SQLException en cas d'erreur lors de l'accès à la base de données
     */
    void updateHealth(Character character)
            throws SQLException;

    /**
     * Récupère un personnage avec ses équipements depuis la base de données.
     *
     * @param characterId L'identifiant du personnage
     * @return Le personnage avec ses équipements, ou null s'il n'existe pas
     * @throws SQLException en cas d'erreur lors de l'accès à la base de données
     */
    Character getCharacterWithEquipment(int characterId)
            throws SQLException;
}
