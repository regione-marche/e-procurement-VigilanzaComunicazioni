/*
 * XML Type:  TipoStrumentoType
 * Namespace: https://appaltiliguria.regione.liguria.it/pubblica/webservices/types
 * Java type: it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipoStrumentoType
 *
 * Automatically generated - do not modify.
 */
package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types;


/**
 * An XML TipoStrumentoType(@https://appaltiliguria.regione.liguria.it/pubblica/webservices/types).
 *
 * This is an atomic type that is a restriction of it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipoStrumentoType.
 */
public interface TipoStrumentoType extends org.apache.xmlbeans.XmlString
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(TipoStrumentoType.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s9B544F4075EA19B46E708810C26CE1A4").resolveHandle("tipostrumentotype10datype");
    
    org.apache.xmlbeans.StringEnumAbstractBase enumValue();
    void set(org.apache.xmlbeans.StringEnumAbstractBase e);
    
    static final Enum A_01 = Enum.forString("A01");
    static final Enum A_02 = Enum.forString("A02");
    static final Enum A_03 = Enum.forString("A03");
    static final Enum A_04 = Enum.forString("A04");
    static final Enum A_05 = Enum.forString("A05");
    static final Enum A_06 = Enum.forString("A06");
    static final Enum B_01 = Enum.forString("B01");
    static final Enum B_02 = Enum.forString("B02");
    static final Enum B_03 = Enum.forString("B03");
    static final Enum B_04 = Enum.forString("B04");
    static final Enum B_05 = Enum.forString("B05");
    static final Enum B_06 = Enum.forString("B06");
    static final Enum B_07 = Enum.forString("B07");
    static final Enum B_09 = Enum.forString("B09");
    static final Enum B_10 = Enum.forString("B10");
    static final Enum B_11 = Enum.forString("B11");
    static final Enum B_12 = Enum.forString("B12");
    static final Enum B_13 = Enum.forString("B13");
    static final Enum B_14 = Enum.forString("B14");
    static final Enum B_15 = Enum.forString("B15");
    static final Enum C_01 = Enum.forString("C01");
    static final Enum C_02 = Enum.forString("C02");
    static final Enum C_03 = Enum.forString("C03");
    static final Enum C_04 = Enum.forString("C04");
    static final Enum C_05 = Enum.forString("C05");
    static final Enum C_06 = Enum.forString("C06");
    static final Enum C_07 = Enum.forString("C07");
    static final Enum D_01 = Enum.forString("D01");
    static final Enum D_02 = Enum.forString("D02");
    static final Enum E_01 = Enum.forString("E01");
    
    static final int INT_A_01 = Enum.INT_A_01;
    static final int INT_A_02 = Enum.INT_A_02;
    static final int INT_A_03 = Enum.INT_A_03;
    static final int INT_A_04 = Enum.INT_A_04;
    static final int INT_A_05 = Enum.INT_A_05;
    static final int INT_A_06 = Enum.INT_A_06;
    static final int INT_B_01 = Enum.INT_B_01;
    static final int INT_B_02 = Enum.INT_B_02;
    static final int INT_B_03 = Enum.INT_B_03;
    static final int INT_B_04 = Enum.INT_B_04;
    static final int INT_B_05 = Enum.INT_B_05;
    static final int INT_B_06 = Enum.INT_B_06;
    static final int INT_B_07 = Enum.INT_B_07;
    static final int INT_B_09 = Enum.INT_B_09;
    static final int INT_B_10 = Enum.INT_B_10;
    static final int INT_B_11 = Enum.INT_B_11;
    static final int INT_B_12 = Enum.INT_B_12;
    static final int INT_B_13 = Enum.INT_B_13;
    static final int INT_B_14 = Enum.INT_B_14;
    static final int INT_B_15 = Enum.INT_B_15;
    static final int INT_C_01 = Enum.INT_C_01;
    static final int INT_C_02 = Enum.INT_C_02;
    static final int INT_C_03 = Enum.INT_C_03;
    static final int INT_C_04 = Enum.INT_C_04;
    static final int INT_C_05 = Enum.INT_C_05;
    static final int INT_C_06 = Enum.INT_C_06;
    static final int INT_C_07 = Enum.INT_C_07;
    static final int INT_D_01 = Enum.INT_D_01;
    static final int INT_D_02 = Enum.INT_D_02;
    static final int INT_E_01 = Enum.INT_E_01;
    
    /**
     * Enumeration value class for it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipoStrumentoType.
     * These enum values can be used as follows:
     * <pre>
     * enum.toString(); // returns the string value of the enum
     * enum.intValue(); // returns an int value, useful for switches
     * // e.g., case Enum.INT_A_01
     * Enum.forString(s); // returns the enum value for a string
     * Enum.forInt(i); // returns the enum value for an int
     * </pre>
     * Enumeration objects are immutable singleton objects that
     * can be compared using == object equality. They have no
     * public constructor. See the constants defined within this
     * class for all the valid values.
     */
    static final class Enum extends org.apache.xmlbeans.StringEnumAbstractBase
    {
        /**
         * Returns the enum value for a string, or null if none.
         */
        public static Enum forString(java.lang.String s)
            { return (Enum)table.forString(s); }
        /**
         * Returns the enum value corresponding to an int, or null if none.
         */
        public static Enum forInt(int i)
            { return (Enum)table.forInt(i); }
        
        private Enum(java.lang.String s, int i)
            { super(s, i); }
        
        static final int INT_A_01 = 1;
        static final int INT_A_02 = 2;
        static final int INT_A_03 = 3;
        static final int INT_A_04 = 4;
        static final int INT_A_05 = 5;
        static final int INT_A_06 = 6;
        static final int INT_B_01 = 7;
        static final int INT_B_02 = 8;
        static final int INT_B_03 = 9;
        static final int INT_B_04 = 10;
        static final int INT_B_05 = 11;
        static final int INT_B_06 = 12;
        static final int INT_B_07 = 13;
        static final int INT_B_09 = 14;
        static final int INT_B_10 = 15;
        static final int INT_B_11 = 16;
        static final int INT_B_12 = 17;
        static final int INT_B_13 = 18;
        static final int INT_B_14 = 19;
        static final int INT_B_15 = 20;
        static final int INT_C_01 = 21;
        static final int INT_C_02 = 22;
        static final int INT_C_03 = 23;
        static final int INT_C_04 = 24;
        static final int INT_C_05 = 25;
        static final int INT_C_06 = 26;
        static final int INT_C_07 = 27;
        static final int INT_D_01 = 28;
        static final int INT_D_02 = 29;
        static final int INT_E_01 = 30;
        
        public static final org.apache.xmlbeans.StringEnumAbstractBase.Table table =
            new org.apache.xmlbeans.StringEnumAbstractBase.Table
        (
            new Enum[]
            {
                new Enum("A01", INT_A_01),
                new Enum("A02", INT_A_02),
                new Enum("A03", INT_A_03),
                new Enum("A04", INT_A_04),
                new Enum("A05", INT_A_05),
                new Enum("A06", INT_A_06),
                new Enum("B01", INT_B_01),
                new Enum("B02", INT_B_02),
                new Enum("B03", INT_B_03),
                new Enum("B04", INT_B_04),
                new Enum("B05", INT_B_05),
                new Enum("B06", INT_B_06),
                new Enum("B07", INT_B_07),
                new Enum("B09", INT_B_09),
                new Enum("B10", INT_B_10),
                new Enum("B11", INT_B_11),
                new Enum("B12", INT_B_12),
                new Enum("B13", INT_B_13),
                new Enum("B14", INT_B_14),
                new Enum("B15", INT_B_15),
                new Enum("C01", INT_C_01),
                new Enum("C02", INT_C_02),
                new Enum("C03", INT_C_03),
                new Enum("C04", INT_C_04),
                new Enum("C05", INT_C_05),
                new Enum("C06", INT_C_06),
                new Enum("C07", INT_C_07),
                new Enum("D01", INT_D_01),
                new Enum("D02", INT_D_02),
                new Enum("E01", INT_E_01),
            }
        );
        private static final long serialVersionUID = 1L;
        private java.lang.Object readResolve() { return forInt(intValue()); } 
    }
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipoStrumentoType newValue(java.lang.Object obj) {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipoStrumentoType) type.newValue( obj ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipoStrumentoType newInstance() {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipoStrumentoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipoStrumentoType newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipoStrumentoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipoStrumentoType parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipoStrumentoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipoStrumentoType parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipoStrumentoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipoStrumentoType parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipoStrumentoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipoStrumentoType parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipoStrumentoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipoStrumentoType parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipoStrumentoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipoStrumentoType parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipoStrumentoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipoStrumentoType parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipoStrumentoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipoStrumentoType parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipoStrumentoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipoStrumentoType parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipoStrumentoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipoStrumentoType parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipoStrumentoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipoStrumentoType parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipoStrumentoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipoStrumentoType parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipoStrumentoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipoStrumentoType parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipoStrumentoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipoStrumentoType parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipoStrumentoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipoStrumentoType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipoStrumentoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipoStrumentoType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipoStrumentoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
