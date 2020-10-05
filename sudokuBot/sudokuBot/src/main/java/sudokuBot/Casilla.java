package sudokuBot;

import java.awt.Point;
import java.awt.image.BufferedImage;



public class Casilla {
    private Point p;
    private int valor;
    public float porcentaje;
    private boolean ocupada;
    BufferedImage view;
    
    public Casilla(Point p, BufferedImage view) {
        this.p = p;
        this.view = view;
        this.valor=0;
        this.ocupada=false;
        //reading view to get percentage of ocupation, then decide what number it is
        int counter = 0;
        for (int i = 7; i < view.getWidth() - 7; i++) {
            for (int j = 7; j < view.getHeight() - 7; j++) {
                if (view.getRGB(i, j) != -26266) {
                    counter++;
                    //System.out.println(i + "," + j + ":" + view.getRGB(i, j));
                }
            }
        }
        porcentaje = (float) counter / (view.getWidth() - 14) / (view.getHeight() - 14) * 100;

    }
    
    
    
    public void setView(BufferedImage img){
        this.view=img;
    }
    
    public BufferedImage getView(){
        return view;
    }
    
    public Point getUbicacion() {
        return p;
    }

    public void setUbicacion(Point ubicacion) {
        this.p = ubicacion;
    }
    
    public void setValor(int n){
        if(n!=0)ocupada=true;
        this.valor=n;
    }
    
    public String getValor(){
        return ""+(valor!=0?valor:"X");
    }
    
    public void setOcupada(){
        ocupada=true;
    }
    
    public boolean isOcupada(){
        return ocupada;
    }
    
    @Override
    public String toString(){
        return "("+p.x+","+p.y+"),"+valor+((ocupada?",llena":",disponible"));
    }
}
