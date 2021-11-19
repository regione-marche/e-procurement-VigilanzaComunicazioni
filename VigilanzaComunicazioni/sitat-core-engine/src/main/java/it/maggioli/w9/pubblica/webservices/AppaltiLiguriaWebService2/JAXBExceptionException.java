
/**
 * JAXBExceptionException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2;

public class JAXBExceptionException extends java.lang.Exception{

    private static final long serialVersionUID = 1588250795958L;
    
    private it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.JAXBExceptionDocument faultMessage;

    
        public JAXBExceptionException() {
            super("JAXBExceptionException");
        }

        public JAXBExceptionException(java.lang.String s) {
           super(s);
        }

        public JAXBExceptionException(java.lang.String s, java.lang.Throwable ex) {
          super(s, ex);
        }

        public JAXBExceptionException(java.lang.Throwable cause) {
            super(cause);
        }
    

    public void setFaultMessage(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.JAXBExceptionDocument msg){
       faultMessage = msg;
    }
    
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.JAXBExceptionDocument getFaultMessage(){
       return faultMessage;
    }
}
    