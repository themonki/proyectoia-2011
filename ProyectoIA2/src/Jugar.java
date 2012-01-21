import java.util.Random;




public class Jugar {
	
	private char tablero[][] = new char[6][6];
	CheckTheCheck check = new CheckTheCheck();
	public Jugar(){
		generarPartida();
	}
	
	public char[][] getTablero() {
		return tablero;
	}

	public void setTablero(char[][] tablero) {
		this.tablero = tablero;
	}
	
	public void generarPartida(){
		for(int i = 0; i < 6; i ++)
			for(int j = 0; j < 6 ; j++ )
				tablero[i][j]= '.';
		
		int contador=0, max = 16;
		char fichas[] = {'k', 'K', 'p', 'P', 'p', 'P','p', 'P','p', 'P','q','Q','b','B','n','N'};
		Random randomx = new Random(), randomy ;
		int x = randomx.nextInt(6), y;
		
		
		randomy=new Random(42344543);
		y = randomy.nextInt(6);
		
		tablero[x][y]=fichas[contador];
		contador++;
		do{
			if(tablero[x][y]=='.'){
				tablero[x][y]= fichas[contador];
				if(!check.isCheck(tablero, 'k') && !check.isCheck(tablero, 'K')){
					contador++;
				}else{
					tablero[x][y]='.';
				}
			}		
			
			x=randomx.nextInt(6);
			y=randomy.nextInt(6);
			
			
		}while(contador<max);
		
		
		
	}
	
	/**
	 * En el segundo click
	 * el posxNow y el posyNow son la posicion de la ficha seleccionada (color verde)
	 * posx y posy son la posicion donde se va a mover
	 * pieza es la ficha seleccionada (color verde)
	 */
	public boolean comprobarMovimientos(char pieza, int posxNow, int posyNow, int posx, int posy ){
		String piezaConvertida = Character.toString(pieza);
		String elementoUbicar = Character.toString(tablero[posx][posy]);
		String minusculas = "bknpq";
		boolean fichaNegra= minusculas.contains(piezaConvertida);
		boolean fichaContrariaVacia;
		if(fichaNegra)
		{
			fichaContrariaVacia=!minusculas.contains(elementoUbicar) || elementoUbicar.contains(".");
		}else{
			fichaContrariaVacia= minusculas.contains(elementoUbicar) || elementoUbicar.contains(".");
		}	
		
		if(!fichaContrariaVacia){//si donde se va a mover NO ahi una ficha contraria o un espacio vacio
			return false;
		}
		int restax = Math.abs((posxNow-posx)), restay = Math.abs((posyNow-posy));
		
		if(pieza == 'p' || pieza == 'P'){//peon
			if(restay==0 && restax==1 && elementoUbicar.contains(".")){
				if(fichaNegra && posxNow<posx){
					return true;
				}else if(!fichaNegra && posx<posxNow){
					return true;
				}				
			}else if(restay==1 && restax==1 && !elementoUbicar.contains(".")){
				if(fichaNegra && posxNow<posx){
					return true;
				}else if(!fichaNegra && posx<posxNow){
					return true;
				}
			}
		}else if(pieza== 'k' || pieza == 'K'){//Rey
			if(restax<=1 && restay<=1){
				tablero[posx][posy] = tablero[posxNow][posyNow];
				tablero[posxNow][posyNow]=',';
				if(fichaNegra && !check.isCheck(tablero, 'k')){
					tablero[posxNow][posyNow] = tablero[posx][posy];
					tablero[posx][posy]=elementoUbicar.charAt(0);
					return true;
				}else if(!fichaNegra && !check.isCheck(tablero, 'K')){
					tablero[posxNow][posyNow] = tablero[posx][posy];
					tablero[posx][posy]=elementoUbicar.charAt(0);
					return true;
				}else{
					tablero[posxNow][posyNow] = tablero[posx][posy];
					tablero[posx][posy]=elementoUbicar.charAt(0);
				}
			}
		}else if(pieza== 'n' || pieza == 'N'){//caballo
			if(restax==1 && restay ==2){
				return true;
			}else if(restax == 2 && restay == 1){
				return true;
			}
		}else if(pieza== 'b' || pieza == 'B'){// alfil			
			if(restax==restay && comprobarCaminoLibreDiagonal(posxNow, posyNow, posx, posy)){				
				return true;
			}
		}else if(pieza== 'q' || pieza == 'Q'){// reina
			if(restax==restay && comprobarCaminoLibreDiagonal(posxNow, posyNow, posx, posy)){				
				return true;
			}else if(restax==0 && comprobarCaminoLibreHorizontal(posxNow, posyNow, posx, posy)){
				return true;
			}else if(restay==0 && comprobarCaminoLibreVertical(posxNow, posyNow, posx, posy)){				
				return true;
			}
		}
		
		return false;
	}
	
	public boolean comprobarCaminoLibreDiagonal(int iniciox, int inicioy, int finx, int finy){
		return comprobarCaminoLibre(iniciox, inicioy, finx, finy, 1, 1);
	}
	public boolean comprobarCaminoLibreHorizontal(int iniciox, int inicioy, int finx, int finy){
		return comprobarCaminoLibre(iniciox, inicioy, finx, finy, 0, 1);
	}
	public boolean comprobarCaminoLibreVertical(int iniciox, int inicioy, int finx, int finy){
		return comprobarCaminoLibre(iniciox, inicioy, finx, finy, 1, 0);
	}
	
	public boolean comprobarCaminoLibre(int iniciox, int inicioy, int finx, int finy, int moverx, int movery){
		int iteracionx =iniciox,iteraciony =inicioy, signox, signoy,divisor = Math.abs((finx-iniciox));
		
		if(divisor==0){signox=0;}else{signox=(finx-iniciox)/divisor;}
		divisor = Math.abs((finy-inicioy));
		if(divisor==0){signoy=0;}else{signoy=(finy-inicioy)/divisor;}
		while((iteracionx!=finx || iteraciony!=finy) && 
				(iteracionx<6 && iteraciony<6) && (iteracionx>=0 && iteraciony>=0)){			
			if((iteracionx!=iniciox || iteraciony!=inicioy) && tablero[iteracionx][iteraciony]!='.'){
				return false;
			}
			iteracionx=iteracionx + 1*moverx*signox;
			iteraciony=iteraciony + 1*movery*signoy;
		}	
		return true;
	}
	
	public void ganaMin(){
		
	}
	
	public void jugadaMax(){
		MiniMaxClass max = new MiniMaxClass(tablero,4);
		tablero = max.decisionMiniMax();
		CheckTheCheck check = new CheckTheCheck();
		check.verEstado(tablero);
	}

}
