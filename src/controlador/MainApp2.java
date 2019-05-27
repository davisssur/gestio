
package controlador;

import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.controlsfx.dialog.Dialogs;

import controlador.modelo.ListaPersonaWrapper2;
import controlador.modelo.Persona2;
import controlador.vistas.CumpleFijoControlador;
import controlador.vistas.DisenoRaizControlador;
import controlador.vistas.EditarPersonaControlador;
import controlador.vistas.PersonaVistasControlador;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainApp2 extends Application {

	private Stage primaryStage;
	private BorderPane DiseñoRaiz;
	private ObservableList<Persona2> personaData = FXCollections.observableArrayList();

	/**
	 * Constructor
	 */
	public MainApp2() {
		// Add some sample data
		personaData.add(new Persona2("Hans", "Muster"));
		personaData.add(new Persona2("Ruth", "Mueller"));
		personaData.add(new Persona2("Heinz", "Kurz"));
		personaData.add(new Persona2("Cornelia", "Meier"));
		personaData.add(new Persona2("Werner", "Meyer"));
		personaData.add(new Persona2("Lydia", "Kunz"));
		personaData.add(new Persona2("Anna", "Best"));
		personaData.add(new Persona2("Stefan", "Meier"));
		personaData.add(new Persona2("Martin", "Mueller"));
	}

	/**
	 * Returns the data as an observable list of Persons.
	 * 
	 * @return
	 */
	public ObservableList<Persona2> getPersonaData() {
		return personaData;
	}

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("AddressApp");
		this.primaryStage.getIcons().add(new Image("file:recursos/imagenes/address_book_32.png"));
		initDiseñoRaiz();

		showPersonasVistas();
	}

	public void initDiseñoRaiz() {
		try {
	        // Load root layout from fxml file.
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(MainApp2.class
	                .getResource("vistas/DiseñoRaiz.fxml"));
	        DiseñoRaiz = (BorderPane) loader.load();

	        // Show the scene containing the root layout.
	        Scene scene = new Scene(DiseñoRaiz);
	        primaryStage.setScene(scene);

	        // Give the controller access to the main app.
	        DisenoRaizControlador controller = loader.getController();
	        controller.setMainApp2(this);

	        primaryStage.show();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    // Try to load last opened person file.
	    File file = getPersonFilePath();
	    if (file != null) {
	    	CargarDatosPersonaArchivo(file);
	    }
	}

	/**
	 * Shows the person overvistas inside the root layout.
	 */
	public void showPersonasVistas() {
		try {
			// Load person overvistas.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp2.class.getResource("vistas/PersonasVistas.fxml"));
			AnchorPane PersonasVistas = (AnchorPane) loader.load();

			// Set person overvistas into the center of root layout.
			DiseñoRaiz.setCenter(PersonasVistas);
			
			PersonaVistasControlador controller = loader.getController();
		        controller.setMainApp(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * Returns the main stage.
	 * 
	 * @return
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void main(String[] args) {
		launch(args);
	}
	public boolean showEditarPersonaControlador(Persona2 persona) {
	    try {
	        // Load the fxml file and create a new stage for the popup dialog.
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(MainApp2.class.getResource("vistas/EditarPersona.fxml"));
	        AnchorPane page = (AnchorPane) loader.load();

	        // Create the dialog Stage.
	        Stage dialogStage = new Stage();
	        dialogStage.setTitle("Editar Persona");
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(primaryStage);
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);

	        // Set the person into the controller.
	        EditarPersonaControlador controller = loader.getController();
	        controller.setDialogStage(dialogStage);
	        controller.seleccionar(persona);

	        // Show the dialog and wait until the user closes it
	        dialogStage.showAndWait();

	        return controller.isOkClicked();
	    } catch (IOException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	public File getPersonFilePath() {
	    Preferences prefs = Preferences.userNodeForPackage(MainApp2.class);
	    String filePath = prefs.get("filePath", null);
	    if (filePath != null) {
	        return new File(filePath);
	    } else {
	        return null;
	    }
	}

	/**
	 * Sets the file path of the currently loaded file. The path is persisted in
	 * the OS specific registry.
	 * 
	 * @param file the file or null to remove the path
	 */
	public void setPersonaFilePath(File file) {
	    Preferences prefs = Preferences.userNodeForPackage(MainApp2.class);
	    if (file != null) {
	        prefs.put("filePath", file.getPath());

	        // Update the stage title.
	        primaryStage.setTitle("AddressApp - " + file.getName());
	    } else {
	        prefs.remove("filePath");

	        // Update the stage title.
	        primaryStage.setTitle("AddressApp");
	    }
	}
	public void CargarDatosPersonaArchivo(File file) {
	    try {
	        JAXBContext context = JAXBContext
	                .newInstance(ListaPersonaWrapper2.class);
	        Unmarshaller um = context.createUnmarshaller();

	        // Reading XML from the file and unmarshalling.
	        ListaPersonaWrapper2 wrapper = (ListaPersonaWrapper2) um.unmarshal(file);

	        personaData.clear();
	        personaData.addAll(wrapper.getPersonas());

	        // Save the file path to the registry.
	        setPersonaFilePath(file);

	    } catch (Exception e) { // catches ANY exception
	        Dialogs.create()
	                .title("Error")
	                .masthead("NO SE PUDE CARGAR LOS ARCHIVOS:\n" + file.getPath())
	                .showException(e);
	    }
	}

	/**
	 * Saves the current person data to the specified file.
	 * 
	 * @param file
	 */
	public void GuardarDatosPersonaArchivo(File file) {
	    try {
	        JAXBContext context = JAXBContext
	                .newInstance(ListaPersonaWrapper2.class);
	        Marshaller m = context.createMarshaller();
	        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

	        // Wrapping our person data.
	        ListaPersonaWrapper2 wrapper = new ListaPersonaWrapper2();
	        wrapper.setPersonas(personaData);

	        // Marshalling and saving XML to the file.
	        m.marshal(wrapper, file);

	        // Save the file path to the registry.
	        setPersonaFilePath(file);
	    } catch (Exception e) { // catches ANY exception
	        Dialogs.create().title("Error")
	                .masthead("NO SE PUDE GUARDAR LOS ARCHIVOS:\n" + file.getPath())
	                .showException(e);
	    }
	}
	public void showCumpleFijo() {
		try {
			// Load the fxml file and create a new stage for the popup.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp2.class.getResource("vistas/CumpleFijo.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Birthday Statistics");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Set the persons into the controller.
			CumpleFijoControlador controller = loader.getController();
			controller.setPersonaData(personaData);

			dialogStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}