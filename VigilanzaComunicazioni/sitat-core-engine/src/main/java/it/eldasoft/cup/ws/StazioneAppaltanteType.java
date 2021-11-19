/**
 * StazioneAppaltanteType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.eldasoft.cup.ws;

public class StazioneAppaltanteType  implements java.io.Serializable {
    private java.lang.String codein;

    private java.lang.String nomein;

    public StazioneAppaltanteType() {
    }

    public StazioneAppaltanteType(
           java.lang.String codein,
           java.lang.String nomein) {
           this.codein = codein;
           this.nomein = nomein;
    }


    /**
     * Gets the codein value for this StazioneAppaltanteType.
     * 
     * @return codein
     */
    public java.lang.String getCodein() {
        return codein;
    }


    /**
     * Sets the codein value for this StazioneAppaltanteType.
     * 
     * @param codein
     */
    public void setCodein(java.lang.String codein) {
        this.codein = codein;
    }


    /**
     * Gets the nomein value for this StazioneAppaltanteType.
     * 
     * @return nomein
     */
    public java.lang.String getNomein() {
        return nomein;
    }


    /**
     * Sets the nomein value for this StazioneAppaltanteType.
     * 
     * @param nomein
     */
    public void setNomein(java.lang.String nomein) {
        this.nomein = nomein;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof StazioneAppaltanteType)) return false;
        StazioneAppaltanteType other = (StazioneAppaltanteType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.codein==null && other.getCodein()==null) || 
             (this.codein!=null &&
              this.codein.equals(other.getCodein()))) &&
            ((this.nomein==null && other.getNomein()==null) || 
             (this.nomein!=null &&
              this.nomein.equals(other.getNomein())));
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
        if (getCodein() != null) {
            _hashCode += getCodein().hashCode();
        }
        if (getNomein() != null) {
            _hashCode += getNomein().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(StazioneAppaltanteType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.cup.eldasoft.it/", "stazioneAppaltanteType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codein");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codein"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nomein");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nomein"));
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
