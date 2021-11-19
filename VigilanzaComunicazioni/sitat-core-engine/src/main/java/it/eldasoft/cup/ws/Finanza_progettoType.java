/**
 * Finanza_progettoType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.eldasoft.cup.ws;

public class Finanza_progettoType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected Finanza_progettoType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _A = "A";
    public static final java.lang.String _N = "N";
    public static final java.lang.String _P = "P";
    public static final Finanza_progettoType A = new Finanza_progettoType(_A);
    public static final Finanza_progettoType N = new Finanza_progettoType(_N);
    public static final Finanza_progettoType P = new Finanza_progettoType(_P);
    public java.lang.String getValue() { return _value_;}
    public static Finanza_progettoType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        Finanza_progettoType enumeration = (Finanza_progettoType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static Finanza_progettoType fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(Finanza_progettoType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.cup.eldasoft.it/", "finanza_progettoType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
