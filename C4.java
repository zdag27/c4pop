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
    
    public int minimax (int i, Tauler t, int color){
        Tauler actual = new Tauler (t);
        //boolean seguir = false;
        if(t.movpossible(i)){
                //Full attack
                    actual = new Tauler(t);
                    actual.afegeix(i, color);
                    if (actual.solucio(i, color)){
                        turno = 0;
                        return i;
                    
                    }
                    //Full counter
                    else{
                        actual = new Tauler(t);
                        actual.afegeix(i, color*-1);
                        if(actual.solucio(i, color*-1)){
                            return i;
                        }
                    }      
                }
        return -1;
    }
    
    
   public void primerturno(Tauler t, int color){
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
        }else{
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
   } 
    
   
   public int primeras2jugadas(Tauler t){
       ++turno;
        if (turno == 1){
            return t.getMida()/2 - 1;
        }
        else{
            return t.getMida()/2;
        }
   }
   
   public int choosemove(Tauler t, int color){
            int col=-1;
            for (int j = 0; j < 8; j++) {
                int aux=-1;
                for (int i = 0; i < t.getMida() && aux==-1; i++) {
                    aux = minimax (i, t, color);
                }
                if(aux==-1){
                    aux = (int)(t.getMida() * Math.random());
                    while (!t.movpossible(col)) {
                      aux = (int)(t.getMida() * Math.random());
                    }
                }
                if(j==0){
                    col=aux;
                }
            }
            return col;
   }
    
    @Override
    public int moviment(Tauler t, int color) {
        //color 1 = rojo
        //color -1 = azul
        //comprueba primer turno
        primerturno(t,color);
        
        int col;
        if(turno >= 2){
            col=choosemove(t,color);
        }else{
            col=primeras2jugadas(t);
        }
        estado_anterior = new Tauler(t);

        estado_anterior.pintaTaulerALaConsola();
        return col;
      
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
