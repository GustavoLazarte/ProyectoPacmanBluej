/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import Herramientas.MovimientoAuto;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.ImageIcon;

/**
 *
 * @author Miguel
 */
public class Fantasma {

    private boolean comible;
    private final int valor;
    private Posicion posicion, inicial;
    private ImageIcon fantasmaNormal, fantasmaComible;
    private MovimientoAuto mov;
    private ArrayList<ImageIcon> imagenes;
    private int numFantasma;

    public Fantasma(ArrayList<ImageIcon> fant, int numImagenFant, ImageIcon fantasmaComible, Posicion p) {
        valor = 200;
        this.imagenes = fant;
        this.fantasmaNormal = imagenes.get(numImagenFant);
        this.fantasmaComible = fantasmaComible;
        numFantasma = (numImagenFant + 1) / 4;
        comible = false;
        inicial = new Posicion(p.getX(), p.getY(), (int) p.getArea().getWidth(), (int) p.getArea().getHeight());
        posicion = p;
        mov = new MovimientoAuto(this);
    }

    public void paint(Graphics g) {
        if (!comible) {
            g.drawImage(fantasmaNormal.getImage(), posicion.getX(), posicion.getY(), 25, 25, null);
        } else {
            g.drawImage(fantasmaComible.getImage(), posicion.getX(), posicion.getY(), 25, 25, null);
        }
    }

    public void cambiarFormaComible() {
        comible = true;
    }

    public void cambiarFormaNoComible() {
        comible = false;
    }

    public boolean esComible() {
        return comible == true;
    }

    public int getValor() {
        return valor;
    }

    public Posicion getPosicion() {
        return posicion;
    }

    public MovimientoAuto getMov() {
        return mov;
    }

    public void setImg(int i) {
        this.fantasmaNormal = imagenes.get(i);
    }

    public void reiniciarFantasma() {
        comible = false;
        posicion = new Posicion(inicial.getX(), inicial.getY(), (int) inicial.getArea().getMaxX(), (int) inicial.getArea().getMaxY());
    }

    public int getNumFantasma() {
        return numFantasma;
    }
    
    

}
