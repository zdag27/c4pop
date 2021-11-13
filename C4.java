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
    
    
    
    public int minimax2 (int profundidad, Tauler t, int color, int True_Color, int col){
        int heuristica = 0;
        if(profundidad == 0 || t.solucio(col, color) || t.solucio(col, color*-1) || !t.espotmoure()){
            if(t.solucio(col, color)){
                heuristica = 2;
            }
            else if(t.solucio(col, color*-1)){
                heuristica = 1;
            }
            else{
                heuristica = 0;
            }  
        }
        else{
            if(True_Color == color){ //max
                heuristica = -999999;
                for(int i = 0; i < t.getMida(); ++i){
                    Tauler aux = new Tauler (t);
                    aux.afegeix(i, color);
                    heuristica = Math.max (heuristica, minimax2(profundidad-1, t, color*-1, True_Color, i));  
                }
            }
            else{ //min
                heuristica = 999999;
                for(int i = 0; i < t.getMida(); ++i){
                    Tauler aux = new Tauler (t);
                    aux.afegeix(i, color);
                    heuristica = Math.min (heuristica, minimax2(profundidad-1, t, color*-1, True_Color, i));
                }
                
            }
        }
        return heuristica;
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
   
   public int minimax (Tauler t, int color, int profundidad, int ficha, int True_Color){
        int heuristica = 0;
        if(True_Color == color){
            heuristica = -999999;
        }
        else{
            heuristica = 999999;
        }
        Tauler aux = new Tauler (t);
        aux.afegeix(ficha, color);
        if(profundidad != 0){
            System.out.println("OOOOOOOOOOOOOOOOOOOO");
            if(aux.solucio(ficha, color)){
                heuristica = 1;
            }
            else if(aux.solucio(ficha, color*-1)){
                heuristica = 99999;
            }
            else{
                for(int i = 0; i < t.getMida(); ++i){
                    int heuristica_aux;
                    heuristica_aux = minimax(aux, color*-1, profundidad-1, i, color);
                    if(color == True_Color){
                       if(heuristica_aux > heuristica){
                        heuristica = heuristica_aux;
                        } 
                    }
                    else{
                      if(heuristica_aux < heuristica){
                        heuristica = heuristica_aux;
                        }  
                    }
                    
                }
            } 
        }
        else{
            System.out.println("=================");
             t.pintaTaulerALaConsola();
           if(aux.solucio(ficha, color)){
                heuristica = 1;  
           }
           else if(aux.solucio(ficha, color*-1)){
               heuristica = 99999;   
           }
       }
       if(color != True_Color){
           heuristica = heuristica * -1;
       }
      
       System.out.println("Heuristica" + heuristica);
       return heuristica;
   }
   
   public int choosemove(Tauler t, int color, int profundidad){
       int heuristica_aux;
       int heuristica = -999999;
       int col = (int)(t.getMida() * Math.random());
       for(int i = 0; i < t.getMida(); ++i){
           Tauler aux = new Tauler (t);
           aux.afegeix(i, color);
           heuristica_aux = minimax2(profundidad, aux, color, color, i);
           if(heuristica_aux > heuristica){
               heuristica = heuristica_aux;
               col = i;
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