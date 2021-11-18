/**
 * Richiesta_RichiestaRispostaSincrona_RichiestaGenerazioneCUP_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.mef.serviziCUP.types;

public class Richiesta_RichiestaRispostaSincrona_RichiestaGenerazioneCUP_Type  implements java.io.Serializable {
    private java.lang.String titoloRichiesta;

    private byte[] richiesta;

    public Richiesta_RichiestaRispostaSincrona_RichiestaGenerazioneCUP_Type() {
    }

    public Richiesta_RichiestaRispostaSincrona_RichiestaGenerazioneCUP_Type(
           java.lang.String titoloRichiesta,
           byte[] richiesta) {
           this.titoloRichiesta = titoloRichiesta;
           this.richiesta = richiesta;
    }


    /**
     * Gets the titoloRichiesta value for this Richiesta_RichiestaRispostaSincrona_RichiestaGenerazioneCUP_Type.
     * 
     * @return titoloRichiesta
     */
    public java.lang.String getTitoloRichiesta() {
        return titoloRichiesta;
    }


    /**
     * Sets the titoloRichiesta value for this Richiesta_RichiestaRispostaSincrona_RichiestaGenerazioneCUP_Type.
     * 
     * @param titoloRichiesta
     */
    public void setTitoloRichiesta(java.lang.String titoloRichiesta) {
        this.titoloRichiesta = titoloRichiesta;
    }


    /**
     * Gets the richiesta value for this Richiesta_RichiestaRispostaSincrona_RichiestaGenerazioneCUP_Type.
     * 
     * @return richiesta
     */
    public byte[] getRichiesta() {
        return richiesta;
    }


    /**
     * Sets the richiesta value for this Richiesta_RichiestaRispostaSincrona_RichiestaGenerazioneCUP_Type.
     * 
     * @param richiesta
     */
    public void setRichiesta(byte[] richiesta) {
        this.richiesta = richiesta;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Richiesta_RichiestaRispostaSincrona_RichiestaGenerazioneCUP_Type)) return false;
        Richiesta_RichiestaRispostaSincrona_RichiestaGenerazioneCUP_Type other = (Richiesta_RichiestaRispostaSincrona_RichiestaGenerazioneCUP_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.titoloRichiesta==null && other.getTitoloRichiesta()==null) || 
             (this.titoloRichiesta!=null &&
              this.titoloRichiesta.equals(other.getTitoloRichiesta()))) &&
            ((this.richiesta==null && other.getRichiesta()==null) || 
             (this.richiesta!=null &&
              java.util.Arrays.equals(this.richiesta, other.getRichiesta())));
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
        if (getTitoloRichiesta() != null) {
            _hashCode += getTitoloRichiesta().hashCode();
        }
        if (getRichiesta() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getRichiesta());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getRichiesta(), i);
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
        new org.apache.axis.description.TypeDesc(Richiesta_RichiestaRispostaSincrona_RichiestaGenerazioneCUP_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://serviziCUP.mef.it/types/", "richiesta_RichiestaRispostaSincrona_RichiestaGenerazioneCUP_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("titoloRichiesta");
        elemField.setXmlName(new javax.xml.namespace.QName("", "TitoloRichiesta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("richiesta");
        elemField.setXmlName(new javax.xml.namespace.QName("", "richiesta"));
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
