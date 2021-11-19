/*
 * XML Type:  ListaDatiAccordiBonari
 * Namespace: https://appaltiliguria.regione.liguria.it/pubblica/webservices/types
 * Java type: it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiAccordiBonari
 *
 * Automatically generated - do not modify.
 */
package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.impl;
/**
 * An XML ListaDatiAccordiBonari(@https://appaltiliguria.regione.liguria.it/pubblica/webservices/types).
 *
 * This is a complex type.
 */
public class ListaDatiAccordiBonariImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiAccordiBonari
{
    
    public ListaDatiAccordiBonariImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName IDDATIACCORDIBONARI$0 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "Id_DatiAccordiBonari");
    
    
    /**
     * Gets array of all "Id_DatiAccordiBonari" elements
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAccordo[] getIdDatiAccordiBonariArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(IDDATIACCORDIBONARI$0, targetList);
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAccordo[] result = new it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAccordo[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "Id_DatiAccordiBonari" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAccordo getIdDatiAccordiBonariArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAccordo target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAccordo)get_store().find_element_user(IDDATIACCORDIBONARI$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "Id_DatiAccordiBonari" element
     */
    public int sizeOfIdDatiAccordiBonariArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(IDDATIACCORDIBONARI$0);
        }
    }
    
    /**
     * Sets array of all "Id_DatiAccordiBonari" element
     */
    public void setIdDatiAccordiBonariArray(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAccordo[] idDatiAccordiBonariArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(idDatiAccordiBonariArray, IDDATIACCORDIBONARI$0);
        }
    }
    
    /**
     * Sets ith "Id_DatiAccordiBonari" element
     */
    public void setIdDatiAccordiBonariArray(int i, it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAccordo idDatiAccordiBonari)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAccordo target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAccordo)get_store().find_element_user(IDDATIACCORDIBONARI$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(idDatiAccordiBonari);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "Id_DatiAccordiBonari" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAccordo insertNewIdDatiAccordiBonari(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAccordo target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAccordo)get_store().insert_element_user(IDDATIACCORDIBONARI$0, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "Id_DatiAccordiBonari" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAccordo addNewIdDatiAccordiBonari()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAccordo target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAccordo)get_store().add_element_user(IDDATIACCORDIBONARI$0);
            return target;
        }
    }
    
    /**
     * Removes the ith "Id_DatiAccordiBonari" element
     */
    public void removeIdDatiAccordiBonari(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(IDDATIACCORDIBONARI$0, i);
        }
    }
}
