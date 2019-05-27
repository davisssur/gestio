package controlador;

import java.io.File;
import java.io.IOException;

import controlador.modelo.Persona;
import controlador.vistas.MenuControlador;
import controlador.vistas.NotasVistasControlador;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MenuMain extends Application {
	private Stage primaryStage;
	private BorderPane Menu;
	


	@Override
	public void start(Stage primaryStage) {	
		this.primaryStage = primaryStage;
	this.primaryStage.setTitle("AddressApp");
	this.primaryStage.getIcons().add(new Image("file:recursos/imagenes/address_book_32.png"));
	initMenu();

	showMenus();
}
		
	public void initMenu() {
		try {
	        // Load root layout from fxml file.
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(MainApp.class
	                .getResource("vistas/Menu.fxml"));
	        Menu = (BorderPane) loader.load();

	        // Show the scene containing the root layout.
	        Scene scene = new Scene(Menu);
	        primaryStage.setScene(scene);

	        // Give the controller access to the main app.
	        MenuControlador controller = loader.getController();
	        controller.setMenuMain(this);

	        primaryStage.show();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    }
	    public void showNotasVistas() {
			try {
				// Load person overvistas.
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(MainApp.class.getResource("vistas/NotasVistas.fxml"));
				AnchorPane NotasVistas = (AnchorPane) loader.load();

				// Set person overvistas into the center of root layout.
				Menu.setCenter(NotasVistas);
				
				NotasVistasControlador controller = loader.getController();
			        controller.setMenuMain(this);
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
