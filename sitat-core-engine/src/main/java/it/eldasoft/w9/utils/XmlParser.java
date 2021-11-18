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
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

/**
 * @author Eliseo Marini
 * 
 */
public class XmlParser {

  /**
   * Conversione dell'inputStream contenente l'XML nell'oggetto Document.
   * 
   * @param input InputStream che rappresenta l'XML
   * @return Ritorna l'oggetto Document a partire dall'inputStream dell'XML
   * @throws DocumentException DocumentException
   */
	public Document parse(final InputStream input) throws DocumentException {
		SAXReader reader = new SAXReader();
		Document document = reader.read(input);
		return document;
	}
}
