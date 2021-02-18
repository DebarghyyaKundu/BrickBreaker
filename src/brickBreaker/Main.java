package brickBreaker;

// Swing package is used to import buttons
import javax.swing.JFrame;

public class Main {

    public static void main(String[] args) {
	// write your code here
        JFrame obj = new JFrame();
    // To access gameplay in main method
        Gameplay gamePlay = new Gameplay();
        obj.setBounds(10,10,700,600);
        obj.setTitle("Breakout Ball");
        obj.setResizable(false);
        obj.setVisible(true);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.add(gamePlay);
    }
}
