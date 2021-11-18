/*
 * XML Type:  ListaAggiudicatari
 * Namespace: https://appaltiliguria.regione.liguria.it/pubblica/webservices/types
 * Java type: it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaAggiudicatari
 *
 * Automatically generated - do not modify.
 */
package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.impl;
/**
 * An XML ListaAggiudicatari(@https://appaltiliguria.regione.liguria.it/pubblica/webservices/types).
 *
 * This is a complex type.
 */
public class ListaAggiudicatariImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaAggiudicatari
{
    
    public ListaAggiudicatariImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName IDAGGIUDICATARI$0 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "Id_Aggiudicatari");
    
    
    /**
     * Gets array of all "Id_Aggiudicatari" elements
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Aggiudicatario[] getIdAggiudicatariArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(IDAGGIUDICATARI$0, targetList);
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Aggiudicatario[] result = new it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Aggiudicatario[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "Id_Aggiudicatari" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Aggiudicatario getIdAggiudicatariArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Aggiudicatario target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Aggiudicatario)get_store().find_element_user(IDAGGIUDICATARI$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "Id_Aggiudicatari" element
     */
    public int sizeOfIdAggiudicatariArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(IDAGGIUDICATARI$0);
        }
    }
    
    /**
     * Sets array of all "Id_Aggiudicatari" element
     */
    public void setIdAggiudicatariArray(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Aggiudicatario[] idAggiudicatariArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(idAggiudicatariArray, IDAGGIUDICATARI$0);
        }
    }
    
    /**
     * Sets ith "Id_Aggiudicatari" element
     */
    public void setIdAggiudicatariArray(int i, it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Aggiudicatario idAggiudicatari)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Aggiudicatario target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Aggiudicatario)get_store().find_element_user(IDAGGIUDICATARI$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(idAggiudicatari);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "Id_Aggiudicatari" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Aggiudicatario insertNewIdAggiudicatari(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Aggiudicatario target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Aggiudicatario)get_store().insert_element_user(IDAGGIUDICATARI$0, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "Id_Aggiudicatari" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Aggiudicatario addNewIdAggiudicatari()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Aggiudicatario target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Aggiudicatario)get_store().add_element_user(IDAGGIUDICATARI$0);
            return target;
        }
    }
    
    /**
     * Removes the ith "Id_Aggiudicatari" element
     */
    public void removeIdAggiudicatari(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(IDAGGIUDICATARI$0, i);
        }
    }
}
