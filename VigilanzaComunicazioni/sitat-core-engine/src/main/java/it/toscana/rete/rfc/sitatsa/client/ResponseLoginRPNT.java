/**
 * ResponseLoginRPNT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.toscana.rete.rfc.sitatsa.client;

public class ResponseLoginRPNT  implements java.io.Serializable {
    private boolean success;

    private java.lang.String error;

    private java.lang.String loginXML;

    public ResponseLoginRPNT() {
    }

    public ResponseLoginRPNT(
           boolean success,
           java.lang.String error,
           java.lang.String loginXML) {
           this.success = success;
           this.error = error;
           this.loginXML = loginXML;
    }


    /**
     * Gets the success value for this ResponseLoginRPNT.
     * 
     * @return success
     */
    public boolean isSuccess() {
        return success;
    }


    /**
     * Sets the success value for this ResponseLoginRPNT.
     * 
     * @param success
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }


    /**
     * Gets the error value for this ResponseLoginRPNT.
     * 
     * @return error
     */
    public java.lang.String getError() {
        return error;
    }


    /**
     * Sets the error value for this ResponseLoginRPNT.
     * 
     * @param error
     */
    public void setError(java.lang.String error) {
        this.error = error;
    }


    /**
     * Gets the loginXML value for this ResponseLoginRPNT.
     * 
     * @return loginXML
     */
    public java.lang.String getLoginXML() {
        return loginXML;
    }


    /**
     * Sets the loginXML value for this ResponseLoginRPNT.
     * 
     * @param loginXML
     */
    public void setLoginXML(java.lang.String loginXML) {
        this.loginXML = loginXML;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ResponseLoginRPNT)) return false;
        ResponseLoginRPNT other = (ResponseLoginRPNT) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.success == other.isSuccess() &&
            ((this.error==null && other.getError()==null) || 
             (this.error!=null &&
              this.error.equals(other.getError()))) &&
            ((this.loginXML==null && other.getLoginXML()==null) || 
             (this.loginXML!=null &&
              this.loginXML.equals(other.getLoginXML())));
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
        _hashCode += (isSuccess() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getError() != null) {
            _hashCode += getError().hashCode();
        }
        if (getLoginXML() != null) {
            _hashCode += getLoginXML().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ResponseLoginRPNT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://rete.toscana.it/rfc/sitatort/", "ResponseLoginRPNT"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("success");
        elemField.setXmlName(new javax.xml.namespace.QName("", "success"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("error");
        elemField.setXmlName(new javax.xml.namespace.QName("", "error"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("loginXML");
        elemField.setXmlName(new javax.xml.namespace.QName("", "loginXML"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
