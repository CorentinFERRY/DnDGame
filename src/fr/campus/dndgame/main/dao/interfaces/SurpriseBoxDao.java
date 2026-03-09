package fr.campus.dndgame.main.dao.interfaces;

import fr.campus.dndgame.main.model.equipments.SurpriseBox;

import java.sql.SQLException;
import java.util.List;

/**
 * Contrat DAO pour la persistance des boîtes surprises.
 */
public interface SurpriseBoxDao {

    /**
     * Récupère une boîte surprise à partir de son identifiant.
     *
     * @param id identifiant de la boîte
     * @return boîte surprise trouvée, ou {@code null}
     * @throws SQLException en cas d'erreur SQL
     */
    SurpriseBox getSurpriseBox(int id) throws SQLException;

    /**
     * Récupère toutes les boîtes surprises.
     *
     * @return liste des boîtes surprises
     * @throws SQLException en cas d'erreur SQL
     */
    List<SurpriseBox> getAllSurpriseBox() throws SQLException;

    /**
     * Ajoute une boîte surprise en base de données.
     *
     * @param box boîte à ajouter
     * @return nombre de lignes affectées
     * @throws SQLException en cas d'erreur SQL
     */
    int add(SurpriseBox box) throws SQLException;

    /**
     * Met à jour une boîte surprise existante.
     *
     * @param box boîte à mettre à jour
     * @throws SQLException en cas d'erreur SQL
     */
    void update (SurpriseBox box) throws SQLException;

    /**
     * Supprime une boîte surprise par identifiant.
     *
     * @param id identifiant de la boîte
     * @throws SQLException en cas d'erreur SQL
     */
    void delete (int id) throws SQLException;
}
