package it.unibs.ing.progetto.ristorante.XML;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReaderXML {
    private static final String ERRORE_READER = "Errore nell'inizializzazione del reader: ";
    private static final String STRINGA_INIZIO_LETTURA = "Inzio lettura file: ";
    private static final String STRINGA_FINE_LETTURA = "Fine lettura file: ";
    private static final String CITY = "city";
    private static final String LINK = "link";
    private static final String MAPPA_CREATA = "La mappaTerritorio e' stata creata con successo";

    /**
     * Legge il file .xml che contiene le città e i vari collegamenti tra esse. Inserisce in elencoNodi le città, in
     * mappaArchi l'id della città di partenza come key e gli id delle città di arrivo rispettive come valori, in
     * mappaTerritorio un Arco come key e il carburante utilizzato dal veicolo 1 e quello utilizzato dal veicolo 2 come
     * valori rispettivi.
     * @param filename file .xml che contiene le città e i vari collegamenti tra esse.
     */
    /*
    public void leggiXML (String filename) {
        int idNodoPartenza = 0;
        ArrayList<Integer> elencoIdNodiArrivo = null;
        mappaTerritorio = new HashMap<>();
        mappaArchi = new HashMap<>();
        elencoNodi = new ArrayList<>();
        //Questo frammento di codice serve a creare ed istanziare la variabile xmlr di tipo XMLStreamReader,
        //che sarà utilizzata per leggere il file XML
        XMLInputFactory xmlif;
        XMLStreamReader xmlr;
        try {
            xmlif = XMLInputFactory.newInstance();
            xmlr = xmlif.createXMLStreamReader(filename, new FileInputStream(filename));
            System.out.println(STRINGA_INIZIO_LETTURA+filename);
            //Legge il File xml fino a quando ci sono eventi di parsing disponibili
            while (xmlr.hasNext()) {
                //Se trova un evento di tipo START.ELEMENT controlla il nome del tag dell'elemento corrente
                if (xmlr.getEventType() == XMLStreamConstants.START_ELEMENT) {
                    String nomeTag = xmlr.getLocalName();
                    //Se il tag è "city" allora crea un nuovo oggetto Nodo e lo aggiunge all'elencoNodi
                    if (nomeTag.equals(CITY)) {
                        idNodoPartenza = Integer.parseInt(xmlr.getAttributeValue(0));
                        String name = xmlr.getAttributeValue(1);
                        int x = Integer.parseInt(xmlr.getAttributeValue(2));
                        int y = Integer.parseInt(xmlr.getAttributeValue(3));
                        int h = Integer.parseInt(xmlr.getAttributeValue(4));
                        elencoNodi.add(new Nodo(name, idNodoPartenza, x, y, h));
                        //Crea un arrayList in cui verranno inseriti gli id delle città a cui si collega
                        elencoIdNodiArrivo = new ArrayList<>();
                    }
                    //Se il tag è "link" allora inserisci l'id trovato in elencoNodiArrivo
                    else if (nomeTag.equals(LINK)){
                        //Continua finchè ci sono eventi di tipo START_ELEMENT (che hanno tag "link")
                        while (xmlr.getEventType() == XMLStreamConstants.START_ELEMENT) {
                            int idNodoArrivo = Integer.parseInt(xmlr.getAttributeValue(0));
                            elencoIdNodiArrivo.add(idNodoArrivo);
                            xmlr.next();
                            xmlr.next();
                        }
                    }
                }
                //Quando trova un evento di tipo END_ELEMENT
                if (xmlr.getEventType() == XMLStreamConstants.END_ELEMENT){
                    String nomeTag = xmlr.getLocalName();
                    //Se il tag è "city" imposta la mappaArchi
                    if (nomeTag.equals(CITY)){
                        mappaArchi.put(idNodoPartenza, elencoIdNodiArrivo);
                    }
                }
                //Passa all’evento successivo
                xmlr.next();
            }
            System.out.println(STRINGA_FINE_LETTURA+filename);
            creaMappaTerritorio(mappaArchi, elencoNodi);
            System.out.println(MAPPA_CREATA);
        } catch (Exception e) {
            System.out.println(ERRORE_READER);
            System.out.println(e.getMessage());
        }
    }
	*/

    /**
     * Imposta la mappaTerritorio. Come key imposta i vari Archi presenti, e come valore rispettivo quanto
     * carburante usa il primo veicolo e quanto il secondo.
     * @param mappaArchi Map contenente gli id delle città ed i loro collegamenti.
     * @param elencoNodi ArrayList delle città.
     */
    /*
    public void creaMappaTerritorio(Map<Integer, ArrayList<Integer>> mappaArchi, ArrayList<Nodo> elencoNodi){
        for(int i = 0; i < mappaArchi.size(); i++){
            for (int j = 0; j < mappaArchi.get(i).size(); j++){
                Nodo nodoPartenza = elencoNodi.get(i);
                Nodo nodoArrivo = elencoNodi.get(mappaArchi.get(i).get(j));
                double consumoVeicolo1 = nodoPartenza.distanzaNelPianoXY(nodoArrivo);
                double consumoVeicolo2 = nodoPartenza.differenzaAltezze(nodoArrivo);
                mappaTerritorio.put(new Arco(nodoPartenza, nodoArrivo), new Double[]{consumoVeicolo1,consumoVeicolo2});
            }
        }
    }
    */
}