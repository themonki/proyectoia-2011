import java.util.Vector;
import java.math.*;

public class Nodo {
	
	 private char[][] Tablero= new char [6][6];
	 
	 int maxOmin ;
	 int altura;
	 Nodo padre;
	 boolean primeraAsignacionMinMax=true ;
	 boolean isMax;
	
	
	
	
	public Nodo() {
		// TODO Auto-generated constructor stub
	}
	public Nodo(char[][] Tablero, int altura,Nodo padre) {
		
		this.padre=padre;
		this.Tablero=Tablero;
		
		this.altura=altura;
		isMax = altura%2==0;
	}
	
	public  Vector <Nodo> expandir ()
	{
		boolean isBlanca;
		if (getAltura()%2==0 )  isBlanca=true; else isBlanca=false   ;
		
		
		UtilsChess check= new UtilsChess() ;
		Vector <Nodo> hijos = new Vector<Nodo>();
		check.expandir(this,isBlanca);
		hijos=check.getHijos();
		
		return hijos ;
		
	}
	
	public int getAltura (){return altura;}


	public int getMinMax (){return maxOmin;}
	public boolean getIsMax (){return isMax;}
	
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
			
			UtilsChess algo = new UtilsChess();
			//algo.verEstado(Tablero);
			
		}
		
		
		
		
	}
	
	public int    funcionDeUtilidad(int mate ){
		
		int MaxFichas = 0,MinFichas=0;
		for (int i=0;i<6;i++){
			for (int j=0;j<6;j++){
				if (Tablero [i][j]!='.')
				{
				
					int temp=0;
					
					switch(Character.toUpperCase(Tablero[i][j])) {//convierte la letra a mayuscula y busca el caso correspondiente

					case 'P': temp=1;//peon
						
						
						break;

					case 'N': temp=4;//Caballo
						
						break;

					case 'B':temp=4; //alfil
						
						break;

					case 'R':temp=4; //torre
				
						break;

					case 'Q':temp=7; //reina
						
						
						break;
						
					case 'K':temp=100; //rey
					
						break;
					}
					
					if (Character.isUpperCase(Tablero[i][j]) ) MaxFichas+=temp;
					else MinFichas+=temp;
					
				
				
				}
				}
			}
		
		
		UtilsChess check= new UtilsChess();
		int factorJaque=0;
		if(check.isCheck(Tablero, 'K')) factorJaque=-1 ;
		if(check.isCheck(Tablero, 'k')) factorJaque=1 ;
		
		
		return MaxFichas-MinFichas+1000*mate+3*factorJaque;
		
	}
	
	

}
