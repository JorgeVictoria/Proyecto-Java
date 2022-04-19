package com.jovian.fontaneria.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("PantallaLogin"), 600, 400);
        stage.setScene(scene);
        stage.setTitle("VICTORIA FONTANERIA");
        Image image = new Image("file:iconoBarra.jpg");
        stage.setResizable(true);
        stage.getIcons().add(image);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
    	scene.setRoot(loadFXML(fxml));
    	scene.getWindow().setWidth(800);
    	scene.getWindow().setHeight(600);
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}