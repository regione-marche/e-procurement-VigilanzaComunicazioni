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

import java.sql.Timestamp; 
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import it.avlp.simog.massload.xmlbeans.DbDateType;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.utils.utility.UtilityDate;

/**
 * Extension of <code>it.eldasoft.utils.utility.UtilityDate</code>.
 * @author Eliseo Marini
 * @version 1.0.0
 */
public class UtilityDateExtension extends UtilityDate {

  
//  public static Calendar convertJdbcParametroDateToCalendar (JdbcParametro arg0)throws GestoreException{
//    Calendar calendar = new GregorianCalendar (Locale.ITALY);
//    long date = arg0.dataValue().getTime();
//    calendar.setTimeInMillis(date);
//    return calendar;
//  }

  /**
   * Conversione di un oggetto JdbcParametro in oggetto Calendar.
   * 
   * @param arg0 JdbcParametro
   * @return Ritorna un oggetto Calendar dalla conversione del JdbcParamerto in ingresso
   * @throws GestoreException GestoreException
   */
  public static Calendar convertJdbcParametroDateToCalendar(final JdbcParametro arg0) throws GestoreException {
    Calendar calendar = new GregorianCalendar(Locale.ITALY);
    Timestamp tmpStp = arg0.dataValue();
    
    // SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss.SSS");
    Date data = (Date) tmpStp;
    calendar.setTime(data);
    
    DbDateType returnDate = DbDateType.Factory.newInstance();
    returnDate.setCalendarValue(calendar);
    
    return returnDate.getCalendarValue();
  }
  
  /*public static Calendar convertJdbcParametroDateToCalendar(final JdbcParametro arg0) throws GestoreException {
	    Calendar calendar = new GregorianCalendar(Locale.ITALY);
	    Timestamp tmpStp = arg0.dataValue();
	    
	    // SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss.SSS");
	    Date data = (Date) tmpStp;
	    calendar.setTime(data);
	    return calendar;
	  }*/
  /**
   * Converte la data odierna in calendar.
   * 
   * @return il calendar con la data odierna
   * @throws GestoreException GestoreException
   */
  public static Calendar convertTodayToCalendar() throws GestoreException {
    return convertDateToCalendar(new Date());
  }
  
  /**
   * Converte una data in Calendar.
   * @param arg0 la data
   * @return Ritorna un oggetto Calendar a partire da un oggetto Date
   * @throws GestoreException GestoreException
   */
  /*public static Calendar convertDateToCalendar(final Date arg0) throws GestoreException {
    Calendar calendar = new GregorianCalendar(Locale.ITALY);
    calendar.setTime(arg0);
    return calendar;
  }*/
  
  /**
   * Converte una data in Calendar.
   * @param arg0 la data
   * @return Ritorna un oggetto Calendar a partire da un oggetto Date
   * @throws GestoreException GestoreException
   */
  public static Calendar convertDateToCalendar(final Date arg0) throws GestoreException {
    Calendar calendar = new GregorianCalendar(Locale.ITALY);
    calendar.setTime(arg0);
    DbDateType returnDate = DbDateType.Factory.newInstance();
    returnDate.setCalendarValue(calendar);
    return returnDate.getCalendarValue();
  }
  	
}
