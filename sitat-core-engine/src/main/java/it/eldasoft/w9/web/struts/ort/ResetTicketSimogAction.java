package it.eldasoft.w9.web.struts.ort;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import it.eldasoft.gene.commons.web.struts.CostantiGeneraliStruts;
import it.eldasoft.w9.bl.simog.TicketSimogManager;

/**
 * Action per resettare l'oggetto TicketSimogBean.
 * 
 * @author Luca.Giacomazzo
 */
public class ResetTicketSimogAction extends Action {

	private TicketSimogManager ticketSimogManager;
	
	public void setTicketSimogManager(TicketSimogManager ticketSimogManager) {
		this.ticketSimogManager = ticketSimogManager;
	}
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		this.ticketSimogManager.resetTicket();
		
		return mapping.findForward(CostantiGeneraliStruts.FORWARD_OK);
	}

}
