
/**
 * AppaltiLiguriaWebServiceStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */
        package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2;

        

        /*
        *  AppaltiLiguriaWebServiceStub java implementation
        */

        
        public class AppaltiLiguriaWebServiceStub extends org.apache.axis2.client.Stub
        {
        protected org.apache.axis2.description.AxisOperation[] _operations;

        //hashmaps to keep the fault mapping
        private java.util.HashMap faultExceptionNameMap = new java.util.HashMap();
        private java.util.HashMap faultExceptionClassNameMap = new java.util.HashMap();
        private java.util.HashMap faultMessageMap = new java.util.HashMap();

        private static int counter = 0;

        private static synchronized java.lang.String getUniqueSuffix(){
            // reset the counter if it is greater than 99999
            if (counter > 99999){
                counter = 0;
            }
            counter = counter + 1; 
            return java.lang.Long.toString(java.lang.System.currentTimeMillis()) + "_" + counter;
        }

    
    private void populateAxisService() throws org.apache.axis2.AxisFault {

     //creating the Service with a unique name
     _service = new org.apache.axis2.description.AxisService("AppaltiLiguriaWebService" + getUniqueSuffix());
     addAnonymousOperations();

        //creating the operations
        org.apache.axis2.description.AxisOperation __operation;

        _operations = new org.apache.axis2.description.AxisOperation[1];
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/AppaltiLiguriaWebService.wsdl", "invioContratto"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[0]=__operation;
            
        
        }

    //populates the faults
    private void populateFaults(){
         
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/AppaltiLiguriaWebService.wsdl","JAXBException"), "invioContratto"),"it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.JAXBExceptionException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/AppaltiLiguriaWebService.wsdl","JAXBException"), "invioContratto"),"it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.JAXBExceptionException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/AppaltiLiguriaWebService.wsdl","JAXBException"), "invioContratto"),"it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.JAXBExceptionDocument");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/AppaltiLiguriaWebService.wsdl","CheckVerifyFault"), "invioContratto"),"it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.WsException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/AppaltiLiguriaWebService.wsdl","CheckVerifyFault"), "invioContratto"),"it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.WsException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/AppaltiLiguriaWebService.wsdl","CheckVerifyFault"), "invioContratto"),"it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.CheckVerifyFaultDocument");
           


    }

    /**
      *Constructor that takes in a configContext
      */

    public AppaltiLiguriaWebServiceStub(org.apache.axis2.context.ConfigurationContext configurationContext,
       java.lang.String targetEndpoint)
       throws org.apache.axis2.AxisFault {
         this(configurationContext,targetEndpoint,false);
   }


   /**
     * Constructor that takes in a configContext  and useseperate listner
     */
   public AppaltiLiguriaWebServiceStub(org.apache.axis2.context.ConfigurationContext configurationContext,
        java.lang.String targetEndpoint, boolean useSeparateListener)
        throws org.apache.axis2.AxisFault {
         //To populate AxisService
         populateAxisService();
         populateFaults();

        _serviceClient = new org.apache.axis2.client.ServiceClient(configurationContext,_service);
        
	
        _serviceClient.getOptions().setTo(new org.apache.axis2.addressing.EndpointReference(
                targetEndpoint));
        _serviceClient.getOptions().setUseSeparateListener(useSeparateListener);
        
    
    }

    /**
     * Default Constructor
     */
    public AppaltiLiguriaWebServiceStub(org.apache.axis2.context.ConfigurationContext configurationContext) throws org.apache.axis2.AxisFault {
        
                    this(configurationContext,"https://appaltiliguria.regione.liguria.it/pubblica/webservices/AppaltiLiguriaWebService" );
                
    }

    /**
     * Default Constructor
     */
    public AppaltiLiguriaWebServiceStub() throws org.apache.axis2.AxisFault {
        
                    this("https://appaltiliguria.regione.liguria.it/pubblica/webservices/AppaltiLiguriaWebService" );
                
    }

    /**
     * Constructor taking the target endpoint
     */
    public AppaltiLiguriaWebServiceStub(java.lang.String targetEndpoint) throws org.apache.axis2.AxisFault {
        this(null,targetEndpoint);
    }



        
                    /**
                     * Auto generated method signature
                     * 
                     * @see it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.AppaltiLiguriaWebService#invioContratto
                     * @param invioContratto
                    
                     * @throws it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.JAXBExceptionException : 
                     * @throws it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.WsException : 
                     */

                    

                            public  it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.InvioContrattoResponseDocument invioContratto(

                            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.InvioContrattoDocument invioContratto)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.JAXBExceptionException
                        ,it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.WsException{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[0].getName());
              _operationClient.getOptions().setAction("urn:invioContratto");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    invioContratto,
                                                    optimizeContent(new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/AppaltiLiguriaWebService.wsdl",
                                                    "invioContratto")), new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/AppaltiLiguriaWebService.wsdl",
                                                    "invioContratto"));
                                                
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.InvioContrattoResponseDocument.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.InvioContrattoResponseDocument)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"invioContratto"))){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"invioContratto"));
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                        java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"invioContratto"));
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.JAXBExceptionException){
                          throw (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.JAXBExceptionException)ex;
                        }
                        
                        if (ex instanceof it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.WsException){
                          throw (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.WsException)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                if (_messageContext.getTransportOut() != null) {
                      _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                }
            }
        }
            


       /**
        *  A utility method that copies the namepaces from the SOAPEnvelope
        */
       private java.util.Map getEnvelopeNamespaces(org.apache.axiom.soap.SOAPEnvelope env){
        java.util.Map returnMap = new java.util.HashMap();
        java.util.Iterator namespaceIterator = env.getAllDeclaredNamespaces();
        while (namespaceIterator.hasNext()) {
            org.apache.axiom.om.OMNamespace ns = (org.apache.axiom.om.OMNamespace) namespaceIterator.next();
            returnMap.put(ns.getPrefix(),ns.getNamespaceURI());
        }
       return returnMap;
    }

    
    
    private javax.xml.namespace.QName[] opNameArray = null;
    private boolean optimizeContent(javax.xml.namespace.QName opName) {
        

        if (opNameArray == null) {
            return false;
        }
        for (int i = 0; i < opNameArray.length; i++) {
            if (opName.equals(opNameArray[i])) {
                return true;   
            }
        }
        return false;
    }
     //https://appaltiliguria.regione.liguria.it/pubblica/webservices/AppaltiLiguriaWebService

            private  org.apache.axiom.om.OMElement  toOM(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.InvioContrattoDocument param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault{

            
                    return toOM(param);
                

            }

            private org.apache.axiom.om.OMElement toOM(final it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.InvioContrattoDocument param)
                    throws org.apache.axis2.AxisFault {

                org.apache.xmlbeans.XmlOptions xmlOptions = new org.apache.xmlbeans.XmlOptions();
                xmlOptions.setSaveNoXmlDecl();
                xmlOptions.setSaveAggressiveNamespaces();
                xmlOptions.setSaveNamespacesFirst();
                org.apache.axiom.om.OMXMLParserWrapper builder = org.apache.axiom.om.OMXMLBuilderFactory.createOMBuilder(
                        new javax.xml.transform.sax.SAXSource(new org.apache.axis2.xmlbeans.XmlBeansXMLReader(param, xmlOptions), new org.xml.sax.InputSource()));
                try {
                    return builder.getDocumentElement(true);
                } catch (java.lang.Exception e) {
                    throw org.apache.axis2.AxisFault.makeFault(e);
                }
            }
        

            private  org.apache.axiom.om.OMElement  toOM(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.InvioContrattoResponseDocument param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault{

            
                    return toOM(param);
                

            }

            private org.apache.axiom.om.OMElement toOM(final it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.InvioContrattoResponseDocument param)
                    throws org.apache.axis2.AxisFault {

                org.apache.xmlbeans.XmlOptions xmlOptions = new org.apache.xmlbeans.XmlOptions();
                xmlOptions.setSaveNoXmlDecl();
                xmlOptions.setSaveAggressiveNamespaces();
                xmlOptions.setSaveNamespacesFirst();
                org.apache.axiom.om.OMXMLParserWrapper builder = org.apache.axiom.om.OMXMLBuilderFactory.createOMBuilder(
                        new javax.xml.transform.sax.SAXSource(new org.apache.axis2.xmlbeans.XmlBeansXMLReader(param, xmlOptions), new org.xml.sax.InputSource()));
                try {
                    return builder.getDocumentElement(true);
                } catch (java.lang.Exception e) {
                    throw org.apache.axis2.AxisFault.makeFault(e);
                }
            }
        

            private  org.apache.axiom.om.OMElement  toOM(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.JAXBExceptionDocument param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault{

            
                    return toOM(param);
                

            }

            private org.apache.axiom.om.OMElement toOM(final it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.JAXBExceptionDocument param)
                    throws org.apache.axis2.AxisFault {

                org.apache.xmlbeans.XmlOptions xmlOptions = new org.apache.xmlbeans.XmlOptions();
                xmlOptions.setSaveNoXmlDecl();
                xmlOptions.setSaveAggressiveNamespaces();
                xmlOptions.setSaveNamespacesFirst();
                org.apache.axiom.om.OMXMLParserWrapper builder = org.apache.axiom.om.OMXMLBuilderFactory.createOMBuilder(
                        new javax.xml.transform.sax.SAXSource(new org.apache.axis2.xmlbeans.XmlBeansXMLReader(param, xmlOptions), new org.xml.sax.InputSource()));
                try {
                    return builder.getDocumentElement(true);
                } catch (java.lang.Exception e) {
                    throw org.apache.axis2.AxisFault.makeFault(e);
                }
            }
        

            private  org.apache.axiom.om.OMElement  toOM(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.CheckVerifyFaultDocument param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault{

            
                    return toOM(param);
                

            }

            private org.apache.axiom.om.OMElement toOM(final it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.CheckVerifyFaultDocument param)
                    throws org.apache.axis2.AxisFault {

                org.apache.xmlbeans.XmlOptions xmlOptions = new org.apache.xmlbeans.XmlOptions();
                xmlOptions.setSaveNoXmlDecl();
                xmlOptions.setSaveAggressiveNamespaces();
                xmlOptions.setSaveNamespacesFirst();
                org.apache.axiom.om.OMXMLParserWrapper builder = org.apache.axiom.om.OMXMLBuilderFactory.createOMBuilder(
                        new javax.xml.transform.sax.SAXSource(new org.apache.axis2.xmlbeans.XmlBeansXMLReader(param, xmlOptions), new org.xml.sax.InputSource()));
                try {
                    return builder.getDocumentElement(true);
                } catch (java.lang.Exception e) {
                    throw org.apache.axis2.AxisFault.makeFault(e);
                }
            }
        
                                
                                private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.InvioContrattoDocument param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                                throws org.apache.axis2.AxisFault{
                                org.apache.axiom.soap.SOAPEnvelope envelope = factory.getDefaultEnvelope();
                                if (param != null){
                                envelope.getBody().addChild(toOM(param, optimizeContent));
                                }
                                return envelope;
                                }
                            


        /**
        *  get the default envelope
        */
        private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory){
        return factory.getDefaultEnvelope();
        }

        public org.apache.xmlbeans.XmlObject fromOM(
        org.apache.axiom.om.OMElement param,
        java.lang.Class type,
        java.util.Map extraNamespaces) throws org.apache.axis2.AxisFault{
        try{
        

            if (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.InvioContrattoDocument.class.equals(type)){
            if (extraNamespaces!=null){
            return it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.InvioContrattoDocument.Factory.parse(
            param.getXMLStreamReaderWithoutCaching(),
            new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(extraNamespaces));
            }else{
            return it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.InvioContrattoDocument.Factory.parse(
            param.getXMLStreamReaderWithoutCaching());
            }
            }

        

            if (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.InvioContrattoResponseDocument.class.equals(type)){
            if (extraNamespaces!=null){
            return it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.InvioContrattoResponseDocument.Factory.parse(
            param.getXMLStreamReaderWithoutCaching(),
            new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(extraNamespaces));
            }else{
            return it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.InvioContrattoResponseDocument.Factory.parse(
            param.getXMLStreamReaderWithoutCaching());
            }
            }

        

            if (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.JAXBExceptionDocument.class.equals(type)){
            if (extraNamespaces!=null){
            return it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.JAXBExceptionDocument.Factory.parse(
            param.getXMLStreamReaderWithoutCaching(),
            new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(extraNamespaces));
            }else{
            return it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.JAXBExceptionDocument.Factory.parse(
            param.getXMLStreamReaderWithoutCaching());
            }
            }

        

            if (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.CheckVerifyFaultDocument.class.equals(type)){
            if (extraNamespaces!=null){
            return it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.CheckVerifyFaultDocument.Factory.parse(
            param.getXMLStreamReaderWithoutCaching(),
            new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(extraNamespaces));
            }else{
            return it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.CheckVerifyFaultDocument.Factory.parse(
            param.getXMLStreamReaderWithoutCaching());
            }
            }

        
        }catch(java.lang.Exception e){
        throw org.apache.axis2.AxisFault.makeFault(e);
        }
        return null;
        }

        
        
   }
   