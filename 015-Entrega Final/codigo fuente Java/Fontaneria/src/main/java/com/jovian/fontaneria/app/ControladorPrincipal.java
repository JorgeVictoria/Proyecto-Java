package com.jovian.fontaneria.app;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * clase controlador del menu login
 * @author Jorge Victoria Andreu
 * @version 1.0
 */
public class ControladorPrincipal {
	
	//variables locales
	private static String usuario = "fontanero"; //usuario para acceder
	private static String password = "test";	 //password para acceder
	
	//variables FXML
	@FXML private TextField tfUsuario;
	@FXML private TextField tfPassword;
	@FXML private Label lbWarning;
	@FXML private Button btnLogin;
	
	/**
	 * metodo que recoge el evento del boton login
	 * comprueba las credenciales para acceder a la aplicacion
	 * @param event evento del btnLogiv al pulsar sobre él.
	 * @throws IOException control de excepciones IO 
	 */
	@FXML public void comprobarCredenciales(ActionEvent event) throws IOException {
		
		//variables locales
		boolean tfVacio, camposCorrectos = false;
		
		//comprobamos que los textField no pueden estar vacios
		tfVacio = comprobarCamposVacios();
		
		//si los campos no estan vacios, ya podemos comprobar si los campos son correctos
		if(!tfVacio) camposCorrectos = comprobarCamposCorrectos();
		
		//si los campos no estan vacios y son correctos, ya podemos acceder a la siguiente escena
		if(camposCorrectos) App.setRoot("PantallaMenu");
			
	}

	/**
	 * metodo para comprobar que los campos login y password no están vacios
	 * @return correcto false si no estan vacios y true  si al menos uno lo está
	 */
	private boolean comprobarCamposVacios() {
		
		//variables locales
		boolean vacio = false;
		
		//comprobamos que los campos no esten vacios
		if(tfUsuario.getText().isEmpty() || tfPassword.getText().isEmpty()){
			lbWarning.setText("los campos no pueden estar vacios");
			vacio = true;
		}
		
		//devolvemos un valor booleano dependiendo si los campos están vacios o no
		return vacio;
	}
	
	/**
	 * metodo para comprobar que los cmapos login y password son correctos
	 * @return correcto true si son correctos y false si son incorrectos.
	 */
	private boolean comprobarCamposCorrectos() {
		
		//variables locales
		boolean correcto = false;
		
		//comprobamos que los campos sean correctos y sino mostramos warning
		if(tfUsuario.getText().equals(usuario) && tfPassword.getText().equals(password)) correcto = true;
		else lbWarning.setText("usuario o password incorrectos");
		
		//devolvemos un valor booleano en funcion de si los campos son correctos o no
		return correcto;
	}

}
