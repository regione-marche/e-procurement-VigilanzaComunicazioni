/**
 * EsitoElaborazioneCUP_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.mef.serviziCUP.types;

public class EsitoElaborazioneCUP_Type implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected EsitoElaborazioneCUP_Type(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _ELABORAZIONE_ESEGUITA = "ELABORAZIONE_ESEGUITA";
    public static final java.lang.String _ERRORE_NELLA_ELABORAZIONE_DEL_MESSAGGIO = "ERRORE_NELLA_ELABORAZIONE_DEL_MESSAGGIO";
    public static final java.lang.String _RICHIESTA_NON_PRESENTE = "RICHIESTA_NON_PRESENTE";
    public static final java.lang.String _ERRORE_APPLICATIVO = "ERRORE_APPLICATIVO";
    public static final EsitoElaborazioneCUP_Type ELABORAZIONE_ESEGUITA = new EsitoElaborazioneCUP_Type(_ELABORAZIONE_ESEGUITA);
    public static final EsitoElaborazioneCUP_Type ERRORE_NELLA_ELABORAZIONE_DEL_MESSAGGIO = new EsitoElaborazioneCUP_Type(_ERRORE_NELLA_ELABORAZIONE_DEL_MESSAGGIO);
    public static final EsitoElaborazioneCUP_Type RICHIESTA_NON_PRESENTE = new EsitoElaborazioneCUP_Type(_RICHIESTA_NON_PRESENTE);
    public static final EsitoElaborazioneCUP_Type ERRORE_APPLICATIVO = new EsitoElaborazioneCUP_Type(_ERRORE_APPLICATIVO);
    public java.lang.String getValue() { return _value_;}
    public static EsitoElaborazioneCUP_Type fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        EsitoElaborazioneCUP_Type enumeration = (EsitoElaborazioneCUP_Type)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static EsitoElaborazioneCUP_Type fromString(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumSerializer(
            _javaType, _xmlType);
    }
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumDeserializer(
            _javaType, _xmlType);
    }
    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(EsitoElaborazioneCUP_Type.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://serviziCUP.mef.it/types/", "esitoElaborazioneCUP_Type"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
