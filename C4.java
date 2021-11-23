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
    private int profundidad_inicial;
    private boolean type_game;
    
/**
 * Esta funcion es llamada cada vez que inicia el turno del jugador, se encarga de retornar la columna con mejor heuristica, es decir, la jugada que mas le beneficie al jugador, llama por cada ficha a la funcion minimax que devolvera la heuristica de esta ficha
 * haciendo una recursividad entre la funcion minimax, min, max y como condicion final acabando en la funcion urgot.
* @param t 
* es el tauler actual
* @param color 
* es el color del jugador
* @return col 
* es la columna de mejor heuristica
*/    
    @Override
    public int moviment(Tauler t, int color) {
        int heuristica_aux=-999999;
        int heuristica = -999999;
        int alpha=-999999;
        int beta=999999;
        int col = (int)(t.getMida() * Math.random());
        String s="";
        if(type_game){
            for (int i = 0; i < t.getMida(); i++) { 
                if(t.movpossible(i)){
                    heuristica_aux = alphabeta (profundidad_inicial-1, t, color, color, i,alpha,beta);
                    if(heuristica_aux > heuristica){
                        heuristica = heuristica_aux;
                        col=i;
                    }
                    alpha = Math.max( alpha, heuristica);
                    if(beta <= alpha){
                        break;
                    }
                    s+="heuristica:"+heuristica_aux+" columna:"+i+" ";
                }
            }
        }else{
            for(int i = 0; i < t.getMida(); ++i){
               if(t.movpossible(i)){
                    heuristica_aux = minimax(profundidad_inicial-1,t , color, color, i);
                    if(heuristica_aux > heuristica){
                        heuristica = heuristica_aux;
                        col = i;
                    }
                    s+="heuristica:"+heuristica_aux+" columna:"+i+" ";
                }
            }
        }
        System.out.println(s);
        System.out.println("Jugada seleccionada heuristica:"+heuristica+" columna:"+col+" ");
        return col;
    }
    
/**
* Metodo publico que se usa para obtener el nombre de la ia en cuestion y asi el programa de juga2 pueda optenerlo y enviarlo por la interfaz grafica
* @return string nombre, retorna el nombre del programa o ia el cual es C4 por que el url pensamos que era una exageracion
*/
    public String nom (){
        return nombre;
    }
/**
* Metodo publico que se usa para inicializar el programa, genera el nombre y se guarda la profundidad a la que se desea jugar
* @param prof 
* parametro entero que a de ser un numero mayor o igual a 1 representa la profundidad maxima que revisara en el arbol (la usada contra el profe es el 8)
*/   
    public c4 (int prof,boolean b){
        nombre = "C4";
        profundidad_inicial=prof;
        type_game=b;
    }
    
/**
 * la funcion de minimax es la parte parametrica de nuestro algoritmo, se encarga de llamar a las funciones segun los casos correspondientes, llamando a max en el turno aliado, min en el turno enemigo y la heuristica (urgot)
 * y retorna la heuristiuca actual, Ademas de insertar la ficha en la columna deseada
* @param profundidad
* es la profundidad actual
* @param t
* es el tablero actual
* @param color
* es el color de la jugada que estamos investigando ahora
* @param True_Color
* es el color del jugador aliado
* @param col
* es la columna donde insertamos la ficha
* @return int heuristica
* retorna la heuristica de la jugada para la columna col
*/    
public int alphabeta (int profundidad, Tauler t, int color, int True_Color, int col,int alpha,int beta){
    int heuristica = 0;
    Tauler asme = new Tauler (t);
    asme.afegeix(col, color);
    if(profundidad == 0 || asme.solucio(col, color) || !asme.espotmoure()){
        heuristica= urgot(asme,t,color,col);
        if(color!=True_Color)
            heuristica=-1*heuristica;
    }else{
        if(True_Color != color){ //max
            heuristica = -999999;
            for (int i = 0; i < asme.getMida(); i++) { 
                if(asme.movpossible(i)){
                    heuristica = Math.max(heuristica, alphabeta (profundidad-1, asme, color*-1, True_Color, i,alpha,beta)); 
                    alpha = Math.max( alpha, heuristica);
                    if(beta <= alpha){
                        break;
                    }
                }
            }
        }else{ //min
            heuristica = 999999;
            for (int i = 0; i < asme.getMida(); i++) { 
                if(asme.movpossible(i)){
                    heuristica = Math.min(heuristica, alphabeta (profundidad-1, asme, color*-1, True_Color, i,alpha,beta)); 
                    beta = Math.min( beta, heuristica);
                    if(beta <= alpha){
                        break;
                    }
                }
            }
        }
    }
    return heuristica;
}
    
/**
 * la funcion de minimax es la parte parametrica de nuestro algoritmo, se encarga de llamar a las funciones segun los casos correspondientes, llamando a max en el turno aliado, min en el turno enemigo y la heuristica (urgot)
 * y retorna la heuristiuca actual, Ademas de insertar la ficha en la columna deseada
* @param profundidad
* es la profundidad actual
* @param t
* es el tablero actual
* @param color
* es el color de la jugada que estamos investigando ahora
* @param True_Color
* es el color del jugador aliado
* @param col
* es la columna donde insertamos la ficha
* @return int heuristica
* retorna la heuristica de la jugada para la columna col
*/    
    public int minimax (int profundidad, Tauler t, int color, int True_Color, int col){ 
        int heuristica = 0;
        Tauler asme = new Tauler (t);
        asme.afegeix(col, color);
        if(profundidad == 0 || asme.solucio(col, color) || !asme.espotmoure()){
            heuristica= urgot(asme,t,color,col);
            if(color!=True_Color)
                heuristica=-1*heuristica;
        }else{
            if(True_Color != color){ //max
                heuristica= max(asme, color, profundidad, True_Color);
            }else{ //min
                heuristica= min(asme, color, profundidad, True_Color);
            }
        } 
        return heuristica;
    }
    
    
    
/**
* se encarga de hacer el apartado min del algoritmo min y max, cuando estamos investigando las posibles jugadas del jugador enemigo buscamos la que nos de la menor heuristica (la cual es la que retornamos) iterativamente
* para cada ficha la funcion min y max y nos quedamos la menor heuristica que nos devuelva para retornarla la cual es la jugada que mas le perjudica al jugador aliado. Ademas es la encargada de indicar que se ha de decrementar la profundidad y cambiar el color
* @param t
* es el tablero actual a esta profundidad
* @param color
* es el color del jugador que estamos investigando
* @param profundidad
* es la profundidad actual
* @param True_Color 
* es el color del jugador aliado
* @return int heuristica
* es la heuristica de la peor jugada
*/     
    public int min(Tauler t, int color, int profundidad, int True_Color){
        int heuristica = 999999;
        for(int i = 0; i < t.getMida(); ++i){
            if(t.movpossible(i)){
                heuristica = Math.min (heuristica, minimax(profundidad-1, t, color*-1, True_Color, i));
            }
        }
        return heuristica;
    }
 /**
  * se encarga de hacer el apartado max del algoritmo min y max, cuando estamos investigando las posibles jugadas del jugador aliado buscamos la que nos de la mayor heuristica (la cual es la que retornamos) iterativamente
  * para cada ficha la funcion min y max y nos quedamos la mayor heuristica que nos devuelva para retornarla siendo la maxima el valor de la mejor jugada que podemos hacer. Ademas es la encargada de indicar que se ha de decrementar la profundidad y cambiar el color
* @param t
* es el tablero actual de la profundidad habiendo insertado la ficha
* @param color
* es el color actual
* @param profundidad
* es la profundidad actual
* @param True_Color
* es el color del jugador aliado
* @return int heuristica
* es la heuristica de la mejor jugada
*/    
    public int max(Tauler t, int color, int profundidad, int True_Color){
            int heuristica = -999999;  
                for(int i = 0; i < t.getMida(); ++i){
                    if(t.movpossible(i)){
                        int miniaux = minimax(profundidad-1, t, color*-1, True_Color, i);
                        heuristica = Math.max (heuristica, miniaux );  
                        
                    }
                }
            return heuristica;
    }
/**
 * la funcion urgot, llamada asi por el personaje de ficcion urgot del afamado juego league of legends, conciste en la acumulacion de casos favorables contra los no favorables.
 * la funcion devuelve 1 si se gana atraves de este camino, 0 si no se gana o no se pierde, -1 en el caso que poner esta ficha le da oportunidad de ganar al rival y un numero menor o igual a -1 si cabe la posibilidad de perder
 * siendo este numero el numero de formas en qe se perderia atravez de este camino
* @param asme 
* es el tablero suponiendo que se hizo la jugada que genero la condicion de finalizar
* @param asenemy 
* es el tablero suponiendo que el jugador alla decidido no jugar para ver si esta jugada evitaria que el gane
* @param color 
* es el color del jugador
* @param col 
* es la columna en la que se esta jugando en ese momento
* @return ((win-ifckit)-already_lose) 
* es la posibilidad de ganar, menos la posibilidad de generar una jugada que termine en victoria para el enemigo menos las posibilidades de derrota del tablero actual
*/  
    public int urgot(Tauler asme, Tauler asenemy, int color, int col){
        int win=0;
        int already_lose=0;
        int ifckit=0;
        Tauler auxme=new Tauler(asme);
        if(auxme.movpossible(col)){
            auxme.afegeix(col, color);
            if(auxme.solucio(col, color*-1)){
                ifckit=2;
            }
        }
        if(asme.solucio(col, color)){
            win=1;
        }
        for (int i = 0; i < asme.getMida(); i++) {
            if(asme.solucio(i, color*-1)){
                already_lose+=2;
            }
        }
        return (win-ifckit)-already_lose;
    }

}

//#############################################Heuristicas rechazadas###################################################################
// Maximas aliadas - maximas enemigas +(offset de jugada compremetedora+ victoria+derrota)
    /*public int h(Tauler asme, Tauler asenemy, int color, int col){
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
                            da+=2;
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
                            de+=2;
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
                            dae+=2;
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
                            dee+=2;
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
        
        heuristica=(((maxdame+maxdeme+maxhme+maxvme) )) - (( (maxdahe+maxdehe+maxhhe+maxvhe) ));
        if(asme.solucio(col, color)){
            heuristica += 100;
        }
        Tauler aux = new Tauler(asenemy);
        aux.afegeix(col, color*-1);
        if(aux.solucio(col, color*-1)){
            heuristica += 700;
        }else if(asme.movpossible(col)){
            Tauler aux2 = new Tauler(asme);
            aux2.afegeix(col, color*-1);
            if(aux2.solucio(col, color*-1)){
                heuristica -=600;
            }
        }
        return heuristica;
    }
    
    */

//###################################################Heuristica rechazada 2##############################################################
//el planteamiento consistia en revisar cuantas consecutivas existian y hacer una formula segun a importancia de cuantas consecutivas existian (este codigo no nos pertenece lo usamos como base para entender la heuristica en realidad es de un proyecto de c++ que se adapto a java)
//
//    int AdyacenteVertical(Tauler t, int row, int col, int color, int nivel) {
// 
//	int consecutivas = 0;
//
//	for (int i=row; i<t.getMida() && col<t.getMida() && col>=0; i++) {
//		if (t.getColor(i,col) == color)
//			consecutivas += 1;
//		else
//			break;
//	}
//
//	if (consecutivas >= nivel)
//		return 1;
//	else
//		return 0;
//
//}
//
//int AdyacenteHorizontal(Tauler t, int row, int col, int color, int nivel) {
// 
//	int consecutivas = 0;
//
//	for (int j=col; j<t.getMida() && row<t.getMida() && row>=0; j++) {
//		if (t.getColor(row,j) == color)
//			consecutivas += 1;
//		else
//			break;
//	}
//
//	if (consecutivas >= nivel)
//		return 1;
//	else
//		return 0;
//
//}
//
//int AdyacenteDiagonal(Tauler t, int row, int col, int color, int nivel) {
//
//	int total = 0;
//
//	// Comprobamos los diagonales ascendentes
//	double consecutivas = 0;
//
//	int j = col;
//
//	for (int i=row; i<t.getMida();i++) {
//		if (j > t.getMida()-1) // Control para no pasarnos del maximo de columnas
//			break;
//		else if (t.getColor(i,j) == color)
//			consecutivas += 1;
//		else
//			break;
//		j+=1; // Incrementamos la columna cuando se incrementa la fila
//		
//	}
//
//	if (consecutivas >= nivel)
//		total += 1;
//
//	// Comprobamos los diagonales descendentes
//	consecutivas = 0;
//	j = col;
//	for (int i=row; i>=0; i--) {
//		if (j > t.getMida()-1) // Control para no pasarnos del maximo de columnas
//			break;
//		else if (t.getColor(i,j) == color)
//			consecutivas += 1;
//		else
//			break;
//		j+=1; // Incrementamos la columna cuando se decrementa la fila
//
//	}
//
//	if (consecutivas >= nivel)
//		total += 1;
//
//	return total;
//
//}
//
//
//int ComprobarAdyacentes(Tauler t, int color, int nivel) {
//
//	int count = 0;
//
//	// Para cada ficha del tablero...
//	for (int i=0; i<t.getMida();i++) { 
//		for (int j=0; j<t.getMida();j++) { 
//
//			// ...si es del color del jugador...
//			if (t.getColor(i,j) == color) {
//				// Comprueba los verticales de la columna
//				count += AdyacenteVertical(t, i, j, color, nivel);
//				
//				// Comprueba los horizontales de la fila
//				count += AdyacenteHorizontal(t, i, j, color, nivel);
//				
//				// Comprueba los diagonales (los dos tipos)
//				count += AdyacenteDiagonal(t, i, j, color, nivel);
//			}
//		}
//	}
//
//	// Devolvemos la suma de los adyacentes encontrados para el "nivel"
//	return count;
//
//}
//
//
//double Valoracion(Tauler t, int color){
//
//	// La valoracion es un número resultante de la suma de:
//	// total de 4 en raya * 1000000) + total de 3 en raya * 100 + total de 2 en raya * 10
//	// al que le restamos:
//	// total de 4 en raya del opuesto * 10000000, el total de 3 en raya del opuesto * 100 y el total de 2 en raya del opuesto * 10
//
//	// Obtenemos el judador opuesto
//
//
//	// Comprobamos los adyacentes del jugador actual
//	int cuatros = ComprobarAdyacentes(t, color, 4);
//	int treses =  ComprobarAdyacentes(t, color, 3);
//	int doses =   ComprobarAdyacentes(t, color, 2);
//	
//	// Comprobamos los adyacentes del jugador opuesto
//	int cuatros_opuesto = ComprobarAdyacentes(t, color*-1, 4);
//	int treses_opuesto =  ComprobarAdyacentes(t, color*-1, 3);
//	int doses_opuesto =   ComprobarAdyacentes(t, color*-1, 2);
//
//	// Calculamos el valor heurístico del tablero
//	//return (cuatros*100000 + treses*100 + doses*10);
//	//return (cuatros*100000 + treses*100 + doses*10 + unos);
//	return (((cuatros*1000000 + treses*100 + doses*10) - cuatros_opuesto*10000000) - treses_opuesto*100) - doses_opuesto*10;
//
//}
//#################################################heristica rechazada 3###############################################################
//este planteamiento consistia en un sistema de casos favorables sumando la cantidad de fichas en el caso de que ninguno fuera favorable (ironicamente este que fue nuestro primer planteamiento se modifico un poco y termino realizando la heuristica ganadora que usamos al final)
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
//CODIGO AL en el siguiente comentario
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

//####################################Planteamientos de inicio de partida##########################################
// en teoria dominar el centro es dominar el juego,sin embargo, entendimos mas que era el centro del tablero y entendimos el centro de la primera fila
//como se planeaba priorizar eso pensabamos que por temas de rapidez efectuar estas jugadas sin importar la heuristica
//   public void primerturno(Tauler t, int color){
//         if(color == 1){
//            boolean acaba = false;
//            for(int i = 0; i < t.getMida() && !acaba; ++i){
//               if(t.getColor(0, i) != 0){
//                   acaba = true;
//               }
//            }
//            if(!acaba){
//                turno = 0;
//            }
//        }else{
//            int acaba = 0;
//            for(int i = 0; i < 2 && acaba <= 1; ++i){
//                for(int j = 0; j < t.getMida() && acaba <= 1; ++j){
//                    if(t.getColor(0, i) != 0){
//                        ++acaba;
//                    }
//                }
//            }
//            if(acaba <= 1){
//                turno = 0;
//            }
//        }
//   } 
//    
//   
//   public int primeras2jugadas(Tauler t,int color){
//       ++turno;
//        if (turno == 1){
//            Tauler aux=new Tauler(t);
//            aux.afegeix(t.getMida()/2, color);
//            if(aux.getColor(t.getMida()/2, 2)==color){
//                return t.getMida()/2;
//            }
//            return t.getMida()/2-1;
//        }
//        else{
//            return t.getMida()/2;
//        }
//   }
//####################################funcion auxiliar rechazada###########################################
//su unica funcion era facilitarnos el probar otra heuristica siendo lo de adentro la heuristica que estaba siendo probada actualmente (dentro de esta funcion donde esta urgot han estado todas las demas heuristicas antes de el)
//    public int h(Tauler asme, Tauler asenemy, int color, int col){
//        return urgot(asme, asenemy, color, col);
//    }
