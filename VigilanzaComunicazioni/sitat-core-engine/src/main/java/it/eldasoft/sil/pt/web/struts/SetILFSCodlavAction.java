package it.eldasoft.sil.pt.web.struts;

import it.eldasoft.gene.bl.GeneManager;
import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.commons.web.spring.DataSourceTransactionManagerBase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.transaction.TransactionStatus;

public class SetILFSCodlavAction extends Action {

	 Logger logger = Logger.getLogger(SetILFSCodlavAction.class);
	 
  private GeneManager geneManager;

  private SqlManager  sqlManager;

  public GeneManager getGeneManager() {
    return geneManager;
  }

  public void setGeneManager(GeneManager geneManager) {
    this.geneManager = geneManager;
  }

  public SqlManager getSqlManager() {
    return this.sqlManager;
  }

  public void setSqlManager(SqlManager sqlManager) {
    this.sqlManager = sqlManager;
  }

  public final ActionForward execute(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
      final HttpServletResponse response) throws Exception {

    DataSourceTransactionManagerBase.setRequest(request);

    response.setHeader("cache-control", "no-cache");
    response.setContentType("text/text;charset=utf-8");

    String endpoint = request.getParameter("endpoint");
    String codlav = request.getParameter("codlav");
    String contri = request.getParameter("contri");
    String conint = request.getParameter("conint");
    String cui = null;

    TransactionStatus status = null;
    boolean commitTransaction = false;
    try {
    	cui = (String)this.sqlManager.getObject("select cuiint from inttri where contri = ? and conint = ?", new Object[] { new Long(contri), new Long(conint) });
    	status = this.sqlManager.startTransaction();

    	// Aggiornamento dati di INTTRI
    	this.sqlManager.update("update inttri set codint = ?, CEFINT = '1' where contri = ? and conint = ?", new Object[] { codlav, new Long(contri), new Long(conint) });

    	commitTransaction = true;
    } catch (Exception e) {
    	logger.error(e);
      commitTransaction = false;
    } finally {
      if (status != null) {
        if (commitTransaction) {
          this.sqlManager.commitTransaction(status);
        } else {
          this.sqlManager.rollbackTransaction(status);
        }
      }
    }

    // Collegamento del codice Lavoro al Cui (invio dei dati in POST a Programmi Triennali
    try {
      String _url = endpoint + "/collegacui";

      URL url = new URL(_url);
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setDoOutput(true);
      conn.setRequestMethod("POST");
      conn.setRequestProperty("Content-Type", "application/json");

      String richiesta = "{ \"codicelavoro\": \"" + codlav + "\",\"codicecui\":\"" + cui + "\"}";
      OutputStream os = conn.getOutputStream();
      os.write(richiesta.getBytes());
      os.flush();

      BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

      String output;
      while ((output = br.readLine()) != null) {
    	  logger.info(output);
      }

      conn.disconnect();

    } catch (MalformedURLException e) {
    	logger.error(e);
    } catch (IOException e) {
    	logger.error(e);
    }

    return null;

  }

}
