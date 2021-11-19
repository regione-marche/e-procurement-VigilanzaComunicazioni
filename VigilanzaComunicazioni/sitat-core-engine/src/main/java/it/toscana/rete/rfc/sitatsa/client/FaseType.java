/**
 * FaseType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.toscana.rete.rfc.sitatsa.client;

public class FaseType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected FaseType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String FASE1 = "1";
    public static final java.lang.String FASE2 = "2";
    public static final java.lang.String FASE3 = "3";
    public static final java.lang.String FASE4 = "4";
    public static final java.lang.String FASE5 = "5";
    public static final java.lang.String FASE6 = "6";
    public static final java.lang.String FASE7 = "7";
    public static final java.lang.String FASE8 = "8";
    public static final java.lang.String FASE9 = "9";
    public static final java.lang.String FASE10 = "10";
    public static final java.lang.String FASE11 = "11";
    public static final java.lang.String FASE12 = "12";
    public static final java.lang.String FASE13 = "13";
    public static final java.lang.String FASE101 = "101";
    public static final java.lang.String FASE102 = "102";
    public static final java.lang.String FASE983 = "983";
    public static final java.lang.String FASE984 = "984";
    public static final java.lang.String FASE985 = "985";
    public static final java.lang.String FASE986 = "986";
    public static final java.lang.String FASE987 = "987";
    public static final java.lang.String FASE988 = "988";
    public static final java.lang.String FASE989 = "989";
    public static final java.lang.String FASE991 = "991";
    public static final java.lang.String FASE992 = "992";
    public static final java.lang.String FASE993 = "993";
    public static final java.lang.String FASE994 = "994";
    public static final java.lang.String FASE995 = "995";
    public static final java.lang.String FASE996 = "996";
    public static final java.lang.String FASE997 = "997";
    public static final java.lang.String FASE998 = "998";
    public static final FaseType FASE_1 = new FaseType(FASE1);
    public static final FaseType FASE_2 = new FaseType(FASE2);
    public static final FaseType FASE_3 = new FaseType(FASE3);
    public static final FaseType FASE_4 = new FaseType(FASE4);
    public static final FaseType FASE_5 = new FaseType(FASE5);
    public static final FaseType FASE_6 = new FaseType(FASE6);
    public static final FaseType FASE_7 = new FaseType(FASE7);
    public static final FaseType FASE_8 = new FaseType(FASE8);
    public static final FaseType FASE_9 = new FaseType(FASE9);
    public static final FaseType FASE_10 = new FaseType(FASE10);
    public static final FaseType FASE_11 = new FaseType(FASE11);
    public static final FaseType FASE_12 = new FaseType(FASE12);
    public static final FaseType FASE_13 = new FaseType(FASE13);
    public static final FaseType FASE_101 = new FaseType(FASE101);
    public static final FaseType FASE_102 = new FaseType(FASE102);
    public static final FaseType FASE_983 = new FaseType(FASE983);
    public static final FaseType FASE_984 = new FaseType(FASE984);
    public static final FaseType FASE_985 = new FaseType(FASE985);
    public static final FaseType FASE_986 = new FaseType(FASE986);
    public static final FaseType FASE_987 = new FaseType(FASE987);
    public static final FaseType FASE_988 = new FaseType(FASE988);
    public static final FaseType FASE_989 = new FaseType(FASE989);
    public static final FaseType FASE_991 = new FaseType(FASE991);
    public static final FaseType FASE_992 = new FaseType(FASE992);
    public static final FaseType FASE_993 = new FaseType(FASE993);
    public static final FaseType FASE_994 = new FaseType(FASE994);
    public static final FaseType FASE_995 = new FaseType(FASE995);
    public static final FaseType FASE_996 = new FaseType(FASE996);
    public static final FaseType FASE_997 = new FaseType(FASE997);
    public static final FaseType FASE_998 = new FaseType(FASE998);
    
    public java.lang.String getValue() { return _value_;}
    public static FaseType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        FaseType enumeration = (FaseType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static FaseType fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(FaseType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://rete.toscana.it/rfc/sitatort/", "FaseType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
