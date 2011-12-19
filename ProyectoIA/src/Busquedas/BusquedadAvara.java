package Busquedas;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Vector;

import Arbol.Nodo;
import Comparadores.ComparadorNodoHeuristica;


public class BusquedadAvara {
	
	
	Comparator<Nodo> comparator = new ComparadorNodoHeuristica(); 
    PriorityQueue<Nodo> cola; 
       
	
	public BusquedadAvara(Nodo raiz){
		cola = new PriorityQueue<Nodo>(10, comparator); 
		cola.add(raiz);
	}
	
	private void eliminarNodosCiclos(Vector <Nodo> resultadoValidacion)
	{
		//System.out.println("llego1"); 
		for(int posVector=0; posVector<resultadoValidacion.size();posVector++)
		{
			if(resultadoValidacion.get(posVector).comprobarCiclo(resultadoValidacion.get(posVector).getContenido(), resultadoValidacion.get(posVector).getPadre()))
			{
				resultadoValidacion.removeElementAt(posVector);
				posVector--;
				
			}
				
				
			
				
			
		}
	}
	
	public Nodo realizarBusqueda(){
		
		Vector <Nodo> resultadoValidacion;
		Nodo raiz;
		do{
						
			raiz = cola.poll();		
			resultadoValidacion =raiz.expandir();
			//System.out.println(""+raiz.getValHeuristica());
			/*for(int i=0; i<7; i++)
			{
				System.out.println("");
				for(int j=0;j<7; j++)
				{
					System.out.println(""+raiz.getContenido()[i][j]);
					
				}
				
			}*/
			
			
			eliminarNodosCiclos(resultadoValidacion);
			cola.addAll(resultadoValidacion);
		}while(!raiz.esMeta());	
		System.out.println("tamano final cola de prioridad:  "+cola.size()); 
		return raiz;		
	}

}
