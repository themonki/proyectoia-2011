import java.util.Vector;


public class Nodo {
	
	 private char[][] stateTablero={
			 
			{'.','.','.','.','.','.'} ,
			{'.','.','.','.','.','.'} ,
			{'.','.','.','P','.','.'} ,
			{'.','.','.','.','.','.'} ,
			{'.','.','.','.','.','.'} ,
			{'.','.','.','.','.','.'} ,
		
	 };
	
	
	
	
	public Nodo() {
		// TODO Auto-generated constructor stub
	}
	public Nodo(char[][] stateTablero) {
		
		this.stateTablero=stateTablero.clone();
		
	}
	public  Vector <Nodo> expandir ()
	{
		 Vector <Nodo> hijos = new Vector<Nodo>();
		 
		 
		 
		 
		 
		
		return hijos ;
		
	}



	public String  verEstado() {
		
		String matriz = "";
		for (int i=0;i<6;i++){
			
			for (int j=0;j<6;j++){
				matriz+=stateTablero[i][j];
				
			}
			matriz+="\n";
		}
		
		
		return matriz;
		// TODO Auto-generated method stub
		
	}
	
	

}
