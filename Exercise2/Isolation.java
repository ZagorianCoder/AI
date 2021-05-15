import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Isolation {

    public static String[][] black_squares = new String[4][4];
    private static int playerPOSX;
    private static int playerPOSY;
    private static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        
        boolean game_over = false;
        System.out.println("__________________ISOLATION__________________");
        System.out.println("About the game:");
        System.out.println("4X4 Board\nYou can move 1 block in every turn.\nYou can move upwards, downwards, diagonially and also left and right");
        System.out.println("Coordinates are integers from 0 to 3");
        System.out.println("The game begins now!");
        System.out.println("Player1's Turn!\nInsert the coordinates of your next move.");
        getPlayerMove();
        
        
        
        
        
        
        
        
        
        
    }
    
    private static void getPlayerMove() {
        
        boolean isValidMove = true;
        
        do {
        
            System.out.print("x: ");
            playerPOSX = input.nextInt();
            System.out.print("\ny: ");
            playerPOSY = input.nextInt();
            System.out.println("\n");

            if (playerPOSX < 0 || playerPOSX > 3 ) {
                
                System.out.println("Invalid x coordinate! Valid coordinates are from 0 to 3.Try again!\n");
                isValidMove = false;

            }else if (playerPOSY < 0 || playerPOSY > 3 ) {

                System.out.println("Invalid y coordinate! Valid coordinates are from 0 to 3.Try again!\n");
                isValidMove = false;

            }else if (black_squares[playerPOSX][playerPOSY].equals("PC") || black_squares[playerPOSX][playerPOSY].equals("PL1")){

                System.out.println("Invalid move! You can't move on a black square.Try again!\n");
                isValidMove = false;

            }

        }while (!isValidMove);
        black_squares[playerPOSX][playerPOSY] = "PL1";

    }

    private static NodeArch getPCstartingPos(){

        Random handler = new Random();
        int computerPOSX = 0;
        int computerPOSY = 0;
        try {
            
            computerPOSX = handler.nextInt(4);
            computerPOSY= handler.nextInt(4);
            if (computerPOSX < 0 || computerPOSY < 0 || black_squares[computerPOSX][computerPOSY].equals("PC") || black_squares[playerPOSX][playerPOSY].equals("PL1")) {
                
                throw new Exception();

            }
            
        } catch (Exception e) {
            
            getPCstartingPos();
            
        }
        black_squares[computerPOSX][computerPOSY] = "PC";
        return new NodeArch(computerPOSX, computerPOSY, playerPOSX, playerPOSY, black_squares, new ArrayList<NodeArch>(), null, 0);
        
    }

    
    
}


class CanMove{

    
    public static boolean decide(int current_posX, int current_posY)  {
        
        int unavailable_moves = 0;
        
        if (!(up(current_posX, current_posY))){
            
            unavailable_moves += 1;
            
        }
        if(!(down(current_posX, current_posY))){
            
            unavailable_moves += 1;
            
        }
        if(!(left(current_posX, current_posY))){
            
            unavailable_moves += 1;
            
        }
        if(!(right(current_posX, current_posY))){
            
            unavailable_moves += 1;
            
        }
        if(!(upMainDiag(current_posX, current_posY))){
            
            unavailable_moves += 1;
            
        }
        if (!(downMainDiag(current_posX, current_posY))) {
            
            unavailable_moves += 1;
            
        }
        if (!(upSeconDiag(current_posX, current_posY))) {
            
            unavailable_moves += 1;
            
        }
        if (!(downSeconDiag(current_posX, current_posY))) {
            
            unavailable_moves += 1;
            
        }
        if (unavailable_moves == 8) {
            
            return false;
            
        }
        return true;
        
    }
    //
    private static boolean up(int current_posX, int current_posY) {

        if (current_posX < 0 || Isolation.black_squares[current_posX][current_posY].equals("PC") || Isolation.black_squares[current_posX][current_posY].equals("PL1")) {
            

            return false;
            
        }
        return true;

    }
    
    private static boolean down(int current_posX, int current_posY) {


        
        if (current_posX > 3 || Isolation.black_squares[current_posX][current_posY].equals("PC") || Isolation.black_squares[current_posX][current_posY].equals("PL1")) {
            

            return false;
            
        }
        return true;
    }
    
    private static boolean left(int current_posX, int current_posY) {

        if (current_posY < 0 || Isolation.black_squares[current_posX][current_posY].equals("PC") || Isolation.black_squares[current_posX][current_posY].equals("PL1")){

            return false;
        }
        return true;
    }
    
    private static boolean right(int current_posX, int current_posY) {
        
        if (current_posY > 3 || Isolation.black_squares[current_posX][current_posY].equals("PC") || Isolation.black_squares[current_posX][current_posY].equals("PL1")) {
            

            return false;
            
        }
        return true;
    }
    
    private static boolean upMainDiag(int current_posX, int current_posY) {

        if (current_posX < 0 || current_posY < 0 || Isolation.black_squares[current_posX][current_posY].equals("PC") || Isolation.black_squares[current_posX][current_posY].equals("PL1")) {


            return false;
        }
        return true;
        
    }
    
    private static boolean downMainDiag(int current_posX, int current_posY) {

        if (current_posX > 3 || current_posY > 3 || Isolation.black_squares[current_posX][current_posY].equals("PC") || Isolation.black_squares[current_posX][current_posY].equals("PL1")) {
            

            return false;
        }
        return true;
        
    }
    
    private static boolean upSeconDiag(int current_posX, int current_posY) {

        if (current_posX < 0 || current_posY > 3 || Isolation.black_squares[current_posX][current_posY].equals("PC") || Isolation.black_squares[current_posX][current_posY].equals("PL1")) {
            

            return false;
        }
        return true;

    }
    
    private static boolean downSeconDiag(int current_posX, int current_posY) {
        
        if (current_posX > 3 || current_posY < 0 || Isolation.black_squares[current_posX][current_posY].equals("PC") || Isolation.black_squares[current_posX][current_posY].equals("PL1")) {
            
            return false;
        }
        return true;
        
    }
    
}

