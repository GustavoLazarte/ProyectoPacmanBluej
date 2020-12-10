/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

/**
 *
 * @author Miguel
 */
public class Puntuacion implements Comparable<Puntuacion> {

    private String nombre;
    private int puntos;

    public Puntuacion(String nombre, int puntos) {
        this.nombre = nombre;
        this.puntos = puntos;
    }

    public int compareTo(Puntuacion otra) {
        if (puntos > otra.getPuntos()) {
            return 1;
        } else if (puntos == otra.getPuntos()) {
            return 0;
        } else {
            return -1;
        }
    }

    public int getPuntos() {
        return puntos;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return "" + puntos;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    

}
