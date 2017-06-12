/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship3;

/**
 *
 * @author Asus
 */
public interface IBattleShip {
    
    // начать игру 
    void start(); 
    // 
    void shot(int i, int j); 
    // 
    int[][] player();
    int[][] computer();
    
    boolean gameStarted(); 
    
    boolean computerStep(); 

    boolean playerWin();

    boolean computerWin(); 
}
