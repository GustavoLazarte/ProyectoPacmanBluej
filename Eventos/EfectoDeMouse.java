/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Eventos;

import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author Miguel
 */
public class EfectoDeMouse implements MouseListener {

    private String direccion,direccion2;
    public static final int AUMENTO=25;
    private JButton boton;
    private int anchoImg,altoImg;

    public EfectoDeMouse(String direccion,String direccion2, JButton boton) {
        this.direccion = direccion;
        //System.out.println("LA dir es"+direccion);
        this.direccion2 = direccion2;
        this.boton = boton;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        ImageIcon aux = new ImageIcon(direccion2);
        Image ima = aux.getImage().getScaledInstance(boton.getWidth(), boton.getHeight(), Image.SCALE_SMOOTH);
        aux = new ImageIcon(ima);
        boton.setIcon(aux);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        ImageIcon aux = new ImageIcon(direccion);
        Image ima = aux.getImage().getScaledInstance(boton.getWidth()-25, boton.getHeight()-25, Image.SCALE_SMOOTH);
        aux = new ImageIcon(ima);

        boton.setIcon(aux);
    }

}
