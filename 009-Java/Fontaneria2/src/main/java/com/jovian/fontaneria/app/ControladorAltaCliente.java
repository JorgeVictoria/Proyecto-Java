package com.jovian.fontaneria.app;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import interfaces.BaseDatos;
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
		try {
			numCliente = String.valueOf(obtenerIdCliente());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("numCliente" + numCliente);
		tfIDCliente.setText("C." + numCliente);
		
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
	 * @throws SQLException control de excepciones SQL
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
	 * metodo para limpiar los campos del formulario de alta de clientes
	 * @param event, recoge el evento de click del boton nuevo cliente
	 * @throws SQLException 
	 */
	@FXML public void nuevoCliente(ActionEvent event) throws SQLException {
		
		//obtenemos el nuevo id del cliente
		numCliente = String.valueOf(obtenerIdCliente());
		tfIDCliente.setText("C." + numCliente);
		System.out.println("numCliente" + numCliente);
		
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

//*******************************************************************************************************************************************************
//*******************************************************************************************************************************************************
	
	/**
	 * metodo para iniciar el proceso de insertar datos en la BBDD
	 * una vez comprobado que todos los campos del formulario son correctos
	 * @throws SQLException control de excepciones SQL
	 */
	private void insertarDatos() throws SQLException {
		
		//variables locales
		boolean conectado = false;	//para controlar que estamos conectados a la BBDD
		
		//intentamos la conexion a la BBDD
		try {
			//llamada a la funcion de conexion
			conectado = BaseDatos.conectarBBDD();
			
			//si hay exito en la conexion,  lo indicamos y vamos llamando a las distintas funciones
			if(conectado) {
				System.out.println("Se ha conectado correctamente a la BBDD.");
				
			//creamos la cadena con lo que vamos a insertar
			String sql="INSERT INTO cliente VALUES (" 
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
			
			//insertamos los datos en la BBDD
			boolean insertado = BaseDatos.insertar(sql);
			
			//Si se han insertado los datos correctamente, realizamos una serie de operaciones
			if(insertado) {
				
				//si no hay problemas, avisamos al usuario que el dato ha sido insertado en la BBDD	
				lblWarning.setText("cliente Introducido");
				
				//aumentamos el id del cliente
				//Fichero.aumentarNumCliente(numCliente);
				
				//deshabilitamos el boton dar de alta para no poder insertar el mismo cliente
				btnDarAlta.setDisable(true);
				
				//habilitamos el boton nuevo cliente para poder limpiar el formulario
				btnNuevoCliente.setDisable(false);
				
			} else {
		        //si el campo DNI, como primary key que es, esta repetido
				lblWarning.setText("ya existe un cliente con ese DNI");
				//habilitamos el boton nuevo cliente para poder limpiar el formulario
				btnNuevoCliente.setDisable(false);
			}
			
		}
		
		//control de excepciones en caso de error durante la conexion.
		} catch (SQLException e) {
			lblWarning.setText("No se ha conectado a la BBDD.");
			e.printStackTrace();
		}
		
	}
	
	//*******************************************************************************************************************************************************
    //*******************************************************************************************************************************************************
	  /**
	   * metodo para obtener el codigo de cliente
	   * @return un numero entero que corresponde con el valor del ultimo cliente +1
	   * @throws SQLException
	   */
      public int obtenerIdCliente() throws SQLException {
    	  
    	//variables locales
    	boolean conectado = false;	//para controlar que estamos conectados a la BBDD
    	String cliente = "";
    	int num;
    		
    	//intentamos la conexion a la BBDD
		try {
			//llamada a la funcion de conexion
			conectado = BaseDatos.conectarBBDD();
			
			//si hay exito en la conexion,  lo indicamos y vamos llamando a las distintas funciones
			if(conectado) {
				System.out.println("Se ha conectado correctamente a la BBDD.");
				
				//construimos la consulta
				String sql = "SELECT * FROM cliente;";
				
				//lanzamos la busuqeda
		  		ResultSet detalle = BaseDatos.buscar(sql);
		  		
		  	    //vamos a almacenar los datos en el arraylist
		  		while(detalle.next()) {
		  			cliente = detalle.getString(2);
		  		}
		  			
		  		
		  
				
			}
			//control de excepciones en caso de error durante la conexion.
		} catch (SQLException e) {
			lblWarning.setText("No se ha conectado a la BBDD.");
			e.printStackTrace();
		}
		
		if(cliente.isBlank() || cliente.isEmpty()) num = 1;
		else {
			num = Integer.valueOf(cliente.substring(2));
			num++;
		}
		
		System.out.println(cliente);
		
		return num;
  		
  	}
		
	
}
