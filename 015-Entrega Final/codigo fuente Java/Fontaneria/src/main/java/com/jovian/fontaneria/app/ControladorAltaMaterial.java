package com.jovian.fontaneria.app;

import java.sql.SQLException;
import java.time.LocalDateTime;

import interfaces.BaseDatos;
import interfaces.Chequeable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * clase para controlar la escena de alta de clientes
 * @author Jorge Victoria Andreu
 * @version 1.0
 */
public class ControladorAltaMaterial {
	
	//variables elementos formulario
	@FXML private TextField tfIdMaterial;
	@FXML private TextField tfNombreMaterial;
	@FXML private TextField tfPrecioCosteMaterial;
	@FXML private TextField tfPorcentajeIncrementoMaterial;
	@FXML private TextField tfIncrementoMaterial;
	@FXML private TextField tfPrecioUnitarioMaterial;
	@FXML private TextArea taDescripcionMaterial;
	@FXML private ComboBox<String> cbTipoMaterial = new ComboBox<String>();
	@FXML private Button btnDarAlta;
	@FXML private Button btnCalcularPrecios;
	@FXML private Button btnNuevoMaterial;
	@FXML private Label lblWarning;
	
//*******************************************************************************************************************************************************
//*******************************************************************************************************************************************************
		
	/**
	 * metodo para inicializar listeners u otras opciones al cargar esta scene
	 * @throws SQLException control de excepciones SQL
	 */
	
	@FXML public void initialize() throws SQLException {
		
		//mostramos un mensaje con instrucciones
		lblWarning.setText("Rellene los campos para dar de alta un nuevo producto");
				
		//montamos el listado del combobox con los elementos que va a contener
		ObservableList<String> items = FXCollections.observableArrayList();
		items.addAll("Muebles de baño", "Lavabos", "Espejos", "Griferia Baño","Griferia Cocina", "Columnas de ducha",
				"Platos de ducha", "Mamparas", "Ceramica Pavimentos", "Ceramica Revestimentos", "Ceramica Laminados", "Calefacción", "Trabajos");
		cbTipoMaterial.setItems(items);
		
	}
	
//*******************************************************************************************************************************************************
//*******************************************************************************************************************************************************
	
	/**
	 * metodo que controla la acción sobre el boton de alta de material
	 * Basicamente inicia todo el proceso necesario para el insertado de material en la BBDD
	 * @param event, recoge el evento sobre el boton
	 * @throws SQLException control de excepciones SQL
	 */
	@FXML public void darAltaMaterial(ActionEvent event) throws SQLException  {
		
		//llamada a la funcion para iniciar el proceso de insercion de datos
		insertarDatos();
		
	}
	
//*******************************************************************************************************************************************************
//*******************************************************************************************************************************************************
	
	/**
	 * metodo que controla el boton que permite insertar nuevo material en la BBDD
	 * Su función es limpiar todos los campos para permitir introducir nuevos datos
	 * @param event recoge el evento sobre el boton
	 */
	@FXML public void nuevoMaterial(ActionEvent event)  {
		
		//limpiamos los campos
		tfIdMaterial.clear();
		tfNombreMaterial.clear();
		taDescripcionMaterial.clear();
		tfPrecioCosteMaterial.clear();
		tfIncrementoMaterial.clear();
		tfPrecioUnitarioMaterial.clear();
		
		//habilitamos el boton para calcular campos
		btnCalcularPrecios.setDisable(false);
		
		//deshabilitamos el boton del nuevoMaterial
		btnNuevoMaterial.setDisable(true);
		
		
	}
	
//*******************************************************************************************************************************************************
//*******************************************************************************************************************************************************
	
	/**
	 * metodo que controla el boton calcular campos
	 * Se chequea que todos los campos sean correctos y se realizan los calculos correspondientes
	 * @param event controla el evento sobre el botón
	 */
	@FXML public void calcularCampos(ActionEvent event){
		
		//variables locales
		String tipoMaterial;
		
		//iniciamos la cadena warning cada vez que pulsemos click
		lblWarning.setText("");
		
		//controlamos con un try catch que se seleccione un tipo de material
		try {
			tipoMaterial = cbTipoMaterial.getValue().toString();
		} catch (Exception ex){
			tipoMaterial = "ninguno";
		}
				
		//iniciamos la comprobación del material
		boolean correcto = Chequeable.chequeaMaterial(lblWarning, tipoMaterial, tfNombreMaterial, tfPrecioCosteMaterial, tfPorcentajeIncrementoMaterial, taDescripcionMaterial);
				
		//si está todo correcto, calculamos unos campos, conectamos con la BBDD e insertamos el materia
		if(correcto) {
			tfIdMaterial.setText(obtenerClave());
			calcularPrecioFinal();
			btnDarAlta.setDisable(false);
			btnCalcularPrecios.setDisable(true);
		}
		
	}

//*******************************************************************************************************************************************************
//*******************************************************************************************************************************************************

	/**
	 * metodo para obtener la clave primaria del material
	 * como de momento no hay formato establecido, vamos a hacer uso de los datos fecha/hora
	 * @return la primaryKey
	 */
	private static String obtenerClave() {
		
		//variables locales
		String mes, dia, hora, minuto,segundos;
			
		//cogemos el valor fecha/hora del momento
		LocalDateTime tiempo = LocalDateTime.now();
		
		//empezamos la construccion de la primary key
		//para respetar que todas las claves tengan la misma longitud,
		//añadimos el valor cuando el valor de una variable sea menor que 0
		String year = String.valueOf(tiempo.getYear());
		
		if(tiempo.getMonthValue()<10) mes = "0" +String.valueOf(tiempo.getMonthValue());
		else mes = String.valueOf(tiempo.getMonthValue());
		
		if(tiempo.getDayOfMonth()<10) dia = "0" +String.valueOf(tiempo.getDayOfMonth());
		else dia = String.valueOf(tiempo.getDayOfMonth());
		
		if(tiempo.getHour()<10) hora = "0" +String.valueOf(tiempo.getHour());
		else hora = String.valueOf(tiempo.getHour());
		
		if(tiempo.getMinute()<10) minuto = "0" +String.valueOf(tiempo.getMinute());
		else minuto = String.valueOf(tiempo.getMinute());
		
		if(tiempo.getSecond()<10) segundos = "0" +String.valueOf(tiempo.getSecond());
		else segundos = String.valueOf(tiempo.getSecond());
		
		//se devuelve el formato de la primary key
		return year+mes+dia+hora+minuto+segundos;
		
	}
	
//*******************************************************************************************************************************************************
//*******************************************************************************************************************************************************
	
	/**
	 * metodo para calcular el precio final del material
	 */
	private void calcularPrecioFinal() {
		
		//operaciones para calcular el precio final del producto a partir del del precio de coste y el iva
		double precioOrigen = Double.parseDouble(tfPrecioCosteMaterial.getText().toString());
		double porcentajeIncremento = Double.parseDouble(tfPorcentajeIncrementoMaterial.getText().toString());
		double totalIncremento = precioOrigen * porcentajeIncremento / 100;
		double precioFinal = precioOrigen + totalIncremento;
		
		//se muestra en pantalla el valor del incremento del iva y el valor del precio final
		tfIncrementoMaterial.setText(String.format("%.2f", totalIncremento));
		tfPrecioUnitarioMaterial.setText(String.format("%.2f", precioFinal));
	
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
			String sql="INSERT INTO material VALUES (" 
						+ "'" + tfIdMaterial.getText() + "',"
						+ "'" + cbTipoMaterial.getValue().toString() + "',"
						+ "'" + tfNombreMaterial.getText() + "',"
						+ "'" + taDescripcionMaterial.getText() + "',"
						+ "'" + Double.parseDouble(tfPrecioCosteMaterial.getText()) + "',"
						+ "'" + Double.parseDouble(tfIncrementoMaterial.getText()) + "',"
						+ "'" + Double.parseDouble(tfPrecioUnitarioMaterial.getText()) + "'"
						+ ")";
			
			//insertamos los datos en la BBDD
			boolean insertado = BaseDatos.insertar(sql);
			
			if(insertado) {
				
				//si no hay problemas, avisamos al usuario que el dato ha sido insertado en la BBDD	
				lblWarning.setText("Material Introducido");
				
				//deshabilitamos el boton dar de alta para no poder insertar el mismo material
				btnDarAlta.setDisable(true);
				
				//habilitamos el boton nuevo material para poder limpiar el formulario
				btnNuevoMaterial.setDisable(false);
				
			} else {
				lblWarning.setText("ya existe un material con ese Id");
				//habilitamos el boton nuevo cliente para poder limpiar el formulario
				btnNuevoMaterial.setDisable(false);
			}
			
		}
			//control de excepciones en caso de error durante la conexion.
		} catch (SQLException e) {
			lblWarning.setText("No se ha conectado a la BBDD.");
			e.printStackTrace();
		}
		
	}

}
