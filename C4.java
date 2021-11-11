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
    

    @Override
    public int moviment(Tauler t, int color) {
        int col;
        
        if(turno >= 2){
            Tauler actual;
            col = -1;
            for (int i = 0; i < t.getMida(); i++) {
                actual = new Tauler(t);
                actual.afegeix(i, color);
                if (actual.solucio(i, color)){
                    col = i;
                }
            }
            if (col == -1){
                
                for (int i = 0; i < t.getMida(); i++) {
                    actual = new Tauler(t);
                    actual.afegeix(i, color*-1);
                    if (actual.solucio(i, color*-1)){
                        col = i;
                    }
                }
                if (col == -1){
                    col = (int)(t.getMida() * Math.random());
                }
            }  
        }
        else if (turno == 1){
            col = t.getMida()/2;
            ++turno;
        }
        else{
            col = t.getMida()/2 - 1;
            ++turno;
        }
        estado_anterior = new Tauler(t);

        estado_anterior.pintaTaulerALaConsola();
        return col;
      
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
