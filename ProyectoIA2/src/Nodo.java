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
		boolean isBlanca;
		if (getAltura()%2==0 )  isBlanca=true; else isBlanca=false   ;
		
		
		CheckTheCheck check= new CheckTheCheck() ;
		Vector <Nodo> hijos = new Vector<Nodo>();
		check.expandir(this,isBlanca);
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
			//System.out.println("papa  "+maxOmin+" altura  " + this.getAltura());
			
			CheckTheCheck algo = new CheckTheCheck();
			//algo.verEstado(Tablero);
			
		}
		
		
		
		
	}
	
	public int    funcionDeUtilidad( ){
		
		int MaxFichas = 0,MinFichas=0;
		for (int i=0;i<6;i++){
			for (int j=0;j<6;j++){
				if (Tablero [i][j]!='.')
				{
				
					int temp=0;
					
					switch(Character.toUpperCase(Tablero[i][j])) {//convierte la letra a mayuscula y busca el caso correspondiente

					case 'P': temp=2;//peon
						
						
						break;

					case 'N': temp=4;//Caballo
						
						break;

					case 'B':temp=4; //alfil
						
						break;

					case 'R':temp=4; //torre
				
						break;

					case 'Q':temp=6; //reina
						
						
						break;
						
					case 'K':temp=1000000; //reina
					
						break;
					}
					
					if (Character.isUpperCase(Tablero[i][j]) ) MaxFichas+=temp;
					else MinFichas+=temp;
					
				
				}}}
		
		System.out.println(MaxFichas-MinFichas);
		return MaxFichas-MinFichas;
		
	}
	
	

}
