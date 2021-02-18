package brickBreaker;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

//KeyListener == listens to arrow keys
//ActionListener == listens to ball movement

public class Gameplay extends JPanel implements KeyListener, ActionListener {
    private boolean play = false;
    private int score = 0;

    private int totalBricks = 21;

    private Timer timer;
    private int delay = 8;

    private int playerX = 310;

    private int ballposX = 120;
    private int ballposY = 350;
    private int ballXdir = -1;
    private int ballYdir = -2;

    private MapGenerator map;

    public Gameplay(){
        map = new MapGenerator(3,7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics g){
        // Background
        g.setColor(Color.black);
        g.fillRect(1,1, 692, 592);

        // Drawing Map
        map.draw((Graphics2D)g);

        // Borders
        g.setColor(Color.orange);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(691, 0, 3, 592);

        // Scores
        g.setColor(Color.white);
        g.setFont(new Font("Times New Roman", Font.BOLD,25));
        g.drawString("" +score,590,30);

        // The paddle
        g.setColor(Color.cyan);
        g.fillRect(playerX, 550, 100, 8);

        // Ball
        g.setColor(Color.yellow);
        g.fillOval(ballposX, ballposY, 20, 20);

        // Win
        if(totalBricks<=0){
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.RED);
            g.setFont(new Font("Times New Roman", Font.BOLD,30));
            g.drawString("YOU WON! Score:"+ score, 190,300);

            g.setFont(new Font("Times New Roman", Font.BOLD,30));
            g.drawString("Press Enter to Restart",200,350);
        }

        // GameOver
        if(ballposY>570){
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.RED);
            g.setFont(new Font("Times New Roman", Font.BOLD,30));
            g.drawString("GAME OVER! Score:" + score, 190,300);

            g.setFont(new Font("Times New Roman", Font.BOLD,30));
            g.drawString("Press Enter to Restart",200,350);
        }

        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();

        // Code for movement of the ball
        if(play){
            if(new Rectangle(ballposX, ballposY, 20,20).intersects(new Rectangle(playerX, 550, 100, 8))){
                ballYdir = - ballYdir;
            }

        A:  for(int i=0; i<map.map.length; i++){
                for(int j=0; j<map.map[0].length; j++){
                    if(map.map[i][j]>0){
                        int brickX = j*map.brickwidth + 80;
                        int brickY = i*map.brickheight + 50;
                        int brickwidth = map.brickwidth;
                        int brickheight = map.brickheight;

                        Rectangle rect = new Rectangle(brickX, brickY, brickwidth, brickheight);
                        Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
                        Rectangle brickRect = rect;

                        if(ballRect.intersects(brickRect)){
                            map.setBrickvalue(0, i, j);
                            totalBricks--;
                            score += 5;

                            if(ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width){
                                ballXdir = -ballXdir;
                            }
                            else{
                                ballYdir = -ballYdir;
                            }
                            break A;
                        }
                    }
                }
            }


            ballposX += ballXdir;
            ballposY += ballYdir;
        }

        // Left Position
        if(ballposX < 0){
            ballXdir = -ballXdir;
        }

        // Top Position
        if(ballposY < 0) {
            ballYdir = -ballYdir;
        }

        // Right Position
        if(ballposX > 670){
            ballXdir = -ballXdir;
        }

        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            if(playerX >= 585){
                playerX = 580;
            }else{
                moveRight();
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            if(playerX < 10){
                playerX = 10;
            }else{
                moveLeft();
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            if(!play){
                play = true;
                ballposX = 120;
                ballposY = 350;
                ballXdir = -1;
                ballYdir = -2;
                playerX = 310;
                score = 0;
                totalBricks = 21;
                map = new MapGenerator(3,7);

                repaint();
            }
        }
    }
    public void moveRight(){
        play = true;
        playerX+=20;
    }
    public void moveLeft(){
        play = true;
        playerX-=20;
    }

}