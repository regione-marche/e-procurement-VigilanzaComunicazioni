package it.eldasoft.w9.web.struts;

import it.eldasoft.gene.bl.LoginManager;
import it.eldasoft.gene.commons.web.struts.CostantiGeneraliStruts;
import it.eldasoft.gene.db.domain.admin.Account;
import it.eldasoft.gene.web.struts.admin.CostantiDettaglioAccount;
import it.eldasoft.gene.web.struts.login.IsUserLoggedAction;
import it.eldasoft.gene.web.struts.login.cohesion.error.AuthenticationException;

import java.io.IOException;
import java.rmi.RemoteException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action per la gestione della risposta di Idm-RAS alla login
 * 
 * @author Mirco.Franzoni
 */
public class IdmRasLoginResponseAction extends IsUserLoggedAction {
    
  /**
   * Logger di classe.
   */
  private static Logger logger = Logger.getLogger(IdmRasLoginResponseAction.class);

  // private boolean forceLogout = false;
  
  private LoginManager loginManager;
  
  public void setLoginManager(LoginManager loginManager) {
    this.loginManager = loginManager;
  }

  protected ActionForward runAction(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
	  
	  if (logger.isDebugEnabled()) {
	      logger.debug("runAction: inizio metodo");
	    }
    String target = CostantiGeneraliStruts.FORWARD_OK;
    
    // URL del form di registrazione di SAU, per le stazioni appaltanti
    //String urlSAURegistration = ConfigManager.getValore("it.eldasoft.sau.registration.url");
    AccountIDMRAS accountIDM = null;
    try {
      String username = request.getParameter("username");
      String nome = request.getParameter("nome");
      String cognome = request.getParameter("cognome");
      String email = request.getParameter("email");
      String telefono = request.getParameter("telefono");
      
      if (username != null) {
        Account account = this.loginManager.getAccountByLogin(username);
        if (account == null) {
        	// Account esistente su IDM-RAS, ma non esistente nella base dati dell'applicazione.
        	// Si rimanda alla pagina di registrazione dell'utente.
        	accountIDM = new AccountIDMRAS();
        	accountIDM.setUsername(username);
        	accountIDM.setNome(nome);
        	accountIDM.setCognome(cognome);
        	accountIDM.setEmail(email);
        	accountIDM.setTelefono(telefono);
        	accountIDM.setIdmLogin(true);
            
            request.setAttribute("account", accountIDM);
          
            this.aggiungiMessaggio(request, "warnings.login.idm-ras.userNotRegisteredForApp");
          
            target = "register";
          
        } else if (account != null && (account.getStato() != null && CostantiDettaglioAccount.ABILITATO != account.getStato())) {
            throw new AuthenticationException(AuthenticationException.USER_NOT_ACTIVE);
        } else {

          //request.getSession().setAttribute(AccountCohesion.ID_ATTRIBUTO_SESSIONE_ACCOUNT_COHESION, accountIDM);
          
          request.setAttribute("username", account.getLogin());
          request.setAttribute("password", account.getPassword());
          
        }
      } else {
    	  target = "login";
      }
    } catch (Exception ex) {
      if (ex instanceof RemoteException && ((RemoteException) ex).getMessage().equals("USER-UNKNOWN")) {
        //this.addActionError(this.getText("errors.login.unknownUser"));
        this.aggiungiMessaggio(request, "errors.login.unknownUser");
        target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE; //ERROR;
      } else if (ex instanceof RemoteException && ((RemoteException) ex).getMessage().equals("USER-DISABLED")) {
        //this.addActionError(this.getText("errors.login.suspendedUser"));
        this.aggiungiMessaggio(request, "errors.login.suspendedUser");
        target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE; //ERROR;
      } else if (ex instanceof RemoteException && ((RemoteException) ex).getMessage().equals("NO-PROFILE")) {
        //this.addActionError(this.getText("errors.login.unknownUserForApp"));
        this.aggiungiMessaggio(request, "errors.login.unknownUserForApp");
        target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE; //ERROR;
      } else if (ex instanceof AuthenticationException && ((AuthenticationException) ex).getMessage().equals(AuthenticationException.USER_NOT_ACTIVE)) {
        //this.addActionError(this.getText("errors.login.userNotActive"));
        this.aggiungiMessaggio(request, "errors.login.cohesion.userNotActive");
        target = "login";
      } else {
        this.aggiungiMessaggio(request, "errors.login.unknownError");
        target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE; //ERROR;
      }
    }
    
    if (logger.isDebugEnabled()) {
        logger.debug("runAction: fine metodo");
      }
    
    return mapping.findForward(target);
  }
   
}
