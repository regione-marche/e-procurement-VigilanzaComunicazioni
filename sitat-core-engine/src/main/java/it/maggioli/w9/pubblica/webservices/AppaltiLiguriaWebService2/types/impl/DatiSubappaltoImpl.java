/*
 * XML Type:  DatiSubappalto
 * Namespace: https://appaltiliguria.regione.liguria.it/pubblica/webservices/types
 * Java type: it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSubappalto
 *
 * Automatically generated - do not modify.
 */
package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.impl;
/**
 * An XML DatiSubappalto(@https://appaltiliguria.regione.liguria.it/pubblica/webservices/types).
 *
 * This is a complex type.
 */
public class DatiSubappaltoImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSubappalto
{
    
    public DatiSubappaltoImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName DATAAUTORIZZAZIONE$0 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "DATA_AUTORIZZAZIONE");
    private static final javax.xml.namespace.QName OGGETTOSUBAPPALTO$2 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "OGGETTO_SUBAPPALTO");
    private static final javax.xml.namespace.QName IMPORTOPRESUNTO$4 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "IMPORTO_PRESUNTO");
    private static final javax.xml.namespace.QName IMPORTOEFFETTIVO$6 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "IMPORTO_EFFETTIVO");
    private static final javax.xml.namespace.QName IDCATEGORIA$8 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "ID_CATEGORIA");
    private static final javax.xml.namespace.QName IDCPV$10 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "ID_CPV");
    private static final javax.xml.namespace.QName AGGIUDICATARIO$12 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "aggiudicatario");
    private static final javax.xml.namespace.QName ARCH3IMPAGG$14 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "arch3_impagg");
    
    
    /**
     * Gets the "DATA_AUTORIZZAZIONE" element
     */
    public java.util.Calendar getDATAAUTORIZZAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAAUTORIZZAZIONE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_AUTORIZZAZIONE" element
     */
    public org.apache.xmlbeans.XmlDateTime xgetDATAAUTORIZZAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATAAUTORIZZAZIONE$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "DATA_AUTORIZZAZIONE" element
     */
    public void setDATAAUTORIZZAZIONE(java.util.Calendar dataautorizzazione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAAUTORIZZAZIONE$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DATAAUTORIZZAZIONE$0);
            }
            target.setCalendarValue(dataautorizzazione);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_AUTORIZZAZIONE" element
     */
    public void xsetDATAAUTORIZZAZIONE(org.apache.xmlbeans.XmlDateTime dataautorizzazione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATAAUTORIZZAZIONE$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_element_user(DATAAUTORIZZAZIONE$0);
            }
            target.set(dataautorizzazione);
        }
    }
    
    /**
     * Gets the "OGGETTO_SUBAPPALTO" element
     */
    public java.lang.String getOGGETTOSUBAPPALTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(OGGETTOSUBAPPALTO$2, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "OGGETTO_SUBAPPALTO" element
     */
    public org.apache.xmlbeans.XmlString xgetOGGETTOSUBAPPALTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(OGGETTOSUBAPPALTO$2, 0);
            return target;
        }
    }
    
    /**
     * Sets the "OGGETTO_SUBAPPALTO" element
     */
    public void setOGGETTOSUBAPPALTO(java.lang.String oggettosubappalto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(OGGETTOSUBAPPALTO$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(OGGETTOSUBAPPALTO$2);
            }
            target.setStringValue(oggettosubappalto);
        }
    }
    
    /**
     * Sets (as xml) the "OGGETTO_SUBAPPALTO" element
     */
    public void xsetOGGETTOSUBAPPALTO(org.apache.xmlbeans.XmlString oggettosubappalto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(OGGETTOSUBAPPALTO$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(OGGETTOSUBAPPALTO$2);
            }
            target.set(oggettosubappalto);
        }
    }
    
    /**
     * Gets the "IMPORTO_PRESUNTO" element
     */
    public double getIMPORTOPRESUNTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPORTOPRESUNTO$4, 0);
            if (target == null)
            {
                return 0.0;
            }
            return target.getDoubleValue();
        }
    }
    
    /**
     * Gets (as xml) the "IMPORTO_PRESUNTO" element
     */
    public org.apache.xmlbeans.XmlDouble xgetIMPORTOPRESUNTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPORTOPRESUNTO$4, 0);
            return target;
        }
    }
    
    /**
     * Sets the "IMPORTO_PRESUNTO" element
     */
    public void setIMPORTOPRESUNTO(double importopresunto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPORTOPRESUNTO$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IMPORTOPRESUNTO$4);
            }
            target.setDoubleValue(importopresunto);
        }
    }
    
    /**
     * Sets (as xml) the "IMPORTO_PRESUNTO" element
     */
    public void xsetIMPORTOPRESUNTO(org.apache.xmlbeans.XmlDouble importopresunto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPORTOPRESUNTO$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(IMPORTOPRESUNTO$4);
            }
            target.set(importopresunto);
        }
    }
    
    /**
     * Gets the "IMPORTO_EFFETTIVO" element
     */
    public double getIMPORTOEFFETTIVO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPORTOEFFETTIVO$6, 0);
            if (target == null)
            {
                return 0.0;
            }
            return target.getDoubleValue();
        }
    }
    
    /**
     * Gets (as xml) the "IMPORTO_EFFETTIVO" element
     */
    public org.apache.xmlbeans.XmlDouble xgetIMPORTOEFFETTIVO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPORTOEFFETTIVO$6, 0);
            return target;
        }
    }
    
    /**
     * Sets the "IMPORTO_EFFETTIVO" element
     */
    public void setIMPORTOEFFETTIVO(double importoeffettivo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPORTOEFFETTIVO$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IMPORTOEFFETTIVO$6);
            }
            target.setDoubleValue(importoeffettivo);
        }
    }
    
    /**
     * Sets (as xml) the "IMPORTO_EFFETTIVO" element
     */
    public void xsetIMPORTOEFFETTIVO(org.apache.xmlbeans.XmlDouble importoeffettivo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPORTOEFFETTIVO$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(IMPORTOEFFETTIVO$6);
            }
            target.set(importoeffettivo);
        }
    }
    
    /**
     * Gets the "ID_CATEGORIA" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.CategoriaType.Enum getIDCATEGORIA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IDCATEGORIA$8, 0);
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
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.CategoriaType)get_store().find_element_user(IDCATEGORIA$8, 0);
            return target;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IDCATEGORIA$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IDCATEGORIA$8);
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
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.CategoriaType)get_store().find_element_user(IDCATEGORIA$8, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.CategoriaType)get_store().add_element_user(IDCATEGORIA$8);
            }
            target.set(idcategoria);
        }
    }
    
    /**
     * Gets the "ID_CPV" element
     */
    public java.lang.String getIDCPV()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IDCPV$10, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "ID_CPV" element
     */
    public org.apache.xmlbeans.XmlString xgetIDCPV()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(IDCPV$10, 0);
            return target;
        }
    }
    
    /**
     * Sets the "ID_CPV" element
     */
    public void setIDCPV(java.lang.String idcpv)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IDCPV$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IDCPV$10);
            }
            target.setStringValue(idcpv);
        }
    }
    
    /**
     * Sets (as xml) the "ID_CPV" element
     */
    public void xsetIDCPV(org.apache.xmlbeans.XmlString idcpv)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(IDCPV$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(IDCPV$10);
            }
            target.set(idcpv);
        }
    }
    
    /**
     * Gets the "aggiudicatario" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicatari getAggiudicatario()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicatari target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicatari)get_store().find_element_user(AGGIUDICATARIO$12, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "aggiudicatario" element
     */
    public void setAggiudicatario(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicatari aggiudicatario)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicatari target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicatari)get_store().find_element_user(AGGIUDICATARIO$12, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicatari)get_store().add_element_user(AGGIUDICATARIO$12);
            }
            target.set(aggiudicatario);
        }
    }
    
    /**
     * Appends and returns a new empty "aggiudicatario" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicatari addNewAggiudicatario()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicatari target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicatari)get_store().add_element_user(AGGIUDICATARIO$12);
            return target;
        }
    }
    
    /**
     * Gets the "arch3_impagg" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicatari getArch3Impagg()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicatari target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicatari)get_store().find_element_user(ARCH3IMPAGG$14, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "arch3_impagg" element
     */
    public boolean isSetArch3Impagg()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(ARCH3IMPAGG$14) != 0;
        }
    }
    
    /**
     * Sets the "arch3_impagg" element
     */
    public void setArch3Impagg(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicatari arch3Impagg)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicatari target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicatari)get_store().find_element_user(ARCH3IMPAGG$14, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicatari)get_store().add_element_user(ARCH3IMPAGG$14);
            }
            target.set(arch3Impagg);
        }
    }
    
    /**
     * Appends and returns a new empty "arch3_impagg" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicatari addNewArch3Impagg()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicatari target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicatari)get_store().add_element_user(ARCH3IMPAGG$14);
            return target;
        }
    }
    
    /**
     * Unsets the "arch3_impagg" element
     */
    public void unsetArch3Impagg()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(ARCH3IMPAGG$14, 0);
        }
    }
}
