/**
 * AliceProgrammiSOAPSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.eldasoft.sil.pt.ws.AliceProgrammi;

public class AliceProgrammiSOAPSkeleton implements it.eldasoft.sil.pt.ws.AliceProgrammi.AliceProgrammi_PortType, org.apache.axis.wsdl.Skeleton {
    private it.eldasoft.sil.pt.ws.AliceProgrammi.AliceProgrammi_PortType impl;
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
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "id"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "anno"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "tipo"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("getListaProgrammi", _params, new javax.xml.namespace.QName("", "out"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://ws.pt.sil.eldasoft.it/AliceProgrammi/", "getListaProgrammi"));
        _oper.setSoapAction("http://www.example.org/AliceProgrammi/getListaProgrammi");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getListaProgrammi") == null) {
            _myOperations.put("getListaProgrammi", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getListaProgrammi")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "contri"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("getListaInterventi", _params, new javax.xml.namespace.QName("", "out"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://ws.pt.sil.eldasoft.it/AliceProgrammi/", "getListaInterventi"));
        _oper.setSoapAction("http://www.example.org/AliceProgrammi/getListaInterventi");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getListaInterventi") == null) {
            _myOperations.put("getListaInterventi", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getListaInterventi")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "contri"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "conint"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("getSchedaIntervento", _params, new javax.xml.namespace.QName("", "out"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://ws.pt.sil.eldasoft.it/AliceProgrammi/", "getSchedaIntervento"));
        _oper.setSoapAction("http://www.example.org/AliceProgrammi/getSchedaIntervento");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getSchedaIntervento") == null) {
            _myOperations.put("getSchedaIntervento", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getSchedaIntervento")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "contri"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "out"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "result"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"), boolean.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("setSchedaIntervento", _params, null);
        _oper.setElementQName(new javax.xml.namespace.QName("http://ws.pt.sil.eldasoft.it/AliceProgrammi/", "setSchedaIntervento"));
        _oper.setSoapAction("http://www.example.org/AliceProgrammi/setSchedaIntervento");
        _myOperationsList.add(_oper);
        if (_myOperations.get("setSchedaIntervento") == null) {
            _myOperations.put("setSchedaIntervento", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("setSchedaIntervento")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "login"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "password"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "id"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "xmlProgramma"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("inserisciProgramma", _params, new javax.xml.namespace.QName("", "esito"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://ws.pt.sil.eldasoft.it/AliceProgrammi/", "esitoInserisciProgramma"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://ws.pt.sil.eldasoft.it/AliceProgrammi/", "InserisciProgramma"));
        _oper.setSoapAction("http://www.example.org/AliceProgrammi/InserisciProgramma");
        _myOperationsList.add(_oper);
        if (_myOperations.get("inserisciProgramma") == null) {
            _myOperations.put("inserisciProgramma", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("inserisciProgramma")).add(_oper);
    }

    public AliceProgrammiSOAPSkeleton() {
        this.impl = new it.eldasoft.sil.pt.ws.AliceProgrammi.AliceProgrammiSOAPImpl();
    }

    public AliceProgrammiSOAPSkeleton(it.eldasoft.sil.pt.ws.AliceProgrammi.AliceProgrammi_PortType impl) {
        this.impl = impl;
    }
    public java.lang.String getListaProgrammi(java.lang.String id, int anno, int tipo) throws java.rmi.RemoteException
    {
        java.lang.String ret = impl.getListaProgrammi(id, anno, tipo);
        return ret;
    }

    public java.lang.String getListaInterventi(int contri) throws java.rmi.RemoteException
    {
        java.lang.String ret = impl.getListaInterventi(contri);
        return ret;
    }

    public java.lang.String getSchedaIntervento(int contri, int conint) throws java.rmi.RemoteException
    {
        java.lang.String ret = impl.getSchedaIntervento(contri, conint);
        return ret;
    }

    public void setSchedaIntervento(java.lang.String in, int contri, javax.xml.rpc.holders.StringHolder out, javax.xml.rpc.holders.BooleanHolder result) throws java.rmi.RemoteException
    {
        impl.setSchedaIntervento(in, contri, out, result);
    }

    public it.eldasoft.sil.pt.ws.AliceProgrammi.EsitoInserisciProgramma inserisciProgramma(java.lang.String login, java.lang.String password, java.lang.String id, java.lang.String xmlProgramma) throws java.rmi.RemoteException
    {
        it.eldasoft.sil.pt.ws.AliceProgrammi.EsitoInserisciProgramma ret = impl.inserisciProgramma(login, password, id, xmlProgramma);
        return ret;
    }

}
