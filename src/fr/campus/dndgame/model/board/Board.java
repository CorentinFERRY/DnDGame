package fr.campus.dndgame.model.board;

import fr.campus.dndgame.model.enemies.Dragon;
import fr.campus.dndgame.model.equipments.*;
import fr.campus.dndgame.model.equipments.defensives.LargePotion;
import fr.campus.dndgame.model.equipments.defensives.StandardPotion;
import fr.campus.dndgame.model.equipments.offensives.FireBall;
import fr.campus.dndgame.model.equipments.offensives.Lightning;
import fr.campus.dndgame.model.equipments.offensives.Mace;
import fr.campus.dndgame.model.equipments.offensives.Sword;

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
        Mace mace = new Mace();
        Sword sword = new Sword();
        Lightning light = new Lightning();
        FireBall fireBall = new FireBall();
        LargePotion largePotion = new LargePotion();
        StandardPotion standardPotion = new StandardPotion();
        Dragon dragon = new Dragon();
        SurpriseBox boxMace = new SurpriseBox(mace);
        SurpriseBox boxSword = new SurpriseBox(sword);
        SurpriseBox boxLight = new SurpriseBox(light);
        SurpriseBox boxFireBall = new SurpriseBox(fireBall);
        SurpriseBox boxLargePotion = new SurpriseBox(largePotion);
        SurpriseBox boxStandardPotion = new SurpriseBox(standardPotion);
        cells.clear();
        for (int i =0 ; i < size; i++){
            cells.add(new Cell(i+1));
        }
        cells.get(1).setEnemy(dragon);
        cells.get(2).setBox(boxMace);
        cells.get(3).setBox(boxSword);
        cells.get(4).setBox(boxLight);
        cells.get(5).setBox(boxFireBall);
        cells.get(6).setBox(boxLargePotion);
        cells.get(7).setBox(boxStandardPotion);

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

    public void setSize(int size) {
        this.size = size;
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
