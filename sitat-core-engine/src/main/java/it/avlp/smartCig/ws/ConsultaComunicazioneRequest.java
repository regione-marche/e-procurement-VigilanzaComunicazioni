/**
 * ConsultaComunicazioneRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.avlp.smartCig.ws;

import it.avlp.smartCig.user.LoggedUserInfoType;

public class ConsultaComunicazioneRequest  implements java.io.Serializable {
    private java.lang.String cig;

    private LoggedUserInfoType user;

    public ConsultaComunicazioneRequest() {
    }

    public ConsultaComunicazioneRequest(
           java.lang.String cig,
           LoggedUserInfoType user) {
           this.cig = cig;
           this.user = user;
    }


    /**
     * Gets the cig value for this ConsultaComunicazioneRequest.
     * 
     * @return cig
     */
    public java.lang.String getCig() {
        return cig;
    }


    /**
     * Sets the cig value for this ConsultaComunicazioneRequest.
     * 
     * @param cig
     */
    public void setCig(java.lang.String cig) {
        this.cig = cig;
    }


    /**
     * Gets the user value for this ConsultaComunicazioneRequest.
     * 
     * @return user
     */
    public LoggedUserInfoType getUser() {
        return user;
    }


    /**
     * Sets the user value for this ConsultaComunicazioneRequest.
     * 
     * @param user
     */
    public void setUser(LoggedUserInfoType user) {
        this.user = user;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ConsultaComunicazioneRequest)) return false;
        ConsultaComunicazioneRequest other = (ConsultaComunicazioneRequest) obj;
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
        if (getCig() != null) {
            _hashCode += getCig().hashCode();
        }
        if (getUser() != null) {
            _hashCode += getUser().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ConsultaComunicazioneRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("smartCig.ws.comunicazioni_v1.0.0", "ConsultaComunicazioneRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cig");
        elemField.setXmlName(new javax.xml.namespace.QName("smartCig.ws.comunicazioni_v1.0.0", "cig"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
