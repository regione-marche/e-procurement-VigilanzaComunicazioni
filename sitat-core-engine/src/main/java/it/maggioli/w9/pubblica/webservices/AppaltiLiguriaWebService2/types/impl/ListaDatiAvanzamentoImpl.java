/*
 * XML Type:  ListaDatiAvanzamento
 * Namespace: https://appaltiliguria.regione.liguria.it/pubblica/webservices/types
 * Java type: it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiAvanzamento
 *
 * Automatically generated - do not modify.
 */
package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.impl;
/**
 * An XML ListaDatiAvanzamento(@https://appaltiliguria.regione.liguria.it/pubblica/webservices/types).
 *
 * This is a complex type.
 */
public class ListaDatiAvanzamentoImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiAvanzamento
{
    
    public ListaDatiAvanzamentoImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName IDDATIAVANZAMENTO$0 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "Id_DatiAvanzamento");
    
    
    /**
     * Gets array of all "Id_DatiAvanzamento" elements
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAvanzamento[] getIdDatiAvanzamentoArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(IDDATIAVANZAMENTO$0, targetList);
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAvanzamento[] result = new it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAvanzamento[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "Id_DatiAvanzamento" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAvanzamento getIdDatiAvanzamentoArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAvanzamento target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAvanzamento)get_store().find_element_user(IDDATIAVANZAMENTO$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "Id_DatiAvanzamento" element
     */
    public int sizeOfIdDatiAvanzamentoArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(IDDATIAVANZAMENTO$0);
        }
    }
    
    /**
     * Sets array of all "Id_DatiAvanzamento" element
     */
    public void setIdDatiAvanzamentoArray(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAvanzamento[] idDatiAvanzamentoArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(idDatiAvanzamentoArray, IDDATIAVANZAMENTO$0);
        }
    }
    
    /**
     * Sets ith "Id_DatiAvanzamento" element
     */
    public void setIdDatiAvanzamentoArray(int i, it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAvanzamento idDatiAvanzamento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAvanzamento target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAvanzamento)get_store().find_element_user(IDDATIAVANZAMENTO$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(idDatiAvanzamento);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "Id_DatiAvanzamento" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAvanzamento insertNewIdDatiAvanzamento(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAvanzamento target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAvanzamento)get_store().insert_element_user(IDDATIAVANZAMENTO$0, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "Id_DatiAvanzamento" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAvanzamento addNewIdDatiAvanzamento()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAvanzamento target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAvanzamento)get_store().add_element_user(IDDATIAVANZAMENTO$0);
            return target;
        }
    }
    
    /**
     * Removes the ith "Id_DatiAvanzamento" element
     */
    public void removeIdDatiAvanzamento(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(IDDATIAVANZAMENTO$0, i);
        }
    }
}
