import java.util.Vector;
import java.math.*;

public class Nodo {
	
	 private char[][] Tablero= new char [6][6];
	 
	 int maxOmin ;
	 int altura;
	 Nodo padre;
	 boolean primeraAsignacionMinMax=true ;
	
	
	
	
	public Nodo() {
		// TODO Auto-generated constructor stub
	}
	public Nodo(char[][] Tablero, int altura,Nodo padre) {
		
		
		this.Tablero=Tablero;
		
		this.altura=altura;
	}
	
	public  Vector <Nodo> expandir ()
	{
		CheckTheCheck check= new CheckTheCheck() ;
		Vector <Nodo> hijos = new Vector<Nodo>();
		check.expandir(this);
		hijos=check.getHijos();
		
		return hijos ;
		
	}
	
	public int getAltura (){return altura;}


	public int getMinMax (){return maxOmin;}
	public char [] [] getTablero (){return Tablero ;};

	public String  verEstado() {
		
		String matriz = "";
		for (int i=0;i<6;i++){
			
			for (int j=0;j<6;j++){
				matriz+=Tablero[i][j];
				
			}
			matriz+="\n";
		}
		
		
		return matriz;
		// TODO Auto-generated method stub
		
	}
	
	public void  asignarMinMax(int valor ){
		if (primeraAsignacionMinMax)
		{
			maxOmin=valor ;
			
			primeraAsignacionMinMax=false;
			
		
		} 
		else 
		{
			if (altura%2==0)
		
			{
			
			
			maxOmin=Math.max(maxOmin, valor);
			
		
		
			} 
		
			else 
		
			{
				maxOmin=Math.min(maxOmin, valor);
			
			
		
			}
			
		}
		
		if (padre!=null)
		{
			asignarMinMax(maxOmin);
			
		}
		
		
		
		
	}
	
	

}
