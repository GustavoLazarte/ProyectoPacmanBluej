package Interfaz_PanelInicio;

import Herramientas.Audio;
import Herramientas.BotonesPersonalizados;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Panel_Inicio extends JPanel{
    private BotonesPersonalizados btn_inicio, btn_fin;
    private JLabel titulo, gif;


    public Panel_Inicio() {
        setLayout(null);
        setOpaque(true);
        setBackground(Color.BLACK);
        setBounds(0,0,500, 500);
        agregarGif();
        agregarTitulo();
        agregarBotones();
 
    }

    private void agregarBotones() {
        agregarBotonInicio();
        agregarBotonSalir();
    }

    private void agregarBotonInicio() {
        int x = 65;
        int y = 345;
        ImageIcon imgico = new ImageIcon("imgPanelInicio/boton star.png");
        ImageIcon imgico2 = new ImageIcon("imgPanelInicio/boton star.png");
        btn_inicio = new BotonesPersonalizados(x, y, imgico, imgico2);
        btn_inicio.setBounds(x , y, 180, 85);
        add(btn_inicio);
    }
    
    private void agregarBotonSalir() {
        int x = 250;
        int y = 335;
        ImageIcon imgico = new ImageIcon("imgPanelInicio/boton exit.png");
        ImageIcon imgico2 = new ImageIcon("imgPanelInicio/boton exit.png");
        btn_fin = new BotonesPersonalizados(x, y, imgico,imgico2);
        btn_fin.setBounds(x , y, 230, 115);
        add(btn_fin);
    }

    private void agregarGif(){
        int x = 70;
        int y = 150;
        ImageIcon ico = new ImageIcon("imgPanelInicio/pacman14.gif");      
        gif = new JLabel(ico);
        gif.setBounds(x ,y, 400, 200);
        add(gif);
    }

    private void agregarTitulo() {
        int x,y;
        ImageIcon ico= new ImageIcon("imgPanelInicio/TituloPacman.png");
        x = 5;
        y = 5;
        titulo = new JLabel(ico);
        titulo.setBounds(x, y,ico.getIconWidth(),ico.getIconHeight());
        
        add(titulo);
    }



    public BotonesPersonalizados getBtn_inicio() {
        return btn_inicio;
    }

    public BotonesPersonalizados getBtn_fin() {
        return btn_fin;
    }
    

}
