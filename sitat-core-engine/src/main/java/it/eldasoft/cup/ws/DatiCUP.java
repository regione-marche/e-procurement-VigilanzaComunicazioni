/**
 * DatiCUP.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.eldasoft.cup.ws;

public class DatiCUP  implements java.io.Serializable {
    private java.lang.String uuid;

    private java.lang.String descrizione_intervento;

    private java.math.BigInteger anno_decisione;

    private it.eldasoft.cup.ws.NaturaType natura;

    private it.eldasoft.cup.ws.TipologiaType tipologia;

    private it.eldasoft.cup.ws.SettoreType settore;

    private it.eldasoft.cup.ws.SottosettoreType sottosettore;

    private it.eldasoft.cup.ws.CategoriaType categoria;

    private java.lang.String cpv;

    private java.lang.String cup_master;

    private it.eldasoft.cup.ws.SponsorizzazioneType sponsorizzazione;

    private it.eldasoft.cup.ws.Finanza_progettoType finanza_progetto;

    private java.lang.Double costo;

    private java.lang.Double finanziamento;

    public DatiCUP() {
    }

    public DatiCUP(
           java.lang.String uuid,
           java.lang.String descrizione_intervento,
           java.math.BigInteger anno_decisione,
           it.eldasoft.cup.ws.NaturaType natura,
           it.eldasoft.cup.ws.TipologiaType tipologia,
           it.eldasoft.cup.ws.SettoreType settore,
           it.eldasoft.cup.ws.SottosettoreType sottosettore,
           it.eldasoft.cup.ws.CategoriaType categoria,
           java.lang.String cpv,
           java.lang.String cup_master,
           it.eldasoft.cup.ws.SponsorizzazioneType sponsorizzazione,
           it.eldasoft.cup.ws.Finanza_progettoType finanza_progetto,
           java.lang.Double costo,
           java.lang.Double finanziamento) {
           this.uuid = uuid;
           this.descrizione_intervento = descrizione_intervento;
           this.anno_decisione = anno_decisione;
           this.natura = natura;
           this.tipologia = tipologia;
           this.settore = settore;
           this.sottosettore = sottosettore;
           this.categoria = categoria;
           this.cpv = cpv;
           this.cup_master = cup_master;
           this.sponsorizzazione = sponsorizzazione;
           this.finanza_progetto = finanza_progetto;
           this.costo = costo;
           this.finanziamento = finanziamento;
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
    public java.lang.String getDescrizione_intervento() {
        return descrizione_intervento;
    }


    /**
     * Sets the descrizione_intervento value for this DatiCUP.
     * 
     * @param descrizione_intervento
     */
    public void setDescrizione_intervento(java.lang.String descrizione_intervento) {
        this.descrizione_intervento = descrizione_intervento;
    }


    /**
     * Gets the anno_decisione value for this DatiCUP.
     * 
     * @return anno_decisione
     */
    public java.math.BigInteger getAnno_decisione() {
        return anno_decisione;
    }


    /**
     * Sets the anno_decisione value for this DatiCUP.
     * 
     * @param anno_decisione
     */
    public void setAnno_decisione(java.math.BigInteger anno_decisione) {
        this.anno_decisione = anno_decisione;
    }


    /**
     * Gets the natura value for this DatiCUP.
     * 
     * @return natura
     */
    public it.eldasoft.cup.ws.NaturaType getNatura() {
        return natura;
    }


    /**
     * Sets the natura value for this DatiCUP.
     * 
     * @param natura
     */
    public void setNatura(it.eldasoft.cup.ws.NaturaType natura) {
        this.natura = natura;
    }


    /**
     * Gets the tipologia value for this DatiCUP.
     * 
     * @return tipologia
     */
    public it.eldasoft.cup.ws.TipologiaType getTipologia() {
        return tipologia;
    }


    /**
     * Sets the tipologia value for this DatiCUP.
     * 
     * @param tipologia
     */
    public void setTipologia(it.eldasoft.cup.ws.TipologiaType tipologia) {
        this.tipologia = tipologia;
    }


    /**
     * Gets the settore value for this DatiCUP.
     * 
     * @return settore
     */
    public it.eldasoft.cup.ws.SettoreType getSettore() {
        return settore;
    }


    /**
     * Sets the settore value for this DatiCUP.
     * 
     * @param settore
     */
    public void setSettore(it.eldasoft.cup.ws.SettoreType settore) {
        this.settore = settore;
    }


    /**
     * Gets the sottosettore value for this DatiCUP.
     * 
     * @return sottosettore
     */
    public it.eldasoft.cup.ws.SottosettoreType getSottosettore() {
        return sottosettore;
    }


    /**
     * Sets the sottosettore value for this DatiCUP.
     * 
     * @param sottosettore
     */
    public void setSottosettore(it.eldasoft.cup.ws.SottosettoreType sottosettore) {
        this.sottosettore = sottosettore;
    }


    /**
     * Gets the categoria value for this DatiCUP.
     * 
     * @return categoria
     */
    public it.eldasoft.cup.ws.CategoriaType getCategoria() {
        return categoria;
    }


    /**
     * Sets the categoria value for this DatiCUP.
     * 
     * @param categoria
     */
    public void setCategoria(it.eldasoft.cup.ws.CategoriaType categoria) {
        this.categoria = categoria;
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
     * Gets the cup_master value for this DatiCUP.
     * 
     * @return cup_master
     */
    public java.lang.String getCup_master() {
        return cup_master;
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
    public it.eldasoft.cup.ws.SponsorizzazioneType getSponsorizzazione() {
        return sponsorizzazione;
    }


    /**
     * Sets the sponsorizzazione value for this DatiCUP.
     * 
     * @param sponsorizzazione
     */
    public void setSponsorizzazione(it.eldasoft.cup.ws.SponsorizzazioneType sponsorizzazione) {
        this.sponsorizzazione = sponsorizzazione;
    }


    /**
     * Gets the finanza_progetto value for this DatiCUP.
     * 
     * @return finanza_progetto
     */
    public it.eldasoft.cup.ws.Finanza_progettoType getFinanza_progetto() {
        return finanza_progetto;
    }


    /**
     * Sets the finanza_progetto value for this DatiCUP.
     * 
     * @param finanza_progetto
     */
    public void setFinanza_progetto(it.eldasoft.cup.ws.Finanza_progettoType finanza_progetto) {
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

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DatiCUP)) return false;
        DatiCUP other = (DatiCUP) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.uuid==null && other.getUuid()==null) || 
             (this.uuid!=null &&
              this.uuid.equals(other.getUuid()))) &&
            ((this.descrizione_intervento==null && other.getDescrizione_intervento()==null) || 
             (this.descrizione_intervento!=null &&
              this.descrizione_intervento.equals(other.getDescrizione_intervento()))) &&
            ((this.anno_decisione==null && other.getAnno_decisione()==null) || 
             (this.anno_decisione!=null &&
              this.anno_decisione.equals(other.getAnno_decisione()))) &&
            ((this.natura==null && other.getNatura()==null) || 
             (this.natura!=null &&
              this.natura.equals(other.getNatura()))) &&
            ((this.tipologia==null && other.getTipologia()==null) || 
             (this.tipologia!=null &&
              this.tipologia.equals(other.getTipologia()))) &&
            ((this.settore==null && other.getSettore()==null) || 
             (this.settore!=null &&
              this.settore.equals(other.getSettore()))) &&
            ((this.sottosettore==null && other.getSottosettore()==null) || 
             (this.sottosettore!=null &&
              this.sottosettore.equals(other.getSottosettore()))) &&
            ((this.categoria==null && other.getCategoria()==null) || 
             (this.categoria!=null &&
              this.categoria.equals(other.getCategoria()))) &&
            ((this.cpv==null && other.getCpv()==null) || 
             (this.cpv!=null &&
              this.cpv.equals(other.getCpv()))) &&
            ((this.cup_master==null && other.getCup_master()==null) || 
             (this.cup_master!=null &&
              this.cup_master.equals(other.getCup_master()))) &&
            ((this.sponsorizzazione==null && other.getSponsorizzazione()==null) || 
             (this.sponsorizzazione!=null &&
              this.sponsorizzazione.equals(other.getSponsorizzazione()))) &&
            ((this.finanza_progetto==null && other.getFinanza_progetto()==null) || 
             (this.finanza_progetto!=null &&
              this.finanza_progetto.equals(other.getFinanza_progetto()))) &&
            ((this.costo==null && other.getCosto()==null) || 
             (this.costo!=null &&
              this.costo.equals(other.getCosto()))) &&
            ((this.finanziamento==null && other.getFinanziamento()==null) || 
             (this.finanziamento!=null &&
              this.finanziamento.equals(other.getFinanziamento())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getUuid() != null) {
            _hashCode += getUuid().hashCode();
        }
        if (getDescrizione_intervento() != null) {
            _hashCode += getDescrizione_intervento().hashCode();
        }
        if (getAnno_decisione() != null) {
            _hashCode += getAnno_decisione().hashCode();
        }
        if (getNatura() != null) {
            _hashCode += getNatura().hashCode();
        }
        if (getTipologia() != null) {
            _hashCode += getTipologia().hashCode();
        }
        if (getSettore() != null) {
            _hashCode += getSettore().hashCode();
        }
        if (getSottosettore() != null) {
            _hashCode += getSottosettore().hashCode();
        }
        if (getCategoria() != null) {
            _hashCode += getCategoria().hashCode();
        }
        if (getCpv() != null) {
            _hashCode += getCpv().hashCode();
        }
        if (getCup_master() != null) {
            _hashCode += getCup_master().hashCode();
        }
        if (getSponsorizzazione() != null) {
            _hashCode += getSponsorizzazione().hashCode();
        }
        if (getFinanza_progetto() != null) {
            _hashCode += getFinanza_progetto().hashCode();
        }
        if (getCosto() != null) {
            _hashCode += getCosto().hashCode();
        }
        if (getFinanziamento() != null) {
            _hashCode += getFinanziamento().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DatiCUP.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.cup.eldasoft.it/", "datiCUP"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("uuid");
        elemField.setXmlName(new javax.xml.namespace.QName("", "uuid"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descrizione_intervento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descrizione_intervento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("anno_decisione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "anno_decisione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("natura");
        elemField.setXmlName(new javax.xml.namespace.QName("", "natura"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.cup.eldasoft.it/", "naturaType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipologia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipologia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.cup.eldasoft.it/", "tipologiaType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("settore");
        elemField.setXmlName(new javax.xml.namespace.QName("", "settore"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.cup.eldasoft.it/", "settoreType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sottosettore");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sottosettore"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.cup.eldasoft.it/", "sottosettoreType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("categoria");
        elemField.setXmlName(new javax.xml.namespace.QName("", "categoria"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.cup.eldasoft.it/", "categoriaType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cpv");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cpv"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cup_master");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cup_master"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sponsorizzazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sponsorizzazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.cup.eldasoft.it/", "sponsorizzazioneType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("finanza_progetto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "finanza_progetto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.cup.eldasoft.it/", "finanza_progettoType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("costo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "costo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("finanziamento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "finanziamento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
