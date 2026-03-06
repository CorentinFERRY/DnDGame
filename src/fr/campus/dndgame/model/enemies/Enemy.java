package fr.campus.dndgame.model.enemies;

/**
 * Classe abstraite représentant un ennemi dans le jeu.
 * Les ennemies possèdent des caractéristiques comme la santé et l'attaque.
 * Ils sont défini par un nom.
 *
 * @author CorentinFERRY
 * @version 1.0
 */
public abstract class Enemy {
    private int id;
    private final String name;
    private int attack;
    private int health;
    private int maxHealth;
    private int defense;
    /**
     * Constructeur protégé pour initialiser un ennemi.
     * Il défini également les points de vie maximum de l'ennemi
     *
     * @param name Le nom de l'ennemi
     * @param health La santé initiale de l'ennemi
     * @param attack La force d'attaque de l'ennemi
     */
    protected Enemy(String name, int health, int attack){
        this.name = name;
        this.attack = attack;
        this.health = health;
        this.maxHealth =health;
        this.defense = 0;
    }

    // ========== GETTERS & SETTERS ==========
    /**
     * Retourne le nom de l'ennemi.
     *
     * @return Le nom de l'ennemi
     */
    public String getName(){
        return name;
    }
    /**
     * Retourne la force d'attaque de l'ennemi.
     *
     * @return la force d'attaque de l'ennemi.
     */
    public int getAttack() {
        return attack;
    }

    /**
     * Retourne la santé actuelle de l'ennemi.
     *
     * @return La santé actuelle
     */
    public int getHealth() {
        return health;
    }

    /**
     * Définit la santé actuelle de l'ennemi.
     *
     * @param health Nouvelle valeur de santé
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * Retourne la santé maximum de l'ennemi.
     *
     * @return La santé maximum
     */
    public int getMaxHealth() {
        return maxHealth;
    }

    /**
     * Définit l'identifiant de persistance de l'ennemi.
     *
     * @param id identifiant en base de données
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retourne l'identifiant de persistance de l'ennemi.
     *
     * @return identifiant en base de données
     */
    public int getId() {
        return id;
    }

    /**
     * Définit la valeur d'attaque de l'ennemi.
     *
     * @param attack nouvelle valeur d'attaque
     */
    public void setAttack(int attack) {
        this.attack = attack;
    }

    /**
     * Définit la santé maximale de l'ennemi.
     *
     * @param maxHealth nouvelle santé maximale
     */
    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    /**
     * Retourne la valeur de défense de l'ennemi.
     *
     * @return défense courante
     */
    public int getDefense() {
        return defense;
    }

    /**
     * Définit la valeur de défense de l'ennemi.
     *
     * @param defense nouvelle valeur de défense
     */
    public void setDefense(int defense) {
        this.defense = defense;
    }

    /**
     * Retourne une représentation textuelle de l'ennemi.
     *
     * @return Une chaîne de caractères décrivant l'ennemi.
     */
    @Override
    public String toString() {
        return name + " (HP: " + health + " attaque: " + attack +")";
    }
}
