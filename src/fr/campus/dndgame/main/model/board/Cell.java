package fr.campus.dndgame.main.model.board;

import fr.campus.dndgame.main.model.characters.Character;
import fr.campus.dndgame.main.model.characters.Warrior;
import fr.campus.dndgame.main.model.characters.Wizard;
import fr.campus.dndgame.main.model.enemies.Enemy;
import fr.campus.dndgame.main.model.equipments.Equipment;
import fr.campus.dndgame.main.model.equipments.SurpriseBox;
import fr.campus.dndgame.main.model.equipments.defensives.Potion;
import fr.campus.dndgame.main.model.equipments.defensives.Shield;
import fr.campus.dndgame.main.model.equipments.offensives.Spell;
import fr.campus.dndgame.main.model.equipments.offensives.Weapon;

/**
 * Classe représentant une case du plateau de jeu.
 * Chaque case a un numéro qui l'identifie.
 * Un case peut etre vide (par default) avoir une boite surprise ou un ennemi
 *
 * @author CorentinFERRY
 * @version 1.0
 */
public class Cell {
    private int id;
    private int number;
    private int boardId;
    private Enemy enemy = null;
    private SurpriseBox box = null;
    private Character character = null;
    /**
     * Constructeur pour créer une case.
     *
     * @param number Le numéro identifiant la case
     */
    public Cell(int number){
        this.number = number;
    }

    /**
     * Constructeur pour créer une case depuis la BDD
     *
     * @param id L'identifiant de la cellule en BDD
     * @param number Le numéro identifiant la case
     */
    public Cell(int id,int number){
        this.id = id;
        this.number = number;
    }
    // ========== GETTERS et SETTERS =========="
    /**
     * Retourne l'identifiant de la cellule en BDD
     *
     * @return l'id de la cellule
     */
    public int getId() {
        return id;
    }
    /**
     * Défini l'identifiant de la cellule en BDD
     *
     * @param id l'identifiant en BDD
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retourne l'identifiant du plateau auquel appartient la case.
     *
     * @return identifiant du plateau
     */
    public int getBoardId() {
        return boardId;
    }

    /**
     * Définit l'identifiant du plateau auquel appartient la case.
     *
     * @param boardId identifiant du plateau
     */
    public void setBoardId(int boardId) {
        this.boardId = boardId;
    }

    /**
     * Retourne le personnage actuellement positionné sur la case.
     *
     * @return personnage présent sur la case, ou {@code null}
     */
    public Character getCharacter() {
        return character;
    }

    /**
     * Place un personnage sur la case.
     *
     * @param character personnage à placer
     */
    public void setCharacter(Character character) {
        this.character = character;
    }

    /**
     * Retourne le numéro de la case.
     *
     * @return Le numéro de la case
     */
    public int getNumber() {
        return number;
    }
    /**
     * Définit le numéro de la case.
     *
     * @param number Le nouveau numéro
     */
    public void setNumber(int number) {
        this.number = number;
    }
    /**
     * Retourne l'ennemi présent sur la case'.
     *
     * @return un ennemi
     */
    public Enemy getEnemy() {
        return enemy;
    }
    /**
     * Définit un ennemi sur la case.
     *
     * @param enemy Le nouvel enemy
     */
    public void setEnemy(Enemy enemy) {
        this.enemy = enemy;
    }
    /**
     * Retourne la boite surprise présente sur la case'.
     *
     * @return une boite
     */
    public SurpriseBox getBox() {
        return box;
    }
    /**
     * Définit une boite sur la case.
     *
     * @param box La nouvelle boite
     */
    public void setBox(SurpriseBox box) {
        this.box = box;
    }

    /**
     * Vérifie si la case est vide ou non
     *
     * @return true si la case vie false sinon
     */
    public boolean isEmpty(){
        return (enemy==null && box==null && character==null);
    }
    /**
     * Retourne une représentation textuelle de la case.
     *
     * @return Une chaîne décrivant la case et son contenu
     */
    public String toString() {
        if (enemy != null){
            return "Case n°: " + number + ".\nVous tomber sur un ennemi : " + enemy;
        }
        else if(box != null){
            return "Case n°: " + number + ".\nVous dropez une box qui contient : " + box;
        }
        else {
            return "Case n°: " + number + ".\n";
        }
    }

    public void interact(Character character){

        if(enemy != null){
            System.out.println("Un ennemi apparaît : " + enemy);
        }
        if(box != null){
            Equipment equipment = box.getEquipment();
            if (equipment instanceof Potion){
                System.out.println("Vous utilisez la potion.");
            }
            else if (equipment instanceof Shield){
                System.out.println("Vous equipez le bouclier.");
            }
            else {
                if (character instanceof Warrior){
                    if (equipment instanceof Weapon) {
                        System.out.println("Le guerrier équipe l'arme.");
                    } else {
                        System.out.println("Le guerrier ne peut pas utiliser ce sort.");
                    }
                }
                else if (character instanceof Wizard) {

                    if (equipment instanceof Spell) {
                        System.out.println("Le magicien apprend le sort.");
                    } else {
                        System.out.println("Le magicien ne peut pas utiliser cette arme.");
                    }
                }
            }
        }
    }
}
