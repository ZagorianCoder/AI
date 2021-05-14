import java.awt.*;

import javax.swing.*;

//import jdk.internal.platform.Container;

import java.awt.event.*;

/*class GBoard extends JPanel{
     
    public void paintComponent(Graphics g){

        super.paintComponent(g);
        this.setBackground(Color.GRAY);

        Graphics2D g2D = (Graphics2D) g;

        g2D.setColor(Color.BLUE);
        g2D.drawLine(0, 0, 100, 50);

    }
}

class MyFrame extends JFrame{

    GBoard gb = new GBoard();

    public MyFrame() {

        this.setSize(420, 420);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(gb);
        this.setVisible(true);

    }
}*/

class BoardGridLayout extends JFrame{

    private Container contents;

    private JButton[][] squares = new JButton[4][4];

    private Color colorBlue = Color.BLUE;

    public BoardGridLayout() {
        
        super("ISOLATION THE ULTIMATE CHALLENGE");

        contents = getContentPane();
        contents.setLayout(new GridLayout(4,4));

        for (int i = 0; i < squares.length; i++) {
            for (int j = 0; j < squares.length; j++) {
                if ((i+j) % 2 != 0) {

                    squares [i][j] = new JButton("Lykas");
                    
                    squares[i][j].setBackground(Color.PINK);
                    squares[i][j].setName("Lykas");
                    
                    
                }else{
                    
                    squares [i][j] = new JButton();
                    
                }
                contents.add(squares[i][j]).setEnabled(false);
                
            }
        }
        setSize(500,500);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    
}

/**
 * Board
 */
public class Board {

    public static void main(String[] args) {
        BoardGridLayout board = new BoardGridLayout();
    }
}

