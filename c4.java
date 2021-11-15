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
public class c4 
        implements Jugador, IAuto{
    
    private String nombre;
    private int turno;
    
    public String nom (){
        return nombre;
    }
    
    public c4 (){
        nombre = "C4";
        turno = 0;
    }
    
    public int evita3 (Tauler t, int i, int j){
        int heuristica = 0;
        boolean esquinas = false;
        esquinas = (i == 1 || i == t.getMida()-2 || j == 1 || j == t.getMida()-2); 
        if(t.getColor(i, j) == t.getColor(i+1, j) && (t.getColor(i+2, j) == 0) && (t.getColor(i+2, j-1) != 0) && t.getColor(i, j) != 0){ //horizontal derecha
            heuristica = 1 * t.getColor(i, j);
        }
        else if(t.getColor(i, j) == t.getColor(i-1, j) && (t.getColor(i-2, j) == 0) && (t.getColor(i-2, j-1) != 0) && t.getColor(i, j) != 0){ //horizontal izquierda
            heuristica = 1 * t.getColor(i, j);          
        }
        else if(t.getColor(i, j) == t.getColor(i, j+1) && (t.getColor(i, j+2) == 0) && (t.getColor(i, j+1) != 0) && t.getColor(i, j) != 0){ //vertical arriba
            heuristica = 1 * t.getColor(i, j);
        }
        else if(t.getColor(i, j) == t.getColor(i, j-1) && (t.getColor(i, j-2) == 0) && t.getColor(i, j) != 0){ //vertical abajo
            heuristica = 1 * t.getColor(i, j);
        }
        else if(t.getColor(i, j) == t.getColor(i-1, j-1) && (t.getColor(i-2, j-2) == 0) && t.getColor(i, j) != 0){//diagonal izquierda abajo
            heuristica = 1 * t.getColor(i, j);
        }
        else if(t.getColor(i, j) == t.getColor(i+1, j+1) && (t.getColor(i+2, j+2) == 0) && (t.getColor(i+2, j+1) != 0) && t.getColor(i, j) != 0){//diagonal derecha arriba
            heuristica = 1 * t.getColor(i, j);
        }
        else if(t.getColor(i, j) == t.getColor(i-1, j+1) && (t.getColor(i-2, j+2) == 0) && (t.getColor(i-2, j+1) != 0) && t.getColor(i, j) != 0){ //diagonal izquierda arriba
            heuristica = 1 * t.getColor(i, j);
        }
        else if(t.getColor(i, j) == t.getColor(i+1, j-1) && (t.getColor(i+2, j-2) == 0) && t.getColor(i, j) != 0){ //diagonal derecha abajo
            heuristica = 1 * t.getColor(i, j);
        }
        
        
        
        else if(t.getColor(i+2, j) == t.getColor(i+1, j) && (t.getColor(i, j) == 0) && t.getColor(i, j-1)!=0 /* && esquinas*/ && t.getColor(i+2, j) != 0){ //horizontal derecha
            heuristica = 1 * t.getColor(i, j);
        }
        else if(t.getColor(i-2, j) == t.getColor(i-1, j) && (t.getColor(i, j) == 0) && t.getColor(i, j-1)!=0 /* && esquinas*/ && t.getColor(i-2, j) != 0){ //horizontal izquierda
            heuristica = 1 * t.getColor(i, j);
        }
        else if(t.getColor(i, j+2) == t.getColor(i, j+1) && (t.getColor(i, j) == 0) && t.getColor(i, j-1)!=0 /* && esquinas*/ && t.getColor(i, j+2) != 0){ //vertical arriba
            heuristica = 1 * t.getColor(i, j);
        }
        else if(t.getColor(i, j-2) == t.getColor(i, j-1) && (t.getColor(i, j) == 0) && t.getColor(i, j-1)!=0 /* && esquinas*/ && t.getColor(i, j-2) != 0){ //vertical abajo
            heuristica = 1 * t.getColor(i, j);
        }
        else if(t.getColor(i-2, j-2) == t.getColor(i-1, j-1) && (t.getColor(i, j) == 0) && t.getColor(i, j-1)!=0 /* && esquinas*/ && t.getColor(i-2, j-2) != 0){//diagonal izquierda abajo
            heuristica = 1 * t.getColor(i, j);
        }
        else if(t.getColor(i+2, j+2) == t.getColor(i+1, j+1) && (t.getColor(i, j) == 0) && t.getColor(i, j-1)!=0 /* && esquinas*/ && t.getColor(i+2, j+2) != 0){//diagonal derecha arriba
            heuristica = 1 * t.getColor(i, j);
        }
        else if(t.getColor(i-2, j+2) == t.getColor(i-1, j+1) && (t.getColor(i, j) == 0) && t.getColor(i, j-1)!=0 /* && esquinas*/ && t.getColor(i-2, j+2) != 0){ //diagonal izquierda arriba
            heuristica = 1 * t.getColor(i, j);
        }
        else if(t.getColor(i+2, j-2) == t.getColor(i+1, j-1) && (t.getColor(i, j) == 0) && t.getColor(i, j-1)!=0 /* && esquinas*/ && t.getColor(i+2, j-2) != 0){ //diagonal derecha abajo
            heuristica = 1 * t.getColor(i, j);
        }
        return heuristica;
    }
    
    public int min(Tauler t,Tauler asenemy, int color, int profundidad, int True_Color){
        int heuristica = 999999;
        for(int i = 0; i < t.getMida(); ++i){
            if(t.movpossible(i)){
                Tauler aux = new Tauler (t);
                aux.afegeix(i, color);
                asenemy = new Tauler (t);
                heuristica = Math.min (heuristica, minimax2(profundidad-1, t, asenemy, color*-1, True_Color, i));
            }
        }
        return heuristica;
    }
    
    public int max(Tauler t,Tauler asenemy, int color, int profundidad, int True_Color){
            int heuristica = -999999;  
                for(int i = 0; i < t.getMida(); ++i){
                    if(t.movpossible(i)){
                        Tauler aux = new Tauler (t);
                        aux.afegeix(i, color);
                        asenemy = new Tauler (t);
                        int miniaux = minimax2(profundidad-1, t,asenemy, color*-1, True_Color, i);
                        heuristica = Math.max (heuristica, miniaux );  
                        
                    }
                }
            return heuristica;
    }
    
    public int h(Tauler t,int color, int col){
            int heuristica = 0;
            Tauler auxi = new Tauler(t);
            Tauler auxi2 = new Tauler(t);
            auxi.afegeix(col, color*-1);
            auxi2.afegeix(col, color);
            if(t.solucio(col, color)){
                heuristica = 3;
            }
            else if(t.solucio(col, color*-1)){
                heuristica = 2;
            }     
            else if(auxi.solucio(col, color*-1)){
                heuristica = -3;
            }
            else if(auxi2.solucio(col, color)){
                heuristica = -2;
            }
//          else{
//CODIGO AL FINAL
//            }
            return heuristica;
    }
    
    public int minimax2 (int profundidad, Tauler t,Tauler asenemy, int color, int True_Color, int col){
        int heuristica = 0;
        if(profundidad == 0 || t.solucio(col, color) || t.solucio(col, color*-1) || !t.espotmoure()){
            heuristica= h(t,color,col);
        }else{
            if(True_Color == color){ //max
                heuristica= max(t,asenemy, color, profundidad, True_Color);
            }else{ //min
                heuristica= min(t,asenemy, color, profundidad, True_Color);
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
    
   
   public int primeras2jugadas(Tauler t,int color){
       ++turno;
        if (turno == 1){
            Tauler aux=new Tauler(t);
            aux.afegeix(t.getMida()/2, color);
            if(aux.getColor(t.getMida()/2, 2)==color){
                return t.getMida()/2;
            }
            return t.getMida()/2-1;
        }
        else{
            return t.getMida()/2;
        }
   }
   
   
   public int choosemove(Tauler t, int color, int profundidad){
       int heuristica_aux;
       int heuristica = -999999;
       int col = (int)(t.getMida() * Math.random());
       boolean be_aleatorian=((int)(t.getMida() * Math.random()))%2 == 0;
       for(int i = 0; i < t.getMida(); ++i){
           if(t.movpossible(i)){
                Tauler asme = new Tauler (t);
                asme.afegeix(i, color);
                Tauler asenemy = new Tauler (t);
                heuristica_aux = minimax2(profundidad, asme,asenemy, color, color, i);
                if(heuristica_aux > heuristica){
                    heuristica = heuristica_aux;
                    col = i;
                }
                if(be_aleatorian && heuristica_aux == heuristica){
                    be_aleatorian=false;
                    col = i;
                }
            }
        }
      return col;
   }
    
    @Override
    public int moviment(Tauler t, int color) {
        primerturno(t,color);
        int col;
        if(turno >= 2){
            col=choosemove(t,color,2);
        }else{
            col=primeras2jugadas(t,color);
        }
        return col;
    }
}

//                heuristica = 0;
//                int auxii_heu;
//                int cont = 0;
//                for(int i = 2; i < t.getMida()- 2 && cont < t.getMida(); i+=2){
//                    cont = 0;
//                    for(int j = 2; j < t.getMida()- 2 && cont < t.getMida(); j+=2){
//                        if(t.getColor(i, j) == t.getColor(i+1, j) && (t.getColor(i+2, j) == 0) && t.getColor(i, j) != 0){ //horizontal derecha
//                            cont+=2;
//                        }
//                        else if(t.getColor(i, j) == t.getColor(i-1, j) && (t.getColor(i-2, j) == 0) && t.getColor(i, j) != 0){ //horizontal izquierda
//                            cont+=2;
//                        }
//                        else if(t.getColor(i+2, j) == t.getColor(i+1, j) && (t.getColor(i, j) == 0) /* && esquinas*/ && t.getColor(i+2, j) != 0){ //horizontal derecha
//                            cont+=2;
//                        }
//                        else if(t.getColor(i-2, j) == t.getColor(i-1, j) && (t.getColor(i, j) == 0) /* && esquinas*/ && t.getColor(i-2, j) != 0){ //horizontal izquierda
//                            cont+=2;
//                        }
//                        t.getColor(i, j);
//                        auxii_heu = evita3 (t, i, j);
//                        if(heuristica == 0){
//                            heuristica = auxii_heu;
//                        }
//                        else if((int)(t.getMida() * Math.random())%2 == 0 && auxii_heu != 0){
//                            heuristica = auxii_heu;
//                        }
//                    }
//                }
