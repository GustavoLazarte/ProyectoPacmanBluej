/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Herramientas;

import Eventos.EfectoDeMouse;
import java.awt.Font;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author Miguel
 */
public class BotonesPersonalizados extends JButton{
    public static final int ancho= 250,alto=100;
    private ImageIcon icono,iconoCambio;
    public BotonesPersonalizados(int x, int y, ImageIcon icon, ImageIcon icon2) {
        Image img= icon.getImage().getScaledInstance(ancho-EfectoDeMouse.AUMENTO, alto-EfectoDeMouse.AUMENTO, Image.SCALE_SMOOTH);
        icono = new ImageIcon(img);
        img= icon2.getImage().getScaledInstance(ancho-EfectoDeMouse.AUMENTO, alto-EfectoDeMouse.AUMENTO, Image.SCALE_SMOOTH);
        iconoCambio = new ImageIcon(img);
        setContentAreaFilled(false);
        setBounds(x, y, ancho, alto);
        setIcon(icono);
        setBorderPainted(false);
        setFocusable(false);
        String a= icon.getDescription();
        String b= icon2.getDescription();
        //System.out.println(b);
        addMouseListener(new EfectoDeMouse(a,b, this));
        setVisible(true);
    }
    
    
}
