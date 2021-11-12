/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.epsevg.prop.lab.c4;

/**
 *
 * @author clast
 */
public class C4 
        implements Jugador, IAuto{
    
    private String nombre;
    private int turno;
    private Tauler estado_anterior;
    
    public String nom (){
        return nombre;
    }
    
    public C4 (){
        nombre = "C4";
        turno = 0;
    }
    
//    public int Moviment (Tauler t, int color){
//        int col;
//        if(turno >= 2){
//                col = (int)(t.getMida() * Math.random());
//                while (!t.movpossible(col)) {
//                    col = (int)(t.getMida() * Math.random());
//                    
//                }
//        }
//        else if (turno == 1){
//            col = t.getMida()/2 + 1;
//            ++turno;
//        }
//        else{
//            col = t.getMida()/2;
//            ++turno;
//        }
//        
//        return col;
//        
//    }
//    
    public int minimax (int i, Tauler t, int color, boolean seguir){
        Tauler actual = new Tauler (t);
        //boolean seguir = false;
        int col = (int)(t.getMida() * Math.random());
        if(t.movpossible(i)){
                //Full attack
                    actual = new Tauler(t);
                    actual.afegeix(i, color);
                    if (actual.solucio(i, color)){
                        seguir = false;
                        turno = 0;
                        col = i;
                    
                    }
                    //Full counter
                    else{
                        actual = new Tauler(t);
                        actual.afegeix(i, color*-1);
                        if(actual.solucio(i, color*-1)){
                            col = i;
                        }
                    }      
                }
        return col;
    }
    @Override
    public int moviment(Tauler t, int color) {
        //color 1 = rojo
        //color -1 = azul
        //comprueba primer turno
        if(color == 1){
            boolean acaba = false;
            for(int i = 0; i < t.getMida() && !acaba; ++i){
               if(t.getColor(0, i) != 0){
                   acaba = true;
               }
            }
            if(!acaba){
                turno = 0;
            }
        }
        else{
            int acaba = 0;
            for(int i = 0; i < 2 && acaba <= 1; ++i){
                for(int j = 0; j < t.getMida() && acaba <= 1; ++j){
                    if(t.getColor(0, i) != 0){
                    ++acaba;
                    }
                }
            }
            if(acaba <= 1){
                turno = 0;
            }
        }
        
        int col;
        boolean seguir = true;
        if(turno >= 2){
            Tauler actual = new Tauler (t);
            col = (int)(t.getMida() * Math.random());
            for (int i = 0; i < t.getMida() && seguir; i++) {
                col = minimax (i, t, color, seguir);
//                if(t.movpossible(i)){
//                //Full attack
//                    actual = new Tauler(t);
//                    actual.afegeix(i, color);
//                    if (actual.solucio(i, color)){
//                        seguir = false;
//                        turno = 0;
//                        col = i;
//                    
//                    }
//                    //Full counter
//                    else{
//                        actual = new Tauler(t);
//                        actual.afegeix(i, color*-1);
//                        if(actual.solucio(i, color*-1)){
//                            col = i;
//                        }
//                    }      
//                }
            } 
        }
        else if (turno == 1){
            col = t.getMida()/2 - 1;
            ++turno;
        }
        else{
            col = t.getMida()/2;
            ++turno;
        }
        estado_anterior = new Tauler(t);

        estado_anterior.pintaTaulerALaConsola();
        return col;
      
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
