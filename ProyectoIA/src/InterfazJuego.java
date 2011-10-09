
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

import javax.swing.*;



public class InterfazJuego extends JFrame   {


	private static Lector LectorObj= new Lector();
	JPanel Celdas;
	JPanel inferiorIz;
	canvasGlobal canvasCampo;
	private String[][] matrizPadre;
	 
	//boton
	Button ejecutar,BotonAlgoritmo;
	//etiqueta de adorno

	JMenuBar barramenu;//la barra de arriva del juego 
	JMenu ayuda,archivo,cuadriculas, configuracion, busquedaInformada, busquedaNoInformada;//menus de la barra
	
	JRadioButtonMenuItem rbAmplitud, rbCosto, rbProfundidad, rbAvaro, rbAEstrella;
	
	
	ManejadorRadioButton manejadorRadioButton = new ManejadorRadioButton();
	
	InterfazJuego (){
		super("Animaci�n Algoritmos de Busqueda Informada y No Informada");


		barramenu= new JMenuBar();
		ayuda= new JMenu(" Ayuda ");
		archivo= new JMenu("Archivo");
		configuracion = new JMenu("Configuracion");
		busquedaInformada= new JMenu("Busqueda Informada");
		busquedaNoInformada= new JMenu("Busqueda No Informada");
		JMenuItem i1,i2,i3,i4,i5,i6;//menus item 
		//todo lo relacionado a la barra del menu
		i1= new JMenuItem("Nuevo Juego");
		i1.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {
			setVisible(false);
			new InterfazJuego().setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );	
		}});
		i2= new JMenuItem("Salir");
		i2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);	}});

		i3= new JMenuItem("acerca de.....");
		
		rbAmplitud = new JRadioButtonMenuItem("Preferente Por Amplitud");
		rbAmplitud.setSelected(true);
		rbCosto= new JRadioButtonMenuItem("Costo Uniforme");
		rbProfundidad= new JRadioButtonMenuItem("Preferente por profundidad Evitando Costos");
		rbAvaro= new JRadioButtonMenuItem("Avara");
		rbAEstrella= new JRadioButtonMenuItem("A*");
		
		rbAmplitud.addActionListener(manejadorRadioButton);
		rbCosto.addActionListener(manejadorRadioButton);
		rbProfundidad.addActionListener(manejadorRadioButton);
		rbAvaro.addActionListener(manejadorRadioButton);
		rbAEstrella.addActionListener(manejadorRadioButton);
		
		busquedaInformada.add(rbAmplitud);
		busquedaInformada.add(rbCosto);
		busquedaNoInformada.add(rbProfundidad);
		busquedaNoInformada.add(rbAvaro);
		busquedaNoInformada.add(rbAEstrella);

		configuracion.add(busquedaInformada);
		configuracion.add(busquedaNoInformada);
		//----------------------------------------------------------

		//--------------------------------------------------------------
		ayuda.add(i3);
		archivo.add(i1);
		archivo.add(i2);


		Font text9= new Font("Verdana", Font.BOLD, 12);
		barramenu.setFont(text9);
		barramenu.add(archivo);
		barramenu.add(configuracion);
		barramenu.add(ayuda);
		setLayout( new BorderLayout());
		setJMenuBar(barramenu);
		//aqui lo relacionado a la vantana
		Celdas = new JPanel();// un gridlayaut donde estan los canvas 
		inferiorIz= new JPanel(new  FlowLayout());//tiene el boton 
		inferiorIz.setBackground(Color.DARK_GRAY);

		ejecutar= new Button("Iniciar Recorrido" );

		BotonAlgoritmo= new Button("Realizar Busqueda");


		ejecutar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				canvasCampo.mover(2, 0);
				System.out.print("paso ");
			}

		}

		);
		

		canvasCampo= new canvasGlobal(7);  
		matrizPadre=LectorObj.leer("imagen/mapa.txt");
		System.out.print(matrizPadre);
		canvasCampo.sacarDatoscarros(matrizPadre);
		
		
		add(canvasCampo);
		pintarcelda(7);//tablero 7*7
		
		//------------------------------------------------------    

		//-----------------------------------------------------

		//add(Celdas,BorderLayout.CENTER);




		inferiorIz.add(ejecutar);

		inferiorIz.add(BotonAlgoritmo);

		inferiorIz.setBackground(Color.LIGHT_GRAY);

		add(inferiorIz,BorderLayout.SOUTH);


		setVisible(true);
	}


	protected void System(int k) {
		// TODO Auto-generated method stub

	}

	public void pintarcelda(int cantCuadros )
	{
		canvasCampo.setTam(cantCuadros);
		canvasCampo.repaint();

		this.setSize(cantCuadros*100+200,cantCuadros*100+300);
		
	}


	public static void main (String args  [])
	{

		InterfazJuego ven = new InterfazJuego();
		ven.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		
	}
	
	//para asegurar una sola seleccion
	private class ManejadorRadioButton implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			
			if(e.getSource()==rbAmplitud){
				rbAEstrella.setSelected(false);
				rbAvaro.setSelected(false);
				rbCosto.setSelected(false);
				rbProfundidad.setSelected(false);
			}
			else if(e.getSource()==rbAEstrella){
				rbAmplitud.setSelected(false);
				rbAvaro.setSelected(false);
				rbCosto.setSelected(false);
				rbProfundidad.setSelected(false);
			}
			else if(e.getSource()==rbAvaro){
				rbAmplitud.setSelected(false);
				rbAEstrella.setSelected(false);
				rbCosto.setSelected(false);
				rbProfundidad.setSelected(false);
			}
			else if(e.getSource()==rbCosto){
				rbAmplitud.setSelected(false);
				rbAvaro.setSelected(false);
				rbAEstrella.setSelected(false);
				rbProfundidad.setSelected(false);
			}
			else if(e.getSource()==rbProfundidad){
				rbAmplitud.setSelected(false);
				rbAvaro.setSelected(false);
				rbCosto.setSelected(false);
				rbAEstrella.setSelected(false);
			}
		}

	}


}
