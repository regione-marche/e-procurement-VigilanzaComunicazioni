/**
 * CUPWSBindingSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.eldasoft.cup.ws;

public class CUPWSBindingSkeleton implements it.eldasoft.cup.ws.CUPWS, org.apache.axis.wsdl.Skeleton {
    private it.eldasoft.cup.ws.CUPWS impl;
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
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "login"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "password"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "codein"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.cup.eldasoft.it/", "_16stringType"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "datiCUP"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.cup.eldasoft.it/", "datiCUP"), it.eldasoft.cup.ws.DatiCUP.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("inserisciCUP", _params, new javax.xml.namespace.QName("", "esito"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://ws.cup.eldasoft.it/", "esitoInserisciCUP"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://ws.cup.eldasoft.it/", "inserisciCUP"));
        _oper.setSoapAction("http://ws.cup.eldasoft.it/inserisciCUP");
        _myOperationsList.add(_oper);
        if (_myOperations.get("inserisciCUP") == null) {
            _myOperations.put("inserisciCUP", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("inserisciCUP")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "uuid"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("consultaCUP", _params, new javax.xml.namespace.QName("", "esito"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://ws.cup.eldasoft.it/", "esitoConsultaCUP"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://ws.cup.eldasoft.it/", "consultaCUP"));
        _oper.setSoapAction("http://ws.cup.eldasoft.it/consultaCUP");
        _myOperationsList.add(_oper);
        if (_myOperations.get("consultaCUP") == null) {
            _myOperations.put("consultaCUP", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("consultaCUP")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("listaStazioniAppaltanti", _params, new javax.xml.namespace.QName("", "stazioniAppaltanti"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://ws.cup.eldasoft.it/", "stazioneAppaltanteType"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://ws.cup.eldasoft.it/", "listaStazioniAppaltanti"));
        _oper.setSoapAction("http://ws.cup.eldasoft.it/listaStazioneAppaltanti");
        _myOperationsList.add(_oper);
        if (_myOperations.get("listaStazioniAppaltanti") == null) {
            _myOperations.put("listaStazioniAppaltanti", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("listaStazioniAppaltanti")).add(_oper);
    }

    public CUPWSBindingSkeleton() {
        this.impl = new it.eldasoft.cup.ws.CUPWSBindingImpl();
    }

    public CUPWSBindingSkeleton(it.eldasoft.cup.ws.CUPWS impl) {
        this.impl = impl;
    }
    public it.eldasoft.cup.ws.EsitoInserisciCUP inserisciCUP(java.lang.String login, java.lang.String password, java.lang.String codein, it.eldasoft.cup.ws.DatiCUP datiCUP) throws java.rmi.RemoteException
    {
        it.eldasoft.cup.ws.EsitoInserisciCUP ret = impl.inserisciCUP(login, password, codein, datiCUP);
        return ret;
    }

    public it.eldasoft.cup.ws.EsitoConsultaCUP consultaCUP(java.lang.String uuid) throws java.rmi.RemoteException
    {
        it.eldasoft.cup.ws.EsitoConsultaCUP ret = impl.consultaCUP(uuid);
        return ret;
    }

    public it.eldasoft.cup.ws.StazioneAppaltanteType[] listaStazioniAppaltanti(java.lang.String in) throws java.rmi.RemoteException
    {
        it.eldasoft.cup.ws.StazioneAppaltanteType[] ret = impl.listaStazioniAppaltanti(in);
        return ret;
    }

}
