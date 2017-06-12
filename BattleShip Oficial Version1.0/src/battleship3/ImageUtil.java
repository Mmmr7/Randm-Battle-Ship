/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship3;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/**
 *
 * @author student
 */
public class ImageUtil {
    
    // метод для загрузки изображения 
    public static BufferedImage loadImage(String fileName){
        //
        try{
            return ImageIO.read(new File(fileName));
        }catch(Exception exc){
            exc.printStackTrace();
        }
        return null; 
    }   
    // метод для загрузки изображения из jar архива 
    public static BufferedImage loadResourceImage(String fileName){
        // 
        try{
            return ImageIO.read(ImageUtil.class.getResourceAsStream(fileName));
        }catch(Exception exc){
            exc.printStackTrace();
        }
        return null; 
    }
}
