package controlador.modelo;

import java.time.LocalDate;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import controlador.ayuda.AdaptadorDeFecha;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Persona2 {

    private final StringProperty nombre;
    private final StringProperty apellido;
    private final StringProperty calle;
    private final IntegerProperty codigopostal;
    private final StringProperty ciudad;
    private final ObjectProperty<LocalDate> cumpleaños;

    /**
     * Default constructor.
     */
    public Persona2() {
        this(null, null);
    }
    
    /**
     * Constructor with some initial data.
     * 
     * @param nombre
     * @param apellido
     */
    public Persona2(String nombre, String apellido) {
        this.nombre = new SimpleStringProperty(nombre);
        this.apellido = new SimpleStringProperty(apellido);
        
        // Some initial dummy data, just for convenient testing.
        this.calle = new SimpleStringProperty("some calle");
        this.codigopostal = new SimpleIntegerProperty(1234);
        this.ciudad = new SimpleStringProperty("some ciudad");
        this.cumpleaños = new SimpleObjectProperty<LocalDate>(LocalDate.of(1999, 2, 21));
    }
    
    public String getnombre() {
        return nombre.get();
    }

    public void setnombre(String nombre) {
        this.nombre.set(nombre);
    }
    
    public StringProperty nombreProperty() {
        return nombre;
    }

    public String getapellido() {
        return apellido.get();
    }

    public void setapellido(String apellido) {
        this.apellido.set(apellido);
    }
    
    public StringProperty apellidoProperty() {
        return apellido;
    }

    public String getcalle() {
        return calle.get();
    }

    public void setcalle(String calle) {
        this.calle.set(calle);
    }
    
    public StringProperty calleProperty() {
        return calle;
    }

    public int getcodigopostal() {
        return codigopostal.get();
    }

    public void setcodigopostal(int codigopostal) {
        this.codigopostal.set(codigopostal);
    }
    
    public IntegerProperty codigopostalProperty() {
        return codigopostal;
    }

    public String getciudad() {
        return ciudad.get();
    }

    public void setciudad(String ciudad) {
        this.ciudad.set(ciudad);
    }
    
    public StringProperty ciudadProperty() {
        return ciudad;
    }
    @XmlJavaTypeAdapter(AdaptadorDeFecha.class)
    public LocalDate getcumpleaños() {
        
    	return cumpleaños.get();
    }

    public void setcumpleaños(LocalDate cumpleaños) {
        this.cumpleaños.set(cumpleaños);
    }
    
    public ObjectProperty<LocalDate> cumpleañosProperty() {
        return cumpleaños;
    }
}