package com.jovian.fontaneria.app;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jdt.internal.compiler.batch.Main;

import com.lowagie.text.pdf.codec.Base64.InputStream;

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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

/**
 * clase para controlar la escena de consulta de documentos
 * @author Jorge Victoria Andreu
 * @version 1.0
 */
public class ControladorBuscarDocumento {
	
	//variables locales
	ObservableList<Presupuesto> presupuestos = FXCollections.observableArrayList(); //array de objetos presupuesto
	ObservableList<FacturaBase> list = FXCollections.observableArrayList();			//array de objetos detalle
	
	//elementos scene
	@FXML private Label lblWarning;
	@FXML private Button btnBuscarFactura;
	@FXML private Button btnBuscarCliente;
	@FXML private Button btnImprimirFactura;
	@FXML private Button btnRellenarDetalles;
	@FXML private Button btnMostrarTodo;
	@FXML private TextField tfDni;
	@FXML private TableView<FacturaBase> tbDetalles;
	@FXML private TableColumn<FacturaBase, Integer> tcCantidad = new TableColumn<>("Cantidad");
    @FXML private TableColumn<FacturaBase, String> tcDescripcion = new TableColumn<>("Descripcion");
    @FXML private TableColumn<FacturaBase, Double> tcPreUnitario = new TableColumn<>("Precio Unit");
    @FXML private TableColumn<FacturaBase, Double> tcImporte = new TableColumn<>("Importe");
    @FXML private TableView<Presupuesto> tbFacturas;
    @FXML private TableColumn<Presupuesto, String> tcIdPresupuesto = new TableColumn<>("IdPresupuesto");
    @FXML private TableColumn<Presupuesto, String> tcCliente_DNI = new TableColumn<>("Cliente_DNI");
    @FXML private TableColumn<Presupuesto, String> tcNumAlbaran = new TableColumn<>("NumAlbaran");
    @FXML private TableColumn<Presupuesto, String> tcNumFactura = new TableColumn<>("NumFactura");
    @FXML private TableColumn<Presupuesto, String> tcFecha = new TableColumn<>("Fecha");
    @FXML private TableColumn<Presupuesto, Integer> tcTasaIva = new TableColumn<>("TasaIva");
    @FXML private TableColumn<Presupuesto, Double> tcBaseImponible = new TableColumn<>("BaseImponible");
    @FXML private TableColumn<Presupuesto, Double> tcImporteIva = new TableColumn<>("ImporteIva");
    @FXML private TableColumn<Presupuesto, Double> tcTotal = new TableColumn<>("Total");
    
  //*******************************************************************************************************************************************************
  //*******************************************************************************************************************************************************
  	
  	/**
  	 * metodo para inicializar listeners u otras opciones al cargar esta scene
  	 * @throws SQLException control de excepciones sql
  	 */
  	
  	@SuppressWarnings("unchecked")
  	@FXML public void initialize() throws SQLException {

  		//indicamos el tipo de valor que va a almacenar cada una de las celdas de la tabla
  		tcCantidad.setCellValueFactory(new PropertyValueFactory<FacturaBase, Integer>("cantidad"));
  		tcDescripcion.setCellValueFactory(new PropertyValueFactory<FacturaBase, String>("descripcion"));
  		tcPreUnitario.setCellValueFactory(new PropertyValueFactory<FacturaBase, Double>("precioUnitario"));
  		tcImporte.setCellValueFactory(new PropertyValueFactory<FacturaBase, Double>("importe"));
  		
  		tcIdPresupuesto.setCellValueFactory(new PropertyValueFactory<Presupuesto, String>("IDPresupuesto"));
  		tcCliente_DNI.setCellValueFactory(new PropertyValueFactory<Presupuesto, String>("Cliente_DNI"));
  		tcNumAlbaran.setCellValueFactory(new PropertyValueFactory<Presupuesto, String>("NumAlbaran"));
  		tcNumFactura.setCellValueFactory(new PropertyValueFactory<Presupuesto, String>("NumFactura"));
  		tcFecha.setCellValueFactory(new PropertyValueFactory<Presupuesto, String>("Fecha"));
  		tcTasaIva.setCellValueFactory(new PropertyValueFactory<Presupuesto, Integer>("TasaIva"));
  		tcBaseImponible.setCellValueFactory(new PropertyValueFactory<Presupuesto, Double>("BaseImponible"));
  		tcImporteIva.setCellValueFactory(new PropertyValueFactory<Presupuesto, Double>("ImporteIva"));
  		tcTotal.setCellValueFactory(new PropertyValueFactory<Presupuesto, Double>("Total"));
  		
  		
  		//construimos las columnas de la tabla
  		tcCantidad.setMinWidth(100);
  		tcCantidad.setStyle("-fx-alignment: CENTER");
  		tcDescripcion.setMinWidth(375);
  		tcDescripcion.setStyle("-fx-alignment: CENTER");
  		tcPreUnitario.setMinWidth(100);
  		tcPreUnitario.setStyle("-fx-alignment: CENTER");
  		tcImporte.setMinWidth(100);
  		tcImporte.setStyle("-fx-alignment: CENTER");
  		
  		tcIdPresupuesto.setMinWidth(134);
  		tcIdPresupuesto.setStyle("-fx-alignment: CENTER");
  		tcCliente_DNI.setMinWidth(134);
  		tcCliente_DNI.setStyle("-fx-alignment: CENTER");
  		tcNumAlbaran.setMinWidth(134);
  		tcNumAlbaran.setStyle("-fx-alignment: CENTER");
  		tcNumFactura.setMinWidth(134);
  		tcNumFactura.setStyle("-fx-alignment: CENTER");
  		tcFecha.setMinWidth(134);
  		tcFecha.setStyle("-fx-alignment: CENTER");
  		tcTasaIva.setMinWidth(134);
  		tcTasaIva.setStyle("-fx-alignment: CENTER");
  		tcBaseImponible.setMinWidth(134);
  		tcBaseImponible.setStyle("-fx-alignment: CENTER");
  		tcImporteIva.setMinWidth(134);
  		tcImporteIva.setStyle("-fx-alignment: CENTER");
  		tcTotal.setMinWidth(132);
  		tcTotal.setStyle("-fx-alignment: CENTER");
  		
  		//añadimos las columnas a las tablas
  		tbDetalles.getColumns().addAll(tcCantidad, tcDescripcion, tcPreUnitario, tcImporte);
  		tbFacturas.getColumns().addAll(tcIdPresupuesto, tcCliente_DNI, tcNumAlbaran, tcNumFactura, tcFecha, tcTasaIva, tcBaseImponible, tcImporteIva, tcTotal);
  		
  		//leemos los datos de todas las facturas
  		leerDatos();
  		
  	}
    
  //*******************************************************************************************************************************************************
  //*******************************************************************************************************************************************************
    @FXML public void buscarFactura(ActionEvent event) throws SQLException {
    	
    	//variables locales
  		boolean conectado = false;	//para controlar que estamos conectados a la BBDD
  		
  		//intentamos la conexion a la BBDD
  		try {
  			//llamada a la funcion de conexion
  			conectado = BaseDatos.conectarBBDD();
  			
  			//si hay exito en la conexion,  lo indicamos y vamos llamando a las distintas funciones
  			if(conectado) {
  				System.out.println("Se ha conectado correctamente a la BBDD.");
  				
  				presupuestos.clear();
  				
  			//creamos la cadena a buscatr
  				String sql = "SELECT * FROM presupuesto where Cliente_DNI LIKE '%" + tfDni.getText() + "%';";
  		  			
  		  		//lanzamos la busuqeda
  		  		ResultSet presupuesto = BaseDatos.buscar(sql);
  		  			
	  			//vamos a almacenar los datos en el arraylist
	  			while(presupuesto.next()) {
	  				presupuestos.add(new Presupuesto(presupuesto.getString(1),
	  						                         presupuesto.getString(2),
	  						                         presupuesto.getString(3),
	  						                         presupuesto.getString(4),
	  						                         presupuesto.getDate(5).toString(),
	  						                         presupuesto.getInt(6),
	  						                         presupuesto.getDouble(7),
	  						                         presupuesto.getDouble(8),
	  						                         presupuesto.getDouble(9)));
	  			}
	  			
	  			//ahora debemos pasar esos datos a la tabla
	  			//pasamos a la tabla los datos del array lista con el listado de materiales de la factura
	  		    tbFacturas.setItems(presupuestos);
	  			
	  		    //limpiamos la tabla
	  			tbFacturas.getColumns().clear();
	  			tbDetalles.getColumns().clear();
	  			
	  			//construimos las columnas de la tabla
	  			tbFacturas.getColumns().addAll(tcIdPresupuesto, tcCliente_DNI, tcNumAlbaran, tcNumFactura, tcFecha, tcTasaIva, tcBaseImponible,tcImporteIva, tcTotal);
  		  		
  			}
  			//control de excepciones en caso de error durante la conexion.
  		} catch (SQLException e) {
  			lblWarning.setText("No se ha conectado a la BBDD.");
  			e.printStackTrace();
  		}
    	
		
	}
  	
  //*******************************************************************************************************************************************************
  //*******************************************************************************************************************************************************
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
    @FXML public void mostrarTodo(ActionEvent event) throws SQLException {
    	
    	//limpiamos la tabla
	    tbFacturas.getColumns().clear();
	    tbDetalles.getColumns().clear();
	    presupuestos.clear();
	    list.clear();
	    //construimos las columnas de la tabla
		tbDetalles.getColumns().addAll(tcCantidad, tcDescripcion, tcPreUnitario, tcImporte);
	    
	    leerDatos();
		
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
  				buscarPresupuestos();
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
  	private void buscarPresupuestos() throws SQLException {
  		
  		//variables locales
  		String sql="";
  		
  		//creamos la cadena a buscatr
  		sql = "SELECT * FROM presupuesto;";
  			
  		//lanzamos la busuqeda
  		ResultSet presupuesto = BaseDatos.buscar(sql);
  			
  			//vamos a almacenar los datos en el arraylist
  			while(presupuesto.next()) {
  				presupuestos.add(new Presupuesto(presupuesto.getString(1),
  						                         presupuesto.getString(2),
  						                         presupuesto.getString(3),
  						                         presupuesto.getString(4),
  						                         presupuesto.getDate(5).toString(),
  						                         presupuesto.getInt(6),
  						                         presupuesto.getDouble(7),
  						                         presupuesto.getDouble(8),
  						                         presupuesto.getDouble(9)));
  			}
  			
  			//ahora debemos pasar esos datos a la tabla
  			//pasamos a la tabla los datos del array lista con el listado de materiales de la factura
  		    tbFacturas.setItems(presupuestos);
  			
  		    //limpiamos la tabla
  			tbFacturas.getColumns().clear();
  			
  			//construimos las columnas de la tabla
  			tbFacturas.getColumns().addAll(tcIdPresupuesto, tcCliente_DNI, tcNumAlbaran, tcNumFactura, tcFecha, tcTasaIva, tcBaseImponible,tcImporteIva, tcTotal);
  		
  				
  	}
  	
  //*******************************************************************************************************************************************************
  //*******************************************************************************************************************************************************
      @FXML public void rellenarDetalles(ActionEvent event) throws SQLException {
    	  
    	//variables locales
    		boolean conectado = false;	//para controlar que estamos conectados a la BBDD
    		
    		//intentamos la conexion a la BBDD
    		try {
    			//llamada a la funcion de conexion
    			conectado = BaseDatos.conectarBBDD();
    			
    			//si hay exito en la conexion,  lo indicamos y vamos llamando a las distintas funciones
    			if(conectado) {
    				System.out.println("Se ha conectado correctamente a la BBDD.");
    				
    				//cogemos la factura correspondiente
    				String numFactura = tbFacturas.getSelectionModel().getSelectedItems().get(0).getIDPresupuesto();
    				
    				//construimos la consulta
    				String sql = "SELECT * FROM detalle where IDDetalle LIKE '%" + numFactura + "-%';";
    				
    				//limpiamos el array
    				list.clear();
    				
    				//lanzamos la busuqeda
    		  		ResultSet detalle = BaseDatos.buscar(sql);
    		  		
    		  		
    		  			
    		  			//vamos a almacenar los datos en el arraylist
    		  			while(detalle.next()) {
    		  				list.add(new FacturaBase(detalle.getInt(4),
	  						                         detalle.getString(3),
	  						                         detalle.getDouble(5),
	  						                         detalle.getDouble(6)));
    		  			}
    		  			
    		  			//ahora debemos pasar esos datos a la tabla
    		  		//pasamos a la tabla los datos del array lista con el listado de materiales de la factura
    		  		    tbDetalles.setItems(list);
    		  			
    		  		    //limpiamos la tabla
    		  			tbDetalles.getColumns().clear();
    		  			
    		  			//construimos las columnas de la tabla
    		  			tbDetalles.getColumns().addAll(tcCantidad, tcDescripcion, tcPreUnitario, tcImporte);
    		  
    				
    			}
    			//control de excepciones en caso de error durante la conexion.
    		} catch (SQLException e) {
    			lblWarning.setText("No se ha conectado a la BBDD.");
    			e.printStackTrace();
    		}
  		
  	}
      
    //*******************************************************************************************************************************************************
    //*******************************************************************************************************************************************************
          @FXML public void imprimirAlbaran(ActionEvent event) throws SQLException, ClassNotFoundException, JRException {
        	  
        	  Map<String, Object> parameters = new HashMap<String, Object>();
        	  
        	  String dataBaseName = "fontaneria";
        	  String databaseUser = "root";
        	  String databasePassword = null;
        	  String url = "jdbc:mysql://localhost/" + dataBaseName;
        	  
        	  //Class.forName("org.mariadb.jdbc.Driver");
        	  Class.forName("com.mysql.cj.jdbc.Driver");
        	  
        	  Connection databaseLink = DriverManager.getConnection(url, databaseUser, databasePassword);
        	  
        	  java.io.InputStream is = App.class.getResourceAsStream("primero.jrxml");
        	  
        	  JasperReport report = JasperCompileManager.compileReport(is);
        	  
        	  JasperPrint print = JasperFillManager.fillReport(report, parameters, databaseLink);
        	  
        	  System.out.println(print.toString());
        	  
        	  JasperViewer.viewReport(print);
          }
}
