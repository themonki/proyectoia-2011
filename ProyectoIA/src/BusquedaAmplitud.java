import java.util.Vector;


public class BusquedaAmplitud {	
	Vector<Nodo> cola;
	
	public BusquedaAmplitud(Nodo raiz){
		cola = new Vector<Nodo>(0,1);
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
	public void impResultValidacion(Vector <Nodo> resultadoValidacion){
		String [][] matriz;
		System.out.println("expande:" +resultadoValidacion.size());
		for (int i=0;i<resultadoValidacion.size();i++){
			matriz= resultadoValidacion.get(i).getContenido();
			
			for (int j=0;j<7;j++){
				String aspero="";
				for (int k=0;k<7;k++){
					
					aspero+=matriz[j][k]+" ";
				
				}
				System.out.println(aspero);
				
			}
			
			System.out.println("hijo "+i);
			
		}
		
	}
	
	public Nodo realizarBusqueda(){
		int y=0;
		Vector <Nodo> resultadoValidacion;
		Nodo raiz;
		int Expande=0;
		do{
			
			raiz = cola.remove(0);
			resultadoValidacion =raiz.expandir();
			
			cola.addAll(resultadoValidacion);
			Expande+=resultadoValidacion.size();
			
			//System.out.println(resultadoValidacion.size());
			if (y<2)
			{
				impResultValidacion(resultadoValidacion);
			
			
			}
			y++;
		}while(resultadoValidacion.size()!=0);	
		System.out.println("Altura: "+raiz.getProfundidad() +"nodos Expandidos "+Expande+" Y="+y);
		return raiz;		
	}
}

