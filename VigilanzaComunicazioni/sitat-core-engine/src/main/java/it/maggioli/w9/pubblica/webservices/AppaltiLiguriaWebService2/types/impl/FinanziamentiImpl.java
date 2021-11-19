/*
 * XML Type:  Finanziamenti
 * Namespace: https://appaltiliguria.regione.liguria.it/pubblica/webservices/types
 * Java type: it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Finanziamenti
 *
 * Automatically generated - do not modify.
 */
package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.impl;
/**
 * An XML Finanziamenti(@https://appaltiliguria.regione.liguria.it/pubblica/webservices/types).
 *
 * This is a complex type.
 */
public class FinanziamentiImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Finanziamenti
{
    
    public FinanziamentiImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName IDFINANZIAMENTO$0 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "ID_FINANZIAMENTO");
    private static final javax.xml.namespace.QName IMPORTOFINANZIAMENTO$2 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "IMPORTO_FINANZIAMENTO");
    
    
    /**
     * Gets the "ID_FINANZIAMENTO" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipoFinanziamentoType.Enum getIDFINANZIAMENTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IDFINANZIAMENTO$0, 0);
            if (target == null)
            {
                return null;
            }
            return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipoFinanziamentoType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "ID_FINANZIAMENTO" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipoFinanziamentoType xgetIDFINANZIAMENTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipoFinanziamentoType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipoFinanziamentoType)get_store().find_element_user(IDFINANZIAMENTO$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "ID_FINANZIAMENTO" element
     */
    public void setIDFINANZIAMENTO(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipoFinanziamentoType.Enum idfinanziamento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IDFINANZIAMENTO$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IDFINANZIAMENTO$0);
            }
            target.setEnumValue(idfinanziamento);
        }
    }
    
    /**
     * Sets (as xml) the "ID_FINANZIAMENTO" element
     */
    public void xsetIDFINANZIAMENTO(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipoFinanziamentoType idfinanziamento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipoFinanziamentoType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipoFinanziamentoType)get_store().find_element_user(IDFINANZIAMENTO$0, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipoFinanziamentoType)get_store().add_element_user(IDFINANZIAMENTO$0);
            }
            target.set(idfinanziamento);
        }
    }
    
    /**
     * Gets the "IMPORTO_FINANZIAMENTO" element
     */
    public double getIMPORTOFINANZIAMENTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPORTOFINANZIAMENTO$2, 0);
            if (target == null)
            {
                return 0.0;
            }
            return target.getDoubleValue();
        }
    }
    
    /**
     * Gets (as xml) the "IMPORTO_FINANZIAMENTO" element
     */
    public org.apache.xmlbeans.XmlDouble xgetIMPORTOFINANZIAMENTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPORTOFINANZIAMENTO$2, 0);
            return target;
        }
    }
    
    /**
     * Sets the "IMPORTO_FINANZIAMENTO" element
     */
    public void setIMPORTOFINANZIAMENTO(double importofinanziamento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPORTOFINANZIAMENTO$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IMPORTOFINANZIAMENTO$2);
            }
            target.setDoubleValue(importofinanziamento);
        }
    }
    
    /**
     * Sets (as xml) the "IMPORTO_FINANZIAMENTO" element
     */
    public void xsetIMPORTOFINANZIAMENTO(org.apache.xmlbeans.XmlDouble importofinanziamento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPORTOFINANZIAMENTO$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(IMPORTOFINANZIAMENTO$2);
            }
            target.set(importofinanziamento);
        }
    }
}
