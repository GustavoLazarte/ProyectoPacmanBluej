/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Herramientas;

import Clases.Pacman;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Miguel
 */
public class Controles implements KeyListener {

    private int teclaArriba, teclaAbajo, teclaIzq, teclaDer;
    private boolean arriba = false,
            abajo = false,
            izq = false,
            der = true;
    private Pacman p;

    public Controles(int teclaArriba, int teclaAbajo, int teclaIzq, int teclaDer, Pacman p) {
        this.teclaArriba = teclaArriba;
        this.teclaAbajo = teclaAbajo;
        this.teclaIzq = teclaIzq;
        this.teclaDer = teclaDer;
        this.p = p;
    }
    

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode()== teclaArriba) {
            if (p.getPosicion().sePuedeMoverArri()) {
                arriba = true;
                abajo = false;
                izq = false;
                der = false;
                p.setImg(2);
            }
            //repaint();
        } else if (e.getKeyCode()== teclaAbajo) {
            if (p.getPosicion().sePuedeMoverAba()) {
                arriba = false;
                abajo = true;
                izq = false;
                der = false;
                p.setImg(3);
            }
        } else if (e.getKeyCode()== teclaIzq) {
            if (p.getPosicion().sePuedeMoverIzq()) {
                arriba = false;
                abajo = false;
                izq = true;
                der = false;
                p.setImg(1);
            }
        } else if (e.getKeyCode()== teclaDer) {
            if (p.getPosicion().sePuedeMoverDer()) {
                arriba = false;
                abajo = false;
                izq = false;
                der = true;
                p.setImg(0);
            }

        }
    }
    @Override
    public void keyReleased (KeyEvent e) {
        
    }

    public int getTeclaArriba() {
        return teclaArriba;
    }

    public int getTeclaAbajo() {
        return teclaAbajo;
    }

    public int getTeclaIzq() {
        return teclaIzq;
    }

    public int getTeclaDer() {
        return teclaDer;
    }

    public boolean isArriba() {
        return arriba;
    }

    public boolean isAbajo() {
        return abajo;
    }

    public boolean isIzq() {
        return izq;
    }

    public boolean isDer() {
        return der;
    }

    public void updateControles(int Arriba, int Abajo, int Izq, int Der){
        this.teclaArriba = Arriba;
        this.teclaAbajo = Abajo;
        this.teclaIzq = Izq;
        this.teclaDer = Der;
    }

    public void setP(Pacman p) {
        this.p = p;
    }
    
    
}

        


