package fr.campus.dndgame.utils;

import java.util.Scanner;

public class Menu {
    private final Scanner scanner;

    public Menu(){
        scanner = new Scanner(System.in);
    }

    public String getStringInput(String message){
        System.out.println(message);
        return scanner.nextLine();
    }
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
    public void showMessage(String message){
        System.out.println(message);
    }

    public int displayMenu(String title, String[] options) {
        System.out.println("\n=== " + title + " ===");
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + " - " + options[i]);
        }
        return getIntInput("Votre choix :", 1, options.length);
    }

    public void closeScanner() {
        scanner.close();
    }
}
