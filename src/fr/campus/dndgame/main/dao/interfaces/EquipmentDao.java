package fr.campus.dndgame.main.dao.interfaces;

import fr.campus.dndgame.main.model.equipments.Equipment;

import java.sql.SQLException;
import java.util.List;

/**
 * Contrat DAO pour la persistance des équipements.
 */
public interface EquipmentDao {
    /**
     * Récupère un équipement à partir de son identifiant.
     *
     * @param id identifiant de l'équipement
     * @return équipement trouvé, ou {@code null}
     * @throws SQLException en cas d'erreur SQL
     */
    Equipment getEquipment(int id) throws SQLException;

    /**
     * Récupère tous les équipements.
     *
     * @return liste des équipements
     * @throws SQLException en cas d'erreur SQL
     */
    List<Equipment> getEquipments() throws SQLException;

    /**
     * Ajoute un équipement en base de données.
     *
     * @param equipment équipement à ajouter
     * @return nombre de lignes affectées
     * @throws SQLException en cas d'erreur SQL
     */
    int add (Equipment equipment) throws SQLException;

    /**
     * Met à jour un équipement existant.
     *
     * @param equipment équipement à mettre à jour
     * @throws SQLException en cas d'erreur SQL
     */
    void update(Equipment equipment) throws SQLException;

    /**
     * Supprime un équipement par identifiant.
     *
     * @param id identifiant de l'équipement
     * @throws SQLException en cas d'erreur SQL
     */
    void delete(int id) throws SQLException;
}
