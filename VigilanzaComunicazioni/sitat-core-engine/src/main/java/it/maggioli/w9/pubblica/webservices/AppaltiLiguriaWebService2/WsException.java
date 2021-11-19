
/**
 * WsException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2;

public class WsException extends java.lang.Exception{

    private static final long serialVersionUID = 1588250796082L;
    
    private it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.CheckVerifyFaultDocument faultMessage;

    
        public WsException() {
            super("WsException");
        }

        public WsException(java.lang.String s) {
           super(s);
        }

        public WsException(java.lang.String s, java.lang.Throwable ex) {
          super(s, ex);
        }

        public WsException(java.lang.Throwable cause) {
            super(cause);
        }
    

    public void setFaultMessage(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.CheckVerifyFaultDocument msg){
       faultMessage = msg;
    }
    
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.CheckVerifyFaultDocument getFaultMessage(){
       return faultMessage;
    }
}
    