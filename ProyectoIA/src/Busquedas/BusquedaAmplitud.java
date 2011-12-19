package Busquedas;

import java.util.LinkedList;
import java.util.Vector;

import Arbol.Nodo;


public class BusquedaAmplitud {	
	//Vector<Nodo> cola;
	LinkedList<Nodo> cola;
	public BusquedaAmplitud(Nodo raiz){
		cola =new LinkedList<Nodo>();
		//cola = new Vector<Nodo>();//1813 1084 991
		cola.add(raiz);
	}
	
	private void eliminarNodosCiclos(Vector <Nodo> resultadoValidacion)
	{
		//System.out.println("llego1"); 
		for(int posVector=0; posVector<resultadoValidacion.size();posVector++)
		{
			//if(resultadoValidacion.get(posVector).comprobarCiclo(resultadoValidacion.get(posVector).getContenido(), resultadoValidacion.get(posVector).getPadre()))
		if(resultadoValidacion.get(posVector).comprobarCiclo(resultadoValidacion.get(posVector).getContenido(), resultadoValidacion.get(posVector).getPadre()))
			
			{
				resultadoValidacion.removeElementAt(posVector); 
				posVector--;				
				
			}
				
		}
	}
	
	
	public Nodo realizarBusqueda(){
		int y=0;
		Vector <Nodo> resultadoValidacion;
		Nodo raiz;
		
		do{
			
			raiz = cola.remove(0);
			resultadoValidacion =raiz.expandir();
			eliminarNodosCiclos(resultadoValidacion);
			cola.addAll(resultadoValidacion);
			
			
		
		}while(!raiz.esMeta());	
		
		return raiz;		
	}
}

