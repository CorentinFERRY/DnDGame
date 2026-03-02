package fr.campus.dndgame.board;

public class Cell {
    private int number;

    public Cell(int number){
        this.number = number;
    }
    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }

    public String toString() {
        return "Case n°: " + number;
    }
}
