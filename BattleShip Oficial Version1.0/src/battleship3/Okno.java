/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship3;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author student
 */
public class Okno extends JFrame implements ActionListener{
        
    BattleShip3 game = new BattleShip3(); 
    
    public Okno(){
        //
        setTitle("Игра Морской бой"); 
        setBounds(10, 10, 900, 600);
        // 
        getContentPane().add(createPanel()); 
        
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);  
    }
    //
    public JPanel createPanel(){
        //
        JPanel pole = new Pole(game); 
        // JButton
        // Добавить 2 кнопки: "Новая игра" и "Выход"
        JButton startButton = new JButton("Новая игра"); 
        startButton.setActionCommand(AppCommands.START);
        startButton.addActionListener(this);
        pole.add(startButton); 
        
        JButton exitButton = new JButton("Выход"); 
        exitButton.setActionCommand(AppCommands.EXIT);
        exitButton.addActionListener(this);
        pole.add(exitButton); 
        
        return pole; 
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("AppActionListener.actionPerformed.e.command=" + e.getActionCommand());
        
        if(e.getActionCommand().equals(AppCommands.EXIT)){
            System.exit(0);
        }else if(e.getActionCommand().equals(AppCommands.START)){
            game.start();
        }
    }
    
}
