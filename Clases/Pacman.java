/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import Herramientas.Controles;
import Interfaz_Juego.Juego;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

/**
 *
 * @author Miguel
 */
public class Pacman implements ActionListener {

    
    private Controles controles;
    private int vida;
    private int puntos;
    private Posicion posicion, inicial;
    private boolean vivo;
    private ImageIcon imgActual, cerrado;
    private ArrayList<ImageIcon> imagenes , imagenes2;
    private boolean estado;
    private int cantidadVidas;
    private JLabel etiquetaVidas, etiquetaCantidad, puntuacion;

    public Pacman(ArrayList<ImageIcon> img, Posicion pos, Controles c) {
        etiquetaVidas = new JLabel("LIVES");
        cantidadVidas = 3;
        etiquetaCantidad = new JLabel(""+cantidadVidas);
        puntuacion = new JLabel(""+ puntos);
        estado = true;
        imagenes = img;
        imgActual = img.get(0);
        inicial = new Posicion(pos.getX(), pos.getY(), (int)pos.getArea().getWidth(), (int)pos.getArea().getHeight());
        posicion = pos;
        controles = c;
        
        controles.setP(this);
        vivo = true;
    }

    public void paint(Graphics g) {
        g.drawImage(imgActual.getImage(), posicion.getX(), posicion.getY(), 25, 25, null);    
    }

    public int getVida() {
        return vida;
    }

    public void setImg(int i) {
        this.imgActual = imagenes.get(i);
    }

    public int getPuntos() {
        return puntos;
    }

    public Posicion getPosicion() {
        return posicion;
    }

    public boolean estaVivo() {
        return vivo;
    }

    public ImageIcon getImg() {
        return imgActual;
    }

    public Controles getControles() {
        return controles;
    }

    public void comer(int puntos) {
        this.puntos += puntos;
        puntuacion.setText(""+ this.puntos);
        puntuacion.repaint();
    }

    public void morir() {
        vivo= false;
        cantidadVidas--;
        etiquetaCantidad.setText(" "+ cantidadVidas);
        etiquetaCantidad.repaint();
    }
    
    public void reiniciarPacman(){
        if(cantidadVidas > 0){
            posicion = new Posicion(inicial.getX(), inicial.getY(), (int)inicial.getArea().getMaxX(), (int)inicial.getArea().getMaxY());
            vivo = true;
        }
    }
    
    public boolean tieneVidas(){
        return cantidadVidas > 0;
    }
    
    public boolean isVivo() {
        return vivo;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if (tieneVidas()) {
            if (getControles().isArriba()) {
                posicion.moverArriba();
            } else if (getControles().isAbajo()) {
                posicion.moverAbajo();
            } else if (getControles().isDer()) {
                posicion.moverDerecha();
            } else if (getControles().isIzq()) {
                posicion.moverIzquierda();
            }
            
            if(puntos >0 && puntos % 2500== 0){
                cantidadVidas++;
            }
        }
    }

    public JLabel getEtiquetaVidas() {
        return etiquetaVidas;
    }

    public JLabel getEtiquetaCantidad() {
        return etiquetaCantidad;
    }

    private void agregarEtiquetas() {
        etiquetaVidas = new JLabel("LIVES");
        cantidadVidas = 3;
        etiquetaVidas.setVisible(true);
        etiquetaCantidad = new JLabel(""+cantidadVidas);
        etiquetaCantidad.setVisible(true);
        
        puntuacion = new JLabel(""+puntos);
        etiquetaCantidad.setVisible(true);
    }

    public JLabel getPuntuacion() {
        return puntuacion;
    }
    
    
}
