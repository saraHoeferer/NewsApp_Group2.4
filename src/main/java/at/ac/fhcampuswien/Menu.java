package at.ac.fhcampuswien;

import java.util.Objects;
import java.util.Scanner;

public class Menu {
    private AppController controller;
    private static final String INVALID_INPUT_MESSAGE = "Please enter a valid input!";
    private static final String EXIT_MESSAGE = "Bye bye!";

    public void start(){
        Scanner scanner = new Scanner(System.in);
        printMenu();
        String input = scanner.nextLine();
        while (!Objects.equals(input, "q")){

            switch (input){
                case "a":
                    getTopHeadlinesAustria(controller);
                    break;
                case "b":
                    getAllNewsBitcoin(controller);
                    break;
                case "y":
                    getArticleCount(controller);
                    break;
                case "q":
                    printExitMessage();
                    break;
                default:
                    printInvalidInputMessage();
            }
            printMenu();
            input = scanner.nextLine();
        }
    }

    private void handleInput(String input){}

    private void getArticleCount(AppController ctrl){}

    private void getTopHeadlinesAustria(AppController ctrl){
        ctrl.getTopHeadlinesAustria();
    }

    private void getAllNewsBitcoin(AppController ctrl){}

    private static void printExitMessage(){
        System.out.println(EXIT_MESSAGE);
    }

    private static void printInvalidInputMessage(){
        System.out.println(INVALID_INPUT_MESSAGE);
    }

    private static void printMenu(){
        System.out.println("*****************************");
        System.out.println(" *    Welcome to NewsApp   * ");
        System.out.println("*****************************");
        System.out.println("Enter what you wanna do:");
        System.out.println("a: Get top headlines austria");
        System.out.println("b: Get all news about bitcoin");
        System.out.println("y: Count articles");
        System.out.println("q: Quit program");
    }

}
