package com.jovian.fontaneria.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

public class ControladorModificarCliente {
	
	//variable para almacenar los campos originales del formulario
	ArrayList<String> datosOriginales = new ArrayList<String>();

	//variables para la conexion a la BBDD
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
	@FXML private Label	lblWarning;
	
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
		
		//esta parte del codigo controla que el campo Telefono no tenga mas de 13 caracteres
		Pattern patternTel = Pattern.compile(".{0,13}");
			TextFormatter formatterTel = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
				return patternTel.matcher(change.getControlNewText()).matches()?change:null;
			});
		tfTelefono.setTextFormatter(formatterTel);
		
		//esta parte del codigo controla que el campo Codigo Postal no tenga mas de 5 caracteres
		Pattern patternCP = Pattern.compile(".{0,5}");
		TextFormatter formatterCP = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
			return patternCP.matcher(change.getControlNewText()).matches()?change:null;
		});
		tfCPostal.setTextFormatter(formatterCP);
		
	}
	
	/**
	 * funcion que sirve para poder iniciar la busqueda de un cliente por su dni
	 * @param event, recoge el evento al hacer click sobre el botón correspondiente
	 * @throws SQLException
	 */
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
	
	/**
	 * funcion que sirve para poder iniciar la busqueda de un cliente por su nombre y apellidos
	 * @param event, recoge el evento al hacer click sobre el botón correspondiente
	 * @throws SQLException
	 */
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
	
	/**
	 * metodo para limpiar todos los campos del formulario y poder realizar una nueva busqueda
	 * @param event, recoge el evento al hacer click sobre el botón correspondiente
	 */
	@FXML public void CrearNuevaBusqueda(ActionEvent event) {
		
		//activamos y desactivamos botones
		btnNuevaBusqueda.setDisable(true);
		btnBuscarDni.setDisable(false);
		btnBuscarNombre.setDisable(false);
		btnModificarCliente.setDisable(true);
		tfDireccion.setEditable(false);
		tfLocalidad.setEditable(false);
		tfCPostal.setEditable(false);
		tfProvincia.setEditable(false);
		tfEmail.setEditable(false);
		tfTelefono.setEditable(false);
		
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
		
		//limpiamos el arrayList
		datosOriginales.clear();
		
	}
	
	/**
	 * metodo para poder hacer una llamada a la escena que permite modificar los clientes
	 * @param event, recoge el evento al hacer click sobre el botón correspondiente
	 * @throws SQLException 
	 */
	@FXML public void modificarCliente(ActionEvent event) throws SQLException {
		
		//variables locales
		boolean hayModificacion = false;
		boolean correcto = true;
		
		//comprobamos que haya cambios en los datos
		if(!tfDNI.getText().equals(datosOriginales.get(0)) ||
		   !tfIDCliente.getText().equals(datosOriginales.get(1)) ||
		   !tfNombreCliente.getText().equals(datosOriginales.get(2)) ||
		   !tfApellido1.getText().equals(datosOriginales.get(3)) ||
		   !tfApellido2.getText().equals(datosOriginales.get(4)) ||
		   !tfDireccion.getText().equals(datosOriginales.get(5)) ||
		   !tfCPostal.getText().equals(datosOriginales.get(6)) ||
		   !tfLocalidad.getText().equals(datosOriginales.get(7)) ||
		   !tfProvincia.getText().equals(datosOriginales.get(8)) ||
		   !tfEmail.getText().equals(datosOriginales.get(9)) ||
		   !tfTelefono.getText().equals(datosOriginales.get(10))) {
			hayModificacion = true;
		} 
		
		if(!hayModificacion) lblWarning.setText("No hay datos modificados");
		
		//si hay datos modificados debemos volver a comprobar todos los datos, igual que en el alta de clientes
		else {
			
			//vamos comprobando campo a campo mientras vayan siendo correctos
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
				
				//comprobamos que el campo direccion no esté vacio y tenga el formato correcto(letras y numeros segun patron)
				tfDireccion.setText(tfDireccion.getText().trim());
				correcto = Comprobable.comprobarDireccion(tfDireccion.getText());
				if(!correcto) {
					lblWarning.setText("El formato de la direccion no es correcto.");
					break;
				}
				
				//comprobamos que el campo codigo postal no esté vacio y tenga el formato correcto(segun patron)
				tfCPostal.setText(tfCPostal.getText().trim());
				correcto = Comprobable.comprobarCodigoPostal(tfCPostal.getText());
				if(!correcto) {
					lblWarning.setText("El formato del codigo postal no es correcto.");
					break;
				}
				
				//comprobamos que el campo localidad no esté vacio y tenga el formato correcto(solo letras)
				tfLocalidad.setText(tfLocalidad.getText().trim());
				correcto = Comprobable.comprobarNombres(tfLocalidad.getText());
				if(!correcto) {
					lblWarning.setText("El formato de la localidad no es correcto.");
					break;
				}
				
				//comprobamos que el campo provincia no esté vacio y tenga el formato correcto(solo letras)
				tfProvincia.setText(tfProvincia.getText().trim());
				correcto = Comprobable.comprobarNombres(tfProvincia.getText());
				if(!correcto) {
					lblWarning.setText("El formato de la provincia no es correcto.");
					break;
				}
				
				//comprobamos que el campo email no esté vacio y tenga el formato correcto(pattern correcto)
				tfEmail.setText(tfEmail.getText().trim());
				correcto = Comprobable.comprobarEmail(tfEmail.getText());
				if(!correcto) {
					lblWarning.setText("El formato del email no es correcto.");
					break;
				}
				
				//comprobamos que el campo telefono no esté vacio y tenga el formato correcto(pattern correcto)
				tfTelefono.setText(tfTelefono.getText().trim());
				correcto = Comprobable.comprobarTelefono(tfTelefono.getText());
				if(!correcto) {
					lblWarning.setText("El formato del numero de telefono no es correcto.");
					break;
				}
				
				//si está todo correcto, conectamos con la BBDD e insertamos el cliente
				if(correcto) {
					modificarDatos();
					break;
				}
			}
			
		}
	}
	
	private void modificarDatos() throws SQLException {
		
		//variables locales
		boolean conectado = false;	//para controlar que estamos conectados a la BBDD
		
		//intentamos la conexion a la BBDD
		try {
			//llamada a la funcion de conexion
			conectado = conectarBBDD();
			
			//si hay exito en la conexion,  lo indicamos y vamos llamando a las distintas funciones
			if(conectado) {
				System.out.println("Se ha conectado correctamente a la BBDD.");
				modificarCliente();
			}
			//control de excepciones en caso de error durante la conexion.
		} catch (SQLException e) {
			lblWarning.setText("No se ha conectado a la BBDD.");
			e.printStackTrace();
		}
		
		//cerramos la conexion
		connection.close();
		
	}

	private void modificarCliente() throws SQLException {
		
		//creamos la cadena con la consulta
		String sql="UPDATE cliente SET DNI=?, Nombre=?, Apellido1=?, Apellido2=?, Direccion=?, CodigoPostal=?, Localidad=?, Provincia=?, Telefono=?, Correo=? "
				+ "WHERE IDCliente=?";
		
		//creamos el statement para poder realizar la consulta
		PreparedStatement sentencia = (PreparedStatement) connection.prepareStatement(sql);
		
		//preparamos el update
		sentencia.setString(1, tfDNI.getText());
		sentencia.setString(2, tfNombreCliente.getText());
		sentencia.setString(3, tfApellido1.getText());
		sentencia.setString(4, tfApellido2.getText());
		sentencia.setString(5, tfDireccion.getText());
		sentencia.setString(6, tfCPostal.getText());
		sentencia.setString(7, tfLocalidad.getText());
		sentencia.setString(8, tfProvincia.getText());
		sentencia.setString(9, tfTelefono.getText());
		sentencia.setString(10, tfEmail.getText());
		sentencia.setString(11, tfIDCliente.getText());
		
		//hacemos el update y comprobamos que no haya errores
		if(sentencia.executeUpdate() > 0) {
			lblWarning.setText("Los datos han sido modificados correctamente");
			almacenarDatosOriginales();
		} else lblWarning.setText("No se ha podido realizar la actualizacion");
		
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
		tfDireccion.setEditable(true);
		tfLocalidad.setEditable(true);
		tfCPostal.setEditable(true);
		tfProvincia.setEditable(true);
		tfEmail.setEditable(true);
		tfTelefono.setEditable(true);
		if(!rs.wasNull()) {
			lblWarning.setText("No se ha encontrado ningun cliente con esos datos");
		}
		
		//mostramos los datos por pantalla. Solo mostrará una linea
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
			
			//activamos el boton modificar cliente
			btnModificarCliente.setDisable(false);
			
			//almacenamos los datos de los campos en el array
			almacenarDatosOriginales();
			
		}
		
		//cerramos el statement
		sentencia.close();
				
	
	}

	private void almacenarDatosOriginales() {
	
		//limpiamos el arrayList
		datosOriginales.clear();
		
		//almacenamos los datos originales
		datosOriginales.add(tfDNI.getText());
		datosOriginales.add(tfIDCliente.getText());
		datosOriginales.add(tfNombreCliente.getText()); 
		datosOriginales.add(tfApellido1.getText());
		datosOriginales.add(tfApellido2.getText());
		datosOriginales.add(tfDireccion.getText());
		datosOriginales.add(tfCPostal.getText());
		datosOriginales.add(tfLocalidad.getText());
		datosOriginales.add(tfProvincia.getText());
		datosOriginales.add(tfEmail.getText());
		datosOriginales.add(tfTelefono.getText());
		
	}
		
}

	

