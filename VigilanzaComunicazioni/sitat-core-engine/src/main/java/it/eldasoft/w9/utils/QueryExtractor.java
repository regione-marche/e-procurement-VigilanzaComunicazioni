/*
 * Created on 02/dic/08
 *
 * Copyright (c) EldaSoft S.p.A.
 * Tutti i diritti sono riservati.
 *
 * Questo codice sorgente e' materiale confidenziale di proprieta' di EldaSoft S.p.A.
 * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di 
 * aver prima formalizzato un accordo specifico con EldaSoft.
 */
package it.eldasoft.w9.utils;

import java.io.InputStream;
import org.dom4j.Document;
import org.dom4j.Node; 
/**
 * Classe che consente l'interrogazione del db mediante query inserite in un file xml.
 * 
 * @author Eliseo Marini
 */

public class QueryExtractor {

  /**
   * Costruttore di default.
   */
	public QueryExtractor() {
	}

	/**
	 * Metodo che ritorna la query presente in un file xml.
	 * 
	 * @param fileXML rappresenta il nome del file xml (esclusa l'estensione) contenente i metodi e le query
	 * @param nomeMetodo rappresenta il nome del metodo che deve essere preso in considerazione nel file xml
	 * @param nomeQuery rappresenta il nome della query che deve essere eseguita
	 * @return Ritorna la query presente nel file XML 
	 */
	/*public String getQuery(final String fileXML, final String nomeMetodo, final String nomeQuery) {
		String query = "";
		String percorsoXML = "it/eldasoft/w9/utils/common/" + fileXML + ".xml";
		try {
			InputStream inSQL = this.getClass().getClassLoader().getResourceAsStream(percorsoXML);
			XmlParser pars = new XmlParser(); 
			Document docSQL = pars.parse(inSQL);
			String selectNode = "//Classe/metodo[@nome='" + nomeMetodo + "']/query[@nome='" + nomeQuery + "']";
			Node nodeQuery = docSQL.selectSingleNode(selectNode);
			query = nodeQuery.getText();
			return query;
		} catch (Exception ex) {
			System.out.println("Errore in QueryExtractor.getQuery, caused by : " + ex.getMessage());
			ex.printStackTrace();
			return null;
		}
	}*/

  /**
   * Ritorna la query.
   * 
   * @param nomeMetodo nome del metodo
   * @param nomeQuery nome della query
   * @return Ritorna la query presente nel file XMl
   */
  public String getQuery(final String tipoDB, final String nomeMetodo, final String nomeQuery) {
    String query = "";
    
    String percorsoXML = "it/eldasoft/w9/utils/common/W9Query-" + tipoDB.toUpperCase() + ".xml";
    try {
      InputStream inSQL = this.getClass().getClassLoader().getResourceAsStream(percorsoXML);
      XmlParser pars = new XmlParser();
      Document docSQL = pars.parse(inSQL);
      String selectNode =
          "//Classe/metodo[@nome='"
              + nomeMetodo
              + "']/query[@nome='"
              + nomeQuery
              + "']";
      Node nodeQuery = docSQL.selectSingleNode(selectNode);
      query = nodeQuery.getText();
      return query;
    } catch (Exception ex) {
      System.out.println("Errore in QueryExtractor.getQuey, caused by : "
          + ex.getMessage());
      ex.printStackTrace();
      return null;
    }
  }

}
