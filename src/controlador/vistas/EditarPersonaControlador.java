package controlador.vistas;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import org.controlsfx.dialog.Dialogs;

import controlador.ayuda.FechaAyuda;
import controlador.modelo.Persona2;

/**
 * Dialog to edit details of a person.
 * 
 * @author Marco Jakob
 */
public class EditarPersonaControlador {

    @FXML
    private TextField nombreField;
    @FXML
    private TextField apellidoField;
    @FXML
    private TextField calleField;
    @FXML
    private TextField codigopostalField;
    @FXML
    private TextField ciudadField;
    @FXML
    private TextField cumpleañosField;


    private Stage dialogStage;
    private Persona2 persona;
    private boolean okClicked = false;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    }

    /**
     * Sets the stage of this dialog.
     * 
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Sets the person to be edited in the dialog.
     * 
     * @param person
     */
    public void seleccionar(Persona2 persona) {
        this.persona = persona;

        nombreField.setText(persona.getnombre());
        apellidoField.setText(persona.getapellido());
        calleField.setText(persona.getcalle());
        codigopostalField.setText(Integer.toString(persona.getcodigopostal()));
        ciudadField.setText(persona.getciudad());
        cumpleañosField.setText(FechaAyuda.format(persona.getcumpleaños()));
        cumpleañosField.setPromptText("dd.mm.yyyy");
    }

    /**
     * Returns true if the user clicked OK, false otherwise.
     * 
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void guardar() {
        if (Comprovacion()) {
            persona.setnombre(nombreField.getText());
            persona.setapellido(apellidoField.getText());
            persona.setcalle(calleField.getText());
            persona.setcodigopostal(Integer.parseInt(codigopostalField.getText()));
            persona.setciudad(ciudadField.getText());
            persona.setcumpleaños(FechaAyuda.parse(cumpleañosField.getText()));

            okClicked = true;
            dialogStage.close();
        }
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void Cancelar() {
        dialogStage.close();
    }

    /**
     * Validates the user input in the text fields.
     * 
     * @return true if the input is valid
     */
  
    private boolean Comprovacion() {
        String errorMessage = "";

        if (nombreField.getText() == null || nombreField.getText().length() == 0) {
            errorMessage += "No nombre valido !\n"; 
        }
        if (apellidoField.getText() == null || apellidoField.getText().length() == 0) {
            errorMessage += "No apellido valido!\n"; 
        }
        if (calleField.getText() == null || calleField.getText().length() == 0) {
            errorMessage += "No calle valida !\n"; 
        }

        if (codigopostalField.getText() == null || codigopostalField.getText().length() == 0) {
            errorMessage += "No codigo postal valido!\n"; 
        } else {
            // try to parse the postal code into an int.
            try {
                Integer.parseInt(codigopostalField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No codigo postal valido  (tiene que ser numeros)!\n"; 
            }
        }

        if (ciudadField.getText() == null || ciudadField.getText().length() == 0) {
            errorMessage += "No valid ciudad!\n"; 
        }

        if (cumpleañosField.getText() == null || cumpleañosField.getText().length() == 0) {
            errorMessage += "No valid cumpleaños!\n";
        } else {
            if (!FechaAyuda.validDate(cumpleañosField.getText())) {
                errorMessage += "No valid cumpleaños. Use the format dd.mm.yyyy!\n";
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Dialogs.create()
                .title("Invalid Fields")
                .masthead("Please correct invalid fields")
                .message(errorMessage)
                .showError();
            return false;
        }
    }
}


