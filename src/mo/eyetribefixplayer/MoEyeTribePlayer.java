/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mo.eyetribefixplayer;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import javax.swing.JFrame;
import javax.swing.Timer;

/**
 *
 * @author gustavo
 */
public class MoEyeTribePlayer {

    private static long t;
 
    
    
    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
 /*EyeTribePanel panel=new EyeTribePanel();
        JFrame mainWindow=new JFrame("Menu sample");
        mainWindow.setLayout(new BorderLayout());
        mainWindow.add(panel);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setVisible(true);
        mainWindow.pack();*/

        //File videoFile =  new File("C:\\Users\\gustavo\\Desktop\\futureislands.mp4");
        File videoFile =  new File("C:\\Users\\gustavo\\Desktop\\mo-final\\MultimodalObserver-analysisModule\\build\\libs\\tt3\\participant-t3\\capture\\test1.mp4");
        File dataFile = new File("C:\\Users\\gustavo\\Desktop\\mo-final\\MultimodalObserver-analysisModule\\build\\libs\\tt3\\participant-t3\\capture\\test2.txt");
 
        EyeTribeFixPlayer player =  new EyeTribeFixPlayer(dataFile, videoFile);
        t = 0;


        for(int i = 0 ; i<5 ; i++){Thread.sleep(1000);}
        long t = 1495834712;
        
        t= t * 1000;
        t= t + 174;
        player.sumT(t);
        
        
        Timer timer = new Timer (10, new ActionListener () 
        { 
            public void actionPerformed(ActionEvent e) 
            {
                player.sumT(10);
                player.play(player.getT());
                
            } 
        }); 

        timer.start(); 
        
        
        

    }
    
}
