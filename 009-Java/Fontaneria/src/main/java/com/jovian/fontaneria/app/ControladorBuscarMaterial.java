package com.jovian.fontaneria.app;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import interfaces.BaseDatos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ControladorBuscarMaterial {
	
	//variables locales
    private static String btnId = null;
    private static ArrayList<String> claves = new ArrayList<String>();
    private int indice = 0;
	
	//variables formulario
	@FXML private TextField tfIdMaterial;
	@FXML private TextField tfNombreMaterial;
	@FXML private TextField tfPrecioCosteMaterial;
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
	@FXML private Label lblWarning;
	
//*******************************************************************************************************************************************************
//*******************************************************************************************************************************************************
		
	/*
	 * metodo para inicializar listeners u otras opciones al cargar esta scene
	 * @throws SQLException 
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
		
		//borramos la lista de claves
		claves.clear();
				
		//cogemos las claves
		btnId = "ningunBoton";
		leerDatos();
		
	}
	
//*******************************************************************************************************************************************************
//*******************************************************************************************************************************************************
			
	@FXML public void elegirTipoMaterial(ActionEvent event)  {
			
	}
	
//*******************************************************************************************************************************************************
//*******************************************************************************************************************************************************
				
	@FXML public void verPrimerMaterial(ActionEvent event) throws SQLException  {
		
		//recogemos el id del boton
		Button btn = (Button)event.getSource();
		btnId = btn.getId();
		
		//leemos el objeto
		leerDatos();
				
	}
	
//*******************************************************************************************************************************************************
//*******************************************************************************************************************************************************
				
	@FXML public void verUltimoMaterial(ActionEvent event) throws SQLException  {
		
		//recogemos el id del boton
		Button btn = (Button)event.getSource();
		btnId = btn.getId();
		
		//leemos el objeto
		leerDatos();
				
	}
	
//*******************************************************************************************************************************************************
//*******************************************************************************************************************************************************
				
	@FXML public void verAnteriorMaterial(ActionEvent event) throws SQLException  {
		
		//recogemos el id del boton
		Button btn = (Button)event.getSource();
		btnId = btn.getId();
		
		//leemos el objeto
		leerDatos();
				
	}
	
//*******************************************************************************************************************************************************
//*******************************************************************************************************************************************************
					
	@FXML public void verSiguienteMaterial(ActionEvent event) throws SQLException  {
		
		//recogemos el id del boton
		Button btn = (Button)event.getSource();
		btnId = btn.getId();
		
		//leemos el objeto
		leerDatos();
	
					
	}
	
//*******************************************************************************************************************************************************
//*******************************************************************************************************************************************************
					
	@FXML public void buscarMaterial(ActionEvent event) throws SQLException  {
		
		//recogemos el id del boton
		Button btn = (Button)event.getSource();
	    btnId = btn.getId();
				
		//leemos el objeto
		leerDatos();
					
	}
	
//*******************************************************************************************************************************************************
//*******************************************************************************************************************************************************
					
	@FXML public void buscarNombre(ActionEvent event) throws SQLException  {
		
		//recogemos el id del boton
		Button btn = (Button)event.getSource();
	    btnId = btn.getId();
						
		//leemos el objeto
		leerDatos();
					
	}
	
//*******************************************************************************************************************************************************
//*******************************************************************************************************************************************************
					
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
		
		//borramos la lista de claves
		claves.clear();
						
		//cogemos las claves
		btnId = "ningunBoton";
		leerDatos();
					
	}
	
//*******************************************************************************************************************************************************
//*******************************************************************************************************************************************************

			
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
			conectado = BaseDatos.conectarBBDD();
			
			//si hay exito en la conexion,  lo indicamos y vamos llamando a las distintas funciones
			if(conectado) {
				System.out.println("Se ha conectado correctamente a la BBDD.");
				buscarObjeto();
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
	 * @throws SQLException
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
			
			//si no hay datos encontrados, se muestra este mensaje
			if(!rs.wasNull()) {
				lblWarning.setText("No se ha encontrado ningun objeto con esos datos");
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
				
				//control de botones de avanzado y retroceso, para su activado en caso necesario
				if(claves.size()>1 && indice < claves.size()-1) btnSiguienteMaterial.setDisable(false);
				else btnSiguienteMaterial.setDisable(true);
				if(claves.size()>1 && indice > 0) btnAnteriorMaterial.setDisable(false);
				else btnAnteriorMaterial.setDisable(true);
				
			}
		}
				
	}
	
	

}


