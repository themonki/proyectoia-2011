
import java.io.*;
import java.util.*;

public class CheckTheCheck {

	char[][] tablero ,TableroCheck;

	static int[] dxPeon = {+1,+1};
	static int[] dyPeon = {-1,+1};
	static int[] dxPeon1 = {+1,+1,+1};
	static int[] dyPeon1 = {-1,+1,0};
	static int[] dxCaballo = {-2,-1,+2,+1,+2,+1,-2,-1};
	static int[] dyCaballo = {-1,-2,-1,-2,+1,+2,+1,+2};

	static int[] dxTorre = { 0, 0,-1,+1};
	static int[] dyTorre = {-1,+1, 0, 0};
	static int[] dxAlfil = {-1,+1,-1,+1};
	static int[] dyAlfil = {-1,-1,+1,+1};
	
	static int[] dyRey = {0,0,1,-1,+1,-1,+1,-1};
	static int[] dxRey = {1,-1,0,0,+1,-1,-1,+1};
	


	Vector <Nodo> hijos= new Vector<Nodo>() ; 
	Nodo padre;

	public  void expandir(Nodo padre,boolean isBlanca) {

		

		this.padre=padre;
		this.tablero=padre.getTablero();
		

		System.out.println("altura "+padre.altura);
		
	

		System.out.println("blanca "+isBlanca);
		char amenazante='.';

		//	        
		for(int i=0; i<6; i++) {
			for(int j=0; j<6; j++) {

				final char actual=tablero[i][j];

				
				if(Character.isUpperCase(actual) && isBlanca || (!Character.isUpperCase(actual) &&  !isBlanca ) ) 
				 {
					
					switch(Character.toUpperCase(actual)) {//convierte la letra a mayuscula y busca el caso correspondiente

					case 'P': //peon
						int signX= Character.isUpperCase(actual) ? -1 : 1;
						if(recorrido1pasoExpandir(i,j, dxPeon1, dyPeon1, signX,false))
							amenazante=actual;
						break;

					case 'N': //Caballo
						if(recorrido1pasoExpandir(i,j, dxCaballo, dyCaballo, 1,true))
							amenazante=actual;
						System.out.println("entre " +actual);
						
						break;

					case 'B': //alfil
						if(recorridoMultipleExpandir(i,j, dxAlfil, dyAlfil))
							amenazante=actual;
						break;

					case 'R': //torre
						if(recorridoMultipleExpandir(i,j, dxTorre, dyTorre))
							amenazante=actual;
						break;

					case 'Q': //reina
						System.out.println("si expandio "+ amenazante);
						if(recorridoMultipleExpandir(i,j, dxTorre, dyTorre) ||
								recorridoMultipleExpandir(i,j, dxAlfil, dyAlfil))
							amenazante=actual;
						System.out.println("si expandio fin "+ amenazante);
						break;
						
					case 'K': //reina
						if(recorrido1pasoExpandir(i,j, dxRey, dyRey,1,true))
							amenazante=actual;
						break;
						
					
					}
					
					
				
				
				}

			}
		}



	}


	public  boolean isCheck(char [][] tableroComparar,char comparador) {



	
		TableroCheck=tableroComparar;

		char amenazante='.';

			        
		for(int i=0; i<6; i++) {
			for(int j=0; j<6; j++) {

				final char actual=TableroCheck[i][j];

				switch(Character.toUpperCase(actual)) {//convierte la letra a mayuscula y busca el caso correspondiente

				case 'P': //peon
					int signX= Character.isUpperCase(actual) ? -1 : 1;
					if(recorrido1paso(i,j, dxPeon, dyPeon, signX))
						amenazante=actual;
					break;

				case 'N': //Caballo
					if(recorrido1paso(i,j, dxCaballo, dyCaballo, 1))
						amenazante=actual;
					break;

				case 'B': //alfil
					if(recorridoMultiple(i,j, dxAlfil, dyAlfil))
						amenazante=actual;
					break;

				case 'R': //torre
					if(recorridoMultiple(i,j, dxTorre, dyTorre))
						amenazante=actual;
					break;

				case 'Q': //reina
					if(recorridoMultiple(i,j, dxTorre, dyTorre) ||
							recorridoMultiple(i,j, dxAlfil, dyAlfil))
						amenazante=actual;
					break;
					
				case 'K': //reina
					if(recorrido1paso(i,j, dxRey, dyRey,1))
						amenazante=actual;
					break;
				}
				if(amenazante!='.') {
					System.err.println("amenazante at "+i+","+j+" : "+amenazante+comparador);
					boolean temo =Character.isUpperCase(comparador)^Character.isUpperCase(amenazante);
					//System.out.println("amena :: "+temo+comparador+amenazante);
					amenazante='.';
					if (temo==true) return true ;
					

					
				}
			}
		}


		if(amenazante=='.')// no encontro un amenazante 
			System.out.println("no king is in check.");
		else {//si encontro un amenazante  si es mayuscula el amenazante  entonces  el amenazado el es rey negro 
			String amenazado = Character.isUpperCase(amenazante) ? "black" : "white";
			System.out.println(amenazado+" king is in check. "+ amenazante);
		}
		
		
		return false;    
	}



	 boolean recorrido1pasoExpandir(int x, int y, int[] dx, int[] dy, int signoX,boolean isCaballo) {
		final char actual=tablero[x][y];
		for(int dir=0; dir<dx.length; dir++) {
			int xx = x+dx[dir]*signoX, yy = y+dy[dir];

			if(valid(xx,yy)&& dy[dir]!=0 && tablero[xx][yy]!='.' && esFichaEnemiga(actual, tablero[xx][yy]) && !isCaballo)// miro si es valida la posicion y si tengo uno que matar 
			{
				
				//System.out.println("if  1 "+tablero[xx][yy])  ;


				char [][] nuevoTablero= new char [6][6]; 
				nuevoEstado(nuevoTablero);
				nuevoTablero[xx][yy]=actual;
				nuevoTablero[x][y]='.';

				
				
				// falta la condicion de si ese movimiento deja en jaque a mi rey ojo ? ????  
				//si mi movimiento hace que quede en jaque mi rey no creo ese nodo
				if (!isCheck (nuevoTablero,actual)){
					//verEstado(nuevoTablero);
				Nodo hijo = new Nodo( nuevoTablero, padre.getAltura()+1,padre);	       	
				hijos.add(hijo);}

			}

			if (valid(xx,yy) && dy[dir]==0  && tablero[xx][yy]=='.' && !isCaballo) // en caso de que se pueda mover a su frente 
			{
				System.out.println("if  2 ");

				esFichaEnemiga(actual, tablero[xx][yy]);
				char [][] nuevoTablero= new char [6][6]; 
				nuevoEstado(nuevoTablero);
				nuevoTablero[xx][yy]=actual;
				
				
				nuevoTablero[x][y]='.';

			
				
				if (!isCheck (nuevoTablero,actual)){
				//	verEstado(nuevoTablero);
				// falta la condicion de si ese movimiento deja en jaque a mi rey ojo ? ????  
				Nodo hijo = new Nodo( nuevoTablero, padre.getAltura()+1,padre);	        	
				hijos.add(hijo);
				
				}	
			}
			
			if (valid(xx,yy) &&  isCaballo  && ( tablero[xx][yy]=='.'||esFichaEnemiga(actual, tablero[xx][yy])))// en caso de que se pueda mover a su frente 
			{
				System.out.println("if  3");

				char [][] nuevoTablero= new char [6][6]; 
				nuevoEstado(nuevoTablero);
				nuevoTablero[xx][yy]=actual;
				
				
				nuevoTablero[x][y]='.';

				
				
				if (!isCheck (nuevoTablero,actual)){
					//verEstado(nuevoTablero);
				// falta la condicion de si ese movimiento deja en jaque a mi rey ojo ? ????  
				Nodo hijo = new Nodo( nuevoTablero, padre.getAltura()+1,padre);	        	
				hijos.add(hijo);	}
			}
			
			
		}
		return false;
	}

	 boolean recorridoMultipleExpandir(int x, int y, int[] dx, int[] dy) {
		final char actual=tablero[x][y];

		for(int dir=0; dir<dx.length; dir++) {
			int xx=x+dx[dir], yy=y+dy[dir];
			
			while(valid(xx,yy) && (tablero[xx][yy]=='.' || esFichaEnemiga(actual, tablero[xx][yy]))) {


				
				char [][] nuevoTablero= new char [6][6]; 
				nuevoEstado(nuevoTablero);
				char tem = tablero[xx][yy];
				nuevoTablero[xx][yy]=actual;
				
				nuevoTablero[x][y]='.';



				System.out.println("  " + actual);
			
				if (!isCheck (nuevoTablero,actual))
				
				{
					
					//verEstado(nuevoTablero);
				// falta la condicion de si ese movimiento deja en jaque a mi rey ojo ? ????  
				Nodo hijo = new Nodo( nuevoTablero, padre.getAltura()+1,padre);	        	

				hijos.add(hijo);
				}

				if(tem!='.' &&esFichaEnemiga(actual, tablero[xx][yy])) {break;};
				xx+=dx[dir];
				yy+=dy[dir];
				
				
				}

			// if(valid(xx,yy) && esReyEnemigo(actual,tablero[xx][yy]))
			// return true;
		}
		return false;
	}

	boolean recorrido1paso(int x, int y, int[] dx, int[] dy, int signoX) {
		final char actual=TableroCheck[x][y];
		for(int dir=0; dir<dx.length; dir++) {
			int xx = x+dx[dir]*signoX, yy = y+dy[dir];
			if(valid(xx,yy) && esReyEnemigo(actual, TableroCheck[xx][yy]))
				return true;
		}

		return false;
	}

	boolean recorridoMultiple(int x, int y, int[] dx, int[] dy) {
		final char actual=TableroCheck[x][y];
		for(int dir=0; dir<dx.length; dir++) {
			int xx=x+dx[dir], yy=y+dy[dir];
			while(valid(xx,yy) && TableroCheck[xx][yy]=='.') {
				xx+=dx[dir];
				yy+=dy[dir];
			}

			if(valid(xx,yy) && esReyEnemigo(actual,TableroCheck[xx][yy]))
				return true;
		}
		return false;
	}


	boolean tableroVacio() {
		for(int i=0; i<8; i++)
			for(int j=0; j<8; j++)
				if(TableroCheck[i][j]!='.') 
					return false;
		return true;
	}

	boolean esReyEnemigo(char actual, char otra) {	
		//verfica que la otra ficha sea un rey y luego que ambos no sean mayusculas ni minisculas  
		//es un xor que toma verdad cuando hay un true y un false mas no dos true o dos false
		return Character.toUpperCase(otra)=='K' &&
				(Character.isUpperCase(actual) ^ Character.isUpperCase(otra));

	}

	boolean esFichaEnemiga(char actual, char otra) {	
		//verifica que ambos no sean mayusculas 
		return   (Character.isUpperCase(actual) ^ Character.isUpperCase(otra));

	}

	boolean valid(int x, int y) {
		return x>=0 && x<6 && y>=0 && y<6;
	}

	boolean nuevoEstado(char [][] state) {


		for (int i=0;i<6;i++)
			for (int j=0;j<6;j++)
				state [i][j] =tablero[i][j];

		return false ; 
	}

	public Vector <Nodo> getHijos(){
	
		if (hijos.size()==0){// nodo hoja 
			
			padre.asignarMinMax(padre.funcionDeUtilidad());
		}
		return hijos;
		
	}

	public  String  verEstado(char [][] stateTablero) {

		String matriz = "";
		for (int i=0;i<6;i++){

			for (int j=0;j<6;j++){
				matriz+=stateTablero[i][j]+" ";

			}
			matriz+="\n";
		}

		System.out.println(matriz);
		return matriz;
		// TODO Auto-generated method stub

	}

}

