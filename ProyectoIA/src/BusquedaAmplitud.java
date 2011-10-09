import java.util.Vector;


public class BusquedaAmplitud {	
	Vector<Nodo> cola;
	
	public BusquedaAmplitud(Nodo raiz){
		cola = new Vector<Nodo>(0,1);
		cola.add(raiz);
	}
	
	public Nodo realizarBusqueda(){
		
		Vector <Nodo> resultadoValidacion;
		Nodo raiz;
		do{
			raiz = cola.remove(0);
			resultadoValidacion =raiz.expandir();
			cola.addAll(resultadoValidacion);
		}while(resultadoValidacion.size()!=0);		
		return raiz;		
	}
}

