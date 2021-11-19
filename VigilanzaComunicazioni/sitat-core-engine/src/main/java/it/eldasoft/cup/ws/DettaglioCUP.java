/**
 * DatiCUP.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.eldasoft.cup.ws;

import java.util.Date;

public class DettaglioCUP  implements java.io.Serializable {
    private java.lang.String uuid;

    private java.lang.String descrizione_intervento;

    private Integer anno_decisione;

    private java.lang.String indAreaRifer;

    private java.lang.String strum_progr;

    private java.lang.String desc_strum_progr;

    private java.lang.String altre_informazioni;

    private Date data_definitivo;

    private java.lang.String natura;

    private java.lang.String tipologia;

    private java.lang.String settore;

    private java.lang.String sottosettore;

    private java.lang.String categoria;

    private java.lang.String cumulativo;

    private java.lang.String beneficiario;

    private java.lang.String nomeStrInfrastr;

    private java.lang.String strInfrastrUnica;

    private java.lang.String denomImprStab;

    private java.lang.String denomImprStabPrec;

    private java.lang.String ragioneSociale;

    private java.lang.String ragioneSocialePrec;

    private java.lang.String partitaIVA;

    private java.lang.String denominazioneProgetto;

    private java.lang.String ente;

    private java.lang.String attoAmministrativo;

    private java.lang.String scopoIntervento;

    private java.lang.String tipoIndAreaRifer;

    private java.lang.String obiettivoCorso;

    private java.lang.String modInterventoFrequenza;

    private java.lang.String servizio;

    private java.lang.String bene;

    private java.lang.String cpv;

    private java.lang.String cup_master;

    private java.lang.String ragioni_collegamento;

    private java.lang.String sponsorizzazione;

    private java.lang.String finanza_progetto;

    private java.lang.Double costo;

    private java.lang.Double finanziamento;

    private java.lang.String [] codTipoCopFin;

    private LocalizzazioneCUP [] localizzazioni;

    public DettaglioCUP() {
    }

    public DettaglioCUP(
           java.lang.String uuid,
           java.lang.String descrizione_intervento,
           Integer anno_decisione,
           java.lang.String indAreaRifer,
           java.lang.String strum_progr,
           java.lang.String desc_strum_progr,
           java.lang.String altre_informazioni,
           java.lang.String natura,
           Date data_definitivo,
           java.lang.String tipologia,
           java.lang.String settore,
           java.lang.String sottosettore,
           java.lang.String categoria,
           java.lang.String cumulativo,
           java.lang.String beneficiario,
           java.lang.String nomeStrInfrastr,
           java.lang.String strInfrastrUnica,
           java.lang.String denomImprStab,
           java.lang.String denomImprStabPrec,
           java.lang.String ragioneSociale,
           java.lang.String ragioneSocialePrec,
           java.lang.String partitaIVA,
           java.lang.String denominazioneProgetto,
           java.lang.String ente,
           java.lang.String attoAmministrativo,
           java.lang.String scopoIntervento,
           java.lang.String tipoIndAreaRifer,
           java.lang.String obiettivoCorso,
           java.lang.String modInterventoFrequenza,
           java.lang.String servizio,
           java.lang.String bene,
           java.lang.String cpv,
           java.lang.String cup_master,
           java.lang.String ragioni_collegamento,
           java.lang.String sponsorizzazione,
           java.lang.String finanza_progetto,
           java.lang.Double costo,
           java.lang.Double finanziamento,
           java.lang.String[] codTipoCopFin,
           LocalizzazioneCUP[] localizzazioni) {
           this.uuid = uuid;
           this.descrizione_intervento = descrizione_intervento;
           this.indAreaRifer = indAreaRifer;
           this.strum_progr = strum_progr;
           this.desc_strum_progr = desc_strum_progr;
           this.altre_informazioni = altre_informazioni;
           this.anno_decisione = anno_decisione;
           this.data_definitivo = data_definitivo;
           this.natura = natura;
           this.tipologia = tipologia;
           this.settore = settore;
           this.sottosettore = sottosettore;
           this.categoria = categoria;
           this.cumulativo = cumulativo;
           this.beneficiario = beneficiario;
           this.nomeStrInfrastr = nomeStrInfrastr;
           this.strInfrastrUnica = strInfrastrUnica;
           this.denomImprStab = denomImprStab;
           this.denomImprStabPrec = denomImprStabPrec;
           this.ragioneSociale = ragioneSociale;
           this.partitaIVA = partitaIVA;
           this.denominazioneProgetto = denominazioneProgetto;
           this.ente = ente;
           this.attoAmministrativo = attoAmministrativo;
           this.scopoIntervento = scopoIntervento;
           this.tipoIndAreaRifer = tipoIndAreaRifer;
           this.obiettivoCorso = obiettivoCorso;
           this.modInterventoFrequenza = modInterventoFrequenza;
           this.servizio = servizio;
           this.bene = bene;
           this.cpv = cpv;
           this.cup_master = cup_master;
           this.ragioni_collegamento = ragioni_collegamento;
           this.sponsorizzazione = sponsorizzazione;
           this.finanza_progetto = finanza_progetto;
           this.costo = costo;
           this.finanziamento = finanziamento;
           this.codTipoCopFin = codTipoCopFin;
           this.localizzazioni = localizzazioni;
    }


    /**
     * Gets the uuid value for this DatiCUP.
     *
     * @return uuid
     */
    public java.lang.String getUuid() {
        return uuid;
    }


    /**
     * Sets the uuid value for this DatiCUP.
     *
     * @param uuid
     */
    public void setUuid(java.lang.String uuid) {
        this.uuid = uuid;
    }


    /**
     * Gets the descrizione_intervento value for this DatiCUP.
     *
     * @return descrizione_intervento
     */
    public java.lang.String getDescrizioneIntervento() {
        return descrizione_intervento;
    }


    /**
     * Sets the descrizione_intervento value for this DatiCUP.
     *
     * @param descrizione_intervento
     */
    public void setDescrizioneIntervento(java.lang.String descrizione_intervento) {
        this.descrizione_intervento = descrizione_intervento;
    }


    /**
     * Gets the anno_decisione value for this DatiCUP.
     *
     * @return anno_decisione
     */
    public Integer getAnno_decisione() {
        return anno_decisione;
    }


    /**
     * Sets the anno_decisione value for this DatiCUP.
     *
     * @param anno_decisione
     */
    public void setAnno_decisione(Integer anno_decisione) {
        this.anno_decisione = anno_decisione;
    }

    /**
     * Gets the indAreaRifer value for this DatiCUP.
     *
     * @return indAreaRifer
     */
    public java.lang.String getIndAreaRifer() {
        return indAreaRifer;
    }


    /**
     * Sets the indAreaRifer value for this DatiCUP.
     *
     * @param indAreaRifer
     */
    public void setIndAreaRifer(java.lang.String indAreaRifer) {
        this.indAreaRifer = indAreaRifer;
    }


    /**
     * Gets the strum_progr value for this DatiCUP.
     *
     * @return strum_progr
     */
    public java.lang.String getStrumProgr() {
        return strum_progr;
    }

    /**
     * Sets the strum_progr value for this DatiCUP.
     *
     * @param strum_progr
     */
    public void setStrumProgr(java.lang.String strum_progr) {
        this.strum_progr = strum_progr;
    }

    /**
     * Gets the desc_strum_progr value for this DatiCUP.
     *
     * @return desc_strum_progr
     */
    public java.lang.String getDescStrumProgr() {
        return desc_strum_progr;
    }

    /**
     * Sets the desc_strum_progr value for this DatiCUP.
     *
     * @param desc_strum_progr
     */
    public void setDescStrumProgr(java.lang.String desc_strum_progr) {
        this.desc_strum_progr = desc_strum_progr;
    }


    /**
     * Gets the altre_informazioni value for this DatiCUP.
     *
     * @return altre_informazioni
     */
    public java.lang.String getAltreInformazioni() {
        return altre_informazioni;
    }


    /**
     * Sets the altre_informazioni value for this DatiCUP.
     *
     * @param altre_informazioni
     */
    public void setAltreInformazioni(java.lang.String altre_informazioni) {
        this.altre_informazioni = altre_informazioni;
    }
    /**
     * Gets the data_definitivo value for this DatiCUP.
     *
     * @return data_definitivo
     */
    public Date getDataDefinitivo() {
        return data_definitivo;
    }


    /**
     * Sets the data_definitivo value for this DatiCUP.
     *
     * @param data_definitivo
     */
    public void setDataDefinitivo(Date data_definitivo) {
        this.data_definitivo = data_definitivo;
    }

    /**
     * Gets the natura value for this DatiCUP.
     *
     * @return natura
     */
    public java.lang.String getNatura() {
        return natura;
    }


    /**
     * Sets the natura value for this DatiCUP.
     *
     * @param natura
     */
    public void setNatura(java.lang.String natura) {
        this.natura = natura;
    }
    /**
     * Gets the tipologia value for this DatiCUP.
     *
     * @return tipologia
     */
    public java.lang.String getTipologia() {
        return tipologia;
    }


    /**
     * Sets the tipologia value for this DatiCUP.
     *
     * @param tipologia
     */
    public void setTipologia(java.lang.String tipologia) {
        this.tipologia = tipologia;
    }


    /**
     * Gets the settore value for this DatiCUP.
     *
     * @return settore
     */
    public java.lang.String getSettore() {
        return settore;
    }


    /**
     * Sets the settore value for this DatiCUP.
     *
     * @param settore
     */
    public void setSettore(java.lang.String settore) {
        this.settore = settore;
    }


    /**
     * Gets the sottosettore value for this DatiCUP.
     *
     * @return sottosettore
     */
    public java.lang.String getSottosettore() {
        return sottosettore;
    }


    /**
     * Sets the sottosettore value for this DatiCUP.
     *
     * @param sottosettore
     */
    public void setSottosettore(java.lang.String sottosettore) {
        this.sottosettore = sottosettore;
    }


    /**
     * Gets the categoria value for this DatiCUP.
     *
     * @return categoria
     */
    public java.lang.String getCategoria() {
        return categoria;
    }


    /**
     * Sets the categoria value for this DatiCUP.
     *
     * @param categoria
     */
    public void setCategoria(java.lang.String categoria) {
        this.categoria = categoria;
    }

    /**
     * Gets the cumulativo value for this DatiCUP.
     *
     * @return cumulativo
     */
    public java.lang.String getCumulativo() {
        return cumulativo;
    }


    /**
     * Sets the cumulativo value for this DatiCUP.
     *
     * @param cumulativo
     */
    public void setCumulativo(java.lang.String cumulativo) {
        this.cumulativo = cumulativo;
    }

    /**
     * Gets the beneficiario value for this DatiCUP.
     *
     * @return beneficiario
     */
    public java.lang.String getBeneficiario() {
        return beneficiario;
    }


    /**
     * Sets the beneficiario value for this DatiCUP.
     *
     * @param beneficiario
     */
    public void setBeneficiario(java.lang.String beneficiario) {
        this.beneficiario = beneficiario;
    }

    /**
     * Gets the nomeStrInfrastr value for this DatiCUP.
     *
     * @return nomeStrInfrastr
     */
    public java.lang.String getNomeStrInfrastr() {
        return nomeStrInfrastr;
    }


    /**
     * Sets the nomeStrInfrastr value for this DatiCUP.
     *
     * @param nomeStrInfrastr
     */
    public void setNomeStrInfrastr(java.lang.String nomeStrInfrastr) {
        this.nomeStrInfrastr = nomeStrInfrastr;
    }

    /**
     * Gets the strInfrastrUnica value for this DatiCUP.
     *
     * @return strInfrastrUnica
     */
    public java.lang.String getStrInfrastrUnica() {
        return strInfrastrUnica;
    }


    /**
     * Sets the strInfrastrUnica value for this DatiCUP.
     *
     * @param strInfrastrUnica
     */
    public void setStrInfrastrUnica(java.lang.String strInfrastrUnica) {
        this.strInfrastrUnica = strInfrastrUnica;
    }

    /**
     * Gets the denomImprStab     value for this DatiCUP.
     *
     * @return denomImprStab
     */
    public java.lang.String getDenomImprStab() {
        return denomImprStab;
    }


    /**
     * Sets the denomImprStab value for this DatiCUP.
     *
     * @param denomImprStab
     */
    public void setDenomImprStab(java.lang.String denomImprStab) {
        this.denomImprStab = denomImprStab;
    }

    /**
     * Gets the denomImprStabPrec     value for this DatiCUP.
     *
     * @return denomImprStabPrec
     */
    public java.lang.String getDenomImprStabPrec() {
        return denomImprStabPrec;
    }


    /**
     * Sets the denomImprStabPrec value for this DatiCUP.
     *
     * @param denomImprStabPrec
     */
    public void setDenomImprStabPrec(java.lang.String denomImprStabPrec) {
        this.denomImprStabPrec = denomImprStabPrec;
    }

    /**
     * Gets the ragioneSociale     value for this DatiCUP.
     *
     * @return ragioneSociale
     */
    public java.lang.String getRagioneSociale() {
        return ragioneSociale;
    }


    /**
     * Sets the ragioneSociale value for this DatiCUP.
     *
     * @param ragioneSociale
     */
    public void setRagioneSociale(java.lang.String ragioneSociale) {
        this.ragioneSociale = ragioneSociale;
    }

    /**
     * Gets the ragioneSocialePrec     value for this DatiCUP.
     *
     * @return ragioneSocialePrec
     */
    public java.lang.String getRagioneSocialePrec() {
        return ragioneSocialePrec;
    }


    /**
     * Sets the ragioneSocialePrec value for this DatiCUP.
     *
     * @param ragioneSocialePrec
     */
    public void setRagioneSocialePrec(java.lang.String ragioneSocialePrec) {
        this.ragioneSocialePrec = ragioneSocialePrec;
    }

    /**
     * Gets the partitaIVA value for this DatiCUP.
     *
     * @return partitaIVA
     */
    public java.lang.String getPartitaIVA() {
        return partitaIVA;
    }


    /**
     * Sets the partitaIVA value for this DatiCUP.
     *
     * @param partitaIVA
     */
    public void setPartitaIVA(java.lang.String partitaIVA) {
        this.partitaIVA = partitaIVA;
    }


    /**
     * Gets the denominazioneProgetto value for this DatiCUP.
     *
     * @return denominazioneProgetto
     */
    public java.lang.String getDenominazioneProgetto() {
        return denominazioneProgetto;
    }


    /**
     * Sets the denominazioneProgetto value for this DatiCUP.
     *
     * @param denominazioneProgetto
     */
    public void setDenominazioneProgetto(java.lang.String denominazioneProgetto) {
        this.denominazioneProgetto = denominazioneProgetto;
    }

    /**
     * Gets the ente value for this DatiCUP.
     *
     * @return ente
     */
    public java.lang.String getEnte() {
        return ente;
    }


    /**
     * Sets the ente value for this DatiCUP.
     *
     * @param ente
     */
    public void setEnte(java.lang.String ente) {
        this.ente = ente;
    }

    /**
     * Gets the attoAmministrativo value for this DatiCUP.
     *
     * @return attoAmministrativo
     */
    public java.lang.String getAttoAmministrativo() {
        return attoAmministrativo;
    }


    /**
     * Sets the attoAmministrativo value for this DatiCUP.
     *
     * @param attoAmministrativo
     */
    public void setAttoAmministrativo(java.lang.String attoAmministrativo) {
        this.attoAmministrativo = attoAmministrativo;
    }

    /**
     * Gets the scopoIntervento value for this DatiCUP.
     *
     * @return scopoIntervento
     */
    public java.lang.String getScopoIntervento() {
        return scopoIntervento;
    }


    /**
     * Sets the scopoIntervento value for this DatiCUP.
     *
     * @param scopoIntervento
     */
    public void setScopoIntervento(java.lang.String scopoIntervento) {
        this.scopoIntervento = scopoIntervento;
    }


    /**
     * Gets the tipoIndAreaRifer value for this DatiCUP.
     *
     * @return tipoIndAreaRifer
     */
    public java.lang.String getTipoIndAreaRifer() {
        return tipoIndAreaRifer;
    }


    /**
     * Sets the tipoIndAreaRifer value for this DatiCUP.
     *
     * @param tipoIndAreaRifer
     */
    public void setTipoIndAreaRifer(java.lang.String tipoIndAreaRifer) {
        this.tipoIndAreaRifer = tipoIndAreaRifer;
    }

    /**
     * Gets the obiettivoCorso value for this DatiCUP.
     *
     * @return obiettivoCorso
     */
    public java.lang.String getObiettivoCorso() {
        return obiettivoCorso;
    }


    /**
     * Sets the obiettivoCorso value for this DatiCUP.
     *
     * @param obiettivoCorso
     */
    public void setObiettivoCorso(java.lang.String obiettivoCorso) {
        this.obiettivoCorso = obiettivoCorso;
    }

    /**
     * Gets the servizio value for this DatiCUP.
     *
     * @return servizio
     */
    public java.lang.String getServizio() {
        return servizio;
    }


    /**
     * Sets the servizio value for this DatiCUP.
     *
     * @param servizio
     */
    public void setServizio(java.lang.String servizio) {
        this.servizio = servizio;
    }

    /**
     * Gets the bene value for this DatiCUP.
     *
     * @return bene
     */
    public java.lang.String getBene() {
        return bene;
    }


    /**
     * Sets the bene value for this DatiCUP.
     *
     * @param bene
     */
    public void setBene(java.lang.String bene) {
        this.bene = bene;
    }


    /**
     * Gets the cpv value for this DatiCUP.
     *
     * @return cpv
     */
    public java.lang.String getCpv() {
        return cpv;
    }


    /**
     * Sets the cpv value for this DatiCUP.
     *
     * @param cpv
     */
    public void setCpv(java.lang.String cpv) {
        this.cpv = cpv;
    }

    /**
     * Gets the modInterventoFrequenza value for this DatiCUP.
     *
     * @return modInterventoFrequenza
     */
    public java.lang.String getModInterventoFrequenza() {
        return modInterventoFrequenza;
    }


    /**
     * Sets the modInterventoFrequenza value for this DatiCUP.
     *
     * @param modInterventoFrequenza
     */
    public void setModInterventoFrequenza(java.lang.String modInterventoFrequenza) {
        this.modInterventoFrequenza = modInterventoFrequenza;
    }


    /**
     * Gets the cup_master value for this DatiCUP.
     *
     * @return cup_master
     */
    public java.lang.String getCup_master() {
        return cup_master;
    }


    /**
     * Sets the ragioni_collegamento value for this DatiCUP.
     *
     * @param ragioni_collegamento
     */
    public void setRagioni_collegamento(java.lang.String cup_master) {
        this.ragioni_collegamento = ragioni_collegamento;
    }

    /**
     * Gets the ragioni_collegamento value for this DatiCUP.
     *
     * @return ragioni_collegamento
     */
    public java.lang.String getRagioni_collegamento() {
        return ragioni_collegamento;
    }


    /**
     * Sets the cup_master value for this DatiCUP.
     *
     * @param cup_master
     */
    public void setCup_master(java.lang.String cup_master) {
        this.cup_master = cup_master;
    }

    /**
     * Gets the sponsorizzazione value for this DatiCUP.
     *
     * @return sponsorizzazione
     */
    public java.lang.String getSponsorizzazione() {
        return sponsorizzazione;
    }


    /**
     * Sets the sponsorizzazione value for this DatiCUP.
     *
     * @param sponsorizzazione
     */
    public void setSponsorizzazione(java.lang.String sponsorizzazione) {
        this.sponsorizzazione = sponsorizzazione;
    }


    /**
     * Gets the finanza_progetto value for this DatiCUP.
     *
     * @return finanza_progetto
     */
    public java.lang.String getFinanza_progetto() {
        return finanza_progetto;
    }


    /**
     * Sets the finanza_progetto value for this DatiCUP.
     *
     * @param finanza_progetto
     */
    public void setFinanza_progetto(java.lang.String finanza_progetto) {
        this.finanza_progetto = finanza_progetto;
    }


    /**
     * Gets the costo value for this DatiCUP.
     *
     * @return costo
     */
    public java.lang.Double getCosto() {
        return costo;
    }


    /**
     * Sets the costo value for this DatiCUP.
     *
     * @param costo
     */
    public void setCosto(java.lang.Double costo) {
        this.costo = costo;
    }


    /**
     * Gets the finanziamento value for this DatiCUP.
     *
     * @return finanziamento
     */
    public java.lang.Double getFinanziamento() {
        return finanziamento;
    }


    /**
     * Sets the finanziamento value for this DatiCUP.
     *
     * @param finanziamento
     */
    public void setFinanziamento(java.lang.Double finanziamento) {
        this.finanziamento = finanziamento;
    }

    /**
     * Gets the codTipoCopFin value for this DettaglioCUP.
     *
     * @return codTipoCopFin
     */

    public java.lang.String[] getCodTipoCopFin() {
      return codTipoCopFin;
    }



    /**
     * Sets the codTipoCopFin value for this DatiCUP.
     *
     * @param codTipoCopFin
     */
    public void setCodTipoCopFin(java.lang.String[] codTipoCopFin) {
      this.codTipoCopFin = codTipoCopFin;
  }

    /**
     * Gets the Localizzazioni value for this DettaglioCUP.
     *
     * @return localizzazioni
     */

    public LocalizzazioneCUP[] getLocalizzazioni() {
      return localizzazioni;
    }



    /**
     * Sets the localizzazioni value for this DatiCUP.
     *
     * @param localizzazioni
     */
    public void setLocalizzazioni(LocalizzazioneCUP[] localizzazioni) {
      this.localizzazioni = localizzazioni;
  }

}
