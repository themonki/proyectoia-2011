package Interfaz;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.Timer;


public class canvasGlobal  extends JComponent  implements ActionListener{
	int filas; 
	int movimientoActual=0;
	int posX=0,posY=0;
	String  direccion;
	int casillas;
	int pixelRecorrido=0;
	Vector < Vector < Integer>> obstaculos= new Vector<Vector<Integer>>();
	Vector < Integer[]> posxYPosy= new Vector<Integer[]>();
	Vector <String[]> estadoDeLosCarros=new Vector<String[]>(1);
	Vector <Vector <String>> resultado;

	private Timer tiempo;

	private String letraCar;
	//a continuacion los diferentes constructures 
	public canvasGlobal(int cantFilas)	
	{
		this.filas= cantFilas;
		setBackground(Color.white);
		tiempo= new Timer(1, this);//timer que ejecuta las ordenes progresivamente	
	}
	
	//constructor por default 
	//***********************************************************
	public void paint(Graphics g) {
		// en caso de que el canvas sea un obstaculo dibura una imagen diferente

		//System.out.print(estadoDeLosCarros.size());
		for (int i = 0; i < obstaculos.size(); i++) {
			Image miImagen = (Toolkit.getDefaultToolkit()).getImage("imagen/wheels.gif");
			g.drawImage(miImagen, obstaculos.get(i).get(1) * 100, obstaculos.get(i).get(0) * 100, this);
		}
		
		for (int i = 0; i < estadoDeLosCarros.size(); i++) {
			Image miImagen = (Toolkit.getDefaultToolkit()).getImage("imagen/"
					+ estadoDeLosCarros.get(i)[1] + "/"
					+ estadoDeLosCarros.get(i)[0] +"/"+estadoDeLosCarros.get(i)[2]  +".gif");
			
			g.drawImage(miImagen, posxYPosy.get(i)[1] , posxYPosy.get(i)[0] , this);
		}
		
		for (int i = 0; i < filas; i++) {
			if (i == 2) {
				Image miImagn3 = (Toolkit.getDefaultToolkit()).getImage("imagen/stonewall2.jpg");
				g.drawImage(miImagn3, filas * 100 + 40, 100 * i - 42, this);

				Image miImagn4 = (Toolkit.getDefaultToolkit()).getImage("imagen/stonewall2.jpg");
				g.drawImage(miImagn4, filas * 100 + 40, 100 * (i + 1), this);
				i++;
			}
			Image miImagn2 = (Toolkit.getDefaultToolkit()).getImage("imagen/stonewall.jpg");
			g.drawImage(miImagn2, filas * 100, 100 * i, this);
		}

		for (int i = 0; i < filas; i++) {
			Image miImagn2 = (Toolkit.getDefaultToolkit()).getImage("imagen/stonewall2.jpg");
			g.drawImage(miImagn2, 100 * i, filas * 100, this);
		}
		// representa las filas del juego
		int longitudY = 100;
		int longitudX = 100;
		for (int i = 1; i < filas + 1; i++) {
			g.fillRect(i * longitudX, 0, 1, 100 * filas);
			g.fillRect(0, i * longitudY, 100 * filas, 1);
		}
	}
	
	public void setTam(int i){
		this.filas=i;
	}
	public void actionPerformed(ActionEvent e){
		
		if (casillas*100==pixelRecorrido)
		{
			movimientoActual++;
			pixelRecorrido=0;
			if(movimientoActual==resultado.size()) {
				tiempo.stop();
				resultado=null;
				
				return;}
		
			run();
		
		}
		if (direccion.equals("derecha"))
		{
			
			int indexCarroAMover=indexOfLetra();
			posxYPosy.get(indexCarroAMover)[1]+=1;
		
			pixelRecorrido++;
		
			repaint();
		}
		if (direccion.equals("izquierda"))
		{
			
			int indexCarroAMover=indexOfLetra();
			posxYPosy.get(indexCarroAMover)[1]-=1;
		
			pixelRecorrido++;
		
			repaint();
		}
		if (direccion.equals("arriba"))
		{
			
			int indexCarroAMover=indexOfLetra();
			posxYPosy.get(indexCarroAMover)[0]-=1;
		
			pixelRecorrido++;
		
			repaint();
		}
		if (direccion.equals("abajo"))
		{
			
			int indexCarroAMover=indexOfLetra();
			posxYPosy.get(indexCarroAMover)[0]+=1;
		
			pixelRecorrido++;
		
			repaint();
		}
		
	}
	public void mover (int casillas ,String  direccion,String letraCar)
	{
		
		this.direccion=direccion;
		this.casillas=casillas; 
		this.letraCar=letraCar;
	}
	
	//public void update (Graphics g){
		
		//paint (g);
	//}
	
	//metodo que retorna la posicion en el arreglo del carro que representa la letra
	int  indexOfLetra()
	{
		for (int i=0;i<estadoDeLosCarros.size();i++)
		
		{
			
			if (estadoDeLosCarros.get(i)[0].equals( letraCar)) return i;
			
			
		}
		
		
		return -1;
		
		
	}// generalizada
	
	
	 void sacarDatoscarros(String[][] matrizPadre)// generalizada
		{			
			Vector <String> vectorLetras= new Vector<String>(1,1); 
			String letra="";
			int tamanoCarro=1;
			String direccion="";

			for(int i=0; i<matrizPadre.length;i++)
			{
				for(int j=0; j<matrizPadre.length;j++)
				{
					letra=matrizPadre[i][j];
					//  solo se mira si es diferente de 1 o 0 osea muro o vacio si lo es pues se averigua su direccion tama�o y letra 
					
					if(letra.equals("1")){						
						Vector <Integer> pos = new Vector<Integer>();
						pos.add(i);
						pos.add(j);
						obstaculos.add(pos);	
					}
					else if(!(letra.equals("0"))  && !vectorLetras.contains(letra))
					{
						vectorLetras.add(letra);
						Integer [] pos = new Integer [2];
						pos[0]=i*100;
						pos[1]=j*100;
						
						posxYPosy.add(pos);
						int columnas=j+1,filas=i+1;
						while(columnas<matrizPadre.length && (matrizPadre[i][columnas].equals(letra)) )
						{
							tamanoCarro++;
							columnas++;
							direccion="horizontal";
						}

						while( filas<matrizPadre.length && (matrizPadre[filas][j].equals(letra))){
							tamanoCarro++;
							filas++;
							direccion="vertical";
						}

						String[] datosCarro=new String[3];
						datosCarro[0]=letra;
						datosCarro[1]=direccion;
						datosCarro[2]=""+tamanoCarro;
						estadoDeLosCarros.add(datosCarro);
					}
					letra="";
					tamanoCarro=1;
					direccion="";
				}
			}						
			repaint();
		}

	public void setResultadoMov(Vector <Vector <String>> resultado) {
		this.resultado=resultado;
	}
	
	public void run()
	{
		if(resultado.size()>15) tiempo.setDelay(1);
		tiempo.start();
		mover(Integer.parseInt(resultado.get(movimientoActual).get(2)), resultado.get(movimientoActual).get(1), resultado.get(movimientoActual).get(0));
		
	}
}
