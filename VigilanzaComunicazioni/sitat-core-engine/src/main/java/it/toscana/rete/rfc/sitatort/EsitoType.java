/**
 * EsitoType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.toscana.rete.rfc.sitatort;

public class EsitoType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected EsitoType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String RICEVUTA_1 = "1";
    public static final java.lang.String IMPORTATA_2 = "2";
    public static final java.lang.String ERRORE_3 = "3";
    public static final java.lang.String WARNING_4 = "4";
    public static final java.lang.String TEST_5 = "5";
    public static final EsitoType RICEVUTA = new EsitoType(RICEVUTA_1);
    public static final EsitoType IMPORTATA = new EsitoType(IMPORTATA_2);
    public static final EsitoType ERRORE = new EsitoType(ERRORE_3);
    public static final EsitoType WARNING = new EsitoType(WARNING_4);
    public static final EsitoType TEST = new EsitoType(TEST_5);
    public java.lang.String getValue() { return _value_;}
    public static EsitoType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        EsitoType enumeration = (EsitoType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static EsitoType fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(EsitoType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://rete.toscana.it/rfc/sitatort/", "EsitoType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
