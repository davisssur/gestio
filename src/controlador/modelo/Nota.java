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

public class Nota {

    private final StringProperty nombre;
    private final StringProperty descripcion;
    private final ObjectProperty<LocalDate> fecha;

    /**
     * Default constructor.
     */
    public Nota() {
        this(null, null);
    }
    
    /**
     * Constructor with some initial data.
     * 
     * @param nombre
     * @param descripcion
     */
    public Nota(String nombre, String descripcion) {
        this.nombre = new SimpleStringProperty(nombre);
        this.descripcion = new SimpleStringProperty(descripcion);
        
        // Some initial dummy data, just for convenient testing.
     
        this.fecha = new SimpleObjectProperty<LocalDate>(LocalDate.of(1999, 2, 21));
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

    public String getdescripcion() {
        return descripcion.get();
    }

    public void setdescripcion(String descripcion) {
        this.descripcion.set(descripcion);
    }
    
    public StringProperty descripcionProperty() {
        return descripcion;
    }

    @XmlJavaTypeAdapter(AdaptadorDeFecha.class)
    public LocalDate getfecha() {
        
    	return fecha.get();
    }

    public void setfecha(LocalDate fecha) {
        this.fecha.set(fecha);
    }
    
    public ObjectProperty<LocalDate> fechaProperty() {
        return fecha;
    }
}