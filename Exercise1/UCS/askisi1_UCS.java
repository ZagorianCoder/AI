import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

class NodeArch {

    private int expansion_operator;
    private int gN;
    private int[] current_state;
    private NodeArch parent;
    private ArrayList<NodeArch> descendants = new ArrayList<NodeArch>();

    public NodeArch(int expansion_operator, int gN, int[] current_state, NodeArch parent){
        this.expansion_operator = expansion_operator;
        this.current_state = current_state;
        this.gN = gN;
        this.parent = parent;
    }

    public int[] getCurrent_state() {
        return current_state;
    }

    public int getgN() {
        return gN;
    }

    public NodeArch getParent() {
        return parent;
    }
    
    public int getExpansion_operator() {
        return expansion_operator;
    }
    
    public ArrayList<NodeArch> getdescendants() {
        return descendants;
    }

    public String toString_CurrentState(){

        String cState = "[";
        for (int i = 0; i < current_state.length - 1; i++) {
            
            cState += current_state[i] + " ";
            
        }
        return cState + current_state[current_state.length - 1] + "]";

    }

}


class UCS {

    public static int expansions = 0;
    public static int path_cost = 0;
    public static ArrayList<String> path;
    public static boolean path_found = false;
    public static int[] sorted_array;
    private static ArrayList<NodeArch> lvl_nodes = new ArrayList<NodeArch>();
    private static int min_lvlNode_pos;

    public static void startSearch(NodeArch S_Node) {

        expand_and_check(S_Node);
        while (path_found == false) {
            
            NodeArch next_node = find_minCost_lvlNode();
            lvl_nodes.remove(min_lvlNode_pos);
            expand_and_check(next_node);

        }

        
        
    }

    private static NodeArch find_minCost_lvlNode() {
        
        NodeArch min_node = lvl_nodes.get(0);
        min_lvlNode_pos = 0;

        for (int i = 1; i < lvl_nodes.size(); i++) {
            
            if (lvl_nodes.get(i).getgN() < min_node.getgN()) {
                
                min_node = lvl_nodes.get(i);
                min_lvlNode_pos = i;

            }

        }

        return min_node;

    }
    
    private static void expand_and_check(NodeArch n){
        
        for (int i = 2; i <= n.getCurrent_state().length; i++) {
            if (i != n.getExpansion_operator()) {
                UCS.expansions ++;
                int[] new_state = swapElem(i-1, n.getCurrent_state());
                NodeArch descendant_node = new NodeArch(i, n.getgN() + 1, new_state, n);
                
                n.getdescendants().add(descendant_node);
                lvl_nodes.add(descendant_node);
                check_if_final_state(descendant_node);
                if (path_found == true) {
                    break;
                }
                
            }
        }
        
    }

    private static int[] swapElem(int i, int[] array){
        int[] new_array = new int[array.length];
        copyArray(array, new_array);

        for (int j = 0; j < i; j++) {
            
            if (j >= i-j) {
                
                break;
                
            }
            
            int temp = new_array[j];
            new_array[j] = new_array[i - j];
            new_array[i-j] = temp;

        }
        return new_array;

    }

    private static void copyArray(int[] original_array, int[] copy_array) {

        for (int i = 0; i < original_array.length; i++) {
            
            copy_array[i] = original_array[i];

        }
        

    }
    
    private static void check_if_final_state(NodeArch n) {
        
        if(isSorted(n.getCurrent_state())){
    
            path_found = true;
            path_cost = n.getgN();
            constructPath(n);
            
        }

    }

    private static boolean isSorted(int[] array) {

        return Arrays.equals(array, sorted_array);

    }

    private static void constructPath(NodeArch path_node) {
        
        path = new ArrayList<String>();
        do {
            
            path.add("T" + path_node.getExpansion_operator() + ": " + path_node.toString_CurrentState());
            path_node = path_node.getParent();

        } while (path_node.getParent() != null);

    }

    
}

public class askisi1_UCS {

    private static int size;
    private static HashMap<Integer, Integer> numbersMap;
    private static int[] initState;
    

    public static void main(String[] args) {
        
        Scanner input = new Scanner(System.in);
        
        getSize(input);
        
        construct_sortedArray();
        constructMap();

        initState = new int[size];
        validate(input);
        
        NodeArch s_node = new NodeArch(0, 0, initState, null);
        UCS.startSearch(s_node);
        printResults(s_node);
        
    }
    
    private static void getSize(Scanner input){

        do {
            
            System.out.print("Type a number greater than 1 to define your array length: ");
            size = input.nextInt();
            System.out.println();
            
        } while (size <= 1);
        
    }
    
    private static void construct_sortedArray(){
        
        UCS.sorted_array = new int[size];
        for (int i = 0; i < size; i++) {
            UCS.sorted_array[i] = i + 1;
        }
        
    }
    
    private static void constructMap(){
        
        numbersMap = new HashMap<Integer, Integer>();
        for (int i = 1; i <= size; i++) {
            numbersMap.put(i, 0);
        }

    }
    
    private static void validate(Scanner input){
        
        System.out.println("Initial State creation.");
        boolean valid_initState;
        do {

            valid_initState = true;
            for (int i = 0; i < initState.length; i++) {
                
                number_validation(input, i);
                
            }
            
        } while (valid_initState == false);
        
    }

    private static void number_validation(Scanner input, int i) {
        
        boolean validElement;
        do {
            
            validElement = true;
            do {
    
                System.out.print("Type a number > = 1 and less than the array's length!\nYour number:");
                initState[i] = input.nextInt();
                System.out.println();
    
            } while (initState[i] > initState.length || initState[i] < 1);
    
            numbersMap.put(initState[i], numbersMap.get(initState[i]) + 1);
            if (numbersMap.get(initState[i]) > 1) {
                
                numbersMap.put(initState[i], 1);
                System.err.println("You can't add the same number twice into the array");
                validElement = false;
    
            }
            
        } while (validElement == false);
        
    }

    private static void printResults(NodeArch node) {

        System.out.println("The Path to Final State is:");
        System.out.println("AK: " + node.toString_CurrentState());

        for (int i = UCS.path.size() - 1; i >= 0; i = i -1 ) {
            
            System.out.println(UCS.path.get(i));
            
        }
        System.out.println("Path's cost: " + UCS.path_cost);
        System.out.println("Expansions: " + UCS.expansions);
        
    }
}
