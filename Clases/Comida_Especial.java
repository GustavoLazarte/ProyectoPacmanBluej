/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.ImageIcon;

/**
 *
 * @author Miguel
 */
public class Comida_Especial extends Comida {

    private static final int VALOR = 50;

    public Comida_Especial(ImageIcon sp) {
        super(VALOR,sp);
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

    public void cambiarEstado(Fantasma[] fantasmas){
        for (int i = 0; i < fantasmas.length; i++) {
            fantasmas[i].cambiarFormaComible();
        }
    }
    
}
