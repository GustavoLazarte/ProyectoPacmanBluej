/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

/**
 *
 * @author Miguel
 */
public class Ubicacion {
    protected int x;
    protected int y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public void setX(int x) {
        this.x = x*20;
    }

    public void setY(int y) {
        this.y = y*20;
    }
    
    public void setUbicacion(int x, int y){
        setX(x);
        setY(y);
    }
    
    
}
