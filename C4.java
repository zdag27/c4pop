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
   
   public int[] BFS (Tauler t, int color, int profundidad, int ficha){
        int nombre_array[];
        nombre_array = new int[2];
        int heuristica = 1;
       if(profundidad != 0){
           Tauler aux = new Tauler (t);
           aux.afegeix(ficha, color);
           if(aux.solucio(ficha, color)){
             heuristica = 0;  
           }
           for(int i = 0; i < t.getMida(); ++i){
               BFS(aux, color*-1, profundidad-1, i);
           }
       }else{
           System.out.println("AAAAA");
       }
       System.out.println("-----------------------------------------");
       t.pintaTaulerALaConsola();
       nombre_array[1] = ficha;
       nombre_array[2] = heuristica;
       return nombre_array;
   }
   
   public int choosemove(Tauler t, int color, int profundidad){
       Tauler aux = new Tauler (t);
       for(int i = 0; i < t.getMida(); ++i){
               BFS(aux, color, profundidad-1, i);
           }
      return (int)(t.getMida() * Math.random());
   }
    
    @Override
    public int moviment(Tauler t, int color) {
        //color 1 = rojo
        //color -1 = azul
        //comprueba primer turno
        primerturno(t,color);
        
        int col;
        if(turno >= 2){
            col=choosemove(t,color,2);
        }else{
            col=primeras2jugadas(t);
        }
        //estado_anterior = new Tauler(t);

        //estado_anterior.pintaTaulerALaConsola();
        return col;
      
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}