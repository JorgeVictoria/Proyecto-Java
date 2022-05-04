package com.jovian.fontaneria.app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ResourceBundle;

import interfaces.Chequeable;
import interfaces.Comprobable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


/**
 * clase para controlar la escena de alta de clientes
 * @author Jorge Victoria Andreu
 * @version 1.0
 */
public class ControladorAltaCliente implements Initializable, Comprobable {
	
	//variables para la conexion a la BBD
	private static String url = "jdbc:mariadb://localhost:3306/fontaneria"; 
    private static String user = "root";									
	private static String password = "";									
	private static Connection connection = null;
	
	//variable para poder trabajar con el nº identificador de cliente
	private static String numCliente = null;
	
	//variables formulario
	@FXML private TextField tfIDCliente;
	@FXML private TextField tfDNI;
	@FXML private TextField tfNombreCliente;
	@FXML private TextField tfApellido1;
	@FXML private TextField tfApellido2;
	@FXML private TextField tfDireccion;
	@FXML private TextField tfCPostal;
	@FXML private TextField tfLocalidad;
	@FXML private TextField tfProvincia;
	@FXML private TextField tfEmail;
	@FXML private TextField tfTelefono;
	@FXML private Button btnDarAlta;
	@FXML private Button btnNuevoCliente;
	@FXML private Label	lblWarning;
	
//*******************************************************************************************************************************************************
//*******************************************************************************************************************************************************
 
	/**
	 * metodo para inicializar listeners u otras opciones al cargar esta scene
	 */
	public void initialize(URL url, ResourceBundle rb) {
		
		//mostramos un mensaje con instrucciones
		lblWarning.setText("Rellene todos los campos para dar de alta un nuevo cliente");
		
		//debemos obtener un numero de cliente
		tfIDCliente.setText("C." + obtenerIdCliente());
		
		//esta parte del codigo controla que el campo DNI no tenga mas de 9 caracteres
		tfDNI.setTextFormatter(Comprobable.getFormatter(".{0,9}"));
		
		//esta parte del codigo controla que el campo Telefono no tenga mas de 13 caracteres
		tfTelefono.setTextFormatter(Comprobable.getFormatter(".{0,13}"));
		
		//esta parte del codigo controla que el campo Codigo Postal no tenga mas de 5 caracteres
		tfCPostal.setTextFormatter(Comprobable.getFormatter(".{0,5}"));
		
	}
	
//*******************************************************************************************************************************************************
//*******************************************************************************************************************************************************
	
	/**
	 * metodo que recoge el evento click del boton del formulario
	 * Inicia la comprobacion de todos los campos del formulario
	 * y si está todo correcto, insertará los datos en la BBDD
	 * @param event, evento del ratón(click)
	 * @throws SQLException 
	 */
	@FXML public void comprobarFormularioAlta(ActionEvent event) throws SQLException {
		
		//iniciamos la cadena warning cada vez que pulsemos click
		lblWarning.setText("");
		
		//iniciamos la comprobación del cliente
		boolean correcto = Chequeable.chequeaCliente(lblWarning,tfDNI, tfNombreCliente,tfApellido1, tfApellido2, tfDireccion, tfCPostal, tfLocalidad, tfProvincia, tfEmail, tfTelefono);
		
		//si está todo correcto, conectamos con la BBDD e insertamos el cliente
		if(correcto)insertarDatos();
				
	}
	
//*******************************************************************************************************************************************************
//*******************************************************************************************************************************************************
	
	/**
	 * metodo para limpiar el formulario de alta de clientes
	 * @param event, recoge el evento de click del boton nuevo cliente
	 */
	@FXML public void nuevoCliente(ActionEvent event) {
		
		//obtenemos el nuevo id del cliente
		tfIDCliente.setText("C." + obtenerIdCliente());
		
		//limpiamos todos los campos del formulario
		tfDNI.clear();
		tfNombreCliente.clear();
		tfApellido1.clear();
		tfApellido2.clear();
		tfDireccion.clear();
		tfCPostal.clear();
		tfLocalidad.clear();
		tfProvincia.clear();
		tfEmail.clear();
		tfTelefono.clear();
		
		//activamos el boton para dar de alta y desactivamos el de nuevo cliente
		btnDarAlta.setDisable(false);
		btnNuevoCliente.setDisable(true);
	}

	/**
	 * metodo para obtener el numero de cliente a partir de un fichero txt
	 * Hay que tener en cuenta que podemos agregar y quitar clientes, 
	 * por lo que debemos tener en cuenta que cada cliente debe tener su propio id
	 * y no podemos usar los datos de la tabla cliente como referencia(count, size, id del ultimo cliente)
	 * @return linea, que será el num del cliente
	 */
	private String obtenerIdCliente() {
		
		//variables locales
		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;
		String linea = null;
		
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
	 * metodo para iniciar el proceso de insertar datos en la BBDD
	 * una vez comprobado que todos los campos del formulario son correctos
	 * @throws SQLException
	 */
	private void insertarDatos() throws SQLException {
		
		//variables locales
		boolean conectado = false;	//para controlar que estamos conectados a la BBDD
		
		//intentamos la conexion a la BBDD
		try {
			//llamada a la funcion de conexion
			conectado = conectarBBDD();
			
			//si hay exito en la conexion,  lo indicamos y vamos llamando a las distintas funciones
			if(conectado) {
				System.out.println("Se ha conectado correctamente a la BBDD.");
				insertarCliente();
			}
			//control de excepciones en caso de error durante la conexion.
		} catch (SQLException e) {
			lblWarning.setText("No se ha conectado a la BBDD.");
			e.printStackTrace();
		}
		
		//cerramos la conexion
		connection.close();
		
	}

	/**
	 * metodo que realiza la conexion a la BBDD
	 * @return true/false : en funcion de si hemos conectado o no
	 * @throws SQLException
	 */
	private static boolean conectarBBDD() throws SQLException {
		//intentamos la conexion
		connection = DriverManager.getConnection(url, user, password);
		//en funcion de si conectamos o no, devolvemos true or false
		if(connection!=null) return true;
		else return false;
	}
	
	/**
	 * metodo con la sentencia sql para poder insertar datos del cliente en la BBDD
	 * @throws SQLException
	 */
	private void insertarCliente() throws SQLException {
		
		//variables locales
		//creamos el statement para poder realizar la consulta
		Statement sentencia = connection.createStatement();
		//inicializamos la cadena sql que almacenará la sentencía sql a ejecutar
		String sql = "";									
				
		//insertamos los clientes
		//para controlar el duplicado de la primary key, lo encerramos en un try catch
		try {
			//creamos la sentencia sql
			sql="INSERT INTO cliente VALUES (" 
					+ "'" + tfDNI.getText() + "',"
					+ "'" + tfIDCliente.getText() + "',"
					+ "'" + tfNombreCliente.getText() + "',"
					+ "'" + tfApellido1.getText() + "',"
					+ "'" + tfApellido2.getText() + "',"
					+ "'" + tfDireccion.getText() + "',"
					+ "'" + tfCPostal.getText() + "',"
					+ "'" + tfLocalidad.getText() + "',"
					+ "'" + tfProvincia.getText() + "',"
					+ "'" + tfTelefono.getText() + "',"
					+ "'" + tfEmail.getText() + "'"
					+ ")";
			
			//intentamos realizar la ejecución de la sentencia
			sentencia.executeUpdate(sql);
			
			//si no hay problemas, avisamos al usuario que el dato ha sido insertado en la BBDD	
			lblWarning.setText("cliente Introducido");
			
			//aumentamos el id del cliente
			aumentarNumCliente();
			
			//deshabilitamos el boton dar de alta para no poder insertar el mismo cliente
			//y tener que renovar el id del cliente
			btnDarAlta.setDisable(true);
			
			//habilitamos el boton nuevo cliente para poder limpiar el formulario
			//y obtener un nuevo id de cliente
			btnNuevoCliente.setDisable(false);
			
			//excepciones por si el campo dni está repetido
		} catch (SQLIntegrityConstraintViolationException e) {
			System.out.println("No se ha podido ejecutar la linea: " + sql);
			lblWarning.setText("ya existe un cliente con ese DNI");
		}
		
	}

	/**
	 * metodo para aumentar el num identificador del cliente
	 * Y guardarlo en el fichero
	 */
	private void aumentarNumCliente() {
		
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
