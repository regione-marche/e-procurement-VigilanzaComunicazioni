/*
 * XML Type:  DatiCollaudo
 * Namespace: https://appaltiliguria.regione.liguria.it/pubblica/webservices/types
 * Java type: it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiCollaudo
 *
 * Automatically generated - do not modify.
 */
package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types;


/**
 * An XML DatiCollaudo(@https://appaltiliguria.regione.liguria.it/pubblica/webservices/types).
 *
 * This is a complex type.
 */
public interface DatiCollaudo extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(DatiCollaudo.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s9B544F4075EA19B46E708810C26CE1A4").resolveHandle("daticollaudoa65etype");
    
    /**
     * Gets the "FLAG_SINGOLO_COMMISSIONE" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSingoloCommissioneType.Enum getFLAGSINGOLOCOMMISSIONE();
    
    /**
     * Gets (as xml) the "FLAG_SINGOLO_COMMISSIONE" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSingoloCommissioneType xgetFLAGSINGOLOCOMMISSIONE();
    
    /**
     * Sets the "FLAG_SINGOLO_COMMISSIONE" element
     */
    void setFLAGSINGOLOCOMMISSIONE(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSingoloCommissioneType.Enum flagsingolocommissione);
    
    /**
     * Sets (as xml) the "FLAG_SINGOLO_COMMISSIONE" element
     */
    void xsetFLAGSINGOLOCOMMISSIONE(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSingoloCommissioneType flagsingolocommissione);
    
    /**
     * Gets the "DATA_APPROVAZIONE" element
     */
    java.util.Calendar getDATAAPPROVAZIONE();
    
    /**
     * Gets (as xml) the "DATA_APPROVAZIONE" element
     */
    org.apache.xmlbeans.XmlDateTime xgetDATAAPPROVAZIONE();
    
    /**
     * Sets the "DATA_APPROVAZIONE" element
     */
    void setDATAAPPROVAZIONE(java.util.Calendar dataapprovazione);
    
    /**
     * Sets (as xml) the "DATA_APPROVAZIONE" element
     */
    void xsetDATAAPPROVAZIONE(org.apache.xmlbeans.XmlDateTime dataapprovazione);
    
    /**
     * Gets the "FLAG_IMPORTI" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagImportiType.Enum getFLAGIMPORTI();
    
    /**
     * Gets (as xml) the "FLAG_IMPORTI" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagImportiType xgetFLAGIMPORTI();
    
    /**
     * Sets the "FLAG_IMPORTI" element
     */
    void setFLAGIMPORTI(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagImportiType.Enum flagimporti);
    
    /**
     * Sets (as xml) the "FLAG_IMPORTI" element
     */
    void xsetFLAGIMPORTI(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagImportiType flagimporti);
    
    /**
     * Gets the "FLAG_SICUREZZA" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagImportiType.Enum getFLAGSICUREZZA();
    
    /**
     * Gets (as xml) the "FLAG_SICUREZZA" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagImportiType xgetFLAGSICUREZZA();
    
    /**
     * Sets the "FLAG_SICUREZZA" element
     */
    void setFLAGSICUREZZA(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagImportiType.Enum flagsicurezza);
    
    /**
     * Sets (as xml) the "FLAG_SICUREZZA" element
     */
    void xsetFLAGSICUREZZA(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagImportiType flagsicurezza);
    
    /**
     * Gets the "DATA_INIZIO_AMM_RISERVE" element
     */
    java.util.Calendar getDATAINIZIOAMMRISERVE();
    
    /**
     * Gets (as xml) the "DATA_INIZIO_AMM_RISERVE" element
     */
    org.apache.xmlbeans.XmlDateTime xgetDATAINIZIOAMMRISERVE();
    
    /**
     * True if has "DATA_INIZIO_AMM_RISERVE" element
     */
    boolean isSetDATAINIZIOAMMRISERVE();
    
    /**
     * Sets the "DATA_INIZIO_AMM_RISERVE" element
     */
    void setDATAINIZIOAMMRISERVE(java.util.Calendar datainizioammriserve);
    
    /**
     * Sets (as xml) the "DATA_INIZIO_AMM_RISERVE" element
     */
    void xsetDATAINIZIOAMMRISERVE(org.apache.xmlbeans.XmlDateTime datainizioammriserve);
    
    /**
     * Unsets the "DATA_INIZIO_AMM_RISERVE" element
     */
    void unsetDATAINIZIOAMMRISERVE();
    
    /**
     * Gets the "DATA_FINE_AMM_RISERVE" element
     */
    java.util.Calendar getDATAFINEAMMRISERVE();
    
    /**
     * Gets (as xml) the "DATA_FINE_AMM_RISERVE" element
     */
    org.apache.xmlbeans.XmlDateTime xgetDATAFINEAMMRISERVE();
    
    /**
     * True if has "DATA_FINE_AMM_RISERVE" element
     */
    boolean isSetDATAFINEAMMRISERVE();
    
    /**
     * Sets the "DATA_FINE_AMM_RISERVE" element
     */
    void setDATAFINEAMMRISERVE(java.util.Calendar datafineammriserve);
    
    /**
     * Sets (as xml) the "DATA_FINE_AMM_RISERVE" element
     */
    void xsetDATAFINEAMMRISERVE(org.apache.xmlbeans.XmlDateTime datafineammriserve);
    
    /**
     * Unsets the "DATA_FINE_AMM_RISERVE" element
     */
    void unsetDATAFINEAMMRISERVE();
    
    /**
     * Gets the "ONERI_AMM_RISERVE" element
     */
    double getONERIAMMRISERVE();
    
    /**
     * Gets (as xml) the "ONERI_AMM_RISERVE" element
     */
    org.apache.xmlbeans.XmlDouble xgetONERIAMMRISERVE();
    
    /**
     * True if has "ONERI_AMM_RISERVE" element
     */
    boolean isSetONERIAMMRISERVE();
    
    /**
     * Sets the "ONERI_AMM_RISERVE" element
     */
    void setONERIAMMRISERVE(double oneriammriserve);
    
    /**
     * Sets (as xml) the "ONERI_AMM_RISERVE" element
     */
    void xsetONERIAMMRISERVE(org.apache.xmlbeans.XmlDouble oneriammriserve);
    
    /**
     * Unsets the "ONERI_AMM_RISERVE" element
     */
    void unsetONERIAMMRISERVE();
    
    /**
     * Gets the "DATA_INIZIO_ARB_RISERVE" element
     */
    java.util.Calendar getDATAINIZIOARBRISERVE();
    
    /**
     * Gets (as xml) the "DATA_INIZIO_ARB_RISERVE" element
     */
    org.apache.xmlbeans.XmlDateTime xgetDATAINIZIOARBRISERVE();
    
    /**
     * True if has "DATA_INIZIO_ARB_RISERVE" element
     */
    boolean isSetDATAINIZIOARBRISERVE();
    
    /**
     * Sets the "DATA_INIZIO_ARB_RISERVE" element
     */
    void setDATAINIZIOARBRISERVE(java.util.Calendar datainizioarbriserve);
    
    /**
     * Sets (as xml) the "DATA_INIZIO_ARB_RISERVE" element
     */
    void xsetDATAINIZIOARBRISERVE(org.apache.xmlbeans.XmlDateTime datainizioarbriserve);
    
    /**
     * Unsets the "DATA_INIZIO_ARB_RISERVE" element
     */
    void unsetDATAINIZIOARBRISERVE();
    
    /**
     * Gets the "DATA_FINE_ARB_RISERVE" element
     */
    java.util.Calendar getDATAFINEARBRISERVE();
    
    /**
     * Gets (as xml) the "DATA_FINE_ARB_RISERVE" element
     */
    org.apache.xmlbeans.XmlDateTime xgetDATAFINEARBRISERVE();
    
    /**
     * True if has "DATA_FINE_ARB_RISERVE" element
     */
    boolean isSetDATAFINEARBRISERVE();
    
    /**
     * Sets the "DATA_FINE_ARB_RISERVE" element
     */
    void setDATAFINEARBRISERVE(java.util.Calendar datafinearbriserve);
    
    /**
     * Sets (as xml) the "DATA_FINE_ARB_RISERVE" element
     */
    void xsetDATAFINEARBRISERVE(org.apache.xmlbeans.XmlDateTime datafinearbriserve);
    
    /**
     * Unsets the "DATA_FINE_ARB_RISERVE" element
     */
    void unsetDATAFINEARBRISERVE();
    
    /**
     * Gets the "ONERI_ARB_RISERVE" element
     */
    double getONERIARBRISERVE();
    
    /**
     * Gets (as xml) the "ONERI_ARB_RISERVE" element
     */
    org.apache.xmlbeans.XmlDouble xgetONERIARBRISERVE();
    
    /**
     * True if has "ONERI_ARB_RISERVE" element
     */
    boolean isSetONERIARBRISERVE();
    
    /**
     * Sets the "ONERI_ARB_RISERVE" element
     */
    void setONERIARBRISERVE(double oneriarbriserve);
    
    /**
     * Sets (as xml) the "ONERI_ARB_RISERVE" element
     */
    void xsetONERIARBRISERVE(org.apache.xmlbeans.XmlDouble oneriarbriserve);
    
    /**
     * Unsets the "ONERI_ARB_RISERVE" element
     */
    void unsetONERIARBRISERVE();
    
    /**
     * Gets the "DATA_INIZIO_GIU_RISERVE" element
     */
    java.util.Calendar getDATAINIZIOGIURISERVE();
    
    /**
     * Gets (as xml) the "DATA_INIZIO_GIU_RISERVE" element
     */
    org.apache.xmlbeans.XmlDateTime xgetDATAINIZIOGIURISERVE();
    
    /**
     * True if has "DATA_INIZIO_GIU_RISERVE" element
     */
    boolean isSetDATAINIZIOGIURISERVE();
    
    /**
     * Sets the "DATA_INIZIO_GIU_RISERVE" element
     */
    void setDATAINIZIOGIURISERVE(java.util.Calendar datainiziogiuriserve);
    
    /**
     * Sets (as xml) the "DATA_INIZIO_GIU_RISERVE" element
     */
    void xsetDATAINIZIOGIURISERVE(org.apache.xmlbeans.XmlDateTime datainiziogiuriserve);
    
    /**
     * Unsets the "DATA_INIZIO_GIU_RISERVE" element
     */
    void unsetDATAINIZIOGIURISERVE();
    
    /**
     * Gets the "DATA_FINE_GIU_RISERVE" element
     */
    java.util.Calendar getDATAFINEGIURISERVE();
    
    /**
     * Gets (as xml) the "DATA_FINE_GIU_RISERVE" element
     */
    org.apache.xmlbeans.XmlDateTime xgetDATAFINEGIURISERVE();
    
    /**
     * True if has "DATA_FINE_GIU_RISERVE" element
     */
    boolean isSetDATAFINEGIURISERVE();
    
    /**
     * Sets the "DATA_FINE_GIU_RISERVE" element
     */
    void setDATAFINEGIURISERVE(java.util.Calendar datafinegiuriserve);
    
    /**
     * Sets (as xml) the "DATA_FINE_GIU_RISERVE" element
     */
    void xsetDATAFINEGIURISERVE(org.apache.xmlbeans.XmlDateTime datafinegiuriserve);
    
    /**
     * Unsets the "DATA_FINE_GIU_RISERVE" element
     */
    void unsetDATAFINEGIURISERVE();
    
    /**
     * Gets the "ONERI_GIU_RISERVE" element
     */
    double getONERIGIURISERVE();
    
    /**
     * Gets (as xml) the "ONERI_GIU_RISERVE" element
     */
    org.apache.xmlbeans.XmlDouble xgetONERIGIURISERVE();
    
    /**
     * True if has "ONERI_GIU_RISERVE" element
     */
    boolean isSetONERIGIURISERVE();
    
    /**
     * Sets the "ONERI_GIU_RISERVE" element
     */
    void setONERIGIURISERVE(double onerigiuriserve);
    
    /**
     * Sets (as xml) the "ONERI_GIU_RISERVE" element
     */
    void xsetONERIGIURISERVE(org.apache.xmlbeans.XmlDouble onerigiuriserve);
    
    /**
     * Unsets the "ONERI_GIU_RISERVE" element
     */
    void unsetONERIGIURISERVE();
    
    /**
     * Gets the "DATA_INIZIO_TRA_RISERVE" element
     */
    java.util.Calendar getDATAINIZIOTRARISERVE();
    
    /**
     * Gets (as xml) the "DATA_INIZIO_TRA_RISERVE" element
     */
    org.apache.xmlbeans.XmlDateTime xgetDATAINIZIOTRARISERVE();
    
    /**
     * True if has "DATA_INIZIO_TRA_RISERVE" element
     */
    boolean isSetDATAINIZIOTRARISERVE();
    
    /**
     * Sets the "DATA_INIZIO_TRA_RISERVE" element
     */
    void setDATAINIZIOTRARISERVE(java.util.Calendar datainiziotrariserve);
    
    /**
     * Sets (as xml) the "DATA_INIZIO_TRA_RISERVE" element
     */
    void xsetDATAINIZIOTRARISERVE(org.apache.xmlbeans.XmlDateTime datainiziotrariserve);
    
    /**
     * Unsets the "DATA_INIZIO_TRA_RISERVE" element
     */
    void unsetDATAINIZIOTRARISERVE();
    
    /**
     * Gets the "DATA_FINE_TRA_RISERVE" element
     */
    java.util.Calendar getDATAFINETRARISERVE();
    
    /**
     * Gets (as xml) the "DATA_FINE_TRA_RISERVE" element
     */
    org.apache.xmlbeans.XmlDateTime xgetDATAFINETRARISERVE();
    
    /**
     * True if has "DATA_FINE_TRA_RISERVE" element
     */
    boolean isSetDATAFINETRARISERVE();
    
    /**
     * Sets the "DATA_FINE_TRA_RISERVE" element
     */
    void setDATAFINETRARISERVE(java.util.Calendar datafinetrariserve);
    
    /**
     * Sets (as xml) the "DATA_FINE_TRA_RISERVE" element
     */
    void xsetDATAFINETRARISERVE(org.apache.xmlbeans.XmlDateTime datafinetrariserve);
    
    /**
     * Unsets the "DATA_FINE_TRA_RISERVE" element
     */
    void unsetDATAFINETRARISERVE();
    
    /**
     * Gets the "ONERI_TRA_RISERVE" element
     */
    double getONERITRARISERVE();
    
    /**
     * Gets (as xml) the "ONERI_TRA_RISERVE" element
     */
    org.apache.xmlbeans.XmlDouble xgetONERITRARISERVE();
    
    /**
     * True if has "ONERI_TRA_RISERVE" element
     */
    boolean isSetONERITRARISERVE();
    
    /**
     * Sets the "ONERI_TRA_RISERVE" element
     */
    void setONERITRARISERVE(double oneritrariserve);
    
    /**
     * Sets (as xml) the "ONERI_TRA_RISERVE" element
     */
    void xsetONERITRARISERVE(org.apache.xmlbeans.XmlDouble oneritrariserve);
    
    /**
     * Unsets the "ONERI_TRA_RISERVE" element
     */
    void unsetONERITRARISERVE();
    
    /**
     * Gets the "FLAG_SUBAPPALTATORI" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum getFLAGSUBAPPALTATORI();
    
    /**
     * Gets (as xml) the "FLAG_SUBAPPALTATORI" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType xgetFLAGSUBAPPALTATORI();
    
    /**
     * Sets the "FLAG_SUBAPPALTATORI" element
     */
    void setFLAGSUBAPPALTATORI(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum flagsubappaltatori);
    
    /**
     * Sets (as xml) the "FLAG_SUBAPPALTATORI" element
     */
    void xsetFLAGSUBAPPALTATORI(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType flagsubappaltatori);
    
    /**
     * Gets the "DATA_REGOLARE_ESEC" element
     */
    java.util.Calendar getDATAREGOLAREESEC();
    
    /**
     * Gets (as xml) the "DATA_REGOLARE_ESEC" element
     */
    org.apache.xmlbeans.XmlDateTime xgetDATAREGOLAREESEC();
    
    /**
     * True if has "DATA_REGOLARE_ESEC" element
     */
    boolean isSetDATAREGOLAREESEC();
    
    /**
     * Sets the "DATA_REGOLARE_ESEC" element
     */
    void setDATAREGOLAREESEC(java.util.Calendar dataregolareesec);
    
    /**
     * Sets (as xml) the "DATA_REGOLARE_ESEC" element
     */
    void xsetDATAREGOLAREESEC(org.apache.xmlbeans.XmlDateTime dataregolareesec);
    
    /**
     * Unsets the "DATA_REGOLARE_ESEC" element
     */
    void unsetDATAREGOLAREESEC();
    
    /**
     * Gets the "DATA_COLLAUDO_STAT" element
     */
    java.util.Calendar getDATACOLLAUDOSTAT();
    
    /**
     * Gets (as xml) the "DATA_COLLAUDO_STAT" element
     */
    org.apache.xmlbeans.XmlDateTime xgetDATACOLLAUDOSTAT();
    
    /**
     * True if has "DATA_COLLAUDO_STAT" element
     */
    boolean isSetDATACOLLAUDOSTAT();
    
    /**
     * Sets the "DATA_COLLAUDO_STAT" element
     */
    void setDATACOLLAUDOSTAT(java.util.Calendar datacollaudostat);
    
    /**
     * Sets (as xml) the "DATA_COLLAUDO_STAT" element
     */
    void xsetDATACOLLAUDOSTAT(org.apache.xmlbeans.XmlDateTime datacollaudostat);
    
    /**
     * Unsets the "DATA_COLLAUDO_STAT" element
     */
    void unsetDATACOLLAUDOSTAT();
    
    /**
     * Gets the "MODO_COLLAUDO" element
     */
    java.lang.String getMODOCOLLAUDO();
    
    /**
     * Gets (as xml) the "MODO_COLLAUDO" element
     */
    org.apache.xmlbeans.XmlString xgetMODOCOLLAUDO();
    
    /**
     * True if has "MODO_COLLAUDO" element
     */
    boolean isSetMODOCOLLAUDO();
    
    /**
     * Sets the "MODO_COLLAUDO" element
     */
    void setMODOCOLLAUDO(java.lang.String modocollaudo);
    
    /**
     * Sets (as xml) the "MODO_COLLAUDO" element
     */
    void xsetMODOCOLLAUDO(org.apache.xmlbeans.XmlString modocollaudo);
    
    /**
     * Unsets the "MODO_COLLAUDO" element
     */
    void unsetMODOCOLLAUDO();
    
    /**
     * Gets the "DATA_NOMINA_COLL" element
     */
    java.util.Calendar getDATANOMINACOLL();
    
    /**
     * Gets (as xml) the "DATA_NOMINA_COLL" element
     */
    org.apache.xmlbeans.XmlDateTime xgetDATANOMINACOLL();
    
    /**
     * Sets the "DATA_NOMINA_COLL" element
     */
    void setDATANOMINACOLL(java.util.Calendar datanominacoll);
    
    /**
     * Sets (as xml) the "DATA_NOMINA_COLL" element
     */
    void xsetDATANOMINACOLL(org.apache.xmlbeans.XmlDateTime datanominacoll);
    
    /**
     * Gets the "DATA_INIZIO_OPER" element
     */
    java.util.Calendar getDATAINIZIOOPER();
    
    /**
     * Gets (as xml) the "DATA_INIZIO_OPER" element
     */
    org.apache.xmlbeans.XmlDateTime xgetDATAINIZIOOPER();
    
    /**
     * True if has "DATA_INIZIO_OPER" element
     */
    boolean isSetDATAINIZIOOPER();
    
    /**
     * Sets the "DATA_INIZIO_OPER" element
     */
    void setDATAINIZIOOPER(java.util.Calendar datainiziooper);
    
    /**
     * Sets (as xml) the "DATA_INIZIO_OPER" element
     */
    void xsetDATAINIZIOOPER(org.apache.xmlbeans.XmlDateTime datainiziooper);
    
    /**
     * Unsets the "DATA_INIZIO_OPER" element
     */
    void unsetDATAINIZIOOPER();
    
    /**
     * Gets the "DATA_CERT_COLLAUDO" element
     */
    java.util.Calendar getDATACERTCOLLAUDO();
    
    /**
     * Gets (as xml) the "DATA_CERT_COLLAUDO" element
     */
    org.apache.xmlbeans.XmlDateTime xgetDATACERTCOLLAUDO();
    
    /**
     * True if has "DATA_CERT_COLLAUDO" element
     */
    boolean isSetDATACERTCOLLAUDO();
    
    /**
     * Sets the "DATA_CERT_COLLAUDO" element
     */
    void setDATACERTCOLLAUDO(java.util.Calendar datacertcollaudo);
    
    /**
     * Sets (as xml) the "DATA_CERT_COLLAUDO" element
     */
    void xsetDATACERTCOLLAUDO(org.apache.xmlbeans.XmlDateTime datacertcollaudo);
    
    /**
     * Unsets the "DATA_CERT_COLLAUDO" element
     */
    void unsetDATACERTCOLLAUDO();
    
    /**
     * Gets the "DATA_DELIBERA" element
     */
    java.util.Calendar getDATADELIBERA();
    
    /**
     * Gets (as xml) the "DATA_DELIBERA" element
     */
    org.apache.xmlbeans.XmlDateTime xgetDATADELIBERA();
    
    /**
     * True if has "DATA_DELIBERA" element
     */
    boolean isSetDATADELIBERA();
    
    /**
     * Sets the "DATA_DELIBERA" element
     */
    void setDATADELIBERA(java.util.Calendar datadelibera);
    
    /**
     * Sets (as xml) the "DATA_DELIBERA" element
     */
    void xsetDATADELIBERA(org.apache.xmlbeans.XmlDateTime datadelibera);
    
    /**
     * Unsets the "DATA_DELIBERA" element
     */
    void unsetDATADELIBERA();
    
    /**
     * Gets the "ESITO_COLLAUDO" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagEsitoCollaudoType.Enum getESITOCOLLAUDO();
    
    /**
     * Gets (as xml) the "ESITO_COLLAUDO" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagEsitoCollaudoType xgetESITOCOLLAUDO();
    
    /**
     * Sets the "ESITO_COLLAUDO" element
     */
    void setESITOCOLLAUDO(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagEsitoCollaudoType.Enum esitocollaudo);
    
    /**
     * Sets (as xml) the "ESITO_COLLAUDO" element
     */
    void xsetESITOCOLLAUDO(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagEsitoCollaudoType esitocollaudo);
    
    /**
     * Gets the "IMP_FINALE_LAVORI" element
     */
    double getIMPFINALELAVORI();
    
    /**
     * Gets (as xml) the "IMP_FINALE_LAVORI" element
     */
    org.apache.xmlbeans.XmlDouble xgetIMPFINALELAVORI();
    
    /**
     * True if has "IMP_FINALE_LAVORI" element
     */
    boolean isSetIMPFINALELAVORI();
    
    /**
     * Sets the "IMP_FINALE_LAVORI" element
     */
    void setIMPFINALELAVORI(double impfinalelavori);
    
    /**
     * Sets (as xml) the "IMP_FINALE_LAVORI" element
     */
    void xsetIMPFINALELAVORI(org.apache.xmlbeans.XmlDouble impfinalelavori);
    
    /**
     * Unsets the "IMP_FINALE_LAVORI" element
     */
    void unsetIMPFINALELAVORI();
    
    /**
     * Gets the "IMP_FINALE_SERVIZI" element
     */
    double getIMPFINALESERVIZI();
    
    /**
     * Gets (as xml) the "IMP_FINALE_SERVIZI" element
     */
    org.apache.xmlbeans.XmlDouble xgetIMPFINALESERVIZI();
    
    /**
     * True if has "IMP_FINALE_SERVIZI" element
     */
    boolean isSetIMPFINALESERVIZI();
    
    /**
     * Sets the "IMP_FINALE_SERVIZI" element
     */
    void setIMPFINALESERVIZI(double impfinaleservizi);
    
    /**
     * Sets (as xml) the "IMP_FINALE_SERVIZI" element
     */
    void xsetIMPFINALESERVIZI(org.apache.xmlbeans.XmlDouble impfinaleservizi);
    
    /**
     * Unsets the "IMP_FINALE_SERVIZI" element
     */
    void unsetIMPFINALESERVIZI();
    
    /**
     * Gets the "IMP_FINALE_FORNIT" element
     */
    double getIMPFINALEFORNIT();
    
    /**
     * Gets (as xml) the "IMP_FINALE_FORNIT" element
     */
    org.apache.xmlbeans.XmlDouble xgetIMPFINALEFORNIT();
    
    /**
     * True if has "IMP_FINALE_FORNIT" element
     */
    boolean isSetIMPFINALEFORNIT();
    
    /**
     * Sets the "IMP_FINALE_FORNIT" element
     */
    void setIMPFINALEFORNIT(double impfinalefornit);
    
    /**
     * Sets (as xml) the "IMP_FINALE_FORNIT" element
     */
    void xsetIMPFINALEFORNIT(org.apache.xmlbeans.XmlDouble impfinalefornit);
    
    /**
     * Unsets the "IMP_FINALE_FORNIT" element
     */
    void unsetIMPFINALEFORNIT();
    
    /**
     * Gets the "IMP_FINALE_SECUR" element
     */
    double getIMPFINALESECUR();
    
    /**
     * Gets (as xml) the "IMP_FINALE_SECUR" element
     */
    org.apache.xmlbeans.XmlDouble xgetIMPFINALESECUR();
    
    /**
     * True if has "IMP_FINALE_SECUR" element
     */
    boolean isSetIMPFINALESECUR();
    
    /**
     * Sets the "IMP_FINALE_SECUR" element
     */
    void setIMPFINALESECUR(double impfinalesecur);
    
    /**
     * Sets (as xml) the "IMP_FINALE_SECUR" element
     */
    void xsetIMPFINALESECUR(org.apache.xmlbeans.XmlDouble impfinalesecur);
    
    /**
     * Unsets the "IMP_FINALE_SECUR" element
     */
    void unsetIMPFINALESECUR();
    
    /**
     * Gets the "IMP_PROGETTAZIONE" element
     */
    double getIMPPROGETTAZIONE();
    
    /**
     * Gets (as xml) the "IMP_PROGETTAZIONE" element
     */
    org.apache.xmlbeans.XmlDouble xgetIMPPROGETTAZIONE();
    
    /**
     * True if has "IMP_PROGETTAZIONE" element
     */
    boolean isSetIMPPROGETTAZIONE();
    
    /**
     * Sets the "IMP_PROGETTAZIONE" element
     */
    void setIMPPROGETTAZIONE(double impprogettazione);
    
    /**
     * Sets (as xml) the "IMP_PROGETTAZIONE" element
     */
    void xsetIMPPROGETTAZIONE(org.apache.xmlbeans.XmlDouble impprogettazione);
    
    /**
     * Unsets the "IMP_PROGETTAZIONE" element
     */
    void unsetIMPPROGETTAZIONE();
    
    /**
     * Gets the "IMP_DISPOSIZIONE" element
     */
    double getIMPDISPOSIZIONE();
    
    /**
     * Gets (as xml) the "IMP_DISPOSIZIONE" element
     */
    org.apache.xmlbeans.XmlDouble xgetIMPDISPOSIZIONE();
    
    /**
     * Sets the "IMP_DISPOSIZIONE" element
     */
    void setIMPDISPOSIZIONE(double impdisposizione);
    
    /**
     * Sets (as xml) the "IMP_DISPOSIZIONE" element
     */
    void xsetIMPDISPOSIZIONE(org.apache.xmlbeans.XmlDouble impdisposizione);
    
    /**
     * Gets the "AMM_NUM_DEFINITE" element
     */
    long getAMMNUMDEFINITE();
    
    /**
     * Gets (as xml) the "AMM_NUM_DEFINITE" element
     */
    org.apache.xmlbeans.XmlLong xgetAMMNUMDEFINITE();
    
    /**
     * True if has "AMM_NUM_DEFINITE" element
     */
    boolean isSetAMMNUMDEFINITE();
    
    /**
     * Sets the "AMM_NUM_DEFINITE" element
     */
    void setAMMNUMDEFINITE(long ammnumdefinite);
    
    /**
     * Sets (as xml) the "AMM_NUM_DEFINITE" element
     */
    void xsetAMMNUMDEFINITE(org.apache.xmlbeans.XmlLong ammnumdefinite);
    
    /**
     * Unsets the "AMM_NUM_DEFINITE" element
     */
    void unsetAMMNUMDEFINITE();
    
    /**
     * Gets the "AMM_NUM_DADEF" element
     */
    long getAMMNUMDADEF();
    
    /**
     * Gets (as xml) the "AMM_NUM_DADEF" element
     */
    org.apache.xmlbeans.XmlLong xgetAMMNUMDADEF();
    
    /**
     * True if has "AMM_NUM_DADEF" element
     */
    boolean isSetAMMNUMDADEF();
    
    /**
     * Sets the "AMM_NUM_DADEF" element
     */
    void setAMMNUMDADEF(long ammnumdadef);
    
    /**
     * Sets (as xml) the "AMM_NUM_DADEF" element
     */
    void xsetAMMNUMDADEF(org.apache.xmlbeans.XmlLong ammnumdadef);
    
    /**
     * Unsets the "AMM_NUM_DADEF" element
     */
    void unsetAMMNUMDADEF();
    
    /**
     * Gets the "AMM_IMPORTO_RICH" element
     */
    double getAMMIMPORTORICH();
    
    /**
     * Gets (as xml) the "AMM_IMPORTO_RICH" element
     */
    org.apache.xmlbeans.XmlDouble xgetAMMIMPORTORICH();
    
    /**
     * True if has "AMM_IMPORTO_RICH" element
     */
    boolean isSetAMMIMPORTORICH();
    
    /**
     * Sets the "AMM_IMPORTO_RICH" element
     */
    void setAMMIMPORTORICH(double ammimportorich);
    
    /**
     * Sets (as xml) the "AMM_IMPORTO_RICH" element
     */
    void xsetAMMIMPORTORICH(org.apache.xmlbeans.XmlDouble ammimportorich);
    
    /**
     * Unsets the "AMM_IMPORTO_RICH" element
     */
    void unsetAMMIMPORTORICH();
    
    /**
     * Gets the "AMM_IMPORTO_DEF" element
     */
    double getAMMIMPORTODEF();
    
    /**
     * Gets (as xml) the "AMM_IMPORTO_DEF" element
     */
    org.apache.xmlbeans.XmlDouble xgetAMMIMPORTODEF();
    
    /**
     * True if has "AMM_IMPORTO_DEF" element
     */
    boolean isSetAMMIMPORTODEF();
    
    /**
     * Sets the "AMM_IMPORTO_DEF" element
     */
    void setAMMIMPORTODEF(double ammimportodef);
    
    /**
     * Sets (as xml) the "AMM_IMPORTO_DEF" element
     */
    void xsetAMMIMPORTODEF(org.apache.xmlbeans.XmlDouble ammimportodef);
    
    /**
     * Unsets the "AMM_IMPORTO_DEF" element
     */
    void unsetAMMIMPORTODEF();
    
    /**
     * Gets the "ARB_NUM_DEFINITE" element
     */
    long getARBNUMDEFINITE();
    
    /**
     * Gets (as xml) the "ARB_NUM_DEFINITE" element
     */
    org.apache.xmlbeans.XmlLong xgetARBNUMDEFINITE();
    
    /**
     * True if has "ARB_NUM_DEFINITE" element
     */
    boolean isSetARBNUMDEFINITE();
    
    /**
     * Sets the "ARB_NUM_DEFINITE" element
     */
    void setARBNUMDEFINITE(long arbnumdefinite);
    
    /**
     * Sets (as xml) the "ARB_NUM_DEFINITE" element
     */
    void xsetARBNUMDEFINITE(org.apache.xmlbeans.XmlLong arbnumdefinite);
    
    /**
     * Unsets the "ARB_NUM_DEFINITE" element
     */
    void unsetARBNUMDEFINITE();
    
    /**
     * Gets the "ARB_NUM_DADEF" element
     */
    long getARBNUMDADEF();
    
    /**
     * Gets (as xml) the "ARB_NUM_DADEF" element
     */
    org.apache.xmlbeans.XmlLong xgetARBNUMDADEF();
    
    /**
     * True if has "ARB_NUM_DADEF" element
     */
    boolean isSetARBNUMDADEF();
    
    /**
     * Sets the "ARB_NUM_DADEF" element
     */
    void setARBNUMDADEF(long arbnumdadef);
    
    /**
     * Sets (as xml) the "ARB_NUM_DADEF" element
     */
    void xsetARBNUMDADEF(org.apache.xmlbeans.XmlLong arbnumdadef);
    
    /**
     * Unsets the "ARB_NUM_DADEF" element
     */
    void unsetARBNUMDADEF();
    
    /**
     * Gets the "ARB_IMPORTO_RICH" element
     */
    double getARBIMPORTORICH();
    
    /**
     * Gets (as xml) the "ARB_IMPORTO_RICH" element
     */
    org.apache.xmlbeans.XmlDouble xgetARBIMPORTORICH();
    
    /**
     * True if has "ARB_IMPORTO_RICH" element
     */
    boolean isSetARBIMPORTORICH();
    
    /**
     * Sets the "ARB_IMPORTO_RICH" element
     */
    void setARBIMPORTORICH(double arbimportorich);
    
    /**
     * Sets (as xml) the "ARB_IMPORTO_RICH" element
     */
    void xsetARBIMPORTORICH(org.apache.xmlbeans.XmlDouble arbimportorich);
    
    /**
     * Unsets the "ARB_IMPORTO_RICH" element
     */
    void unsetARBIMPORTORICH();
    
    /**
     * Gets the "ARB_IMPORTO_DEF" element
     */
    double getARBIMPORTODEF();
    
    /**
     * Gets (as xml) the "ARB_IMPORTO_DEF" element
     */
    org.apache.xmlbeans.XmlDouble xgetARBIMPORTODEF();
    
    /**
     * True if has "ARB_IMPORTO_DEF" element
     */
    boolean isSetARBIMPORTODEF();
    
    /**
     * Sets the "ARB_IMPORTO_DEF" element
     */
    void setARBIMPORTODEF(double arbimportodef);
    
    /**
     * Sets (as xml) the "ARB_IMPORTO_DEF" element
     */
    void xsetARBIMPORTODEF(org.apache.xmlbeans.XmlDouble arbimportodef);
    
    /**
     * Unsets the "ARB_IMPORTO_DEF" element
     */
    void unsetARBIMPORTODEF();
    
    /**
     * Gets the "GIU_NUM_DEFINITE" element
     */
    long getGIUNUMDEFINITE();
    
    /**
     * Gets (as xml) the "GIU_NUM_DEFINITE" element
     */
    org.apache.xmlbeans.XmlLong xgetGIUNUMDEFINITE();
    
    /**
     * True if has "GIU_NUM_DEFINITE" element
     */
    boolean isSetGIUNUMDEFINITE();
    
    /**
     * Sets the "GIU_NUM_DEFINITE" element
     */
    void setGIUNUMDEFINITE(long giunumdefinite);
    
    /**
     * Sets (as xml) the "GIU_NUM_DEFINITE" element
     */
    void xsetGIUNUMDEFINITE(org.apache.xmlbeans.XmlLong giunumdefinite);
    
    /**
     * Unsets the "GIU_NUM_DEFINITE" element
     */
    void unsetGIUNUMDEFINITE();
    
    /**
     * Gets the "GIU_NUM_DADEF" element
     */
    long getGIUNUMDADEF();
    
    /**
     * Gets (as xml) the "GIU_NUM_DADEF" element
     */
    org.apache.xmlbeans.XmlLong xgetGIUNUMDADEF();
    
    /**
     * True if has "GIU_NUM_DADEF" element
     */
    boolean isSetGIUNUMDADEF();
    
    /**
     * Sets the "GIU_NUM_DADEF" element
     */
    void setGIUNUMDADEF(long giunumdadef);
    
    /**
     * Sets (as xml) the "GIU_NUM_DADEF" element
     */
    void xsetGIUNUMDADEF(org.apache.xmlbeans.XmlLong giunumdadef);
    
    /**
     * Unsets the "GIU_NUM_DADEF" element
     */
    void unsetGIUNUMDADEF();
    
    /**
     * Gets the "GIU_IMPORTO_RICH" element
     */
    double getGIUIMPORTORICH();
    
    /**
     * Gets (as xml) the "GIU_IMPORTO_RICH" element
     */
    org.apache.xmlbeans.XmlDouble xgetGIUIMPORTORICH();
    
    /**
     * True if has "GIU_IMPORTO_RICH" element
     */
    boolean isSetGIUIMPORTORICH();
    
    /**
     * Sets the "GIU_IMPORTO_RICH" element
     */
    void setGIUIMPORTORICH(double giuimportorich);
    
    /**
     * Sets (as xml) the "GIU_IMPORTO_RICH" element
     */
    void xsetGIUIMPORTORICH(org.apache.xmlbeans.XmlDouble giuimportorich);
    
    /**
     * Unsets the "GIU_IMPORTO_RICH" element
     */
    void unsetGIUIMPORTORICH();
    
    /**
     * Gets the "GIU_IMPORTO_DEF" element
     */
    double getGIUIMPORTODEF();
    
    /**
     * Gets (as xml) the "GIU_IMPORTO_DEF" element
     */
    org.apache.xmlbeans.XmlDouble xgetGIUIMPORTODEF();
    
    /**
     * True if has "GIU_IMPORTO_DEF" element
     */
    boolean isSetGIUIMPORTODEF();
    
    /**
     * Sets the "GIU_IMPORTO_DEF" element
     */
    void setGIUIMPORTODEF(double giuimportodef);
    
    /**
     * Sets (as xml) the "GIU_IMPORTO_DEF" element
     */
    void xsetGIUIMPORTODEF(org.apache.xmlbeans.XmlDouble giuimportodef);
    
    /**
     * Unsets the "GIU_IMPORTO_DEF" element
     */
    void unsetGIUIMPORTODEF();
    
    /**
     * Gets the "TRA_NUM_DEFINITE" element
     */
    long getTRANUMDEFINITE();
    
    /**
     * Gets (as xml) the "TRA_NUM_DEFINITE" element
     */
    org.apache.xmlbeans.XmlLong xgetTRANUMDEFINITE();
    
    /**
     * True if has "TRA_NUM_DEFINITE" element
     */
    boolean isSetTRANUMDEFINITE();
    
    /**
     * Sets the "TRA_NUM_DEFINITE" element
     */
    void setTRANUMDEFINITE(long tranumdefinite);
    
    /**
     * Sets (as xml) the "TRA_NUM_DEFINITE" element
     */
    void xsetTRANUMDEFINITE(org.apache.xmlbeans.XmlLong tranumdefinite);
    
    /**
     * Unsets the "TRA_NUM_DEFINITE" element
     */
    void unsetTRANUMDEFINITE();
    
    /**
     * Gets the "TRA_NUM_DADEF" element
     */
    long getTRANUMDADEF();
    
    /**
     * Gets (as xml) the "TRA_NUM_DADEF" element
     */
    org.apache.xmlbeans.XmlLong xgetTRANUMDADEF();
    
    /**
     * True if has "TRA_NUM_DADEF" element
     */
    boolean isSetTRANUMDADEF();
    
    /**
     * Sets the "TRA_NUM_DADEF" element
     */
    void setTRANUMDADEF(long tranumdadef);
    
    /**
     * Sets (as xml) the "TRA_NUM_DADEF" element
     */
    void xsetTRANUMDADEF(org.apache.xmlbeans.XmlLong tranumdadef);
    
    /**
     * Unsets the "TRA_NUM_DADEF" element
     */
    void unsetTRANUMDADEF();
    
    /**
     * Gets the "TRA_IMPORTO_RICH" element
     */
    double getTRAIMPORTORICH();
    
    /**
     * Gets (as xml) the "TRA_IMPORTO_RICH" element
     */
    org.apache.xmlbeans.XmlDouble xgetTRAIMPORTORICH();
    
    /**
     * True if has "TRA_IMPORTO_RICH" element
     */
    boolean isSetTRAIMPORTORICH();
    
    /**
     * Sets the "TRA_IMPORTO_RICH" element
     */
    void setTRAIMPORTORICH(double traimportorich);
    
    /**
     * Sets (as xml) the "TRA_IMPORTO_RICH" element
     */
    void xsetTRAIMPORTORICH(org.apache.xmlbeans.XmlDouble traimportorich);
    
    /**
     * Unsets the "TRA_IMPORTO_RICH" element
     */
    void unsetTRAIMPORTORICH();
    
    /**
     * Gets the "TRA_IMPORTO_DEF" element
     */
    double getTRAIMPORTODEF();
    
    /**
     * Gets (as xml) the "TRA_IMPORTO_DEF" element
     */
    org.apache.xmlbeans.XmlDouble xgetTRAIMPORTODEF();
    
    /**
     * True if has "TRA_IMPORTO_DEF" element
     */
    boolean isSetTRAIMPORTODEF();
    
    /**
     * Sets the "TRA_IMPORTO_DEF" element
     */
    void setTRAIMPORTODEF(double traimportodef);
    
    /**
     * Sets (as xml) the "TRA_IMPORTO_DEF" element
     */
    void xsetTRAIMPORTODEF(org.apache.xmlbeans.XmlDouble traimportodef);
    
    /**
     * Unsets the "TRA_IMPORTO_DEF" element
     */
    void unsetTRAIMPORTODEF();
    
    /**
     * Gets the "IMPORTO_SUBTOTALE" element
     */
    double getIMPORTOSUBTOTALE();
    
    /**
     * Gets (as xml) the "IMPORTO_SUBTOTALE" element
     */
    org.apache.xmlbeans.XmlDouble xgetIMPORTOSUBTOTALE();
    
    /**
     * Sets the "IMPORTO_SUBTOTALE" element
     */
    void setIMPORTOSUBTOTALE(double importosubtotale);
    
    /**
     * Sets (as xml) the "IMPORTO_SUBTOTALE" element
     */
    void xsetIMPORTOSUBTOTALE(org.apache.xmlbeans.XmlDouble importosubtotale);
    
    /**
     * Gets the "IMPORTO_FINALE" element
     */
    double getIMPORTOFINALE();
    
    /**
     * Gets (as xml) the "IMPORTO_FINALE" element
     */
    org.apache.xmlbeans.XmlDouble xgetIMPORTOFINALE();
    
    /**
     * Sets the "IMPORTO_FINALE" element
     */
    void setIMPORTOFINALE(double importofinale);
    
    /**
     * Sets (as xml) the "IMPORTO_FINALE" element
     */
    void xsetIMPORTOFINALE(org.apache.xmlbeans.XmlDouble importofinale);
    
    /**
     * Gets the "IMPORTO_CONSUNTIVO" element
     */
    double getIMPORTOCONSUNTIVO();
    
    /**
     * Gets (as xml) the "IMPORTO_CONSUNTIVO" element
     */
    org.apache.xmlbeans.XmlDouble xgetIMPORTOCONSUNTIVO();
    
    /**
     * Sets the "IMPORTO_CONSUNTIVO" element
     */
    void setIMPORTOCONSUNTIVO(double importoconsuntivo);
    
    /**
     * Sets (as xml) the "IMPORTO_CONSUNTIVO" element
     */
    void xsetIMPORTOCONSUNTIVO(org.apache.xmlbeans.XmlDouble importoconsuntivo);
    
    /**
     * Gets the "FLAG_LAVORI_ESTESI" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum getFLAGLAVORIESTESI();
    
    /**
     * Gets (as xml) the "FLAG_LAVORI_ESTESI" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType xgetFLAGLAVORIESTESI();
    
    /**
     * True if has "FLAG_LAVORI_ESTESI" element
     */
    boolean isSetFLAGLAVORIESTESI();
    
    /**
     * Sets the "FLAG_LAVORI_ESTESI" element
     */
    void setFLAGLAVORIESTESI(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum flaglavoriestesi);
    
    /**
     * Sets (as xml) the "FLAG_LAVORI_ESTESI" element
     */
    void xsetFLAGLAVORIESTESI(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType flaglavoriestesi);
    
    /**
     * Unsets the "FLAG_LAVORI_ESTESI" element
     */
    void unsetFLAGLAVORIESTESI();
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiCollaudo newInstance() {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiCollaudo) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiCollaudo newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiCollaudo) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiCollaudo parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiCollaudo) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiCollaudo parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiCollaudo) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiCollaudo parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiCollaudo) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiCollaudo parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiCollaudo) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiCollaudo parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiCollaudo) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiCollaudo parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiCollaudo) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiCollaudo parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiCollaudo) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiCollaudo parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiCollaudo) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiCollaudo parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiCollaudo) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiCollaudo parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiCollaudo) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiCollaudo parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiCollaudo) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiCollaudo parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiCollaudo) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiCollaudo parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiCollaudo) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiCollaudo parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiCollaudo) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiCollaudo parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiCollaudo) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiCollaudo parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiCollaudo) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
