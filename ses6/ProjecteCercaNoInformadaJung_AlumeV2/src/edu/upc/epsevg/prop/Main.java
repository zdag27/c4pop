package edu.upc.epsevg.prop;

import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.TreeLayout;
import edu.uci.ics.jung.graph.DelegateTree;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.GraphZoomScrollPane;
import edu.uci.ics.jung.visualization.Layer;
import edu.uci.ics.jung.visualization.control.CrossoverScalingControl;
import edu.uci.ics.jung.visualization.control.ScalingControl;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Bernat
 */
public class Main {

    public static void main(String[] args) {
        
        // puzzle amb solució de 5 moviments
        NinePuzzle start = new NinePuzzle(new int[]{1, 2, 3,
                                                    4, 5, 0,
                                                    6, 7, 8});

        NinePuzzle goal = new NinePuzzle(new int[]{ 1, 3, 5,
                                                    4, 2, 8,
                                                    6, 7, 0});

        // puzzle amb solució de 31 moviments
        /*
        NinePuzzle start = new NinePuzzle(new int[]{    8, 6, 7, 
                                                        2, 5, 4, 
                                                        3, 0, 1}); 
        NinePuzzle goal = new NinePuzzle(new int[]{     1, 2, 3, 
                                                        4, 5, 6, 
                                                        7, 8, 0});
         */
        
        // Executem la cerca
        ResultatCerca rc = cercaInformadaA(start, goal, 2);
        // Mostra el resultat gràficament
        Visualizacio.showResultatCerca(rc);
    }

    /**
     * Implementació de BFS (Breadth-first Search) (@TODO: A implementar)
     * 
     * @param start node origen
     * @param goal node destí o solució
     * @param max_depth profunditat màxima permesa
     * @return el resultat de la cerca o null si no ha trobat resultat.
     */
    private static ResultatCerca cercaNoInformadaBFS(NinePuzzle start, NinePuzzle goal, int max_depth) {
        DelegateTree<NinePuzzle, Integer> g = new DelegateTree<>();
        g.addVertex(start);
        NinePuzzle actual= (start);
        boolean b=true;
        int hijo_actual=0;
        List <NinePuzzle> LNO=new ArrayList<NinePuzzle>();
        List <NinePuzzle> LNT=new ArrayList<NinePuzzle>();
        LNO.add(actual);
        int offsett=0;
        int actualdepth=0;
        List<Dir> movis=actual.validMoves();
        NinePuzzle fin=null;
        if(movis.size()>0){
            fin= new NinePuzzle(actual);
            fin.move(movis.get(movis.size()-1));
        }
        while(LNO.size()>0 && !goal.comparaEstat(actual)){
            actual=LNO.get(0);
            LNO.remove(actual);
            boolean auxbolean=false;
            int auxit5=0;
            if(!goal.comparaEstat(actual)){
                while(auxit5<LNT.size() && !auxbolean){
                    if(LNT.get(auxit5).comparaEstat(actual))
                        auxbolean=true;
                    auxit5++;
                }
                if(!auxbolean){
                    LNT.add(actual);
                    movis=actual.validMoves();
                    for (int i = 0; i < movis.size(); i++) {
                        NinePuzzle aux= new NinePuzzle(actual);
                        aux.move(movis.get(i));
                        if(g.getDepth(actual)<max_depth){
                            LNO.add(aux);
                        }
                        System.out.println(g.getDepth(actual));
                        System.out.println(movis.get(i));
                        System.out.println(actual);
                        System.out.println(i);
                        g.addChild(actualdepth, actual, aux);
                        actualdepth++;
                    }
                }
            }
        }
        if(goal.comparaEstat(actual)){
            return new ResultatCerca(g, actual);
        }else
            return new ResultatCerca(g, null);
        
        //----------------------------------- CODI D'EXEMPLE A ESBORRAR --------------------------------
        // Creació inicial del graf
//        DelegateTree<NinePuzzle, Integer> g = new DelegateTree<>();
//        // afegim el node arrel
//        g.addVertex(start);
//
//        // Simulem dos moviments a partir de l'inici
//        NinePuzzle up = new NinePuzzle(start);
//        up.move(Dir.UP);
//        NinePuzzle down = new NinePuzzle(start);
//        down.move(Dir.DOWN);
//        // afegim els nodes (vertex) al graf. El primer nombre és l'identificador de l'aresta (edge), que ha de ser únic.
//        g.addChild(1, start, up);
//        g.addChild(2, start, down);
//
//        // creem un segon nivell de moviment (a partir de down)
//        NinePuzzle down_left = new NinePuzzle(down);
//        down_left.move(Dir.LEFT);
//        g.addChild(3, down, down_left);
//
//       
//        
//        // retornem el graf construit, dient que down_left és la solució (MENTIDA!!!)
//        return new ResultatCerca(g, down_left);
        
         //----------------------------------- FI CODI D'EXEMPLE A ESBORRAR --------------------------------
    }
    /**
     * Implementació de DFS (Depth-First Search) (@TODO: A implementar)
     * 
     * @param start node origen
     * @param goal node destí o solució
     * @param max_depth profunditat màxima permesa
     * @return el resultat de la cerca o null si no ha trobat resultat.
     */
    private static ResultatCerca cercaNoInformadaDFS(NinePuzzle start, NinePuzzle goal, int max_depth) {
        // @TODO: A implementar
        DelegateTree<NinePuzzle, Integer> g = new DelegateTree<>();
        List<NinePuzzle>L=new ArrayList<NinePuzzle>();
        L=cercaNoInformadaDFS(start, goal, max_depth,L);
        NinePuzzle result=null;
        g.addVertex(L.get(0));
        for (int i = 0; i < L.size()-1; i++) {
            g.addChild(i, L.get(i), L.get(i+1));
        }
        if(L.size()>1)
            result=L.get(L.size()-1);
            return new ResultatCerca(g, result);
    }
    private static List<NinePuzzle> cercaNoInformadaDFS(NinePuzzle start, NinePuzzle goal, int max_depth,List<NinePuzzle> L){
        if(max_depth!=0){
            System.out.println("AAAAAAAA");
            if(!goal.comparaEstat(start) || start!=null){
                List<Dir>movis=start.validMoves();
                for (int i = 0; i < movis.size(); i++) {
                    NinePuzzle aux= new NinePuzzle(start);
                    aux.move(movis.get(i));
                    L=cercaNoInformadaDFS(aux, goal, max_depth-1,L);
                    if(!L.isEmpty()){
                        L.add(0, start);
                        i=movis.size();
                    }
                }
            }
            if(goal.comparaEstat(start)){
                System.out.println("BB");
                L.add(start);
            }
        }
        return L;
    }
    /**
     * Implementació de IDS (Iterative Deepening depth-first Search) (@TODO: A implementar)
     * 
     * @param start node origen
     * @param goal node destí o solució
     * @param max_depth profunditat màxima permesa
     * @return el resultat de la cerca o null si no ha trobat resultat.
     */
    private static ResultatCerca cercaNoInformadaIDS(NinePuzzle start, NinePuzzle goal, int max_depth) {
       // @TODO: A implementar
        DelegateTree<NinePuzzle, Integer> g = new DelegateTree<>();
        List<NinePuzzle>L=new ArrayList<NinePuzzle>();
        NinePuzzle result=null;
        for (int i = 1; i < max_depth && L.isEmpty(); i++) {
            L=cercaNoInformadaDFS(start, goal, i,L);
        }
        if(!L.isEmpty()){
            g.addVertex(L.get(0));
            for (int j = 0; j < L.size()-1; j++) {
                g.addChild(j, L.get(j), L.get(j+1));
            }
        }
        if(L.size()>1)
            result=L.get(L.size()-1);
        else
            g.addVertex(start);

        return new ResultatCerca(g, result);
    }
    private static ResultatCerca cercaInformadaHC(NinePuzzle start, NinePuzzle goal, int max_depth){
        DelegateTree<NinePuzzle, Integer> g = new DelegateTree<>();
        g.addVertex(start);
        NinePuzzle actual= (start);
        NinePuzzle result=null;
        List <NinePuzzle> LNO=new ArrayList<NinePuzzle>();
        List <NinePuzzle> LNT=new ArrayList<NinePuzzle>();
        LNO.add(actual);
        for (int i = 0; i < max_depth && !actual.comparaEstat(goal); i++) {
            LNT.add(actual);
            LNO.remove(actual);
            List<Dir> movis=actual.validMoves();
            if(movis.isEmpty()){
                i=max_depth;
            }else{
                int be=8,bp=-1;
                for (int j = 0; j < movis.size(); j++) {
                    NinePuzzle auxpuzzle= new NinePuzzle(actual);
                    auxpuzzle.move(movis.get(j));
                    if(!ciclo(LNT,auxpuzzle)){
                        int auxeur= goal.heuristica(auxpuzzle);
                        if(auxeur<=be){
                            be=auxeur;
                            bp=j;
                        }
                    }
                }
                if(bp>-1){
                    NinePuzzle auxpuzzle= new NinePuzzle(actual);
                    auxpuzzle.move(movis.get(bp));
                    LNO.add(0, auxpuzzle);
                    g.addChild(i, actual, auxpuzzle);
                    actual=auxpuzzle;
                }else{
                    i=max_depth;
                    System.out.println("f");
                }
            }
        }
        if(actual.comparaEstat(goal)){
            return new ResultatCerca(g, actual);
        }
        return new ResultatCerca(g, result);
    }
    private static ResultatCerca cercaInformadaBS(NinePuzzle start, NinePuzzle goal, int max_depth){
        DelegateTree<NinePuzzle, Integer> g = new DelegateTree<>();
        g.addVertex(start);
        NinePuzzle actual= (start);
        NinePuzzle result=null;
        List <NinePuzzle> LNO=new ArrayList<NinePuzzle>();
        List <NinePuzzle> LNT=new ArrayList<NinePuzzle>();
        LNO.add(actual);
        int paso=0;
        while(LNO.size()>0 && !actual.comparaEstat(goal)){
            actual=LNO.get(0);
            LNT.add(actual);
            LNO.remove(actual);
            List<Dir> movis=actual.validMoves();
            if(!movis.isEmpty()){
                for (int i = 0; i < movis.size(); i++) {
                    NinePuzzle auxpuzzle= new NinePuzzle(actual);
                    auxpuzzle.move(movis.get(i));
                    if(!ciclo(LNT,auxpuzzle)){
                        organizeLNO(LNO,auxpuzzle,goal,max_depth);
                        if(LNO.contains(auxpuzzle)){
                            g.addChild(paso, actual, auxpuzzle);
                            ++paso;
                        }
                    }
                }
            }
        }
        if(actual.comparaEstat(goal))
            return new ResultatCerca(g, actual);
        return new ResultatCerca(g, result);
    }
    private static ResultatCerca cercaInformadaA(NinePuzzle start, NinePuzzle goal, int max_depth){
        DelegateTree<NinePuzzle, Integer> g = new DelegateTree<>();
        g.addVertex(start);
        NinePuzzle actual= (start);
        NinePuzzle result=null;
        List <NinePuzzle> LNO=new ArrayList<NinePuzzle>();
        List <NinePuzzle> LNT=new ArrayList<NinePuzzle>();
        LNO.add(actual);
        int paso=0;
        while(LNO.size()>0 && !actual.comparaEstat(goal)){
            actual=LNO.get(0);
            LNT.add(actual);
            LNO.remove(actual);
            List<Dir> movis=actual.validMoves();
            if(!movis.isEmpty()){
                for (int i = 0; i < movis.size(); i++) {
                    NinePuzzle auxpuzzle= new NinePuzzle(actual);
                    auxpuzzle.move(movis.get(i));
                    if(!ciclo(LNT,auxpuzzle)){
                        LNO=organizeLNOA(LNO,auxpuzzle,goal);
                        if(LNO.contains(auxpuzzle)){
                            g.addChild(paso, actual, auxpuzzle);
                            ++paso;
                        }
                    }
                }
            }
        }
        if(actual.comparaEstat(goal))
            return new ResultatCerca(g, actual);
        return new ResultatCerca(g, result);
    }
private static List <NinePuzzle> organizeLNO(List <NinePuzzle> LNO,NinePuzzle actual,NinePuzzle goal,int N){
    int i =0;
    int eua=goal.heuristica(actual);
    boolean stop=true;
    while(i<LNO.size() && stop){
        int eui=goal.heuristica(LNO.get(i));
        if(eua<eui){
            stop=false;
            --i;
        }
        ++i;
    }
    LNO.add(i, actual);
    while(LNO.size()>N){
        LNO.remove(LNO.size()-1);
    }
    return LNO;
}

private static List <NinePuzzle> organizeLNOA(List <NinePuzzle> LNO,NinePuzzle actual,NinePuzzle goal){
    int i =0;
    int eua=goal.heuristica(actual)+actual.getDepth();
    boolean stop=true;
    while(i<LNO.size() && stop){
        int eui=goal.heuristica(LNO.get(i))+LNO.get(i).getDepth();
        if(eua<eui){
            stop=false;
            --i;
        }
        ++i;
    }
    LNO.add(i, actual);
    return LNO;
}

//true =es ciclo
    private static boolean ciclo(List <NinePuzzle> LNT,NinePuzzle actual){
        boolean aux=false;
        for (int j = 0; j < LNT.size() && !aux; j++) {
            if(LNT.get(j).comparaEstat(actual))
                    aux=true;
        }
        return aux;
    }
}
