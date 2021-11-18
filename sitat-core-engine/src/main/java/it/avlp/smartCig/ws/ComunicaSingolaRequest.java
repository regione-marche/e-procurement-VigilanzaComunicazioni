/**
 * ComunicaSingolaRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.avlp.smartCig.ws;

import it.avlp.smartCig.comunicazione.ComunicazioneInputType;
import it.avlp.smartCig.user.LoggedUserInfoType;

public class ComunicaSingolaRequest  implements java.io.Serializable {
    private ComunicazioneInputType comunicazione;

    private LoggedUserInfoType user;

    public ComunicaSingolaRequest() {
    }

    public ComunicaSingolaRequest(
           ComunicazioneInputType comunicazione,
           LoggedUserInfoType user) {
           this.comunicazione = comunicazione;
           this.user = user;
    }


    /**
     * Gets the comunicazione value for this ComunicaSingolaRequest.
     * 
     * @return comunicazione
     */
    public ComunicazioneInputType getComunicazione() {
        return comunicazione;
    }


    /**
     * Sets the comunicazione value for this ComunicaSingolaRequest.
     * 
     * @param comunicazione
     */
    public void setComunicazione(ComunicazioneInputType comunicazione) {
        this.comunicazione = comunicazione;
    }


    /**
     * Gets the user value for this ComunicaSingolaRequest.
     * 
     * @return user
     */
    public LoggedUserInfoType getUser() {
        return user;
    }


    /**
     * Sets the user value for this ComunicaSingolaRequest.
     * 
     * @param user
     */
    public void setUser(LoggedUserInfoType user) {
        this.user = user;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ComunicaSingolaRequest)) return false;
        ComunicaSingolaRequest other = (ComunicaSingolaRequest) obj;
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
            ((this.user==null && other.getUser()==null) || 
             (this.user!=null &&
              this.user.equals(other.getUser())));
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
        if (getUser() != null) {
            _hashCode += getUser().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ComunicaSingolaRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("smartCig.ws.comunicazioni_v1.0.0", "ComunicaSingolaRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("comunicazione");
        elemField.setXmlName(new javax.xml.namespace.QName("smartCig.ws.comunicazioni_v1.0.0", "comunicazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("smartCig.comunicazione", "ComunicazioneInputType"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("user");
        elemField.setXmlName(new javax.xml.namespace.QName("smartCig.ws.comunicazioni_v1.0.0", "user"));
        elemField.setXmlType(new javax.xml.namespace.QName("smartCig.user", "LoggedUserInfoType"));
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
