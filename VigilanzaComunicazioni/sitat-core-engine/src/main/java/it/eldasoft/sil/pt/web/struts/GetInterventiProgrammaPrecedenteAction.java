package it.eldasoft.sil.pt.web.struts;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.commons.web.domain.CostantiGenerali;
import it.eldasoft.gene.commons.web.struts.ActionBaseNoOpzioni;
import it.eldasoft.gene.commons.web.struts.CostantiGeneraliStruts;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;

/**
 * Action per carica la lista degli interventi del programma precedente da selezionare
 * per poi copiarli nel programma attuale.
 * 
 * @author Luca.Giacomazzo
 *
 */
public class GetInterventiProgrammaPrecedenteAction extends ActionBaseNoOpzioni {

	Logger logger = Logger.getLogger(GetInterventiProgrammaPrecedenteAction.class);

	private SqlManager sqlManager;

	public void setSqlManager(SqlManager sqlManager) {
		this.sqlManager = sqlManager;
	}

	@Override
	protected ActionForward runAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException {

		if (logger.isDebugEnabled()) {
			logger.debug("runAction: inizio metodo");
		}

		String target = CostantiGeneraliStruts.FORWARD_OK;

		Long chiavePianoTriennale = new Long(request.getParameter("codiceProgramma"));
		String annoIntervento = null; 
		String idProgramma = null;
		if (request.getParameter("idProg") != null) {
			idProgramma = request.getParameter("idProg");
		} 
		if (request.getParameter("anno") != null) {
			annoIntervento = request.getParameter("anno");
			request.setAttribute("anno", annoIntervento);
		} else {
			request.setAttribute("anno", "-1");
		}

		String tipoInterventi = request.getParameter("tipo");
		String codiceStazioneAppaltante = (String) request.getSession().getAttribute(
				CostantiGenerali.UFFICIO_INTESTATARIO_ATTIVO);
		try {

			Long annoProgramma = (Long) this.sqlManager.getObject("select ANNTRI from PIATRI where CONTRI=?",
					new Object[] { chiavePianoTriennale });

			Long tipoProgramma = (Long) this.sqlManager.getObject("select TIPROG from PIATRI where CONTRI=?",
					new Object[] { chiavePianoTriennale });

			List<Vector<JdbcParametro>> listaInterventi = null; 

			if ("INR".equalsIgnoreCase(tipoInterventi)) {

				String filtroIdProgramma = "";

				List< ? > listaIdProgrammi = sqlManager.getListVector(
						"select ID from PIATRI where ANNTRI=? and CENINT=? and TIPROG in (?, 3) order by ID desc",
						new Object[] { annoProgramma-1, codiceStazioneAppaltante, tipoProgramma } );

				if (listaIdProgrammi != null && listaIdProgrammi.size() > 1) {
					request.setAttribute("listaIdProgrammi", listaIdProgrammi);
				}

				if (idProgramma != null) {
					if ("-1".equals(idProgramma)) {
						filtroIdProgramma = "";
						request.setAttribute("idProg", "-1");
					} else {
						filtroIdProgramma = " and p.ID = '" + idProgramma + "' ";
						request.setAttribute("idProg", idProgramma);
					}
				} else if (listaIdProgrammi != null && listaIdProgrammi.size() > 1) {
					idProgramma = ((Vector) listaIdProgrammi.get(0)).get(0).toString();
					filtroIdProgramma = " and p.ID = '" + idProgramma + "' ";
					request.setAttribute("idProg", idProgramma);
				}

				// Per gli interventi non riproposti 
				listaInterventi = this.sqlManager.getListVector(
						"select i.CONTRI, i.CONINT, i.CUIINT, i.DESINT, i.TOTINT, " + sqlManager.getDBFunction("isnull",new String[] {"i.ANNRIF","0"}) + "+p.ANNTRI-1 as ANNO from INTTRI i, PIATRI p "
						+ "where i.CONTRI in (select CONTRI from PIATRI where ANNTRI=? and CENINT=?) "
						+ "  and i.CONTRI = p.CONTRI"
						+ "  and p.TIPROG in (?, 3) and i.TIPINT = ? "
						+ "  and i.ANNINT = '1' and i.ANNRIF is not null "
						+ "  and p.CENINT = ? "
						+ filtroIdProgramma
						+ "  and (i.CUIINT not in (select CUIINT from INRTRI where CONTRI=? union select CUIINT from INTTRI where CONTRI=?) or i.CUIINT is null) "
						+ "order by  ANNO asc, i.CUIINT asc, i.CONTRI asc, i.CONINT asc",
						new Object[] { annoProgramma-1, codiceStazioneAppaltante, tipoProgramma, tipoProgramma,
							codiceStazioneAppaltante, chiavePianoTriennale, chiavePianoTriennale } );
			
			} else if ("ANN".equalsIgnoreCase(tipoInterventi)) {
			
			  String filtroIdProgramma = "";

        List< ? > listaIdProgrammi = sqlManager.getListVector(
            "select ID from PIATRI where ANNTRI=? and CENINT=? and TIPROG in (?, 3) order by ID desc",
            new Object[] { annoProgramma-1, codiceStazioneAppaltante, tipoProgramma } );

        if (listaIdProgrammi != null && listaIdProgrammi.size() > 1) {
          request.setAttribute("listaIdProgrammi", listaIdProgrammi);
        }

        if (idProgramma != null) {
          if ("-1".equals(idProgramma)) {
            filtroIdProgramma = "";
            request.setAttribute("idProg", "-1");
          } else {
            filtroIdProgramma = " and p.ID = '" + idProgramma + "' ";
            request.setAttribute("idProg", idProgramma);
          }
        } else if (listaIdProgrammi != null && listaIdProgrammi.size() > 1) {
          idProgramma = ((Vector) listaIdProgrammi.get(0)).get(0).toString();
          filtroIdProgramma = " and p.ID = '" + idProgramma + "' ";
          request.setAttribute("idProg", idProgramma);
        }
        
        // Per gli interventi annullati 
        listaInterventi = this.sqlManager.getListVector(
            "select i.CONTRI, i.CONINT, i.CUIINT, i.DESINT, i.TOTINT, " + sqlManager.getDBFunction("isnull",new String[] {"i.ANNRIF","0"}) + "+p.ANNTRI-1 as ANNO"
            +" from INTTRI i, PIATRI p, FABBISOGNI f, PTAPPROVAZIONI a "
           + "where i.CONTRI in (select CONTRI from PIATRI where ANNTRI=? and CENINT=?) "
           + "  and i.CONTRI = p.CONTRI"
           + "  and i.CONTRI = a.ID_PROGRAMMA"
           + "  and i.CONINT = a.ID_INTERV_PROGRAMMA"
           + "  and p.TIPROG in (?, 3) and i.TIPINT = ? "
           + "  and i.ANNINT = '1' and i.ANNRIF is not null "
           + "  and p.CENINT = ? "
           + filtroIdProgramma
           + "  and (i.CUIINT not in (select CUIINT from INRTRI where CONTRI=? union select CUIINT from INTTRI where CONTRI=?) or i.CUIINT is null) "
           + "  and f.CONINT = a.CONINT and f.STATO in (31,32) "
           + "order by  ANNO asc, i.CUIINT asc, i.CONTRI asc, i.CONINT asc",
            new Object[] { annoProgramma-1, codiceStazioneAppaltante, tipoProgramma, tipoProgramma,
              codiceStazioneAppaltante, chiavePianoTriennale, chiavePianoTriennale } );
			} else {

				String filtroIdProgramma = "";
				String filtroAnno = "";
				//marzo 2019
				String filtroFabbisogni = "";
				String filtroNOTFabbisogni = "";

				List< ? > listaIdProgrammi = sqlManager.getListVector(
						"select ID from PIATRI where (ANNTRI=? OR ANNTRI=?) and CONTRI <> ? and CENINT=? and TIPROG in (?, 3) order by ID desc",
						new Object[] { annoProgramma - 1, annoProgramma, chiavePianoTriennale, codiceStazioneAppaltante, tipoProgramma } );

				if (listaIdProgrammi != null && listaIdProgrammi.size() > 1) {
					request.setAttribute("listaIdProgrammi", listaIdProgrammi);
				}

				if (idProgramma != null) {
					if ("-1".equals(idProgramma)) {
						filtroIdProgramma = "";
						request.setAttribute("idProg", "-1");
					} else {
						filtroIdProgramma = " and p.ID = '" + idProgramma + "' ";
						request.setAttribute("idProg", idProgramma);
					}
				} else if (listaIdProgrammi != null && listaIdProgrammi.size() > 1) {
					idProgramma = ((Vector) listaIdProgrammi.get(0)).get(0).toString();
					filtroIdProgramma = " and p.ID = '" + idProgramma + "' ";
					request.setAttribute("idProg", idProgramma);
				}

				if (annoIntervento != null) {
					if (("-1".equals(annoIntervento))) {
						filtroAnno = "";
					} else {
						filtroAnno = " and " + sqlManager.getDBFunction("isnull",new String[] {"i.ANNRIF","0"}) + "+ p.ANNTRI-1 = " + annoIntervento;
					}
				}

				List< ? > listaAnnualitaInterventiProgPrecedenti;

				if ("RDP_RevisioneInterventiAnnoPrecedente".equalsIgnoreCase(tipoInterventi)) {
					//marzo 2019
					filtroFabbisogni = " and (f.stato in (4,21) and f.conint=pt.conint and pt.ID_PROGRAMMA=i.CONTRI and pt.ID_INTERV_PROGRAMMA=i.CONINT) ";

					listaInterventi = this.sqlManager.getListVector(
							"select i.CONTRI, i.CONINT, i.CUIINT, i.DESINT, i.TOTINT, " + sqlManager.getDBFunction("isnull",new String[] {"i.ANNRIF","0"}) + "+ p.ANNTRI-1 as ANNO, f.conint AS F_CONINT "
							+ " from INTTRI i, PIATRI p, FABBISOGNI f, PTAPPROVAZIONI pt "
							+ " where i.CONTRI in (select CONTRI from PIATRI where ANNTRI=? and CENINT=?) "
							+ "  and i.CONTRI = p.CONTRI "
							+ "  and p.TIPROG in (?, 3) and i.TIPINT = ? "
							+ "  and p.CENINT = ?  and i.ANNRIF is not null "
							+ filtroIdProgramma
							+ filtroAnno
							+ "  and (i.CUIINT not in (select CUIINT from INRTRI where CONTRI=? union select CUIINT from INTTRI where CONTRI=?) or i.CUIINT is null) "
							//marzo 2019
							+ filtroFabbisogni
							+ "order by ANNO asc, i.CUIINT asc, i.CONTRI asc, i.CONINT asc",
							new Object[] { annoProgramma-1, codiceStazioneAppaltante, tipoProgramma, tipoProgramma,
								codiceStazioneAppaltante, chiavePianoTriennale, chiavePianoTriennale } );

					listaAnnualitaInterventiProgPrecedenti = sqlManager.getListVector(
							"select distinct(" + sqlManager.getDBFunction("isnull",new String[] {"i.ANNRIF","0"}) + "+p.ANNTRI-1) as ANNO," + sqlManager.getDBFunction("isnull",new String[] {"i.ANNRIF","0"}) + " as ANNRIF " 
							+ " from INTTRI i, PIATRI p, FABBISOGNI f, PTAPPROVAZIONI pt "
							+ " where i.CONTRI in (select CONTRI from PIATRI where ANNTRI=? and CENINT=?) "
							+ "   and i.CONTRI = p.CONTRI "
							+ "   and p.TIPROG in (?, 3) and i.TIPINT = ? and i.ANNRIF is not null "
							+ "   and p.CENINT = ? "
							+ "   and (i.CUIINT not in (select CUIINT from INRTRI where CONTRI=? union select CUIINT from INTTRI where CONTRI=?) or i.CUIINT is null) "
							//marzo 2019
							+ filtroFabbisogni
							+ "order by ANNO asc",
							new Object[] { annoProgramma-1, codiceStazioneAppaltante, tipoProgramma, tipoProgramma,
								codiceStazioneAppaltante, chiavePianoTriennale, chiavePianoTriennale } );


				} else {
					//marzo 2019
					filtroNOTFabbisogni = " and NOT(i.contri in (select pt.ID_PROGRAMMA from PTAPPROVAZIONI pt where pt.ID_PROGRAMMA=i.CONTRI and pt.ID_INTERV_PROGRAMMA=i.CONINT)) ";

					listaInterventi = this.sqlManager.getListVector(
							"select i.CONTRI, i.CONINT, i.CUIINT, i.DESINT, i.TOTINT, " + sqlManager.getDBFunction("isnull",new String[] {"i.ANNRIF","0"}) + " + p.ANNTRI-1 as ANNO from INTTRI i, PIATRI p "
							+ " where i.CONTRI in (select CONTRI from PIATRI where (ANNTRI=? OR ANNTRI=?) and CONTRI <> ? and CENINT=?) "
							+ "  and i.CONTRI = p.CONTRI "
							+ "  and p.TIPROG in (?, 3) and i.TIPINT = ? "
							+ "  and p.CENINT = ?  and i.ANNRIF is not null "
							+ filtroIdProgramma
							+ filtroAnno
							+ "  and (i.CUIINT not in (select CUIINT from INRTRI where CONTRI=? union select CUIINT from INTTRI where CONTRI=?) or i.CUIINT is null) "
							//marzo 2019
							+ filtroNOTFabbisogni
							+ "order by ANNO asc, i.CUIINT asc, i.CONTRI asc, i.CONINT asc",
							new Object[] { annoProgramma - 1, annoProgramma, chiavePianoTriennale, codiceStazioneAppaltante, tipoProgramma, tipoProgramma,
								codiceStazioneAppaltante, chiavePianoTriennale, chiavePianoTriennale } );

					listaAnnualitaInterventiProgPrecedenti = sqlManager.getListVector(
							"select distinct(" + sqlManager.getDBFunction("isnull",new String[] {"i.ANNRIF","0"}) + "+p.ANNTRI-1) as ANNO," + sqlManager.getDBFunction("isnull",new String[] {"i.ANNRIF","0"}) + "+p.ANNTRI-1 as ANNRIF " 
							+ " from INTTRI i, PIATRI p "
							+ " where i.CONTRI in (select CONTRI from PIATRI where (ANNTRI=? OR ANNTRI=?) and CONTRI <> ? and CENINT=?) "
							+ "   and i.CONTRI = p.CONTRI "
							+ "   and p.TIPROG in (?, 3) and i.TIPINT = ? and i.ANNRIF is not null "
							+ "   and p.CENINT = ? "
							+ "   and (i.CUIINT not in (select CUIINT from INRTRI where CONTRI=? union select CUIINT from INTTRI where CONTRI=?) or i.CUIINT is null) "
							//marzo 2019
							+ filtroNOTFabbisogni
							+ "order by ANNO asc",
							new Object[] { annoProgramma - 1, annoProgramma, chiavePianoTriennale, codiceStazioneAppaltante, tipoProgramma, tipoProgramma,
								codiceStazioneAppaltante, chiavePianoTriennale, chiavePianoTriennale } );
					if (idProgramma != null) {
						//ricavo l'anno del programma scelto come sorgente se è uguale all'anno del programma di destinazione
						//gli interventi selezionati devono mantenere invariati gli importi del quadro economico
						//ricavo annualità del programma sorgente e destinazione
            Long annoProgrammaSorgente = (Long) sqlManager.getObject("select ANNTRI from PIATRI where ID = ?", new Object[] { idProgramma });
            //Long annoProgrammaDestinazione = (Long) sqlManager.getObject("select ANNTRI from PIATRI where CONTRI = ?", new Object[] { contriInterventoDestinazione });
            if (annoProgramma.equals(annoProgrammaSorgente)) {
             	request.setAttribute("copiaInterventiDaProgrammaConStessaAnnualita", "si");
            }
					}
				}

				if (listaAnnualitaInterventiProgPrecedenti != null && listaAnnualitaInterventiProgPrecedenti.size() > 0) {
					request.setAttribute("listaAnnualitaInterventiProgPrecedenti", listaAnnualitaInterventiProgPrecedenti);
				}
			}
			if (listaInterventi != null && listaInterventi.size() > 0) {
				request.setAttribute("listaInterventi", listaInterventi);
			}
		} catch (SQLException e) {
			target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
			logger.error("Errore nell'estrazioe della lista degli interventi dei programmi dell'anno precedente", e);
			this.aggiungiMessaggio(request, "errors.listaInterventiProgrammaPrecedente");
		}
		if (logger.isDebugEnabled()) {
			logger.debug("runAction: fine metodo");
		}

		return mapping.findForward(target);
	}

}
