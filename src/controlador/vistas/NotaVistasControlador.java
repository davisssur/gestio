package controlador.vistas;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;

import org.controlsfx.dialog.Dialogs;

import controlador.MainApp;
import controlador.ayuda.FechaAyuda;
import controlador.modelo.Nota;

public class NotaVistasControlador {
	@FXML
	private TableView<Nota> personaTable;
	@FXML
	private TableColumn<Nota, String> nombreColumn;
	@FXML
	private TableColumn<Nota, String> descripcionColumn;

	@FXML
	private Label nombreLabel;
	@FXML
	private Label descripcionLabel;
	@FXML
	private Label fechaLabel;

	// Reference to the main application.

	private MainApp mainApp;

	/**
	 * The constructor. The constructor is called before the initialize() method.
	 * 
	 */

	public NotaVistasControlador() {
	}

	/**
	 * Initializes the controller class. This method is automatically called after
	 * the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		// Initialize the person table with the two columns.
		nombreColumn.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
		descripcionColumn.setCellValueFactory(cellData -> cellData.getValue().descripcionProperty());
		nombreColumn.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
		descripcionColumn.setCellValueFactory(cellData -> cellData.getValue().descripcionProperty());

		// Clear person details.
		showPersonaDetails(null);

		// Listen for selection changes and show the person details when changed.
		personaTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showPersonaDetails(newValue));
	}

	@FXML
	private void eliminarPersona() {
		int selectedIndex = personaTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			personaTable.getItems().remove(selectedIndex);
		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("No seleccionada");
			alert.setHeaderText("No Nota Selecccionada");
			alert.setContentText("Selecciona una persona de la tabla.");

			alert.showAndWait();
		}
	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;

		// Add observable list data to the table
		personaTable.setItems(mainApp.getnotasData());
	}

	private void showPersonaDetails(Nota nota) {
		if (nota != null) {
			// Fill the labels with info from the person object.
			nombreLabel.setText(nota.getnombre());
			descripcionLabel.setText(nota.getdescripcion());
			fechaLabel.setText(FechaAyuda.format(nota.getfecha()));

		} else {
			// Person is null, remove all the text.
			nombreLabel.setText("");
			descripcionLabel.setText("");
			fechaLabel.setText("");
		}
	}

	/**
	 * Called when the user clicks on the delete button.
	 */
	@FXML
	private void handleDeletePersona() {
		int selectedIndex = personaTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			personaTable.getItems().remove(selectedIndex);
		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("No seleccionada");
			alert.setHeaderText("No Nota Selecccionada");
			alert.setContentText("Selecciona una persona de la tabla.");

			alert.showAndWait();
		}
	}

	/**
	 * Called when the user clicks the new button. Opens a dialog to edit details
	 * for a new person.
	 */
	@FXML
	private void handleNewPersona() {
		Nota tempPersona = new Nota();
		boolean okClicked = mainApp.showEditarNotaControlador(tempPersona);
		if (okClicked) {
			mainApp.getnotasData().add(tempPersona);
		}
	}

	/**
	 * Called when the user clicks the edit button. Opens a dialog to edit details
	 * for the selected person.
	 */
	@FXML
	private void handleEditPersona() {
		Nota selectedPersona = personaTable.getSelectionModel().getSelectedItem();
		if (selectedPersona != null) {
			boolean okClicked = mainApp.showEditarNotaControlador(selectedPersona);
			if (okClicked) {
				showPersonaDetails(selectedPersona);
			}

		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("No seleccionada");
			alert.setHeaderText("No Nota Selecccionada");
			alert.setContentText("Selecciona una persona de la tabla..");

			alert.showAndWait();
		}
	}

}