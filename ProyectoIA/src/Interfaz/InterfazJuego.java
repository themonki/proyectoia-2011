package Interfaz;


import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Vector;

import javax.swing.*;

import Arbol.Nodo;
import Busquedas.BusquedaAasterisco;
import Busquedas.BusquedaAmplitud;
import Busquedas.BusquedaCosto;
import Busquedas.BusquedaProfundidaSinCiclos;
import Busquedas.BusquedadAvara;



public class InterfazJuego extends JFrame   {


	private static Lector LectorObj;
	JPanel Celdas;
	JPanel inferiorIz;
	canvasGlobal canvasCampo;
	private String[][] matrizPadre;
	Vector <Vector <String>> resultado;
	JTextArea areaEntrada;
	JTextArea areaMovimiento;
	
	 
	//boton
	Button ejecutar,BotonAlgoritmo;

	JMenuBar barramenu;//la barra de arriva del juego 
	
	JMenu archivo,cuadriculas, configuracion, busquedaInformada, busquedaNoInformada;//menus de la barra
	
	JRadioButtonMenuItem rbAmplitud, rbCosto, rbProfundidad, rbAvaro, rbAEstrella;
	
	JMenuItem seleccionarArchivo;
	ManejadorRadioButton manejadorRadioButton = new ManejadorRadioButton();
	
	static String dirArchivo="prueba1.txt";
		
	InterfazJuego (){
		super("Animación Algoritmos de Busqueda Informada y No Informada");

		
		 LectorObj= new Lector();

		barramenu= new JMenuBar();
		//ayuda= new JMenu(" Ayuda ");
		archivo= new JMenu("Archivo");
		configuracion = new JMenu("Configuracion");
		busquedaInformada= new JMenu("Busqueda Informada");
		busquedaNoInformada= new JMenu("Busqueda No Informada");
		
		//-------------------------------------------------------------------------------------
		JMenuItem i1,i2;//menus item 
		//todo lo relacionado a la barra del menu
		i1= new JMenuItem("Nuevo ");
		i2= new JMenuItem("Salir");
		seleccionarArchivo=new JMenuItem("Cargar Archivo");
		i1.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {
			setVisible(false);
			dispose();
			new InterfazJuego().setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
			
		}});
		
		i2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					}});
		seleccionarArchivo.addActionListener(new ManejadorButton());
		//-----------------------------------------------------------------------------------------

		
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
		
		archivo.add(i1);
		archivo.add(seleccionarArchivo);
		archivo.add(i2);


		barramenu.add(archivo);
		barramenu.add(configuracion);
		//barramenu.add(ayuda);
		
		//-----------------------------------------------
		setLayout( new BorderLayout());
		setJMenuBar(barramenu);
		
		//aqui lo relacionado a la vantana
		inferiorIz= new JPanel();//tiene el boton 
		inferiorIz.setBackground(Color.DARK_GRAY);

		
		    
		 
		
		 areaEntrada= new JTextArea("Matriz Entrada \n",20,20);
		
		areaMovimiento= new JTextArea("Info Del Algoritmo \n",20,20);
		
		Font fontSubrayados = new Font("Book Antiqua", Font.BOLD, 16);
		areaMovimiento.setFont(fontSubrayados);
		areaEntrada.setFont(fontSubrayados);

		
		JPanel panelaux= new JPanel( );
		JScrollPane scrollAreaEntrada = new JScrollPane(areaEntrada);
		areaEntrada.setLineWrap(true);
		areaEntrada.setEditable(false);
		JScrollPane scrollAreaMovimiento = new JScrollPane(areaMovimiento);
		areaMovimiento.setLineWrap(true);
		areaMovimiento.setEditable(false);

		panelaux.setLayout(new BoxLayout(panelaux, BoxLayout.Y_AXIS));
		ejecutar= new Button("Iniciar Recorrido" );
		ejecutar.setEnabled(false);

		BotonAlgoritmo= new Button("Realizar Busqueda");


		ejecutar.addActionListener(new ManejadorButton () );
		BotonAlgoritmo.addActionListener(new ManejadorButton ());
		
	
		canvasCampo= new canvasGlobal(7);  
		
				
		matrizPadre=LectorObj.leer(dirArchivo);
		
		
		canvasCampo.sacarDatoscarros(matrizPadre);
		
		
		add(canvasCampo);
		
		pintarcelda(7);//tablero 7*7
		


		panelaux.add(scrollAreaEntrada);
		panelaux.add(scrollAreaMovimiento);
		inferiorIz.add(ejecutar);

		inferiorIz.add(BotonAlgoritmo);
		
		inferiorIz.setBackground(Color.WHITE);


		add(panelaux,BorderLayout.EAST);

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

		this.setSize(cantCuadros*100+200+200+50,cantCuadros*100+200);
		
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
	private class ManejadorButton implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			
			if(e.getSource()==BotonAlgoritmo){	
				long a=System.currentTimeMillis();//tiempo actual 
			
				String stringmovimientos="";// para ver los movimientos resultantes
				String stringMatrizEntrada = "";// para ver la matriz ingresada
				
				Nodo raiz = new Nodo();
				raiz.setPadre(null);
				raiz.setCosto(0);
				raiz.setProfundidad(0);
				raiz.setContenido(matrizPadre);
				raiz.sacarDatoscarros();
				Nodo.CANTIDAD_NODOS++;
				Nodo solucion=new Nodo();
				
				
				
				//LLAMADO DE LOS ALGORITMOS dependiendo del que este seleccionando
				if(rbAmplitud.isSelected())
				{ 
					BusquedaAmplitud algoritmo = new BusquedaAmplitud(raiz);
					 solucion =algoritmo.realizarBusqueda();
					 stringmovimientos="Algoritmo: Busqueda Por Amplitud \n";
				}
				else if(rbCosto.isSelected())
				{ 
					 BusquedaCosto  algoritmo = new BusquedaCosto(raiz);
					 solucion=algoritmo.realizarBusqueda();
					 stringmovimientos="Algoritmo: Busqueda Por Costo \n";
				}
				else if(rbProfundidad.isSelected())
				{ 
					 BusquedaProfundidaSinCiclos  algoritmo = new BusquedaProfundidaSinCiclos(raiz);
					 solucion=algoritmo.realizarBusqueda();
					 stringmovimientos="Algoritmo: Busqueda Por Profundida Evitando Ciclos \n";
				}
				else if(rbAEstrella.isSelected())
				{ 
					 BusquedaAasterisco  algoritmo = new BusquedaAasterisco(raiz);
					 solucion=algoritmo.realizarBusqueda();
					 stringmovimientos="Algoritmo: A* \n";
				}
				else if(rbAvaro.isSelected())
				{ 
					 BusquedadAvara  algoritmo = new BusquedadAvara(raiz);
					 solucion=algoritmo.realizarBusqueda();
					 stringmovimientos="Algoritmo: Busqueda Avara \n";
				}
				else {
					
					mensajeNoSeleccion("Seleccione El Algoritmo de Busqueda");
					
					return;
				}
				
				
				long b=System.currentTimeMillis();//tiempo actual 
				stringmovimientos+="Tiempo de busqueda (ms) : " +(b-a)+"\n";
				
				stringmovimientos+="Costo : " +solucion.getCosto()+"\n";
				stringmovimientos+="Profundida : " +solucion.getProfundidad()+"\n";
				stringmovimientos+="Nodos Expan. : " +solucion.getNodosExpandidos()+"\n";
				stringmovimientos+="Nodos Creados. : " +solucion.getNodosCreados()+"\n";
				
				resultado =solucion.RETORNAR_MOVIMIENTO();
				Vector<String> mover= new Vector<String>();
				mover.add("A");
				mover.add("derecha");
				mover.add("7");
				
				resultado.add(mover);
				
				for (int i=0;i<7;i++){
					
					
					for (int j=0 ;j<7;j++)
						
						stringMatrizEntrada+= " " +matrizPadre[i][j];
					
					
					stringMatrizEntrada+="\n";
				}
				
				areaEntrada.setText(areaEntrada.getText()+stringMatrizEntrada);
				
				
				// para ver los movimientos retornados
				for (int i=0;i<resultado.size();i++){
					
					 stringmovimientos+="Movimiento #"+i+" : " + resultado.get(i).get(0)+ "   "+resultado.get(i).get(1)+"   "+resultado.get(i).get(2) +"\n";
					
				};
				
				areaMovimiento.setText(areaMovimiento.getText()+stringmovimientos);
				raiz.iniEstadoCarros();// PROBLEMAS POR SER UNA VARIABLE ESTATICA 
				raiz=null;
				ejecutar.setEnabled(true);
			
			
			}else if(e.getSource()==ejecutar){
				
				canvasCampo.setResultadoMov(resultado);
				canvasCampo.run();	
				
				
				
				
				
			}else if(e.getSource()==seleccionarArchivo){
				
				JFileChooser manager = new JFileChooser(System.getProperty("user.dir"));
				manager.setDialogTitle("Seleccionar Archivo de juego");
				manager.setFileSelectionMode(JFileChooser.FILES_ONLY);
				manager.setApproveButtonText("Cargar");
				int returnVal = manager.showSaveDialog(new JFrame());
				if (returnVal == JFileChooser.APPROVE_OPTION) {//si selecciona guardar
					File file = manager.getSelectedFile();
					dirArchivo=  file.getAbsolutePath();
					//cargar el canvas!!
					setVisible(false);
					new InterfazJuego().setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
					dispose();
				}
				
			}
		}
		
		
	}
	public void mensajeNoSeleccion(String mensaje) {
		JOptionPane.showMessageDialog(
				   this,
				   mensaje);
		
	}

}