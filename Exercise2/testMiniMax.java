import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

//IMPORTANT MUST ADD AT MINIMAX.java line 175 the following line testMiniMax.allNodes.add(new_state);
public class testMiniMax {
    
    public static File f;

    public static FileWriter fw;

    public static ArrayList<NodeArch> allNodes =  new ArrayList<NodeArch>();

    public static void main(String[] args) throws IOException {
        
        f = new File("./Errors.txt");
        fw = new FileWriter("./Errors.txt");

        String[][] previous_moves = new String [4][4];
        for (int i = 0; i < previous_moves.length; i++) {
            
            for (int j = 0; j < previous_moves.length; j++) {
                
                previous_moves[i][j] = " ";
                
            }
        }
        previous_moves[0][2] = "PL1";
        previous_moves[0][1] = "PL1";
        previous_moves[1][1] = "PC";
        NodeArch pc = new NodeArch(1, 1, 0, 1, previous_moves, new ArrayList<NodeArch>(), null, 0);
        pc.setState_origin(pc);
        pc.setName("PC");
        MiniMax.driver(pc);
        sortArrayList();
        int current_lvl = 0;
        System.out.println("IMPORTANT MUST ADD AT MINIMAX.java line 175 the following line testMiniMax.allNodes.add(new_state);\n\n");
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