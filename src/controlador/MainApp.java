
package controlador;

import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.controlsfx.dialog.Dialogs;

import controlador.modelo.ListaNotaWrapper;
import controlador.modelo.Nota;
import controlador.vistas.DisenoRaizControlador;
import controlador.vistas.EditarNotaControlador;
import controlador.vistas.NotaVistasControlador;
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

public class MainApp extends Application {

	private Stage primaryStage;
	private BorderPane DiseñoRaiz;
	private ObservableList<Nota> notasData = FXCollections.observableArrayList();

	/**
	 * Constructor
	 */
	public MainApp() {
		// Add some sample data
		notasData.add(new Nota("Hans", "Muster"));
		notasData.add(new Nota("Ruth", "Mueller"));
		notasData.add(new Nota("Heinz", "Kurz"));
		notasData.add(new Nota("Cornelia", "Meier"));
		notasData.add(new Nota("Werner", "Meyer"));
		notasData.add(new Nota("Lydia", "Kunz"));
		notasData.add(new Nota("Anna", "Best"));
		notasData.add(new Nota("Stefan", "Meier"));
		notasData.add(new Nota("Martin", "Mueller"));
	}

	/**
	 * Returns the data as an observable list of Persons.
	 * 
	 * @return
	 */
	public ObservableList<Nota> getnotasData() {
		return notasData;
	}

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("AddressApp");
		this.primaryStage.getIcons().add(new Image("file:recursos/imagenes/address_book_32.png"));
		initDiseñoRaiz();

		showNotaVistas();
	}

	public void initDiseñoRaiz() {
		try {
	        // Load root layout from fxml file.
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(MainApp.class
	                .getResource("vistas/DiseñoRaiz.fxml"));
	        DiseñoRaiz = (BorderPane) loader.load();

	        // Show the scene containing the root layout.
	        Scene scene = new Scene(DiseñoRaiz);
	        primaryStage.setScene(scene);

	        // Give the controller access to the main app.
	        DisenoRaizControlador controller = loader.getController();
	        controller.setMainApp(this);

	        primaryStage.show();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    // Try to load last opened person file.
	    File file = getPersonFilePath();
	    if (file != null) {
	    	CargarDatosNotaArchivo(file);
	    }
	}

	/**
	 * Shows the person overvistas inside the root layout.
	 */
	public void showNotaVistas() {
		try {
			// Load person overvistas.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("vistas/NotaVistas.fxml"));
			AnchorPane NotaVistas = (AnchorPane) loader.load();

			// Set person overvistas into the center of root layout.
			DiseñoRaiz.setCenter(NotaVistas);
			
			NotaVistasControlador controller = loader.getController();
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
	public boolean showEditarNotaControlador(Nota nota) {
	    try {
	        // Load the fxml file and create a new stage for the popup dialog.
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(MainApp.class.getResource("vistas/EditarNota.fxml"));
	        AnchorPane page = (AnchorPane) loader.load();

	        // Create the dialog Stage.
	        Stage dialogStage = new Stage();
	        dialogStage.setTitle("Editar Nota");
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(primaryStage);
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);

	        // Set the person into the controller.
	        EditarNotaControlador controller = loader.getController();
	        controller.setDialogStage(dialogStage);
	        controller.seleccionar(nota);

	        // Show the dialog and wait until the user closes it
	        dialogStage.showAndWait();

	        return controller.isOkClicked();
	    } catch (IOException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	public File getPersonFilePath() {
	    Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
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
	public void setNotaFilePath(File file) {
	    Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
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
	public void CargarDatosNotaArchivo(File file) {
	    try {
	        JAXBContext context = JAXBContext
	                .newInstance(ListaNotaWrapper.class);
	        Unmarshaller um = context.createUnmarshaller();

	        // Reading XML from the file and unmarshalling.
	        ListaNotaWrapper wrapper = (ListaNotaWrapper) um.unmarshal(file);

	        notasData.clear();
	        notasData.addAll(wrapper.getNotas());

	        // Save the file path to the registry.
	        setNotaFilePath(file);

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
	public void GuardarDatosNotaArchivo(File file) {
	    try {
	        JAXBContext context = JAXBContext
	                .newInstance(ListaNotaWrapper.class);
	        Marshaller m = context.createMarshaller();
	        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

	        // Wrapping our person data.
	        ListaNotaWrapper wrapper = new ListaNotaWrapper();
	        wrapper.setNotas(notasData);

	        // Marshalling and saving XML to the file.
	        m.marshal(wrapper, file);

	        // Save the file path to the registry.
	        setNotaFilePath(file);
	    } catch (Exception e) { // catches ANY exception
	        Dialogs.create().title("Error")
	                .masthead("NO SE PUDE GUARDAR LOS ARCHIVOS:\n" + file.getPath())
	                .showException(e);
	    }
	}

}