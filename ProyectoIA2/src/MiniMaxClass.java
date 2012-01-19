
import java.util.*;
public class MiniMaxClass {
	
	
	Nodo raiz ; 
	Vector <Vector <Nodo>> arbol ;
	private int profundida=2;  
	
	public MiniMaxClass() 
	{
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
		
		char[][] tablero ={
			{'.','.','.','.','.','.','.','.'} ,
			{'.','.','.','.','.','.','.','.'},
			{'.','.','.','.','.','.','.','.'},
			{'.','.','.','.','.','.','.','.'},
			{'.','.','.','.','.','q','K','.'},
			{'.','.','.','.','.','.','.','.'},
			{'.','.','.','.','.','.','.','.'},
			{'.','.','Q','k','.','.','.','.'}};
		
		char[][] tablero2=new char [6][6];
		for (int i=0;i<6;i++)
			for (int j=0;j<6;j++)
				tablero2 [i][j] =tablero[i][j];
		
		tablero2[5][5]='n';
		
		System.out.println(tablero[5][5] );
		System.out.println(tablero );
		System.out.println(tablero2 );
		
		
	}

}
