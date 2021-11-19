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

/**
 * Modalit� acquisizione forniture.
 *
 * @author Mirco.Franzoni
 */
public class AppaFornEntry implements Serializable {
	/**
	 * UID.
	 */
	private static final long serialVersionUID = -4433185026855332865L;

	private Long modalitaAcquisizione;
	
	public void setModalitaAcquisizione(Long modalitaAcquisizione) {
		this.modalitaAcquisizione = modalitaAcquisizione;
	}
	public Long getModalitaAcquisizione() {
		return modalitaAcquisizione;
	}

}
