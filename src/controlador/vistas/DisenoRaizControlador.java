package controlador.vistas;

import java.io.File;

import javafx.fxml.FXML;
import javafx.stage.FileChooser;

import org.controlsfx.dialog.Dialogs;

import controlador.MainApp;
import controlador.MainApp2;

/**
 * The controller for the root layout. The root layout provides the basic
 * application layout containing a menu bar and space where other JavaFX
 * elements can be placed.
 * 
 * @author Marco Jakob
 */

public class DisenoRaizControlador {
    // Reference to the main application
    private MainApp mainApp;
    private MainApp2 mainApp2;
    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    public void setMainApp2(MainApp2 mainApp2) {
        this.mainApp2 = mainApp2;
    }

    /**
     * Creates an empty address book.
     */
    @FXML
    private void Nuevo() {
        mainApp.getnotasData().clear();
        mainApp.setNotaFilePath(null);
    }

    /**
     * Opens a FileChooser to let the user select an address book to load.
     */
    @FXML
    private void Abrir() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

        if (file != null) {
            mainApp.CargarDatosNotaArchivo(file);
        }
    }

    /**
     * Saves the file to the person file that is currently open. If there is no
     * open file, the "save as" dialog is shown.
     */
    @FXML
    private void Guardar() {
        File personFile = mainApp.getPersonFilePath();
        if (personFile != null) {
            mainApp.GuardarDatosNotaArchivo(personFile);
        } else {
        	GuardarComo();
        }
    }

    /**
     * Opens a FileChooser to let the user select a file to save to.
     */
    @FXML
    private void GuardarComo() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

        if (file != null) {
            // Make sure it has the correct extension
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
            mainApp.GuardarDatosNotaArchivo(file);
        }
    }

    /**
     * Opens an about dialog.
     */
    @FXML
    private void Sobre() {
        Dialogs.create()
            .title("AddressApp")
            .masthead("About")
            .message("Author: Marco Jakob\nWebsite: http://code.makery.ch")
            .showInformation();
    }


    /**
     * Closes the application.
     */
    @FXML
    private void Salir() {
        System.exit(0);
    }
}