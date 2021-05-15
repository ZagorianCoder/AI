import java.util.ArrayList;


public class NodeArch {
    
    private String name;
    private int coord_i;
    private int coord_j;
    private int rival_coord_i;
    private int rival_coord_j;
    private ArrayList<NodeArch> next_states; // contains descendant nodes.
    private NodeArch state_origin; // parent node.
    private String[][] previous_moves;
    private int lvl;//Level: distance from parent node.
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