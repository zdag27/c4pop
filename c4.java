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
    
    public String nom (){
        return nombre;
    }
    
    public C4 (){
        nombre = "C4";
        turno = 0;
    }
    
    public int min(Tauler t, int color, int profundidad, int True_Color){
        int heuristica = 999999;
        for(int i = 0; i < t.getMida(); ++i){
            if(t.movpossible(i)){
                heuristica = Math.min (heuristica, minimax2(profundidad-1, t, color*-1, True_Color, i));
            }
        }
        return heuristica;
    }
    
    public int max(Tauler t, int color, int profundidad, int True_Color){
            int heuristica = -999999;  
                for(int i = 0; i < t.getMida(); ++i){
                    if(t.movpossible(i)){
                        int miniaux = minimax2(profundidad-1, t, color*-1, True_Color, i);
                        heuristica = Math.max (heuristica, miniaux );  
                        
                    }
                }
            return heuristica;
    }
    
    public int h(Tauler asme, Tauler asenemy, int color, int col){
        int heuristica = 0;
        int da=0;
        int de=0;
        int h=0;
        int v=0;
        int dae=0;
        int dee=0;
        int he=0;
        int ve=0;
        int maxdame=0;
        int maxdeme=0;
        int maxhme=0;
        int maxvme=0;
        int maxdahe=0;
        int maxdehe=0;
        int maxhhe=0;
        int maxvhe=0;  
        for (int i = 0; i < asme.getMida(); i++) {
            for (int j = 0; j < asme.getMida(); j++) {
                if(asme.getColor(i, j)==color){
                    if(i<asme.getMida()-1){
                        if(asme.getColor(i+1, j)==asme.getColor(i, j) ){
                            v+=1;
                        }else{
                            if(asme.getColor(i+1, j)!=0){
                                if(maxvme<v){
                                    maxvme=v;
                                }
                                v=0;
                            }
                        }
                    }
                    if(j<asme.getMida()-1){
                        if(asme.getColor(i, j+1)==asme.getColor(i, j) ){
                            h+=1;
                        }else{
                            if(asme.getColor(i, j+1)!=0){
                                if(maxhme<h){
                                    maxhme=h;
                                }
                                h=0;
                            }
                        }
                    }
                    if(i<asme.getMida()-1 && j<asme.getMida()-1){
                        if(asme.getColor(i+1, j+1)==asme.getColor(i, j) ){
                            da+=1;
                        }else{
                            if(asme.getColor(i+1, j+1)!=0){
                                if(maxdame<da){
                                    maxdame=da;
                                }
                                da=0;
                            }
                        }
                    }
                    if(i>0 && j>0){
                        if(asme.getColor(i-1, j-1)==asme.getColor(i, j) ){
                            de+=1;
                        }else{
                            if(asme.getColor(i-1, j-1)!=0){
                                if(maxdeme<de){
                                    maxdeme=de;
                                }
                                de=0;
                            }
                        }
                    }
                }
                else if(asme.getColor(i, j)==color*-1){
                    if(i<asme.getMida()-1){
                        if(asme.getColor(i+1, j)==asme.getColor(i, j) ){
                            ve+=1;
                        }else{
                            if(asme.getColor(i+1, j)!=0){
                                if(maxvhe<ve){
                                    maxvhe=ve;
                                }
                                ve=0;
                            }
                        }  
                    }
                    if(j<asme.getMida()-1){
                        if(asme.getColor(i, j+1)==asme.getColor(i, j) ){
                            he+=1;
                        }else{
                            if(asme.getColor(i, j+1)!=0){
                                if(maxhhe<he){
                                    maxhhe=he;
                                }
                                he=0;
                                }
                        }
                    }
                    if(i<asme.getMida()-1 && j<asme.getMida()-1){
                        if(asme.getColor(i+1, j+1)==asme.getColor(i, j) ){
                            dae+=1;
                        }else{
                            if(asme.getColor(i+1, j+1)!=0){
                                if(maxdahe<dae){
                                    maxdahe=dae;
                                }
                                dae=0;
                            }
                        }
                    }
                    if(i>0 && j>0){
                        if(asme.getColor(i-1, j-1)==asme.getColor(i, j) ){
                            dee+=1;
                        }else{
                            if(asme.getColor(i-1, j-1)!=0){
                                if(maxdehe<dee){
                                    maxdehe=dee;
                                }
                                dee=0;
                            }
                        }
                    }
                }
            } 
        }
        
        heuristica=(((maxdame+maxdeme+maxhme+maxvme) )) - (( 2*(maxdahe+maxdehe+maxhhe+maxvhe) ));
        if(asme.solucio(col, color)){
            heuristica += 100;
        }else{
            Tauler aux = new Tauler(asenemy);
            aux.afegeix(col, color*-1);
            if(aux.solucio(col, color*-1)){
                heuristica += 100;
            }else if(asme.movpossible(col)){
                Tauler aux2 = new Tauler(asme);
                aux2.afegeix(col, color*-1);
                if(aux2.solucio(col, color*-1)){
                    heuristica -=100;
                }
            }
        }
        return heuristica;
    }
    
    public int minimax2 (int profundidad, Tauler t, int color, int True_Color, int col){ 
        int heuristica = 0;
        Tauler asme = new Tauler (t);
        asme.afegeix(col, color);
        //System.out.println("profund"+profundidad);
        if(profundidad == 0 || asme.solucio(col, color) || !t.espotmoure()){
            //System.out.println("---------->"+profundidad+"->>>>>>>>>>>"+col+"--------------<");
            heuristica= h(asme,t,color,col);
            if(color!=True_Color)
                heuristica=-1*heuristica;
            //System.out.println("---------->"+heuristica);
        }else{
            if(True_Color != color){ //max
                heuristica= max(asme, color, profundidad, True_Color);
            }else{ //min
                heuristica= min(asme, color, profundidad, True_Color);
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
       int heuristica_aux=-999999;
       int heuristica = -999999;
       String aux="";
       String betters="";
       int col = (int)(t.getMida() * Math.random());
       //boolean be_aleatorian=((int)(t.getMida() * Math.random()))%2 == 0;
       for(int i = 0; i < t.getMida(); ++i){
           if(t.movpossible(i)){
                heuristica_aux = minimax2(profundidad-1,t , color, color, i);
                //System.out.println("->>>>>>>>>>>>>>>>>>>>>"+i);
                //System.out.println("->>>>>>>>>>>>>>>>>>>>>"+heuristica);
                //System.out.println("->>>>>>>>>>>>>>>>>>>>>"+heuristica_aux);
                aux+="."+heuristica_aux;
                if(heuristica_aux > heuristica){
                    heuristica = heuristica_aux;
                    betters="";
                    betters="."+col;
                    col = i;
                }
                if(heuristica_aux == heuristica){
                    betters="."+col;
                }
            }
        }
       String Rockstars[] = betters.split(".");
       int auximiliano=(int)((Rockstars.length-1) * Math.random());
       System.out.println(betters);
       System.out.println(auximiliano);
       System.out.println(aux);
      //System.out.println("->>>>>>>>>>>>>>>>>>>>>"+col);
      return col;
   }
    
    @Override
    public int moviment(Tauler t, int color) {
        //System.out.println("__________________________");
        //System.out.println("__________________________");
        //System.out.println("__________________________");//System.out.println("__________________________");//System.out.println("__________________________");//System.out.println("__________________________");
        primerturno(t,color);
        int col;
        if(turno >= 2){
            col=choosemove(t,color,8);
        }else{
            col=primeras2jugadas(t,color);
        }
        return col;
    }
}

        /*
            int heuristica = 0;
            Tauler auxi = new Tauler(t);
            Tauler auxi2 = new Tauler(t);
            Tauler aux=new Tauler(asenemy);
            if(aux.movpossible(col)){
                aux.afegeix(col, color*-1);
            }
            if(t.movpossible(col)){
                auxi.afegeix(col, color*-1);
                auxi2.afegeix(col, color);
            }
            if(aux.solucio(col, color*-1)){
                //aux.pintaTaulerALaConsola();
                heuristica=4;
                //System.out.println("-------------------------------->"+heuristica+"JJ");
            }else if(t.solucio(col, color)){
                //t.pintaTaulerALaConsola();
                heuristica = 3;
                //System.out.println("-------------------------------->"+heuristica+"JJ");
            }
            else if(t.solucio(col, color*-1)){
                //t.pintaTaulerALaConsola();
                heuristica = 2;
                //System.out.println("-------------------------------->"+heuristica+"JJ");
            }     
            else if(auxi.solucio(col, color*-1)){
                auxi.pintaTaulerALaConsola();
                heuristica = -3;
                //System.out.println("-------------------------------->"+heuristica+"JJ");
            }
            else if(auxi2.solucio(col, color)){
                auxi2.pintaTaulerALaConsola();
                heuristica = -2;
                //System.out.println("-------------------------------->"+heuristica+"JJ");
            }
//          else{
//CODIGO AL FINAL
//            }
        
        */

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
