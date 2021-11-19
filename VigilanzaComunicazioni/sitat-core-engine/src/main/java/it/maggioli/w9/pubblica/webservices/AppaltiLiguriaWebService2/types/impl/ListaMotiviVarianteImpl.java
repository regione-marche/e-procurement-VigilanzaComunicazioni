/*
 * XML Type:  ListaMotiviVariante
 * Namespace: https://appaltiliguria.regione.liguria.it/pubblica/webservices/types
 * Java type: it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaMotiviVariante
 *
 * Automatically generated - do not modify.
 */
package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.impl;
/**
 * An XML ListaMotiviVariante(@https://appaltiliguria.regione.liguria.it/pubblica/webservices/types).
 *
 * This is a complex type.
 */
public class ListaMotiviVarianteImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaMotiviVariante
{
    
    public ListaMotiviVarianteImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName IDMOTIVIVARIANTE$0 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "Id_MotiviVariante");
    
    
    /**
     * Gets array of all "Id_MotiviVariante" elements
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.MotiviVariante[] getIdMotiviVarianteArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(IDMOTIVIVARIANTE$0, targetList);
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.MotiviVariante[] result = new it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.MotiviVariante[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "Id_MotiviVariante" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.MotiviVariante getIdMotiviVarianteArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.MotiviVariante target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.MotiviVariante)get_store().find_element_user(IDMOTIVIVARIANTE$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "Id_MotiviVariante" element
     */
    public int sizeOfIdMotiviVarianteArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(IDMOTIVIVARIANTE$0);
        }
    }
    
    /**
     * Sets array of all "Id_MotiviVariante" element
     */
    public void setIdMotiviVarianteArray(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.MotiviVariante[] idMotiviVarianteArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(idMotiviVarianteArray, IDMOTIVIVARIANTE$0);
        }
    }
    
    /**
     * Sets ith "Id_MotiviVariante" element
     */
    public void setIdMotiviVarianteArray(int i, it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.MotiviVariante idMotiviVariante)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.MotiviVariante target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.MotiviVariante)get_store().find_element_user(IDMOTIVIVARIANTE$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(idMotiviVariante);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "Id_MotiviVariante" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.MotiviVariante insertNewIdMotiviVariante(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.MotiviVariante target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.MotiviVariante)get_store().insert_element_user(IDMOTIVIVARIANTE$0, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "Id_MotiviVariante" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.MotiviVariante addNewIdMotiviVariante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.MotiviVariante target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.MotiviVariante)get_store().add_element_user(IDMOTIVIVARIANTE$0);
            return target;
        }
    }
    
    /**
     * Removes the ith "Id_MotiviVariante" element
     */
    public void removeIdMotiviVariante(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(IDMOTIVIVARIANTE$0, i);
        }
    }
}
