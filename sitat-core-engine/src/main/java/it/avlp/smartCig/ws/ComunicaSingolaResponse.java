/**
 * ComunicaSingolaResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.avlp.smartCig.ws;

import it.avlp.smartCig.errore.ErroreType;
import it.avlp.smartCig.risultato.RisultatoType;

public class ComunicaSingolaResponse  implements java.io.Serializable {
    private java.lang.String cig;

    private RisultatoType codiceRisultato;

    private ErroreType[] errore;

    public ComunicaSingolaResponse() {
    }

    public ComunicaSingolaResponse(
           java.lang.String cig,
           RisultatoType codiceRisultato,
           ErroreType[] errore) {
           this.cig = cig;
           this.codiceRisultato = codiceRisultato;
           this.errore = errore;
    }


    /**
     * Gets the cig value for this ComunicaSingolaResponse.
     * 
     * @return cig
     */
    public java.lang.String getCig() {
        return cig;
    }


    /**
     * Sets the cig value for this ComunicaSingolaResponse.
     * 
     * @param cig
     */
    public void setCig(java.lang.String cig) {
        this.cig = cig;
    }


    /**
     * Gets the codiceRisultato value for this ComunicaSingolaResponse.
     * 
     * @return codiceRisultato
     */
    public RisultatoType getCodiceRisultato() {
        return codiceRisultato;
    }


    /**
     * Sets the codiceRisultato value for this ComunicaSingolaResponse.
     * 
     * @param codiceRisultato
     */
    public void setCodiceRisultato(RisultatoType codiceRisultato) {
        this.codiceRisultato = codiceRisultato;
    }


    /**
     * Gets the errore value for this ComunicaSingolaResponse.
     * 
     * @return errore
     */
    public ErroreType[] getErrore() {
        return errore;
    }


    /**
     * Sets the errore value for this ComunicaSingolaResponse.
     * 
     * @param errore
     */
    public void setErrore(ErroreType[] errore) {
        this.errore = errore;
    }

    public ErroreType getErrore(int i) {
        return this.errore[i];
    }

    public void setErrore(int i, ErroreType _value) {
        this.errore[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ComunicaSingolaResponse)) return false;
        ComunicaSingolaResponse other = (ComunicaSingolaResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.cig==null && other.getCig()==null) || 
             (this.cig!=null &&
              this.cig.equals(other.getCig()))) &&
            ((this.codiceRisultato==null && other.getCodiceRisultato()==null) || 
             (this.codiceRisultato!=null &&
              this.codiceRisultato.equals(other.getCodiceRisultato()))) &&
            ((this.errore==null && other.getErrore()==null) || 
             (this.errore!=null &&
              java.util.Arrays.equals(this.errore, other.getErrore())));
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
        if (getCig() != null) {
            _hashCode += getCig().hashCode();
        }
        if (getCodiceRisultato() != null) {
            _hashCode += getCodiceRisultato().hashCode();
        }
        if (getErrore() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getErrore());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getErrore(), i);
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
        new org.apache.axis.description.TypeDesc(ComunicaSingolaResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("smartCig.ws.comunicazioni_v1.0.0", "ComunicaSingolaResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cig");
        elemField.setXmlName(new javax.xml.namespace.QName("smartCig.ws.comunicazioni_v1.0.0", "cig"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceRisultato");
        elemField.setXmlName(new javax.xml.namespace.QName("smartCig.ws.comunicazioni_v1.0.0", "codiceRisultato"));
        elemField.setXmlType(new javax.xml.namespace.QName("smartCig.risultato", "RisultatoType"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("errore");
        elemField.setXmlName(new javax.xml.namespace.QName("smartCig.ws.comunicazioni_v1.0.0", "errore"));
        elemField.setXmlType(new javax.xml.namespace.QName("smartCig.errore", "ErroreType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
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
