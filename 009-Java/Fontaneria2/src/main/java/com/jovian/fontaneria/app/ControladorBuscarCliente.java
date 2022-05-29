package com.jovian.fontaneria.app;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import interfaces.BaseDatos;
import interfaces.Chequeable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

/**
 * clase para controlar la escena de consulta de la tabla clientes
 * @author Jorge Victoria Andreu
 * @version 1.0
 */
public class ControladorBuscarCliente {
	
	//variables locales
    private static String btnId = null;									//para almacenar el id del botón pulsado
    private static ArrayList<String> claves = new ArrayList<String>();  //almcena las primary key de materiales. Será el indice
    private int indice = 0;												//para controlar la posicion del indice
	
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
	@FXML private Button btnIntroducirCampos;
	@FXML private Button btnPrimerCliente;
	@FXML private Button btnAnteriorCliente;
	@FXML private Button btnPosteriorCliente;
	@FXML private Button btnUltimoCliente;
	@FXML private Label	lblWarning;

//*******************************************************************************************************************************************************
//*******************************************************************************************************************************************************
	
	/**
	 * metodo para inicializar listeners u otras opciones al cargar esta scene
	 * @throws SQLException control de excepciones SQL 
	 */
	
	@FXML public void initialize() throws SQLException {
				
		//mostramos un mensaje con instrucciones
		lblWarning.setText("Pulse introducir datos para iniciar la busqueda de un cliente");
		
		//deshabilitamos el boton nuevaBusqueda
		btnNuevaBusqueda.setDisable(true);
		
		//desactivamos los botones
		btnAnteriorCliente.setDisable(true);
		btnBuscarDni.setDisable(true);
		btnBuscarNombre.setDisable(true);
		btnNuevaBusqueda.setDisable(true);
		btnPosteriorCliente.setDisable(true);
		
		//borramos la lista
		claves.clear();
		
		//cogemos las claves
		btnId = "ningunBoton";
		leerDatos();
		
	}
	
//*******************************************************************************************************************************************************
//*******************************************************************************************************************************************************

	/**
	 * funcion que sirve para poder iniciar la busqueda de un cliente por su dni
	 * @param event recoge el evento al hacer click sobre el botón correspondiente
	 * @throws SQLException control de excepciones SQL
	 */
	@FXML public void comprobarBusquedaDni(ActionEvent event) throws SQLException {
		
		//variables locales
		boolean correcto = true;
		
		//obtenemos el id del evento
		Button btn = (Button)event.getSource();
		btnId = btn.getId();
				
		//iniciamos la cadena warning cada vez que pulsemos click
		lblWarning.setText("");
		
		//iniciamos la comprobacion del dni
		correcto = Chequeable.chequearDni(lblWarning, tfDNI);
				
		//si está todo correcto, conectamos con la BBDD e insertamos el cliente
		if(correcto)leerDatos();
				
	}
	
//*******************************************************************************************************************************************************
//*******************************************************************************************************************************************************

	
	/**
	 * funcion que sirve para poder iniciar la busqueda de un cliente por su nombre y apellidos
	 * @param event recoge el evento al hacer click sobre el botón correspondiente
	 * @throws SQLException control de excepciones SQL
	 */
	@FXML public void comprobarBusquedaNombre(ActionEvent event) throws SQLException {
		
		//variables locales
		boolean correcto = true;
				
		//obtenemos el id del evento
		Button btn = (Button)event.getSource();
		btnId = btn.getId();
						
		//iniciamos la cadena warning cada vez que pulsemos click
		lblWarning.setText("");
		
		//iniciamos la comprobacion del dni
		correcto = Chequeable.chequearNombreApellidos(lblWarning, tfNombreCliente,tfApellido1,tfApellido2);
						
		//si está todo correcto, conectamos con la BBDD e insertamos el cliente
		if(correcto)leerDatos();
		
	}
	
//*******************************************************************************************************************************************************
//*******************************************************************************************************************************************************

	
	/**
	 * metodo para limpiar todos los campos del formulario y poder realizar una nueva busqueda
	 * @param event recoge el evento al hacer click sobre el botón correspondiente
	 */
	@FXML public void CrearNuevaBusqueda(ActionEvent event) {
		
		//desactivamos los botones
		btnNuevaBusqueda.setDisable(true);
		btnBuscarDni.setDisable(true);
		btnBuscarNombre.setDisable(true);
		btnAnteriorCliente.setDisable(true);
		btnPosteriorCliente.setDisable(true);
		
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
	
//*******************************************************************************************************************************************************
//*******************************************************************************************************************************************************

	
	/**
	 * metodo que mostrará una ventana donde se podrán introducir algunos campos para la busqueda de datos
	 * @param event recoge el evento al hacer click sobre el botón correspondiente
	 */
	@FXML public void introducirCampos(ActionEvent event) {
		
		//variables locales
		String cadena;
		ArrayList<String> datos = null;
		
		//desactivamos algunos botones
		CrearNuevaBusqueda(event);
		
		//creamos un cuadro de dialogo donde vamos a introducir datos
		Dialog<String> dialog = new Dialog<>();
		dialog.setTitle("Campos Busqueda");
		dialog.setHeaderText("Puede introducir el dni y/o los campos nombre, apellido1 y apellido2");
		dialog.setResizable(false);
		
		//etiquetas y cuadros de texto que mostraran los campos que se pueden introducir
		Label label1 = new Label("Dni: ");
		Label label2 = new Label("Nombre: ");
		Label label3 = new Label("Primer Apellido: ");
		Label label4 = new Label("Segundo Apellido: ");
		TextField text1 = new TextField();
		TextField text2 = new TextField();
		TextField text3 = new TextField();
		TextField text4 = new TextField();
		 
	    //dibujo en el panel de los elementos que vamos a mostrar
		GridPane grid = new GridPane();
		grid.add(label1, 1, 1);
		grid.add(text1, 2, 1);
		grid.add(label2, 1, 2);
		grid.add(text2, 2, 2);
		grid.add(label3, 1, 3);
		grid.add(text3, 2, 3);
		grid.add(label4, 1, 4);
		grid.add(text4, 2, 4);
		dialog.getDialogPane().setContent(grid);
		 
		//creamos y añadimos un boton al panel
		ButtonType buttonTypeOk = new ButtonType("Buscar", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
		
		//accion que controla la accion de pulsar ok para recoger los datos de los campos
		dialog.setResultConverter(new Callback<ButtonType, String>() {
		    @Override
		    public String call(ButtonType b) {
		 
		    	//al pulsar ok, debemos tener cuidado y ver si los campos estan vacios
		    	//añadimos ";" como campo separador
		        if (b == buttonTypeOk) {
		        	if(!text1.getText().isEmpty())text1.setText(text1.getText()+";"); else text1.setText(" ;");
		        	if(!text2.getText().isEmpty())text2.setText(text2.getText()+";"); else text2.setText(" ;");
		        	if(!text3.getText().isEmpty())text3.setText(text3.getText()+";"); else text3.setText(" ;");
		        	if(!text4.getText().isEmpty())text4.setText(text4.getText()+";"); else text4.setText(" ;");
		            return (text1.getText()+text2.getText()+text3.getText()+text4.getText());
		        }
		 
		        return null;
		    }
		});
		
		//recogemos los datos de los campos en una sola cadena
		Optional<String> result = dialog.showAndWait();
		
		//si hay resultado, debemos separar la cadena obtenida en un arraylist para posteriormente rellenar los campos en el formulario
		if (result.isPresent()) {
		 
			cadena = result.get().toString();
			String[] strArr = cadena.split(";");
			datos = new ArrayList<String>(Arrays.asList(strArr));
		}
		
		//mostramos los datos en pantalla
		tfDNI.setText(datos.get(0).toString());
		tfNombreCliente.setText(datos.get(1).toString());
		tfApellido1.setText(datos.get(2).toString());
		tfApellido2.setText(datos.get(3).toString());
		
		//habilitamos los botones, teniendo en cuenta que los campos no esten vacios
		if(!tfDNI.getText().isBlank())btnBuscarDni.setDisable(false);
		if(!tfNombreCliente.getText().isBlank() && !tfApellido1.getText().isBlank() && !tfApellido2.getText().isBlank()) btnBuscarNombre.setDisable(false);
			
}
	
//*******************************************************************************************************************************************************
//*******************************************************************************************************************************************************

		
	/**
	 * metodo para iniciar el proceso de insertar datos en la BBDD
	 * una vez comprobado que todos los campos del formulario son correctos
	 * @throws SQLException, control de excepciones SQL
	 */
	private void leerDatos() throws SQLException {
		
		//variables locales
		boolean conectado = false;	//para controlar que estamos conectados a la BBDD
		
		//intentamos la conexion a la BBDD
		try {
			//llamada a la funcion de conexion
			conectado = BaseDatos.conectarBBDD();
			
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
		
	}
	
//*******************************************************************************************************************************************************
//*******************************************************************************************************************************************************

 
	/**
	 * metodo con las sentencia sql para poder consultar datos del cliente en la BBDD
	 * @throws SQLException control de excepciones SQL
	 */
	private void buscarCliente() throws SQLException {
		
		//variables locales
		String sql="";
		
		//este bloque lo vamos a utilizar para construir un indice con el que poder avanzar y retroceder durante las consultas
		if(btnId.equals("ningunBoton")) {
			
			//creamos la cadena a buscatr
			sql = "SELECT DNI FROM cliente;";
			
			//lanzamos la busuqeda
			ResultSet claveDni = BaseDatos.buscar(sql);
			
			//vamos a almacenar los datos en el arraylist
			while(claveDni.next()) {
				claves.add(claveDni.getString(1));
			}
			
			
		} else {
			
			//consulta de un cliente por su dni
			if(btnId.equals("btnBuscarDni")) sql  = "SELECT * FROM cliente where DNI = '" + tfDNI.getText() + "';";
			
			//consulta de un cliente por su nombre y apellidos
			if (btnId.equals("btnBuscarNombre")) sql  = "SELECT * FROM cliente where Nombre = '" + tfNombreCliente.getText() + "' && Apellido1 = '" + tfApellido1.getText() + "' && Apellido2 = '" + tfApellido2.getText() + "';";
			
			//retrocedemos hasta el primer registro en la BBDD
			if(btnId.equals("btnPrimerCliente")) {
				sql = "SELECT * FROM cliente where DNI = '" + claves.get(0).toString() + "';";
			}
			
			//retrocedemos hasta el ultimo registro en la BBDD
			if(btnId.equals("btnUltimoCliente")) {
				sql = "SELECT * FROM cliente where DNI = '" + claves.get(claves.size()-1).toString() + "';";
			}
			
			//avanzamos una posicion en el registro de la BBDD
			if(btnId.equals("btnPosteriorCliente")) {
				if(indice != claves.size()-1)indice++;
				sql = "SELECT * FROM cliente where DNI = '" + claves.get(indice) + "';";
			}
			
			//retrocedemos una posicion en el registro de la BBDD
			if(btnId.equals("btnAnteriorCliente")) {
				if(indice != 0)indice--;
				sql = "SELECT * FROM cliente where DNI = '" + claves.get(indice) + "';";
			}
			
			
			//recogemos los datos de la consulta
			ResultSet rs = BaseDatos.buscar(sql);
			
			//activamos y desactivamos botones y celdas
			btnNuevaBusqueda.setDisable(false);
			btnBuscarDni.setDisable(true);
			btnBuscarNombre.setDisable(true);
			
			//si no hay datos encontrados, se muestra este mensaje
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
				
				//actualizamos el indice tras la consulta
				indice = claves.indexOf(tfDNI.getText());
				
				//control de botones de avanzado y retroceso, para su activado en caso necesario
				if(claves.size()>1 && indice < claves.size()-1) btnPosteriorCliente.setDisable(false);
				else btnPosteriorCliente.setDisable(true);
				if(claves.size()>1 && indice > 0) btnAnteriorCliente.setDisable(false);
				else btnAnteriorCliente.setDisable(true);
				
			}
		}
				
	}
	
//*******************************************************************************************************************************************************
//*******************************************************************************************************************************************************

	
	/**
	 * metodo para poder leer el primer registro de la tabla clientes en la BBDD
	 * @param event recoge el evento al hacer click sobre el botón correspondiente
	 * @throws SQLException control de excepciones SQL
	 */
	@FXML public void buscarPrimerCliente(ActionEvent event) throws SQLException {
		
		//recogemos el id del boton
		Button btn = (Button)event.getSource();
		btnId = btn.getId();
		
		//leemos el cliente
		leerDatos();
		
	}
	
//*******************************************************************************************************************************************************
//*******************************************************************************************************************************************************

	
	/**
	 * metodo para poder leer el anterior registro de la tabla clientes en la BBDD
	 * @param event recoge el evento al hacer click sobre el botón correspondiente
	 * @throws SQLException control de excepciones SQL 
	 */
	@FXML public void buscarAnteriorCliente(ActionEvent event) throws SQLException {
		
		//recogemos el id del boton
		Button btn = (Button)event.getSource();
		btnId = btn.getId();
		
		//leemos el cliente
		leerDatos();
	}
	
//*******************************************************************************************************************************************************
//*******************************************************************************************************************************************************

	/**
	 * metodo para poder leer el posterior registro de la tabla clientes en la BBDD
	 * @param event recoge el evento al hacer click sobre el botón correspondiente
	 * @throws SQLException control de excepciones SQL 
	 */
	@FXML public void buscarPosteriorCliente(ActionEvent event) throws SQLException {
		
		//recogemos el id del boton
		Button btn = (Button)event.getSource();
		btnId = btn.getId();
		
		//leemos el cliente
		leerDatos();
}

//*******************************************************************************************************************************************************
//*******************************************************************************************************************************************************

	/**
	 * metodo para poder leer el primer registro de la tabla clientes en la BBDD
	 * @param event recoge el evento al hacer click sobre el botón correspondiente
	 * @throws SQLException control de excepciones SQL 
	 */
	@FXML public void buscarUltimoCliente(ActionEvent event) throws SQLException {
		
		//recogemos el id del boton
		Button btn = (Button)event.getSource();
		btnId = btn.getId();
		
		//leemos el cliente
		leerDatos();
	}
		
}

	
