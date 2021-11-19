/*
 * XML Type:  ListaCategorie
 * Namespace: https://appaltiliguria.regione.liguria.it/pubblica/webservices/types
 * Java type: it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaCategorie
 *
 * Automatically generated - do not modify.
 */
package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.impl;
/**
 * An XML ListaCategorie(@https://appaltiliguria.regione.liguria.it/pubblica/webservices/types).
 *
 * This is a complex type.
 */
public class ListaCategorieImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaCategorie
{
    
    public ListaCategorieImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName IDCATEGORIE$0 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "Id_Categorie");
    
    
    /**
     * Gets array of all "Id_Categorie" elements
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Categorie[] getIdCategorieArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(IDCATEGORIE$0, targetList);
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Categorie[] result = new it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Categorie[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "Id_Categorie" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Categorie getIdCategorieArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Categorie target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Categorie)get_store().find_element_user(IDCATEGORIE$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "Id_Categorie" element
     */
    public int sizeOfIdCategorieArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(IDCATEGORIE$0);
        }
    }
    
    /**
     * Sets array of all "Id_Categorie" element
     */
    public void setIdCategorieArray(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Categorie[] idCategorieArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(idCategorieArray, IDCATEGORIE$0);
        }
    }
    
    /**
     * Sets ith "Id_Categorie" element
     */
    public void setIdCategorieArray(int i, it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Categorie idCategorie)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Categorie target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Categorie)get_store().find_element_user(IDCATEGORIE$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(idCategorie);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "Id_Categorie" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Categorie insertNewIdCategorie(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Categorie target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Categorie)get_store().insert_element_user(IDCATEGORIE$0, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "Id_Categorie" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Categorie addNewIdCategorie()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Categorie target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Categorie)get_store().add_element_user(IDCATEGORIE$0);
            return target;
        }
    }
    
    /**
     * Removes the ith "Id_Categorie" element
     */
    public void removeIdCategorie(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(IDCATEGORIE$0, i);
        }
    }
}
