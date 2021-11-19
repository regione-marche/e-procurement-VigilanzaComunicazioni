/**
 * ConsultaComunicazioneResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.avlp.smartCig.ws;

import it.avlp.smartCig.comunicazione.ComunicazioneType;
import it.avlp.smartCig.risultato.RisultatoType;

public class ConsultaComunicazioneResponse  implements java.io.Serializable {
    private ComunicazioneType comunicazione;

    private RisultatoType codiceRisultato;

    public ConsultaComunicazioneResponse() {
    }

    public ConsultaComunicazioneResponse(
           ComunicazioneType comunicazione,
           RisultatoType codiceRisultato) {
           this.comunicazione = comunicazione;
           this.codiceRisultato = codiceRisultato;
    }


    /**
     * Gets the comunicazione value for this ConsultaComunicazioneResponse.
     * 
     * @return comunicazione
     */
    public ComunicazioneType getComunicazione() {
        return comunicazione;
    }


    /**
     * Sets the comunicazione value for this ConsultaComunicazioneResponse.
     * 
     * @param comunicazione
     */
    public void setComunicazione(ComunicazioneType comunicazione) {
        this.comunicazione = comunicazione;
    }


    /**
     * Gets the codiceRisultato value for this ConsultaComunicazioneResponse.
     * 
     * @return codiceRisultato
     */
    public RisultatoType getCodiceRisultato() {
        return codiceRisultato;
    }


    /**
     * Sets the codiceRisultato value for this ConsultaComunicazioneResponse.
     * 
     * @param codiceRisultato
     */
    public void setCodiceRisultato(RisultatoType codiceRisultato) {
        this.codiceRisultato = codiceRisultato;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ConsultaComunicazioneResponse)) return false;
        ConsultaComunicazioneResponse other = (ConsultaComunicazioneResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.comunicazione==null && other.getComunicazione()==null) || 
             (this.comunicazione!=null &&
              this.comunicazione.equals(other.getComunicazione()))) &&
            ((this.codiceRisultato==null && other.getCodiceRisultato()==null) || 
             (this.codiceRisultato!=null &&
              this.codiceRisultato.equals(other.getCodiceRisultato())));
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
        if (getComunicazione() != null) {
            _hashCode += getComunicazione().hashCode();
        }
        if (getCodiceRisultato() != null) {
            _hashCode += getCodiceRisultato().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ConsultaComunicazioneResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("smartCig.ws.comunicazioni_v1.0.0", "ConsultaComunicazioneResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("comunicazione");
        elemField.setXmlName(new javax.xml.namespace.QName("smartCig.ws.comunicazioni_v1.0.0", "comunicazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("smartCig.comunicazione", "ComunicazioneType"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceRisultato");
        elemField.setXmlName(new javax.xml.namespace.QName("smartCig.ws.comunicazioni_v1.0.0", "codiceRisultato"));
        elemField.setXmlType(new javax.xml.namespace.QName("smartCig.risultato", "RisultatoType"));
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
