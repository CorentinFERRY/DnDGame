package fr.campus.dndgame.characters;


import fr.campus.dndgame.equipments.DefensiveEquipment;

/**
 * Classe abstraite représentant un personnage dans le jeu.
 * Les personnages possèdent des caractéristiques comme la santé, l'attaque,
 * une position sur le plateau et un équipement défensif optionnel.
 * 
 * @author CorentinFERRY
 * @version 1.0
 */
public abstract class Character {
    private final String type;
    private int health;
    private int maxHealth;
    private int attack;
    private String name;
    private int position;
    private int defense;
    private DefensiveEquipment defensiveEquipment;


    /**
     * Constructeur protégé pour initialiser un personnage.
     * Il défini également les points de vie maximum du personnage.
     *
     * @param type Le type de personnage (ex: "Warrior", "Wizard")
     * @param name Le nom du personnage
     * @param health La santé initiale du personnage
     * @param attack La force d'attaque du personnage
     */
    protected Character (String type, String name, int health, int attack){
        this.type = type;
        this.name = name;
        this.health = health;
        this.maxHealth = health;
        this.attack = attack;
        this.defensiveEquipment = null;
        this.defense = 0;
    }
    
    // ========== GETTERS et SETTERS ==========
    /**
     * Retourne le type du personnage.
     *
     * @return Le type du personnage
     */
    public String getType(){
        return type;
    }
    
    /**
     * Retourne la santé maximale du personnage.
     *
     * @return La santé maximale
     */
    public int getMaxHealth(){
        return maxHealth;
    }
    
    /**
     * Retourne la santé actuelle du personnage.
     *
     * @return La santé actuelle
     */
    public int getHealth() {
        return health;
    }
    
    /**
     * Définit la santé actuelle du personnage.
     *
     * @param health Nouvelle valeur de santé
     */
    public void setHealth(int health){
        this.health = health;
    }
    
    /**
     * Retourne la force d'attaque du personnage.
     *
     * @return La force d'attaque
     */
    public int getAttack() {
        return attack;
    }
    
    /**
     * Retourne le nom du personnage.
     *
     * @return Le nom du personnage
     */
    public String getName() {
        return name;
    }
    
    /**
     * Définit le nom du personnage.
     *
     * @param name Nouveau nom
     */
    public void setName (String name){
        this.name = name;
    }
    
    /**
     * Retourne la position actuelle du personnage sur le plateau.
     *
     * @return La position du personnage
     */
    public int getPosition() {
        return position;
    }
    
    /**
     * Définit la position du personnage sur le plateau.
     *
     * @param position Nouvelle position
     */
    public void setPosition(int position) {
        this.position = position;
    }
    
    /**
     * Définit l'équipement défensif du personnage.
     *
     * @param equipment L'équipement défensif à attribuer
     */
    public void setDefensiveEquipment(DefensiveEquipment equipment) {
        this.defensiveEquipment = equipment;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    // ========== MÉTHODES D'AFFICHAGE ==========
    
    /**
     * Retourne une chaîne de caractères représentant l'équipement défensif actuel.
     *
     * @return Informations sur l'équipement défensif ou message si aucun équipement
     */
    public String getDefensiveInfo(){
        if (defensiveEquipment != null) {
            return defensiveEquipment.getType() + " : " + defensiveEquipment.getName();
        }
        return "Aucun équipement défensif";
    }
    
    /**
     * Retourne une chaîne de caractères représentant l'équipement offensif actuel.
     * Cette méthode doit être implémentée par les sous-classes.
     *
     * @return Informations sur l'équipement offensif
     */
    public abstract String getOffensiveInfo();

    /**
     * Retourne une représentation textuelle du personnage.
     *
     * @return Une chaîne de caractères décrivant le personnage
     */
    public String toString() {
        return type + " '" + name + "' (Vie: " + health + ", Attaque: " + attack + ")";
    }

    // ========== MÉTHODES UTILITAIRES ==========
    /**
     * Déplace le personnage d'une case sur le plateau.
     */
    public void move(){
        this.position += 1;
    }

    /**
     * Utilise l'équipement défensif du personnage.
     * Affiche un message si aucun équipement n'est disponible.
     */
    public void useDefensiveEquipment() {
        if (defensiveEquipment != null) {
            defensiveEquipment.use(this);
        } else {
            System.out.println(name + " n'a aucun équipement défensif !");
        }
    }

}
