
import java.util.*;
public class MiniMaxClass {
	
	
	Nodo raiz ; 
	Vector <Vector <Nodo>> arbol= new Vector<Vector<Nodo>>() ;
	private int profundida=2;  
	char [][] TableroInicial={
			{'.','.','.','.','.','.'},
			{'.','.','.','.','.','.'},
			{'.','.','N','.','.','.'},
			{'.','.','.','.','.','.'},
			{'.','.','.','p','.','.'},
			{'.','.','.','.','.','.'},
	};
	public MiniMaxClass() 
	{
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
			
			System.out.println("hijos :: "+hijos.size());
			
			arbol.add(hijos);
			
			
		}
		
		decisionMiniMax();
		
		
	}
	
	public void decisionMiniMax()
	{
		Vector <Nodo> hojas=arbol.get(arbol.size()-1);
		Vector <Nodo> SiguienteJugada=arbol.get(1);
		Nodo respuesta= new Nodo();
		int mayorMax=-999999;
		for (int i=0;i<hojas.size();i++)
		{
			hojas.get(i).asignarMinMax(funcionDeUtilidad(hojas.get(i).getTablero()));
			//System.out.println("utilidad max::"+funcionDeUtilidad(hojas.get(i).getTablero()));
			
			
		}
		
		
		
		for (int j=0;j<SiguienteJugada.size();j++)
		{
			//System.out.println("Nodo max::"+SiguienteJugada.get(j).getMinMax());
			if (SiguienteJugada.get(j).getMinMax()>mayorMax){
				
				respuesta=SiguienteJugada.get(j);
				mayorMax=SiguienteJugada.get(j).getMinMax();
			}
			
			
		}
		System.out.println("decision que tomo :::" +respuesta.getMinMax());
		
		CheckTheCheck ver =new CheckTheCheck() ;
		ver.verEstado(respuesta.getTablero());
		
		
		
	}
	
	public int    funcionDeUtilidad(char [][] tablero ){
		
		int MaxFichas = 0,MinFichas=0;
		for (int i=0;i<6;i++)
			for (int j=0;j<6;j++)
				if (tablero [i][j]!='.')
				{
				
					if (Character.isUpperCase(tablero[i][j]) ) MaxFichas++;
					else MinFichas++;}
		
		
		return MaxFichas-MinFichas;
	
		
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
		
	
		MiniMaxClass init = new MiniMaxClass();
		
	}

}
