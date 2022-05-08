package interfaces;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

public interface Fichero {
	
	/**
	 * metodo para obtener el numero de cliente a partir de un fichero txt
	 * Hay que tener en cuenta que podemos agregar y quitar clientes, 
	 * por lo que debemos tener en cuenta que cada cliente debe tener su propio id
	 * y no podemos usar los datos de la tabla cliente como referencia(count, size, id del ultimo cliente)
	 * @return linea, que será el num del cliente
	 */
	public static String obtenerIdCliente() {
		
		//variables locales
		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;
		String linea = null;
		String numCliente = null;
		
		try {
			//Apertura del fichero y creacion de BufferedReader para poder
			//hacer una lectura comoda (disponer del metodo readline()).
			archivo = new File ("numCliente.txt");
			fr = new FileReader (archivo);
			br = new BufferedReader(fr);
			
			//Lectura del fichero
			while((linea=br.readLine())!=null) {
				numCliente = linea;
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			//cerramos el fichero
			try {
				if(null != fr) {
					fr.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		//devolvemos el numero de cliente obtenido
		return numCliente;
	}
	
	/**
	 * metodo para aumentar el num identificador del cliente
	 * Y guardarlo en el fichero
	 * @return 
	 */
	public static void aumentarNumCliente(String numCliente) {
		
		//variables locales
		FileWriter fichero = null;
		PrintWriter pw = null;
		
		try
		{
			//apertura del fichero y creacion del objeto Printwriter
			//para poder escribir en el fichero
			fichero = new FileWriter("numCliente.txt");
			pw = new PrintWriter(fichero);
			
			//incrementamos en uno el numero del Cliente
			//y almacenamos dicho numero en el fichero
			int incremento = Integer.parseInt(numCliente)+1;
			pw.println(incremento);
			
			//control de excepiones y cerrado del fichero
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(null != fichero) fichero.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	
}
