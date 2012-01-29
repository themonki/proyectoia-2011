import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.BorderFactory;
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
	
	Vector < Vector <Integer>> posxYposy;
	Color colorFondo = new Color (50,50,110);
	
	Font fuenteEtiquetas =new Font("Algerian", Font.BOLD, 24);
	
	Color c [] = {Color.white,colorFondo }, colorViejo, colorSelect = new Color (90,250,90);
	Manejador manejador = new Manejador();
	boolean seleccionado = false, flagClick = false;//nota:cambiarlo a false
	int posSeleccionado [] = {-1,-1}, nivelAmateur=4 , nivelPrincipiante=2, nivel=nivelAmateur;
	JPanel panel;
	JLabel etiquetaSelect;
	Button botonJugadaSiguiente;
	java.awt.Dimension d = new java.awt.Dimension(100,100);//tamaño del cuadro
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
		super("Juego de Ajedrez para Aprendices.");
		juego = new Jugar();
		tablero = juego.getTablero();

		
		
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
		
		botonJugadaSiguiente = new Button("Siguiente Jugada");
		botonJugadaSiguiente.addActionListener(manejador);
		botonJugadaSiguiente.setEnabled(true);
		
		GridBagLayout gl = new GridBagLayout();
		panel.setLayout(gl);
		
		panel.setBorder(BorderFactory.createRaisedBevelBorder());
		
		
		
		cargarFondo();

				
		JPanel p1 = new JPanel(new java.awt.BorderLayout()), pboton = new JPanel();
		JPanel panelJuego = new JPanel(gl);
	
		 
		
		GridBagConstraints constrain = new GridBagConstraints();
		constrain.gridy=1;
		JLabel negras = new JLabel("Negras");
		negras.setFont(fuenteEtiquetas);
		panelJuego.add(negras, constrain);
		constrain.gridy=2;
		JLabel blancas = new JLabel("Blancas");
		blancas.setFont(fuenteEtiquetas);
		panelJuego.add(panel, constrain);
		constrain.gridy=3;
		panelJuego.add(blancas, constrain);
		
		pboton.add(botonJugadaSiguiente);
		
		p1.add(panelJuego, BorderLayout.CENTER);		
		p1.add(pboton, BorderLayout.SOUTH);
		
		contenedor.add(p1);
		
		cargarImagenes();
		
		
		setSize(900,900);
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
				temp.setBorder(BorderFactory.createLineBorder(Color.black));
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
	private void pintarPosibles(int posx, int posy) {
		UtilsChess utils= new UtilsChess();
		char pieza= tablero[posx] [posy];
		
		
		posxYposy= utils.posibilidadesFicha(tablero, pieza, posx, posy);
		
		
		for (int i=0 ;i<posxYposy.get(0).size();i++)
		{
			int pos=Math.abs(posxYposy.get(0).get(i)+posxYposy.get(1).get(i)*6);
			JLabel temp= (JLabel) panel.getComponent(pos);
			temp.setForeground(temp.getBackground());// jajaj truco :D
			temp.setBackground(colorSelect);
		
		}
		
	}
	
	private void borrarPosibles(){
		
		for (int i=0 ;i<posxYposy.get(0).size();i++)
		{
			int pos=Math.abs(posxYposy.get(0).get(i)+posxYposy.get(1).get(i)*6);
			JLabel temp= (JLabel) panel.getComponent(pos);
			
			temp.setBackground(temp.getForeground());
			
			
		
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
						borrarPosibles();
						seleccionado = false;
						if(posSeleccionado[0] == posx	&& posSeleccionado[1] == posy) {
							// no ahi cambios es el mismo
							etq.setBackground(colorViejo);							
							colorViejo = null;
							etiquetaSelect = null;
						}else {
							//no se puede mover ahi
							if(!juego.comprobarMovimientos(tablero[posSeleccionado[0]][posSeleccionado[1]],
									posSeleccionado[0],posSeleccionado[1], posx, posy)){
								JOptionPane.showMessageDialog(null, "No se puede mover :: Deja en Jaque a su REY");
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
							if(juego.ganaMin(tablero)){
								return;
							}
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
							
							pintarPosibles(posx,posy);
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
				botonJugadaSiguiente.setEnabled(false);
				juego.jugadaMax(nivel);
				tablero=juego.getTablero();
				cargarImagenes();
				amateur.setEnabled(false);
				principiante.setEnabled(false);
				if(juego.pierdeMin(tablero)){
					return;
				}
				flagClick=true;
			}else if(e.getSource()==amateur){
				amateur.setSelected(true);
				principiante.setSelected(false);
				nivel=nivelAmateur;
			}else if(e.getSource()==principiante){				
				amateur.setSelected(false);
				principiante.setSelected(true);
				nivel=nivelPrincipiante;
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
