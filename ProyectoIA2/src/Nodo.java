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
		
		this.padre=padre;
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
			padre.asignarMinMax(maxOmin);
			
		}
		
		if (padre==null)
		{
			System.out.println("papa  "+maxOmin+" altura  " + this.getAltura());
			
			CheckTheCheck algo = new CheckTheCheck();
			algo.verEstado(Tablero);
			
		}
		
		
		
		
	}
	
	public int    funcionDeUtilidad( ){
		
		int MaxFichas = 0,MinFichas=0;
		for (int i=0;i<6;i++)
			for (int j=0;j<6;j++)
				if (Tablero [i][j]!='.')
				{
				
					if (Character.isUpperCase(Tablero[i][j]) ) MaxFichas++;
					else MinFichas++;}
		
		System.out.println(MaxFichas-MinFichas);
		return MaxFichas-MinFichas;
		
	}
	
	

}
