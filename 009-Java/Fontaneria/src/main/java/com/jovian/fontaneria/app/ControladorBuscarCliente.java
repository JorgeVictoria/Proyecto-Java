package com.jovian.fontaneria.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

public class ControladorBuscarCliente {
	
	//variables para la conexion a la BBD
	private static String url = "jdbc:mariadb://localhost:3306/fontaneria"; 
	private static String user = "root";									
    private static String password = "";									
    private static Connection connection = null;
    private static String btnId = null;
	
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
	@FXML private Button btnBuscarNombre;
	@FXML private Button btnBuscarDni;
	@FXML private Button btnNuevaBusqueda;
	@FXML private Button btnModificarCliente;
	@FXML private Button btnListarClientes;
	@FXML private Label	lblWarning;
	@FXML private ComboBox<String> cbListadoClientes;
	
	/**
	 * metodo para inicializar listeners u otras opciones al cargar esta scene
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@FXML public void initialize() {
				
		//mostramos un mensaje con instrucciones
		lblWarning.setText("Busque un cliente por DNI o por nombre y apellidos");
		
		//deshabilitamos el boton nuevaBusqueda
		btnNuevaBusqueda.setDisable(true);
		
		//deshabilitamos el boton modificarCliente
		btnModificarCliente.setDisable(true);
		
		//esta parte del codigo controla que el campo DNI no tenga mas de 9 caracteres
		Pattern pattern = Pattern.compile(".{0,9}");
		TextFormatter formatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
			return pattern.matcher(change.getControlNewText()).matches()?change:null;
		});
				
		tfDNI.setTextFormatter(formatter);
		
		ObservableList<String> items = FXCollections.observableArrayList();
		items.addAll("Listar todos los clientes", "Listar por Codigo Postal", "Listar por Poblacion", "Listar por Provincia");
		cbListadoClientes.setItems(items);
		cbListadoClientes.getSelectionModel().select(0);
		
	}
	
	
	@FXML public void comprobarBusquedaDni(ActionEvent event) throws SQLException {
		
		//variables locales
		boolean correcto = true;
		
		//obtenemos el id del evento
		Button btn = (Button)event.getSource();
		btnId = btn.getId();
				
		//iniciamos la cadena warning cada vez que pulsemos click
		lblWarning.setText("");
				
		//mientras correcto sea true, iremos chequeando que los campos esten rellenos correctamente
		while(correcto) {
					
			//comprobamos que el formato del DNI es correcto
			correcto = Comprobable.comprobarFormatoDNI(tfDNI.getText());
			if(!correcto) {
				lblWarning.setText("El formato del DNI incorrecto.");
				break;
			}
					
			//comprobamos que la letra del dni es correcta
			correcto = Comprobable.comprobarLetraDNI(tfDNI.getText());
			if(!correcto) {
				lblWarning.setText("La letra del DNI no es correcta.");
				break;
			}
			
			//si está todo correcto, conectamos con la BBDD e insertamos el cliente
			if(correcto) {
				leerDatos();
				break;
			}
		}
	}
	
	@FXML public void comprobarBusquedaNombre(ActionEvent event) throws SQLException {
		
		//variables locales
		boolean correcto = true;
				
		//obtenemos el id del evento
		Button btn = (Button)event.getSource();
		btnId = btn.getId();
						
		//iniciamos la cadena warning cada vez que pulsemos click
		lblWarning.setText("");
						
		//mientras correcto sea true, iremos chequeando que los campos esten rellenos correctamente
		while(correcto) {
							
			//comprobamos que el campo nombre no esté vacio y tenga el formato correcto(solo letras)
			tfNombreCliente.setText(tfNombreCliente.getText().trim());
			correcto = Comprobable.comprobarNombres(tfNombreCliente.getText());
			if(!correcto) {
				lblWarning.setText("El formato del nombre no es correcto.");
				break;
			}
			
			//comprobamos que el campo apellido1 no esté vacio y tenga el formato correcto(solo letras)
			tfApellido1.setText(tfApellido1.getText().trim());
			correcto = Comprobable.comprobarNombres(tfApellido1.getText());
			if(!correcto) {
				lblWarning.setText("El formato del primer apellido no es correcto.");
				break;
			}
			
			//comprobamos que el campo apellido2 no esté vacio y tenga el formato correcto(solo letras)
			tfApellido2.setText(tfApellido2.getText().trim());
			correcto = Comprobable.comprobarNombres(tfApellido2.getText());
			if(!correcto) {
				lblWarning.setText("El formato del segundo apellido no es correcto.");
				break;
			}
					
			//si está todo correcto, conectamos con la BBDD y leemos los datos del cliente
			if(correcto) {
				//leemos los datos de la BBDD
				leerDatos();
				break;
			}
		}
		
	}
	
	@FXML public void CrearNuevaBusqueda(ActionEvent event) {
		
		//activamos y desactivamos botones
		btnNuevaBusqueda.setDisable(true);
		btnBuscarDni.setDisable(false);
		btnBuscarNombre.setDisable(false);
		btnModificarCliente.setDisable(true);
		tfDNI.setEditable(true);
		tfNombreCliente.setEditable(true);
		tfApellido1.setEditable(true);
		tfApellido2.setEditable(true);
		
		//limpiamos todos los campos del formulario
		tfIDCliente.clear();
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
		
		
	}
	
	@FXML public void modificarCliente(ActionEvent event) {
	}
	
	@FXML public void listarClientes(ActionEvent event) {
	}
	
	/**
	 * metodo para iniciar el proceso de insertar datos en la BBDD
	 * una vez comprobado que todos los campos del formulario son correctos
	 * @throws SQLException
	 */
	private void leerDatos() throws SQLException {
		
		//variables locales
		boolean conectado = false;	//para controlar que estamos conectados a la BBDD
		
		//intentamos la conexion a la BBDD
		try {
			//llamada a la funcion de conexion
			conectado = conectarBBDD();
			
			//si hay exito en la conexion,  lo indicamos y vamos llamando a las distintas funciones
			if(conectado) {
				System.out.println("Se ha conectado correctamente a la BBDD.");
				buscarCliente();
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
	private void buscarCliente() throws SQLException {
		
		String sql="";
		
		//creamos el statement para poder realizar la consulta
		Statement sentencia = connection.createStatement();
		
		//inicializamos la cadena sql que almacenará la sentencía sql a ejecutar
		if(btnId.equals("btnBuscarDni")) sql  = "SELECT * FROM cliente where DNI = '" + tfDNI.getText() + "';";
		else if (btnId.equals("btnBuscarNombre")) {
			sql  = "SELECT * FROM cliente where Nombre = '" + tfNombreCliente.getText() + "' && Apellido1 = '" + tfApellido1.getText() + "' && Apellido2 = '" + tfApellido2.getText() + "';";
		}
		
		//recogemos los datos de la consulta
		ResultSet rs = sentencia.executeQuery(sql);
		
		//activamos y desactivamos botones y celdas
		btnNuevaBusqueda.setDisable(false);
		btnBuscarDni.setDisable(true);
		btnBuscarNombre.setDisable(true);
		tfDNI.setEditable(false);
		tfNombreCliente.setEditable(false);
		tfApellido1.setEditable(false);
		tfApellido2.setEditable(false);
		if(!rs.wasNull()) {
			lblWarning.setText("No se ha encontrado ningun cliente con esos datos");
		}
		
		//mostramos los datos por pantalla
		while(rs.next()) {
			lblWarning.setText("");
			tfDNI.setText(rs.getString(1));
			tfIDCliente.setText(rs.getString(2));
			tfNombreCliente.setText(rs.getString(3));
			tfApellido1.setText(rs.getString(4));
			tfApellido2.setText(rs.getString(5));
			tfDireccion.setText(rs.getString(6));
			tfCPostal.setText(rs.getString(7));
			tfLocalidad.setText(rs.getString(8));
			tfProvincia.setText(rs.getString(9));
			tfEmail.setText(rs.getString(11));
			tfTelefono.setText(rs.getString(10));
			btnModificarCliente.setDisable(false);
		}
		
		sentencia.close();
				
	
	}
		
}

	
