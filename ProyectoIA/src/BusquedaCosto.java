
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Vector;
public class BusquedaCosto {
	
	
	//esto es por si queren probar q el monticulo funciona bien 
  /* public static void main(String[] args) 
	    { 
	        Comparator<Nodo> comparator = new comparadorNodo(); 
	        PriorityQueue<Nodo> queue =  
	            new PriorityQueue<Nodo>(10, comparator); 
	        
	        Nodo nodo1= new Nodo();
	        Nodo nodo2= new Nodo();
	        Nodo nodo3= new Nodo();
	        Nodo nodo4= new Nodo();
	        Nodo nodo5= new Nodo();
	        Nodo nodo6= new Nodo();
	        Nodo nodo7= new Nodo();
	        Nodo nodo8= new Nodo();
	        Nodo nodo9= new Nodo();
	        
	        nodo1.setCosto(23);
	        nodo2.setCosto(13);
	        nodo3.setCosto(43);
	        nodo4.setCosto(73);
	        nodo5.setCosto(13);
	        nodo6.setCosto(333);
	        nodo7.setCosto(563);
	        nodo8.setCosto(323);
	        nodo9.setCosto(231);
	        
	        Vector <Nodo> resultadoValidacion = new Vector <Nodo> (0,1);
	        
	        resultadoValidacion.addElement(nodo2);
	        resultadoValidacion.addElement(nodo9);
	        resultadoValidacion.addElement(nodo7);
	        resultadoValidacion.addElement(nodo5);
	        
	        
	        queue.add(nodo1);    
	        queue.add(nodo3); 
	        queue.add(nodo4); 	    
	        queue.add(nodo6); 	       
	        queue.add(nodo8); 
	        queue.addAll(resultadoValidacion);
	       
	       
	        int i=0;
	        while (queue.size() != 0) 
	        { 
	        	Nodo raiz=queue.poll();
	        	if(i<4)
	        	{
	        		 queue.addAll(resultadoValidacion);
	        		 i++;	
	        	}
	        	
	           // System.out.println(queue.element().getCosto()+"  "+queue.remove()); 
	        	//System.out.println(queue.size()); 
	            System.out.println(raiz.getCosto()); 
	        } 
	    } */
	
    Comparator<Nodo> comparator = new comparadorNodo(); 
    PriorityQueue<Nodo> cola; 
       
	
	public BusquedaCosto(Nodo raiz){
		cola = new PriorityQueue<Nodo>(10, comparator); 
		cola.add(raiz);
	}
	
	private void eliminarNodosCiclos(Vector <Nodo> resultadoValidacion)
	{
		//System.out.println("llego1"); 
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
						
			raiz = cola.poll();		
			resultadoValidacion =raiz.expandir();
			//SI QUIEREN COMENTAN ESTE ELIMINAR CICLOS Y VERAS Q SE HACE MAS GRANDE LA COLA DE PRIORIDAD
			eliminarNodosCiclos(resultadoValidacion);
			cola.addAll(resultadoValidacion);
		}while(resultadoValidacion.size()!=0);	
		System.out.println("tamano final cola de prioridad:  "+cola.size()); 
		return raiz;		
	}
	
	
	
	
	
	
	

}
