/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author Miguel
 */
public class Tablero {

    public static ObjetoDeJuego[][] elTablero;
    private ImageIcon comidaNormal;
    private ImageIcon comidaEspecial;
    private ImageIcon comidaBonus;
    private ImageIcon muro;
    private ImageIcon suelo;
    private ArrayList<ImageIcon> img;
    private ArrayList<ImageIcon> muros;

    public Tablero(ArrayList<ImageIcon> img, int[][] tableroBase) {
        this.img = img;
        comidaNormal = img.get(1);
        comidaEspecial = img.get(3);
        comidaBonus = img.get(2);
        suelo = img.get(0);
        muro = img.get(4);
        generarTablero(tableroBase);
        elTablero = generarTablero(tableroBase);
    }

    public Tablero(ArrayList<ImageIcon> img, ArrayList<ImageIcon> muros, int[][] tableroBase) {
        this.img = img;
        comidaNormal = img.get(0);
        comidaEspecial = img.get(1);
        comidaBonus = img.get(2);
        suelo = img.get(3);
        this.muros = muros;
        elTablero = generarTablero(tableroBase);
        this.muros = muros;

    }

    public void paint(Graphics g) {

        for (int i = 0; i < elTablero.length; i++) {
            for (int j = 0; j < elTablero[i].length; j++) {
                if (elTablero[i][j] != null) {
                    elTablero[i][j].setUbicacion(i, j);
                    elTablero[i][j].paint(g);
                } else {
                    if (suelo != null) {
                        g.drawImage(suelo.getImage(), j * 20, i * 20, 20, 20, null);
                    }
                }
            }
        }
    }

    private ObjetoDeJuego[][] generarTablero(int[][] t) {
        elTablero = new ObjetoDeJuego[t.length][t[0].length];
        for (int i = 0; i < t.length; i++) {
            for (int j = 0; j < t[i].length; j++) {
                if (t[i][j] == 1) {
                    if (muro != null) {
                        elTablero[i][j] = new Muro(muro);
                    } else {
                        elTablero[i][j] = new Muro(muros);
                    }
                } else if (t[i][j] == 7) {
                    elTablero[i][j] = new Comida_Normal(comidaNormal);
                } else if (t[i][j] == 8) {
                    elTablero[i][j] = new Portal(suelo);
                } else if (t[i][j] == 9) {
                    elTablero[i][j] = new Comida_Especial(comidaEspecial);
                } else if (t[i][j] == 6) {
                    elTablero[i][j] = new Comida_Bonus(comidaBonus, suelo);
                } else {
                    elTablero[i][j] = null;
                }
            }
        }

        return elTablero;
    }
    
    public static ObjetoDeJuego[][] getElTablero() {
        return elTablero;
    }

    public boolean hayComida() {
        boolean hay = false;
        for (int i = 0; i < elTablero.length && !hay; i++) {
            for (int j = 0; j < elTablero[i].length && !hay; j++) {
                if (elTablero[i][j] instanceof Comida_Especial || elTablero[i][j] instanceof Comida_Normal) {
                    hay = true;
                }
            }
        }

        return hay;
    }

    public void setElTablero(int[][] otroTab) {
        elTablero = generarTablero(otroTab);
    }

    public void aparecerFrutitas() {
        for (int i = 0; i < elTablero.length; i++) {
            for (int j = 0; j < elTablero[i].length; j++) {
                if (elTablero[i][j] instanceof Comida_Bonus) {
                    Comida_Bonus aux = (Comida_Bonus) elTablero[i][j];
                    aux.aparecer();
                }
            }
        }
    }
    
    public boolean  hayComnidaBonus() {
        for (int i = 0; i < elTablero.length; i++) {
            for (int j = 0; j < elTablero[i].length; j++) {
                if (elTablero[i][j] instanceof Comida_Bonus) {
                    return true;
                }
            }
        }
        
        return false;
    }

}
