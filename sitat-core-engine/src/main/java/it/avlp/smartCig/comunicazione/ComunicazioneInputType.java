/**
 * ComunicazioneInputType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.avlp.smartCig.comunicazione;

public class ComunicazioneInputType  implements java.io.Serializable {
    private java.lang.String cig;

    private java.lang.String codiceFattispecieContrattuale;

    private java.math.BigDecimal importo;

    private java.lang.String oggetto;

    private java.lang.String codiceProceduraSceltaContraente;

    private java.lang.String codiceClassificazioneGara;

    private java.lang.String cigAccordoQuadro;

    private java.lang.String cup;

    private java.lang.String motivo_rich_cig_comuni;

    private java.lang.String motivo_rich_cig_catmerc;

    private java.lang.String[] categorie_merc;

    public ComunicazioneInputType() {
    }

    public ComunicazioneInputType(
           java.lang.String cig,
           java.lang.String codiceFattispecieContrattuale,
           java.math.BigDecimal importo,
           java.lang.String oggetto,
           java.lang.String codiceProceduraSceltaContraente,
           java.lang.String codiceClassificazioneGara,
           java.lang.String cigAccordoQuadro,
           java.lang.String cup,
           java.lang.String motivo_rich_cig_comuni,
           java.lang.String motivo_rich_cig_catmerc,
           java.lang.String[] categorie_merc) {
           this.cig = cig;
           this.codiceFattispecieContrattuale = codiceFattispecieContrattuale;
           this.importo = importo;
           this.oggetto = oggetto;
           this.codiceProceduraSceltaContraente = codiceProceduraSceltaContraente;
           this.codiceClassificazioneGara = codiceClassificazioneGara;
           this.cigAccordoQuadro = cigAccordoQuadro;
           this.cup = cup;
           this.motivo_rich_cig_comuni = motivo_rich_cig_comuni;
           this.motivo_rich_cig_catmerc = motivo_rich_cig_catmerc;
           this.categorie_merc = categorie_merc;
    }


    /**
     * Gets the cig value for this ComunicazioneInputType.
     * 
     * @return cig
     */
    public java.lang.String getCig() {
        return cig;
    }


    /**
     * Sets the cig value for this ComunicazioneInputType.
     * 
     * @param cig
     */
    public void setCig(java.lang.String cig) {
        this.cig = cig;
    }


    /**
     * Gets the codiceFattispecieContrattuale value for this ComunicazioneInputType.
     * 
     * @return codiceFattispecieContrattuale
     */
    public java.lang.String getCodiceFattispecieContrattuale() {
        return codiceFattispecieContrattuale;
    }


    /**
     * Sets the codiceFattispecieContrattuale value for this ComunicazioneInputType.
     * 
     * @param codiceFattispecieContrattuale
     */
    public void setCodiceFattispecieContrattuale(java.lang.String codiceFattispecieContrattuale) {
        this.codiceFattispecieContrattuale = codiceFattispecieContrattuale;
    }


    /**
     * Gets the importo value for this ComunicazioneInputType.
     * 
     * @return importo
     */
    public java.math.BigDecimal getImporto() {
        return importo;
    }


    /**
     * Sets the importo value for this ComunicazioneInputType.
     * 
     * @param importo
     */
    public void setImporto(java.math.BigDecimal importo) {
        this.importo = importo;
    }


    /**
     * Gets the oggetto value for this ComunicazioneInputType.
     * 
     * @return oggetto
     */
    public java.lang.String getOggetto() {
        return oggetto;
    }


    /**
     * Sets the oggetto value for this ComunicazioneInputType.
     * 
     * @param oggetto
     */
    public void setOggetto(java.lang.String oggetto) {
        this.oggetto = oggetto;
    }


    /**
     * Gets the codiceProceduraSceltaContraente value for this ComunicazioneInputType.
     * 
     * @return codiceProceduraSceltaContraente
     */
    public java.lang.String getCodiceProceduraSceltaContraente() {
        return codiceProceduraSceltaContraente;
    }


    /**
     * Sets the codiceProceduraSceltaContraente value for this ComunicazioneInputType.
     * 
     * @param codiceProceduraSceltaContraente
     */
    public void setCodiceProceduraSceltaContraente(java.lang.String codiceProceduraSceltaContraente) {
        this.codiceProceduraSceltaContraente = codiceProceduraSceltaContraente;
    }


    /**
     * Gets the codiceClassificazioneGara value for this ComunicazioneInputType.
     * 
     * @return codiceClassificazioneGara
     */
    public java.lang.String getCodiceClassificazioneGara() {
        return codiceClassificazioneGara;
    }


    /**
     * Sets the codiceClassificazioneGara value for this ComunicazioneInputType.
     * 
     * @param codiceClassificazioneGara
     */
    public void setCodiceClassificazioneGara(java.lang.String codiceClassificazioneGara) {
        this.codiceClassificazioneGara = codiceClassificazioneGara;
    }


    /**
     * Gets the cigAccordoQuadro value for this ComunicazioneInputType.
     * 
     * @return cigAccordoQuadro
     */
    public java.lang.String getCigAccordoQuadro() {
        return cigAccordoQuadro;
    }


    /**
     * Sets the cigAccordoQuadro value for this ComunicazioneInputType.
     * 
     * @param cigAccordoQuadro
     */
    public void setCigAccordoQuadro(java.lang.String cigAccordoQuadro) {
        this.cigAccordoQuadro = cigAccordoQuadro;
    }


    /**
     * Gets the cup value for this ComunicazioneInputType.
     * 
     * @return cup
     */
    public java.lang.String getCup() {
        return cup;
    }


    /**
     * Sets the cup value for this ComunicazioneInputType.
     * 
     * @param cup
     */
    public void setCup(java.lang.String cup) {
        this.cup = cup;
    }


    /**
     * Gets the motivo_rich_cig_comuni value for this ComunicazioneInputType.
     * 
     * @return motivo_rich_cig_comuni
     */
    public java.lang.String getMotivo_rich_cig_comuni() {
        return motivo_rich_cig_comuni;
    }


    /**
     * Sets the motivo_rich_cig_comuni value for this ComunicazioneInputType.
     * 
     * @param motivo_rich_cig_comuni
     */
    public void setMotivo_rich_cig_comuni(java.lang.String motivo_rich_cig_comuni) {
        this.motivo_rich_cig_comuni = motivo_rich_cig_comuni;
    }


    /**
     * Gets the motivo_rich_cig_catmerc value for this ComunicazioneInputType.
     * 
     * @return motivo_rich_cig_catmerc
     */
    public java.lang.String getMotivo_rich_cig_catmerc() {
        return motivo_rich_cig_catmerc;
    }


    /**
     * Sets the motivo_rich_cig_catmerc value for this ComunicazioneInputType.
     * 
     * @param motivo_rich_cig_catmerc
     */
    public void setMotivo_rich_cig_catmerc(java.lang.String motivo_rich_cig_catmerc) {
        this.motivo_rich_cig_catmerc = motivo_rich_cig_catmerc;
    }


    /**
     * Gets the categorie_merc value for this ComunicazioneInputType.
     * 
     * @return categorie_merc
     */
    public java.lang.String[] getCategorie_merc() {
        return categorie_merc;
    }


    /**
     * Sets the categorie_merc value for this ComunicazioneInputType.
     * 
     * @param categorie_merc
     */
    public void setCategorie_merc(java.lang.String[] categorie_merc) {
        this.categorie_merc = categorie_merc;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ComunicazioneInputType)) return false;
        ComunicazioneInputType other = (ComunicazioneInputType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.cig==null && other.getCig()==null) || 
             (this.cig!=null &&
              this.cig.equals(other.getCig()))) &&
            ((this.codiceFattispecieContrattuale==null && other.getCodiceFattispecieContrattuale()==null) || 
             (this.codiceFattispecieContrattuale!=null &&
              this.codiceFattispecieContrattuale.equals(other.getCodiceFattispecieContrattuale()))) &&
            ((this.importo==null && other.getImporto()==null) || 
             (this.importo!=null &&
              this.importo.equals(other.getImporto()))) &&
            ((this.oggetto==null && other.getOggetto()==null) || 
             (this.oggetto!=null &&
              this.oggetto.equals(other.getOggetto()))) &&
            ((this.codiceProceduraSceltaContraente==null && other.getCodiceProceduraSceltaContraente()==null) || 
             (this.codiceProceduraSceltaContraente!=null &&
              this.codiceProceduraSceltaContraente.equals(other.getCodiceProceduraSceltaContraente()))) &&
            ((this.codiceClassificazioneGara==null && other.getCodiceClassificazioneGara()==null) || 
             (this.codiceClassificazioneGara!=null &&
              this.codiceClassificazioneGara.equals(other.getCodiceClassificazioneGara()))) &&
            ((this.cigAccordoQuadro==null && other.getCigAccordoQuadro()==null) || 
             (this.cigAccordoQuadro!=null &&
              this.cigAccordoQuadro.equals(other.getCigAccordoQuadro()))) &&
            ((this.cup==null && other.getCup()==null) || 
             (this.cup!=null &&
              this.cup.equals(other.getCup()))) &&
            ((this.motivo_rich_cig_comuni==null && other.getMotivo_rich_cig_comuni()==null) || 
             (this.motivo_rich_cig_comuni!=null &&
              this.motivo_rich_cig_comuni.equals(other.getMotivo_rich_cig_comuni()))) &&
            ((this.motivo_rich_cig_catmerc==null && other.getMotivo_rich_cig_catmerc()==null) || 
             (this.motivo_rich_cig_catmerc!=null &&
              this.motivo_rich_cig_catmerc.equals(other.getMotivo_rich_cig_catmerc()))) &&
            ((this.categorie_merc==null && other.getCategorie_merc()==null) || 
             (this.categorie_merc!=null &&
              java.util.Arrays.equals(this.categorie_merc, other.getCategorie_merc())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getCig() != null) {
            _hashCode += getCig().hashCode();
        }
        if (getCodiceFattispecieContrattuale() != null) {
            _hashCode += getCodiceFattispecieContrattuale().hashCode();
        }
        if (getImporto() != null) {
            _hashCode += getImporto().hashCode();
        }
        if (getOggetto() != null) {
            _hashCode += getOggetto().hashCode();
        }
        if (getCodiceProceduraSceltaContraente() != null) {
            _hashCode += getCodiceProceduraSceltaContraente().hashCode();
        }
        if (getCodiceClassificazioneGara() != null) {
            _hashCode += getCodiceClassificazioneGara().hashCode();
        }
        if (getCigAccordoQuadro() != null) {
            _hashCode += getCigAccordoQuadro().hashCode();
        }
        if (getCup() != null) {
            _hashCode += getCup().hashCode();
        }
        if (getMotivo_rich_cig_comuni() != null) {
            _hashCode += getMotivo_rich_cig_comuni().hashCode();
        }
        if (getMotivo_rich_cig_catmerc() != null) {
            _hashCode += getMotivo_rich_cig_catmerc().hashCode();
        }
        if (getCategorie_merc() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCategorie_merc());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCategorie_merc(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ComunicazioneInputType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("smartCig.comunicazione", "ComunicazioneInputType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cig");
        elemField.setXmlName(new javax.xml.namespace.QName("smartCig.comunicazione", "cig"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceFattispecieContrattuale");
        elemField.setXmlName(new javax.xml.namespace.QName("smartCig.comunicazione", "codiceFattispecieContrattuale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("importo");
        elemField.setXmlName(new javax.xml.namespace.QName("smartCig.comunicazione", "importo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oggetto");
        elemField.setXmlName(new javax.xml.namespace.QName("smartCig.comunicazione", "oggetto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceProceduraSceltaContraente");
        elemField.setXmlName(new javax.xml.namespace.QName("smartCig.comunicazione", "codiceProceduraSceltaContraente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceClassificazioneGara");
        elemField.setXmlName(new javax.xml.namespace.QName("smartCig.comunicazione", "codiceClassificazioneGara"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cigAccordoQuadro");
        elemField.setXmlName(new javax.xml.namespace.QName("smartCig.comunicazione", "cigAccordoQuadro"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cup");
        elemField.setXmlName(new javax.xml.namespace.QName("smartCig.comunicazione", "cup"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("motivo_rich_cig_comuni");
        elemField.setXmlName(new javax.xml.namespace.QName("smartCig.comunicazione", "motivo_rich_cig_comuni"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("motivo_rich_cig_catmerc");
        elemField.setXmlName(new javax.xml.namespace.QName("smartCig.comunicazione", "motivo_rich_cig_catmerc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("categorie_merc");
        elemField.setXmlName(new javax.xml.namespace.QName("smartCig.comunicazione", "categorie_merc"));
        elemField.setXmlType(new javax.xml.namespace.QName("smartCig.comunicazione", ">ElencoCategMercType>categoria"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("smartCig.comunicazione", "categoria"));
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
