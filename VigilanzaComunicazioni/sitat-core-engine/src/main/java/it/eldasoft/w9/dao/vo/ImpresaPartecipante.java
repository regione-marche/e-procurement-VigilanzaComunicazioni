package it.eldasoft.w9.dao.vo;

import java.io.Serializable;

/**
 * Bean che rappresenta il record estratto per creare la lista delle 
 * imprese invitate/partecipanti (W9IMPRESE)
 * 
 * @author Luca.Giacomazzo
 */
public class ImpresaPartecipante implements Serializable {

  /** UID */
	private static final long serialVersionUID = 884575354368859548L;
	
	private Long codiceGara;    // codgara
  private Long codiceLotto;   // codlott
  private Long numeroImpresa; // num_impr
  private Long tipoImpresa;   // tipoagg
  private String descTipoImpresa;  // tab1desc
  private String codiceImpresa;    // codimp
  private String nomeEstesoImpr;   // nomest di IMPR
  private Long partecip;    // partecip
  private String descPartecip;    // tab1desc
  private Long numRaggruppamento; // num_ragg
  private Long ruolo;     // ruolo
  private String descRuolo;   // tab1desc
  private Long numeroImpreseGruppo;  // numero delle imprese che compongono un gruppo
  private String identificativoImpresa; //campo fittizio per l'identificativo del record impresa
  


/**
   * Costruttore di default.
   */
  public ImpresaPartecipante() {
    this.codiceGara = null;    // codgara
    this.codiceLotto = null;   // codlott
    this.numeroImpresa = null; // num_impr
    this.tipoImpresa = null;   // tipoagg
    this.descTipoImpresa = null;  // tab1desc
    this.codiceImpresa = null;    // codimp
    this.nomeEstesoImpr = null;   // nomest di IMPR
    this.partecip = null;    // partecip
    this.descPartecip = null;    // tab1desc
    this.numRaggruppamento = null; // num_ragg
    this.ruolo = null;     // ruolo
    this.descRuolo = null;   // tab1desc
    this.numeroImpreseGruppo = null;  // numero delle imprese che compongono un gruppo
    this.identificativoImpresa = null;
  }

  /**
   * @return the codiceGara
   */
  public Long getCodiceGara() {
    return codiceGara;
  }

  /**
   * @param codiceGara the codiceGara to set
   */
  public void setCodiceGara(Long codiceGara) {
    this.codiceGara = codiceGara;
  }

  /**
   * @return the codiceLotto
   */
  public Long getCodiceLotto() {
    return codiceLotto;
  }

  /**
   * @param codiceLotto the codiceLotto to set
   */
  public void setCodiceLotto(Long codiceLotto) {
    this.codiceLotto = codiceLotto;
  }

  /**
   * @return the numeroImpresa
   */
  public Long getNumeroImpresa() {
    return numeroImpresa;
  }

  /**
   * @param numeroImpresa the numeroImpresa to set
   */
  public void setNumeroImpresa(Long numeroImpresa) {
    this.numeroImpresa = numeroImpresa;
  }

  /**
   * @return the tipoImpresa
   */
  public Long getTipoImpresa() {
    return tipoImpresa;
  }

  /**
   * @param tipoImpresa the tipoImpresa to set
   */
  public void setTipoImpresa(Long tipoImpresa) {
    this.tipoImpresa = tipoImpresa;
  }

  /**
   * @return the descTipoImpresa
   */
  public String getDescTipoImpresa() {
    return descTipoImpresa;
  }

  /**
   * @param descTipoImpresa the descTipoImpresa to set
   */
  public void setDescTipoImpresa(String descTipoImpresa) {
    this.descTipoImpresa = descTipoImpresa;
  }

  /**
   * @return the codiceImpresa
   */
  public String getCodiceImpresa() {
    return codiceImpresa;
  }

  /**
   * @param codiceImpresa the codiceImpresa to set
   */
  public void setCodiceImpresa(String codiceImpresa) {
    this.codiceImpresa = codiceImpresa;
  }

  /**
   * @return the nomeEstesoImpr
   */
  public String getNomeEstesoImpr() {
    return nomeEstesoImpr;
  }

  /**
   * @param nomeEstesoImpr the nomeEstesoImpr to set
   */
  public void setNomeEstesoImpr(String nomeEstesoImpr) {
    this.nomeEstesoImpr = nomeEstesoImpr;
  }

  /**
   * @return the partecip
   */
  public Long getPartecip() {
    return partecip;
  }

  /**
   * @param partecip the partecip to set
   */
  public void setPartecip(Long partecip) {
    this.partecip = partecip;
  }

  /**
   * @return the descPartecip
   */
  public String getDescPartecip() {
    return descPartecip;
  }

  /**
   * @param descPartecip the descPartecip to set
   */
  public void setDescPartecip(String descPartecip) {
    this.descPartecip = descPartecip;
  }

  /**
   * @return the numRAggruppamento
   */
  public Long getNumRaggruppamento() {
    return numRaggruppamento;
  }

  /**
   * @param numRAggruppamento the numRAggruppamento to set
   */
  public void setNumRaggruppamento(Long numRaggruppamento) {
    this.numRaggruppamento = numRaggruppamento;
  }

  /**
   * @return the ruolo
   */
  public Long getRuolo() {
    return ruolo;
  }

  /**
   * @param ruolo the ruolo to set
   */
  public void setRuolo(Long ruolo) {
    this.ruolo = ruolo;
  }

  /**
   * @return the descRuolo
   */
  public String getDescRuolo() {
    return descRuolo;
  }

  /**
   * @param descRuolo the descRuolo to set
   */
  public void setDescRuolo(String descRuolo) {
    this.descRuolo = descRuolo;
  }

  /**
   * @return the numeroImpreseGruppo
   */
  public Long getNumeroImpreseGruppo() {
    return numeroImpreseGruppo;
  }

  /**
   * @param numeroImpreseGruppo the numeroImpreseGruppo to set
   */
  public void setNumeroImpreseGruppo(Long numeroImpreseGruppo) {
    this.numeroImpreseGruppo = numeroImpreseGruppo;
  }
 
  public String getIdentificativoImpresa() {
	return identificativoImpresa;
  }

  public void setIdentificativoImpresa(String identificativoImpresa) {
	this.identificativoImpresa = identificativoImpresa;
  }
  
}
