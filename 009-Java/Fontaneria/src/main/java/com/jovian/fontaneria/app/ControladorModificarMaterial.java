package com.jovian.fontaneria.app;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
 * clase para controlar la escena de modificación de material
 * @author Jorge Victoria Andreu
 * @version 1.0
 */
public class ControladorModificarMaterial {
	
	//variables locales
    private static String btnId = null;									//para almacenar el id del botón pulsado
    private static ArrayList<String> claves = new ArrayList<String>();  //almcena las primary key de materiales. Será el indice
    ArrayList<String> datosOriginales = new ArrayList<String>();        //array con los datos originales de los campos
    private int indice = 0;                                             //para controlar la posicion del indice
	
	//variables formulario
	@FXML private TextField tfIdMaterial;
	@FXML private TextField tfNombreMaterial;
	@FXML private TextField tfPrecioCosteMaterial;
	@FXML private TextField tfPorcentajeIncremento;
	@FXML private TextField tfIncrementoMaterial;
	@FXML private TextField tfPrecioUnitarioMaterial;
	@FXML private TextArea taDescripcionMaterial;
	@FXML private ComboBox<String> cbTipoMaterial = new ComboBox<String>();
	@FXML private Button btnPrimerMaterial;
	@FXML private Button btnUltimoMaterial;
	@FXML private Button btnAnteriorMaterial;
	@FXML private Button btnSiguienteMaterial;
	@FXML private Button btnBuscarMaterial;
	@FXML private Button btnBuscarNombre;
	@FXML private Button btnNuevaBusqueda;
	@FXML private Button btnModificarMaterial;
	@FXML private Button btnRecalcularCampos;
	@FXML private Label lblWarning;
	
//*******************************************************************************************************************************************************
//*******************************************************************************************************************************************************
		
	/**
	 * metodo para inicializar listeners u otras opciones al cargar esta scene
	 * @throws SQLException control de excepciones SQL 
	 */
	
	@FXML public void initialize() throws SQLException {
		
		//mostramos un mensaje con instrucciones
		lblWarning.setText("Seleccione un tipo de material o rellene el campo nombre para la busqueda de material");
				
		//montamos el listado del combobox con los elementos que va a contener
		ObservableList<String> items = FXCollections.observableArrayList();
		items.addAll("Muebles de baño", "Lavabos", "Espejos", "Griferia Baño","Griferia Cocina", "Columnas de ducha",
				"Platos de ducha", "Mamparas", "Ceramica Pavimentos", "Ceramica Revestimentos", "Ceramica Laminados", "Calefacción", "Trabajos");
		cbTipoMaterial.setItems(items);
		
		//desactivamos algunos botones
		btnAnteriorMaterial.setDisable(true);
		btnSiguienteMaterial.setDisable(true);
		btnNuevaBusqueda.setDisable(true);
		btnRecalcularCampos.setDisable(true);
		btnModificarMaterial.setDisable(true);
		
		//borramos la lista de claves
		claves.clear();
				
		//cogemos las claves
		btnId = "ningunBoton";
		leerDatos("buscarObjeto");
		
	}
	
//*******************************************************************************************************************************************************
//*******************************************************************************************************************************************************
	/**
	 * metodo para poder leer el primer registro de la tabla material en la BBDD
	 * @param event recoge el evento al hacer click sobre el botón correspondiente
	 * @throws SQLException control de excepciones SQL
	 */				
	@FXML public void verPrimerMaterial(ActionEvent event) throws SQLException  {
		
		//recogemos el id del boton
		Button btn = (Button)event.getSource();
		btnId = btn.getId();
		
		//leemos el objeto
		leerDatos("buscarObjeto");
				
	}
	
//*******************************************************************************************************************************************************
//*******************************************************************************************************************************************************
	/**
	 * metodo para poder leer el último registro de la tabla material en la BBDD
	 * @param event recoge el evento al hacer click sobre el botón correspondiente
	 * @throws SQLException control de excepciones SQL
	 */			
	@FXML public void verUltimoMaterial(ActionEvent event) throws SQLException  {
		
		//recogemos el id del boton
		Button btn = (Button)event.getSource();
		btnId = btn.getId();
		
		//leemos el objeto
		leerDatos("buscarObjeto");
				
	}
	
//*******************************************************************************************************************************************************
//*******************************************************************************************************************************************************
	/** metodo para poder leer el anterior registro de la tabla material en la BBDD
	 * @param event recoge el evento al hacer click sobre el botón correspondiente
	 * @throws SQLException control de excepciones SQL
	 */			
	@FXML public void verAnteriorMaterial(ActionEvent event) throws SQLException  {
		
		//recogemos el id del boton
		Button btn = (Button)event.getSource();
		btnId = btn.getId();
		
		//leemos el objeto
		leerDatos("buscarObjeto");
				
	}
	
//*******************************************************************************************************************************************************
//*******************************************************************************************************************************************************
	/** metodo para poder leer el siguiente registro de la tabla material en la BBDD
	 * @param event recoge el evento al hacer click sobre el botón correspondiente
	 * @throws SQLException control de excepciones SQL
	 */					
	@FXML public void verSiguienteMaterial(ActionEvent event) throws SQLException  {
		
		//recogemos el id del boton
		Button btn = (Button)event.getSource();
		btnId = btn.getId();
		
		//leemos el objeto
		leerDatos("buscarObjeto");
	
					
	}
	
//*******************************************************************************************************************************************************
//*******************************************************************************************************************************************************
	/** metodo para poder iniciar la busqueda de material a partir de la lista de materiales del combobox
	 * @param event recoge el evento al hacer click sobre el botón correspondiente
	 * @throws SQLException control de excepciones SQL
	 */					
	@FXML public void buscarMaterial(ActionEvent event) throws SQLException  {
		
		//recogemos el id del boton
		Button btn = (Button)event.getSource();
	    btnId = btn.getId();
				
		//leemos el objeto
		leerDatos("buscarObjeto");
					
	}
	
//*******************************************************************************************************************************************************
//*******************************************************************************************************************************************************
	/** metodo para poder iniciar la busqueda de material por un nombre especifico
	 * @param event recoge el evento al hacer click sobre el botón correspondiente
	 * @throws SQLException control de excepciones SQL
	 */					
	@FXML public void buscarNombre(ActionEvent event) throws SQLException  {
		
		//recogemos el id del boton
		Button btn = (Button)event.getSource();
	    btnId = btn.getId();
						
		//leemos el objeto
		leerDatos("buscarObjeto");
					
	}
	
//*******************************************************************************************************************************************************
//*******************************************************************************************************************************************************
	/** metodo para poder iniciar la comprobación de que haya algún campo modificado
	 * @param event recoge el evento al hacer click sobre el botón correspondiente
	 * @throws SQLException control de excepciones SQL
	 */					
	@FXML public void modificarMaterial(ActionEvent event) throws SQLException  {
		
		//variables locales
		boolean hayModificacion = comprobarCambios();
		
		if(!hayModificacion) lblWarning.setText("No hay datos modificados");
		
		//si está todo correcto, conectamos con la BBDD e insertamos el cliente
		else leerDatos("modificarDatos");
		
		btnModificarMaterial.setDisable(true);
					
	}
	
//*******************************************************************************************************************************************************
//*******************************************************************************************************************************************************
	/** metodo para poder iniciar la comprobación de que los campos estén correctos y recalcular valores
	 * @param event recoge el evento al hacer click sobre el botón correspondiente
	 * @throws SQLException control de excepciones SQL
	 */					
	@FXML public void recalcularCampos(ActionEvent event) throws SQLException  {
		
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
		boolean correcto = Chequeable.chequeaMaterial(lblWarning, tipoMaterial, tfNombreMaterial, tfPrecioCosteMaterial, tfPorcentajeIncremento, taDescripcionMaterial);
				
		//si está todo correcto, calculamos unos campos, conectamos con la BBDD e insertamos el materia
		if(correcto) {
			calcularPrecioFinal();
			btnModificarMaterial.setDisable(false);
			btnRecalcularCampos.setDisable(true);
		} else {
			btnRecalcularCampos.setDisable(true);
		}
		
		
					
	}

//*******************************************************************************************************************************************************
//*******************************************************************************************************************************************************
	/**
	 * metodo para calcular el precio final del material
	 */	
	private void calcularPrecioFinal() {
	
		//operaciones para calcular el precio final del producto a partir del del precio de coste y el iva
		double precioOrigen = Double.parseDouble(tfPrecioCosteMaterial.getText().toString());
		double porcentajeIncremento = Double.parseDouble(tfPorcentajeIncremento.getText().toString());
		double totalIncremento = precioOrigen * porcentajeIncremento / 100;
		double precioFinal = precioOrigen + totalIncremento;
		
		//se muestra en pantalla el valor del incremento del iva y el valor del precio final
		tfIncrementoMaterial.setText(String.format("%.2f", totalIncremento));
		tfPrecioUnitarioMaterial.setText(String.format("%.2f", precioFinal));
	
}

//*******************************************************************************************************************************************************
//*******************************************************************************************************************************************************
	/** metodo para poder iniciar una nueva busqueda de material.
	 * @param event recoge el evento al hacer click sobre el botón correspondiente
	 * @throws SQLException control de excepciones SQL
	 */					
	@FXML public void nuevaBusqueda(ActionEvent event) throws SQLException  {
		
		//limpiamos los campos
		tfIdMaterial.clear();
		tfNombreMaterial.clear();
		taDescripcionMaterial.clear();
		tfPrecioUnitarioMaterial.clear();
		tfPrecioCosteMaterial.clear();
		tfIncrementoMaterial.clear();
		
		//desbloqueamos botones
		btnBuscarMaterial.setDisable(false);
		btnBuscarNombre.setDisable(false);
		btnNuevaBusqueda.setDisable(true);
		btnSiguienteMaterial.setDisable(true);
		btnAnteriorMaterial.setDisable(true);
		btnPrimerMaterial.setDisable(false);
		btnUltimoMaterial.setDisable(false);
		btnRecalcularCampos.setDisable(true);
		btnModificarMaterial.setDisable(true);
		
		//borramos la lista de claves
		claves.clear();
						
		//cogemos las claves
		btnId = "ningunBoton";
		leerDatos("buscarObjeto");
					
	}
	
//*******************************************************************************************************************************************************
//*******************************************************************************************************************************************************

			
	/**
	 * metodo para iniciar el proceso de insertar datos en la BBDD
	 * una vez comprobado que todos los campos del formulario son correctos
	 * @throws SQLException control de excepciones SQL
	 */
	private void leerDatos(String metodo) throws SQLException {
		
		//variables locales
		boolean conectado = false;	//para controlar que estamos conectados a la BBDD
		
		//intentamos la conexion a la BBDD
		try {
			//llamada a la funcion de conexion
			conectado = BaseDatos.conectarBBDD();
			
			//si hay exito en la conexion,  lo indicamos y vamos llamando a las distintas funciones
			if(conectado) {
				System.out.println("Se ha conectado correctamente a la BBDD.");
				if(metodo.equals("buscarObjeto"))buscarObjeto();
				else if(metodo.equals("modificarDatos"))modificarObjeto();
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
	 * metodo para realizar la operacion UPDATE en la BBDD
	 * @throws SQLException control de excepciones SQL
	 */
	private void modificarObjeto() throws SQLException {
		
		//creamos la cadena con la consulta
		String sql="UPDATE material SET Categoria=?, Nombre=?, Descripcion=?, PrecioCoste=?, Incremento=?, PrecioUnitario=?"
						+ "WHERE IDMaterial=?";
				
		//creamos el statement para poder realizar la consulta
		PreparedStatement sentencia = BaseDatos.modificar(sql);
		
		//preparamos el update
		sentencia.setString(1, cbTipoMaterial.getValue().toString());
		sentencia.setString(2, tfNombreMaterial.getText());
		sentencia.setString(3, taDescripcionMaterial.getText());
		sentencia.setDouble(4, Double.parseDouble(tfPrecioCosteMaterial.getText()));
		sentencia.setDouble(5, Double.parseDouble(tfIncrementoMaterial.getText()));
		sentencia.setDouble(6, Double.parseDouble(tfPrecioUnitarioMaterial.getText()));
		sentencia.setString(7, tfIdMaterial.getText());

		//hacemos el update y comprobamos que no haya errores
		if(sentencia.executeUpdate() > 0) {
			lblWarning.setText("Los datos han sido modificados correctamente");
			almacenarDatosOriginales();
		} else lblWarning.setText("No se ha podido realizar la actualizacion");
		
	}
	
//*******************************************************************************************************************************************************
//*******************************************************************************************************************************************************


	/**
	 * metodo con las sentencia sql para poder consultar datos del material en la BBDD
	 * @throws SQLException control de excepciones SQL
	 */
	private void buscarObjeto() throws SQLException {
		
		//variables locales
		String sql="";
		
		//este bloque lo vamos a utilizar para construir un indice con el que poder avanzar y retroceder durante las consultas
		if(btnId.equals("ningunBoton")) {
			
			//creamos la cadena a buscatr
			sql = "SELECT IDMaterial FROM material;";
			
			//lanzamos la busuqeda
			ResultSet claveObjetos = BaseDatos.buscar(sql);
			
			//vamos a almacenar los datos en el arraylist
			while(claveObjetos.next()) {
				claves.add(claveObjetos.getString(1));
			}
			
		} else {
			
			//consulta de un cliente por su dni
			if(btnId.equals("btnBuscarMaterial")) {
				
				//borramos la lista de claves
				claves.clear();
				
				try {
					sql  = "SELECT * FROM material where Categoria = '" + cbTipoMaterial.getValue().toString() + "';";
				} catch (Exception ex) {
					sql  = "SELECT * FROM material where Categoria = 'ninguno';";
				}
				
				//lanzamos la busuqeda
				ResultSet claveObjetos = BaseDatos.buscar(sql);
				
				//vamos a almacenar los datos en el arraylist
				while(claveObjetos.next()) {
					claves.add(claveObjetos.getString(1));
				}
				
				if(claves.size() == 0) {
					btnPrimerMaterial.setDisable(true);
					btnUltimoMaterial.setDisable(true);
					lblWarning.setText("No existe ningun material de ese tipo");
				}
				
			}
			
			//consulta de un cliente por su nombre y apellidos
			if (btnId.equals("btnBuscarNombre")) {
				
				//borramos la lista de claves
				claves.clear();
				
				sql  = "SELECT * FROM material where Nombre LIKE '%" + tfNombreMaterial.getText() + "%';";
				
				//lanzamos la busuqeda
				ResultSet claveObjetos = BaseDatos.buscar(sql);
				
				//vamos a almacenar los datos en el arraylist
				while(claveObjetos.next()) {
					claves.add(claveObjetos.getString(1));
				}
				
				if(claves.size() == 0) {
					btnPrimerMaterial.setDisable(true);
					btnUltimoMaterial.setDisable(true);
					lblWarning.setText("No existe ningun material de ese tipo");
				}
			}
			
			//retrocedemos hasta el primer registro en la BBDD
			if(btnId.equals("btnPrimerMaterial")) {
				sql = "SELECT * FROM material where IDMaterial = '" + claves.get(0).toString() + "';";
			}
			
			//retrocedemos hasta el ultimo registro en la BBDD
			if(btnId.equals("btnUltimoMaterial")) {
				sql = "SELECT * FROM material where IDMaterial = '" + claves.get(claves.size()-1).toString() + "';";
			}
			
			//avanzamos una posicion en el registro de la BBDD
			if(btnId.equals("btnSiguienteMaterial")) {
				if(indice != claves.size()-1)indice++;
				sql = "SELECT * FROM material where IDMaterial = '" + claves.get(indice) + "';";
			}
			
			//retrocedemos una posicion en el registro de la BBDD
			if(btnId.equals("btnAnteriorMaterial")) {
				if(indice != 0)indice--;
				sql = "SELECT * FROM material where IDMaterial = '" + claves.get(indice) + "';";
			}
			
			
			//recogemos los datos de la consulta
			ResultSet rs = BaseDatos.buscar(sql);
			
			//activamos y desactivamos botones y celdas
			btnNuevaBusqueda.setDisable(false);
			btnBuscarMaterial.setDisable(true);
			btnBuscarNombre.setDisable(true);
			btnRecalcularCampos.setDisable(false);
			
			//si no hay datos encontrados, se muestra este mensaje
			if(!rs.wasNull()) {
				lblWarning.setText("No se ha encontrado ningun objeto con esos datos");
				btnRecalcularCampos.setDisable(true);
			}
			
			//mostramos los datos por pantalla
			while(rs.next()) {
				lblWarning.setText("");
				tfIdMaterial.setText(rs.getString(1));
				cbTipoMaterial.setValue(rs.getString(2));
				tfNombreMaterial.setText(rs.getString(3));
				taDescripcionMaterial.setText(rs.getString(4));
				tfPrecioCosteMaterial.setText(String.valueOf(rs.getDouble(5)));
				tfIncrementoMaterial.setText(String.valueOf(rs.getDouble(6)));
				tfPrecioUnitarioMaterial.setText(String.valueOf(rs.getDouble(7)));
				
				//actualizamos el indice tras la consulta
				indice = claves.indexOf(tfIdMaterial.getText());
				
				//almacenamos los datos de los campos en el arrayList para comprobar si hay modificaciones
				almacenarDatosOriginales();
				
				//activamos el boton
				btnRecalcularCampos.setDisable(false);
				
				//control de botones de avanzado y retroceso, para su activado en caso necesario
				if(claves.size()>1 && indice < claves.size()-1) btnSiguienteMaterial.setDisable(false);
				else btnSiguienteMaterial.setDisable(true);
				if(claves.size()>1 && indice > 0) btnAnteriorMaterial.setDisable(false);
				else btnAnteriorMaterial.setDisable(true);
				
			}
		}
				
	}
	


//*******************************************************************************************************************************************************
//*******************************************************************************************************************************************************

	/**
	 * metodo para almacenar los datos de los campos en un arrayList para poder comprobar que hay cambios
	 */
	private void almacenarDatosOriginales() {
		
		//limpiamos el arrayList
		datosOriginales.clear();
		
		//almacenamos los datos originales
		datosOriginales.add(cbTipoMaterial.getValue().toString());
		datosOriginales.add(tfNombreMaterial.getText()); 
		datosOriginales.add(taDescripcionMaterial.getText());
		datosOriginales.add(tfPrecioCosteMaterial.getText());
		datosOriginales.add(tfPorcentajeIncremento.getText());
		datosOriginales.add(tfPrecioUnitarioMaterial.getText());
		datosOriginales.add(tfIncrementoMaterial.getText());
		
	}
	

//*******************************************************************************************************************************************************
//*******************************************************************************************************************************************************

	/**
	 * metodo para comprobar si se han realizado cambios en algun campo
	 * @return un valor booleano que indica si han habido cambios en los campos
	 */
	private boolean comprobarCambios() {
		
		if(!cbTipoMaterial.getValue().toString().equals(datosOriginales.get(0)) ||
		   !tfNombreMaterial.getText().equals(datosOriginales.get(1)) ||
		   !taDescripcionMaterial.getText().equals(datosOriginales.get(2)) ||
		   !tfPrecioCosteMaterial.getText().equals(datosOriginales.get(3)) ||
		   !tfPorcentajeIncremento.getText().equals(datosOriginales.get(4)) ||
		   !tfIncrementoMaterial.getText().equals(datosOriginales.get(5)) ||
		   !tfPrecioUnitarioMaterial.getText().equals(datosOriginales.get(6))) {
			return true;
		} else return false;
		
	}
	
}



