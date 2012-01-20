package Interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;


@SuppressWarnings("serial")
public class Interfaz extends JFrame{
	
	JPanel panel;
	Color c [] = {Color.white, Color.DARK_GRAY}, colorViejo, colorSelect = Color.green;
	Manejador manejador = new Manejador();
	boolean seleccionado = false, flagClick = true;//nota:cambiarlo a false
	int posSeleccionado [] = {-1,-1};
	JLabel etiquetaSelect;
	JButton botonJugadaSiguiente;
	java.awt.Dimension d = new java.awt.Dimension(70,110);//tamaño del cuadro
	JRadioButtonMenuItem principiante, amateur;
	JMenuItem nuevoJuego;
	
	private char tablero[][] = new char[6][6];
	
	public char[][] getTablero() {
		return tablero;
	}

	public void setTablero(char[][] tablero) {
		this.tablero = tablero;
	}	
	
	public Interfaz(){
		super();
		
		for(int i = 0; i < 6; i ++)
			for(int j = 0; j < 6 ; j++ )
				tablero[i][j]= '.';
		
		tablero[0][0]='K';
		tablero[1][1]='Q';
		tablero[2][0]='P';
		tablero[2][1]='N';
		tablero[3][2]='B';
		tablero[4][4]='k';
		tablero[5][5]='q';
		tablero[2][5]='p';
		tablero[1][4]='n';
		tablero[0][5]='b';
		
		initComponet();		
	}
		
	public void initComponet(){
		Container contenedor = getContentPane();
		
		JMenuBar menu = new JMenuBar();
		JMenu archivo = new JMenu("Archivo"),dificultad = new JMenu("Dificultad");
		nuevoJuego = new JMenuItem("Nuevo Juego");
		principiante = new JRadioButtonMenuItem("principiante");
		amateur=new JRadioButtonMenuItem("Amateur");
		
		principiante.setSelected(true);
		
		principiante.addActionListener(manejador);
		amateur.addActionListener(manejador);
		nuevoJuego.addActionListener(manejador);
		
		dificultad.add(principiante);
		dificultad.add(amateur);
		archivo.add(nuevoJuego);
		archivo.add(dificultad);
		menu.add(archivo);
		this.setJMenuBar(menu);
		
				
		panel = new JPanel();
		//JScrollPane scroll = new JScrollPane(panel);
		//scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		//scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		botonJugadaSiguiente = new JButton("Siguiente Jugada");
		botonJugadaSiguiente.addActionListener(manejador);
		botonJugadaSiguiente.setEnabled(false);
		
		GridBagLayout gl = new GridBagLayout();
		panel.setLayout(gl);
		cargarFondo();

				
		JPanel p1 = new JPanel(new java.awt.BorderLayout()), pboton = new JPanel();
		JPanel panelJuego = new JPanel(gl);
		
		GridBagConstraints constrain = new GridBagConstraints();
		constrain.gridy=1;
		panelJuego.add(new JLabel("Negras"), constrain);
		constrain.gridy=2;
		panelJuego.add(panel, constrain);
		constrain.gridy=3;
		panelJuego.add(new JLabel("Blancas"), constrain);
		
		pboton.add(botonJugadaSiguiente);
		
		p1.add(panelJuego, BorderLayout.CENTER);		
		p1.add(pboton, BorderLayout.SOUTH);
		
		contenedor.add(p1);
		
		cargarImagenes();
		
		
		setSize(600,800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screenSize.width)/2-getWidth()/2,(screenSize.height)/2-getHeight()/2);
		setVisible(true);
		
	}
	
	public void cargarImagenes(){
		int contador = 0;
		for(int i = 0; i < 6 ; i++){
			for(int j = 0; j < 6 ; j++){
				JLabel temp = (JLabel)(panel.getComponent(contador));
				ImageIcon img = new ImageIcon("imagenes/"+((int)tablero[j][i])+".gif");		
				//temp.setIcon(img);				
				if(d.width>0 && d.height>0){
					img.setImage(img.getImage().getScaledInstance(d.width, d.height, Image.SCALE_DEFAULT));
					temp.setIcon(img);
				}
				contador++;
			}
		}
	}
	
	public void cargarFondo(){
		GridBagConstraints constrain = new GridBagConstraints();
		
		int selecFondo = 0;
		for(int i = 0; i < 6;i++){
			for(int j = 0; j < 6 ; j++){				
				JLabel etq = new JLabel();
				etq.setOpaque(true);				
				etq.setBackground(c[selecFondo%2]);
				etq.addMouseListener(manejador);
				constrain.gridy=j;
				panel.add(etq, constrain);//agregar evento
				selecFondo++;
			}
			selecFondo--;
		}
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
	
	private class Manejador implements MouseListener, ActionListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			if(!flagClick){
				return;
			}
			JLabel etq = (JLabel)e.getSource();
			int contador=0;
			for(int posy = 0; posy < 6 ;posy++)
				for(int posx = 0; posx < 6 ; posx++ ){
				if (etq == panel.getComponent(contador)) {
					if (seleccionado){//segundo click
						seleccionado = false;
						if (posSeleccionado[0] == posx	&& posSeleccionado[1] == posy) {
							// no ahi cambios es el mismo
							etq.setBackground(colorViejo);							
							colorViejo = null;
							etiquetaSelect = null;
						} else {
							//no se puede mover ahi
							if(!comprobarMovimientos(tablero[posSeleccionado[0]][posSeleccionado[1]],
									posSeleccionado[0],posSeleccionado[1], posx, posy)){
								JOptionPane.showMessageDialog(null, "No se puede mover");
								seleccionado = true;
								return;
							}
							
							// se actualiza la matriz
							etiquetaSelect.setBackground(colorViejo);
							tablero[posx][posy] = tablero[posSeleccionado[0]][posSeleccionado[1]];
							tablero[posSeleccionado[0]][posSeleccionado[1]] = '.';
							cargarImagenes();
							posSeleccionado[0] = -1;
							posSeleccionado[1] = -1;
							colorViejo = null;
							etiquetaSelect = null;
							flagClick=false;
							botonJugadaSiguiente.setEnabled(true);
						}
					} else {// primer click
						String may = "BKNPQ";
						if(may.contains(Character.toString(tablero[posx][posy]))){
							return;// PARA NO MOVER LAS FICHAS BLANCAS
						}
						if (tablero[posx][posy] != '.'){
							etiquetaSelect = etq;
							colorViejo = etiquetaSelect.getBackground();
							etiquetaSelect.setBackground(colorSelect);
							seleccionado = true;
							posSeleccionado[0] = posx;
							posSeleccionado[1] = posy;
						}
					}
					return;
				}
				contador++;
			}
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			// este seria para el boton de movimiento maquina
			if(e.getSource()==botonJugadaSiguiente){
				flagClick=true;
				botonJugadaSiguiente.setEnabled(false);
			}else if(e.getSource()==amateur){
				amateur.setSelected(true);
				principiante.setSelected(false);
			}else if(e.getSource()==principiante){				
				amateur.setSelected(false);
				principiante.setSelected(true);
			}else if(e.getSource()==nuevoJuego){
				dispose();
				new Interfaz();
			}
			

		}
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Interfaz();
		
		
	}

}
