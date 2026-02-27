package fr.campus.dndgame.equipments;

public abstract class Equipment {
    private final String type;
    private String name;

    protected Equipment(String type, String name){
        this.type = type;
        this.name = name;
    }

    //Getter & Setter
    public String getType(){
        return type;
    }
    public String getName() {
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    //Methode toString()
    public String toString(){
        return type + " : " + name;
    }
}
