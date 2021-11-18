/**
 * SettoreType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.eldasoft.cup.ws;

public class SettoreType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected SettoreType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _value1 = "11";
    public static final java.lang.String _value2 = "01";
    public static final java.lang.String _value3 = "02";
    public static final java.lang.String _value4 = "03";
    public static final java.lang.String _value5 = "04";
    public static final java.lang.String _value6 = "05";
    public static final java.lang.String _value7 = "06";
    public static final java.lang.String _value8 = "07";
    public static final java.lang.String _value9 = "08";
    public static final java.lang.String _value10 = "09";
    public static final java.lang.String _value11 = "10";
    public static final SettoreType value1 = new SettoreType(_value1);
    public static final SettoreType value2 = new SettoreType(_value2);
    public static final SettoreType value3 = new SettoreType(_value3);
    public static final SettoreType value4 = new SettoreType(_value4);
    public static final SettoreType value5 = new SettoreType(_value5);
    public static final SettoreType value6 = new SettoreType(_value6);
    public static final SettoreType value7 = new SettoreType(_value7);
    public static final SettoreType value8 = new SettoreType(_value8);
    public static final SettoreType value9 = new SettoreType(_value9);
    public static final SettoreType value10 = new SettoreType(_value10);
    public static final SettoreType value11 = new SettoreType(_value11);
    public java.lang.String getValue() { return _value_;}
    public static SettoreType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        SettoreType enumeration = (SettoreType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static SettoreType fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(SettoreType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.cup.eldasoft.it/", "settoreType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
