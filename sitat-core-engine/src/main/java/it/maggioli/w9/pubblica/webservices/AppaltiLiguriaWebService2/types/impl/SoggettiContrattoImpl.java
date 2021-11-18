/*
 * XML Type:  SoggettiContratto
 * Namespace: https://appaltiliguria.regione.liguria.it/pubblica/webservices/types
 * Java type: it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SoggettiContratto
 *
 * Automatically generated - do not modify.
 */
package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.impl;
/**
 * An XML SoggettiContratto(@https://appaltiliguria.regione.liguria.it/pubblica/webservices/types).
 *
 * This is a complex type.
 */
public class SoggettiContrattoImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SoggettiContratto
{
    
    public SoggettiContrattoImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName LISTAAGGIUDICATARI$0 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "LISTA_AGGIUDICATARI");
    
    
    /**
     * Gets the "LISTA_AGGIUDICATARI" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaAggiudicatari getLISTAAGGIUDICATARI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaAggiudicatari target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaAggiudicatari)get_store().find_element_user(LISTAAGGIUDICATARI$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "LISTA_AGGIUDICATARI" element
     */
    public void setLISTAAGGIUDICATARI(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaAggiudicatari listaaggiudicatari)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaAggiudicatari target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaAggiudicatari)get_store().find_element_user(LISTAAGGIUDICATARI$0, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaAggiudicatari)get_store().add_element_user(LISTAAGGIUDICATARI$0);
            }
            target.set(listaaggiudicatari);
        }
    }
    
    /**
     * Appends and returns a new empty "LISTA_AGGIUDICATARI" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaAggiudicatari addNewLISTAAGGIUDICATARI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaAggiudicatari target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaAggiudicatari)get_store().add_element_user(LISTAAGGIUDICATARI$0);
            return target;
        }
    }
}
