/**
 * SitatSoapBindingSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.toscana.rete.rfc.sitatort;

public class SitatSoapBindingSkeleton implements it.toscana.rete.rfc.sitatort.WsOsservatorio_PortType, org.apache.axis.wsdl.Skeleton {
    private it.toscana.rete.rfc.sitatort.WsOsservatorio_PortType impl;
    private static java.util.Map _myOperations = new java.util.Hashtable();
    private static java.util.Collection _myOperationsList = new java.util.ArrayList();

    /**
    * Returns List of OperationDesc objects with this name
    */
    public static java.util.List getOperationDescByName(java.lang.String methodName) {
        return (java.util.List)_myOperations.get(methodName);
    }

    /**
    * Returns Collection of OperationDescs
    */
    public static java.util.Collection getOperationDescs() {
        return _myOperationsList;
    }

    static {
        org.apache.axis.description.OperationDesc _oper;
        org.apache.axis.description.FaultDesc _fault;
        org.apache.axis.description.ParameterDesc [] _params;
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "codiceCIG"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "idGara"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "cfrup"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "cfsa"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "controlliPreliminari"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"), boolean.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("getGaraXML", _params, new javax.xml.namespace.QName("", "garaXML"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://rete.toscana.it/rfc/sitatort/", "ResponseConsultaGara"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://rete.toscana.it/rfc/sitatort/", "getGaraXML"));
        _oper.setSoapAction("http://rete.toscana.it/rfc/sitatort/getGaraXML");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getGaraXML") == null) {
            _myOperations.put("getGaraXML", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getGaraXML")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "cig"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "idgara"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "cfrup"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "cfsa"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "tipoFeedBack"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://rete.toscana.it/rfc/sitatort/", "TipoFeedbackType"), it.toscana.rete.rfc.sitatort.TipoFeedbackType.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "faseEsecuzione"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://rete.toscana.it/rfc/sitatort/", "FaseEsecuzioneType"), it.toscana.rete.rfc.sitatort.FaseEsecuzioneType.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("getElencoFeedback", _params, new javax.xml.namespace.QName("", "elencoFeedback"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://rete.toscana.it/rfc/sitatort/", "ResponseElencoFeedback"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://rete.toscana.it/rfc/sitatort/", "getElencoFeedback"));
        _oper.setSoapAction("http://rete.toscana.it/rfc/sitatort/getElencoFeedback");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getElencoFeedback") == null) {
            _myOperations.put("getElencoFeedback", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getElencoFeedback")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "cig"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "idgara"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "cfrup"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "cfsa"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("getElencoSchede", _params, new javax.xml.namespace.QName("", "elencoSchede"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://rete.toscana.it/rfc/sitatort/", "ResponseElencoSchedeType"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://rete.toscana.it/rfc/sitatort/", "getElencoSchede"));
        _oper.setSoapAction("http://rete.toscana.it/rfc/sitatort/getElencoSchede");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getElencoSchede") == null) {
            _myOperations.put("getElencoSchede", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getElencoSchede")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "cig"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "idgara"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "cfrup"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "cfsa"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "faseEsecuzione"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://rete.toscana.it/rfc/sitatort/", "FaseEsecuzioneType"), it.toscana.rete.rfc.sitatort.FaseEsecuzioneType.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("getScheda", _params, new javax.xml.namespace.QName("", "scheda"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://rete.toscana.it/rfc/sitatort/", "ResponseSchedaType"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://rete.toscana.it/rfc/sitatort/", "getScheda"));
        _oper.setSoapAction("http://rete.toscana.it/rfc/sitatort/getScheda");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getScheda") == null) {
            _myOperations.put("getScheda", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getScheda")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "idAvGara"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "cfRup"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "indiceColl"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("presaInCaricoGaraDelegata", _params, new javax.xml.namespace.QName("", "esitoPresaInCaricoGaraDelegata"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://rete.toscana.it/rfc/sitatort/", "ResponsePresaInCaricoGaraDelegata"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://rete.toscana.it/rfc/sitatort/", "presaInCaricoGaraDelegata"));
        _oper.setSoapAction("http://rete.toscana.it/rfc/sitatort/presaInCaricoGaraDelegata");
        _myOperationsList.add(_oper);
        if (_myOperations.get("presaInCaricoGaraDelegata") == null) {
            _myOperations.put("presaInCaricoGaraDelegata", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("presaInCaricoGaraDelegata")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "cfRup"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("getLoginRPNT", _params, new javax.xml.namespace.QName("", "loginRPNT"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://rete.toscana.it/rfc/sitatort/", "ResponseLoginRPNT"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://rete.toscana.it/rfc/sitatort/", "getLoginRPNT"));
        _oper.setSoapAction("http://rete.toscana.it/rfc/sitatort/getLoginRPNT");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getLoginRPNT") == null) {
            _myOperations.put("getLoginRPNT", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getLoginRPNT")).add(_oper);
    }

    public SitatSoapBindingSkeleton() {
        this.impl = new it.toscana.rete.rfc.sitatort.SitatSoapBindingImpl();
    }

    public SitatSoapBindingSkeleton(it.toscana.rete.rfc.sitatort.WsOsservatorio_PortType impl) {
        this.impl = impl;
    }
    public it.toscana.rete.rfc.sitatort.ResponseConsultaGara getGaraXML(java.lang.String codiceCIG, java.lang.String idGara, java.lang.String cfrup, java.lang.String cfsa, boolean controlliPreliminari) throws java.rmi.RemoteException
    {
        it.toscana.rete.rfc.sitatort.ResponseConsultaGara ret = impl.getGaraXML(codiceCIG, idGara, cfrup, cfsa, controlliPreliminari);
        return ret;
    }

    public it.toscana.rete.rfc.sitatort.ResponseElencoFeedback getElencoFeedback(java.lang.String cig, java.lang.String idgara, java.lang.String cfrup, java.lang.String cfsa, it.toscana.rete.rfc.sitatort.TipoFeedbackType tipoFeedBack, it.toscana.rete.rfc.sitatort.FaseEsecuzioneType faseEsecuzione) throws java.rmi.RemoteException
    {
        it.toscana.rete.rfc.sitatort.ResponseElencoFeedback ret = impl.getElencoFeedback(cig, idgara, cfrup, cfsa, tipoFeedBack, faseEsecuzione);
        return ret;
    }

    public it.toscana.rete.rfc.sitatort.ResponseElencoSchedeType getElencoSchede(java.lang.String cig, java.lang.String idgara, java.lang.String cfrup, java.lang.String cfsa) throws java.rmi.RemoteException
    {
        it.toscana.rete.rfc.sitatort.ResponseElencoSchedeType ret = impl.getElencoSchede(cig, idgara, cfrup, cfsa);
        return ret;
    }

    public it.toscana.rete.rfc.sitatort.ResponseSchedaType getScheda(java.lang.String cig, java.lang.String idgara, java.lang.String cfrup, java.lang.String cfsa, it.toscana.rete.rfc.sitatort.FaseEsecuzioneType faseEsecuzione) throws java.rmi.RemoteException
    {
        it.toscana.rete.rfc.sitatort.ResponseSchedaType ret = impl.getScheda(cig, idgara, cfrup, cfsa, faseEsecuzione);
        return ret;
    }

    public it.toscana.rete.rfc.sitatort.ResponsePresaInCaricoGaraDelegata presaInCaricoGaraDelegata(java.lang.String idAvGara, java.lang.String cfRup, java.lang.String indiceColl) throws java.rmi.RemoteException
    {
        it.toscana.rete.rfc.sitatort.ResponsePresaInCaricoGaraDelegata ret = impl.presaInCaricoGaraDelegata(idAvGara, cfRup, indiceColl);
        return ret;
    }

    public it.toscana.rete.rfc.sitatort.ResponseLoginRPNT getLoginRPNT(java.lang.String cfRup) throws java.rmi.RemoteException
    {
        it.toscana.rete.rfc.sitatort.ResponseLoginRPNT ret = impl.getLoginRPNT(cfRup);
        return ret;
    }

}
