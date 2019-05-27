package controlador.modelo;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Helper class to wrap a list of persons. This is used for saving the list of
 * persons to XML.
 * 
 * @author Marco Jakob
 */
@XmlRootElement(name = "personas")
public class ListaPersonaWrapper2 {
	
	private List<Persona2> personas;

	@XmlElement(name = "persona")
	public List<Persona2> getPersonas() {
		return personas;
	}

	public void setPersonas(List<Persona2> personas) {
		this.personas = personas;
	}
}
