/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author Miguel
 */
public abstract class ObjetoDeJuego {
    protected Ubicacion ubic;
    protected int ancho,alto;
    protected ImageIcon sprite;
    abstract void paint(Graphics g);
    abstract void setUbicacion(int x, int y);

    public ObjetoDeJuego() {
        ancho = alto = 20;
    }

    
    
    

    public Ubicacion getUbic() {
        return ubic;
    }
    
    
}
