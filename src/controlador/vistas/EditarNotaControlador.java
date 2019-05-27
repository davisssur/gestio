package controlador.vistas;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import org.controlsfx.dialog.Dialogs;

import controlador.modelo.Nota;
import controlador.ayuda.FechaAyuda;

/**
 * Dialog to edit details of a person.
 * 
 * @author Marco Jakob
 */
public class EditarNotaControlador {

    @FXML
    private TextField nombreField;
    @FXML
    private TextField descripcionField;
    @FXML
    private TextField fechaField;


    private Stage dialogStage;
    private Nota nota;
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
    public void seleccionar(Nota nota) {
        this.nota = nota;

        nombreField.setText(nota.getnombre());
        descripcionField.setText(nota.getdescripcion());
        fechaField.setText(FechaAyuda.format(nota.getfecha()));
        fechaField.setPromptText("dd.mm.yyyy");
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
            nota.setnombre(nombreField.getText());
            nota.setdescripcion(descripcionField.getText());
            nota.setfecha(FechaAyuda.parse(fechaField.getText()));

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
        if (descripcionField.getText() == null || descripcionField.getText().length() == 0) {
            errorMessage += "No descripcion valido!\n"; 
        }
       

        if (fechaField.getText() == null || fechaField.getText().length() == 0) {
            errorMessage += "No valid fecha!\n";
        } else {
            if (!FechaAyuda.validDate(fechaField.getText())) {
                errorMessage += "No valid fecha. Use the format dd.mm.yyyy!\n";
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


