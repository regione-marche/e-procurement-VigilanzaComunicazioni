/*
 * XML Type:  ListaDatiSospensione
 * Namespace: https://appaltiliguria.regione.liguria.it/pubblica/webservices/types
 * Java type: it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiSospensione
 *
 * Automatically generated - do not modify.
 */
package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.impl;
/**
 * An XML ListaDatiSospensione(@https://appaltiliguria.regione.liguria.it/pubblica/webservices/types).
 *
 * This is a complex type.
 */
public class ListaDatiSospensioneImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiSospensione
{
    
    public ListaDatiSospensioneImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName IDDATISOSPENSIONE$0 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "Id_DatiSospensione");
    
    
    /**
     * Gets array of all "Id_DatiSospensione" elements
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSospensione[] getIdDatiSospensioneArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(IDDATISOSPENSIONE$0, targetList);
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSospensione[] result = new it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSospensione[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "Id_DatiSospensione" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSospensione getIdDatiSospensioneArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSospensione target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSospensione)get_store().find_element_user(IDDATISOSPENSIONE$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "Id_DatiSospensione" element
     */
    public int sizeOfIdDatiSospensioneArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(IDDATISOSPENSIONE$0);
        }
    }
    
    /**
     * Sets array of all "Id_DatiSospensione" element
     */
    public void setIdDatiSospensioneArray(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSospensione[] idDatiSospensioneArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(idDatiSospensioneArray, IDDATISOSPENSIONE$0);
        }
    }
    
    /**
     * Sets ith "Id_DatiSospensione" element
     */
    public void setIdDatiSospensioneArray(int i, it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSospensione idDatiSospensione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSospensione target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSospensione)get_store().find_element_user(IDDATISOSPENSIONE$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(idDatiSospensione);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "Id_DatiSospensione" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSospensione insertNewIdDatiSospensione(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSospensione target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSospensione)get_store().insert_element_user(IDDATISOSPENSIONE$0, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "Id_DatiSospensione" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSospensione addNewIdDatiSospensione()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSospensione target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSospensione)get_store().add_element_user(IDDATISOSPENSIONE$0);
            return target;
        }
    }
    
    /**
     * Removes the ith "Id_DatiSospensione" element
     */
    public void removeIdDatiSospensione(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(IDDATISOSPENSIONE$0, i);
        }
    }
}
