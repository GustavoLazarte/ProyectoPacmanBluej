/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Herramientas;

import Clases.Fantasma;
import Clases.Pacman;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import jdk.jfr.SettingDescriptor;

/**
 *
 * @author Miguel
 */
public class MovimientoAuto implements ActionListener {

    private static Audio choque1= new Audio(), choque2 =  new Audio();
    private int contadorDeEspera, contadorReset;
    private Timer t;
    private Fantasma f;
    private boolean arriba = false,
            abajo = false,
            izq = false,
            der = true;
    private int contadorDePasos, contadorDeReInicio;
    private int limite;
    private Pacman p;

    public MovimientoAuto(Fantasma f) {
        this.f = f;
//        t = new Timer(155, this);
        cambiarDireccion();
        limite = (int) (Math.random() * 15 + 2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (contadorDePasos < limite) {
            if (hayColision()) {
                colisionar();
            }
            if (arriba) {
                if (f.getPosicion().moverArriba()) {;
                    contadorDePasos++;
                    if (hayColision()) {
                        colisionar();
                    }
                } else {
                    cambiarDireccion();
                }
            } else if (abajo) {
                if (f.getPosicion().moverAbajo()) {;
                    contadorDePasos++;
                    if (hayColision()) {
                        colisionar();
                    }
                } else {
                    cambiarDireccion();
                }

            } else if (der) {
                if (f.getPosicion().moverDerecha()) {;
                    contadorDePasos++;
                    if (hayColision()) {
                        colisionar();
                    }
                } else {
                    cambiarDireccion();
                }

            } else if (izq) {
                if (f.getPosicion().moverIzquierda()) {
                    contadorDePasos++;
                    if (hayColision()) {
                        colisionar();
                    }
                } else {
                    cambiarDireccion();
                }
            }
            if (hayColision()) {
                colisionar();
            }

            if (f.esComible()) {
                retomarForma();
            }

            contadorDePasos++;
        } else {
            contadorDePasos = 0;
            limite = (int) (Math.random() * 15 + 1);
            cambiarDireccion();
            if (hayColision()) {
                colisionar();
            }
            if (f.esComible()) {
                retomarForma();
            }
        }
    }

    private void cambiarDireccion() {
//        621014
        switch ((int) (Math.random() * 4)) {
            case 0:
                if (!arriba) {
                    arriba = true;
                    abajo = false;
                    izq = false;
                    der = false;
                    f.setImg(2 + 4*(f.getNumFantasma()-1));
                    break;
                }
            case 1:
                if (!abajo) {
                    arriba = false;
                    abajo = true;
                    izq = false;
                    der = false;
                    f.setImg(3 + 4*(f.getNumFantasma()-1));
                    break;
                }
            case 2:
                if (!der) {
                    arriba = false;
                    abajo = false;
                    izq = false;
                    der = true;
                    f.setImg(4*(f.getNumFantasma()-1));
                    break;
                }
            case 3:
                if (!izq) {
                    arriba = false;
                    abajo = false;
                    izq = true;
                    der = false;
                    f.setImg(1 + 4*(f.getNumFantasma()-1));
                    break;
                }
        }
    }

    public boolean hayColision(Pacman p) {
        return hayColisionIzq() || hayColisionDer() || hayColisionArriba() || hayColisionAbajo();
    }

    private boolean hayColisionArriba() {
        if (f.getPosicion().getX() == p.getPosicion().getX()) {
            if (f.getPosicion().getY() == p.getPosicion().getY() - 20) {
                return true;
            }
        }
        return false;
    }

    private boolean hayColisionAbajo() {
        if (f.getPosicion().getX() == p.getPosicion().getX()) {
            if (f.getPosicion().getY() == p.getPosicion().getY() + 20) {
                return true;
            }
        }
        return false;
    }

    private boolean hayColision() {
        return hayColisionAbajo() || hayColisionArriba() || hayColisionDer() || hayColisionIzq();
    }

    private boolean hayColisionIzq() {
        if (f.getPosicion().getY() == p.getPosicion().getY()) {
            if (f.getPosicion().getX() == p.getPosicion().getX() - 20) {
                return true;
            }
        }

        return false;
    }

    private boolean hayColisionDer() {
        if (f.getPosicion().getY() == p.getPosicion().getY()) {
            if (f.getPosicion().getX() == p.getPosicion().getX() + 20) {
                return true;
            }
        }

        return false;
    }

    public Timer getT() {
        return t;
    }
    public boolean isArriba() {
        return arriba;
    }

    public boolean isAbajo() {
        return abajo;
    }

    public boolean isIzq() {
        return izq;
    }

    public boolean isDer() {
        return der;
    }

    public void setP(Pacman p) {
        this.p = p;
    }

    private void retomarForma() {
        if (contadorDeReInicio >= 75) {
            f.cambiarFormaNoComible();
            contadorDeReInicio = 0;
        } else {
            contadorDeReInicio++;
        }
    }

    private void colisionar() {
        if(f.esComible()){
            choque2.reproducir("fantasmaMuerto.wav");
            p.comer(f.getValor());
            f.reiniciarFantasma();
            
        }else{
            if(p.tieneVidas()){
                choque1.reproducir("pacmanMuerto.wav");
                p.setImg(1);
                p.morir();
                p.reiniciarPacman();
                f.reiniciarFantasma();
            }
        }
    }

    public Pacman getP() {
        return p;
    }
    
    
}
