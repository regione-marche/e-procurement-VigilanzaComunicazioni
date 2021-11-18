package it.eldasoft.w9.dao.vo;

import java.io.Serializable;

public class ImpresaAggiudicataria implements Serializable {

	/** UID */
	private static final long serialVersionUID = 9016504783962684901L;
	
	// campi propri della tabella W9AGGI
	private Long codiceGara; // codgara
	private Long codiceLotto; // codlott
	private Long numAppalto; // numAppa
	private Long numeroAggiudicataria; // num_aggi
	private Long tipoAggiudicataria; // id_tipoagg
	private Long ruolo; // ruolo
	private String flagAvvalimento; // flag_avvalimento
	private String codiceInps; // codice_inps
	private String codiceInail; // codice_inail
	private String codiceCassa; // codice_cassa
	private String codiceImpresa; // codimp
	private String codiceImpresaAusiliaria; // codimp_ausiliaria
	private Long idGruppo; // id_gruppo

	// chiavi esterne
	private String descTipoImpresa; // tab1desc
	private String nomeEstesoImpr; // nomest di IMPR
	private String descRuolo; // tab1desc
	private String nomeEstesoImprAusiliaria; // nomest di IMPR  (ausiliaria)
	
	// campi derivati
	private Long numeroImpreseGruppo; // numero delle imprese che compongono un
										// gruppo
	private String identificativoImpresa; // campo fittizio per l'identificativo
											// del record impresa

	/**
	 * Costruttore di default.
	 */
	public ImpresaAggiudicataria() {
		this.codiceGara = null; // codgara
		this.codiceLotto = null; // codlott
		this.numAppalto = null; // numAppa
		this.numeroAggiudicataria = null; // num_aggi
		this.tipoAggiudicataria = null; // id_tipoagg
		this.ruolo = null; // ruolo
		this.flagAvvalimento = null; // flag_avvalimento
		this.codiceInps = null; // codice_inps
		this.codiceInail = null; // codice_inail
		this.codiceCassa = null; // codice_cassa
		this.codiceImpresa = null; // codimp
		this.codiceImpresaAusiliaria = null; // codimp_ausiliaria
		this.nomeEstesoImprAusiliaria = null;
		this.idGruppo = null; // id_gruppo
		this.descTipoImpresa = null; // tab1desc
		this.nomeEstesoImpr = null; // nomest di IMPR
		this.descRuolo = null; // tab1desc
		this.numeroImpreseGruppo = null; // numero delle imprese che compongono
											// un gruppo
		this.identificativoImpresa = null; // campo fittizio per
											// l'identificativo
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
	 * @return the numAppalto
	 */
	public Long getNumAppalto() {
		return numAppalto;
	}

	/**
	 * @param numAppalto the numAppalto to set
	 */
	public void setNumAppalto(Long numAppalto) {
		this.numAppalto = numAppalto;
	}

	/**
	 * @return the numeroAggiudicataria
	 */
	public Long getNumeroAggiudicataria() {
		return numeroAggiudicataria;
	}

	/**
	 * @param numeroAggiudicataria the numeroAggiudicataria to set
	 */
	public void setNumeroAggiudicataria(Long numeroAggiudicataria) {
		this.numeroAggiudicataria = numeroAggiudicataria;
	}

	/**
	 * @return the tipoAggiudicataria
	 */
	public Long getTipoAggiudicataria() {
		return tipoAggiudicataria;
	}

	/**
	 * @param tipoAggiudicataria the tipoAggiudicataria to set
	 */
	public void setTipoAggiudicataria(Long tipoAggiudicataria) {
		this.tipoAggiudicataria = tipoAggiudicataria;
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
	 * @return the flagAvvalimento
	 */
	public String getFlagAvvalimento() {
		return flagAvvalimento;
	}

	/**
	 * @param flagAvvalimento the flagAvvalimento to set
	 */
	public void setFlagAvvalimento(String flagAvvalimento) {
		this.flagAvvalimento = flagAvvalimento;
	}

	/**
	 * @return the codiceInps
	 */
	public String getCodiceInps() {
		return codiceInps;
	}

	/**
	 * @param codiceInps the codiceInps to set
	 */
	public void setCodiceInps(String codiceInps) {
		this.codiceInps = codiceInps;
	}

	/**
	 * @return the codiceInail
	 */
	public String getCodiceInail() {
		return codiceInail;
	}

	/**
	 * @param codiceInail the codiceInail to set
	 */
	public void setCodiceInail(String codiceInail) {
		this.codiceInail = codiceInail;
	}

	/**
	 * @return the codiceCassa
	 */
	public String getCodiceCassa() {
		return codiceCassa;
	}

	/**
	 * @param codiceCassa the codiceCassa to set
	 */
	public void setCodiceCassa(String codiceCassa) {
		this.codiceCassa = codiceCassa;
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
	 * @return the codiceImpresaAusiliaria
	 */
	public String getCodiceImpresaAusiliaria() {
		return codiceImpresaAusiliaria;
	}

	/**
	 * @param codiceImpresaAusiliaria the codiceImpresaAusiliaria to set
	 */
	public void setCodiceImpresaAusiliaria(String codiceImpresaAusiliaria) {
		this.codiceImpresaAusiliaria = codiceImpresaAusiliaria;
	}

	/**
	 * @return the idGruppo
	 */
	public Long getIdGruppo() {
		return idGruppo;
	}

	/**
	 * @param idGruppo the idGruppo to set
	 */
	public void setIdGruppo(Long idGruppo) {
		this.idGruppo = idGruppo;
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
	 * @return the nomeEstesoImprAusiliaria
	 */
	public String getNomeEstesoImprAusiliaria() {
		return nomeEstesoImprAusiliaria;
	}

	/**
	 * @param nomeEstesoImprAusiliaria the nomeEstesoImprAusiliaria to set
	 */
	public void setNomeEstesoImprAusiliaria(String nomeEstesoImprAusiliaria) {
		this.nomeEstesoImprAusiliaria = nomeEstesoImprAusiliaria;
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

	/**
	 * @return the identificativoImpresa
	 */
	public String getIdentificativoImpresa() {
		return identificativoImpresa;
	}

	/**
	 * @param identificativoImpresa the identificativoImpresa to set
	 */
	public void setIdentificativoImpresa(String identificativoImpresa) {
		this.identificativoImpresa = identificativoImpresa;
	}

	

}
