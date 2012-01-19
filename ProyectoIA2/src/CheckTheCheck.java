
import java.io.*;
import java.util.*;

public class CheckTheCheck {

	static char[][] tablero ={
		{'.','.','.','.','.','.'} ,
		{'.','.','.','.','.','.'},
		{'.','.','.','.','.','.'},
		{'.','.','.','.','k','.'},
		{'.','.','.','.','.','.'},
		{'.','.','.','.','.','.'},};

	static int[] dxPeon = {+1,+1,+1};
	static int[] dyPeon = {-1,+1,0};
	static int[] dxCaballo = {-2,-1,+2,+1,+2,+1,-2,-1};
	static int[] dyCaballo = {-1,-2,-1,-2,+1,+2,+1,+2};

	static int[] dxTorre = { 0, 0,-1,+1};
	static int[] dyTorre = {-1,+1, 0, 0};
	static int[] dxAlfil = {-1,+1,-1,+1};
	static int[] dyAlfil = {-1,-1,+1,+1};
	
	static int[] dyRey = {0,0,1,-1,+1,-1,+1,-1};
	static int[] dxRey = {1,-1,0,0,+1,-1,-1,+1};
	

	static Vector <Nodo> hijos= new Vector<Nodo>() ; 





	public  void expandir(int altura) {



		boolean isBlanca;
		if (altura%2==0)  isBlanca=true; else isBlanca=false   ;
		System.out.println(isBlanca);

		char amenazante='.';

		//	        
		for(int i=0; i<6; i++) {
			for(int j=0; j<6; j++) {

				final char actual=tablero[i][j];

				
				if(Character.isUpperCase(actual) && !isBlanca || (!Character.isUpperCase(actual) &&  isBlanca ) ) ;
				else {
					
					switch(Character.toUpperCase(actual)) {//convierte la letra a mayuscula y busca el caso correspondiente

					case 'P': //peon
						int signX= Character.isUpperCase(actual) ? -1 : 1;
						if(recorrido1pasoExpandir(i,j, dxPeon, dyPeon, signX,false))
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
						if(recorridoMultipleExpandir(i,j, dxTorre, dyTorre) ||
								recorridoMultipleExpandir(i,j, dxAlfil, dyAlfil))
							amenazante=actual;
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






	public  void isCheck() {



		// for(int caseid=1; ; caseid++) {

		if(tableroVacio()) return;
		//readln(); //linea de separacion entre casos de entrada

		char amenazante='.';

		//outer:	        
		for(int i=0; i<8; i++) {
			for(int j=0; j<8; j++) {

				final char actual=tablero[i][j];

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
				}
				if(amenazante!='.') {
					System.err.println("amenazante at "+i+","+j+" : "+amenazante);

					break ;
				}
			}
		}


		if(amenazante=='.')// no encontro un amenazante 
			System.out.println("no king is in check.");
		else {//si encontro un amenazante  si es mayuscula el amenazante  entonces  el amenazado el es rey negro 
			String amenazado = Character.isUpperCase(amenazante) ? "black" : "white";
			System.out.println(amenazado+" king is in check. "+ amenazante);
		}    
	}



	static boolean recorrido1pasoExpandir(int x, int y, int[] dx, int[] dy, int signoX,boolean isCaballo) {
		final char actual=tablero[x][y];
		for(int dir=0; dir<dx.length; dir++) {
			int xx = x+dx[dir]*signoX, yy = y+dy[dir];

			if(valid(xx,yy)&& dy[dir]!=0 && esFichaEnemiga(actual, tablero[xx][yy]))// miro si es valida la posicion y si tengo uno que matar 
			{


				char [][] nuevoTablero= new char [6][6]; 
				nuevoEstado(nuevoTablero);
				nuevoTablero[xx][yy]=actual;
				nuevoTablero[x][y]='.';

				verEstado(nuevoTablero);
				// falta la condicion de si ese movimiento deja en jaque a mi rey ojo ? ????  
				Nodo hijo = new Nodo( nuevoTablero);	        	
				hijos.add(hijo);

			}

			if (valid(xx,yy) && ((dy[dir]==0  && tablero[xx][yy]=='.') || isCaballo) )// en caso de que se pueda mover a su frente 
			{

				char [][] nuevoTablero= new char [6][6]; 
				nuevoEstado(nuevoTablero);
				nuevoTablero[xx][yy]=actual;
				//5141989 gustavo 
				
				nuevoTablero[x][y]='.';

				verEstado(nuevoTablero);
				// falta la condicion de si ese movimiento deja en jaque a mi rey ojo ? ????  
				Nodo hijo = new Nodo( nuevoTablero);	        	
				hijos.add(hijo);	
			}   
		}
		return false;
	}

	static boolean recorridoMultipleExpandir(int x, int y, int[] dx, int[] dy) {
		final char actual=tablero[x][y];

		for(int dir=0; dir<dx.length; dir++) {
			int xx=x+dx[dir], yy=y+dy[dir];
			while(valid(xx,yy) && (tablero[xx][yy]=='.' || esFichaEnemiga(actual, tablero[xx][yy]))) {


				char [][] nuevoTablero= new char [6][6]; 
				nuevoEstado(nuevoTablero);
				nuevoTablero[xx][yy]=actual;
				nuevoTablero[x][y]='.';



				verEstado(nuevoTablero);
				// falta la condicion de si ese movimiento deja en jaque a mi rey ojo ? ????  
				Nodo hijo = new Nodo( nuevoTablero);	        	

				hijos.add(hijo);

				xx+=dx[dir];
				yy+=dy[dir];
			}

			// if(valid(xx,yy) && esReyEnemigo(actual,tablero[xx][yy]))
			// return true;
		}
		return false;
	}

	static boolean recorrido1paso(int x, int y, int[] dx, int[] dy, int signoX) {
		final char actual=tablero[x][y];
		for(int dir=0; dir<dx.length; dir++) {
			int xx = x+dx[dir]*signoX, yy = y+dy[dir];
			if(valid(xx,yy) && esReyEnemigo(actual, tablero[xx][yy]))
				return true;
		}

		return false;
	}

	static boolean recorridoMultiple(int x, int y, int[] dx, int[] dy) {
		final char actual=tablero[x][y];
		for(int dir=0; dir<dx.length; dir++) {
			int xx=x+dx[dir], yy=y+dy[dir];
			while(valid(xx,yy) && tablero[xx][yy]=='.') {
				xx+=dx[dir];
				yy+=dy[dir];
			}

			if(valid(xx,yy) && esReyEnemigo(actual,tablero[xx][yy]))
				return true;
		}
		return false;
	}


	static boolean tableroVacio() {
		for(int i=0; i<8; i++)
			for(int j=0; j<8; j++)
				if(tablero[i][j]!='.') 
					return false;
		return true;
	}

	static boolean esReyEnemigo(char actual, char otra) {	
		//verfica que la otra ficha sea un rey y luego que ambos no sean mayusculas ni minisculas  
		//es un xor que toma verdad cuando hay un true y un false mas no dos true o dos false
		return Character.toUpperCase(otra)=='K' &&
				(Character.isUpperCase(actual) ^ Character.isUpperCase(otra));

	}

	static boolean esFichaEnemiga(char actual, char otra) {	
		//verifica que ambos no sean mayusculas 
		return   (Character.isUpperCase(actual) ^ Character.isUpperCase(otra));

	}

	static boolean valid(int x, int y) {
		return x>=0 && x<6 && y>=0 && y<6;
	}

	static boolean nuevoEstado(char [][] state) {


		for (int i=0;i<6;i++)
			for (int j=0;j<6;j++)
				state [i][j] =tablero[i][j];

		return false ; 
	}

	public static void main (String args []){
		CheckTheCheck alo = new CheckTheCheck();

		alo.expandir(1);


	}

	public static String  verEstado(char [][] stateTablero) {

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