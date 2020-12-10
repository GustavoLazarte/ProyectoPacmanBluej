package Interfaz_MenuJuego;

import Herramientas.BotonesPersonalizados;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BotonesDeMenu extends JPanel {

    private BotonesPersonalizados btn_Iniciar;
    private BotonesPersonalizados btn_OpcionesDeJuego;
    private BotonesPersonalizados btn_Puntuaciones;
    private BotonesPersonalizados btn_Salir;
    private BotonesPersonalizados btn_Multijugador;

    public BotonesDeMenu(int ancho,  int alto) {
        setSize(ancho, alto);
        setLayout(null);
        setOpaque(false);
        agregarBotones();
    }

    private void agregarBotones() {
        agregarBotonIniciar();
        agregarBotonOpcionesDeJuego();
        agregarBotonVerPuntuaciones();
        agregarBotonMultijugador();
    }

    private void agregarBotonIniciar() {
        int x = 160;
        //System.out.println(x);
        int y = 175;
        ImageIcon imgico = new ImageIcon("imgPanelMenu/boton star.png");
        ImageIcon imgico2 = new ImageIcon("imgPanelMenu/boton star.png");
        btn_Iniciar = new BotonesPersonalizados(x, y, imgico, imgico2);
        btn_Iniciar.setBounds(x, y, 180, 100);
        add(btn_Iniciar);
    }

    private void agregarBotonVerPuntuaciones() {
        int x = 20;
        int y = 255;
        ImageIcon imgico = new ImageIcon("imgPanelMenu/score.png");
        ImageIcon imgico2 = new ImageIcon("imgPanelMenu/score2.png");
        btn_Puntuaciones = new BotonesPersonalizados(x, y, imgico, imgico2);
        btn_Puntuaciones.setBounds(x, y, 230, 120);
        add(btn_Puntuaciones);
    }

    private void agregarBotonOpcionesDeJuego() {
        int x = 20;
        int y = 345;
        ImageIcon imgico = new ImageIcon("imgPanelMenu/Options.png");
        ImageIcon imgico2 = new ImageIcon("imgPanelMenu/Options2.png");
        btn_OpcionesDeJuego = new BotonesPersonalizados(x, y, imgico, imgico2);
        btn_OpcionesDeJuego.setBounds(x, y, 230, 150);
        add(btn_OpcionesDeJuego);
    }
    

    public BotonesPersonalizados getBtn_Iniciar() {
        return btn_Iniciar;
    }

    public BotonesPersonalizados getBtn_OpcionesDeJuego() {
        return btn_OpcionesDeJuego;
    }

    public BotonesPersonalizados getBtn_Puntuaciones() {
        return btn_Puntuaciones;
    }

    public BotonesPersonalizados getBtn_Salir() {
        return btn_Salir;
    }

    public BotonesPersonalizados getBtn_Multijugador() {
        return btn_Multijugador;
    }

    private void agregarBotonMultijugador() {
        int x = 320;
        int y = 360;
        ImageIcon imgico = new ImageIcon("imgPanelMenu/botonVersus.png");
        ImageIcon imgico2 = new ImageIcon("imgPanelMenu/botonVersus.png");
        btn_Multijugador = new BotonesPersonalizados(x, y, imgico, imgico2);
        btn_Multijugador.setBounds(x, y, 200, 150);
        add(btn_Multijugador);
    }
    
    
    
    
}
