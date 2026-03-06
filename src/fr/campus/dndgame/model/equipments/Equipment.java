package fr.campus.dndgame.model.equipments;

/**
 * Classe abstraite représentant un équipement dans le jeu.
 * Un équipement possède un type et un nom.
 * 
 * @author CorentinFERRY
 * @version 1.0
 */
public abstract class Equipment {
    private String type;
    private String name;
    private int effect;

    /**
     * Constructeur protégé pour initialiser un équipement.
     *
     * @param type Le type d'équipement
     * @param name Le nom de l'équipement
     */
    protected Equipment(String type, String name){
        this.type = type;
        this.name = name;
    }

    // ========== GETTERS & SETTERS ==========

    /**
     * Définit le type de l'équipement.
     *
     * @param type type d'équipement
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Retourne le type de l'équipement.
     *
     * @return Le type d'équipement
     */
    public String getType(){
        return type;
    }
    
    /**
     * Retourne le nom de l'équipement.
     *
     * @return Le nom de l'équipement
     */
    public String getName() {
        return name;
    }
    
    /**
     * Définit le nom de l'équipement.
     *
     * @param name Le nouveau nom
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Retourne l'identifiant de persistance de l'équipement.
     *
     * @return identifiant de l'équipement
     */
    public abstract int getId();

    /**
     * Définit l'identifiant de persistance de l'équipement.
     *
     * @param id identifiant de l'équipement
     */
    public abstract void setId(int id);

    /**
     * Retourne la valeur d'effet portée par l'équipement.
     *
     * @return valeur d'effet
     */
    public abstract int getEffect();

    /**
     * Définit la valeur d'effet portée par l'équipement.
     *
     * @param effect valeur d'effet
     */
    public abstract void setEffect(int effect);

    /**
     * Indique si l'équipement est offensif.
     *
     * @return {@code true} si offensif, sinon {@code false}
     */
    public abstract boolean isOffensive();
    /**
     * Retourne une représentation textuelle de l'équipement.
     *
     * @return Une chaîne décrivant l'équipement
     */
    @Override
    public String toString(){
        return type + " : " + name;
    }
}
