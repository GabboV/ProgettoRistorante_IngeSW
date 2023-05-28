package it.unibs.ing.progetto.ristorante.model;

import java.util.List;

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
