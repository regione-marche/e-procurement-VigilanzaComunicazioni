/*
 * XML Type:  Categorie
 * Namespace: https://appaltiliguria.regione.liguria.it/pubblica/webservices/types
 * Java type: it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Categorie
 *
 * Automatically generated - do not modify.
 */
package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.impl;
/**
 * An XML Categorie(@https://appaltiliguria.regione.liguria.it/pubblica/webservices/types).
 *
 * This is a complex type.
 */
public class CategorieImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Categorie
{
    
    public CategorieImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName IDCATEGORIA$0 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "ID_CATEGORIA");
    private static final javax.xml.namespace.QName CLASSEIMPORTO$2 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "CLASSE_IMPORTO");
    private static final javax.xml.namespace.QName PREVALENTE$4 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "PREVALENTE");
    private static final javax.xml.namespace.QName SCORPORABILE$6 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "SCORPORABILE");
    private static final javax.xml.namespace.QName SUBAPPALTABILE$8 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "SUBAPPALTABILE");
    
    
    /**
     * Gets the "ID_CATEGORIA" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.CategoriaType.Enum getIDCATEGORIA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IDCATEGORIA$0, 0);
            if (target == null)
            {
                return null;
            }
            return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.CategoriaType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "ID_CATEGORIA" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.CategoriaType xgetIDCATEGORIA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.CategoriaType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.CategoriaType)get_store().find_element_user(IDCATEGORIA$0, 0);
            return target;
        }
    }
    
    /**
     * True if has "ID_CATEGORIA" element
     */
    public boolean isSetIDCATEGORIA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(IDCATEGORIA$0) != 0;
        }
    }
    
    /**
     * Sets the "ID_CATEGORIA" element
     */
    public void setIDCATEGORIA(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.CategoriaType.Enum idcategoria)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IDCATEGORIA$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IDCATEGORIA$0);
            }
            target.setEnumValue(idcategoria);
        }
    }
    
    /**
     * Sets (as xml) the "ID_CATEGORIA" element
     */
    public void xsetIDCATEGORIA(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.CategoriaType idcategoria)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.CategoriaType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.CategoriaType)get_store().find_element_user(IDCATEGORIA$0, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.CategoriaType)get_store().add_element_user(IDCATEGORIA$0);
            }
            target.set(idcategoria);
        }
    }
    
    /**
     * Unsets the "ID_CATEGORIA" element
     */
    public void unsetIDCATEGORIA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(IDCATEGORIA$0, 0);
        }
    }
    
    /**
     * Gets the "CLASSE_IMPORTO" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ClasseImportoType.Enum getCLASSEIMPORTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CLASSEIMPORTO$2, 0);
            if (target == null)
            {
                return null;
            }
            return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ClasseImportoType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "CLASSE_IMPORTO" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ClasseImportoType xgetCLASSEIMPORTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ClasseImportoType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ClasseImportoType)get_store().find_element_user(CLASSEIMPORTO$2, 0);
            return target;
        }
    }
    
    /**
     * True if has "CLASSE_IMPORTO" element
     */
    public boolean isSetCLASSEIMPORTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CLASSEIMPORTO$2) != 0;
        }
    }
    
    /**
     * Sets the "CLASSE_IMPORTO" element
     */
    public void setCLASSEIMPORTO(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ClasseImportoType.Enum classeimporto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CLASSEIMPORTO$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CLASSEIMPORTO$2);
            }
            target.setEnumValue(classeimporto);
        }
    }
    
    /**
     * Sets (as xml) the "CLASSE_IMPORTO" element
     */
    public void xsetCLASSEIMPORTO(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ClasseImportoType classeimporto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ClasseImportoType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ClasseImportoType)get_store().find_element_user(CLASSEIMPORTO$2, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ClasseImportoType)get_store().add_element_user(CLASSEIMPORTO$2);
            }
            target.set(classeimporto);
        }
    }
    
    /**
     * Unsets the "CLASSE_IMPORTO" element
     */
    public void unsetCLASSEIMPORTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CLASSEIMPORTO$2, 0);
        }
    }
    
    /**
     * Gets the "PREVALENTE" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum getPREVALENTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PREVALENTE$4, 0);
            if (target == null)
            {
                return null;
            }
            return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "PREVALENTE" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType xgetPREVALENTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().find_element_user(PREVALENTE$4, 0);
            return target;
        }
    }
    
    /**
     * True if has "PREVALENTE" element
     */
    public boolean isSetPREVALENTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(PREVALENTE$4) != 0;
        }
    }
    
    /**
     * Sets the "PREVALENTE" element
     */
    public void setPREVALENTE(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum prevalente)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PREVALENTE$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PREVALENTE$4);
            }
            target.setEnumValue(prevalente);
        }
    }
    
    /**
     * Sets (as xml) the "PREVALENTE" element
     */
    public void xsetPREVALENTE(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType prevalente)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().find_element_user(PREVALENTE$4, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().add_element_user(PREVALENTE$4);
            }
            target.set(prevalente);
        }
    }
    
    /**
     * Unsets the "PREVALENTE" element
     */
    public void unsetPREVALENTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(PREVALENTE$4, 0);
        }
    }
    
    /**
     * Gets the "SCORPORABILE" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum getSCORPORABILE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SCORPORABILE$6, 0);
            if (target == null)
            {
                return null;
            }
            return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "SCORPORABILE" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType xgetSCORPORABILE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().find_element_user(SCORPORABILE$6, 0);
            return target;
        }
    }
    
    /**
     * True if has "SCORPORABILE" element
     */
    public boolean isSetSCORPORABILE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(SCORPORABILE$6) != 0;
        }
    }
    
    /**
     * Sets the "SCORPORABILE" element
     */
    public void setSCORPORABILE(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum scorporabile)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SCORPORABILE$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(SCORPORABILE$6);
            }
            target.setEnumValue(scorporabile);
        }
    }
    
    /**
     * Sets (as xml) the "SCORPORABILE" element
     */
    public void xsetSCORPORABILE(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType scorporabile)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().find_element_user(SCORPORABILE$6, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().add_element_user(SCORPORABILE$6);
            }
            target.set(scorporabile);
        }
    }
    
    /**
     * Unsets the "SCORPORABILE" element
     */
    public void unsetSCORPORABILE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(SCORPORABILE$6, 0);
        }
    }
    
    /**
     * Gets the "SUBAPPALTABILE" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum getSUBAPPALTABILE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SUBAPPALTABILE$8, 0);
            if (target == null)
            {
                return null;
            }
            return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "SUBAPPALTABILE" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType xgetSUBAPPALTABILE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().find_element_user(SUBAPPALTABILE$8, 0);
            return target;
        }
    }
    
    /**
     * True if has "SUBAPPALTABILE" element
     */
    public boolean isSetSUBAPPALTABILE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(SUBAPPALTABILE$8) != 0;
        }
    }
    
    /**
     * Sets the "SUBAPPALTABILE" element
     */
    public void setSUBAPPALTABILE(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum subappaltabile)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SUBAPPALTABILE$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(SUBAPPALTABILE$8);
            }
            target.setEnumValue(subappaltabile);
        }
    }
    
    /**
     * Sets (as xml) the "SUBAPPALTABILE" element
     */
    public void xsetSUBAPPALTABILE(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType subappaltabile)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().find_element_user(SUBAPPALTABILE$8, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().add_element_user(SUBAPPALTABILE$8);
            }
            target.set(subappaltabile);
        }
    }
    
    /**
     * Unsets the "SUBAPPALTABILE" element
     */
    public void unsetSUBAPPALTABILE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(SUBAPPALTABILE$8, 0);
        }
    }
}
