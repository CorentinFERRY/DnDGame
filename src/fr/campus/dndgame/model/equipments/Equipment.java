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
    public abstract int getId();
    public abstract void setId(int id);

    public abstract int getEffect();
    public abstract void setEffect(int effect);

    public abstract boolean isOffensive();
    /**
     * Retourne une représentation textuelle de l'équipement.
     *
     * @return Une chaîne décrivant l'équipement
     */
    public String toString(){
        return type + " : " + name;
    }
}
