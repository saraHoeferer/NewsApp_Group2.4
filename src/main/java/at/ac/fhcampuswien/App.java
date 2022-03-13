package at.ac.fhcampuswien;

public class App {
    public static void main (String[] args){
        AppController ctrl = new AppController();
        ctrl.getAllNewsBitcoin();
        Menu menu = new Menu();
        menu.start();
    }
}
