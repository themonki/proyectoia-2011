package Comparadores;

import java.util.Comparator;

import Arbol.Nodo;


public class ComparadorNodoCosto implements Comparator<Nodo> {
	
	
	public int compare(Nodo x, Nodo y) 
    { 
        // Assume neither string is null. Real code should 
        // probably be more robust 
        if (x.getCosto() < y.getCosto()) 
        { 
            return -1; 
        } 
        if (x.getCosto() > y.getCosto()) 
        { 
            return 1; 
        } 
        return 0; 
    } 

}
