/**
 * Risposta_RichiestaRispostaSincrona_RisultatoDettaglioCUP_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.mef.serviziCUP.types;

public class Risposta_RichiestaRispostaSincrona_RisultatoDettaglioCUP_Type  implements java.io.Serializable {
    private java.lang.String titoloRisposta;

    private it.mef.serviziCUP.types.EsitoElaborazioneCUP_Type esitoElaborazione;

    private byte[] risposta;

    public Risposta_RichiestaRispostaSincrona_RisultatoDettaglioCUP_Type() {
    }

    public Risposta_RichiestaRispostaSincrona_RisultatoDettaglioCUP_Type(
           java.lang.String titoloRisposta,
           it.mef.serviziCUP.types.EsitoElaborazioneCUP_Type esitoElaborazione,
           byte[] risposta) {
           this.titoloRisposta = titoloRisposta;
           this.esitoElaborazione = esitoElaborazione;
           this.risposta = risposta;
    }


    /**
     * Gets the titoloRisposta value for this Risposta_RichiestaRispostaSincrona_RisultatoDettaglioCUP_Type.
     * 
     * @return titoloRisposta
     */
    public java.lang.String getTitoloRisposta() {
        return titoloRisposta;
    }


    /**
     * Sets the titoloRisposta value for this Risposta_RichiestaRispostaSincrona_RisultatoDettaglioCUP_Type.
     * 
     * @param titoloRisposta
     */
    public void setTitoloRisposta(java.lang.String titoloRisposta) {
        this.titoloRisposta = titoloRisposta;
    }


    /**
     * Gets the esitoElaborazione value for this Risposta_RichiestaRispostaSincrona_RisultatoDettaglioCUP_Type.
     * 
     * @return esitoElaborazione
     */
    public it.mef.serviziCUP.types.EsitoElaborazioneCUP_Type getEsitoElaborazione() {
        return esitoElaborazione;
    }


    /**
     * Sets the esitoElaborazione value for this Risposta_RichiestaRispostaSincrona_RisultatoDettaglioCUP_Type.
     * 
     * @param esitoElaborazione
     */
    public void setEsitoElaborazione(it.mef.serviziCUP.types.EsitoElaborazioneCUP_Type esitoElaborazione) {
        this.esitoElaborazione = esitoElaborazione;
    }


    /**
     * Gets the risposta value for this Risposta_RichiestaRispostaSincrona_RisultatoDettaglioCUP_Type.
     * 
     * @return risposta
     */
    public byte[] getRisposta() {
        return risposta;
    }


    /**
     * Sets the risposta value for this Risposta_RichiestaRispostaSincrona_RisultatoDettaglioCUP_Type.
     * 
     * @param risposta
     */
    public void setRisposta(byte[] risposta) {
        this.risposta = risposta;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Risposta_RichiestaRispostaSincrona_RisultatoDettaglioCUP_Type)) return false;
        Risposta_RichiestaRispostaSincrona_RisultatoDettaglioCUP_Type other = (Risposta_RichiestaRispostaSincrona_RisultatoDettaglioCUP_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.titoloRisposta==null && other.getTitoloRisposta()==null) || 
             (this.titoloRisposta!=null &&
              this.titoloRisposta.equals(other.getTitoloRisposta()))) &&
            ((this.esitoElaborazione==null && other.getEsitoElaborazione()==null) || 
             (this.esitoElaborazione!=null &&
              this.esitoElaborazione.equals(other.getEsitoElaborazione()))) &&
            ((this.risposta==null && other.getRisposta()==null) || 
             (this.risposta!=null &&
              java.util.Arrays.equals(this.risposta, other.getRisposta())));
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
        if (getTitoloRisposta() != null) {
            _hashCode += getTitoloRisposta().hashCode();
        }
        if (getEsitoElaborazione() != null) {
            _hashCode += getEsitoElaborazione().hashCode();
        }
        if (getRisposta() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getRisposta());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getRisposta(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Risposta_RichiestaRispostaSincrona_RisultatoDettaglioCUP_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://serviziCUP.mef.it/types/", "risposta_RichiestaRispostaSincrona_RisultatoDettaglioCUP_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("titoloRisposta");
        elemField.setXmlName(new javax.xml.namespace.QName("", "TitoloRisposta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("esitoElaborazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "EsitoElaborazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://serviziCUP.mef.it/types/", "esitoElaborazioneCUP_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("risposta");
        elemField.setXmlName(new javax.xml.namespace.QName("", "risposta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "base64Binary"));
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
