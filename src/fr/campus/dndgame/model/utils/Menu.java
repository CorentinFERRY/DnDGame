package fr.campus.dndgame.model.utils;

import java.util.Scanner;

/**
 * Classe gérant les interactions avec l'utilisateur via la console.
 * Affiche des menus et récupère les entrées utilisateur.
 * 
 * @author CorentinFERRY
 * @version 1.0
 */
public class Menu {
    private final Scanner scanner;

    /**
     * Constructeur pour initialiser le menu avec un scanner.
     */
    public Menu(){
        scanner = new Scanner(System.in);
    }
    /**
     * Récupère une chaîne de caractères entrée par l'utilisateur.
     *
     * @param message Le message à afficher avant la saisie
     * @return La chaîne entrée par l'utilisateur
     */
    public String getStringInput(String message){
        System.out.println(message);
        return scanner.nextLine();
    }   
    /**
     * Récupère un nombre entier dans une plage spécifiée.
     * Boucle jusqu'à obtenir une entrée valide.
     *
     * @param message Le message à afficher
     * @param min La valeur minimale acceptée
     * @param max La valeur maximale acceptée
     * @return Le nombre entier entré
     */
    public int getIntInput(String message ,int min, int max) {
        int input;
        while (true) {
            System.out.println(message);
            try {
                input = Integer.parseInt(scanner.nextLine());
                if (input >= min && input <= max) {
                    return input;
                }
                else {
                    System.out.println("Le nombre doit être entre " + min + " et " + max + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrée invalide, veuillez réessayer.");
            }
        }
    }
    
    /**
     * Affiche un message à l'utilisateur.
     *
     * @param message Le message à afficher
     */
    public void showMessage(String message){
        System.out.println(message);
    }

    

    /**
     * Affiche un menu avec des options et récupère le choix de l'utilisateur.
     *
     * @param title Le titre du menu
     * @param options Tableau contenant les options du menu
     * @return Le numéro du choix (index + 1)
     */
    public int displayMenu(String title, String[] options) {
        System.out.println("\n=== " + title + " ===");
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + " - " + options[i]);
        }
        return getIntInput("Votre choix :", 1, options.length);
    }

    /**
     * Ferme le scanner pour libérer les ressources.
     */
    public void closeScanner() {
        scanner.close();
    }
}
