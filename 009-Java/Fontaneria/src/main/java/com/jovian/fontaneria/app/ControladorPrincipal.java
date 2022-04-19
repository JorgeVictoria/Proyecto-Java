package com.jovian.fontaneria.app;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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
	 * @param event
	 * @throws IOException 
	 */
	@FXML public void comprobarCredenciales(ActionEvent event) throws IOException {
		
		//comprobamos que los textField no pueden estar vacios
		if(tfUsuario.getText().isEmpty() || tfPassword.getText().isEmpty()){
			lbWarning.setText("los campos no pueden estar vacios");
		} else {
		
			//comprobamos que el usuario y la contraseña sean correctos
			if(tfUsuario.getText().equals(usuario) && tfPassword.getText().equals(password)) {
				App.setRoot("PantallaMenu");
			} else {
				lbWarning.setText("usuario o password incorrectos");
			}
			
		}
		
	}

}
