

import java.io.*;
import java.util.*;

public class UtilsChess {

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
		

		
	

		//	        
		for(int i=0; i<6; i++) {
			for(int j=0; j<6; j++) {

				final char actual=tablero[i][j];

				
				if(Character.isUpperCase(actual) && isBlanca || (!Character.isUpperCase(actual) &&  !isBlanca ) ) 
				 {
					
					switch(Character.toUpperCase(actual)) {//convierte la letra a mayuscula y busca el caso correspondiente

					case 'P': //peon
						int signX= Character.isUpperCase(actual) ? -1 : 1;
						if(recorrido1pasoExpandir(i,j, dxPeon1, dyPeon1, signX))
							
						break;

					case 'N': //Caballo
						if(recorrido1pasoExpandir(i,j, dxCaballo, dyCaballo, 1))
							
						
						
						break;

					case 'B': //alfil
						if(recorridoMultipleExpandir(i,j, dxAlfil, dyAlfil))
							
						break;

					

					case 'Q': //reina
						
						if(recorridoMultipleExpandir(i,j, dxTorre, dyTorre) ||
								recorridoMultipleExpandir(i,j, dxAlfil, dyAlfil))
							
						
						break;
						
					case 'K': //Rey
						if(recorrido1pasoExpandir(i,j, dxRey, dyRey,1))
							
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
					
					boolean temo =Character.isUpperCase(comparador)^Character.isUpperCase(amenazante);
					//System.out.println("amena :: "+temo+comparador+amenazante);
					amenazante='.';
					if (temo==true) return true ;
					

					
				}
			}
		}


		
		
		return false;    
	}
	
	
	
	
	
	
	
	
	public  Vector<Vector<Integer>> posibilidadesFicha(char [][] tableroComparar,char ficha,int i, int j) {



		
		TableroCheck=tableroComparar;
		Vector <Vector <Integer >> dxYdy= new Vector<Vector<Integer>>();
		Vector <Integer > dxPos= new Vector<Integer>();
		Vector <Integer > dyPos= new Vector<Integer>();
		
		dxYdy.add(dxPos);		
		dxYdy.add(dyPos);

			

				switch(Character.toUpperCase(ficha)) {//convierte la letra a mayuscula y busca el caso correspondiente

				case 'P': //peon
					
					int signX= Character.isUpperCase(ficha) ? -1 : 1;
					recorrido1paso2(i,j, dxPeon1, dyPeon1, signX,dxYdy);
						
						return dxYdy;

				case 'N': //Caballo
					
					recorrido1paso2(i,j, dxCaballo, dyCaballo, 1,dxYdy);
						
					return dxYdy;

				case 'B': //alfil
					
					recorridoMultiple2(i,j, dxAlfil, dyAlfil,dxYdy);
						
						return dxYdy;

			
				case 'Q': //reina
					recorridoMultiple2(i,j, dxTorre, dyTorre,dxYdy) ;
							recorridoMultiple2(i,j, dxAlfil, dyAlfil,dxYdy);
						
						return dxYdy;
					
				case 'K': //Rey
					
					recorrido1paso2(i,j, dxRey, dyRey,1,dxYdy);
						
						return dxYdy;
				}
				
				
				return dxYdy;
			
			
		}
	
	
	 boolean recorrido1pasoExpandir(int x, int y, int[] dx, int[] dy, int signoX) {
		final char actual=tablero[x][y];
		for(int dir=0; dir<dx.length; dir++) {
			int xx = x+dx[dir]*signoX, yy = y+dy[dir];
			
			boolean peonCase=true ;
			if(Character.toUpperCase(actual)=='P' && valid(xx,yy)){
				
				if(dy[dir]==0)//voy de frente 
					peonCase=tablero[xx][yy]=='.';
				else { peonCase=esFichaEnemiga(actual, tablero[xx][yy]);}//mato de lado 
				
			};

			if(valid(xx,yy)&& peonCase  && (tablero[xx][yy]=='.'||esFichaEnemiga(actual, tablero[xx][yy])))// miro si es valida la posicion y si tengo uno que matar 
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
	
	boolean recorrido1paso2(int x, int y, int[] dx, int[] dy, int signoX,Vector <Vector <Integer >> dxYdy) {
		


		final char actual=TableroCheck[x][y];
		
		for(int dir=0; dir<dx.length; dir++) 
		{
			
			int xx = x+dx[dir]*signoX, yy = y+dy[dir];
			boolean peonCase=true ;
			if(Character.toUpperCase(actual)=='P' && valid(xx,yy)){
				if(dy[dir]==0)//voy de frente 
					peonCase=TableroCheck[xx][yy]=='.';
				else peonCase=esFichaEnemiga(actual, TableroCheck[xx][yy]);//mato de lado 
				
			};
			
			
			if(valid(xx,yy) && peonCase && (esFichaEnemiga(actual, TableroCheck[xx][yy])||TableroCheck[xx][yy]=='.'))
			{
				
				dxYdy.get(0).add(xx);
				dxYdy.get(1).add(yy);
			
			}
				
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
	
	boolean recorridoMultiple2(int x, int y, int[] dx, int[] dy,Vector <Vector <Integer >> dxYdy) {
		final char actual=TableroCheck[x][y];
		for(int dir=0; dir<dx.length; dir++) {
			int xx=x+dx[dir], yy=y+dy[dir];
			while(valid(xx,yy) && TableroCheck[xx][yy]=='.') {
				
				dxYdy.get(0).add(xx);
				dxYdy.get(1).add(yy);
				xx+=dx[dir];
				yy+=dy[dir];
				
			}

			if(valid(xx,yy) && esFichaEnemiga(actual,TableroCheck[xx][yy])){
				dxYdy.get(0).add(xx);
				dxYdy.get(1).add(yy);
				}
		}
		return false;
	}


	

	boolean esReyEnemigo(char actual, char otra) {	
		//verfica que la otra ficha sea un rey y luego que ambos no sean mayusculas ni minisculas  
		//es un xor que toma verdad cuando hay un true y un false mas no dos true o dos false
		return Character.toUpperCase(otra)=='K' &&
				(Character.isUpperCase(actual) ^ Character.isUpperCase(otra));

	}

	boolean esFichaEnemiga(char actual, char otra) {	
		//verifica que ambos no sean mayusculas 
		return   ( otra!='.'&& actual!='.' && (Character.isUpperCase(actual) ^ Character.isUpperCase(otra)));

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
			if(padre.getIsMax()) {
				
				padre.asignarMinMax(padre.funcionDeUtilidad(-1));}
			else {
				
				padre.asignarMinMax(padre.funcionDeUtilidad(1));
				
				
			}
			
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

