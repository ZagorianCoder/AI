import java.util.ArrayList;



class Moves{

    

    private static int next_i;
    private static int next_j;
    
    public static boolean maker(NodeArch s)  {
        
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
            
            return false;
            
        }

        return true;
        
    }
    
    
    private static boolean up(NodeArch s) {
        
        next_i = s.getCoord_i() - 1;
        next_j = s.getCoord_j();
        
        return canExpand(s, next_i, next_j, s.getPrevious_moves());
        
    }
    
    private static boolean down(NodeArch s) {

        next_i = s.getCoord_i() + 1;
        next_j = s.getCoord_j();
        
        return canExpand(s, next_i, next_j, s.getPrevious_moves());
        
    }
    
    private static boolean left(NodeArch s) {
        
        next_i = s.getCoord_i();
        next_j = s.getCoord_j() - 1;
        
        return canExpand(s, next_i, next_j, s.getPrevious_moves());
        
    }
    
    private static boolean right(NodeArch s) {
        
        next_i = s.getCoord_i();
        next_j = s.getCoord_j() + 1;
        
        return canExpand(s, next_i, next_j, s.getPrevious_moves());
    }
    
    private static boolean upMainDiag(NodeArch s) {
        
        next_i = s.getCoord_i() - 1;
        next_j = s.getCoord_j() - 1;
        
        return canExpand(s, next_i, next_j, s.getPrevious_moves());
        
    }
    
    private static boolean downMainDiag(NodeArch s) {
        
        next_i = s.getCoord_i() + 1;
        next_j = s.getCoord_j() + 1;
        
        return canExpand(s, next_i, next_j, s.getPrevious_moves());
        
    }
    
    private static boolean upSeconDiag(NodeArch s) {
        
        next_i = s.getCoord_i() - 1;
        next_j = s.getCoord_j() + 1;
        
        return canExpand(s, next_i, next_j, s.getPrevious_moves());
        
    }
    
    private static boolean downSeconDiag(NodeArch s) {
        
        next_i = s.getCoord_i() + 1;
        next_j = s.getCoord_j() - 1;
        
        return canExpand(s, next_i, next_j, s.getPrevious_moves());
        
        
    }
    
    private static boolean canExpand(NodeArch s, int i, int j, String[][] moves) {
        
        if (isvalid(i, j, moves)){
            
            expandtoNextState(s, new String[4][4],next_i, next_j);
            return true;
            
        }
        return false;
        
    }
    
    private static boolean isvalid(int i, int j, String[][] moves) {
        
        if (i >= 0 && i < moves.length && j >= 0 && j < moves.length) {
            
            if (moves[i][j].equals("PC") || moves[i][j].equals("PL1")) {
                
                return false;
                
            }
            
        }else{
            return false;
        }
        return true;
        
    }
    
    private static void expandtoNextState(NodeArch s, String[][] updated_pm,int newPos_row, int newPos_column){
        
        update_previousMoves(s, s.getPrevious_moves(), updated_pm,  newPos_row, newPos_column);
        
        NodeArch new_state = new NodeArch(newPos_row, newPos_column, s.getRival_coord_i(), s.getRival_coord_j(), updated_pm, new ArrayList<NodeArch>(), s.getState_origin(), s.getLVL() + 1);
        new_state.setName(s.getName());
        s.getNext_states().add(new_state);
        testMiniMax.allNodes.add(new_state);
        
    }
    
    private static void update_previousMoves(NodeArch s, String[][] outdated_matrix, String[][] updated_matrix, int pos_row, int pos_column) {
        
        for (int i = 0; i < updated_matrix.length; i++) {
    
            for (int k = 0; k < updated_matrix.length; k++) {
                
                updated_matrix[i][k] = outdated_matrix[i][k];
                
            }
        }
        updated_matrix[pos_row][pos_column] = s.getName();
        
        
    }

}    

public class MiniMax{
    
    private static ArrayList<NodeArch> finalStates  = new ArrayList<NodeArch>();
    
    public static void driver(NodeArch s) {

        make_PCmoves(s);
        
        
    }
    
    private static void make_PCmoves(NodeArch s) {
        
        if (Moves.maker(s)) {

            for (int i = 0; i < s.getNext_states().size(); i++) {
                
                NodeArch nextState = s.getNext_states().get(i);
                NodeArch rival_node = new NodeArch(nextState.getRival_coord_i(), nextState.getRival_coord_j(), nextState.getCoord_i(), nextState.getCoord_j(), nextState.getPrevious_moves(), nextState.getNext_states(), nextState, nextState.getLVL());
                rival_node.setName("PL1");
                
                make_Playermoves(rival_node);
                
            }

            evaluate(s.getNext_states());
            
        }else{
            
            finalStates.add(s.getState_origin());
            return;
            
        }
        
    }
    
    private static void make_Playermoves(NodeArch s) {

        if (Moves.maker(s)) {
            
            for (int i = 0; i < s.getNext_states().size(); i++) {
                
                NodeArch nextState = s.getNext_states().get(i);

                NodeArch rival_node = new NodeArch(nextState.getRival_coord_i(), nextState.getRival_coord_j(), nextState.getCoord_i(), nextState.getCoord_j(), nextState.getPrevious_moves(), nextState.getNext_states(), nextState, nextState.getLVL());
                rival_node.setName("PC");

                make_PCmoves(rival_node);
                
            }
            evaluate(s.getNext_states());
            
        }else{
            
            finalStates.add(s.getState_origin());
            return;
            
        }
        
        
    }
    
    private static void evaluate(ArrayList<NodeArch> states) {
        
        checkForNonEvaluatedStates(states);

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