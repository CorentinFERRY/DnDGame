package fr.campus.dndgame.main.model.board;

import fr.campus.dndgame.main.model.enemies.Dragon;
import fr.campus.dndgame.main.model.enemies.Enemy;
import fr.campus.dndgame.main.model.enemies.Goblin;
import fr.campus.dndgame.main.model.enemies.Sorcerer;
import fr.campus.dndgame.main.model.equipments.*;
import fr.campus.dndgame.main.model.equipments.defensives.LargePotion;
import fr.campus.dndgame.main.model.equipments.defensives.StandardPotion;
import fr.campus.dndgame.main.model.equipments.offensives.FireBall;
import fr.campus.dndgame.main.model.equipments.offensives.Lightning;
import fr.campus.dndgame.main.model.equipments.offensives.Mace;
import fr.campus.dndgame.main.model.equipments.offensives.Sword;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

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
        cells.clear();
        // On crée une liste de positions
        List<Integer> positions = new ArrayList<>();

        for (int i =0 ; i < size; i++){
            cells.add(new Cell(i+1));
            positions.add(i);
        }
        // On mélange de manière aléatoire les positions
        Collections.shuffle(positions);

        // On créer un map avec les règles (4 dragons, 10 Sorciers etc ...)
        Map<Supplier<Object>, Integer> generationRules = Map.of(
                Dragon::new, 4,
                Sorcerer::new, 10,
                Goblin::new, 10,
                () -> new SurpriseBox(new Mace()),             5,
                () -> new SurpriseBox(new Lightning()),        5,
                () -> new SurpriseBox(new Sword()),            4,
                () -> new SurpriseBox(new FireBall()),         2,
                () -> new SurpriseBox(new StandardPotion()),   6,
                () -> new SurpriseBox(new LargePotion()),      2
        );

        //On place nos éléments de façon aléatoire via placeRandom()
        for (Map.Entry<Supplier<Object>, Integer> rule : generationRules.entrySet()) {
            placeRandom(positions, rule.getValue(), rule.getKey());
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

    /**
     * Place aléatoirement les éléments de sur mon plateau
     *
     * @param positions Liste des positions disponible (préalablement mélangée)
     * @param count Le nombre d'élément à placer
    /**
     * Place des éléments de manière aléatoire sur le plateau.
     * Crée des instances d'ennemis ou de boîtes surprises et les place sur des positions aléatoires.
     * Les positions utilisées sont retirées de la liste pour éviter les doublons.
     * 
     * @param positions Une liste de positions disponibles sur le plateau
     * @param count Le nombre d'éléments à placer
     * @param supplier Un supplier pour créer les instances d'éléments (Enemy ou SurpriseBox)
     */
    private void placeRandom(List<Integer> positions, int count, Supplier<Object> supplier) {
        for (int i = 0; i < count && !positions.isEmpty(); i++) {
            int pos = positions.remove(0);  // retire la position pour éviter les doublons
            Object entity = supplier.get();
            if (entity instanceof Enemy enemy) {
                cells.get(pos).setEnemy(enemy);
            } else if (entity instanceof SurpriseBox box) {
                cells.get(pos).setBox(box);
            }
        }
    }
}
