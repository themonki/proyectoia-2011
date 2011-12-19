package Arbol;
import java.util.Arrays;
import java.util.Vector;

public class Nodo implements Cloneable{
	//propiedades del nodo el estado de los carros es el vector q dice letra direccion y 
	//tamaño del carro, letra q saldra es la letra a salir del parqueadero
	static int numnodos=0 ; 
	private Nodo padre;
	private String[][] matriz;
	private int profundidad;
	private int costo;
	private int valHeuristica=0;
	private boolean esMeta=false;
	
	public int getValHeuristica() {
		return valHeuristica;
	}

	public void setValHeuristica(int valHeuristica) {
		this.valHeuristica = valHeuristica;
	}
	static Vector <String[]>estadoDeLosCarros=new Vector<String[]>(1,0);
	
	private Vector<String> mover;//triplete: letra, direccion(arriba, abajo, der, izq), cantidad a mover
	
	public static int CANTIDAD_NODOS=0;
	public static int CANTIDAD_NODOS_EXPANDIDOS=0;
	public Nodo(){}

	public Nodo(Nodo p, String[][] matriz,int profPadre){
		this.matriz = matriz;
		padre = p;
		
		setProfundidad(profPadre+1);
	}

	//en el ejemplo del profe como es matriz 7*7 entonces la letra inicial debe estar en la posicion 2,6

	public boolean esMeta()
	{
	
		for (int i=6;true;i--)
		{
			
			if(matriz[2][i].equals("A")) return true;

		
			
			if(!matriz[2][i].equals("0")) return false;
		}
	}

	//expandir como dije antes pregunta si es meta si lo es manda vector vacio si no manda los hijos
	public Vector <Nodo> expandir()
	{
		CANTIDAD_NODOS_EXPANDIDOS++;
	
		if(!(esMeta())){		
			return sacarHijos();
		}
		esMeta=true;
	
		//System.out.println("nodo #: " +numnodos );
		return new Vector <Nodo> (0,1);
	}
	
	
	public void iniEstadoCarros()
	{
		estadoDeLosCarros=null;
		estadoDeLosCarros=new Vector<String[]>(1,0);
		
	}

	//esta es la q saca los datos de letra direccion y tama�o del carro
public	void sacarDatoscarros()// generalizada
	{
		Vector <String> vectorLetras= new Vector<String>(0,1); 
		String letra="";
		int tamanoCarro=1;
		String direccion="";
		for(int i=0; i<matriz.length;i++)
		{
			for(int j=0; j<matriz.length;j++)
			{
				letra=matriz[i][j];
				//  solo se mira si es diferente de 1 o 0 osea muro o vacio si lo es pues se averigua su direccion tama�o y letra   
				if(!(letra.equals("0")) && (!(letra.equals("1"))) && !vectorLetras.contains(letra))
				{
					vectorLetras.add(letra);
					int columnas=j+1,filas=i+1;
					
					while(columnas<matriz.length && (matriz[i][columnas].equals(letra)))
					{
						tamanoCarro++;
						columnas++;
						direccion="horizontal";
					}

					while( filas<matriz.length && (matriz[filas][j].equals(letra)))
					{
						tamanoCarro++;
						filas++;
						direccion="vertical";
					}

					String[] datosCarro=new String[3];
					datosCarro[0]=letra;
					datosCarro[1]=direccion;
					datosCarro[2]=""+tamanoCarro;
					estadoDeLosCarros.add(datosCarro);

				}
				letra="";
				tamanoCarro=1;
				direccion="";
			}
		}
	}
	

	//entendible
	void imprimirEstadoDeLosCarros()
	{
		for(int i=0;i<estadoDeLosCarros.size(); i++ )
		{
			System.out.println("letra: "+estadoDeLosCarros.get(i)[0]+" direccion: "+ estadoDeLosCarros.get(i)[1]+" tama�o: "+estadoDeLosCarros.get(i)[2]);
		}
	}
	
	int aplicarHeuristica(String[][] matriz)
	{
		    int heuristica = 0;
			int fila=2;
			boolean flag=false;
			
			
			for(int columna=0;columna<7;columna++)
			{
				if(matriz[fila][columna].equals("A")) flag=true;
				if(flag && !(matriz[fila][columna].equals("1")) && !(matriz[fila][columna].equals("0")) && !(matriz[fila][columna].equals("A")))
					heuristica++;
				
			}
			return heuristica;
		
	}

	//esta es la funcion mas importante y larga, los try catch y finally los use para q no marcara error cuando se pasara del limite de la matriz osea se q con una validacion en el if se podia
	//pero es q en la de sacar datos carros era muy complicado hacer los limites de la validacion en el if haci q los hice con excepciones en esta funcion sacar hijos talvez si se podia 
	//con los limites en el if pero me embollaba mas, los finally los coloque por que aqui siempre habra excepciones al llegar al limite de la matriz haci q se salta lo q sigue despues de un while
	//los tipos de while q use son 
	//un while para mover Carros Verticalmente Abajo
	//un while para mover Carros Verticalmente Arriba
	//un while para mover Carros horizontalmente  derecha
	//un while para mover Carros horizontalmente izquierda
	//pero como son carros de dos tama�os seria 8 whiles en total
	//lo que hay dentro de un while es lo siguiente 
	//se crea un nodo con el posible movimiento q se hace 
	//se agrega al vector hijos
	//como las casillas a mover varian de acuerdo a cuantos espacion en cero halla entonces se utiliza el while para saber cuantos espacios mover
	//finalmente dependiendo para donde y en q sentido se mueva se aumenta un j o un i auxiliares no los del for por q se da�a el rrecorrido 
	//luego en el finally pues hay estan los while para los movimientos en sentido contrario osea horizontal a la izquierda y vertical hacia arriba
	//al final del finally ya se puede aumentar el j o el i original del for para q siga su recorrido
	//a las funcion por ejemplo moverCarroHorizontalmenteDerecha (casillasAmover, j, k,  3, letra) le entra las casillas a mover la posicion inicial de la priemra letra osea 
	//como la posicion de la cola del carro o algo asi, el tama�o del carro y la letra del carro 

	//Nota : yo se q lo del try catch y finally utilizarlo para los limites de la matriz es medio chambon pero en serio en el momento me hacia ver las cosas claras bueno igual funciona bien XD


public Vector<Nodo> sacarHijos(){	
	String letra = "";
	String direccion = "";
	String tamano = "";
	int numTamano;
	Vector<Nodo> hijos=new Vector<Nodo>(0,1);
	for(int iteratorCarro=0; iteratorCarro<estadoDeLosCarros.size();iteratorCarro++)
	{
		letra=estadoDeLosCarros.get(iteratorCarro)[0];
		direccion=estadoDeLosCarros.get(iteratorCarro)[1];
		tamano=estadoDeLosCarros.get(iteratorCarro)[2];
		numTamano=Integer.parseInt(tamano);

		for(int j=0; j<matriz.length; j++)
		{
			for(int k=0; k<matriz.length;k++)
			{				
				if(matriz[j][k].equals(letra))// teniendo el vector estado de carros lo que hace este if es buscar en la matriz la primera letra del vector estado de carros
				{
					if(direccion.equals("vertical") )
					{
						int auxj=j+numTamano;
						int casillasAmover=1;

						while(auxj<matriz.length && matriz[auxj][k].equals("0"))
						{
							
							//System.out.println("h"+valHeuristica);
							Nodo nodoHijo=new Nodo(this,moverCarroVerticalmente(casillasAmover, j, k,  numTamano, letra,1),profundidad);								
							Vector<String> movimiento = new Vector<String>(0,1);											
							movimiento.add(letra);
							movimiento.add("abajo");
							movimiento.add(""+casillasAmover);
							nodoHijo.setMover(movimiento);
							nodoHijo.setCosto(costo+casillasAmover);
							//System.out.println(aplicarHeuristica(nodoHijo.getContenido()));
							nodoHijo.setValHeuristica(aplicarHeuristica(nodoHijo.getContenido()));
							hijos.add(nodoHijo);
							CANTIDAD_NODOS++;
							
							casillasAmover++;
							auxj++;
						}

						int auxjParaReversa=j-1;
						int casillasAmoverParaReversa=1;

						while( auxjParaReversa>=0 && matriz[auxjParaReversa][k].equals("0"))
						{  
							
							//System.out.println("h"+valHeuristica);
							Nodo nodoHijo=new Nodo(this,moverCarroVerticalmente(casillasAmoverParaReversa, j, k,  numTamano, letra,-1),profundidad);
							Vector<String> movimiento = new Vector<String>(0,1);							
							movimiento.add(letra);
							movimiento.add("arriba");
							movimiento.add(""+casillasAmoverParaReversa);
							nodoHijo.setMover(movimiento);
							nodoHijo.setCosto(costo+casillasAmoverParaReversa);
						//	System.out.println(aplicarHeuristica(nodoHijo.getContenido()));
							nodoHijo.setValHeuristica(aplicarHeuristica(nodoHijo.getContenido()));
							hijos.add(nodoHijo);
							CANTIDAD_NODOS++;
							
							casillasAmoverParaReversa++;
							auxjParaReversa--;
						}
						j=j+numTamano-1;
					}

					if(direccion.equals("horizontal") )
					 {
						int auxK=k+numTamano;	
						int casillasAmover=1;
						
						while( auxK<matriz.length && matriz[j][auxK].equals("0"))
						{    
							
						//	System.out.println("h"+valHeuristica);
							Nodo nodoHijo=new Nodo(this,(moverCarroHorizontalmente (casillasAmover, j, k,  numTamano, letra,1)),profundidad);
							Vector<String> movimiento = new Vector<String>(0,1);
							movimiento.add(letra);
							movimiento.add("derecha");
							movimiento.add(""+casillasAmover);
							nodoHijo.setMover(movimiento);
							nodoHijo.setCosto(costo+casillasAmover);
						//	System.out.println(aplicarHeuristica(nodoHijo.getContenido()));
							nodoHijo.setValHeuristica(aplicarHeuristica(nodoHijo.getContenido()));
							hijos.add(nodoHijo);
							CANTIDAD_NODOS++;
							
							casillasAmover++;
							auxK++;							
						}
						int auxKParaReversa=k-1;// ayuda avalidar la posicion 
						int casillasAmoverParaReversa=1;

						
						while(auxKParaReversa>=0  && matriz[j][auxKParaReversa].equals("0"))
						{
							
						//	System.out.println("h"+valHeuristica);
							Nodo nodoHijo=new Nodo(this,moverCarroHorizontalmente (casillasAmoverParaReversa, j, k,  numTamano, letra,-1),profundidad);
							Vector<String> movimiento = new Vector<String>(0,1);
							movimiento.add(letra);
							movimiento.add("izquierda");
							movimiento.add(""+casillasAmoverParaReversa);
							nodoHijo.setMover(movimiento);
							nodoHijo.setCosto(costo+casillasAmoverParaReversa);
							//System.out.println(aplicarHeuristica(nodoHijo.getContenido()));
							nodoHijo.setValHeuristica(aplicarHeuristica(nodoHijo.getContenido()));
							hijos.add(nodoHijo);
							CANTIDAD_NODOS++;
							
							casillasAmoverParaReversa++;
							auxKParaReversa--;
						}
						
						k=k+numTamano-1;
					}
				}// fin if letra
			}
		}
	}
	
	//verhijos(hijos);
	//System.out.println("hijos length "+hijos.size());
	return hijos;
	}
/*
	private void verhijos(Vector<Nodo> hijos) {
	 
		
		for (int i=0;i<hijos.size();i++){
		String [][] matriz= hijos.get(i).getContenido();
		int prof=hijos.get(i).getCosto();
			for (int j=0;j<7;j++)
			{
				String fila = "" ;
				for (int k=0;k<7;k++){
					fila+=matriz[j][k] ;
					
				}
			System.out.println(fila);
			}
			
			System.out.println(prof);
		}
	}*/

	//bueno aqui viene la parte de analisis osea yo les explico el codigo pero deben coger una hoja y un papel y simular los movimientos de los carros
	//de acuerdo a la direccion y sentido al q vallan a mi me toco hacer en papel para entenderlo sobre todo las q van en reversa son complicadas	
	public String[][] moverCarroHorizontalmente(int casillasAmover, int posx,
			int posy, int tamanoCarro, String letra, int dereOizq)// izquierda -1 derecha 1
	{
		String[][] MatrizHija = new String[matriz.length][matriz.length];

		// aqui  se tiene q crear una matriz nueva lo q hice es q mientras
		// se creaba la matriz realice el movimiento del carro de acuerdo a su
		// tama�o y casillas a mover
		for (int i = 0; i < matriz.length; i++)
			for (int j = 0; j < matriz.length; j++)
				MatrizHija[i][j] = matriz[i][j];

		for (int iter = 0; iter < casillasAmover; iter++) {
			MatrizHija[posx][posy] = "0";
			posy = posy + dereOizq;
		}

		for (int w = 0; w < tamanoCarro; w++) {
			MatrizHija[posx][posy] = letra;
			posy++;
		}

		if (dereOizq == -1) {
			for (int iter = 0; iter < casillasAmover; iter++) {
				MatrizHija[posx][posy] = "0";
				posy++;
			}
		}
		return MatrizHija;
	}	 
	//en los movimiento verticales utilice otro metodo primero cree la matriz y luego si
	//hice el movimiento aqui era imposible hacer el movimeinto al mismo tiempo q se creaba la matriz
	public String[][] moverCarroVerticalmente (int casillasAmover, int posx, int posy, 
			int tamanoCarro,String letra,int arribaOabajo)//arriba -1 abajo +1
	{
		String[][] MatrizHija = new String[matriz.length][matriz.length];
		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz.length; j++) {
				MatrizHija[i][j] = matriz[i][j];
			}
		}
		for (int iter = 0; iter < casillasAmover; iter++) {
			MatrizHija[posx][posy] = "0";
			posx = posx + arribaOabajo;
		}
		for (int iter = 0; iter < tamanoCarro; iter++) {
			MatrizHija[posx][posy] = letra;
			posx++;
		}
		if (arribaOabajo == -1) {
			for (int iter = 0; iter < casillasAmover; iter++) {
				MatrizHija[posx][posy] = "0";
				posx++;
			}
		}
		return MatrizHija;
	}
	
	public Vector<Vector<String>> RETORNAR_MOVIMIENTO() {

		Vector<Vector<String>> movimiento = new Vector<Vector<String>>(0,1);

		if (this.getPadre() == null) {// nodo raiz
			//movimiento.add(this);//no hay movimiento a agregar
		} else {
			movimiento.addAll(this.getPadre().RETORNAR_MOVIMIENTO());
			movimiento.add(this.mover);
		}
		return movimiento;
	}
	
	//aca se retorna la rama q tiene la solucion osea la primera en la pila cunado encuentra la meta
    public  Vector<Nodo> RETORNAR_RAMA() {

        Vector <Nodo>rama = new Vector<Nodo>(0,1);   
               

        if (this.getPadre()==null) {//nodo raiz
            rama.add(this);
        } else {            
            rama.addAll(this.getPadre().RETORNAR_RAMA());
            rama.add(this);
        }
        return rama;
    }
    
    
    
    // compara las matrices de cada nodo de la rama para mirar si se repite el estado 
    public boolean comprobarCiclo(String[][] matrizAcomparar,Nodo nodoPadre)
    {
    	boolean resultado=false;
    	
    	if (nodoPadre==null) {//nodo raiz
    		return false;
    	}else {   
    		resultado = Arrays.deepEquals(matrizAcomparar, nodoPadre.getContenido());// no se si esto funcione
    		if(resultado)
    		{
    			return true;
    		}else
     	  	  {
    			return comprobarCiclo(matrizAcomparar,nodoPadre.getPadre());
   		 
     	  	  }
            
    	}
    	
    	
    	
    }
    
    
   
	
	//esto es entendible

	public Nodo getPadre() {
		return padre;
	}
	public int getNodosExpandidos(){
		return CANTIDAD_NODOS_EXPANDIDOS;
	}
	public int getNodosCreados(){
		return CANTIDAD_NODOS;
	}

	public String[][] getContenido() {

		return matriz;
	}

	public void setPadre(Nodo p) {
		padre = p;
	}

	public void setContenido(String[][] matriz) {
		this.matriz = matriz;
	}

	public int getProfundidad() {
		return profundidad;
	}

	public void setProfundidad(int profundidad) {
		this.profundidad = profundidad;
	}
	
	public Vector<String> getMover(){
		return mover;
	}
	
	public void setMover(Vector<String> v){
		mover=v;
	}
	public int getCosto(){
		return costo;
	}
	public void setCosto(int v){
		costo=v;
	}
	public boolean getEsMeta(){
		return esMeta;
	}
	
}
