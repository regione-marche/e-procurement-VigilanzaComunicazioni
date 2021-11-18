/**
 * 
 */
package it.eldasoft.w9.common.bean;

import java.io.Serializable;
import java.util.HashMap;

import it.avlp.simog.massload.xmlbeans.Collaborazione;
import it.avlp.simog.massload.xmlbeans.Collaborazioni;
import it.avlp.simog.massload.xmlbeans.GaraType;
import it.avlp.simog.massload.xmlbeans.SchedaType;

/**
 * Bean contentente tutti gli oggetti necessari all'operazione di presa in carico
 * di una gara per delegata
 * 
 * @author luca.giacomazzo
 */
public class PresaInCaricoGaraDelegataBean implements Serializable {

  /** UID	 */
	private static final long serialVersionUID = -129134840431762677L;
	
	private GaraType garaType;
  private SchedaType schedaType;
  private String funzioneDelegata;
  private String codeinStazAppAttiva;
  private String cfStazAppAttiva;
  private String idAvGara;
  private Long   codiceGara;
  private String cfRup;
  private String denominazioneRup;
  private String codTec;
  private Collaborazione collaborazione;
  private Collaborazioni collaborazioni;
  private HashMap<String, String> hmParametriPerConsultaGara;
  
  public PresaInCaricoGaraDelegataBean() {
    this.garaType = null;
    this.schedaType = null;
    this.funzioneDelegata = null;
    this.codeinStazAppAttiva = null;
    this.cfStazAppAttiva = null;
    this.idAvGara = null;
    this.codiceGara = null;
    this.cfRup = null;
    this.denominazioneRup = null;
    this.codTec = null;
    this.collaborazione = null;
    this.collaborazioni = null;
    this.hmParametriPerConsultaGara = null;
  }

  public GaraType getGaraType() {
    return garaType;
  }

  public void setGaraType(GaraType garaType) {
    this.garaType = garaType;
  }

  public SchedaType getSchedaType() {
    return schedaType;
  }

  public void setSchedaType(SchedaType schedaType) {
    this.schedaType = schedaType;
  }
  
  public String getFunzioneDelegata() {
    return funzioneDelegata;
  }

  public void setFunzioneDelegata(String funzioneDelegata) {
    this.funzioneDelegata = funzioneDelegata;
  }

  public String getCodeinStazAppAttiva() {
    return codeinStazAppAttiva;
  }

  public void setCodeinStazAppAttiva(String codeinStazAppAttiva) {
    this.codeinStazAppAttiva = codeinStazAppAttiva;
  }

  public String getCfStazAppAttiva() {
    return cfStazAppAttiva;
  }

  public void setCfStazAppAttiva(String cfStazAppAttiva) {
    this.cfStazAppAttiva = cfStazAppAttiva;
  }

  public String getIdAvGara() {
    return idAvGara;
  }

  public void setIdAvGara(String idAvGara) {
    this.idAvGara = idAvGara;
  }

  public Long getCodiceGara() {
    return codiceGara;
  }

  public void setCodiceGara(Long codiceGara) {
    this.codiceGara = codiceGara;
  }

  public String getCfRup() {
    return cfRup;
  }

  public void setCfRup(String cfRup) {
    this.cfRup = cfRup;
  }

  public Collaborazione getCollaborazione() {
    return collaborazione;
  }

  public void setCollaborazione(Collaborazione collaborazione) {
    this.collaborazione = collaborazione;
  }

  public Collaborazioni getCollaborazioni() {
    return collaborazioni;
  }

  public void setCollaborazioni(Collaborazioni collaborazioni) {
    this.collaborazioni = collaborazioni;
  }
  
  public String getCodTec() {
    return codTec;
  }

  public void setCodTec(String codTec) {
    this.codTec = codTec;
  }

  public String getDenominazioneRup() {
    return denominazioneRup;
  }

  public void setDenominazioneRup(String denominazioneRup) {
    this.denominazioneRup = denominazioneRup;
  }

  public HashMap<String, String> getHmParametriPerConsultaGara() {
    return hmParametriPerConsultaGara;
  }

  public void setHmParametriPerConsultaGara(
      HashMap<String, String> hmParametriPerConsultaGara) {
    this.hmParametriPerConsultaGara = hmParametriPerConsultaGara;
  }
  
}