package it.eldasoft.sil.pt.web.struts;

import it.eldasoft.gene.commons.web.struts.ActionBaseNoOpzioni;
import it.eldasoft.gene.commons.web.struts.CostantiGeneraliStruts;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.sil.pt.bl.PtManager;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletOutputStream;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.xmlbeans.XmlObject;

/**
 * @author Mirco.Franzoni - Eldasoft S.p.A. Treviso
 * action che gestisce la generazione nel formato XML di un programma per l'importaione in HiProg3
 */
public class CreateXMLProgrammiTriennaliAction extends ActionBaseNoOpzioni {

	/** Logger Log4J di classe. */
  private static Logger     logger = Logger.getLogger(CreateXMLProgrammiTriennaliAction.class);

  /** manager per operazioni di interrogazione del db. */
  private PtManager ptManager;

  /**
   * @param manager
   *        manager da settare internamente alla classe.
   */
  public void setPtManager(PtManager manager) {
    ptManager = manager;
  }

	@Override
	protected ActionForward runAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		XmlObject document = null;
		String documentString = "<?xml version=\"1.0\" encoding=\"iso-8859-1\" ?>";
		String target = null;
		Long contri = new Long((String) request.getParameter("contri"));

		try {
			// crea il documento
			document = this.ptManager.getXmlDocumentProgrammiTriennali(contri);

			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition",
					"attachment;filename=programmaTriennale" + contri + ".xml");
				
			//InputStream xmlInputStream = document.newInputStream( );
			ServletOutputStream out = response.getOutputStream();

			/*byte[] outputByte = new byte[4096];
			// copy binary content to output stream
			while (xmlInputStream.read(outputByte, 0, 4096) != -1) {
				out.write(outputByte, 0, 4096);
				
			}*/
			documentString += document.toString();
			byte[] theByteArray = documentString.getBytes();
			
			out.write(theByteArray, 0, theByteArray.length);
			
			//xmlInputStream.close();
			out.flush();
			out.close();

		} catch (GestoreException e) {
			target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
			logger.error(
					"errore durante l'esportazione del Piano triennale nel formato XML",
					e);
			aggiungiMessaggio(request,
					"errors.gestoreException.*.commons.validate", e.getMessage());
		}
		if (target != null) {
			return mapping.findForward(target);
		} else {
			return null;
		}
	}

}
