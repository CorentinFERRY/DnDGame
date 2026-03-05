package fr.campus.dndgame.model.board;

import fr.campus.dndgame.model.enemies.Dragon;
import fr.campus.dndgame.model.equipments.Potion;
import fr.campus.dndgame.model.equipments.Weapon;
import fr.campus.dndgame.model.utils.SurpriseBox;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant le plateau de jeu.
 * Le plateau est composé d'une série de cases que les personnages doivent traverser.
 * 
 * @author CorentinFERRY
 * @version 1.0
 */
public class Board {
    private int id;
    private String name;
    private int size;
    private List<Cell> cells;

    /**
     * Constructeur pour créer un plateau de jeu.
     *
     * @param size Nombre de cases du plateau
     */
    public Board(int size){
        this.size = size;
        this.cells = new ArrayList<>();
    }

    /**
     * Constructeur pour créer un plateau de jeu depuis la BDD.
     * @param id l'identifiant du plateau en BDD
     * @param name Le nom du plateau de jeu
     * @param size Nombre de cases du plateau
     */
    public Board(int id,int size,String name){
        this.size = size;
        this.id=id;
        this.cells = new ArrayList<>();
        this.name = name;
    }

    /**
     * Initialise le plateau de jeu et ces cases spéciales
     *
     */
    public void initBoard(){
        Weapon weapon = new Weapon("Epée",10);
        Potion potion = new Potion("Petite potion",2);
        Dragon dragon = new Dragon();
        SurpriseBox box1 = new SurpriseBox(weapon);
        SurpriseBox box2 = new SurpriseBox(potion);
        cells.clear();
        for (int i =0 ; i < size; i++){
            cells.add(new Cell(i+1));
        }
        cells.get(1).setEnemy(dragon);
        cells.get(2).setBox(box1);
        cells.get(3).setBox(box2);
    }

    /**
     * Retourne l'id du plateau
     *
     * @return l'id
     */
    public int getId() {
        return id;
    }

    /**
     * Défini un identifiant du plateau
     *
     * @param id identifiant en BDD du plateau
     */
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retourne le nombre total de cases du plateau.
     *
     * @return La taille du plateau
     */
    public int getSize() {
        return size;
    }

    /**
     * Retourne la case à une position donnée.
     * Si la position est hors limites, retourne la première ou dernière case.
     *
     * @param position La position demandée
     * @return La case à cette position
     */
    public Cell getCell(int position) {
        if (position < 1) {
            return cells.get(0);
        }
        if (position > size) {
            return cells.get(size - 1);
        }
        return cells.get(position - 1);
    }

    /**
     * Retourne une collection de cellules
     *
     * @return List de cellules
     */
    public List<Cell> getCells() {
        return cells;
    }

    /**
     * Défini la liste de cellules du plateau
     *
     * @param cells un List de cellules
     */
    public void setCells(List<Cell> cells) {
        this.cells = cells;
    }

    /**
     * Vérifie si une position correspond à la dernière case du plateau.
     *
     * @param position La position à vérifier
     * @return true si la position atteint ou dépasse la fin, false sinon
     */
    public boolean isLastCell(int position) {
        return position >= size;
    }

    /**
     * Retourne une représentation textuelle du plateau.
     *
     * @return Une chaîne décrivant le plateau
     */
    public String toString() {
        return "Le plateau à " + size + " cases.";
    }
}
