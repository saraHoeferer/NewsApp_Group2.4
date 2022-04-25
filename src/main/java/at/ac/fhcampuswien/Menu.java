package at.ac.fhcampuswien;

import java.util.Scanner;

public class Menu {
    //instance variables
    private AppController controller;
    private static final String INVALID_INPUT_MESSAGE = "Please enter a valid input!";
    private static final String EXIT_MESSAGE = "Bye bye!";

    //constructor
    Menu(){
        controller = new AppController();
    }

    //starting method for program (user input + print menu)
    public void start(){
        Scanner scanner = new Scanner(System.in);
        String input;
        do {
            printMenu();
            input = scanner.nextLine();
            handleInput(input);
        } while (!input.equals("q"));
    }

    //handle user input
    private void handleInput(String input){
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
    }

    //get amount of articles
    private void getArticleCount(AppController ctrl){
        //System.out.println(controller.getArticleCount());
    }

    //get all news
    private void getTopHeadlinesAustria(AppController ctrl){
        //System.out.println(controller.getTopHeadlinesAustria());
    }

    //get all news about bitcoin
    private void getAllNewsBitcoin(AppController ctrl){
        //System.out.println(controller.getAllNewsBitcoin());
    }

    //prints exit message
    private static void printExitMessage(){
        System.out.println(EXIT_MESSAGE);
    }

    //prints invalid input message
    private static void printInvalidInputMessage(){
        System.out.println(INVALID_INPUT_MESSAGE);
    }

    //prints menu
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
