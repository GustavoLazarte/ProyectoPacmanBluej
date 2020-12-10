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
public abstract class Comida extends ObjetoDeJuego{
    protected int valor;
    
    public Comida(int valor, ImageIcon sp) {
        sprite = sp;
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }
    
    
}
