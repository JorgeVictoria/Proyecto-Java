package com.jovian.fontaneria.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * clase para iniciar la aplicación de escritorio a partir de una scene inicial
 * @author Jorge Victoria Andreu
 * @version 1.0
 */
public class App extends Application {

	//variables locales
    private static Scene scene;
    
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("PantallaLogin"), 600, 400);
        stage.setScene(scene);
        stage.setTitle("VICTORIA FONTANERIA");
        Image image = new Image("file:iconoBarra.jpg");
        stage.setResizable(false);
        stage.getIcons().add(image);
        stage.show();
    }

    /**
     * metodo para la carga de las distintas escenas de la aplicación
     * @param fxml, fichero fxml que se debe leer para la carga de la escena
     * @throws IOException
     */
    static void setRoot(String fxml) throws IOException {
    	scene.setRoot(loadFXML(fxml));
   
    	//como la pantalla de menu principal no es modal, debemos configurar su tamaña
    	if(fxml.equals("PantallaMenu")) {
    		scene.getWindow().setWidth(825);
    		scene.getWindow().setHeight(675);
    	}
    }

    /**
     * metodo para construir el objeto scene
     * @param fxml, nombre del fichero xml
     * @return llamada a metodo para carga de la escena
     * @throws IOException, control de excepciones I/O
     */
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    /**
     * clase main que inicia la aplicacion
     * @param args, parametros de entrada para poder utilizar en la app
     */
    public static void main(String[] args) {
        launch();
    }

}