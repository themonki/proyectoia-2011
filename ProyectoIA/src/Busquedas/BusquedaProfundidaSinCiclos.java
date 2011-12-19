package Busquedas;


import java.util.Stack;
import java.util.Vector;

import Arbol.Nodo;


public class BusquedaProfundidaSinCiclos {
//Vector<Nodo> cola;
Stack<Nodo> pila; 
Vector <Nodo> resultadoValidacion;
	
	public BusquedaProfundidaSinCiclos(Nodo raiz){
		pila = new Stack<Nodo>();
		pila.push(raiz);
	}
	
	private void eliminarNodosCiclos()
	{
		
		for(int posVector=0; posVector<resultadoValidacion.size();posVector++)
		{
			if(resultadoValidacion.get(posVector).comprobarCiclo(resultadoValidacion.get(posVector).getContenido(), resultadoValidacion.get(posVector).getPadre()))
				{
					resultadoValidacion.removeElementAt(posVector);
					posVector--;
					/*for(int i=0; i<resultadoValidacion.size();i++)
					{
						//boolean resultado = Arrays.deepEquals(raiz.getContenido(), resultadoValidacion.get(i).getContenido());
					//	System.out.println(""+resultado+"  k");
						for(int k=0; k<resultadoValidacion.get(k).getContenido().length;k++)
						{
							for(int j=0; j<resultadoValidacion.get(j).getContenido().length;j++)
							{
								
								System.out.print(resultadoValidacion.get(i).getContenido()[k][j]);
								
							}
							System.out.println("");
						}
						System.out.println("");
						
					//	System.out.println(""+resultado+"  k"+Arrays.toString(resultadoValidacion.get(i).getContenido()));
						
						
						
						
						
					}*/
				}
				
			
				
			
		}
	}
	
	public Nodo realizarBusqueda(){
		/*Nodo raiz;
		Vector <Nodo> resultadoValidacion;
		
		raiz = pila.pop();
		resultadoValidacion=raiz.expandir();
	
	
		for(int i=0; i<resultadoValidacion.size();i++)
		{
			boolean resultado = Arrays.deepEquals(raiz.getContenido(), resultadoValidacion.get(i).getContenido());
			System.out.println(""+resultado+"  k");
			for(int k=0; k<resultadoValidacion.get(k).getContenido().length;k++)
			{
				for(int j=0; j<resultadoValidacion.get(j).getContenido().length;j++)
				{
					
					System.out.print(resultadoValidacion.get(i).getContenido()[k][j]);
					
				}
				System.out.println("");
			}
			
		//	System.out.println(""+resultado+"  k"+Arrays.toString(resultadoValidacion.get(i).getContenido()));
			
			
			
			
			
		}*/
		
		
		Nodo raiz;
		do{
			raiz = pila.pop();
			resultadoValidacion =raiz.expandir();
			eliminarNodosCiclos();
			for(int posVector=0; posVector<resultadoValidacion.size(); posVector++)
				pila.push(resultadoValidacion.get(posVector));
			
		}while(!raiz.getEsMeta());		
		return raiz;		
	}

}
