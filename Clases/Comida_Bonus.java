package Clases;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.ImageIcon;

/**
 *
 * @author Miguel
 */
public class Comida_Bonus extends Comida {

    private static final int VALOR = 100;
    private boolean aparecer;
    private ImageIcon aux;

    public Comida_Bonus(ImageIcon sp) {        
        super(VALOR,sp);
        aparecer = false;
        ubic = new Ubicacion();
    }
    
    public Comida_Bonus(ImageIcon sp, ImageIcon aux) {        
        super(VALOR,sp);
        aparecer = false;
        ubic = new Ubicacion();
        this.aux = aux;
    }

    @Override
    void paint(Graphics g) {
        if(sprite != null && aparecer ){
            g.drawImage(sprite.getImage(),ubic.y, ubic.x, ancho, alto, null);
        }else{
            g.drawImage(aux.getImage(),ubic.y, ubic.x, ancho, alto, null);
        }
        
    }

    @Override
    void setUbicacion(int x, int y) {
        ubic.setUbicacion(x, y);
    }
    
    public void aparecer(){
        aparecer = true;
    }
    
    @Override
    public int getValor() {
        if(aparecer){
            return valor;
        }
        
        return 0;
    }

    public boolean isAparecer() {
        return aparecer;
    }
    
    
}
