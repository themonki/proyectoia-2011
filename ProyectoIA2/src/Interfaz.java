import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
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
import javax.swing.JTextArea;


@SuppressWarnings("serial")
/*Arreglar el mensaje , caso de empate */
public class Interfaz extends JFrame{
		
	Jugar juego;
	
	Vector < Vector <Integer>> posxYposy;
	String Mensaje="Jugada Min: \n";
	
	Color colorFondo = new Color (50,50,110);
	
	Font fuenteEtiquetas =new Font("Algerian", Font.BOLD, 20);
	
	
	JPanel ejex= new JPanel(),ejey= new JPanel(),ejex2= new JPanel(),ejey2= new JPanel();
	
	Color c [] = {Color.white,colorFondo }, colorViejo, colorSelect = new Color (90,250,90);
	
	Manejador manejador = new Manejador();
	boolean seleccionado = false, flagClick = false;//nota:cambiarlo a false
	int posSeleccionado [] = {-1,-1}, nivelAmateur=4 , nivelPrincipiante=2, nivel=nivelPrincipiante;
	JPanel panel, panelMuertasBlancas, panelMuertasNegras;
	JLabel etiquetaSelect;
	Button botonJugadaSiguiente;
	java.awt.Dimension d = new java.awt.Dimension(80,80);//tamaño del cuadro
	JRadioButtonMenuItem principiante, amateur;
	JMenuItem nuevoJuego;
	JTextArea areaTexto;
	
	
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
		//------------------Inicio Layout--------------------------
				GridBagLayout gl = new GridBagLayout();
		//-----------------Inicio Paneles ------------------------------------------
		Container contenedor = getContentPane();
		JPanel panelTableroConBordes= new JPanel(new BorderLayout());
		panel = new JPanel();
		panelMuertasNegras= new JPanel();
		panelMuertasBlancas= new JPanel();
		ejex= new JPanel();ejey= new JPanel();ejex2= new JPanel();ejey2= new JPanel();
		
		panelMuertasNegras.setBackground(Color.LIGHT_GRAY);
		panelMuertasBlancas.setBackground(Color.DARK_GRAY);

		JLabel temp = new JLabel(),tem2 = new JLabel();
		ImageIcon img = new ImageIcon("imagenes/"+((int) '.' )+".gif");		
		img.setImage(img.getImage().getScaledInstance(d.width-10, d.height-10, Image.SCALE_DEFAULT));
		temp.setIcon(img);
		tem2.setIcon(img);
		
		
		panelMuertasNegras.add(tem2);
		panelMuertasBlancas.add(temp);
		
	
		panel.setLayout(gl);
		
		panelTableroConBordes.setBorder(BorderFactory.createRaisedBevelBorder());
		
		JPanel p1 = new JPanel(new java.awt.BorderLayout()), pboton = new JPanel();
		JPanel panelJuego = new JPanel(gl);
		pboton.setLayout(( new BoxLayout( pboton, BoxLayout.PAGE_AXIS)));
		
		
		
		
		//------------------Inicio Menus-------------------------------------------------
		JMenuBar menu = new JMenuBar();
		JMenu archivo = new JMenu("Archivo"),dificultad = new JMenu("Dificultad");
		nuevoJuego = new JMenuItem("Nuevo Juego");
		principiante = new JRadioButtonMenuItem("principiante");
		amateur=new JRadioButtonMenuItem("Amateur");
		//------------------Inicio Botones-------------------------------------------
		botonJugadaSiguiente = new Button("Siguiente Jugada");
		botonJugadaSiguiente.addActionListener(manejador);
		botonJugadaSiguiente.setEnabled(true);
		
		
		//-------------------Inicio TexAreas----------------------------------------
		areaTexto= new JTextArea(5,7);
		areaTexto.setBackground(Color.black);
		areaTexto.setForeground(Color.GREEN);
		areaTexto.setFont(botonJugadaSiguiente.getFont());
		
		UtilsChess util = new UtilsChess();
		areaTexto.setText("::Partida :: \n  Tablero Inicial  \n"+util.verEstado(tablero));
		JScrollPane scrollAreaEntrada = new JScrollPane(areaTexto);
		areaTexto.setLineWrap(true);
		areaTexto.setEditable(false);
		
		
		//--------------------Crear Menu Bar------------------
		

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
		
		//----------- ----------------------------
		
		cargarBordes();// carga los bordes del tablero es decir las letras y los numeros  que estan a su alrededor
		
		cargarFondo();//carga el tablero vacio 
		
		cargarImagenes();//carga el panel con las fichas dependiendo del tablero 

				
		//-------------------------------------------
		
		
		//-------------ADD Panels
		
		GridBagConstraints constrain = new GridBagConstraints();
		constrain.gridy=1;
		JLabel negras = new JLabel("Negras");
		negras.setFont(fuenteEtiquetas);
		panelJuego.add(negras, constrain);
		constrain.gridy=2;
		JLabel blancas = new JLabel("Blancas");
		blancas.setFont(fuenteEtiquetas);
		panelJuego.add(panelTableroConBordes, constrain);
		constrain.gridy=3;
		panelJuego.add(blancas, constrain);
		
		pboton.add(botonJugadaSiguiente);
		pboton.add(scrollAreaEntrada);
		
		
		panelTableroConBordes.add(ejex,BorderLayout.SOUTH);
		panelTableroConBordes.add(ejex2,BorderLayout.NORTH);
		panelTableroConBordes.add(ejey,BorderLayout.WEST);
		panelTableroConBordes.add(ejey2,BorderLayout.EAST);
		panelTableroConBordes.add(panel,BorderLayout.CENTER);
		
		p1.add(panelJuego, BorderLayout.CENTER);		
		p1.add(panelMuertasNegras, BorderLayout.NORTH);
		p1.add(panelMuertasBlancas, BorderLayout.SOUTH);
		p1.add(pboton, BorderLayout.WEST);
		
		contenedor.add(p1);
		
		
		
		// -----------Init This------------
		
		setSize(900,900);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screenSize.width)/2-getWidth()/2,(screenSize.height)/2-getHeight()/2);
		setVisible(true);
		
	}
	
	private void cargarBordes()
	{
		ejex.setLayout(new GridLayout(1,6));
		ejex2.setLayout(new GridLayout(1,6));
		ejey2.setLayout(new GridLayout(6,1));
		ejey.setLayout(new GridLayout(6,1));
		
		String letras= "fedcba";
		
		for(int j = 5; j >= 0 ; j--){
			JLabel temp = new JLabel(""+letras.charAt(j),JLabel.CENTER);
			JLabel temp2 = new JLabel(""+letras.charAt(j),JLabel.CENTER);
			JLabel temp3 = new JLabel(""+(j+1),JLabel.CENTER);
			JLabel temp4 = new JLabel(""+(j+1),JLabel.CENTER);
			
			temp.setFont(botonJugadaSiguiente.getFont());
			temp2.setFont(botonJugadaSiguiente.getFont());
			temp3.setFont(botonJugadaSiguiente.getFont());
			temp4.setFont(botonJugadaSiguiente.getFont());
			
	
			//temp.setBorder(BorderFactory.createLineBorder(Color.black));
			ejex2.add(temp);
			ejex.add(temp2);
			ejey.add(temp3);
			ejey2.add(temp4);
		
		}
		
		
		
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
		cargarImagenesMuertas();
		
		
	}
	
	public void cargarImagenesMuertas()
	{
		for (int i=0 ;i<juego.negrasMuertas.size();i++)
		{
		
		JLabel temp = new JLabel();
		ImageIcon img = new ImageIcon("imagenes/"+((int) juego.negrasMuertas.get(i) )+".gif");		
		img.setImage(img.getImage().getScaledInstance(d.width-10, d.height-10, Image.SCALE_DEFAULT));
		temp.setIcon(img);
		//temp.setBorder(BorderFactory.createLineBorder(Color.black));
		panelMuertasNegras.add(temp);
		}		
		juego.negrasMuertas.clear();
		panelMuertasNegras.updateUI();
		
		
		for (int i=0 ;i<juego.blancasMuertas.size();i++)
		{
		
		JLabel temp = new JLabel();
		ImageIcon img = new ImageIcon("imagenes/"+((int) juego.blancasMuertas.get(i) )+".gif");		
		img.setImage(img.getImage().getScaledInstance(d.width-10, d.height-10, Image.SCALE_DEFAULT));
		temp.setIcon(img);
		//temp.setBorder(BorderFactory.createLineBorder(Color.black));
		panelMuertasBlancas.add(temp);
		}		
		juego.blancasMuertas.clear();
		panelMuertasBlancas.updateUI();
		
		
		
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
								JOptionPane.showMessageDialog(null, "Movimiento No Valido");
								seleccionado = true;
								return;
							}
							
							// se actualiza la matriz movimiento min
							etiquetaSelect.setBackground(colorViejo);
							
							
							String letras = "abcdef";
							Mensaje+="Movimiento ( "+juego.nombreFicha(tablero[posSeleccionado[0]][posSeleccionado[1]])+","+letras.charAt(posy) + (6-posx) + ") \n";
							 
							
							if(tablero[posx][posy] !='.' && tablero[posSeleccionado[0]][posSeleccionado[1]]!='.' )//hay muerta 
							{
								juego.blancasMuertas.add(tablero[posx][posy]);
								//JOptionPane.showMessageDialog(null, "Muerta::" +tablero[posx][posy]);
								
								Mensaje+="Muerte::" +juego.nombreFicha(tablero[posx][posy])+"\n";
								
							}
							
							 
							areaTexto.setText(areaTexto.getText()+Mensaje);
							Mensaje="Jugada Min: \n";
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
					}
					
					
					
					else {// primer click						
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
				
				areaTexto.setText(areaTexto.getText()+juego.getMensaje());
				
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
		JOptionPane.showMessageDialog(null, "Para Iniciar el juego debe dar click en el boton siguiente jugada");
		
		
	}

}
