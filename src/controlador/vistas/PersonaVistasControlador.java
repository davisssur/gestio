package controlador.vistas;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;

import org.controlsfx.dialog.Dialogs;

import controlador.MainApp2;
import controlador.ayuda.FechaAyuda;
import controlador.modelo.Persona2;

public class PersonaVistasControlador {
	@FXML
	private TableView<Persona2> personaTable;
	@FXML
	private TableColumn<Persona2, String> nombreColumn;
	@FXML
	private TableColumn<Persona2, String> apellidoColumn;

	@FXML
	private Label nombreLabel;
	@FXML
	private Label apellidoLabel;
	@FXML
	private Label calleLabel;
	@FXML
	private Label codigopostalLabel;
	@FXML
	private Label ciudadLabel;
	@FXML
	private Label cumpleLabel;

	// Reference to the main application.

	private MainApp2 mainApp2;

	/**
	 * The constructor. The constructor is called before the initialize() method.
	 * 
	 */

	public PersonaVistasControlador() {
	}

	/**
	 * Initializes the controller class. This method is automatically called after
	 * the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		// Initialize the person table with the two columns.
		nombreColumn.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
		apellidoColumn.setCellValueFactory(cellData -> cellData.getValue().apellidoProperty());
		nombreColumn.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
		apellidoColumn.setCellValueFactory(cellData -> cellData.getValue().apellidoProperty());

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
			alert.initOwner(mainApp2.getPrimaryStage());
			alert.setTitle("No seleccionada");
			alert.setHeaderText("No Persona Selecccionada");
			alert.setContentText("Selecciona una persona de la tabla.");

			alert.showAndWait();
		}
	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp2
	 */
	public void setMainApp(MainApp2 mainApp2) {
		this.mainApp2 = mainApp2;

		// Add observable list data to the table
		personaTable.setItems(mainApp2.getPersonaData());
	}

	private void showPersonaDetails(Persona2 persona) {
		if (persona != null) {
			// Fill the labels with info from the person object.
			nombreLabel.setText(persona.getnombre());
			apellidoLabel.setText(persona.getapellido());
			calleLabel.setText(persona.getcalle());
			codigopostalLabel.setText(Integer.toString(persona.getcodigopostal()));
			ciudadLabel.setText(persona.getciudad());
			cumpleLabel.setText(FechaAyuda.format(persona.getcumpleaños()));

		} else {
			// Person is null, remove all the text.
			nombreLabel.setText("");
			apellidoLabel.setText("");
			calleLabel.setText("");
			codigopostalLabel.setText("");
			ciudadLabel.setText("");
			cumpleLabel.setText("");
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
			alert.initOwner(mainApp2.getPrimaryStage());
			alert.setTitle("No seleccionada");
			alert.setHeaderText("No Persona Selecccionada");
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
		Persona2 tempPersona = new Persona2();
		boolean okClicked = mainApp2.showEditarPersonaControlador(tempPersona);
		if (okClicked) {
			mainApp2.getPersonaData().add(tempPersona);
		}
	}

	/**
	 * Called when the user clicks the edit button. Opens a dialog to edit details
	 * for the selected person.
	 */
	@FXML
	private void handleEditPersona() {
		Persona2 selectedPersona = personaTable.getSelectionModel().getSelectedItem();
		if (selectedPersona != null) {
			boolean okClicked = mainApp2.showEditarPersonaControlador(selectedPersona);
			if (okClicked) {
				showPersonaDetails(selectedPersona);
			}

		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp2.getPrimaryStage());
			alert.setTitle("No seleccionada");
			alert.setHeaderText("No Persona Selecccionada");
			alert.setContentText("Selecciona una persona de la tabla..");

			alert.showAndWait();
		}
	}

}