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
    
        int AdyacenteVertical(Tauler t, int row, int col, int color, int nivel) {
 
	int consecutivas = 0;

	for (int i=row; i<t.getMida() && col<t.getMida() && col>=0; i++) {
		if (t.getColor(i,col) == color)
			consecutivas += 1;
		else
			break;
	}

	if (consecutivas >= nivel)
		return 1;
	else
		return 0;

}

int AdyacenteHorizontal(Tauler t, int row, int col, int color, int nivel) {
 
	int consecutivas = 0;

	for (int j=col; j<t.getMida() && row<t.getMida() && row>=0; j++) {
		if (t.getColor(row,j) == color)
			consecutivas += 1;
		else
			break;
	}

	if (consecutivas >= nivel)
		return 1;
	else
		return 0;

}

int AdyacenteDiagonal(Tauler t, int row, int col, int color, int nivel) {

	int total = 0;

	// Comprobamos los diagonales ascendentes
	double consecutivas = 0;

	int j = col;

	for (int i=row; i<t.getMida();i++) {
		if (j > t.getMida()-1) // Control para no pasarnos del maximo de columnas
			break;
		else if (t.getColor(i,j) == color)
			consecutivas += 1;
		else
			break;
		j+=1; // Incrementamos la columna cuando se incrementa la fila
		
	}

	if (consecutivas >= nivel)
		total += 1;

	// Comprobamos los diagonales descendentes
	consecutivas = 0;
	j = col;
	for (int i=row; i>=0; i--) {
		if (j > t.getMida()-1) // Control para no pasarnos del maximo de columnas
			break;
		else if (t.getColor(i,j) == color)
			consecutivas += 1;
		else
			break;
		j+=1; // Incrementamos la columna cuando se decrementa la fila

	}

	if (consecutivas >= nivel)
		total += 1;

	return total;

}


int ComprobarAdyacentes(Tauler t, int color, int nivel) {

	int count = 0;

	// Para cada ficha del tablero...
	for (int i=0; i<t.getMida();i++) { 
		for (int j=0; j<t.getMida();j++) { 

			// ...si es del color del jugador...
			if (t.getColor(i,j) == color) {
				// Comprueba los verticales de la columna
				count += AdyacenteVertical(t, i, j, color, nivel);
				
				// Comprueba los horizontales de la fila
				count += AdyacenteHorizontal(t, i, j, color, nivel);
				
				// Comprueba los diagonales (los dos tipos)
				count += AdyacenteDiagonal(t, i, j, color, nivel);
			}
		}
	}

	// Devolvemos la suma de los adyacentes encontrados para el "nivel"
	return count;

}


// Funcion heuristica (ESTA ES LA QUE TENEIS QUE MODIFICAR)
double Valoracion(Tauler t, int color){

	// La valoracion es un número resultante de la suma de:
	// total de 4 en raya * 1000000) + total de 3 en raya * 100 + total de 2 en raya * 10
	// al que le restamos:
	// total de 4 en raya del opuesto * 10000000, el total de 3 en raya del opuesto * 100 y el total de 2 en raya del opuesto * 10

	// Obtenemos el judador opuesto


	// Comprobamos los adyacentes del jugador actual
	int cuatros = ComprobarAdyacentes(t, color, 4);
	int treses =  ComprobarAdyacentes(t, color, 3);
	int doses =   ComprobarAdyacentes(t, color, 2);
	
	// Comprobamos los adyacentes del jugador opuesto
	int cuatros_opuesto = ComprobarAdyacentes(t, color*-1, 4);
	int treses_opuesto =  ComprobarAdyacentes(t, color*-1, 3);
	int doses_opuesto =   ComprobarAdyacentes(t, color*-1, 2);

	// Calculamos el valor heurístico del tablero
	//return (cuatros*100000 + treses*100 + doses*10);
	//return (cuatros*100000 + treses*100 + doses*10 + unos);
	return (((cuatros*1000000 + treses*100 + doses*10) - cuatros_opuesto*10000000) - treses_opuesto*100) - doses_opuesto*10;

    }

    public int h(Tauler asme, Tauler asenemy, int color, int col){
        return (int)Valoracion(asme,color);
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
    


    
//    public int minimaxAB (int profundidad, Tauler t, int color, int True_Color, int col, int alpha, int beta){
//
//        Tauler asme = new Tauler (t);
//        asme.afegeix(col, color);
//        if(profundidad == 0 || asme.solucio(col, color) || !t.espotmoure()) {
//            return h(asme, t, color, col);
//        }
//        if(color == True_Color){
//            for(int i = 0; i < t.getMida() && t.movpossible(i); ++i){
//                alpha = Math.max(alpha, minimaxAB(profundidad- 1, asme, color*-1, True_Color, i, alpha, beta));
//                if(alpha >= beta){
//                    return beta;
//                }
//            }
//            return alpha;
//        }
//        else{
//            for(int i = 0; i < t.getMida() && t.movpossible(i); ++i){
//                beta = Math.min(beta, minimaxAB(profundidad- 1, asme, color*-1, True_Color, i, alpha, beta));
//                if(alpha >= beta){
//                    return alpha;
//                }
//            }
//            return beta;
//        }
//}
    public int minimaxAB (int profundidad, Tauler t, int color, int True_Color, int col, int alpha, int beta){

  Tauler asme = new Tauler (t);
  asme.afegeix(col, color);
  if(profundidad == 0 || asme.solucio(col, color) || !t.espotmoure()) {
    return h(asme, t, color, col);
  }
  if (True_Color == color){// for Maximizer Player
    int heuristica_max = -999999;
    for(int i = 0; i < t.getMida(); ++i){
      if(t.movpossible(i)){  
        int eva = minimaxAB(profundidad - 1, t, color*-1, True_Color, i, alpha, beta);
        heuristica_max = Math.max (heuristica_max, eva);
        alpha = Math.max (alpha, heuristica_max);
        if (beta <= alpha){
          break;
        }
      }
    }
    return heuristica_max;
  }                      
  else{// for Minimizer player 
    int heuristica_min = 999999;
    for(int i = 0; i < t.getMida(); ++i){
      if(t.movpossible(i)){
        int eva = minimaxAB(profundidad - 1, t, color*-1, True_Color, i, alpha, beta);
        heuristica_min = Math.min (heuristica_min, eva);
        beta = Math.min(heuristica_min, eva);
        if (beta <= alpha){
          break;
        }
      }
    }
    return heuristica_min;
  }                                 
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
   
   public int choosemove(Tauler t, int color, int profundidad){
    if(false){
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
    }else{
    int alpha = -999999;
    int heuristica_max = alpha;
    int beta = 999999;
    String x ="";
    int True_Color = color;
    int col = (int)(t.getMida() * Math.random());
    for(int i = 0; i < t.getMida() ; ++i){
        if(t.movpossible(i)){  
            int eva = minimaxAB(profundidad - 1, t, color*-1, True_Color, i, alpha, beta);
            heuristica_max = Math.max (heuristica_max, eva);
            alpha = Math.max (alpha, heuristica_max);
            if (beta <= alpha){
                break;
            }
        }
    }
    
    System.out.println(x);
    return col;
    }
}

    
    @Override
    public int moviment(Tauler t, int color) {
        //System.out.println("__________________________");
        //System.out.println("__________________________");
        //System.out.println("__________________________");//System.out.println("__________________________");//System.out.println("__________________________");//System.out.println("__________________________");

        int col;
        int True_Color = color;
        //if(turno >= 2){
            col=choosemove(t,color,2);
//        }else{
//            col=primeras2jugadas(t,color);
//        }
        return col;
    }

}
