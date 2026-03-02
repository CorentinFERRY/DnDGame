package fr.campus.dndgame.characters;


import fr.campus.dndgame.equipments.DefensiveEquipment;

public abstract class Character {
    private final String type;
    private int health;
    private final int maxHealth;
    private final int attack;
    private String name;
    private int position;
    private DefensiveEquipment defensiveEquipment;


    protected Character (String type, String name, int health, int attack){
        this.type = type;
        this.name = name;
        this.health = health;
        this.maxHealth = health;
        this.attack = attack;
        this.defensiveEquipment = null;
    }
    //Getter and Setter
    public String getType(){
        return type;
    }
    public int getMaxHealth(){
        return maxHealth;
    }
    public int getHealth() {
        return health;
    }
    public void setHealth(int health){
        this.health = health;
    }
    public int getAttack() {
        return attack;
    }
    public String getName() {
        return name;
    }
    public void setName (String name){
        this.name = name;
    }
    public int getPosition() {
        return position;
    }
    public void setPosition(int position) {
        this.position = position;
    }
    public void setDefensiveEquipment(DefensiveEquipment equipment) {
        this.defensiveEquipment = equipment;
    }

    // Affichage
    public String getDefensiveInfo(){
        if (defensiveEquipment != null) {
            return defensiveEquipment.getType() + " : " + defensiveEquipment.getName();
        }
        return "Aucun équipement défensif";
    }
    public abstract String getOffensiveInfo();
    // Methode toString()
    public String toString() {
        return type + " '" + name + "' (Health: " + health + ", Attack: " + attack + ")";
    }

    public void move(){
        this.position += 1;
    }

    public void useDefensiveEquipment() {
        if (defensiveEquipment != null) {
            defensiveEquipment.use(this);
        } else {
            System.out.println(name + " n'a aucun équipement défensif !");
        }
    }

}
