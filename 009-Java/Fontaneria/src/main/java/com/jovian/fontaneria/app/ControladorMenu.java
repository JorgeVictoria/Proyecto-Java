package com.jovian.fontaneria.app;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Metodo para controlar la apertura de escenas en función del botón pulsado
 * @author Jorge Victoria Andreu
 * @version 1.0
 */
public class ControladorMenu {
	
	//variables elementos FXML
	@FXML private Button btnAltaCliente;
	@FXML private Button btnBajaCliente;
	@FXML private Button btnBuscarCliente;
	@FXML private Button btnModificarCliente;
	
	@FXML private Button btnAltaMaterial;
	@FXML private Button btnBajaMaterial;
	@FXML private Button btnBuscarMaterial;
	@FXML private Button btnModificarMaterial;
	
	@FXML private Button btnCrearDocumento;
	@FXML private Button btnBuscarDocumento;
	
	/**
	 * Funcion que recoge el evento de click de los botones,
	 * obtiene el id del boton pulsado e invoca la ventana correspondiente
	 * pasando el controlador y el fichero fxml que corresponda
	 * @param event
	 * @throws IOException
	 */
	@SuppressWarnings("rawtypes")
	@FXML public void seleccionarOpcion(ActionEvent event) throws IOException {
		
		//variables locales
		String id;
		Class nombreClase = null;
		String ficheroFXML = null;
		
		//obtenemos el id del evento
		Button btn = (Button)event.getSource();
		id = btn.getId();
		
		//una vez obtenido el evento, ya podemos llamar al metodo para abrir la ventana que corresponda
		switch(id) {
			case "btnAltaCliente": 
				nombreClase = ControladorAltaCliente.class;
				ficheroFXML = "PantallaAltaCliente.fxml";
				break;
			case "btnBajaCliente": 
				nombreClase = ControladorBajaCliente.class;
				ficheroFXML = "PantallaBajaCliente.fxml";
				break;
			case "btnBuscarCliente": 
				nombreClase = ControladorBuscarCliente.class;
				ficheroFXML = "PantallaBuscarCliente.fxml";
				break;
			case "btnModificarCliente": 
				nombreClase = ControladorModificarCliente.class;
				ficheroFXML = "PantallaModificarCliente.fxml";
				break;
			case "btnAltaMaterial": 
				nombreClase = ControladorAltaMaterial.class;
				ficheroFXML = "PantallaAltaMaterial.fxml";
				break;
			case "btnBajaMaterial": 
				nombreClase = ControladorBajaMaterial.class;
				ficheroFXML = "PantallaBajaMaterial.fxml";
				break;
			case "btnBuscarMaterial": 
				nombreClase = ControladorBuscarMaterial.class;
				ficheroFXML = "PantallaBuscarMaterial.fxml";
				break;
			case "btnModificarMaterial": 
				nombreClase = ControladorModificarMaterial.class;
				ficheroFXML = "PantallaModificarMaterial.fxml";
				break;
			case "btnCrearDocumento": 
				nombreClase = ControladorCrearDocumento.class;
				ficheroFXML = "PantallaCrearDocumento.fxml";
				break;
			case "btnBuscarDocumento": 
				nombreClase = ControladorBuscarDocumento.class;
				ficheroFXML = "PantallaBuscarDocumento.fxml";
				break;
		}
		
		//llamada al metodo para abrir la ventana correspondiente
		abrirVentana(nombreClase, ficheroFXML, event);
	}

	/**
	 * metodo para abrir la escena que corresponda en modo modal
	 * @param nameClass, el nombre del controlador que debe leer
	 * @param resource, el nombre del fichero fxml
	 * @param event,recoge los eventos click de los botones del menu 
	 * @throws IOException
	 */
	@SuppressWarnings("rawtypes")
	private void abrirVentana(Class nameClass, String resource, ActionEvent event) throws IOException {
		Stage stage = new Stage();
		Parent root = FXMLLoader.load(nameClass.getResource(resource));
		stage.setScene(new Scene(root));
		stage.setTitle("VICTORIA FONTANERIA");
        Image image = new Image("file:iconoBarra.jpg");
        stage.setResizable(false);
        stage.getIcons().add(image);
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(((Node) event.getSource()).getScene().getWindow());
		stage.show();
	}
	
}
