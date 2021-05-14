import java.util.ArrayList;

public class t {
    
    public static void main(String[] args) {
        
        String[][] t1 = new String[3][3];
        String[][] t2 = new String[3][3];

        for (int i = 0; i < t1.length; i++) {
            for (int j = 0; j < t1.length; j++) {
                t1[i][j] = "_"; 
            }
        }


        t1[0][0] = "X";
        t1[2][2] = "X";



        NodeArch n = new NodeArch(0, 0, 2, 2, t1, new ArrayList<NodeArch>(), null, 0);
        n.setName("PC");
        Moves.maker(n);

        
        for (NodeArch next_state : n.getNext_states()) {
            
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    System.out.println(next_state.getPrevious_moves()[i][j]);
                }
            }
            System.out.println("\n\n");
        }


    }
}


class NodeArch {
    
    private String name;
    private int coord_i;
    private int coord_j;
    private int rival_coord_i;
    private int rival_coord_j;
    private ArrayList<NodeArch> next_states; // contains descendant nodes.
    private NodeArch state_origin; // parent node.
    private String[][] previous_moves;
    private int lvl;//Level: distance from parent node.
    private int depth;//Depth: distance from final state node.
    private int score;
    private NodeArch next_move;
    
    public NodeArch(int coord_i, int coord_j, int rival_coord_i, int rival_coord_j,String[][] previous_moves, ArrayList<NodeArch> next_states, NodeArch state_origin, int lvl){

        this.coord_i = coord_i;
        this.coord_j = coord_j;
        this.rival_coord_i = rival_coord_i;
        this.rival_coord_j = rival_coord_j;
        this.previous_moves = previous_moves;
        this.next_states = next_states;
        this.state_origin = state_origin;
        this.lvl = lvl;

    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getDepth() {
        return depth;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public int getCoord_i() {
        return coord_i;
    }

    public int getCoord_j() {
        return coord_j;
    }

    public String[][] getPrevious_moves() {
        return previous_moves;
    }

    public ArrayList<NodeArch> getNext_states() {
        return next_states;
    }

    public NodeArch getState_origin() {
        return state_origin;
    }

    public int getLVL() {
        return lvl;
    }

    public int getRival_coord_i() {
        return rival_coord_i;
    }

    public int getRival_coord_j() {
        return rival_coord_j;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setNext_move(NodeArch next_move) {
        this.next_move = next_move;
    }

    public NodeArch getNext_move() {
        return next_move;
    }

}


class Moves{

    private static NodeArch new_state;
    private static int next_i;
    private static int next_j;
    
    public static boolean maker(NodeArch s) {
        
        System.out.println("---------- " + s.getName() + " ----------\n");
        System.out.println("---------- " + s.getCoord_i() + "," + s.getCoord_j() + " ----------");
        System.out.println("---------- previous moves ----------\n");
        //String[][] pm = s.getPrevious_moves();
        int unavailable_moves = 0;
        
        if (up(s) == false){
            
            unavailable_moves += 1;
            
        }
        if(down(s) == false){
            
            unavailable_moves += 1;
            
        }
        if(left(s) == false){
            
            unavailable_moves += 1;
            
        }
        if(right(s) == false){
            
            unavailable_moves += 1;
            
        }
        if(upMainDiag(s) == false){
            
            unavailable_moves += 1;
            
        }
        if (downMainDiag(s) == false) {
            
            unavailable_moves += 1;
            
        }
        if (upSeconDiag(s) == false) {
            
            unavailable_moves += 1;
            
        }
        if (downSeconDiag(s) == false) {
            
            unavailable_moves += 1;
            
        }
        if (unavailable_moves == 8) {
            
            System.out.println("\n\n---------- Finish " + s.getName() + " ----------\n\n");
            return false;
            
        }
        System.out.println("\n\n---------- Finish " + s.getName() + " ----------\n\n");
        return true;

    }

    private static void update_previousMoves(String[][] outdated_matrix, String[][] updated_matrix, int pos_row, int pos_column) {

        for (int i = 0; i < updated_matrix.length; i++) {
            for (int k = 0; k < updated_matrix.length; k++) {
                
                updated_matrix[i][k] = outdated_matrix[i][k];
                
            }
        }
        updated_matrix[pos_row][pos_column] = "X";


    }

    private static void expandtoNextState(NodeArch s, String[][] updated_pm,int newPos_row, int newPos_column){
        
        update_previousMoves(s.getPrevious_moves(), updated_pm,newPos_row, newPos_column);
        new_state = new NodeArch(newPos_row, newPos_column, s.getRival_coord_i(), s.getRival_coord_j(), updated_pm, new ArrayList<NodeArch>(), s.getState_origin(), s.getDepth() + 1);
        new_state.setName(s.getName());
        s.getNext_states().add(new_state);

    }

    private static boolean up(NodeArch s) {
        //code
        next_i = s.getCoord_i() - 1;
        next_j = s.getCoord_j();
        String[][] pm = s.getPrevious_moves();
        if (next_i < 0 || pm[next_i][next_j].equals("X")) {

            System.out.println("\n\n Invalid coord: (" + next_i + "," + next_j + ")");
            return false;

        }
        System.out.println("\n\n Valid coord: (" + next_i + "," + next_j + ")");
        expandtoNextState(s, new String[3][3],next_i, next_j);
        return true;
    }

    private static boolean down(NodeArch s) {
        //code
        next_i = s.getCoord_i() + 1;
        next_j = s.getCoord_j();
        String[][] pm = s.getPrevious_moves();
        
        if (next_i > 2 || pm[next_i][next_j].equals("X")) {

            System.out.println("pm = " + pm[next_i][next_j]);
            System.out.println("\n\n Invalid coord: (" + next_i + "," + next_j + ")");
            return false;

        }
        System.out.println("\n\n Valid coord: (" + next_i + "," + next_j + ")");
        expandtoNextState(s, new String[3][3],next_i, next_j);;
        return true;
    }

    private static boolean left(NodeArch s) {
        //code
        next_i = s.getCoord_i();
        next_j = s.getCoord_j() - 1;
        String[][] pm = s.getPrevious_moves();
        if (next_j < 0 || pm[next_i][next_j].equals("X")){
            System.out.println("\n\n Invalid coord: (" + next_i + "," + next_j + ")");
            return false;
        }
        System.out.println("\n\n Valid coord: (" + next_i + "," + next_j + ")");
        expandtoNextState(s, new String[3][3],next_i, next_j);
        return true;
    }

    private static boolean right(NodeArch s) {
        //code
        next_i = s.getCoord_i();
        next_j = s.getCoord_j() + 1;
        String[][] pm = s.getPrevious_moves();
        if (next_j > 2 || pm[next_i][next_j].equals("X")) {
            
            System.out.println("\n\n Invalid coord: (" + next_i + "," + next_j + ")");
            return false;
            
        }
        System.out.println("\n\n Valid coord: (" + next_i + "," + next_j + ")");
        expandtoNextState(s, new String[3][3],next_i, next_j);
        return true;
    }

    private static boolean upMainDiag(NodeArch s) {
        //code
        next_i = s.getCoord_i() - 1;
        next_j = s.getCoord_j() - 1;
        String[][] pm = s.getPrevious_moves();
        if (next_i < 0 || next_j < 0 || pm[next_i][next_j].equals("X")) {

            System.out.println("\n\n Invalid coord: (" + next_i + "," + next_j + ")");
            return false;
        }
        System.out.println("\n\n Valid coord: (" + next_i + "," + next_j + ")");
        expandtoNextState(s, new String[3][3],next_i, next_j);
        return true;
        
    }

    private static boolean downMainDiag(NodeArch s) {
        //code
        next_i = s.getCoord_i() + 1;
        next_j = s.getCoord_j() + 1;
        String[][] pm = s.getPrevious_moves();
        if (next_i > 2 || next_j > 2 || pm[next_i][next_j].equals("X")) {

            System.out.println("\n\n Invalid coord: (" + next_i + "," + next_j + ")");
            return false;
        }
        System.out.println("\n\n Valid coord: (" + next_i + "," + next_j + ")");
        expandtoNextState(s, new String[3][3],next_i, next_j);
        return true;

    }

    private static boolean upSeconDiag(NodeArch s) {
        //code
        next_i = s.getCoord_i() - 1;
        next_j = s.getCoord_j() + 1;
        String[][] pm = s.getPrevious_moves();
        if (next_i < 0 || next_j > 2 || pm[next_i][next_j].equals("X")) {

            System.out.println("\n\n Invalid coord: (" + next_i + "," + next_j + ")");
            return false;
        }
        System.out.println("\n\n Valid coord: (" + next_i + "," + next_j + ")");
        expandtoNextState(s, new String[3][3],next_i, next_j);
        return true;

    }

    private static boolean downSeconDiag(NodeArch s) {
        
        next_i = s.getCoord_i() + 1;
        next_j = s.getCoord_j() - 1;
        String[][] pm = s.getPrevious_moves();
        if (next_i > 2 || next_j < 0 || pm[next_i][next_j].equals("X")) {

            System.out.println("\n\n Invalid coord: (" + next_i + "," + next_j + ")");
            return false;
        }
        System.out.println("\n\n Valid coord: (" + next_i + "," + next_j + ")");
        expandtoNextState(s, new String[3][3],next_i, next_j);
        return true;

    }

}
