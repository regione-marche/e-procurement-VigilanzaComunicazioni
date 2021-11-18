/**
 * FeedbackType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.toscana.rete.rfc.sitatort;


/**
 * Feedback
 */
public class FeedbackType  implements java.io.Serializable {
    /* Data di produzione feedback */
    private java.util.Calendar data;

    /* Scheda a cui il feedback riferisce */
    private it.toscana.rete.rfc.sitatort.FaseType scheda;

    /* Progressivo della scheda a cui il feedback riferisce */
    private int num;

    /* Esito dell'operazione di invio della scheda */
    private it.toscana.rete.rfc.sitatort.EsitoType esito;

    /* Eventuale messaggio di errore */
    private java.lang.String[] messaggio;

    public FeedbackType() {
    }

    public FeedbackType(
           java.util.Calendar data,
           it.toscana.rete.rfc.sitatort.FaseType scheda,
           int num,
           it.toscana.rete.rfc.sitatort.EsitoType esito,
           java.lang.String[] messaggio) {
           this.data = data;
           this.scheda = scheda;
           this.num = num;
           this.esito = esito;
           this.messaggio = messaggio;
    }


    /**
     * Gets the data value for this FeedbackType.
     * 
     * @return data   * Data di produzione feedback
     */
    public java.util.Calendar getData() {
        return data;
    }


    /**
     * Sets the data value for this FeedbackType.
     * 
     * @param data   * Data di produzione feedback
     */
    public void setData(java.util.Calendar data) {
        this.data = data;
    }


    /**
     * Gets the scheda value for this FeedbackType.
     * 
     * @return scheda   * Scheda a cui il feedback riferisce
     */
    public it.toscana.rete.rfc.sitatort.FaseType getScheda() {
        return scheda;
    }


    /**
     * Sets the scheda value for this FeedbackType.
     * 
     * @param scheda   * Scheda a cui il feedback riferisce
     */
    public void setScheda(it.toscana.rete.rfc.sitatort.FaseType scheda) {
        this.scheda = scheda;
    }


    /**
     * Gets the num value for this FeedbackType.
     * 
     * @return num   * Progressivo della scheda a cui il feedback riferisce
     */
    public int getNum() {
        return num;
    }


    /**
     * Sets the num value for this FeedbackType.
     * 
     * @param num   * Progressivo della scheda a cui il feedback riferisce
     */
    public void setNum(int num) {
        this.num = num;
    }


    /**
     * Gets the esito value for this FeedbackType.
     * 
     * @return esito   * Esito dell'operazione di invio della scheda
     */
    public it.toscana.rete.rfc.sitatort.EsitoType getEsito() {
        return esito;
    }


    /**
     * Sets the esito value for this FeedbackType.
     * 
     * @param esito   * Esito dell'operazione di invio della scheda
     */
    public void setEsito(it.toscana.rete.rfc.sitatort.EsitoType esito) {
        this.esito = esito;
    }


    /**
     * Gets the messaggio value for this FeedbackType.
     * 
     * @return messaggio   * Eventuale messaggio di errore
     */
    public java.lang.String[] getMessaggio() {
        return messaggio;
    }


    /**
     * Sets the messaggio value for this FeedbackType.
     * 
     * @param messaggio   * Eventuale messaggio di errore
     */
    public void setMessaggio(java.lang.String[] messaggio) {
        this.messaggio = messaggio;
    }

    public java.lang.String getMessaggio(int i) {
        return this.messaggio[i];
    }

    public void setMessaggio(int i, java.lang.String _value) {
        this.messaggio[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FeedbackType)) return false;
        FeedbackType other = (FeedbackType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.data==null && other.getData()==null) || 
             (this.data!=null &&
              this.data.equals(other.getData()))) &&
            ((this.scheda==null && other.getScheda()==null) || 
             (this.scheda!=null &&
              this.scheda.equals(other.getScheda()))) &&
            this.num == other.getNum() &&
            ((this.esito==null && other.getEsito()==null) || 
             (this.esito!=null &&
              this.esito.equals(other.getEsito()))) &&
            ((this.messaggio==null && other.getMessaggio()==null) || 
             (this.messaggio!=null &&
              java.util.Arrays.equals(this.messaggio, other.getMessaggio())));
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
        if (getData() != null) {
            _hashCode += getData().hashCode();
        }
        if (getScheda() != null) {
            _hashCode += getScheda().hashCode();
        }
        _hashCode += getNum();
        if (getEsito() != null) {
            _hashCode += getEsito().hashCode();
        }
        if (getMessaggio() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getMessaggio());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getMessaggio(), i);
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
        new org.apache.axis.description.TypeDesc(FeedbackType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://rete.toscana.it/rfc/sitatort/", "FeedbackType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("data");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Data"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("scheda");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Scheda"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://rete.toscana.it/rfc/sitatort/", "FaseType"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("num");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Num"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("esito");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Esito"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://rete.toscana.it/rfc/sitatort/", "EsitoType"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("messaggio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Messaggio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
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
