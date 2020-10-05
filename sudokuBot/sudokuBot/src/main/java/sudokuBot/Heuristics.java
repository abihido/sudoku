
package sudokuBot;

import java.util.ArrayList;

public class Heuristics {
    
    public static final float DELTA=0.01f;
    public static final float[] PORCENTAJES_64=
    {100.0f,
    15.36f,
    20.0f,
    21.24f,
    20.24f,
    22.56f,
    23.96f,
    16.32f,
    26.639997f,
    24.24f};

    
    
    private static int value_64(float n){
        int i;
        for(i=0;i<10;i++){
            if(n>=PORCENTAJES_64[i]-DELTA && n<PORCENTAJES_64[i]+DELTA )break;
        }
        return i;
    }
    
    public static int getValueFromPercent(float n, int anchoCasilla){
        int valor;
        switch(anchoCasilla){
            case 64:{
                valor=value_64(n);
                break;
            }
            //aqui deberían ir las otras resoluciones
            default:{
                valor=0;
            }
        }
        return valor;
    }
}
