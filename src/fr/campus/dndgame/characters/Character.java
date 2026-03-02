package fr.campus.dndgame.characters;



public abstract class Character {
    private final String type;
    private final int health;
    private final int attack;
    private String name;
    private int position;


    protected Character (String type, String name, int health, int attack){
        this.type = type;
        this.name = name;
        this.health = health;
        this.attack = attack;
        this.position = 1;

    }

    //Getter and Setter
    public String getType(){
        return type;
    }
    public int getHealth() {
        return health;
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
    public abstract String getOffensiveInfo();
    public int getPosition() {
        return position;
    }
    public void setPosition(int position) {
        this.position = position;
    }

    // Methode toString()
    public String toString() {
        return type + " '" + name + "' (Health: " + health + ", Attack: " + attack + ")";
    }
}
