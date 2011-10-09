import java.util.Stack;
import java.util.Vector;


public class BusquedaProfundidaSinCiclos {
//Vector<Nodo> cola;
Stack<Nodo> pila; 
	
	public BusquedaProfundidaSinCiclos(Nodo raiz){
		pila = new Stack<Nodo>();
		pila.push(raiz);
	}
	
	private void eliminarNodosCiclos(Vector <Nodo> resultadoValidacion)
	{
		
		for(int posVector=0; posVector<resultadoValidacion.size();posVector++)
		{
			if(resultadoValidacion.get(posVector).comprobarCiclo(resultadoValidacion.get(posVector).getContenido(), resultadoValidacion.get(posVector).getPadre()))
				resultadoValidacion.removeElementAt(posVector);
				
			
				
			
		}
	}
	
	public Nodo realizarBusqueda(){
		
		Vector <Nodo> resultadoValidacion;
		Nodo raiz;
		do{
			raiz = pila.pop();
			resultadoValidacion =raiz.expandir();
			eliminarNodosCiclos(resultadoValidacion);
			for(int posVector=0; posVector<resultadoValidacion.size(); posVector++)
				pila.push(resultadoValidacion.get(posVector));
			
		}while(resultadoValidacion.size()!=0);		
		return raiz;		
	}

}
