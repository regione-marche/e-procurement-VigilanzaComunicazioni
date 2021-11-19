/**
 * ResponseElencoSchedeType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.toscana.rete.rfc.sitatsa.client;

public class ResponseElencoSchedeType  implements java.io.Serializable {
    private java.lang.String error;

    private boolean success;

    private it.toscana.rete.rfc.sitatsa.client.FaseEsecuzioneType[] elencoSchede;

    public ResponseElencoSchedeType() {
    }

    public ResponseElencoSchedeType(
           java.lang.String error,
           boolean success,
           it.toscana.rete.rfc.sitatsa.client.FaseEsecuzioneType[] elencoSchede) {
           this.error = error;
           this.success = success;
           this.elencoSchede = elencoSchede;
    }


    /**
     * Gets the error value for this ResponseElencoSchedeType.
     * 
     * @return error
     */
    public java.lang.String getError() {
        return error;
    }


    /**
     * Sets the error value for this ResponseElencoSchedeType.
     * 
     * @param error
     */
    public void setError(java.lang.String error) {
        this.error = error;
    }


    /**
     * Gets the success value for this ResponseElencoSchedeType.
     * 
     * @return success
     */
    public boolean isSuccess() {
        return success;
    }


    /**
     * Sets the success value for this ResponseElencoSchedeType.
     * 
     * @param success
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }


    /**
     * Gets the elencoSchede value for this ResponseElencoSchedeType.
     * 
     * @return elencoSchede
     */
    public it.toscana.rete.rfc.sitatsa.client.FaseEsecuzioneType[] getElencoSchede() {
        return elencoSchede;
    }


    /**
     * Sets the elencoSchede value for this ResponseElencoSchedeType.
     * 
     * @param elencoSchede
     */
    public void setElencoSchede(it.toscana.rete.rfc.sitatsa.client.FaseEsecuzioneType[] elencoSchede) {
        this.elencoSchede = elencoSchede;
    }

    public it.toscana.rete.rfc.sitatsa.client.FaseEsecuzioneType getElencoSchede(int i) {
        return this.elencoSchede[i];
    }

    public void setElencoSchede(int i, it.toscana.rete.rfc.sitatsa.client.FaseEsecuzioneType _value) {
        this.elencoSchede[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ResponseElencoSchedeType)) return false;
        ResponseElencoSchedeType other = (ResponseElencoSchedeType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.error==null && other.getError()==null) || 
             (this.error!=null &&
              this.error.equals(other.getError()))) &&
            this.success == other.isSuccess() &&
            ((this.elencoSchede==null && other.getElencoSchede()==null) || 
             (this.elencoSchede!=null &&
              java.util.Arrays.equals(this.elencoSchede, other.getElencoSchede())));
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
        if (getError() != null) {
            _hashCode += getError().hashCode();
        }
        _hashCode += (isSuccess() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getElencoSchede() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getElencoSchede());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getElencoSchede(), i);
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
        new org.apache.axis.description.TypeDesc(ResponseElencoSchedeType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://rete.toscana.it/rfc/sitatort/", "ResponseElencoSchedeType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("error");
        elemField.setXmlName(new javax.xml.namespace.QName("", "error"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("success");
        elemField.setXmlName(new javax.xml.namespace.QName("", "success"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("elencoSchede");
        elemField.setXmlName(new javax.xml.namespace.QName("", "elencoSchede"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://rete.toscana.it/rfc/sitatort/", "FaseEsecuzioneType"));
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
