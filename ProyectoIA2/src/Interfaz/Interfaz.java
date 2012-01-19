package Interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


@SuppressWarnings("serial")
public class Interfaz extends JFrame{
	
	JPanel panel;
	Color c [] = {Color.white, Color.GRAY}, colorViejo, colorSelect = Color.green;
	Manejador manejador = new Manejador();
	boolean seleccionado = false, flagClick = true;
	int posSeleccionado [] = {-1,-1};
	JLabel etiquetaSelect;
	JButton botonJugadaSiguiente;
	
	private char tablero[][] = new char[6][6];
	
	public char[][] getTablero() {
		return tablero;
	}

	public void setTablero(char[][] tablero) {
		this.tablero = tablero;
	}	
	
	public void cargarImagenes(){
		int contador = 0;
		for(int i = 0; i < 6 ; i++){
			for(int j = 0; j < 6 ; j++){
				JLabel temp = (JLabel)(panel.getComponent(contador));
				ImageIcon img = new ImageIcon("imagenes/"+tablero[j][i]+".gif");		
				//temp.setIcon(img);				
				java.awt.Dimension d = new java.awt.Dimension(80,80);
				if(d.width>0 && d.height>0){
					img.setImage(img.getImage().getScaledInstance(d.width, d.height, Image.SCALE_DEFAULT));
					temp.setIcon(img);
				}
				contador++;
			}
		}
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
				
		panel = new JPanel();
		JScrollPane scroll = new JScrollPane(panel);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		botonJugadaSiguiente = new JButton("Siguiente Jugada");
		botonJugadaSiguiente.addActionListener(manejador);
		
		GridBagLayout gl = new GridBagLayout();
		panel.setLayout(gl);
		cargarFondo();

				
		JPanel p1 = new JPanel(new java.awt.BorderLayout());
		p1.add(scroll, BorderLayout.CENTER);
		p1.add(botonJugadaSiguiente, BorderLayout.SOUTH);
		contenedor.add(p1, BorderLayout.CENTER);
		
		cargarImagenes();
		
		
		setSize(600,800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
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
	 * 
	 */
	public void posiblesMovimientos(){
		
	}
	
	
	/**
	 * En el segundo click
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
			fichaContrariaVacia=minusculas.contains(elementoUbicar)|| elementoUbicar.contains(".");
			}
		
		
		
		if(pieza == 'p' || pieza == 'P'){//peon
			
		}else if(pieza== 'k' || pieza == 'K'){//Rey			
			if(fichaNegra && fichaContrariaVacia){
				int restax = Math.abs((posxNow-posx)), restay = Math.abs((posyNow-posy));
				if(restax<=1){
					if(restay<=1){
						return true;
					}
				}
			}else if(!fichaNegra && fichaContrariaVacia){
				int restax = Math.abs((posxNow-posx)), restay = Math.abs((posyNow-posy));
				if(restax<=1){
					if(restay<=1){
						return true;
					}
				}
			}
		}else if(pieza== 'n' || pieza == 'N'){//caballo
			
		}else if(pieza== 'b' || pieza == 'B'){// alfil
			
		}else if(pieza== 'q' || pieza == 'Q'){// reina
			
		}
		
		return false;
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

						}
					} else {// primer click
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
			flagClick=true;
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
