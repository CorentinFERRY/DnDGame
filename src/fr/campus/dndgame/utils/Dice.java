package fr.campus.dndgame.utils;

import java.util.Random;

public class Dice {
    private final Random random;
    private final int nbrFaces;

    public Dice(int faces){
        this.nbrFaces = faces;
        this.random = new Random();
    }

    public int roll(){
        return random.nextInt(nbrFaces) + 1;
    }

    //Getter
    public int getNbrFaces() {
        return nbrFaces;
    }
    public String toString() {
        return "Dé à " + nbrFaces + " faces.";
    }
}
