/**
 * SitatSoapBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.toscana.rete.rfc.sitatort;

public class SitatSoapBindingStub extends org.apache.axis.client.Stub implements it.toscana.rete.rfc.sitatort.WsOsservatorio_PortType {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[6];
        _initOperationDesc1();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getGaraXML");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "codiceCIG"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "idGara"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "cfrup"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "cfsa"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "controlliPreliminari"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"), boolean.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://rete.toscana.it/rfc/sitatort/", "ResponseConsultaGara"));
        oper.setReturnClass(it.toscana.rete.rfc.sitatort.ResponseConsultaGara.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "garaXML"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getElencoFeedback");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "cig"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "idgara"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "cfrup"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "cfsa"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "tipoFeedBack"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://rete.toscana.it/rfc/sitatort/", "TipoFeedbackType"), it.toscana.rete.rfc.sitatort.TipoFeedbackType.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "faseEsecuzione"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://rete.toscana.it/rfc/sitatort/", "FaseEsecuzioneType"), it.toscana.rete.rfc.sitatort.FaseEsecuzioneType.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://rete.toscana.it/rfc/sitatort/", "ResponseElencoFeedback"));
        oper.setReturnClass(it.toscana.rete.rfc.sitatort.ResponseElencoFeedback.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "elencoFeedback"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getElencoSchede");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "cig"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "idgara"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "cfrup"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "cfsa"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://rete.toscana.it/rfc/sitatort/", "ResponseElencoSchedeType"));
        oper.setReturnClass(it.toscana.rete.rfc.sitatort.ResponseElencoSchedeType.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "elencoSchede"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getScheda");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "cig"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "idgara"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "cfrup"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "cfsa"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "faseEsecuzione"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://rete.toscana.it/rfc/sitatort/", "FaseEsecuzioneType"), it.toscana.rete.rfc.sitatort.FaseEsecuzioneType.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://rete.toscana.it/rfc/sitatort/", "ResponseSchedaType"));
        oper.setReturnClass(it.toscana.rete.rfc.sitatort.ResponseSchedaType.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "scheda"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("presaInCaricoGaraDelegata");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "idAvGara"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "cfRup"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "indiceColl"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://rete.toscana.it/rfc/sitatort/", "ResponsePresaInCaricoGaraDelegata"));
        oper.setReturnClass(it.toscana.rete.rfc.sitatort.ResponsePresaInCaricoGaraDelegata.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "esitoPresaInCaricoGaraDelegata"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[4] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getLoginRPNT");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "cfRup"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://rete.toscana.it/rfc/sitatort/", "ResponseLoginRPNT"));
        oper.setReturnClass(it.toscana.rete.rfc.sitatort.ResponseLoginRPNT.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "loginRPNT"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[5] = oper;

    }

    public SitatSoapBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public SitatSoapBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public SitatSoapBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        if (service == null) {
            super.service = new org.apache.axis.client.Service();
        } else {
            super.service = service;
        }
        ((org.apache.axis.client.Service)super.service).setTypeMappingVersion("1.2");
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("http://rete.toscana.it/rfc/sitatort/", ">FaseEsecuzioneType>Num");
            cachedSerQNames.add(qName);
            cls = int.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://rete.toscana.it/rfc/sitatort/", "EsitoType");
            cachedSerQNames.add(qName);
            cls = it.toscana.rete.rfc.sitatort.EsitoType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://rete.toscana.it/rfc/sitatort/", "FaseEsecuzioneType");
            cachedSerQNames.add(qName);
            cls = it.toscana.rete.rfc.sitatort.FaseEsecuzioneType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://rete.toscana.it/rfc/sitatort/", "FaseType");
            cachedSerQNames.add(qName);
            cls = it.toscana.rete.rfc.sitatort.FaseType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://rete.toscana.it/rfc/sitatort/", "FeedbackType");
            cachedSerQNames.add(qName);
            cls = it.toscana.rete.rfc.sitatort.FeedbackType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://rete.toscana.it/rfc/sitatort/", "ResponseConsultaGara");
            cachedSerQNames.add(qName);
            cls = it.toscana.rete.rfc.sitatort.ResponseConsultaGara.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://rete.toscana.it/rfc/sitatort/", "ResponseElencoFeedback");
            cachedSerQNames.add(qName);
            cls = it.toscana.rete.rfc.sitatort.ResponseElencoFeedback.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://rete.toscana.it/rfc/sitatort/", "ResponseElencoSchedeType");
            cachedSerQNames.add(qName);
            cls = it.toscana.rete.rfc.sitatort.ResponseElencoSchedeType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://rete.toscana.it/rfc/sitatort/", "ResponseLoginRPNT");
            cachedSerQNames.add(qName);
            cls = it.toscana.rete.rfc.sitatort.ResponseLoginRPNT.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://rete.toscana.it/rfc/sitatort/", "ResponsePresaInCaricoGaraDelegata");
            cachedSerQNames.add(qName);
            cls = it.toscana.rete.rfc.sitatort.ResponsePresaInCaricoGaraDelegata.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://rete.toscana.it/rfc/sitatort/", "ResponseSchedaType");
            cachedSerQNames.add(qName);
            cls = it.toscana.rete.rfc.sitatort.ResponseSchedaType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://rete.toscana.it/rfc/sitatort/", "TipoFeedbackType");
            cachedSerQNames.add(qName);
            cls = it.toscana.rete.rfc.sitatort.TipoFeedbackType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

    }

    protected org.apache.axis.client.Call createCall() throws java.rmi.RemoteException {
        try {
            org.apache.axis.client.Call _call = super._createCall();
            if (super.maintainSessionSet) {
                _call.setMaintainSession(super.maintainSession);
            }
            if (super.cachedUsername != null) {
                _call.setUsername(super.cachedUsername);
            }
            if (super.cachedPassword != null) {
                _call.setPassword(super.cachedPassword);
            }
            if (super.cachedEndpoint != null) {
                _call.setTargetEndpointAddress(super.cachedEndpoint);
            }
            if (super.cachedTimeout != null) {
                _call.setTimeout(super.cachedTimeout);
            }
            if (super.cachedPortName != null) {
                _call.setPortName(super.cachedPortName);
            }
            java.util.Enumeration keys = super.cachedProperties.keys();
            while (keys.hasMoreElements()) {
                java.lang.String key = (java.lang.String) keys.nextElement();
                _call.setProperty(key, super.cachedProperties.get(key));
            }
            // All the type mapping information is registered
            // when the first call is made.
            // The type mapping information is actually registered in
            // the TypeMappingRegistry of the service, which
            // is the reason why registration is only needed for the first call.
            synchronized (this) {
                if (firstCall()) {
                    // must set encoding style before registering serializers
                    _call.setEncodingStyle(null);
                    for (int i = 0; i < cachedSerFactories.size(); ++i) {
                        java.lang.Class cls = (java.lang.Class) cachedSerClasses.get(i);
                        javax.xml.namespace.QName qName =
                                (javax.xml.namespace.QName) cachedSerQNames.get(i);
                        java.lang.Object x = cachedSerFactories.get(i);
                        if (x instanceof Class) {
                            java.lang.Class sf = (java.lang.Class)
                                 cachedSerFactories.get(i);
                            java.lang.Class df = (java.lang.Class)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                        else if (x instanceof javax.xml.rpc.encoding.SerializerFactory) {
                            org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory)
                                 cachedSerFactories.get(i);
                            org.apache.axis.encoding.DeserializerFactory df = (org.apache.axis.encoding.DeserializerFactory)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                    }
                }
            }
            return _call;
        }
        catch (java.lang.Throwable _t) {
            throw new org.apache.axis.AxisFault("Failure trying to get the Call object", _t);
        }
    }

    public it.toscana.rete.rfc.sitatort.ResponseConsultaGara getGaraXML(java.lang.String codiceCIG, java.lang.String idGara, java.lang.String cfrup, java.lang.String cfsa, boolean controlliPreliminari) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://rete.toscana.it/rfc/sitatort/getGaraXML");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://rete.toscana.it/rfc/sitatort/", "getGaraXML"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {codiceCIG, idGara, cfrup, cfsa, new java.lang.Boolean(controlliPreliminari)});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.toscana.rete.rfc.sitatort.ResponseConsultaGara) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.toscana.rete.rfc.sitatort.ResponseConsultaGara) org.apache.axis.utils.JavaUtils.convert(_resp, it.toscana.rete.rfc.sitatort.ResponseConsultaGara.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public it.toscana.rete.rfc.sitatort.ResponseElencoFeedback getElencoFeedback(java.lang.String cig, java.lang.String idgara, java.lang.String cfrup, java.lang.String cfsa, it.toscana.rete.rfc.sitatort.TipoFeedbackType tipoFeedBack, it.toscana.rete.rfc.sitatort.FaseEsecuzioneType faseEsecuzione) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://rete.toscana.it/rfc/sitatort/getElencoFeedback");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://rete.toscana.it/rfc/sitatort/", "getElencoFeedback"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {cig, idgara, cfrup, cfsa, tipoFeedBack, faseEsecuzione});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.toscana.rete.rfc.sitatort.ResponseElencoFeedback) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.toscana.rete.rfc.sitatort.ResponseElencoFeedback) org.apache.axis.utils.JavaUtils.convert(_resp, it.toscana.rete.rfc.sitatort.ResponseElencoFeedback.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public it.toscana.rete.rfc.sitatort.ResponseElencoSchedeType getElencoSchede(java.lang.String cig, java.lang.String idgara, java.lang.String cfrup, java.lang.String cfsa) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://rete.toscana.it/rfc/sitatort/getElencoSchede");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://rete.toscana.it/rfc/sitatort/", "getElencoSchede"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {cig, idgara, cfrup, cfsa});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.toscana.rete.rfc.sitatort.ResponseElencoSchedeType) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.toscana.rete.rfc.sitatort.ResponseElencoSchedeType) org.apache.axis.utils.JavaUtils.convert(_resp, it.toscana.rete.rfc.sitatort.ResponseElencoSchedeType.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public it.toscana.rete.rfc.sitatort.ResponseSchedaType getScheda(java.lang.String cig, java.lang.String idgara, java.lang.String cfrup, java.lang.String cfsa, it.toscana.rete.rfc.sitatort.FaseEsecuzioneType faseEsecuzione) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://rete.toscana.it/rfc/sitatort/getScheda");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://rete.toscana.it/rfc/sitatort/", "getScheda"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {cig, idgara, cfrup, cfsa, faseEsecuzione});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.toscana.rete.rfc.sitatort.ResponseSchedaType) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.toscana.rete.rfc.sitatort.ResponseSchedaType) org.apache.axis.utils.JavaUtils.convert(_resp, it.toscana.rete.rfc.sitatort.ResponseSchedaType.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public it.toscana.rete.rfc.sitatort.ResponsePresaInCaricoGaraDelegata presaInCaricoGaraDelegata(java.lang.String idAvGara, java.lang.String cfRup, java.lang.String indiceColl) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[4]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://rete.toscana.it/rfc/sitatort/presaInCaricoGaraDelegata");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://rete.toscana.it/rfc/sitatort/", "presaInCaricoGaraDelegata"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {idAvGara, cfRup, indiceColl});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.toscana.rete.rfc.sitatort.ResponsePresaInCaricoGaraDelegata) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.toscana.rete.rfc.sitatort.ResponsePresaInCaricoGaraDelegata) org.apache.axis.utils.JavaUtils.convert(_resp, it.toscana.rete.rfc.sitatort.ResponsePresaInCaricoGaraDelegata.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public it.toscana.rete.rfc.sitatort.ResponseLoginRPNT getLoginRPNT(java.lang.String cfRup) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[5]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://rete.toscana.it/rfc/sitatort/getLoginRPNT");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://rete.toscana.it/rfc/sitatort/", "getLoginRPNT"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {cfRup});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.toscana.rete.rfc.sitatort.ResponseLoginRPNT) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.toscana.rete.rfc.sitatort.ResponseLoginRPNT) org.apache.axis.utils.JavaUtils.convert(_resp, it.toscana.rete.rfc.sitatort.ResponseLoginRPNT.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

}
