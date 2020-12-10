/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.ImageIcon;

/**
 *
 * @author Miguel
 */
public class Muro extends ObjetoDeJuego {

    public Muro(ImageIcon sp) {
        super();
        sprite = sp;
        ubic = new Ubicacion();
    }
    
    public Muro(ArrayList<ImageIcon> sprites){
        super();
        int rnd= (int)(Math.random()*sprites.size());
        sprite = sprites.get(rnd);
        ubic = new Ubicacion();
    }
    
    

    @Override
    void paint(Graphics g) {
        if(sprite != null){
            g.drawImage(sprite.getImage(),ubic.y, ubic.x, ancho, alto, null);
        }
    }

    @Override
    void setUbicacion(int x, int y) {
        ubic.setUbicacion(x, y);
    }

    
    
}
