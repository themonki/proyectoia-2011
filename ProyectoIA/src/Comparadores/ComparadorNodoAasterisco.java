package Comparadores;

import java.util.Comparator;

import Arbol.Nodo;
public class ComparadorNodoAasterisco implements Comparator<Nodo>{

	public int compare(Nodo x, Nodo y) 
    { 
        // Assume neither string is null. Real code should 
        // probably be more robust 
        if (((x.getValHeuristica())+(x.getCosto()))< ((y.getValHeuristica())+(y.getCosto()))) 
        { 
            return -1; 
        } 
        if (((x.getValHeuristica())+(x.getCosto()))>((y.getValHeuristica())+(y.getCosto()))) 
        { 
            return 1; 
        } 
        return 0; 
    } 
	
}
