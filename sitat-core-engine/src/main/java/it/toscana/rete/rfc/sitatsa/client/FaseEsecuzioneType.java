/**
 * FaseEsecuzioneType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.toscana.rete.rfc.sitatsa.client;


/**
 * Fase esecuzione e progressivo della fase
 */
public class FaseEsecuzioneType  implements java.io.Serializable {
    /* Codice della fase */
    private it.toscana.rete.rfc.sitatsa.client.FaseType codiceFase;

    /* Progressivo della fase */
    private java.lang.Integer num;

    public FaseEsecuzioneType() {
    }

    public FaseEsecuzioneType(
           it.toscana.rete.rfc.sitatsa.client.FaseType codiceFase,
           java.lang.Integer num) {
           this.codiceFase = codiceFase;
           this.num = num;
    }


    /**
     * Gets the codiceFase value for this FaseEsecuzioneType.
     * 
     * @return codiceFase   * Codice della fase
     */
    public it.toscana.rete.rfc.sitatsa.client.FaseType getCodiceFase() {
        return codiceFase;
    }


    /**
     * Sets the codiceFase value for this FaseEsecuzioneType.
     * 
     * @param codiceFase   * Codice della fase
     */
    public void setCodiceFase(it.toscana.rete.rfc.sitatsa.client.FaseType codiceFase) {
        this.codiceFase = codiceFase;
    }


    /**
     * Gets the num value for this FaseEsecuzioneType.
     * 
     * @return num   * Progressivo della fase
     */
    public java.lang.Integer getNum() {
        return num;
    }


    /**
     * Sets the num value for this FaseEsecuzioneType.
     * 
     * @param num   * Progressivo della fase
     */
    public void setNum(java.lang.Integer num) {
        this.num = num;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FaseEsecuzioneType)) return false;
        FaseEsecuzioneType other = (FaseEsecuzioneType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.codiceFase==null && other.getCodiceFase()==null) || 
             (this.codiceFase!=null &&
              this.codiceFase.equals(other.getCodiceFase()))) &&
            ((this.num==null && other.getNum()==null) || 
             (this.num!=null &&
              this.num.equals(other.getNum())));
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
        if (getCodiceFase() != null) {
            _hashCode += getCodiceFase().hashCode();
        }
        if (getNum() != null) {
            _hashCode += getNum().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(FaseEsecuzioneType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://rete.toscana.it/rfc/sitatort/", "FaseEsecuzioneType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceFase");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CodiceFase"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://rete.toscana.it/rfc/sitatort/", "FaseType"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("num");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Num"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
