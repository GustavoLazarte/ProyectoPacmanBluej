/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz_Juego;

import Clases.Comida;
import Clases.Comida_Bonus;
import Clases.Comida_Especial;
import Clases.Fantasma;
import Clases.Pacman;
import Clases.Posicion;
import Clases.Tablero;
import Herramientas.Audio;
import static Interfaz_Juego.Juego.EN_CURSO;
import static Interfaz_Juego.Juego.NO_INICIADO;
import static Interfaz_Juego.Juego.TERMINADO;
import Interfaz_Opciones.Opciones;
import Ventana.VentanaPrincipal;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Miguel
 */
public class JuegoMulti extends JPanel {

    public static final int EN_CURSO = 1;
    public static final int NO_INICIADO = 0;
    public static final int TERMINADO = -1;
    private int[][] nivel1, nivel2, nivel3;
    private Tablero tab;
    private Pacman p, p2;
    private Timer timer;
    private int estado, nivel, contadorComida;
    private Color colorDeFondo;
    private Rectangle tamaño;
    private Audio audioDeComer, audioDeFondo, audioInicial;
    private JLabel etiquetaj1, etiquetaj2;
    private JLabel etiquetaDeAviso;
    private Font fuente;
    private int contador;
    private VentanaPrincipal padre;
    private Thread hilo;
    private int contadorDeSalida;

    public JuegoMulti(Opciones op, VentanaPrincipal padre) {
        this.padre = padre;
        cambiarAspectoPanel();
        instanciarVariablesJuego(op);
        instanciarHerramientasDeJuego();
        estado = NO_INICIADO;
        iniciarJuego();
        setFocusable(true);
    }

    public void paint(Graphics g) {
        tab.paint(g);
        super.paint(g);
        p.paint(g);
        p2.paint(g);

    }

    private void iniciarJuego() {
        //VentanaPrincipal.detenerMusica();
        audioInicial.reproducir("audio.wav");
        darAccion();
        obtenerControles();
        hilo = new Thread() {
            public void run() {
                while (estado != TERMINADO) {
                    repaint();
                    try {
                        //Paint Velocity 
                        Thread.sleep(25);
                    } catch (Exception ex) {
                        System.out.println("error in graphics engine: " + ex.getMessage());
                    }
                }
            }
        };
        hilo.start();
    }

    private void darAccion() {
        timer = new Timer(165, null);
        timer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (estado == EN_CURSO) {
                    if (sePuedeComer(p.getPosicion())) {
                        comerJ1();
                    }
                    if (sePuedeComer(p2.getPosicion())) {
                        comerJ2();
                    }
                    if (!tab.hayComida()) {
                        if (nivel < 3) {
                            avanzarNivel();
                        } else {
                            declararGanador();
                            estado = TERMINADO;
                        }
                    }
                    if (tab.hayComnidaBonus() && contadorComida >= 120 && contadorComida % 120 == 0) {
                        tab.aparecerFrutitas();
                        contadorComida++;
                    } else {
                        if (tab.hayComnidaBonus()) {
                            contadorComida++;
                        } else {
                            contadorComida = 0;
                        }
                    }
                    repaint();
                } else if (estado == TERMINADO) {
                    quitarAcciones();
                    if (contadorDeSalida == 35) {
                        terminarJuego();
                        timer.stop();
                    } else {
                        contadorDeSalida++;
                    }
                } else if (estado == NO_INICIADO) {
                    activarJuego();
                    repaint();
                }

            }
        });
        timer.start();

    }

    public void declararGanador() {
        if (p.getPuntos() > p2.getPuntos()) {
            etiquetaDeAviso.setText("Winner J1 ");
            etiquetaDeAviso.setVisible(true);
        } else if (p.getPuntos() < p2.getPuntos()) {
            etiquetaDeAviso.setText("Winner J2 ");
            etiquetaDeAviso.setVisible(true);
        } else {
            etiquetaDeAviso.setText("DRAW! ");
            etiquetaDeAviso.setVisible(true);
        }
    }

    public void activarJuego() {
        if (contador == 35) {
            etiquetaDeAviso.setVisible(false);
            etiquetaDeAviso.setText("GAME OVER");
            audioDeFondo.reproducirInfinito("pacmansiren.wav");
            timer.addActionListener(p);
            timer.addActionListener(p2);
            audioInicial.stop();
            estado = EN_CURSO;
            contador = -1;
        } else if (contador > -1) {
            contador++;
        }
    }

    private void avanzarNivel() {
        quitarAcciones();
        nivel++;
        switch (nivel) {
            case 2:
                nivel2 = getNivel2();
                tab.setElTablero(nivel2);
                break;
            case 3:
                nivel3 = getNivel3();
                tab.setElTablero(nivel3);
                break;
        }
        p.reiniciarPacman();
        p2.reiniciarPacman();
        estado = NO_INICIADO;
        contador = 0;
        audioDeFondo.stop();
        etiquetaDeAviso.setText("READY!");
        etiquetaDeAviso.setVisible(true);
    }

    private void terminarJuego() {
        if (contadorDeSalida == 65) {
            hilo.stop();
            audioDeFondo.stop();
            padre.salirDelJuego();
        } else {
            contadorDeSalida++;
            terminarJuego();
        }
    }

    private void comerJ1() {
        int pacx = p.getPosicion().getX();
        int pacy = p.getPosicion().getY();

        if (!audioDeComer.estaEnCurso()) {
            audioDeComer.reproducir("wakawaka.wav");
        }

        Comida aux = (Comida) tab.getElTablero()[pacy / 20][pacx / 20];
        if (aux instanceof Comida_Bonus) {
            Comida_Bonus frutitas = (Comida_Bonus) (aux);
            p.comer(frutitas.getValor());
        } else if (aux instanceof Comida_Especial) {
            Comida_Especial suero = (Comida_Especial) aux;
            p.comer(suero.getValor());
        } else {
            p.comer(aux.getValor());
        }
        tab.getElTablero()[pacy / 20][pacx / 20] = null;
    }

    private void comerJ2() {
        int pacx = p2.getPosicion().getX();
        int pacy = p2.getPosicion().getY();

        if (!audioDeComer.estaEnCurso()) {
            audioDeComer.reproducir("wakawaka.wav");
        }

        Comida aux = (Comida) tab.getElTablero()[pacy / 20][pacx / 20];
        if (aux instanceof Comida_Bonus) {
            Comida_Bonus frutitas = (Comida_Bonus) (aux);
            p2.comer(frutitas.getValor());
        } else if (aux instanceof Comida_Especial) {
            Comida_Especial suero = (Comida_Especial) aux;
            p2.comer(suero.getValor());
        } else {
            p2.comer(aux.getValor());
        };
        tab.getElTablero()[pacy / 20][pacx / 20] = null;
    }

    private boolean sePuedeComer(Posicion pacman) {
        int pacx = pacman.getX();
        int pacy = pacman.getY();

        return tab.getElTablero()[pacy / 20][pacx / 20] instanceof Comida;
    }

    private void obtenerControles() {
        addKeyListener(p.getControles());
        addKeyListener(p2.getControles());
    }

    private void cambiarAspectoPanel() {
        colorDeFondo = new Color(12, 20, 20);
        tamaño = new Rectangle(10, 40, 480, 560);
        setLayout(null);
        setOpaque(false);
        setBackground(colorDeFondo);
        setBounds(tamaño);
        setFocusable(true);
    }

    private void instanciarVariablesJuego(Opciones op) {
        contadorDeSalida = 0;
        nivel = 1;
        nivel1 = getNivel1();
        ArrayList<ImageIcon> img = op.getApariencia();
        ArrayList<ImageIcon> imagenesP1 = new ArrayList<>();
        cargarImagenes(img, imagenesP1, 0, 3);
        ArrayList<ImageIcon> imagenesP2 = new ArrayList<>();
        cargarImagenes(img, imagenesP2, 26, 29);
        ArrayList<ImageIcon> imagenesTab = new ArrayList<>();
        cargarImagenes(img, imagenesTab, 4, 8);
        tab = new Tablero(imagenesTab, nivel1);
        p = new Pacman(imagenesP1, new Posicion(200, 280, getWidth(), getHeight()), op.getControl(1));
        p2 = new Pacman(imagenesP2, new Posicion(300, 280, getWidth(), getHeight()), op.getControl(2));
    }

    private void instanciarHerramientasDeJuego() {
        audioInicial = new Audio();
        audioDeComer = new Audio();
        audioDeFondo = new Audio();
        agregarScore();
        agregarEtiquetaDeAviso();
    }

    private void agregarEtiquetaDeAviso() {
        etiquetaDeAviso = new JLabel("Listo!");
        etiquetaDeAviso.setFont(new Font("Megaman 2", Font.BOLD, 15));
        etiquetaDeAviso.setOpaque(false);
        etiquetaDeAviso.setBounds(170, 240, 250, 25);
        etiquetaDeAviso.setForeground(Color.YELLOW);
        etiquetaDeAviso.setVisible(true);
        add(etiquetaDeAviso);
    }

    private void agregarScore() {
        fuente = new Font("MegaMan 2", Font.BOLD, 15);
        etiquetaj1 = new JLabel("ScoreJ1");
        etiquetaj1.setBounds(10, 10, 120, 20);
        etiquetaj1.setFont(fuente);
        etiquetaj1.setForeground(colorDeFondo.YELLOW);
        etiquetaj1.setVisible(true);

        p.getPuntuacion().setBounds(180, 10, 120, 20);
        p.getPuntuacion().setFont(fuente);
        p.getPuntuacion().setForeground(colorDeFondo.WHITE);
        p.getPuntuacion().setVisible(true);
        etiquetaj2 = new JLabel("ScoreJ2");
        etiquetaj2.setBounds(250, 10, 120, 20);
        etiquetaj2.setFont(fuente);
        etiquetaj2.setForeground(colorDeFondo.YELLOW);
        etiquetaj2.setVisible(true);
        p2.getPuntuacion().setBounds(420, 10, 120, 20);
        p2.getPuntuacion().setFont(fuente);
        p2.getPuntuacion().setForeground(colorDeFondo.WHITE);
        p2.getPuntuacion().setVisible(true);

    }

    private void cargarImagenes(ArrayList<ImageIcon> img, ArrayList<ImageIcon> des, int ini, int fin) {
        for (int i = ini; i <= fin; i++) {
            des.add(img.get(i));
        }
    }

    private void quitarAcciones() {
        timer.removeActionListener(p);
        timer.removeActionListener(p2);
    }
    
    public int[][] getNivel3() {
        int[][] t2 = {
            {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
            {0, 1, 9, 7, 7, 1, 1, 1, 7, 7, 7, 7, 7, 7, 7, 7, 1, 1, 1, 7, 7, 9, 1, 0},
            {0, 1, 7, 1, 7, 7, 7, 7, 7, 1, 1, 1, 1, 1, 1, 7, 7, 7, 7, 7, 1, 7, 1, 0},
            {0, 1, 7, 7, 7, 1, 1, 1, 7, 7, 7, 7, 7, 7, 7, 7, 1, 1, 1, 7, 7, 7, 1, 0},
            {1, 1, 1, 7, 1, 1, 1, 1, 1, 1, 7, 1, 1, 7, 1, 1, 1, 1, 1, 1, 7, 1, 1, 1},
            {8, 7, 7, 7, 7, 7, 1, 1, 7, 7, 7, 7, 7, 7, 7, 7, 1, 1, 7, 7, 7, 7, 7, 8},
            {1, 1, 1, 1, 1, 7, 1, 7, 7, 1, 1, 1, 1, 1, 1, 7, 7, 1, 7, 1, 1, 1, 1, 1},
            {0, 1, 1, 1, 1, 7, 1, 7, 1, 1, 9, 7, 7, 9, 1, 1, 7, 7, 7, 1, 1, 1, 1, 0},
            {0, 1, 1, 7, 7, 7, 7, 7, 7, 1, 7, 1, 1, 7, 1, 7, 7, 1, 7, 7, 7, 1, 1, 0},
            {0, 1, 1, 7, 1, 1, 1, 1, 7, 1, 7, 1, 1, 7, 1, 7, 1, 1, 1, 1, 7, 1, 1, 0},
            {0, 1, 7, 7, 1, 1, 1, 7, 7, 0, 0, 0, 0, 0, 0, 7, 7, 1, 1, 1, 7, 7, 1, 0},
            {0, 1, 7, 1, 1, 1, 1, 7, 1, 0, 1, 0, 0, 1, 0, 1, 7, 1, 1, 1, 1, 7, 1, 0},
            {0, 1, 7, 7, 7, 7, 7, 7, 1, 0, 1, 0, 0, 1, 0, 1, 7, 7, 7, 7, 7, 7, 1, 0},
            {0, 1, 7, 1, 1, 7, 1, 7, 1, 0, 7, 1, 1, 7, 0, 1, 7, 1, 7, 1, 1, 7, 1, 0},
            {0, 1, 7, 1, 1, 7, 7, 7, 0, 1, 0, 6, 0, 0, 1, 0, 7, 7, 7, 1, 1, 7, 1, 0},
            {0, 1, 7, 1, 1, 7, 1, 7, 7, 1, 7, 1, 1, 7, 1, 7, 7, 1, 7, 1, 1, 7, 1, 0},
            {0, 1, 7, 7, 7, 7, 1, 1, 7, 7, 7, 0, 0, 7, 7, 7, 1, 1, 7, 7, 7, 7, 1, 0},
            {0, 1, 1, 7, 1, 7, 1, 7, 7, 1, 1, 1, 1, 1, 1, 7, 7, 7, 7, 1, 7, 1, 1, 0},
            {0, 1, 1, 7, 1, 7, 7, 7, 1, 1, 9, 7, 7, 9, 1, 1, 1, 7, 1, 1, 7, 1, 1, 0},
            {0, 1, 1, 7, 1, 7, 1, 1, 1, 7, 7, 1, 1, 7, 7, 7, 1, 7, 7, 1, 7, 1, 1, 0},
            {0, 1, 1, 7, 1, 7, 1, 1, 7, 7, 1, 1, 1, 1, 1, 7, 1, 1, 7, 1, 7, 1, 1, 0},
            {1, 1, 1, 7, 7, 7, 7, 7, 7, 1, 1, 7, 7, 7, 1, 7, 7, 7, 7, 7, 7, 1, 1, 1},
            {8, 7, 1, 1, 1, 1, 7, 1, 1, 1, 7, 7, 1, 7, 1, 1, 1, 7, 1, 1, 1, 1, 7, 8},
            {1, 7, 7, 1, 1, 7, 7, 7, 1, 1, 7, 1, 1, 7, 1, 1, 7, 7, 7, 1, 1, 7, 7, 1},
            {1, 1, 7, 1, 1, 7, 1, 7, 7, 7, 7, 1, 1, 7, 7, 7, 7, 1, 7, 1, 1, 7, 1, 1},
            {0, 1, 7, 1, 1, 7, 7, 7, 1, 1, 7, 1, 1, 7, 1, 1, 7, 7, 7, 1, 1, 7, 1, 0},
            {0, 1, 7, 7, 7, 7, 1, 1, 1, 9, 7, 7, 7, 7, 9, 1, 1, 1, 7, 7, 7, 7, 1, 0},
            {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0}};

        return t2;
    }

    public int[][] getNivel1() {
        int[][] t = {
            {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
            {0, 1, 9, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 9, 1, 0},
            {0, 1, 7, 1, 1, 1, 1, 1, 7, 1, 1, 1, 1, 1, 1, 7, 1, 1, 1, 1, 1, 7, 1, 0},
            {0, 1, 7, 1, 1, 1, 1, 1, 7, 1, 1, 1, 1, 1, 1, 7, 1, 1, 1, 1, 1, 7, 1, 0},
            {0, 1, 7, 7, 7, 7, 7, 7, 7, 1, 1, 1, 1, 1, 1, 7, 1, 7, 7, 7, 7, 7, 1, 0},
            {0, 1, 1, 7, 1, 1, 7, 1, 7, 7, 7, 7, 7, 7, 7, 7, 1, 7, 1, 1, 7, 1, 1, 0},
            {0, 1, 1, 7, 1, 1, 7, 1, 7, 1, 1, 1, 1, 1, 1, 7, 1, 7, 1, 1, 7, 1, 1, 0},
            {0, 1, 1, 7, 1, 1, 7, 1, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 1, 1, 0},
            {0, 1, 1, 7, 7, 7, 7, 7, 7, 1, 1, 1, 1, 1, 1, 7, 1, 7, 1, 1, 7, 1, 1, 0},
            {0, 1, 1, 7, 1, 1, 7, 1, 7, 1, 1, 1, 1, 1, 1, 7, 7, 7, 7, 7, 7, 7, 1, 0},
            {0, 1, 1, 7, 1, 1, 7, 1, 7, 1, 1, 1, 1, 1, 1, 7, 1, 7, 1, 1, 7, 1, 1, 0},
            {0, 1, 9, 7, 7, 7, 7, 7, 0, 0, 0, 0, 0, 0, 0, 0, 7, 7, 7, 7, 7, 9, 1, 0},
            {1, 1, 1, 1, 1, 1, 7, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 1, 7, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 7, 1, 0, 1, 1, 1, 1, 1, 1, 0, 7, 7, 7, 1, 1, 1, 1, 1},
            {8, 0, 0, 0, 0, 0, 7, 7, 0, 0, 0, 6, 0, 0, 0, 0, 1, 1, 7, 0, 0, 0, 0, 8},
            {1, 1, 1, 1, 1, 1, 7, 1, 7, 1, 1, 1, 1, 1, 1, 7, 1, 1, 7, 1, 1, 1, 1, 1},
            {0, 1, 9, 7, 7, 7, 7, 7, 7, 1, 1, 1, 1, 1, 1, 7, 7, 7, 7, 7, 7, 9, 1, 0},
            {0, 1, 1, 7, 1, 1, 7, 1, 7, 7, 7, 7, 7, 7, 7, 7, 1, 7, 1, 1, 7, 1, 1, 0},
            {0, 1, 7, 7, 7, 7, 7, 7, 7, 1, 1, 1, 1, 1, 1, 7, 1, 7, 1, 1, 7, 1, 1, 0},
            {0, 1, 1, 7, 1, 1, 7, 1, 7, 1, 1, 1, 1, 1, 1, 7, 7, 7, 7, 7, 7, 1, 1, 0},
            {0, 1, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 1, 7, 1, 1, 7, 1, 1, 0},
            {0, 1, 1, 7, 1, 1, 7, 1, 7, 1, 1, 1, 1, 1, 1, 7, 1, 7, 1, 1, 7, 1, 1, 0},
            {0, 1, 1, 7, 1, 1, 7, 1, 7, 7, 7, 7, 7, 7, 7, 7, 1, 7, 1, 1, 7, 1, 1, 0},
            {0, 1, 7, 7, 7, 7, 7, 1, 7, 1, 1, 1, 1, 1, 1, 7, 7, 7, 7, 7, 7, 7, 1, 0},
            {0, 1, 7, 1, 1, 1, 1, 1, 7, 1, 1, 1, 1, 1, 1, 7, 1, 1, 1, 1, 1, 7, 1, 0},
            {0, 1, 7, 1, 1, 1, 1, 1, 7, 1, 1, 1, 1, 1, 1, 7, 1, 1, 1, 1, 1, 7, 1, 0},
            {0, 1, 9, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 9, 1, 0},
            {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0}};
        return t;
    }

    public int[][] getNivel2() {
        int[][] t = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 1, 1, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 1},
            {1, 7, 1, 1, 1, 1, 7, 1, 1, 1, 7, 1, 1, 7, 1, 1, 1, 1, 7, 1, 1, 1, 7, 1},
            {1, 7, 1, 1, 1, 1, 7, 7, 7, 1, 7, 1, 1, 7, 1, 7, 7, 7, 7, 7, 7, 7, 7, 1},
            {8, 7, 7, 7, 7, 7, 7, 1, 7, 7, 7, 9, 7, 7, 7, 7, 1, 7, 1, 1, 1, 1, 7, 8},
            {1, 7, 1, 1, 1, 1, 7, 1, 1, 7, 1, 1, 1, 1, 7, 1, 1, 7, 7, 1, 1, 9, 7, 1},
            {1, 7, 9, 1, 1, 7, 7, 7, 7, 7, 1, 1, 1, 1, 7, 7, 7, 7, 1, 1, 1, 1, 7, 1},
            {1, 7, 1, 1, 1, 1, 7, 1, 1, 7, 7, 7, 7, 7, 1, 7, 1, 1, 7, 1, 1, 7, 7, 1},
            {1, 7, 7, 7, 7, 7, 7, 1, 1, 7, 1, 7, 1, 7, 1, 7, 1, 1, 7, 7, 7, 7, 1, 1},
            {1, 1, 1, 1, 1, 1, 7, 1, 1, 7, 1, 1, 1, 7, 1, 7, 1, 1, 7, 1, 1, 1, 1, 1},
            {0, 0, 0, 0, 0, 1, 7, 7, 0, 0, 0, 0, 0, 0, 0, 0, 7, 7, 7, 1, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 1, 7, 1, 0, 1, 0, 0, 0, 0, 1, 0, 1, 1, 7, 1, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 1, 7, 1, 0, 1, 0, 0, 0, 0, 1, 0, 1, 7, 7, 1, 0, 0, 0, 0},
            {1, 1, 1, 1, 1, 1, 7, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 7, 1, 1, 1, 1, 1},
            {8, 0, 0, 0, 0, 7, 7, 7, 0, 0, 0, 6, 0, 0, 0, 0, 7, 7, 7, 0, 0, 0, 0, 8},
            {1, 1, 1, 1, 1, 7, 1, 1, 7, 1, 1, 1, 1, 1, 1, 7, 1, 1, 7, 1, 1, 1, 1, 1},
            {0, 0, 0, 0, 1, 7, 1, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 1, 7, 1, 0, 0, 0, 0},
            {0, 0, 0, 0, 1, 7, 1, 7, 1, 1, 7, 1, 1, 7, 1, 1, 7, 1, 7, 1, 0, 0, 0, 0},
            {1, 1, 1, 1, 1, 7, 7, 7, 7, 1, 7, 1, 1, 7, 1, 7, 7, 7, 7, 1, 1, 1, 1, 1},
            {8, 7, 7, 7, 7, 7, 1, 1, 7, 7, 9, 1, 1, 9, 7, 7, 1, 1, 7, 7, 7, 7, 7, 8},
            {1, 7, 1, 7, 1, 7, 1, 1, 7, 1, 1, 1, 1, 1, 1, 7, 1, 1, 7, 1, 7, 1, 7, 1},
            {1, 7, 1, 1, 1, 7, 1, 1, 7, 7, 1, 1, 1, 1, 7, 7, 1, 1, 7, 1, 1, 1, 7, 1},
            {1, 7, 7, 9, 1, 7, 1, 1, 7, 1, 7, 1, 1, 7, 1, 7, 1, 1, 7, 1, 9, 7, 7, 1},
            {1, 1, 1, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 1, 1, 1},
            {8, 7, 7, 7, 1, 1, 7, 1, 1, 1, 7, 1, 1, 7, 1, 1, 1, 7, 1, 1, 7, 7, 7, 8},
            {1, 1, 1, 7, 1, 1, 7, 1, 1, 1, 7, 1, 1, 7, 1, 1, 1, 7, 1, 1, 7, 1, 1, 1},
            {1, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},};
        return t;
    }

    public JLabel getEtiquetaj1() {
        return etiquetaj1;
    }

    public JLabel getEtiquetaj2() {
        return etiquetaj2;
    }

    public JLabel getPuntuacionj1() {
        return p.getPuntuacion();
    }

    public JLabel getPuntuacionj2() {
        return p2.getPuntuacion();
    }

    public JLabel getEtiquetaDeAviso() {
        return etiquetaDeAviso;
    }

}
