import java.util.Vector;

//solo para ejecucion del metodo
public class Ejecutar {

	public static void main(String[] args) {
		Lector lectorObj= new Lector();
		String [][] matrizPadre= lectorObj.leer("./mapa.txt");
		
		Nodo raiz = new Nodo();
		raiz.setPadre(null);
		raiz.setCosto(0);
		raiz.setProfundidad(0);
		raiz.setContenido(matrizPadre);
		raiz.sacarDatoscarros();
		Nodo.CANTIDAD_NODOS++;//para que cuente la raiz
		
	//	BusquedaAmplitud ba = new BusquedaAmplitud(raiz);
	//	BusquedaCosto bc = new BusquedaCosto(raiz);
		BusquedaProfundidaSinCiclos bp = new BusquedaProfundidaSinCiclos(raiz);
		
		Nodo resultado= new Nodo();
		try{//exepcion de memoria
		resultado = bp.realizarBusqueda();
		Vector<Vector<String>> movimientos = resultado.RETORNAR_MOVIMIENTO();
		
		for(int i =0; i < movimientos.size();i++){
			Vector<String> vmover = movimientos.get(i);
			System.out.println("letra: " + vmover.get(0) + ", Dir: "+ vmover.get(1) + ", cantidad: "+ vmover.get(2));
		}
		
		}catch(java.lang.StackOverflowError e){
			e.printStackTrace();
		}		
		//aca como dice la diapositiva del profe el primero de la cola sera la rama solucion
		Vector<Nodo> ramaSolucion = resultado.RETORNAR_RAMA();

		// aca se imprime
		System.out.println("");
		for (int i = 0; i < ramaSolucion.size(); i++) {
			for (int j = 0; j < 7; j++) {
				for (int k = 0; k < 7; k++) {
					System.out.print(ramaSolucion.get(i).getContenido()[j][k]+" ");
				}
				System.out.println("");
			}
			System.out.println("");
		}
		
		System.out.println("Profundidad: "+ resultado.getProfundidad());
		System.out.println("Costo: "+ resultado.getCosto());
		System.out.println("Nodos creados: "+ Nodo.CANTIDAD_NODOS);
	}
}
