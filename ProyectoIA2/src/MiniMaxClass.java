
import java.util.*;
public class MiniMaxClass {
	
	/* CASOOOOOOOOOO EMPATE */
	Nodo raiz ; 
	Vector <Vector <Nodo>> arbol= new Vector<Vector<Nodo>>() ;
	private int profundida=4;  
	/*char [][] TableroInicial={
			{'.','.','.','.','.','.'},
			{'.','P','.','.','.','.'},
			{'.','.','N','.','.','.'},
			{'.','.','.','.','.','.'},
			{'.','.','.','p','.','.'},
			{'.','.','.','.','.','.'},
	};*/
	public MiniMaxClass(char [][] TableroInicial, int dificultad ) 
	{
		this.profundida=dificultad;
		raiz= new Nodo(TableroInicial, 0, null);
		Vector<Nodo> temp = new Vector<Nodo>();
		temp.add(raiz);
				
		
		arbol.add(temp);
		
		for (int i=0;i<profundida ;i++)
		{
			Vector <Nodo> hijos = new Vector<Nodo>() ;
			
		
			for (int j=0;j<arbol.get(i).size();j++)// por cada elemento de la añtura anterior del arbol obtenemos sus hijos 
		
			{
				hijos.addAll(arbol.get(i).get(j).expandir());
			
			}
			
			
			
			arbol.add(hijos);
			
			
		}
		
		//decisionMiniMax();
		
		
	}
	
	public char [][] decisionMiniMax()
	{
		Vector <Nodo> hojas=arbol.get(arbol.size()-1);
		Vector <Nodo> SiguienteJugada=arbol.get(1);
		Nodo respuesta= new Nodo();
		int mayorMax=-999999;
		for (int i=0;i<hojas.size();i++)
		{
			hojas.get(i).asignarMinMax(hojas.get(i).funcionDeUtilidad(0));
			
			
			
		}
		
		
		
		for (int j=0;j<SiguienteJugada.size();j++)
		{
			
			if (SiguienteJugada.get(j).getMinMax()>mayorMax){
				
				respuesta=SiguienteJugada.get(j);
				mayorMax=SiguienteJugada.get(j).getMinMax();
			}
			
			
		}
	
		
		UtilsChess ver =new UtilsChess() ;
		ver.verEstado(respuesta.getTablero());
		
		System.out.println(respuesta.maxOmin);
		
		return respuesta.getTablero();
		
		
		
	}
	

	
	public void verHijos(Vector <Nodo> hijos)
	
	{
		for (int i=0;i<hijos.size();i++){
			String matriz;
			for (int j=0;j<6;j++){
				matriz=hijos.get(i).verEstado();
				
			}
			
		}
		
		
	}
	
	public static void main (String args []){
		char [][] TableroInicial=
			{
		
				{'.','.','.','k','.','.'},
				{'.','.','.','.','.','.'},
				{'.','N','.','.','.','n'},
				{'.','.','.','.','.','.'},
				{'.','P','.','.','.','K'},
				{'.','.','.','q','.','.'},
			};
		

	  char o='Q';
	  int a=o;
	  UtilsChess chek = new UtilsChess();
	  
	MiniMaxClass min = new MiniMaxClass(TableroInicial, 2);
	chek.verEstado(min.decisionMiniMax());
	
		
		
	}

}
