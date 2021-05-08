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

class NodeScore {
    private double value;
    private NodeArch representative_node;
    private int T_operation;

    public  NodeScore(double value, NodeArch representative_node, int T_operation) {
        
        this.value = value;
        this.representative_node = representative_node;
        this.T_operation = T_operation;

    }

    public double getValue() {
        return value;
    } 

    public NodeArch getRepresentative_node() {
        return representative_node;
    }

    public int getT_operation() {
        return T_operation;
    }

}


class Astar {

    public static int expansions = 0;
    public static int path_cost = 0;
    public static ArrayList<String> path;
    public static boolean path_found = false;
    public static int[] sorted_array;
    private static ArrayList<NodeScore> nodeScores = new ArrayList<NodeScore>();
    //private static ArrayList<NodeArch> lvl_nodes = new ArrayList<NodeArch>();
    private static int min_lvlNode_pos;

    public static void startSearch(NodeArch S_Node) {

        while (path_found == false) {
            sortArrayList();// to find min(g(n)+h(n))
            /*System.out.print("expansion: " + expansions + " sortedArrayList: ");
            for (NodeScore nodeScore : nodeScores) {
                System.out.print(nodeScore.getValue() + " ");
            }
            System.out.println();*/
            NodeScore next_score = nodeScores.get(0);//The object with the min(g(n) + h(n))
            nodeScores.remove(0);
            expand_and_check(next_score.getRepresentative_node(), next_score.getT_operation());
            
       }
        
        
    }

    private static void sortArrayList() {

        NodeScore[] ns = new NodeScore[nodeScores.size()];
        ns = nodeScores.toArray(ns);
        sortNSArray(ns);
        nodeScores.clear();
        for (int i = 0; i < ns.length; i++) {

            nodeScores.add(ns[i]);

        }
        
    }
    
    private static void sortNSArray(NodeScore[] ns) {
        
        for (int i = 1; i < ns.length; i++) {
            
            NodeScore compare_element = ns[i];
            int j = i - 1;
    
            while (j >=0) {
                
                if(ns[j].getValue() > compare_element.getValue()){
    
                    ns[j+1] = ns[j];
                    j = j - 1;
    
                }else{
    
                    break;
    
                }
            }
            ns[j+1] = compare_element;
    
        }

    }
    
    private static void expand_and_check(NodeArch n, int T_operation){
        
        Astar.expansions ++;
        int[] new_state = swapElem(T_operation-1, n.getCurrent_state());
        NodeArch descendant_node = new NodeArch(T_operation, n.getgN() + 1, new_state, n);
        n.getdescendants().add(descendant_node);
        check_if_final_state(descendant_node);
        if (path_found == false) {

            calculateHn(descendant_node);

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

    public static void calculateHn(NodeArch n) {
        
        int[] current_state = n.getCurrent_state();
        int[] next_state_distances = new int[current_state.length];
        for (int i = 2; i <= current_state.length; i++) {
            if (i == n.getExpansion_operator()) {
                
                continue;
                
            } else {
                
                calculateDistances(current_state, next_state_distances, i);
                int sum = 0;
                for (int j2 = 0; j2 < next_state_distances.length; j2++) {
                    
                    sum += next_state_distances[j2];
                    
                }
                double Hn = sum/(double)next_state_distances.length;
                //System.out.println("h(n)= " + Hn + "\n");
                NodeScore score = new NodeScore(Hn + n.getgN() + 1, n, i);
                nodeScores.add(score);
                
            }
        }
        
    }
    
    private static void calculateDistances(int[] current_state, int[] next_state_distances, int i) {
        
        int x = i;
        int j = 0;
        while (x > 0) {
            //System.out.print(current_state[j] + " - " + x);
            next_state_distances[j] = Math.abs(current_state[j] - x);
            //System.out.println(" = " + next_state_distances[j]);
            j++;
            x--;
    
        }
        if (i < current_state.length) {
            //System.out.println("----remaining elements----");
            for (int j2 = j; j2 < current_state.length; j2++) {
                //System.out.print(current_state[j2] + " - " + sorted_array[j2]);
                next_state_distances[j2] = Math.abs(current_state[j2] - sorted_array[j2]);
                //System.out.println(" = " + next_state_distances[j2]);
    
            }
            //System.out.println();
    
        }

    }

    
}

public class askisi1_Astar {

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
        Astar.calculateHn(s_node);
        Astar.startSearch(s_node);
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
        
        Astar.sorted_array = new int[size];
        for (int i = 0; i < size; i++) {
            Astar.sorted_array[i] = i + 1;
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

        for (int i = Astar.path.size() - 1; i >= 0; i = i -1 ) {
            
            System.out.println(Astar.path.get(i));
            
        }
        System.out.println("Path's cost: " + Astar.path_cost);
        System.out.println("Expansions: " + Astar.expansions);
        
    }
}
