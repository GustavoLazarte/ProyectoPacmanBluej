package Interfaz_MenuJuego;

import Herramientas.Audio;
import Herramientas.BotonesPersonalizados;
import java.awt.Color;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Menu extends JPanel {
    private BotonesDeMenu botones;
    private JLabel fondo;
    private Audio audioFondo;

    public Menu() {
        setLayout(null);
        setOpaque(true);
        setVisible(false);
        setBounds(0,0,500, 500);
        setBackground(Color.BLACK); 
        agregarF();
        //agregarM();
        agregarBotones();
        agregarMusiquita();
        
    }

    private void agregarBotones() {
        
        botones = new BotonesDeMenu(500,500);
        botones.setLocation(0,0);
        //botones.setLocation(0,getHeight());
        add(botones);
    }
    
    private void agregarF(){
        int x = 0;
        int y = 0;
        ImageIcon ico = new ImageIcon("imgPanelMenu/fantasmas.jpg");
        JLabel s = new JLabel(ico);
        s.setBounds(x, y, 500, 180);
        s.setIcon(new ImageIcon(ico.getImage().getScaledInstance(500, 180, Image.SCALE_SMOOTH)));
        add(s);
    }
    private void agregarM(){
        int x = 290;
        int y = 100;
        ImageIcon ico = new ImageIcon("imgPanelMenu/miniPacman4.png");
        JLabel s = new JLabel(ico);
        s.setBounds(x, y, 100, 50);
        s.setIcon(new ImageIcon(ico.getImage().getScaledInstance(100, 50, Image.SCALE_SMOOTH)));
        add(s);
    }
    
    private void agregarMusiquita() {
        if(isVisible()){
            audioFondo = new Audio();
            audioFondo.reproducir("audioDeFondo.wav");
        }
    }

    public BotonesDeMenu getBotones() {
        return botones;
    }
    
  
    
}
