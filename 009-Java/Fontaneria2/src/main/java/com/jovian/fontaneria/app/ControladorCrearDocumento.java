package com.jovian.fontaneria.app;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import interfaces.BaseDatos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * clase para controlar la creación de documentos de tipo presupuesto o facturas
 * @author Jorge Victoria Andreu
 * @version 1.0
 */
public class ControladorCrearDocumento {
	
	//variables locales
	LocalDateTime fechaHora;
	int year;
	int numPresupuesto = 0;
	String dni;
	String numCliente;
	String idProducto;
	String idDetalle;
	ObservableList<String> materiales = FXCollections.observableArrayList();
	ObservableList<FacturaBase> list = FXCollections.observableArrayList();
	ObservableList<String> items = FXCollections.observableArrayList();
	ObservableList<Integer> iva = FXCollections.observableArrayList();
	ObservableList<String> fPago = FXCollections.observableArrayList();
	double nuevoPrecio;
	double cuenta = 0;
	int contador = 0;
	
	//elementos de la escena
	@FXML private Label lblNumPresupuesto;
	@FXML private Label lblFechaPresupuesto;
	@FXML private Label lblWarning;
	@FXML private Label lblNombreCliente;
	@FXML private Label lblDireccion;
	@FXML private Label lblLocalidad;
	@FXML private Label lblDni;
	@FXML private TextField tfCantidad;
	@FXML private TextField tfPUnitario;
	@FXML private TextField tfPrecioTotal;
	@FXML private TextField tfBaseImponible;
	@FXML private TextField tfImporteIva;
	@FXML private TextField tfTotal;
	@FXML private TextField tfNumeroCuenta;
	@FXML private Button btnCrearPresupuesto;
	@FXML private Button btnGuardarPresupuesto;
	@FXML private Button btnBuscarDni;
	@FXML private Button btnBuscarCliente;
	@FXML private Button btnAnyadirProducto;
	@FXML private Button btnBorrarProducto;
	@FXML private Button btnBorrarTabla;
	@FXML private ComboBox<String> cbTipoMaterial;
	@FXML private ComboBox<String> cbMaterial;
	@FXML private ComboBox<Integer> cbTasaIva;
	@FXML private ComboBox<String> cbFormaPago;
	@FXML private TableView<FacturaBase> tbClientes;
	@FXML private TableColumn<FacturaBase, Integer> tcCantidad = new TableColumn<>("Cantidad");
    @FXML private TableColumn<FacturaBase, String> tcDescripcion = new TableColumn<>("Descripcion");
    @FXML private TableColumn<FacturaBase, Double> tcPreUnitario = new TableColumn<>("Precio Unit");
    @FXML private TableColumn<FacturaBase, Double> tcImporte = new TableColumn<>("Importe");
	 
//*******************************************************************************************************************************************************
//*******************************************************************************************************************************************************
	
	/**
	 * metodo para inicializar listeners u otras opciones al cargar esta scene
	 * @throws SQLException control de excepciones sql
	 */
	
	@SuppressWarnings("unchecked")
	@FXML public void initialize() throws SQLException {
		
		//listener para cambiar el precio segun la cantidad
		tfCantidad.textProperty().addListener((observable, oldValue, newValue)->{
			if(!tfCantidad.getText().isBlank()&&!tfCantidad.getText().isEmpty()) {
				nuevoPrecio = Double.parseDouble(newValue)*Double.parseDouble(tfPUnitario.getText());
				tfPrecioTotal.setText(String.valueOf(nuevoPrecio));
			}
		});
		
		//montamos el listado del combobox con los elementos que va a contener
		items.addAll("Muebles de baño", "Lavabos", "Espejos", "Griferia Baño","Griferia Cocina", "Columnas de ducha",
				"Platos de ducha", "Mamparas", "Ceramica Pavimentos", "Ceramica Revestimentos", "Ceramica Laminados", "Calefacción", "Trabajos");
		cbTipoMaterial.setItems(items);
		
		//montamos el listado del combobox con los valores del IVA
		iva.addAll(21, 10, 0);
		cbTasaIva.setItems(iva);
		cbTasaIva.getSelectionModel().selectFirst();
		
		//montamos el listado del combobox con los valores del formato de apgo
		fPago.addAll("Transferencia Bancaria", "Efectivo");
		cbFormaPago.setItems(fPago);
		cbFormaPago.getSelectionModel().selectFirst();

		//indicamos el tipo de valor que va a almacenar cada una de las celdas de la tabla
		tcCantidad.setCellValueFactory(new PropertyValueFactory<FacturaBase, Integer>("cantidad"));
		tcDescripcion.setCellValueFactory(new PropertyValueFactory<FacturaBase, String>("descripcion"));
		tcPreUnitario.setCellValueFactory(new PropertyValueFactory<FacturaBase, Double>("precioUnitario"));
		tcImporte.setCellValueFactory(new PropertyValueFactory<FacturaBase, Double>("importe"));
		
		//construimos las columnas de la tabla
		tcCantidad.setMinWidth(100);
		tcCantidad.setStyle("-fx-alignment: CENTER");
		tcDescripcion.setMinWidth(375);
		tcDescripcion.setStyle("-fx-alignment: CENTER");
		tcPreUnitario.setMinWidth(100);
		tcPreUnitario.setStyle("-fx-alignment: CENTER");
		tcImporte.setMinWidth(100);
		tcImporte.setStyle("-fx-alignment: CENTER");
		tbClientes.getColumns().addAll(tcCantidad, tcDescripcion, tcPreUnitario, tcImporte);
		
	}

	/**
	 * metodo que inicia la creacion de un presupuesto para obtener nº documento y fecha de creación
	 * @param event recoge el evento click sobre el boton
	 * @throws SQLException control de excepciones SQL 
	 */
	@FXML public void crearPresupuesto(ActionEvent event) throws SQLException {
		
		//limpiamos campos
		lblDireccion.setText("");
		lblDni.setText("");
		lblFechaPresupuesto.setText("");
		lblLocalidad.setText("");
		lblNombreCliente.setText("");
		lblNumPresupuesto.setText("");
		tfBaseImponible.clear();
		tfCantidad.clear();
		tfImporteIva.clear();
		tfPrecioTotal.clear();
		tfPUnitario.clear();
		tfTotal.clear();
		tbClientes.getItems().clear();

		//llamada a la funcion para conectar a la BBDD y obtener el total de presupuestos
		leerDatos("contarPresupuestos");
		
		//llamada a la funcion para obtener la fecha
		obtenerFecha();
		
		//activamos los botones de los clientes y material
		btnBuscarCliente.setDisable(false);
		btnBuscarDni.setDisable(false);
		cbTipoMaterial.setDisable(false);
		cbMaterial.setDisable(false);
		
		//desactivamos el boton de crear presupuesto
		btnCrearPresupuesto.setDisable(true);
		
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
				//llamada al metodo para obtener el total de presupuestos que tenemos y dar uno
				if(metodo.equals("contarPresupuestos"))contarPresupuestos();
				//llamada al metodo para buscar al cliente por su dni
				else if(metodo.equals("buscarCliente"))buscarCliente();
				//llamada al metodo para buscar el material
				else if(metodo.equals("seleccionarMaterial"))seleccionarMaterial();
				//llamada al metodo para buscar el material exacto
				else if(metodo.equals("seleccionarUnMaterial"))seleccionarUnMaterialExacto();
				//llamada al metodo para insertar datos de presupuesto y detalles
				else if(metodo.equals("guardarPresupuesto"))guardarDocumento();
				//llamada para coger el id del material
				else if(metodo.equals("idMaterial"))cogerIdMaterial();
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
	 * metodo para contar el total de presupuestos y poder obtener el nº del nuevo presupuesto
	 * @throws SQLException control de excepciones SQL 
	 */
	private void contarPresupuestos() throws SQLException {
		
		//variables locales
		String sql="SELECT COUNT(*) FROM presupuesto";
		
		//lanzamos la busuqeda
		ResultSet contarPresupuestos = BaseDatos.buscar(sql);
		
		//vamos contando los elementos que tenemos
		while(contarPresupuestos.next()) {
			numPresupuesto = contarPresupuestos.getInt("COUNT(*)");
		}
		
		//imprimimos el nuevo nº de presupuesto
		lblNumPresupuesto.setText(String.valueOf(numPresupuesto+1));

	}
	
//*******************************************************************************************************************************************************
//*******************************************************************************************************************************************************

	/**
	 * metodo para obtener la fecha de creacion del presupuesto
	 */
	private void obtenerFecha() {
		
		//variables locales
		String dia;
		String mes = null;
		
		//Cogemos la fecha hora actual
		fechaHora = LocalDateTime.now();
		
		//obtenemos el año para crear los albaranes y las facturas
		year = fechaHora.getYear();
		
		//controlamos que el dia no sea menor que 10, para presentar un valor
		if(fechaHora.getDayOfMonth() < 10) dia = "0" + String.valueOf(fechaHora.getDayOfMonth());
		else dia = String.valueOf(fechaHora.getDayOfMonth());
		
		//damos el formato del mes
		switch(fechaHora.getMonthValue()) {
		 case 1 : mes = "Ene.";
		          break;
		 case 2 : mes = "Feb.";
         		  break;
		 case 3 : mes = "Mar.";
         		  break;
		 case 4 : mes = "Abr.";
		  		  break;
		 case 5 : mes = "May.";
                  break;
		 case 6 : mes = "Jun.";
				  break;
		 case 7 : mes = "Jul.";
				  break;
		 case 8 : mes = "Ago.";
		 		  break;
		 case 9 : mes = "Sep.";
         		  break;
		 case 10 : mes = "Oct.";
		  		   break;
		 case 11 : mes = "Nov.";
		  		   break;
		 case 12 : mes = "Dic.";
 		  	      break;
		}
		
		//creamos la cadena que vamos a mostrar en la escena
		lblFechaPresupuesto.setText(dia + " " + mes + " " + String.valueOf(fechaHora.getYear()));
		
	}
	
	/**
	 * metodo para abrir la ventana de consulta de clientes
	 * @param event recoge el evento click sobre el boton
	 * @throws SQLException control de excepciones SQL 
	 * @throws IOException control de excepciones IO
	 */
	@FXML public void buscarCliente(ActionEvent event) throws SQLException, IOException {
		
		Stage stage = new Stage();
		Parent root = FXMLLoader.load(ControladorBuscarCliente.class.getResource("PantallaBuscarCliente.fxml"));
		stage.setScene(new Scene(root));
		stage.setTitle("VICTORIA FONTANERIA");
        Image image = new Image("file:iconoBarra.jpg");
        stage.setResizable(false);
        stage.getIcons().add(image);
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(((Node) event.getSource()).getScene().getWindow());
		stage.show();
		
	}
	
//*******************************************************************************************************************************************************
//*******************************************************************************************************************************************************

	
	/**
	 * metodo que mostrará una ventana donde se podrán introducir algunos campos para la busqueda de datos
	 * @param event recoge el evento al hacer click sobre el botón correspondiente
	 * @throws SQLException control de excepciones SQL  
	 */
	@FXML public void buscarDni(ActionEvent event) throws SQLException {
		
		//variables locales
		String cadena;
		ArrayList<String> datos = null;
		
		//creamos un cuadro de dialogo donde vamos a introducir datos
		Dialog<String> dialog = new Dialog<>();
		dialog.setTitle("Campos Busqueda");
		dialog.setHeaderText("Introduzca el dni del cliente");
		dialog.setResizable(false);
		
		//etiquetas y cuadros de texto que mostraran los campos que se pueden introducir
		Label label1 = new Label("Dni: ");
		TextField text1 = new TextField();
		
		 
	    //dibujo en el panel de los elementos que vamos a mostrar
		GridPane grid = new GridPane();
		grid.add(label1, 1, 1);
		grid.add(text1, 2, 1);
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
		            return (text1.getText());
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
		
		//alamcenamos el dni que queremos buscar
		dni = datos.get(0).toString();
		
		//llamada a la busqueda
		leerDatos("buscarCliente");
		
	}
	
//*******************************************************************************************************************************************************
//*******************************************************************************************************************************************************

 
	/**
	 * metodo con las sentencia sql para poder consultar datos del cliente en la BBDD
	 * @throws SQLException control de excepciones SQL 
	 */
	private void buscarCliente() throws SQLException {
		
		//variables locales
		String sql  = "SELECT * FROM cliente where DNI = '" + dni + "';";
		
	    //recogemos los datos de la consulta
		ResultSet rs = BaseDatos.buscar(sql);
			
		//si no hay datos encontrados, se muestra este mensaje
		if(!rs.wasNull()) {
			lblWarning.setText("No se ha encontrado ningun cliente con esos datos");
		}
			
		//mostramos los datos por pantalla
		while(rs.next()) {
			lblWarning.setText("");
			lblDni.setText(rs.getString(1));
			numCliente = rs.getString(2);
			lblNombreCliente.setText(rs.getString(3).toUpperCase()+ " " + rs.getString(4).toUpperCase()+ " " + rs.getString(5).toUpperCase());
			lblDireccion.setText(rs.getString(6));
			lblLocalidad.setText(rs.getString(7) + ", " + rs.getString(8) + " (" + rs.getString(9) +")");
		}
				
	}
	
//*******************************************************************************************************************************************************
//*******************************************************************************************************************************************************
	
	/**
	 * metodo para filtrar los elementos segun el material
	 * @param event recoge el evento del combobox
	 * @throws SQLException control de excepciones SQL
	 */
	@FXML public void seleccionarTipo(ActionEvent event) throws SQLException {
		
		leerDatos("seleccionarMaterial");
		
	}
	
//*******************************************************************************************************************************************************
//*******************************************************************************************************************************************************

	/**
	 * metodo para construir un combobox con un listado de materiales pertenecientes a un tipo de material concreto
	 * @throws SQLException control de excepciones SQL
	 */
	private void seleccionarMaterial() throws SQLException {
		
		//variables locales
		String sql  = "SELECT * FROM material where categoria  = '" + cbTipoMaterial.getValue().toString() + "';";
		materiales.clear();
		
	    //recogemos los datos de la consulta
		ResultSet rs = BaseDatos.buscar(sql);
			
		//si no hay datos encontrados, se muestra este mensaje
		if(!rs.wasNull()) {
			lblWarning.setText("No se ha encontrado ningun material con esos datos");
		}
			
		//mostramos los datos por pantalla
		while(rs.next()) {
			materiales.add(rs.getString(3));
		}
		
		//añadimos al combobox el listado de materiales
		cbMaterial.setItems(materiales);
		
	}
	
//*******************************************************************************************************************************************************
//*******************************************************************************************************************************************************
		
	/**
	 * metodo para filtrar los elementos segun el material
	 * @param event recoge el evento del combobox
	 * @throws SQLException control de excepciones SQL
	 */
	@FXML public void seleccionarUnMaterial(ActionEvent event) throws SQLException {
		
		leerDatos("seleccionarUnMaterial");
		
	}
	
//*******************************************************************************************************************************************************
//*******************************************************************************************************************************************************
	/**
	 * metodo para mostrar en pantalla todos los datos correspondientes al material seleccionado
	 */
	private void seleccionarUnMaterialExacto() throws SQLException {
		
		//variables locales
		String sql  = "SELECT * FROM material where nombre  = '" + cbMaterial.getValue().toString() + "';";
		
	    //recogemos los datos de la consulta
		ResultSet rs = BaseDatos.buscar(sql);
			
		//si no hay datos encontrados, se muestra este mensaje
		if(!rs.wasNull()) {
			lblWarning.setText("No se ha encontrado ningun material con esos datos");
		}
			
		//mostramos los datos por pantalla
		while(rs.next()) {
			tfCantidad.setText("1");;
			tfPUnitario.setText(String.valueOf(rs.getDouble(7)));
			tfPrecioTotal.setText(String.valueOf(rs.getDouble(7)));
			btnAnyadirProducto.setDisable(false);
		}
		
		
		
	}
	
//*******************************************************************************************************************************************************
//*******************************************************************************************************************************************************
		
	@SuppressWarnings("unchecked")
	/**
	 * metodo para filtrar los elementos segun el material
	 * @param event recoge el evento del boton
	 * @throws SQLException control de excepciones SQL
	 */
	@FXML public void anyadirProducto(ActionEvent event) throws SQLException {
		
		//añadimos al array los elementos de la nueva factura
	    list.add(new FacturaBase(Integer.valueOf(tfCantidad.getText()), cbMaterial.getValue(), Double.valueOf(tfPUnitario.getText()), Double.valueOf(tfPrecioTotal.getText())));
		
	    //definimos algunos detalles para mostrar los datos en la tabla
		tcCantidad.setMinWidth(100);
		tcCantidad.setStyle("-fx-alignment: CENTER");
		tcDescripcion.setMinWidth(375);
		tcDescripcion.setStyle("-fx-alignment: CENTER");
		tcPreUnitario.setMinWidth(100);
		tcPreUnitario.setStyle("-fx-alignment: CENTER");
		tcImporte.setMinWidth(100);
		tcImporte.setStyle("-fx-alignment: CENTER");
	    
		//pasamos a la tabla los datos del array lista con el listado de materiales de la factura
	    tbClientes.setItems(list);
		
	    //limpiamos la tabla
		tbClientes.getColumns().clear();
		
		//construimos las columnas de la tabla
		tbClientes.getColumns().addAll(tcCantidad, tcDescripcion, tcPreUnitario, tcImporte);
		
		//realizamos calculos
		cuenta = 0;
		for(int i = 0; i < tbClientes.getItems().size(); i++) {
			cuenta = cuenta + (double) tbClientes.getColumns().get(3).getCellData(i);
		}
		
		//realizamos los calculos de la factura
		calculos();
		
		//desactivamos el campo tasa Iva
		cbTasaIva.setDisable(false);
		
		//activamos el boton para guardar presupuesto
		btnGuardarPresupuesto.setDisable(false);
		
	}
	
//*******************************************************************************************************************************************************
//*******************************************************************************************************************************************************
		
	/**
	 * metodo para realizar los calculos de la factura
	 * @param event recoge el evento del botón
	 */
	@FXML public void calcularTotal(ActionEvent event){
		
		calculos();
		
	}
		
//*******************************************************************************************************************************************************
//*******************************************************************************************************************************************************
		
	/**
	 * metodo para introducir el numero de cuenta
	 * @param event recoge el evento del boton
	 */
	@FXML public void ponerNumeroCuenta(ActionEvent event) {
		
		
		
	}
	
//*******************************************************************************************************************************************************
//*******************************************************************************************************************************************************
			
	/**
	 * metodo para realizar algunos calculos y mostrarlos en pantalla
	 */
	public void calculos(){
		
		tfBaseImponible.setText(String.format("%.2f", cuenta));
		double importeIva = cuenta*cbTasaIva.getValue()/100;
		tfImporteIva.setText(String.format("%.2f", importeIva));
		tfTotal.setText(String.format("%.2f", importeIva+cuenta));
		
	}
	
//*******************************************************************************************************************************************************
//*******************************************************************************************************************************************************
		
	/**
	 * metodo para borrar elementos de la tabla
	 * @param event coge el evento del boton
	 */
	@FXML public void borrarProducto(ActionEvent event) {
		
		tbClientes.getItems().removeAll(tbClientes.getSelectionModel().getSelectedItems());
		
		//realizamos calculos
		cuenta = 0;
		for(int i = 0; i < tbClientes.getItems().size(); i++) {
			cuenta = cuenta + (double) tbClientes.getColumns().get(3).getCellData(i);
		}
		
		calculos();
		
		if(tbClientes.getItems().size() == 0) btnGuardarPresupuesto.setDisable(true);
		
	}
		
//*******************************************************************************************************************************************************
//*******************************************************************************************************************************************************
		
	/**
	 * metodo para todos los datos de la tabla
	 * @param event recoge el evento del botón
	 */
	@FXML public void borrarTabla(ActionEvent event) {
		
		tbClientes.getItems().clear();
		tfBaseImponible.clear();
		tfImporteIva.clear();
		tfTotal.clear();
		
		//desactivamos el boton de guardar presuesto
		btnGuardarPresupuesto.setDisable(true);
		
	}
	
//*******************************************************************************************************************************************************
//*******************************************************************************************************************************************************
		
	/**
	 * metodo iniciar el guardado de los datos del presupuesto
	 * @param event recoge el evento del boton
	 * @throws SQLException control de excepciones SQL 
	 */
	@FXML public void guardarPresupuesto(ActionEvent event) throws SQLException {
		
		leerDatos("guardarPresupuesto");
		
		//activamos y desactivamos botones
		btnGuardarPresupuesto.setDisable(true);
		btnCrearPresupuesto.setDisable(false);
		
		
		
		
	}
	
//*******************************************************************************************************************************************************
//*******************************************************************************************************************************************************
			
	/**
	 * metodo para guardar los datos del presupuesto en las diferentes tablas
	 * @throws SQLException control de excepciones SQL
	 */
	public void guardarDocumento() throws SQLException {
		
		//variables locales
		String albaran = crearNumAlbaran();
		String factura = crearNumFactura();
		//creamos la cadena con lo que vamos a insertar
		String sql="INSERT INTO presupuesto VALUES (" 
					+ "'" + lblNumPresupuesto.getText() + "',"
					+ "'" + lblDni.getText() + "',"
					+ "'" + albaran + "',"
					+ "'" + factura + "',"
					+ "'" + fechaHora + "',"
					+ "'" + cbTasaIva.getValue() + "',"
					+ "'" + Double.valueOf(tfBaseImponible.getText()) + "',"
					+ "'" + Double.valueOf(tfImporteIva.getText()) + "',"
					+ "'" + Double.valueOf(tfTotal.getText()) + "'"
					+ ")";
		
		//insertamos los datos en la BBDD
		BaseDatos.insertar(sql);
		
		//intentamos la conexion a la BBDD
		
		
		for(int i = 0; i<tbClientes.getItems().size();i++) {
			
			try {
				//llamada a la funcion de conexion
				BaseDatos.conectarBBDD();
				
				idDetalle = lblNumPresupuesto.getText() + "-" + i;
				leerDatos("idMaterial");
			
				//creamos la cadena con lo que vamos a insertar
				sql="INSERT INTO detalle VALUES (" 
						+ "'" + idDetalle + "',"
						+ "'" + idProducto + "',"
						+ "'" + tbClientes.getColumns().get(1).getCellData(i) + "',"
						+ "'" + tbClientes.getColumns().get(0).getCellData(i) + "',"
						+ "'" + tbClientes.getColumns().get(2).getCellData(i) + "',"
						+ "'" + tbClientes.getColumns().get(3).getCellData(i) + "'"
						+ ")";
			
				//insertamos los datos en la BBDD
				BaseDatos.insertar(sql);
			} catch(Exception ex) {
				System.out.println("no ha sido posible la conexion");
			}
			
			try {
				//llamada a la funcion de conexion
				BaseDatos.conectarBBDD();
				//creamos la cadena con lo que vamos a insertar
				sql="INSERT INTO documento_detalle VALUES (" 
							+ "'" + lblNumPresupuesto.getText() + "',"
							+ "'" + idDetalle + "'"
							+ ")";
				
				//insertamos los datos en la BBDD
				BaseDatos.insertar(sql);
			} catch(Exception ex) {
				System.out.println("no ha sido posible la conexion");
			}
		}
		
	}

//*******************************************************************************************************************************************************
//*******************************************************************************************************************************************************
	/**
	 * metodo para crear el numero de factura
	 * @return el numero de factura
	 */
	private String crearNumFactura() {
	
			if(Integer.valueOf(lblNumPresupuesto.getText()) < 10) {
				return "00" + lblNumPresupuesto.getText() + "/" + String.valueOf(year);
			} else if(Integer.valueOf(lblNumPresupuesto.getText()) < 100) {
				return "0" + lblNumPresupuesto.getText() + "/" + String.valueOf(year);
			} else return lblNumPresupuesto.getText() + "/" + String.valueOf(year);
		}

//*******************************************************************************************************************************************************
//*******************************************************************************************************************************************************
	/**
	 * metodo para crear el numero de albaran
	 * @return el numero de albaran
	 */
	private String crearNumAlbaran() {
		
		if(Integer.valueOf(lblNumPresupuesto.getText()) < 10) {
			return numCliente + "-" + "00" + lblNumPresupuesto.getText() + "/" + String.valueOf(year).substring(2);
		} else if(Integer.valueOf(lblNumPresupuesto.getText()) < 100) {
			return numCliente + "-" + "0" + lblNumPresupuesto.getText() + "/" + String.valueOf(year).substring(2);
		} else return numCliente + "-" + lblNumPresupuesto.getText() + "/" + String.valueOf(year).substring(2);
		
		
	}

//*******************************************************************************************************************************************************
//*******************************************************************************************************************************************************
			
	/**
	 * metodo coger el id del material
	 * @throws SQLException control de excepciones SQL
	 */
	public void cogerIdMaterial() throws SQLException {
		
		//variables locales
		String sql  = "SELECT IDMaterial FROM material where nombre  = '" + tbClientes.getColumns().get(1).getCellData(contador) + "';";
		
	    //recogemos los datos de la consulta
		ResultSet rs = BaseDatos.buscar(sql);
			
		//si no hay datos encontrados, se muestra este mensaje
		if(!rs.wasNull()) {
			lblWarning.setText("No se ha encontrado ningun material con esos datos");
		}
			
		//mostramos los datos por pantalla
		while(rs.next()) {
			idProducto = rs.getString(1);
		}
		
		contador++;
		
	}
	

}
