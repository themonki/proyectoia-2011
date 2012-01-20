


public class Jugar {
	
	private char tablero[][] = new char[6][6];
	
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
			if(restax<=1 && restay<=1 ){
				return true;				
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
	
	public void jugadaMax(){
		MiniMaxClass max = new MiniMaxClass(tablero);
		tablero = max.decisionMiniMax();		
	}

}
