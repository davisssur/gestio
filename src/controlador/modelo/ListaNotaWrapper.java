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
@XmlRootElement(name = "notas")
public class ListaNotaWrapper {
	
	private List<Nota> notas;

	@XmlElement(name = "nota")
	public List<Nota> getNotas() {
		return notas;
	}

	public void setNotas(List<Nota> notas) {
		this.notas = notas;
	}
}
