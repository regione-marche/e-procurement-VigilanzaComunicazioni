/**
 * TipologiaType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.eldasoft.cup.ws;

public class TipologiaType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected TipologiaType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _value1 = "00";
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
    public static final java.lang.String _value12 = "11";
    public static final java.lang.String _value13 = "12";
    public static final java.lang.String _value14 = "13";
    public static final java.lang.String _value15 = "14";
    public static final java.lang.String _value16 = "15";
    public static final java.lang.String _value17 = "16";
    public static final java.lang.String _value18 = "17";
    public static final java.lang.String _value19 = "18";
    public static final java.lang.String _value20 = "19";
    public static final java.lang.String _value21 = "20";
    public static final java.lang.String _value22 = "21";
    public static final java.lang.String _value23 = "51";
    public static final java.lang.String _value24 = "52";
    public static final java.lang.String _value25 = "53";
    public static final java.lang.String _value26 = "54";
    public static final java.lang.String _value27 = "55";
    public static final java.lang.String _value28 = "56";
    public static final java.lang.String _value29 = "57";
    public static final java.lang.String _value30 = "58";
    public static final java.lang.String _value31 = "99";
    public static final TipologiaType value1 = new TipologiaType(_value1);
    public static final TipologiaType value2 = new TipologiaType(_value2);
    public static final TipologiaType value3 = new TipologiaType(_value3);
    public static final TipologiaType value4 = new TipologiaType(_value4);
    public static final TipologiaType value5 = new TipologiaType(_value5);
    public static final TipologiaType value6 = new TipologiaType(_value6);
    public static final TipologiaType value7 = new TipologiaType(_value7);
    public static final TipologiaType value8 = new TipologiaType(_value8);
    public static final TipologiaType value9 = new TipologiaType(_value9);
    public static final TipologiaType value10 = new TipologiaType(_value10);
    public static final TipologiaType value11 = new TipologiaType(_value11);
    public static final TipologiaType value12 = new TipologiaType(_value12);
    public static final TipologiaType value13 = new TipologiaType(_value13);
    public static final TipologiaType value14 = new TipologiaType(_value14);
    public static final TipologiaType value15 = new TipologiaType(_value15);
    public static final TipologiaType value16 = new TipologiaType(_value16);
    public static final TipologiaType value17 = new TipologiaType(_value17);
    public static final TipologiaType value18 = new TipologiaType(_value18);
    public static final TipologiaType value19 = new TipologiaType(_value19);
    public static final TipologiaType value20 = new TipologiaType(_value20);
    public static final TipologiaType value21 = new TipologiaType(_value21);
    public static final TipologiaType value22 = new TipologiaType(_value22);
    public static final TipologiaType value23 = new TipologiaType(_value23);
    public static final TipologiaType value24 = new TipologiaType(_value24);
    public static final TipologiaType value25 = new TipologiaType(_value25);
    public static final TipologiaType value26 = new TipologiaType(_value26);
    public static final TipologiaType value27 = new TipologiaType(_value27);
    public static final TipologiaType value28 = new TipologiaType(_value28);
    public static final TipologiaType value29 = new TipologiaType(_value29);
    public static final TipologiaType value30 = new TipologiaType(_value30);
    public static final TipologiaType value31 = new TipologiaType(_value31);
    public java.lang.String getValue() { return _value_;}
    public static TipologiaType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        TipologiaType enumeration = (TipologiaType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static TipologiaType fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(TipologiaType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.cup.eldasoft.it/", "tipologiaType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
