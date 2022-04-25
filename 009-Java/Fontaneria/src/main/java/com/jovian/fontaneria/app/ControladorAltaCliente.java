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
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

public class ControladorAltaCliente implements Initializable, Comprobable {
	
	//variables locales
	private static String url = "jdbc:mariadb://localhost:3306/fontaneria";
    private static String user = "root";
	private static String password = "";
	private static Connection connection = null;
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
	
	/**
	 * metodo para inicializar listeners u otras opciones al cargar esta scene
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void initialize(URL url, ResourceBundle rb) {
		
		//debemos obtener un numero de cliente
		tfIDCliente.setText("C." + obtenerIdCliente());
		
		//esta parte del codigo controla que el campo DNI no tenga mas de 9 caracteres
		Pattern pattern = Pattern.compile(".{0,9}");
		TextFormatter formatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
			return pattern.matcher(change.getControlNewText()).matches()?change:null;
		});
		
		tfDNI.setTextFormatter(formatter);
		
		//esta parte del codigo controla que el campo Telefono no tenga mas de 9 caracteres
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
	 * metodo que recoge el evento click del boton del formulario
	 * Inicia la comprobacion de todos los campos del formulario
	 * y si está todo correcto, insertará los datos en la BBDD
	 * @param event, evento del ratón(click)
	 * @throws SQLException 
	 */
	@FXML public void comprobarFormularioAlta(ActionEvent event) throws SQLException {
		
		//variables locales
		boolean correcto = true;
		//iniciamos los warning cada vez que pulsemos click
		lblWarning.setText("");
		
		//mientras correcto sea true, iremos chequeando que los campos esten rellenados correctamente
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
			insertarDatos();
			if(correcto) break;
		}
		
		
		
	}
	
	@FXML public void nuevoCliente(ActionEvent event) {
		
		tfIDCliente.setText("C." + obtenerIdCliente());
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
		return numCliente;
	}
	
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
	
	private void insertarCliente() throws SQLException {
		
		//variables locales
		Statement sentencia = connection.createStatement(); //creamos el statement para poder realizar la consulta
		String sql = "";									//para poner 
				
		//insertamos los clientes
		//para controlar el duplicado de la primary key, lo encerramos en un try catch
		try {
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
			sentencia.executeUpdate(sql);			//cogemos la informacion de la consulta
			lblWarning.setText("cliente Introducido");
			aumentarNumCliente();
			btnDarAlta.setDisable(true);
			btnNuevoCliente.setDisable(false);
		} catch (SQLIntegrityConstraintViolationException e) {
			System.out.println("No se ha podido ejecutar la linea: " + sql);
			lblWarning.setText("ya existe un cliente con ese DNI");
		}
		
	}

	private void aumentarNumCliente() {
		
		FileWriter fichero = null;
		PrintWriter pw = null;
		
		try
		{
			fichero = new FileWriter("numCliente.txt");
			pw = new PrintWriter(fichero);
			
			int incremento = Integer.parseInt(numCliente)+1;
			pw.println(incremento);
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
