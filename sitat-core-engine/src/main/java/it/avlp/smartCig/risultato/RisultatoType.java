/**
 * RisultatoType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.avlp.smartCig.risultato;

public class RisultatoType  implements java.io.Serializable {
    private CodiceRisultatoType codice;

    private DescrizioneRisultatoType descrizione;

    private java.lang.String idTransazione;

    public RisultatoType() {
    }

    public RisultatoType(
           CodiceRisultatoType codice,
           DescrizioneRisultatoType descrizione,
           java.lang.String idTransazione) {
           this.codice = codice;
           this.descrizione = descrizione;
           this.idTransazione = idTransazione;
    }


    /**
     * Gets the codice value for this RisultatoType.
     * 
     * @return codice
     */
    public CodiceRisultatoType getCodice() {
        return codice;
    }


    /**
     * Sets the codice value for this RisultatoType.
     * 
     * @param codice
     */
    public void setCodice(CodiceRisultatoType codice) {
        this.codice = codice;
    }


    /**
     * Gets the descrizione value for this RisultatoType.
     * 
     * @return descrizione
     */
    public DescrizioneRisultatoType getDescrizione() {
        return descrizione;
    }


    /**
     * Sets the descrizione value for this RisultatoType.
     * 
     * @param descrizione
     */
    public void setDescrizione(DescrizioneRisultatoType descrizione) {
        this.descrizione = descrizione;
    }


    /**
     * Gets the idTransazione value for this RisultatoType.
     * 
     * @return idTransazione
     */
    public java.lang.String getIdTransazione() {
        return idTransazione;
    }


    /**
     * Sets the idTransazione value for this RisultatoType.
     * 
     * @param idTransazione
     */
    public void setIdTransazione(java.lang.String idTransazione) {
        this.idTransazione = idTransazione;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RisultatoType)) return false;
        RisultatoType other = (RisultatoType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.codice==null && other.getCodice()==null) || 
             (this.codice!=null &&
              this.codice.equals(other.getCodice()))) &&
            ((this.descrizione==null && other.getDescrizione()==null) || 
             (this.descrizione!=null &&
              this.descrizione.equals(other.getDescrizione()))) &&
            ((this.idTransazione==null && other.getIdTransazione()==null) || 
             (this.idTransazione!=null &&
              this.idTransazione.equals(other.getIdTransazione())));
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
        if (getCodice() != null) {
            _hashCode += getCodice().hashCode();
        }
        if (getDescrizione() != null) {
            _hashCode += getDescrizione().hashCode();
        }
        if (getIdTransazione() != null) {
            _hashCode += getIdTransazione().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RisultatoType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("smartCig.risultato", "RisultatoType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codice");
        elemField.setXmlName(new javax.xml.namespace.QName("smartCig.risultato", "codice"));
        elemField.setXmlType(new javax.xml.namespace.QName("smartCig.risultato", "CodiceRisultatoType"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descrizione");
        elemField.setXmlName(new javax.xml.namespace.QName("smartCig.risultato", "descrizione"));
        elemField.setXmlType(new javax.xml.namespace.QName("smartCig.risultato", "DescrizioneRisultatoType"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idTransazione");
        elemField.setXmlName(new javax.xml.namespace.QName("smartCig.risultato", "idTransazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
