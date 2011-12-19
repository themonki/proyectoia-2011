package Interfaz;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Lector {

	FileReader fr;
	BufferedReader bf;

	public void imprimir(String[][] matriz) {

		for (int i = 0; i < 7; i++) {

			for (int j = 0; j < 7; j++) {

				System.out.print(matriz[i][j] + " ");
			}
			System.out.println("");

		}

	}

	public 	String[][] leer(String archivo) {
		try {
			fr = new FileReader(archivo);
		} catch (FileNotFoundException ex) {
			System.out.println("no cargo");
		}
		bf = new BufferedReader(fr);
		String sCadena = null;
		String[][] matriz = new String[7][7];

		int iteradorx = 0;
		int iteradory = 0;
		try {
			while ((sCadena = bf.readLine()) != null) {
				iteradory = 0;
				for (int i = 0; i < 13; i = i + 2) {
					if (!sCadena.substring(i, i + 1).equals(" ")) {
						matriz[iteradorx][iteradory] = sCadena.substring(i,
								i + 1);
						iteradory++;
					}
				}
				iteradorx++;
			}
		} catch (IOException ex) {
		}
		return matriz;
	}

}
