==Nome esteso applicazione==
SitatSA
==versione==
ver 2.7.0 del 29/09/2014

==descrizione==


==team di progetto==
=== Responsabile RT===
*luca.menegatti@regione.toscana.it

===fornitore ===
Eldasoft S.p.A.
*Stefano Sabbadin, stefano.sabbadin@eldasoft.it, +39 04222676 

==deploy==
AMBIENTE: 		TIX
APPLICATION SERVER:	TOMCAT
HTTP O HTTPS:		HTTP
ESPONE WEB SERVICES:	SI

===oggetti jndi attesi===
*jdbc/SitatSA, datasource
*application_properties_file, file di properties esterno alla webapp

==dipendenze da framework/pacchetti==
* Struts1
* Spring2
* iBatis
* Apache Axis, Apache Axis2, Commons, 
* Displaytag
* iText
* Log4J
* XmlBeans
* dipendenze derivate dall'utilizzo dei precedenti framework/pacchetti

==librerie incluse nell'applicazione==
ant-1.7.0.jar
ant-launcher-1.7.0.jar
antlr-2.7.2.jar
apache-mime4j-core-0.7.2.jar
axiom-api-1.2.13.jar
axiom-impl-1.2.13.jar
axis-1.4.jar
axis-jaxrpc-1.4.jar
axis-saaj-1.4.jar
axis-wsdl4j-1.5.1.jar
axis2-1.6.2.jar
axis2-adb-1.6.2.jar
axis2-codegen-1.6.2.jar
axis2-kernel-1.6.2.jar
axis2-transport-http-1.6.2.jar
axis2-transport-local-1.6.2.jar
axis2-xmlbeans-1.6.2.jar
batik-ext-1.7.jar
bcmail-jdk14-1.38.jar
bcmail-jdk14-138.jar
bcprov-jdk14-1.38.jar
bcprov-jdk14-138.jar
bctsp-jdk14-1.38.jar
castor-1.2.jar
cglib-nodep-2.1_3.jar
commons-beanutils-1.8.3.jar
commons-chain-1.2.jar
commons-codec-1.5.jar
commons-collections-3.2.1.jar
commons-digester-2.1.jar
commons-discovery-0.2.jar
commons-fileupload-1.3.jar
commons-httpclient-3.1.jar
commons-io-2.2.jar
commons-lang-2.3.jar
commons-logging-1.1.3.jar
commons-pool-1.3.jar
commons-validator-1.3.1.jar
displaytag-1.1.1-eldasoft.jar
displaytag-export-poi-1.1.1.jar
dom4j-1.6.1.jar
ezmorph-1.0.4.jar
geronimo-activation_1.1_spec-1.1.jar
geronimo-javamail_1.4_spec-1.7.1.jar
geronimo-jta_1.1_spec-1.1.jar
geronimo-stax-api_1.0_spec-1.0.1.jar
geronimo-ws-metadata_2.0_spec-1.1.2.jar
httpcore-4.0.jar
ibatis-sqlmap-2.3.4.726.jar
icu4j-2.6.1.jar
itext-2.1.7.jar
itext-rtf-2.1.7.jar
jackson-core-lgpl-1.7.4.jar
jackson-mapper-lgpl-1.7.4.jar
jasperreports-4.5.1.jar
jasperreports-fonts-4.0.0.jar
jaxb-api-2.2.11.jar
jaxen-1.1.1.jar
jcommon-1.0.15.jar
jdom-1.0.jar
jdtcore-3.1.0.jar
jfreechart-1.0.12.jar
jsch-0.1.42.jar
json-lib-2.2-jdk15.jar
jsr173-1.0.jar
jsr311-api-1.0.jar
jstl-1.1.2.jar
jta-1.1.jar
ldapbp-1.0.jar
log4j-1.2.14.jar
neethi-3.0.2.jar
oro-2.0.8.jar
poi-3.9.jar
poi-ooxml-3.7.jar
poi-ooxml-schemas-3.7.jar
quartz-all-1.6.1.jar
rssutils-1.0.jar
saxon-8.7.jar
serializer-2.7.1.jar
spring-2.0.6.jar
spring-beans-2.0.6.jar
spring-core-2.0.6.jar
spring-ldap-1.2.1.jar
standard-1.1.2.jar
stax-api-1.0.1.jar
struts-core-1.3.11-SNAPSHOT.jar
struts-extras-1.3.10.jar
struts-taglib-1.3.10.jar
struts-tiles-1.3.10.jar
velocity-1.7.jar
woden-api-1.0M9.jar
woden-impl-commons-1.0M9.jar
woden-impl-dom-1.0M9.jar
wsdl4j-1.6.2.jar
wstx-asl-3.2.9.jar
xalan-2.7.1.jar
xercesImpl-2.11.0.jar
xml-apis-1.0.b2.jar
xmlbeans-2.5.0.jar
xmlbeans-xpath-2.2.0.jar
xmlParserAPIs-2.6.2.jar
XmlSchema-1.4.7.jar
xom-1.0.jar


=configurazione==
Si veda il file SitatSA-ManualeConfigurazione.odt allegato alla presente cartella.

Per l'ambiente di certificazione:
- creare la cartella /apps/tix/data/SitatSA/config/ e copiare il file application-cert.properties presente nella cartella config/fileSystem del rilascio (la cartella deve essere accessibile in lettura dall'utente con cui viene avviato il tomcat)
- creare la risorsa JNDI per name="application_properties_file" che punti al file application-cert.properties

Per l'ambiente di produzione:
- creare la cartella /apps/tix/data/SitatSA/config/ e copiare il file application-prod.properties presente nella cartella config/fileSystem del rilascio (la cartella deve essere accessibile in lettura dall'utente con cui viene avviato il tomcat)
- creare la risorsa JNDI per name="application_properties_file" che punti al file application-prod.properties


==note==

