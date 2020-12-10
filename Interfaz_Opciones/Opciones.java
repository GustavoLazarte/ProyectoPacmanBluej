/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz_Opciones;

import Herramientas.Audio;
import Herramientas.Controles;
import Interfaz_MenuJuego.Menu;
import Ventana.VentanaPrincipal;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map.Entry;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Freddy
 */
public class Opciones extends JPanel{

    private Rectangle tamaño;
    final private Hashtable<String, Integer> teclas;
    private JPanel opcionesApariencias;
    private JPanel opcionesControles;
    private JTextField arribaUno;
    private JTextField abajoUno;
    private JTextField izquierdaUno;
    private JTextField derechaUno;
    private JTextField arribaDos;
    private JTextField abajoDos;
    private JTextField izquierdaDos;
    private JTextField derechaDos;
    private JButton guardar;
    private JButton cancelar;
    private JButton apariencias;
    private JButton controles;
    private JButton restaurar;
    private Controles controlUno;
    private Controles controlDos;
    private Font f1, f2, f3;
    private Audio saveSound;
    private Audio optionSound;

    private Hashtable<String, ArrayList> skins;
    private ArrayList<ImageIcon> skinActual;
    private ArrayList<ImageIcon> predeterminada;
    private JComboBox listaApariencias;
    private JLabel pacman;
    private JLabel pacman2;
    private JLabel muro;
    private JLabel comida;
    private JLabel comida2;
    private JLabel suelo;
    private JLabel fantasmaUno;
    private JLabel fantasmaDos;
    private JLabel fantasmaTres;
    private JLabel fantasmaCuatro;

    public Opciones(Menu menu) {
        tamaño = new Rectangle(0, 0, 500, 500);
        setLayout(null);
        setOpaque(true);
        setVisible(false);
        setBounds(tamaño);
        setBackground(Color.black);
        setFocusable(true);
        saveSound = new Audio();
        optionSound = new Audio();
        teclas = new Hashtable();
        controlUno = new Controles(KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, null);
        controlDos = new Controles(KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, null);
        f1 = new Font("MegaMan 2 Regular", Font.BOLD, 16);
        f2 = new Font("MegaMan 2 Regular", Font.BOLD, 14);
        f3 = new Font("MegaMan 2 Regular", Font.BOLD, 20);

        opcionesApariencias = new JPanel();
        opcionesControles = new JPanel();
        crearPanel(opcionesApariencias);
        crearPanel(opcionesControles);

        //Opciones Controles
        crearDictTeclas();
        crearOpcionesJugador(1);
        crearOpcionesJugador(2);
        crearCampos();

        guardar = new JButton("Guardar");
        cancelar = new JButton("Cancelar");
        apariencias = new JButton("Apariencia");
        controles = new JButton("Controles");
        restaurar = new JButton ("Restaurar Predeterminado");

        crearBoton(guardar, 330, 450, 150, 25, f2, this, false);
        crearBoton(cancelar, 20, 450, 150, 25, f2, this, false);
        guardar.setForeground(Color.yellow);
        cancelar.setForeground(Color.yellow);
        crearBoton(apariencias, 5, 5, 242, 50, f3, this, true);
        crearBoton(controles, 253, 5, 242, 50, f3, this, true);
        crearBoton(restaurar, 75, 420, 350, 25, f2, this, false);

        //Opciones Apariencias
        skins = new Hashtable();
        predeterminada = new ArrayList();
        skinActual = new ArrayList();
        crearListaApariencias();
        pacman = new JLabel(skinActual.get(3));
        pacman2 = new JLabel(skinActual.get(29));
        muro = new JLabel(skinActual.get(8));
        comida = new JLabel(skinActual.get(5));
        comida2 = new JLabel(skinActual.get(6));
        suelo = new JLabel(skinActual.get(4));
        fantasmaUno = new JLabel(skinActual.get(12));
        fantasmaDos = new JLabel(skinActual.get(16));
        fantasmaTres = new JLabel(skinActual.get(20));
        fantasmaCuatro = new JLabel(skinActual.get(24));
        listaApariencias = new JComboBox();

        crearOpcionesApariencias();

        add(opcionesApariencias);
        add(opcionesControles);
        opcionesApariencias.setVisible(true);
        apariencias.setEnabled(false);
        controles.setForeground(Color.yellow);
        darFuncionesBotonesControles(menu);
        darFuncionesApariencia(menu);
    }

    private void crearBoton(JButton boton, int posX, int posY, int sizeX, int sizeY, Font f, JPanel panel, boolean conBordes){
        boton.setFont(f);
        boton.setForeground(Color.white);
        boton.setBackground(Color.black);
        boton.setBounds(posX, posY, sizeX, sizeY);
        boton.setFocusable(true);
        if(!conBordes){
            boton.setBorder(null);
        }
        boton.setVisible(true);
        panel.add(boton);
    }

    private void crearPanel(JPanel panel){
        panel.setLayout(null);
        panel.setBounds(0, 0, 500, 500);
        panel.setBackground(Color.black);
        panel.setVisible(false);
    }

    private void crearImagen(JLabel label, int posX, int posY, int objeto){
        label.setBounds(posX, posY, 50, 50);
        label.setIcon(new ImageIcon(skinActual.get(objeto).getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)));
        label.setVisible(true);
        opcionesApariencias.add(label);
    }

    private void darFuncionesBotonesControles(Menu menu){
        apariencias.addActionListener(new  ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                apariencias.setForeground(Color.white);
                apariencias.setEnabled(false);
                controles.setEnabled(true);
                controles.setForeground(Color.yellow);
                opcionesControles.setVisible(false);
                optionSound.reproducir("imgPanelOpciones/Option sound.wav");
                opcionesApariencias.setVisible(true);
            }
        });

        controles.addActionListener(new  ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controles.setForeground(Color.white);
                controles.setEnabled(false);
                apariencias.setEnabled(true);
                apariencias.setForeground(Color.yellow);
                opcionesApariencias.setVisible(false);
                optionSound.reproducir("imgPanelOpciones/Option sound.wav");
                opcionesControles.setVisible(true);
            }
        });

        guardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane advertencia = new JOptionPane();
                if(!checkTextFields()){
                    advertencia.setForeground(Color.white);
                    advertencia.setBackground(Color.black);
                    advertencia.showMessageDialog(opcionesControles, "Uno o más campos están vacíos.");
                }else{
                    int upOne = teclas.get(arribaUno.getText().toUpperCase());
                    int downOne = teclas.get(abajoUno.getText().toUpperCase());
                    int leftOne = teclas.get(izquierdaUno.getText().toUpperCase());
                    int rightOne = teclas.get(derechaUno.getText().toUpperCase());
                    int upTwo = teclas.get(arribaDos.getText().toUpperCase());
                    int downTwo = teclas.get(abajoDos.getText().toUpperCase());
                    int leftTwo = teclas.get(izquierdaDos.getText().toUpperCase());
                    int rightTwo = teclas.get(derechaDos.getText().toUpperCase());

                    if(!checkTeclas()){
                        advertencia.setForeground(Color.white);
                        advertencia.setBackground(Color.black);
                        advertencia.showMessageDialog(opcionesControles, "Inserte valores válidos.");
                    }else if(checkControls(upOne, downOne, leftOne, rightOne, upTwo, downTwo, leftTwo, rightTwo)){
                        advertencia.setForeground(Color.white);
                        advertencia.setBackground(Color.black);
                        advertencia.showMessageDialog(opcionesControles, "Los controles no pueden repetirse.");
                    }else{
                        controlUno.updateControles(upOne, downOne, leftOne, rightOne);
                        controlDos.updateControles(upTwo, downTwo, leftTwo, rightTwo);
                        setVisible(false);
                        //System.out.println("Abajo: " + downOne + " Arriba: " + upOne + " Derecha: " + rightOne + " Izquierda: " + leftOne);
                        skinActual = skins.get(listaApariencias.getSelectedItem().toString());
                        if (!saveSound.estaEnCurso()) {
                            saveSound.reproducir("imgPanelOpciones/Save sound.wav");
                        }
                        menu.setVisible(true);
                    }
                }
            }
        });

        restaurar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlUno.updateControles(KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D);
                controlDos.updateControles(KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT);
                setVisible(false);
                arribaUno.setText(getLlave(KeyEvent.VK_W).toLowerCase());
                abajoUno.setText(getLlave(KeyEvent.VK_S).toLowerCase());
                izquierdaUno.setText(getLlave(KeyEvent.VK_A).toLowerCase());
                derechaUno.setText(getLlave(KeyEvent.VK_D).toLowerCase());
                arribaDos.setText(getLlave(KeyEvent.VK_UP).toLowerCase());
                abajoDos.setText(getLlave(KeyEvent.VK_DOWN).toLowerCase());
                izquierdaDos.setText(getLlave(KeyEvent.VK_LEFT).toLowerCase());
                derechaDos.setText(getLlave(KeyEvent.VK_RIGHT).toLowerCase());
                
                skinActual = predeterminada;
                listaApariencias.setSelectedIndex(0);
                if (!saveSound.estaEnCurso()) {
                    saveSound.reproducir("imgPanelOpciones/Save sound.wav");
                }
                menu.setVisible(true);
                
            }
        });

        cancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                menu.setVisible(true);
            }
        });
    }

    private void crearDictTeclas(){
        teclas.put("A", KeyEvent.VK_A);
        teclas.put("B", KeyEvent.VK_B);
        teclas.put("C", KeyEvent.VK_C);
        teclas.put("D", KeyEvent.VK_D);
        teclas.put("E", KeyEvent.VK_E);
        teclas.put("F", KeyEvent.VK_F);
        teclas.put("G", KeyEvent.VK_G);
        teclas.put("H", KeyEvent.VK_H);
        teclas.put("I", KeyEvent.VK_I);
        teclas.put("J", KeyEvent.VK_J);
        teclas.put("K", KeyEvent.VK_K);
        teclas.put("L", KeyEvent.VK_L);
        teclas.put("M", KeyEvent.VK_M);
        teclas.put("N", KeyEvent.VK_N);
        teclas.put("O", KeyEvent.VK_O);
        teclas.put("P", KeyEvent.VK_P);
        teclas.put("Q", KeyEvent.VK_Q);
        teclas.put("R", KeyEvent.VK_R);
        teclas.put("S", KeyEvent.VK_S); 
        teclas.put("T", KeyEvent.VK_T);
        teclas.put("U", KeyEvent.VK_U);
        teclas.put("V", KeyEvent.VK_V);
        teclas.put("W", KeyEvent.VK_W);
        teclas.put("X", KeyEvent.VK_X);
        teclas.put("Y", KeyEvent.VK_Y);
        teclas.put("Z", KeyEvent.VK_Z);
        teclas.put("ARRIBA", KeyEvent.VK_UP);
        teclas.put("ABAJO", KeyEvent.VK_DOWN);
        teclas.put("IZQUIERDA", KeyEvent.VK_LEFT);
        teclas.put("DERECHA", KeyEvent.VK_RIGHT);
        teclas.put("1", KeyEvent.VK_1);
        teclas.put("2", KeyEvent.VK_2);
        teclas.put("3", KeyEvent.VK_3);
        teclas.put("4", KeyEvent.VK_4);
        teclas.put("5", KeyEvent.VK_5);
        teclas.put("6", KeyEvent.VK_6);
        teclas.put("7", KeyEvent.VK_7);
        teclas.put("8", KeyEvent.VK_8);
        teclas.put("9", KeyEvent.VK_9);
        teclas.put("0", KeyEvent.VK_0);
        teclas.put("NUMPAD 0", KeyEvent.VK_NUMPAD0);
        teclas.put("NUMPAD 1", KeyEvent.VK_NUMPAD1);
        teclas.put("NUMPAD 2", KeyEvent.VK_NUMPAD2);
        teclas.put("NUMPAD 3", KeyEvent.VK_NUMPAD3);
        teclas.put("NUMPAD 4", KeyEvent.VK_NUMPAD4);
        teclas.put("NUMPAD 5", KeyEvent.VK_NUMPAD5);
        teclas.put("NUMPAD 6", KeyEvent.VK_NUMPAD6);
        teclas.put("NUMPAD 7", KeyEvent.VK_NUMPAD7);
        teclas.put("NUMPAD 8", KeyEvent.VK_NUMPAD8);
        teclas.put("NUMPAD 9", KeyEvent.VK_NUMPAD9);
    }

    private void crearOpcionesJugador(int numJugador){
        JLabel control = new JLabel("Controles");
        JLabel jugador = new JLabel("Jugador " + numJugador);
        crearLabel(control, 45 + 265*(numJugador -1), 60, 150, 30, f1);
        crearLabel(jugador, 50 + 265*(numJugador -1), 80, 150, 30, f1);

        opcionesControles.add(control);
        opcionesControles.add(jugador);

        crearOpcion("Arriba", 20 + 265*(numJugador -1), 120);
        crearOpcion("Abajo", 20 + 265*(numJugador -1), 180);
        crearOpcion("Izquierda", 20 + 265*(numJugador -1), 240);
        crearOpcion("Derecha", 20 + 265*(numJugador -1), 300);
    }

    private void crearLabel(JLabel label, int posX, int posY, int sizeX, int sizeY, Font f){
        label.setBounds(posX, posY, sizeX, sizeY);
        label.setFont(f);
        label.setForeground(Color.yellow);
        label.setVisible(true);
    }

    private void crearOpcion(String direccion, int x, int y){
        ImageIcon icono = new ImageIcon("imgPanelOpciones/" + direccion + ".png");
        JLabel tecla = new JLabel(icono);
        tecla.setBounds(x, y, 50, 50);
        tecla.setVisible(true);
        opcionesControles.add(tecla);
    }

    private void crearCampos(){
        int campo = 0;
        String llave = "";
        campo = getCampo("Arriba", controlUno);
        llave = getLlave(campo);
        arribaUno = new JTextField(llave.toLowerCase());
        arribaUno.setBounds(80, 130, 125, 30);
        arribaUno.setFont(f2);
        arribaUno.setBorder(null);
        arribaUno.setBackground(Color.black);
        arribaUno.setForeground(Color.white);
        arribaUno.setVisible(true);
        actualizarCampo(arribaUno);

        campo = getCampo("Abajo", controlUno);
        llave = getLlave(campo);
        abajoUno = new JTextField(llave.toLowerCase());
        abajoUno.setBounds(80, 190, 125, 30);
        abajoUno.setFont(f2);
        abajoUno.setBorder(null);
        abajoUno.setBackground(Color.black);
        abajoUno.setForeground(Color.white);
        abajoUno.setVisible(true);
        actualizarCampo(abajoUno);

        campo = getCampo("Izquierda", controlUno);
        llave = getLlave(campo);
        izquierdaUno = new JTextField(llave.toLowerCase());
        izquierdaUno.setBounds(80, 250, 125, 30);
        izquierdaUno.setFont(f2);
        izquierdaUno.setBorder(null);
        izquierdaUno.setBackground(Color.black);
        izquierdaUno.setForeground(Color.white);
        izquierdaUno.setVisible(true);
        actualizarCampo(izquierdaUno);

        campo = getCampo("Derecha", controlUno);
        llave = getLlave(campo);
        derechaUno = new JTextField(llave.toLowerCase());
        derechaUno.setBounds(80, 310, 125, 30);
        derechaUno.setFont(f2);
        derechaUno.setBorder(null);
        derechaUno.setBackground(Color.black);
        derechaUno.setForeground(Color.white);
        derechaUno.setVisible(true);
        actualizarCampo(derechaUno);
        opcionesControles.add(arribaUno);
        opcionesControles.add(abajoUno);
        opcionesControles.add(izquierdaUno);
        opcionesControles.add(derechaUno);

        campo = getCampo("Arriba", controlDos);
        llave = getLlave(campo);
        arribaDos = new JTextField(llave.toLowerCase());
        arribaDos.setBounds(345, 130, 125, 30);
        arribaDos.setFont(f2);
        arribaDos.setBorder(null);
        arribaDos.setBackground(Color.black);
        arribaDos.setForeground(Color.white);
        arribaDos.setVisible(true);
        actualizarCampo(arribaDos);

        campo = getCampo("Abajo", controlDos);
        llave = getLlave(campo);
        abajoDos = new JTextField(llave.toLowerCase());
        abajoDos.setBounds(345, 190, 125, 30);
        abajoDos.setFont(f2);
        abajoDos.setBorder(null);
        abajoDos.setBackground(Color.black);
        abajoDos.setForeground(Color.white);
        abajoDos.setVisible(true);
        actualizarCampo(abajoDos);

        campo = getCampo("Izquierda", controlDos);
        llave = getLlave(campo);
        izquierdaDos = new JTextField(llave.toLowerCase());
        izquierdaDos.setBounds(345, 250, 125, 30);
        izquierdaDos.setFont(f2);
        izquierdaDos.setBorder(null);
        izquierdaDos.setBackground(Color.black);
        izquierdaDos.setForeground(Color.white);
        izquierdaDos.setVisible(true);
        actualizarCampo(izquierdaDos);

        campo = getCampo("Derecha", controlDos);
        llave = getLlave(campo);
        derechaDos = new JTextField(llave.toLowerCase());
        derechaDos.setBounds(345, 310, 125, 30);
        derechaDos.setFont(f2);
        derechaDos.setBorder(null);
        derechaDos.setBackground(Color.black);
        derechaDos.setForeground(Color.white);
        derechaDos.setVisible(true);
        actualizarCampo(derechaDos);
        opcionesControles.add(arribaDos);
        opcionesControles.add(abajoDos);
        opcionesControles.add(izquierdaDos);
        opcionesControles.add(derechaDos);
    }

    private int getCampo(String direccion, Controles controles){
        int campo = 0;
        switch(direccion){
            case "Arriba":
                campo = controles.getTeclaArriba();
                break;
            case "Abajo":
                campo = controles.getTeclaAbajo();
                break;
            case "Izquierda":
                campo = controles.getTeclaIzq();
                break;
            case "Derecha":
                campo = controles.getTeclaDer();
        }
        return campo;
    }

    private String getLlave(int campo){
        String llave = "";
        for(Entry<String, Integer> elemento: teclas.entrySet()){
            if(elemento.getValue() == campo){
                llave = elemento.getKey();
            }
        }
        return llave;
    }

    private boolean checkTextFields(){
        if(arribaUno.getText().equals("") || abajoUno.getText().equals("") || izquierdaUno.getText().equals("")
                || derechaUno.getText().equals("") || arribaDos.getText().equals("") || abajoDos.getText().equals("")
                || izquierdaDos.getText().equals("") || derechaDos.getText().equals("")){
            return false;
        }
        return true;
    }

    private boolean checkControls(int upOne, int downOne, int leftOne, int rightOne, int upTwo, int downTwo, int leftTwo, int rightTwo){
        if(upOne == downOne || upOne == leftOne || upOne == rightOne || downOne == leftOne
                || downOne == rightOne || leftOne == rightOne || upTwo == downTwo
                || upTwo == leftTwo || upTwo == rightTwo || downTwo == leftTwo || downTwo == rightTwo
                || leftTwo == rightTwo || upOne == upTwo || upOne == downTwo || upOne == leftTwo
                || upOne == rightTwo || downOne == upTwo || downOne == downTwo || downOne == leftTwo
                || downOne == rightTwo || leftOne == upTwo || leftOne == downTwo || leftOne == leftTwo
                || leftOne == rightTwo || rightOne == upTwo || rightOne == downTwo || rightOne == leftTwo
                || rightOne == rightTwo){
            return true;
        }
        return false;
    }

    private boolean checkTeclas(){
          if(teclas.containsKey(arribaUno.getText().toUpperCase()) && teclas.containsKey(abajoUno.getText().toUpperCase())
                  && teclas.containsKey(izquierdaUno.getText().toUpperCase()) && teclas.containsKey(derechaUno.getText().toUpperCase())
                  && teclas.containsKey(arribaDos.getText().toUpperCase()) && teclas.containsKey(abajoDos.getText().toUpperCase())
                  && teclas.containsKey(izquierdaDos.getText().toUpperCase()) && teclas.containsKey(derechaDos.getText().toUpperCase())){
              return true;
          }
          return false;
    }

    private void actualizarCampo(JTextField campo){
        campo.addKeyListener(new KeyListener(){
            @Override
            public void keyTyped(KeyEvent e) {
                campo.setEditable(false);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(teclas.containsValue(e.getKeyCode())){
                    campo.setText(getLlave(e.getKeyCode()).toLowerCase());
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                campo.setEditable(true);
            }

        });
    }

    public void crearListaApariencias(){
        predeterminada = crearApariencias("predeterminado/");
        skinActual = predeterminada;
        skins.put("Predeterminado", predeterminada);

        ArrayList<ImageIcon> skin;
        skin = crearApariencias("naranja/");
        skins.put("Naranja", skin);

        skin = crearApariencias("navidad/");
        skin.add(new ImageIcon("apariencias/navidad/Muro 1.png"));
        skin.add(new ImageIcon("apariencias/navidad/Muro 2.png"));
        skin.add(new ImageIcon("apariencias/navidad/Muro 3.png"));
        skin.add(new ImageIcon("apariencias/navidad/Muro 4.png"));
        skin.add(new ImageIcon("apariencias/navidad/Muro 5.png"));
        skin.add(new ImageIcon("apariencias/navidad/Muro 6.png"));
        skin.add(new ImageIcon("apariencias/navidad/Comida Alternativa 1.png"));
        skin.add(new ImageIcon("apariencias/navidad/Comida Alternativa 2.png"));
        skins.put("Navidad", skin);

        skin = crearApariencias("pokemon/");
        skins.put("Pokemon", skin);
    }

    private ArrayList crearApariencias(String direccion){
        ArrayList<ImageIcon> lista = new ArrayList();
        lista.add(new ImageIcon("apariencias/" + direccion + "pacmanDer.gif"));
        lista.add(new ImageIcon("apariencias/" + direccion + "pacmanIzq.gif"));
        lista.add(new ImageIcon("apariencias/" + direccion + "pacmanArri.gif"));
        lista.add(new ImageIcon("apariencias/" + direccion + "pacmanAba.gif"));

        lista.add(new ImageIcon("apariencias/" + direccion + "Suelo.png"));
        lista.add(new ImageIcon("apariencias/" + direccion + "Comida.png"));
        lista.add(new ImageIcon("apariencias/" + direccion + "Comida 2.png"));
        lista.add(new ImageIcon("apariencias/" + direccion + "Suero.png"));
        lista.add(new ImageIcon("apariencias/" + direccion + "Muro.png"));
        
        lista.add(new ImageIcon("apariencias/" + direccion + "Fan1 Der.png"));
        lista.add(new ImageIcon("apariencias/" + direccion + "Fan1 Izq.png"));
        lista.add(new ImageIcon("apariencias/" + direccion + "Fan1 Arriba.png"));
        lista.add(new ImageIcon("apariencias/" + direccion + "Fan1 Abajo.png"));
        lista.add(new ImageIcon("apariencias/" + direccion + "Fan2 Der.png"));
        lista.add(new ImageIcon("apariencias/" + direccion + "Fan2 Izq.png"));
        lista.add(new ImageIcon("apariencias/" + direccion + "Fan2 Arriba.png"));
        lista.add(new ImageIcon("apariencias/" + direccion + "Fan2 Abajo.png"));
        lista.add(new ImageIcon("apariencias/" + direccion + "Fan3 Der.png"));
        lista.add(new ImageIcon("apariencias/" + direccion + "Fan3 Izq.png"));
        lista.add(new ImageIcon("apariencias/" + direccion + "Fan3 Arriba.png"));
        lista.add(new ImageIcon("apariencias/" + direccion + "Fan3 Abajo.png"));
        lista.add(new ImageIcon("apariencias/" + direccion + "Fan4 Der.png"));
        lista.add(new ImageIcon("apariencias/" + direccion + "Fan4 Izq.png"));
        lista.add(new ImageIcon("apariencias/" + direccion + "Fan4 Arriba.png"));
        lista.add(new ImageIcon("apariencias/" + direccion + "Fan4 Abajo.png"));
        lista.add(new ImageIcon("apariencias/" + direccion + "Fantasma Vulnerable.gif"));

        lista.add(new ImageIcon("apariencias/" + direccion + "pacmanDer2.gif"));
        lista.add(new ImageIcon("apariencias/" + direccion + "pacmanIzq2.gif"));
        lista.add(new ImageIcon("apariencias/" + direccion + "pacmanArri2.gif"));
        lista.add(new ImageIcon("apariencias/" + direccion + "pacmanAba2.gif"));

        return lista;
    }

    public void crearOpcionesApariencias(){
        listaApariencias.setBounds(125, 80, 250, 40);
        listaApariencias.setFont(f2);
        listaApariencias.setForeground(Color.white);
        listaApariencias.setBackground(Color.black);
        listaApariencias.addItem("Predeterminado");
        listaApariencias.addItem("Naranja");
        listaApariencias.addItem("Navidad");
        listaApariencias.addItem("Pokemon");
        listaApariencias.setVisible(true);
        opcionesApariencias.add(listaApariencias);

        crearImagen(pacman, 197, 225, 3);
        crearImagen(pacman2, 253, 225, 29);
        crearImagen(muro, 308, 225, 8);
        crearImagen(comida, 197, 170, 5);
        crearImagen(comida2, 253, 170, 6);
        crearImagen(suelo, 142, 225, 4);
        crearImagen(fantasmaUno, 142, 280, 12);
        crearImagen(fantasmaDos, 197, 280, 16);
        crearImagen(fantasmaTres, 253, 280, 20);
        crearImagen(fantasmaCuatro, 308, 280, 24);
    }

    public void darFuncionesApariencia(Menu menu){
        listaApariencias.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                    ArrayList<ImageIcon> lista = new ArrayList();
                    lista = skins.get(listaApariencias.getSelectedItem().toString());
                    pacman.setIcon(new ImageIcon(lista.get(3).getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)));
                    pacman2.setIcon(new ImageIcon(lista.get(29).getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)));
                    muro.setIcon(new ImageIcon(lista.get(8).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
                    suelo.setIcon(new ImageIcon(lista.get(4).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
                    comida.setIcon(new ImageIcon(lista.get(5).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
                    comida2.setIcon(new ImageIcon(lista.get(6).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
                    fantasmaUno.setIcon(new ImageIcon(lista.get(12).getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)));
                    fantasmaDos.setIcon(new ImageIcon(lista.get(16).getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)));
                    fantasmaTres.setIcon(new ImageIcon(lista.get(20).getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)));
                    fantasmaCuatro.setIcon(new ImageIcon(lista.get(24).getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)));
                }
        });
    }

    public Controles getControl(int numJugador){
        if(numJugador == 1){
            return controlUno;
        }else{
            return controlDos;
        }
    }

    public ArrayList getApariencia(){
        return skinActual;
    }
}
