package it.unibs.ing.progetto.ristorante.pattern;

import java.util.List;

import it.unibs.ing.progetto.ristorante.model.Piatto;

/**
 * Interfaccia per sfruttare il pattern Composite
 * 
 * @author 
 *
 */
public interface MenuComponent {
	int getCaricoLavoro();
	List<Piatto> getContenuto();
}
