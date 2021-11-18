/*
 * Created on 01/giu/2017
 *
 * Copyright (c) Maggioli S.p.A.
 * Tutti i diritti sono riservati.
 *
 * Questo codice sorgente e' materiale confidenziale di proprieta' di Maggioli S.p.A.
 * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di
 * aver prima formalizzato un accordo specifico con Maggioli.
 */
package it.eldasoft.w9.dao.vo.rest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Dati di pubblicazione di un lotto.
 *
 * @author Mirco.Franzoni
 */

public class PubblicaLottoEntry implements Serializable {
	/**
	 * UID.
	 */
	private static final long serialVersionUID = -6611269573839884401L;

	private String oggetto;

	private Double importoLotto;

	private Double importoSicurezza;

	private Double importoTotale;

	private String cpv;

	private Long idSceltaContraente;

	private Long idSceltaContraente50;

	private String categoria;

	private Long numeroLotto;

	private String classe;

	private String lottoPrecedente;

	private Long motivo;

	private String cigCollegato;

	private String tipoAppalto;

	private String settore;

	private Long criterioAggiudicazione;
	  
	private String luogoIstat;

	private String luogoNuts;

	private String cig;

	private String cupEsente;

	private String cup;

	private String urlCommittente;
	  
	private String urlPiattaformaTelematica;
	
	private String cui;
	
	private String sommaUrgenza;
	
	private String manodopera;
	
	private String codiceIntervento;
	
	private Long prestazioniComprese;
	
	private String contrattoEsclusoArt19e26;
	
	private String contrattoEsclusoArt16e17e18;
	
	private String contrattoEsclusoAlleggerito;
	
	private String esclusioneRegimeSpeciale;
	
	private List<AppaFornEntry> modalitaAcquisizioneForniture = new ArrayList<AppaFornEntry>();
	
	private List<AppaLavEntry> tipologieLavori = new ArrayList<AppaLavEntry>();
	
	private List<MotivazioneProceduraNegoziataEntry> motivazioniProceduraNegoziata = new ArrayList<MotivazioneProceduraNegoziataEntry>();
	
	private List<CategoriaLottoEntry> categorie = new ArrayList<CategoriaLottoEntry>();
	  
	private List<CpvLottoEntry> cpvSecondari = new ArrayList<CpvLottoEntry>();
	  
	//private DatiGeneraliTecnicoEntry tecnicoRup;
	private String exSottosoglia;
	
	private String cigMaster;

	private String idSchedaLocale;
	
	private String idSchedaSimog;
	
	public void setCategorie(List<CategoriaLottoEntry> categorie) {
		this.categorie = categorie;
	}

	public List<CategoriaLottoEntry> getCategorie() {
		return categorie;
	}

	public void setCpvSecondari(List<CpvLottoEntry> cpvSecondari) {
		this.cpvSecondari = cpvSecondari;
	}

	public List<CpvLottoEntry> getCpvSecondari() {
		return cpvSecondari;
	}

	/*
	public void setTecnicoRup(DatiGeneraliTecnicoEntry tecnicoRup) {
		this.tecnicoRup = tecnicoRup;
	}

	public DatiGeneraliTecnicoEntry getTecnicoRup() {
		return tecnicoRup;
	}
	*/
	public void setSettore(String settore) {
		this.settore = settore;
	}

	public String getSettore() {
		return settore;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public String getOggetto() {
		return oggetto;
	}

	public void setIdSceltaContraente50(Long idSceltaContraente50) {
		this.idSceltaContraente50 = idSceltaContraente50;
	}

	public Long getIdSceltaContraente50() {
		return idSceltaContraente50;
	}

	public void setImportoLotto(Double importoLotto) {
		this.importoLotto = importoLotto;
	}

	public Double getImportoLotto() {
		if (importoLotto != null)
			return importoLotto;
		else
			return new Double(0);
	}

	public void setImportoSicurezza(Double importoSicurezza) {
		this.importoSicurezza = importoSicurezza;
	}

	public Double getImportoSicurezza() {
		if (importoSicurezza != null)
			return importoSicurezza;
		else
			return new Double(0);
	}

	public void setImportoTotale(Double importoTotale) {
		this.importoTotale = importoTotale;
	}

	public Double getImportoTotale() {
		return importoTotale;
	}

	public void setCpv(String cpv) {
		this.cpv = cpv;
	}

	public String getCpv() {
		return cpv;
	}

	
	public void setIdSceltaContraente(Long idSceltaContraente) {
		this.idSceltaContraente = idSceltaContraente;
	}

	public Long getIdSceltaContraente() {
		return idSceltaContraente;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setNumeroLotto(Long numeroLotto) {
		this.numeroLotto = numeroLotto;
	}

	public Long getNumeroLotto() {
		return numeroLotto;
	}

	public void setClasse(String classe) {
		this.classe = classe;
	}

	public String getClasse() {
		return classe;
	}

	
	public void setLottoPrecedente(String lottoPrecedente) {
		this.lottoPrecedente = lottoPrecedente;
	}

	public String getLottoPrecedente() {
		return lottoPrecedente;
	}

	public void setMotivo(Long motivo) {
		this.motivo = motivo;
	}

	public Long getMotivo() {
		return motivo;
	}

	public void setCigCollegato(String cigCollegato) {
		this.cigCollegato = cigCollegato;
	}

	public String getCigCollegato() {
		return cigCollegato;
	}

	public void setTipoAppalto(String tipoAppalto) {
		this.tipoAppalto = tipoAppalto;
	}

	public String getTipoAppalto() {
		return tipoAppalto;
	}

	public void setCriterioAggiudicazione(Long criterioAggiudicazione) {
		this.criterioAggiudicazione = criterioAggiudicazione;
	}

	public Long getCriterioAggiudicazione() {
		return criterioAggiudicazione;
	}

	public void setLuogoIstat(String luogoIstat) {
		this.luogoIstat = luogoIstat;
	}

	public String getLuogoIstat() {
		return luogoIstat;
	}

	public void setLuogoNuts(String luogoNuts) {
		this.luogoNuts = luogoNuts;
	}

	public String getLuogoNuts() {
		return luogoNuts;
	}

	public void setCig(String cig) {
		this.cig = cig;
	}

	public String getCig() {
		return cig;
	}

	public void setCup(String cup) {
		this.cup = cup;
	}

	public String getCup() {
		return cup;
	}

	public void setUrlCommittente(String urlCommittente) {
		this.urlCommittente = urlCommittente;
	}

	public String getUrlCommittente() {
		return urlCommittente;
	}

	public void setUrlPiattaformaTelematica(String urlPiattaformaTelematica) {
		this.urlPiattaformaTelematica = urlPiattaformaTelematica;
	}

	public String getUrlPiattaformaTelematica() {
		return urlPiattaformaTelematica;
	}

	public void setCupEsente(String cupEsente) {
		this.cupEsente = cupEsente;
	}

	public String getCupEsente() {
		return cupEsente;
	}

	public void setCui(String cui) {
		this.cui = cui;
	}

	public String getCui() {
		return cui;
	}

	public void setSommaUrgenza(String sommaUrgenza) {
		this.sommaUrgenza = sommaUrgenza;
	}

	public String getSommaUrgenza() {
		return sommaUrgenza;
	}

	public void setManodopera(String manodopera) {
		this.manodopera = manodopera;
	}

	public String getManodopera() {
		return manodopera;
	}

	public void setCodiceIntervento(String codiceIntervento) {
		this.codiceIntervento = codiceIntervento;
	}

	public String getCodiceIntervento() {
		return codiceIntervento;
	}

	public void setPrestazioniComprese(Long prestazioniComprese) {
		this.prestazioniComprese = prestazioniComprese;
	}

	public Long getPrestazioniComprese() {
		return prestazioniComprese;
	}

	public void setContrattoEsclusoArt19e26(String contrattoEsclusoArt19e26) {
		this.contrattoEsclusoArt19e26 = contrattoEsclusoArt19e26;
	}

	public String getContrattoEsclusoArt19e26() {
		return contrattoEsclusoArt19e26;
	}

	public void setContrattoEsclusoArt16e17e18(
			String contrattoEsclusoArt16e17e18) {
		this.contrattoEsclusoArt16e17e18 = contrattoEsclusoArt16e17e18;
	}

	public String getContrattoEsclusoArt16e17e18() {
		return contrattoEsclusoArt16e17e18;
	}

	public void setModalitaAcquisizioneForniture(
			List<AppaFornEntry> modalitaAcquisizioneForniture) {
		this.modalitaAcquisizioneForniture = modalitaAcquisizioneForniture;
	}

	public List<AppaFornEntry> getModalitaAcquisizioneForniture() {
		return modalitaAcquisizioneForniture;
	}

	public void setTipologieLavori(List<AppaLavEntry> tipologieLavori) {
		this.tipologieLavori = tipologieLavori;
	}

	public List<AppaLavEntry> getTipologieLavori() {
		return tipologieLavori;
	}

	public void setMotivazioniProceduraNegoziata(
			List<MotivazioneProceduraNegoziataEntry> motivazioniProceduraNegoziata) {
		this.motivazioniProceduraNegoziata = motivazioniProceduraNegoziata;
	}

	public List<MotivazioneProceduraNegoziataEntry> getMotivazioniProceduraNegoziata() {
		return motivazioniProceduraNegoziata;
	}

	public void setExSottosoglia(String exSottosoglia) {
		this.exSottosoglia = exSottosoglia;
	}

	public String getExSottosoglia() {
		return exSottosoglia;
	}

  public String getContrattoEsclusoAlleggerito() {
    return contrattoEsclusoAlleggerito;
  }

  public void setContrattoEsclusoAlleggerito(String contrattoEsclusoAlleggerito) {
    this.contrattoEsclusoAlleggerito = contrattoEsclusoAlleggerito;
  }

  public String getEsclusioneRegimeSpeciale() {
    return esclusioneRegimeSpeciale;
  }

  public void setEsclusioneRegimeSpeciale(String esclusioneRegimeSpeciale) {
    this.esclusioneRegimeSpeciale = esclusioneRegimeSpeciale;
  }

  public void setCigMaster(String cigMaster) {
	  this.cigMaster = cigMaster;
  }

  public String getCigMaster() {
	  return cigMaster;
  }

  public void setIdSchedaLocale(String idSchedaLocale) {
	  this.idSchedaLocale = idSchedaLocale;
  }

  public String getIdSchedaLocale() {
	  return idSchedaLocale;
  }

  public void setIdSchedaSimog(String idSchedaSimog) {
	  this.idSchedaSimog = idSchedaSimog;
  }

  public String getIdSchedaSimog() {
	  return idSchedaSimog;
  }
}
