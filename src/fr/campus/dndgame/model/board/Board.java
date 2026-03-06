package fr.campus.dndgame.model.board;

import fr.campus.dndgame.model.enemies.Dragon;
import fr.campus.dndgame.model.enemies.Goblin;
import fr.campus.dndgame.model.enemies.Sorcerer;
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
        int [] drakePos = {45,52,56,62};
        int [] sorcererPos = {10,20,25,32,35,36,37,40,44,47};
        int [] goblinPos = {3,6,9,12,15,18,21,24,27,30};
        int [] boxMacePos = {2,11,5,22,38};
        int [] boxSwordPos = {19,26,42,53};
        int [] boxLightPos = {1,4,8,17,23};
        int [] boxFireBallPos = {48,49};
        int [] boxStandardPotionPos = {7,13,31,33,39,43};
        int [] boxLargePotionPos = {28,41};
        Mace mace = new Mace();
        Sword sword = new Sword();
        Lightning light = new Lightning();
        FireBall fireBall = new FireBall();
        LargePotion largePotion = new LargePotion();
        StandardPotion standardPotion = new StandardPotion();
        Dragon dragon = new Dragon();
        Sorcerer sorcerer = new Sorcerer();
        Goblin goblin = new Goblin();
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
        for(int pos : drakePos){
            cells.get(pos-1).setEnemy(dragon);
        }
        for(int pos : sorcererPos){
            cells.get(pos-1).setEnemy(sorcerer);
        }
        for(int pos : goblinPos){
            cells.get(pos-1).setEnemy(goblin);
        }
        for(int pos : boxFireBallPos){
            cells.get(pos-1).setBox(boxFireBall);
        }
        for(int pos : boxMacePos){
            cells.get(pos-1).setBox(boxMace);
        }
        for(int pos : boxSwordPos){
            cells.get(pos-1).setBox(boxSword);
        }
        for(int pos : boxLightPos){
            cells.get(pos-1).setBox(boxLight);
        }
        for(int pos : boxStandardPotionPos){
            cells.get(pos-1).setBox(boxStandardPotion);
        }
        for (int pos : boxLargePotionPos){
            cells.get(pos-1).setBox(boxLargePotion);
        }
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

    /**
     * Retourne le nom du plateau.
     *
     * @return nom du plateau
     */
    public String getName() {
        return name;
    }

    /**
     * Définit le nom du plateau.
     *
     * @param name nouveau nom du plateau
     */
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
     * Définit le nombre de cases du plateau.
     *
     * @param size taille du plateau
     */
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
