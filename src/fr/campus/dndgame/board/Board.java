package fr.campus.dndgame.board;

public class Board {
    private final int size;
    private Cell[] cells;

    public Board(int size){
        this.size = size;

        cells = new Cell[size];
        for (int i =0 ; i < size; i++){
            cells[i] = new Cell(i+1);
        }
    }
    public int getSize() {
        return size;
    }

    public Cell getCell(int position) {
        if (position < 1) {
            return cells[0];
        }
        if (position > size) {
            return cells[size - 1];
        }
        return cells[position - 1];
    }

    public boolean isLastCell(int position) {
        return position >= size;
    }

    public String toString() {
        return "Le plateau à " + size + " cases.";
    }
}
