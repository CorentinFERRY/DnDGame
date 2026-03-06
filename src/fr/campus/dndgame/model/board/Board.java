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
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

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
        // Enemies and Boxes
        Dragon dragon = new Dragon();
        Sorcerer sorcerer = new Sorcerer();
        Goblin goblin = new Goblin();
        SurpriseBox boxMace = new SurpriseBox(new Mace());
        SurpriseBox boxSword = new SurpriseBox(new Sword());
        SurpriseBox boxLight = new SurpriseBox(new Lightning());
        SurpriseBox boxFireBall = new SurpriseBox(new FireBall());
        SurpriseBox boxLargePotion = new SurpriseBox(new LargePotion());
        SurpriseBox boxStandardPotion = new SurpriseBox(new StandardPotion());
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
        Map<Consumer<Cell>, Integer> generationRules = Map.of(
                cell -> cell.setEnemy(dragon), 4,
                cell -> cell.setEnemy(sorcerer), 10,
                cell -> cell.setEnemy(goblin), 10,
                cell -> cell.setBox(boxMace), 5,
                cell -> cell.setBox(boxLight), 5,
                cell -> cell.setBox(boxSword), 4,
                cell -> cell.setBox(boxFireBall), 2,
                cell -> cell.setBox(boxStandardPotion), 6,
                cell -> cell.setBox(boxLargePotion), 2
        );

        //On place nos éléments de façon aléatoire via placeRandom()
        for (Map.Entry<Consumer<Cell>, Integer> rule : generationRules.entrySet()) {
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

    private void placeRandom(List<Integer> positions, int count, Consumer<Cell> action) {
        for (int i = 0; i < count; i++) {
            // On supprime de la liste la cellule qui à recupérer un élement
            int pos = positions.remove(0);
            action.accept(cells.get(pos));
        }
    }
}
