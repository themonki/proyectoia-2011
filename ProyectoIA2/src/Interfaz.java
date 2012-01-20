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


@SuppressWarnings("serial")
public class Interfaz extends JFrame{
		
	Jugar juego;
	Color c [] = {Color.white, Color.GRAY}, colorViejo, colorSelect = Color.green;
	Manejador manejador = new Manejador();
	boolean seleccionado = false, flagClick = true;//nota:cambiarlo a false
	int posSeleccionado [] = {-1,-1};
	JPanel panel;
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
		juego = new Jugar();
		tablero = juego.getTablero();
		
		for(int i = 0; i < 6; i ++)
			for(int j = 0; j < 6 ; j++ )
				tablero[i][j]= '.';
		
		tablero[0][0]='K';
		tablero[1][1]='Q';
		tablero[2][0]='P';
		tablero[2][1]='N';
		tablero[3][3]='B';
		tablero[4][4]='k';
		tablero[5][5]='q';
		tablero[2][4]='p';
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
				img.setImage(img.getImage().getScaledInstance(d.width, d.height, Image.SCALE_DEFAULT));
				temp.setIcon(img);				
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
							if(!juego.comprobarMovimientos(tablero[posSeleccionado[0]][posSeleccionado[1]],
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
				juego.jugadaMax();
				tablero=juego.getTablero();
				cargarImagenes();			
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
