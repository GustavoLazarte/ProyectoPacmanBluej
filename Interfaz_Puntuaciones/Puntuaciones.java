package Interfaz_Puntuaciones;

import Clases.Puntuacion;
import Herramientas.BotonesPersonalizados;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.TextField;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Puntuaciones extends JPanel {

    private ArrayList<Puntuacion> puntuaciones;
    private Rectangle tamaño;
    private JLabel titulo, gif;
    private BotonesPersonalizados btn_regresar;
    private ArrayList<TextField> txtNombres;
    private ArrayList<TextField> txtPuntuaciones;

    public Puntuaciones() {

        puntuaciones = new ArrayList<>();
        tamaño = new Rectangle(0, 0, 500, 650);
        setLayout(null);
        setOpaque(true);
        setVisible(false);
        setBounds(tamaño);
        setBackground(Color.BLACK);
        agregarTitulo();
        //agregarGif();
        agregarStickers();
        agregarBotonRegresar();
//        Puntuacion p = new Puntuacion("Mateo", 535);
//        Puntuacion p2 =  new Puntuacion("Jhoel", 132);
//        Puntuacion p3 = new Puntuacion("Vanesa", 170);
//        agregarPuntuacion(p);
//        
//        agregarPuntuacion(p2);
//        agregarPuntuacion(p3);
        mostrarPuntuaciones();
        //getPuntuaciones();
    }

    public void agregarPuntuacion(Puntuacion nueva) {
        boolean agregado = false;

        if (puntuaciones.isEmpty()) {
            puntuaciones.add(nueva);
        } else {
            for (int i = 0; i < puntuaciones.size() && !agregado; i++) {
                if (nueva.compareTo(puntuaciones.get(i)) > 0) {
                    puntuaciones.add(i, nueva);
                    agregado = true;
                }
            }
            if (!agregado) {
                puntuaciones.add(nueva);
            }
        }
    }

    private void agregarTitulo() {
        int x, y;
        ImageIcon ico = new ImageIcon("imgPanelPuntuaciones/ScoreT.png");
        x = 155;
        y = 5;
        titulo = new JLabel(ico);
        titulo.setBounds(x, y, ico.getIconWidth(), ico.getIconHeight());

        add(titulo);
    }

    private void agregarGif() {
        int x = 150;
        int y = 450;
        ImageIcon ico = new ImageIcon("imgPanelPuntuaciones/pacmanComiendo.gif");
        gif = new JLabel(ico);
        gif.setBounds(x, y, 200, 150);
        add(gif);
    }

    private void agregarStickers() {
        agregarStickersT();
        //agregarStickersD();
    }

    private void agregarStickersT() {
        int x = 15;
        int y = 0;
        ImageIcon ico = new ImageIcon("imgPanelPuntuaciones/pacman13.gif");
        JLabel s = new JLabel(ico);
        s.setBounds(x, y, 150, 520);
        add(s);
    }

    private void agregarStickersD() {
        int x = 450;
        int y = 400;
        ImageIcon ico = new ImageIcon("imgPanelPuntuaciones/trofeo.png");
        JLabel s = new JLabel(ico);
        s.setBounds(x, y, 150, 160);
        s.setIcon(new ImageIcon(ico.getImage().getScaledInstance(150, 160, Image.SCALE_SMOOTH)));
        add(s);
    }

    private void agregarBotonRegresar() {
        int x = 15;
        int y = 550;
        ImageIcon imgico = new ImageIcon("imgPanelPuntuaciones/Regresar.png");
        ImageIcon imgico2 = new ImageIcon("imgPanelPuntuaciones/Regresar.png");
        btn_regresar = new BotonesPersonalizados(x, y, imgico, imgico2);
        btn_regresar.setBounds(x, y, 100, 100);
        add(btn_regresar);

    }

    public ArrayList<Puntuacion> getPuntuaciones() {
        for (int i = 0; i < puntuaciones.size(); i++) {
            txtNombres.get(i).setText(puntuaciones.get(i).getNombre());
            txtPuntuaciones.get(i).setText("" + puntuaciones.get(i).getPuntos());
        }
        return puntuaciones;
    }

    public BotonesPersonalizados getBtn_regresar() {
        return btn_regresar;
    }

    public void mostrarPuntuaciones() {
        int a = 180;
        int b = 70;
        Puntuacion[] puntuacionesA = new Puntuacion[10];
        for (int j = 0; j < puntuaciones.size(); j++) {
            puntuacionesA[j] = puntuaciones.get(j);
        }
        for (int i = 0; i < puntuacionesA.length; i++) {
            Puntuacion p = puntuacionesA[i];
            JLabel puntaje = new JLabel();
            if (p != null) {
                puntaje.setText(i + 1 + "  " + p.getNombre() + "...." + "  " + p.getPuntos());
                //puntaje.setMaximumSize(maximumSize);
                puntaje.setBackground(Color.BLACK);
                puntaje.setForeground(Color.WHITE);
                puntaje.setBounds(a, b, 350, 80);
                puntaje.setFont(new Font("MegaMan 2 Regular", Font.BOLD, 20));
                b = b + 35;
            }/*else{
                puntaje.setText(i+1 + ".-" + "  " + "...........");
                puntaje.setBackground(Color.BLACK);
                puntaje.setForeground(Color.WHITE);
                puntaje.setBounds( a, b, 350, 80);
                puntaje.setFont(new Font("MegaMan 2 Regular",Font.BOLD,30));
                b = b + 35;
            }*/
            add(puntaje);
        }
    }

    public boolean sePuedeAñadir(Puntuacion otra) {
        if (otra.getPuntos() > 0) {
            if (puntuaciones.isEmpty() || puntuaciones.size() < 10) {
                return true;
            } else if ((puntuaciones.size() == 10) && (puntuaciones.get(puntuaciones.size() - 1).compareTo(otra) > -1)) {
                return false;
            } else if ((puntuaciones.size() == 10) && (puntuaciones.get(puntuaciones.size() - 1).compareTo(otra) == -1)) {
                return true;
            }
        }
        return false;
    }

    public void actualizar() {
        mostrarPuntuaciones();
    }

}
