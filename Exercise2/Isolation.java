import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Isolation {

    public static String[][] black_squares = new String[4][4];
    public static int playerPOSX;
    public static int playerPOSY;
    private static Scanner input = new Scanner(System.in);
    private static NodeArch pc;

    public static void main(String[] args) {

        fillEmptyBoard();
        System.out.println("__________________ISOLATION__________________");
        System.out.println("About the game:");
        System.out.println("4X4 Board\nYou can move 1 block in every turn.\nYou can move upwards, downwards, diagonially and also left and right");
        System.out.println("Coordinates are integers from 0 to 3");
        System.out.println("The game begins now!");
        System.out.println("Player1's Turn!\nInsert the coordinates of your next move.");
        getPlayerMove();
        pc = getPCstartingPos();
        pc.setState_origin(pc);
        System.out.println("Player1 staring at (" + playerPOSX + "," + playerPOSY + ")\n");
        System.out.println("opponentAI starting at (" + pc.getCoord_i() + "," + pc.getCoord_j() + ")\n");
        startGame();
        
        
        
        
        
        
        
        
        
        
    }

    private static void fillEmptyBoard() {
        
        for (int i = 0; i < black_squares.length; i++) {
            
            for (int j = 0; j < black_squares.length; j++) {
                
                black_squares[i][j] = "_";
                
            }
        }

    }
    
    private static void startGame() {
        
        while (true) {
            
            if (playersTurn()) {

                System.out.println("opponentAI wins\nGAME OVER!!");
                return;
            }
            //print new state of board
            System.out.println("Player1 moves to (" + playerPOSX + "," + playerPOSY + ")\n");
            if (computersTurn(pc)) {
                
                System.out.println("Player1 wins\nCongrats game finished");
                return;
            }
            //print new state of board
            System.out.println("opponentAI moves to (" + pc.getCoord_i() + "," + pc.getCoord_j() + ")\n");
        }
    }
    
    
    private static boolean playersTurn() {

        if (CanMove.decide(playerPOSX, playerPOSY)) {
            
            System.out.println("Player1's Turn!\nInsert the coordinates of your next move.");
            getPlayerMove();
            return false;
        }
        return true;
    
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
        
        //Random handler = new Random();
        int computerPOSX = 1;
        int computerPOSY = 2;
        /*try {
            
            computerPOSX = handler.nextInt(4);
            computerPOSY= handler.nextInt(4);
            if (computerPOSX < 0 || computerPOSY < 0 || computerPOSX > 3 || computerPOSY > 3|| black_squares[computerPOSX][computerPOSY].equals("PC") || black_squares[computerPOSX][computerPOSY].equals("PL1")) {
                
                throw new Exception();
                
            }
            
        } catch (Exception e) {
            
            getPCstartingPos();
            
        }*/
        black_squares[computerPOSX][computerPOSY] = "PC";
        return new NodeArch(computerPOSX, computerPOSY, playerPOSX, playerPOSY, black_squares, new ArrayList<NodeArch>(), null, 0);
        
    }
    

    private static boolean computersTurn(NodeArch pc) {

        if (CanMove.decide(pc.getCoord_i(), pc.getCoord_j())) {
            
            System.out.println("opponentAI Turn");
            
            MiniMax.driver(pc);
    
            black_squares[pc.getNext_move().getCoord_i()][pc.getNext_move().getCoord_j()] = "PC";
    
            pc = new NodeArch(pc.getNext_move().getCoord_i(), pc.getNext_move().getCoord_j(),  pc.getNext_move().getRival_coord_i(), pc.getNext_move().getRival_coord_j(), black_squares, new ArrayList<NodeArch>(), null, 0);
            pc.setState_origin(pc);
            pc.setName("PC");
            return false;

        }
        return true;

    }
    
    
}


class CanMove{
    
    
    public static boolean decide(int current_posX, int current_posY)  {
        
        int unavailable_moves = 0;
        
        if (!canUp(current_posX, current_posY)){
            
            unavailable_moves += 1;
            
        }
        if(!canDown(current_posX, current_posY)){
            
            unavailable_moves += 1;
            
        }
        if(!canLeft(current_posX, current_posY)){
            
            unavailable_moves += 1;
            
        }
        if(!canRight(current_posX, current_posY)){
            
            unavailable_moves += 1;
            
        }
        if(!canUpMainDiag(current_posX, current_posY)){
            
            unavailable_moves += 1;
            
        }
        if (!canDownMainDiag(current_posX, current_posY)) {
            
            unavailable_moves += 1;
            
        }
        if (!canUpSeconDiag(current_posX, current_posY)) {
            
            unavailable_moves += 1;
            
        }
        if (!canDownSeconDiag(current_posX, current_posY)) {
            
            unavailable_moves += 1;
            
        }
        if (unavailable_moves == 8) {
            
            
            return false;
            
        }
        return true;
        
    }

    private static boolean canUp(int current_posX, int current_posY) {
        
        int next_posx = current_posX - 1;
        int next_posy = current_posY;
        
        return isvalid(next_posx, next_posy);

    }
    
    private static boolean canDown(int current_posX, int current_posY) {


        
        int next_posx = current_posX + 1;
        int next_posy = current_posY;
        
        return isvalid(next_posx, next_posy);
    }
    
    private static boolean canLeft(int current_posX, int current_posY) {

        int next_posx = current_posX;
        int next_posy = current_posY - 1;
        
        return isvalid(next_posx, next_posy);
        
    }
    
    private static boolean canRight(int current_posX, int current_posY) {
        
        int next_posx = current_posX;
        int next_posy = current_posY + 1;
        
        return isvalid(next_posx, next_posy);

    }
    
    private static boolean canUpMainDiag(int current_posX, int current_posY) {

        int next_posx = current_posX - 1;
        int next_posy = current_posY - 1;
        
        return isvalid(next_posx, next_posy);
        
    }
    
    private static boolean canDownMainDiag(int current_posX, int current_posY) {

        int next_posx = current_posX + 1;
        int next_posy = current_posY + 1;
        
        return isvalid(next_posx, next_posy);
        
    }
    
    private static boolean canUpSeconDiag(int current_posX, int current_posY) {

        int next_posx = current_posX - 1;
        int next_posy = current_posY + 1;
        
        return isvalid(next_posx, next_posy);

    }
    
    private static boolean canDownSeconDiag(int current_posX, int current_posY) {
        
        int next_posx = current_posX + 1;
        int next_posy = current_posY - 1;
        
        return isvalid(next_posx, next_posy);
        
    }

    private static boolean isvalid(int i, int j) {
        
        if (i >= 0 && i < Isolation.black_squares.length && j >= 0 && j < Isolation.black_squares.length) {
            
            if (Isolation.black_squares[i][j].equals("PC") || Isolation.black_squares[i][j].equals("PL1")) {
                
                return false;
                
            }
            
        }else{
            return false;
        }
        return true;
        
    }
    
}

