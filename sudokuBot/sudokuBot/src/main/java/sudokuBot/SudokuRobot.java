/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudokuBot;

import java.awt.event.KeyEvent;
import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.awt.event.InputEvent;

/**
 *
 * @author Danny
 */
public class SudokuRobot extends Robot{
    
    public static final int ALTO_CASILLA=64;
    public static final int ANCHO_CASILLA=64;
    public static final int ANCHO_BORDE=2;
    
    BufferedImage tablero;
    Dimension vistaGeneral;
    
    Point ultimaPos=new Point(0,0);
    public Point esquinaSupIzq,esquinaInfDer;
    Color pixelFocusedColor;
    int[] porcentajesDeColor;
    private boolean flagUP=false;
    private boolean flagDown=false;
    private boolean flagLeft=false;
    private boolean flagRight=false;
    boolean finalBorderHflag=false;
    public boolean focused=false;
    private Casilla[][] matriz;
    
    public SudokuRobot(Dimension screenSize) throws AWTException{
      
      esquinaSupIzq=new Point();
      esquinaInfDer=new Point();
      vistaGeneral=screenSize;
      matriz=new Casilla[9][9];
    }
    

    
    
    
    
    //inicializa y enfoca el tablero
    public void vistaInicial() {
        tablero= createScreenCapture(new Rectangle(vistaGeneral));
        ultimaPos=new Point(163*vistaGeneral.width/320, 2*vistaGeneral.height/3);
        pixelFocusedColor=new Color(tablero.getRGB(ultimaPos.x,ultimaPos.y));
    }
    
    private int fx(int a){
        int x;
        if(a<3){
            x=a*ANCHO_CASILLA;
        }else if(a>=3 && a<6){
            x=a*ANCHO_CASILLA+ANCHO_BORDE;
        }else{
            x=a*ANCHO_CASILLA+2*ANCHO_BORDE;
        }
        return x;
    }
    
     private int fy(int b){
        int y;
        if(b<3){
            y=b*ALTO_CASILLA;
        }else if(b>=3 && b<6){
            y=b*ALTO_CASILLA+ANCHO_BORDE;
        }else{
            y=b*ALTO_CASILLA+2*ANCHO_BORDE;
        }
        return y;
    }
    
    public boolean buildBoard(){
        
        for(int i=0;i<9;i++){
            int x=esquinaSupIzq.x+8+fx(i);
            for(int j=0;j<9;j++){
                int y=esquinaSupIzq.y+8+fy(j);
                matriz[i][j]=new Casilla(new Point(x,y),tablero.getSubimage(x, y, ANCHO_CASILLA ,ALTO_CASILLA));
                
                matriz[i][j].setValor(Heuristics.getValueFromPercent(matriz[i][j].porcentaje, ANCHO_CASILLA));
                System.out.println("casilla "+(i+1)+","+(j+1)+":"+matriz[i][j]);
            }
        }
        return true;
    }
    
    public String gimmeTheFuckingBoard(){
        String s="";
        for(int j=0;j<9;j++){
            for(int i=0;i<9;i++){
                s+=matriz[i][j].getValor()+(i%3==2?" ":"");
            }
            s+="\n";
        }
        return s;
    }
    
    private BufferedImage refrescarTablero(){
        tablero= createScreenCapture(new Rectangle(vistaGeneral));
        return tablero;
    }
    
    public Color getFocusingPixelColor(){
        return pixelFocusedColor;
    }
    
    
    private void checkAbove() {
        int j;
        for (j = ultimaPos.y; !flagUP && j > 0; j--) {
            int tempColor = tablero.getRGB(ultimaPos.x, j);
            flagUP |= tempColor == -16750951;
            //System.out.println("reading pixel up:("+i+","+ultimaPos.y+")"+tempColor);
        }
        if (flagUP) {
            System.out.println("upper border got at:" + (++j));
            esquinaSupIzq.y=j;
        }
    }
    
    private void checkBelow() {
        int j;
        for (j = ultimaPos.y; !flagDown && j < tablero.getHeight(); j++) {
            int tempColor = tablero.getRGB(ultimaPos.x, j);
            flagDown |= tempColor == -16750951;
            //System.out.println("reading pixel :("+i+","+ultimaPos.y+")"+tempColor);
        }

        if (flagDown) {
            System.out.println("lower border got at:" + (--j));
            esquinaInfDer.y=j;
        }
    }
    
    private void checkLeft(){
        int i;
        for (i = ultimaPos.x; !flagLeft && i > 0; i--) {
            int tempColor = tablero.getRGB(i, ultimaPos.y);
            flagLeft |= tempColor == -16750951;
            //System.out.println("reading pixel up:("+i+","+ultimaPos.y+")"+tempColor);
        }
        if (flagLeft) {
            System.out.println("left border got at:" + (++i));
            esquinaSupIzq.x=i;
        }
    }
    
    private void checkRight(){
        int i;
        for (i = ultimaPos.x; !flagRight && i < tablero.getWidth(); i++) {
            
            int tempColor = tablero.getRGB(i, ultimaPos.y);
            if(tempColor == -16750951 && tablero.getRGB(i+7, ultimaPos.y)==-16750951){
                flagRight=true;
            }
            
            //System.out.println("reading pixel :("+i+","+ultimaPos.y+")"+tempColor);
        }

        if (flagRight) {
            System.out.println("right border got at:" + (--i));
            esquinaInfDer.x=i;
        }
    }
    
    public boolean focus(int attemps){
        flagUP=flagDown=flagRight=flagLeft=focused=false;
        System.out.println("Attemping "+ attemps);
        refrescarTablero();
        mouseMove(ultimaPos.x, ultimaPos.y);
        pixelFocusedColor=new Color(tablero.getRGB(ultimaPos.x,ultimaPos.y));
      
       
       if(attemps>0)
        switch(pixelFocusedColor.getRGB()){
            
            //dentro del tablero
            case    -4387://vacia
            case    -26266://llena
            case    -6750208:{//borde
                
                checkAbove();
                checkBelow();
                checkLeft();
                checkRight();
                
               focused=flagUP&&flagDown; 
               
               if(!flagUP){
                   if(!flagDown){
                       System.out.println("no limits, zooming out");
                       keyPress(KeyEvent.VK_CONTROL);delay(50);
                        mouseWheel(11);delay(400);
                       keyRelease(KeyEvent.VK_CONTROL);delay(50);
                   }else{
                      System.out.println("Not upper border, scrolling up");
                      mouseWheel(-1);delay(400);
                   }
                
                
               }
               if(!flagDown){
                System.out.println("Not lower border, scrolling down");
                mouseWheel(1);delay(400);
               }
               break;
            }
            
            //fuera
            case    -16750951 :{//exterior
                
                break;
            }
            case    -1:{        //blanco de sudoku
                System.out.println("Not focused, scrolling down from white color");
                mouseWheel(1);
                delay(400);
                break;
            }
            default:{
                System.out.println("Not focused, scrolling up from diff color");
                mouseWheel(-10);
                delay(400);
                break;
            }
            
            
        }
        if(!focused && attemps>0)focus(--attemps);
       
        return focused;
    };
    
    public void click(int x, int y){
            int retry=0;
            mouseMove(x, y-10);
            Point location=MouseInfo.getPointerInfo().getLocation();
            while(location.x!=x && location.y!=(y-10) &&retry<400){
                //System.out.println((x-20)+","+(y-10));
                //System.out.println(location.x+","+location.y);
                //System.out.println("retry "+retry);
                mouseMove(x, y-10);
                location=MouseInfo.getPointerInfo().getLocation();
                delay(10);
                retry++;
            }
           delay(10);
           mousePress(InputEvent.BUTTON1_MASK);
           delay(10);
           mouseRelease(InputEvent.BUTTON1_MASK);
           delay(20);
            location=MouseInfo.getPointerInfo().getLocation();
            System.out.println("click on:"+location.x+","+location.y);
    }
    
    public String printEsquinas(){
        return "( "+esquinaSupIzq.x+"," +esquinaSupIzq.y+")"+" & ("+esquinaInfDer.x+","+esquinaInfDer.y+")";
    }

    public void move(Point p) {
        mouseMove(p.x, p.y);delay(50);
    }
    
}
