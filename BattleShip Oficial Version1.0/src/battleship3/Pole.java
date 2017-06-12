/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship3;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author student
 */
public class Pole extends JPanel implements MouseListener, MouseMotionListener{
    
    static final int F_SIZE = BattleShip3.F_SIZE;
    
    static final String FON_IMAGE = "/sea.jpg"; 
    
    static final String PALUBA_IMAGE = "/paluba.png"; 
    static final String RANEN_IMAGE = "/ranen.png"; 
    static final String UBIT_IMAGE = "/ubit.png"; 
    static final String BOMBA_IMAGE = "/bomba.png";
    //
    static final String END1_IMAGE = "/end1.png"; 
    static final String END2_IMAGE = "/end2.png";
    // 
    Image fon; 
    Image paluba, ranen, ubit, bomba;
    Image end1, end2;
    // 
    Timer tmDraw;
    
    BattleShip3 game;
    
    public Pole(BattleShip3 game){
        //
        this.game = game;
        fon    = ImageUtil.loadResourceImage(FON_IMAGE); 
        paluba = ImageUtil.loadResourceImage(PALUBA_IMAGE); 
        ranen  = ImageUtil.loadResourceImage(RANEN_IMAGE); 
        ubit   = ImageUtil.loadResourceImage(UBIT_IMAGE); 
        bomba  = ImageUtil.loadResourceImage(BOMBA_IMAGE);      
        end1   = ImageUtil.loadResourceImage(END1_IMAGE);
        end2   = ImageUtil.loadResourceImage(END2_IMAGE);
        //
        this.tmDraw = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                repaint();
            }
        });
        tmDraw.start();
        
        addMouseListener(this);
        addMouseMotionListener(this);
        
        setFocusable(true);
        game.start();
    }
    
    @Override
    public void paintComponent(Graphics gr){
        super.paintComponent(gr);
        // фон
        gr.drawImage(fon, 0, 0, 900, 600, null);
        // заголовки Компьютер и Игрок
        gr.setColor(Color.blue);
        gr.setFont(new Font("serif", 3, 40));
        gr.drawString("Компьютер", 150, 50);  
        gr.drawString("Игрок", 590, 50);
        // сетка - компьютер
        drawGrid(gr, 100, 100, 30);
        // сетка - игрок
        drawGrid(gr, 500, 100, 30);
        
        
        // отрисовка кораблей 
        for(int i = 0; i < BattleShip3.F_SIZE; i++) {
             for(int j = 0; j < BattleShip3.F_SIZE; j++) {
                // comp
                if (game.computer()[i][j] != 0) {
                     if ((game.computer()[i][j] >= 8) && (game.computer()[i][j] <= 11)) {
                         gr.drawImage(ranen, 100 + j * 30, 100 + i * 30, 30, 30, null);
                     } else if (game.computer()[i][j] >= 15) {
                         gr.drawImage(ubit, 100 + j * 30, 100 + i * 30, 30, 30, null);
                     }
                     if (game.computer()[i][j] >= 5) {
                         gr.drawImage(bomba, 100 + j * 30, 100 + i * 30, 30, 30, null);
                     }
                }
                // player 
                if (game.player()[i][j] != 0) {
                    if ((game.player()[i][j] >= 1) && (game.player()[i][j] <= 4)) {
                        gr.drawImage(paluba, 500 + j * 30, 100 + i * 30, 30, 30, null);
                    } else if ((game.player()[i][j] >= 8) && (game.player()[i][j] <= 11)) {
                        gr.drawImage(ranen, 500 + j * 30, 100 + i * 30, 30, 30, null);
                    } else if (game.player()[i][j] >= 15) {
                        gr.drawImage(ubit, 500 + j * 30, 100 + i * 30, 30, 30, null);
                    }
                    if (game.player()[i][j] >= 5) {
                        gr.drawImage(bomba, 500 + j * 30, 100 + i * 30, 30, 30, null);
                    }
                }
            }
        } 
        

        if(game.playerWin()){            
            gr.drawImage(end1, 300, 200, 300, 100, null);
        }else if(game.computerWin()){
            gr.drawImage(end2, 300, 200, 300, 100, null);
        }  
    }
    
    //
    public void drawGrid(Graphics gr, int x, int y, int shag){

        gr.setColor(Color.blue);
        // отрисовка сетки поля
        for(int i = 0; i <= F_SIZE; i++){
            gr.drawLine(x + i*shag, y, x+i*shag, shag*F_SIZE + y);
            gr.drawLine(x, y+ i*shag, shag*F_SIZE + x, y+i*shag);
        }
        // отрисовка названий колонок и строк  
        gr.setFont(new Font("serif", 0, 20));
        gr.setColor(Color.red);
        
        for(int i = 1; i <= BattleShip3.F_SIZE; i++){
            String num = String.valueOf(i); 
            gr.drawString(num, x - 27,  y - 7 + i * shag); 
            //
            String letter = String.valueOf((char)('A' + i - 1));
            gr.drawString(letter, x + i * shag - 22, y - 7);
        }
    }
   
    
    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
         //
         System.out.println("mousePressed!");
         //
         if((e.getButton() == 1) && (e.getClickCount() == 1)) {
            int mX = e.getX();
            int mY = e.getY();
            if ((mX > 100) && (mY > 100) && (mX < 400) && (mY < 400)) {
                if (game.gameStarted() && (!game.computerStep())) {
                    /*
                    mX=349
                    mY=115
                    SHOT_i=8_j=0
                    */
                    System.out.println("mX=" + mX);
                    System.out.println("mY=" + mY);
                    int i = (mY - 100) / 30;
                    int j = (mX - 100) / 30;
                    //
                    if(game.computer()[i][j] <= 4) {
                        game.shot(i, j);
                    }
                }
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        
    }
}
