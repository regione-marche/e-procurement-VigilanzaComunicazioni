/*
 * XML Type:  ListaDatiRitardiRecessi
 * Namespace: https://appaltiliguria.regione.liguria.it/pubblica/webservices/types
 * Java type: it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiRitardiRecessi
 *
 * Automatically generated - do not modify.
 */
package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.impl;
/**
 * An XML ListaDatiRitardiRecessi(@https://appaltiliguria.regione.liguria.it/pubblica/webservices/types).
 *
 * This is a complex type.
 */
public class ListaDatiRitardiRecessiImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiRitardiRecessi
{
    
    public ListaDatiRitardiRecessiImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName IDDATIRITARDIRECESSI$0 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "Id_DatiRitardiRecessi");
    
    
    /**
     * Gets array of all "Id_DatiRitardiRecessi" elements
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiRecesso[] getIdDatiRitardiRecessiArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(IDDATIRITARDIRECESSI$0, targetList);
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiRecesso[] result = new it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiRecesso[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "Id_DatiRitardiRecessi" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiRecesso getIdDatiRitardiRecessiArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiRecesso target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiRecesso)get_store().find_element_user(IDDATIRITARDIRECESSI$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "Id_DatiRitardiRecessi" element
     */
    public int sizeOfIdDatiRitardiRecessiArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(IDDATIRITARDIRECESSI$0);
        }
    }
    
    /**
     * Sets array of all "Id_DatiRitardiRecessi" element
     */
    public void setIdDatiRitardiRecessiArray(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiRecesso[] idDatiRitardiRecessiArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(idDatiRitardiRecessiArray, IDDATIRITARDIRECESSI$0);
        }
    }
    
    /**
     * Sets ith "Id_DatiRitardiRecessi" element
     */
    public void setIdDatiRitardiRecessiArray(int i, it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiRecesso idDatiRitardiRecessi)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiRecesso target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiRecesso)get_store().find_element_user(IDDATIRITARDIRECESSI$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(idDatiRitardiRecessi);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "Id_DatiRitardiRecessi" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiRecesso insertNewIdDatiRitardiRecessi(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiRecesso target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiRecesso)get_store().insert_element_user(IDDATIRITARDIRECESSI$0, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "Id_DatiRitardiRecessi" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiRecesso addNewIdDatiRitardiRecessi()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiRecesso target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiRecesso)get_store().add_element_user(IDDATIRITARDIRECESSI$0);
            return target;
        }
    }
    
    /**
     * Removes the ith "Id_DatiRitardiRecessi" element
     */
    public void removeIdDatiRitardiRecessi(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(IDDATIRITARDIRECESSI$0, i);
        }
    }
}
