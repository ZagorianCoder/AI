import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;



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

    public void setState_origin(NodeArch state_origin) {
        this.state_origin = state_origin;
    }

}

class Moves{

    

    private static int next_i;
    private static int next_j;
    
    public static boolean maker(NodeArch s) throws IOException {
        
        /*askhsh2.fw.write("\n\n\n---------- " + s.getName() + " ----------\n\n\n");
        askhsh2.fw.write("---------- " + s.getCoord_i() + "," + s.getCoord_j() + " ----------\n\n\n");
        askhsh2.fw.write("---------- rival coord ----------\n\n\n");
        askhsh2.fw.write("---------- " + s.getRival_coord_i() + "," + s.getRival_coord_j() + " ----------\n\n\n");*/
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
            
            //System.out.println("\n\n---------- Finish " + s.getName() + " ----------\n\n");
            return false;
            
        }
        //askhsh2.fw.write("---------- Finish " + s.getName() + " ----------\n\n\n");
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
        //System.out.println("newPos_row: " + newPos_row);
        //System.out.println("newPos_column: " + newPos_column);

        NodeArch new_state = new NodeArch(newPos_row, newPos_column, s.getRival_coord_i(), s.getRival_coord_j(), updated_pm, new ArrayList<NodeArch>(), s.getState_origin(), s.getLVL() + 1);
        //System.out.println("new_state: " + new_state.getCoord_i() + "," + new_state.getCoord_j());
        new_state.setName(s.getName());
        s.getNext_states().add(new_state);
        askhsh2.allNodes.add(new_state);
        //System.out.println("first child of s node: " + s.getNext_states().get(0).getCoord_i() + "," + s.getNext_states().get(0).getCoord_j());
        
    }
    
    private static boolean up(NodeArch s) {
        //code
        next_i = s.getCoord_i() - 1;
        next_j = s.getCoord_j();
        String[][] pm = s.getPrevious_moves();
        if (next_i < 0 || pm[next_i][next_j].equals("X")) {
            
            //System.out.println("\n\n Invalid coord: (" + next_i + "," + next_j + ")");
            return false;
            
        }
        //System.out.println("\n\n Valid coord: (" + next_i + "," + next_j + ")");
        expandtoNextState(s, new String[3][3],next_i, next_j);
        return true;
    }
    
    private static boolean down(NodeArch s) {
        //code
        next_i = s.getCoord_i() + 1;
        next_j = s.getCoord_j();
        String[][] pm = s.getPrevious_moves();
        
        if (next_i > 2 || pm[next_i][next_j].equals("X")) {
            
            //System.out.println("pm = " + pm[next_i][next_j]);
            //System.out.println("\n\n Invalid coord: (" + next_i + "," + next_j + ")");
            return false;
            
        }
        //System.out.println("\n\n Valid coord: (" + next_i + "," + next_j + ")");
        expandtoNextState(s, new String[3][3],next_i, next_j);;
        return true;
    }
    
    private static boolean left(NodeArch s) {
        //code
        next_i = s.getCoord_i();
        next_j = s.getCoord_j() - 1;
        //System.out.println("Left Move: " + next_i + "," + next_j);
        String[][] pm = s.getPrevious_moves();
        if (next_j < 0 || pm[next_i][next_j].equals("X")){
            //System.out.println("\n\n Invalid coord: (" + next_i + "," + next_j + ")");
            return false;
        }
        //System.out.println("\n\n Valid coord: (" + next_i + "," + next_j + ")");
        expandtoNextState(s, new String[3][3],next_i, next_j);
        return true;
    }
    
    private static boolean right(NodeArch s) {
        //code
        next_i = s.getCoord_i();
        next_j = s.getCoord_j() + 1;
        String[][] pm = s.getPrevious_moves();
        if (next_j > 2 || pm[next_i][next_j].equals("X")) {
            
            //System.out.println("\n\n Invalid coord: (" + next_i + "," + next_j + ")");
            return false;
            
        }
        //System.out.println("\n\n Valid coord: (" + next_i + "," + next_j + ")");
        expandtoNextState(s, new String[3][3],next_i, next_j);
        return true;
    }
    
    private static boolean upMainDiag(NodeArch s) {
        //code
        next_i = s.getCoord_i() - 1;
        next_j = s.getCoord_j() - 1;
        String[][] pm = s.getPrevious_moves();
        if (next_i < 0 || next_j < 0 || pm[next_i][next_j].equals("X")) {

            //System.out.println("\n\n Invalid coord: (" + next_i + "," + next_j + ")");
            return false;
        }
        //System.out.println("\n\n Valid coord: (" + next_i + "," + next_j + ")");
        expandtoNextState(s, new String[3][3],next_i, next_j);
        return true;
        
    }
    
    private static boolean downMainDiag(NodeArch s) {
        //code
        next_i = s.getCoord_i() + 1;
        next_j = s.getCoord_j() + 1;
        String[][] pm = s.getPrevious_moves();
        if (next_i > 2 || next_j > 2 || pm[next_i][next_j].equals("X")) {
            
            //System.out.println("\n\n Invalid coord: (" + next_i + "," + next_j + ")");
            return false;
        }
        //System.out.println("\n\n Valid coord: (" + next_i + "," + next_j + ")");
        expandtoNextState(s, new String[3][3],next_i, next_j);
        return true;
        
    }
    
    private static boolean upSeconDiag(NodeArch s) {
        //code
        next_i = s.getCoord_i() - 1;
        next_j = s.getCoord_j() + 1;
        String[][] pm = s.getPrevious_moves();
        if (next_i < 0 || next_j > 2 || pm[next_i][next_j].equals("X")) {
            
            //System.out.println("\n\n Invalid coord: (" + next_i + "," + next_j + ")");
            return false;
        }
        //System.out.println("\n\n Valid coord: (" + next_i + "," + next_j + ")");
        expandtoNextState(s, new String[3][3],next_i, next_j);
        return true;

    }
    
    private static boolean downSeconDiag(NodeArch s) {
        
        next_i = s.getCoord_i() + 1;
        next_j = s.getCoord_j() - 1;
        String[][] pm = s.getPrevious_moves();
        if (next_i > 2 || next_j < 0 || pm[next_i][next_j].equals("X")) {
            
            //System.out.println("\n\n Invalid coord: (" + next_i + "," + next_j + ")");
            return false;
        }
        //System.out.println("\n\n Valid coord: (" + next_i + "," + next_j + ")");
        expandtoNextState(s, new String[3][3],next_i, next_j);
        return true;
        
    }
    
}

class MiniMax{
    
    private static ArrayList<NodeArch> finalStates  = new ArrayList<NodeArch>();
    
    public static void driver(NodeArch s) throws IOException{
        //code
        make_PCmoves(s);
        
        
    }
    
    private static void make_PCmoves(NodeArch s) throws IOException{
        
        if (Moves.maker(s)) {
            //System.out.println("Father's name: " + s.getNext_states().get(0).getState_origin().getCoord_i());
            //System.exit(0);
            for (int i = 0; i < s.getNext_states().size(); i++) {
                
                NodeArch nextState = s.getNext_states().get(i);
                NodeArch rival_node = new NodeArch(nextState.getRival_coord_i(), nextState.getRival_coord_j(), nextState.getCoord_i(), nextState.getCoord_j(), nextState.getPrevious_moves(), nextState.getNext_states(), nextState, nextState.getLVL());
                rival_node.setName("Player");
                
                /*for (int k = 0; k < nextState.getPrevious_moves().length; k++) {
                    
                    for (int k2 = 0; k2 < nextState.getPrevious_moves().length; k2++) {
                        
                        System.out.println(k + "," + k2 + ": " + nextState.getPrevious_moves()[k][k2]);
                        
                    }
                }*/
                
                make_Playermoves(rival_node);
                
            }
            
            /*System.out.println("\n\n------------ s - state ------------\n\n");
            for (int k = 0; k < s.getPrevious_moves().length; k++) {
                
                for (int k2 = 0; k2 < s.getPrevious_moves().length; k2++) {
                    
                    System.out.println(k + "," + k2 + ": " + s.getPrevious_moves()[k][k2]);
                    
                }
            }
            
            System.exit(0);*/
            evaluate(s.getNext_states());
            
        }else{
            
            finalStates.add(s.getState_origin());
            return;
            
        }
        
    }
    
    private static void make_Playermoves(NodeArch s) throws IOException{
        //code
        if (Moves.maker(s)) {
            
            //System.out.println("Player node coord: " + s.getCoord_i() +  "," + s.getCoord_j() + " rival node coord: " + s.getRival_coord_i() + "," + s.getRival_coord_j());
            for (int i = 0; i < s.getNext_states().size(); i++) {
                
                NodeArch nextState = s.getNext_states().get(i);
                //System.out.println("nextState node coord: " + nextState.getCoord_i() +  "," + nextState.getCoord_j() + " rival node coord: " + nextState.getRival_coord_i() + "," + nextState.getRival_coord_j());

                NodeArch rival_node = new NodeArch(nextState.getRival_coord_i(), nextState.getRival_coord_j(), nextState.getCoord_i(), nextState.getCoord_j(), nextState.getPrevious_moves(), nextState.getNext_states(), nextState, nextState.getLVL());
                rival_node.setName("PC");

                //askhsh2.fw.write("NextState coord: " + rival_node.getCoord_i() +  "," + rival_node.getCoord_j() + " rival node coord: " + rival_node.getRival_coord_i() + "," + rival_node.getRival_coord_j());
                //System.out.println("The Rival's coord: " + rival_node.getCoord_i() +  "," + rival_node.getCoord_j() + " rival node coord: " + rival_node.getRival_coord_i() + "," + rival_node.getRival_coord_j());
                //askhsh2.fw.close();
                //System.exit(0);
                make_PCmoves(rival_node);
                
            }
            evaluate(s.getNext_states());
            
        }else{
            
            finalStates.add(s.getState_origin());
            return;
            
        }
        
        
    }
    
    private static void evaluate(ArrayList<NodeArch> states) throws IOException{
        
        checkForNonEvaluatedStates(states);
        //System.out.println("Arrived_here!!");
        /*System.out.println("first child: " + states.get(0).getCoord_i() + "," + states.get(0).getCoord_j());
        System.out.println("1st child's name: " + states.get(0).getName());
        System.out.println("1st childs lvl:" + states.get(0).getLVL());*/
        String origin_state_name = states.get(0).getName();
        if (origin_state_name.equals("PC")) {
            
            findMaxScore(states, states.get(0).getState_origin());

        }else{
            
            findMinScore(states, states.get(0).getState_origin());

        }
        
        
    }
    
    private static void checkForNonEvaluatedStates(ArrayList<NodeArch> states){
        
        for (NodeArch state : states) {
            
            if (finalStates.contains(state)) {
                
                if (state.getName().equals("PC")) {
                    
                    state.setScore(1);
                    
                }else{
                    //System.out.println("Player wins");
                    state.setScore(-1);
                    
                }
                
            }
            
        }
        
    }
    
    private static void findMinScore(ArrayList<NodeArch> states, NodeArch origin_state){
        
        int min_score = states.get(0).getScore();
        NodeArch min_state = states.get(0);
        
        for (NodeArch state : states) {
            
            if (state.getScore() < min_score) {
                
                min_score = state.getScore();
                min_state = state;
                
            }
            
        }
        
        origin_state.setScore(min_score);
        origin_state.setNext_move(min_state);
        
    }
    
    private static void findMaxScore(ArrayList<NodeArch> states, NodeArch origin_state){
        
        int max_score = states.get(0).getScore();
        NodeArch max_state = states.get(0);
        
        for (NodeArch state : states) {
            
            if (state.getScore() > max_score) {
                
                max_score = state.getScore();
                max_state = state;
                
            }
            
        }
        
        origin_state.setScore(max_score);
        origin_state.setNext_move(max_state);
        
    }
    
}

public class testMiniMax {
    
    public static File f;

    public static FileWriter fw;

    public static ArrayList<NodeArch> allNodes =  new ArrayList<NodeArch>();

    public static void main(String[] args) throws IOException {
        
        f = new File("./Errors.txt");
        fw = new FileWriter("./Errors.txt");

        String[][] previous_moves = new String [3][3];
        for (int i = 0; i < previous_moves.length; i++) {
            
            for (int j = 0; j < previous_moves.length; j++) {
                
                previous_moves[i][j] = " ";
                
            }
        }
        previous_moves[0][2] = "X";
        previous_moves[0][1] = "X";
        previous_moves[1][1] = "X";
        NodeArch pc = new NodeArch(1, 1, 0, 1, previous_moves, new ArrayList<NodeArch>(), null, 0);
        pc.setState_origin(pc);
        pc.setName("PC");
        MiniMax.driver(pc);
        sortArrayList();
        int current_lvl = 0;
        System.out.println("______root________");
        System.out.println(pc.getScore());
        for (NodeArch fn : allNodes) {
            
            if (current_lvl != fn.getLVL()) {
                current_lvl = fn.getLVL();
                System.out.println("\n\n");
                System.out.println("______LVL " + fn.getLVL() + "________");
                System.out.print(fn.getName() + "    ");
            }
            System.out.print(fn.getScore() + "    ");

        }


    }

    private static void sortArrayList() {

        NodeArch[] na = new NodeArch[allNodes.size()];
        na = allNodes.toArray(na);
        sortNSArray(na);
        allNodes.clear();
        for (int i = 0; i < na.length; i++) {

            allNodes.add(na[i]);

        }
        
    }
    
    private static void sortNSArray(NodeArch[] na) {
        
        for (int i = 1; i < na.length; i++) {
            
            NodeArch compare_element = na[i];
            int j = i - 1;
    
            while (j >=0) {
                
                if(na[j].getLVL() > compare_element.getLVL()){
    
                    na[j+1] = na[j];
                    j = j - 1;
    
                }else{
    
                    break;
    
                }
            }
            na[j+1] = compare_element;
    
        }

    }
}