package it.eldasoft.sil.pt.web.struts;

import it.eldasoft.gene.bl.GeneManager;
import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.commons.web.domain.CostantiGenerali;
import it.eldasoft.gene.commons.web.struts.ActionBaseNoOpzioni;
import it.eldasoft.gene.commons.web.struts.CostantiGeneraliStruts;
import it.eldasoft.gene.db.datautils.DataColumn;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.web.struts.tags.gestori.AbstractGestoreEntita;
import it.eldasoft.gene.web.struts.tags.gestori.DefaultGestoreEntitaChiaveNumerica;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.sil.pt.bl.PtManager;
import it.eldasoft.utils.spring.UtilitySpring;
import it.eldasoft.utils.utility.UtilityDate;
import it.eldasoft.utils.utility.UtilityStringhe;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.transaction.TransactionStatus;

/**
 * Action per carica la lista defabbisogni da selezionare
 * per poi ...
 *
 * @author C.F.
 *
 */
public class GetListaFabbisogniAction extends ActionBaseNoOpzioni {

    Logger logger = Logger.getLogger(GetListaFabbisogniAction.class);

    private SqlManager sqlManager;

    private PtManager ptManager;

    public void setSqlManager(SqlManager sqlManager) {
        this.sqlManager = sqlManager;
    }

    public void setPtManager(PtManager ptManager) {
      this.ptManager = ptManager;
    }

    @Override
    protected ActionForward runAction(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

      if (logger.isDebugEnabled()) {
        logger.debug("runAction: inizio metodo");
      }

      String target = CostantiGeneraliStruts.FORWARD_OK;
      String codiceStazioneAppaltante = (String) request.getSession().getAttribute(
          CostantiGenerali.UFFICIO_INTESTATARIO_ATTIVO);

      //////////////////////////////////////
      //marzo 2019
      String funzionalita = request.getParameter("funzionalita");
      funzionalita = UtilityStringhe.convertiNullInStringaVuota(funzionalita);
      ////////////////////////////////////////

      String contriStr = request.getParameter("contri");
      String tiprogStr = request.getParameter("tiprog");
      tiprogStr = UtilityStringhe.convertiNullInStringaVuota(tiprogStr);
      String tipoForm = request.getParameter("tipoForm");
      tipoForm = UtilityStringhe.convertiNullInStringaVuota(tipoForm);
      String fabbDaImportare = request.getParameter("fabbisogniDaImportare");

      String nomeinRic = request.getParameter("nomeinRic");
      nomeinRic = UtilityStringhe.convertiNullInStringaVuota(nomeinRic);
      String natgiuRic = request.getParameter("natgiuRic");
      natgiuRic = UtilityStringhe.convertiNullInStringaVuota(natgiuRic);
      String conintRic = request.getParameter("conintRic");
      conintRic = UtilityStringhe.convertiNullInStringaVuota(conintRic);
      String codintRic = request.getParameter("codintRic");
      codintRic = UtilityStringhe.convertiNullInStringaVuota(codintRic);
      String desintRic = request.getParameter("desintRic");
      desintRic = UtilityStringhe.convertiNullInStringaVuota(desintRic);
      String cuiintRic = request.getParameter("cuiintRic");
      cuiintRic = UtilityStringhe.convertiNullInStringaVuota(cuiintRic);
      String tipoinRic = request.getParameter("tipoinRic");
      tipoinRic = UtilityStringhe.convertiNullInStringaVuota(tipoinRic);
      //////////////////////////////////////
      //FEBBRAIO 2019
      String rupRic = request.getParameter("rupRic");
      rupRic = UtilityStringhe.convertiNullInStringaVuota(rupRic);
      String cdiRic = request.getParameter("cdiRic");
      cdiRic = UtilityStringhe.convertiNullInStringaVuota(cdiRic);
      String lsStr = "";
      String anntriStr = request.getParameter("anntri");
      anntriStr = UtilityStringhe.convertiNullInStringaVuota(anntriStr);
      Long anntri = null;
      if(!"".equals(anntriStr)){
        anntri = new Long(anntriStr);
      }
      //////////////////////////////////////

      String noteApprovazione = null;
      String nomeUtente = null;
      String idUtenteStr = null;
      Long idUtente = null;
      if("3".equals(tipoForm)){
        noteApprovazione = request.getParameter("noteAppr");
        nomeUtente = request.getParameter("nomeUtente");
        idUtenteStr = request.getParameter("idUtente");
        idUtenteStr = UtilityStringhe.convertiNullInStringaVuota(idUtenteStr);
        if(!"".equals(idUtenteStr)){
          idUtente = new Long(idUtenteStr);
        }
      }
      
      /////////////////
      //marzo 2019
      Long inttriGiaInserite = new Long(0) ;
      /////////////////

      TransactionStatus status = null;
      boolean commitTransaction = false;

      try {
    	  GeneManager geneManager = (GeneManager) UtilitySpring.getBean("geneManager",
    	          request.getSession().getServletContext(), GeneManager.class);
    	  String profilo = (String)request.getSession().getAttribute(CostantiGenerali.PROFILO_ATTIVO);
    	  boolean filtroCodeinAttivo = geneManager.getProfili().checkProtec(profilo, "FUNZ", "VIS", "ALT.W9.FiltroUffintListaProposte");
          
    	  Date dataOdierna = new Date();
    	  String dataOggi = ""+UtilityDate.convertiData(dataOdierna, UtilityDate.FORMATO_GG_MM_AAAA);

    	  List<Vector<JdbcParametro>> listaFabbisogni = null;

    	  if ("1".equals(tipoForm)) {

          String filtroIdProgramma = "";
          
          if(!filtroCodeinAttivo) {
        	  if(!"".equals(nomeinRic) && !"".equals(natgiuRic)){
                  lsStr = nomeinRic.trim().toUpperCase();
                  filtroIdProgramma = filtroIdProgramma + " and exists(select 1 from UFFINT where UFFINT.CODEIN=f.CODEIN and UPPER(UFFINT.nomein) like '%"+lsStr+"%' and UFFINT.NATGIU="+natgiuRic+") ";
              } else if(!"".equals(nomeinRic)){
                  lsStr = nomeinRic.trim().toUpperCase();
                  filtroIdProgramma = filtroIdProgramma + " and exists(select 1 from UFFINT where UFFINT.CODEIN=f.CODEIN and UPPER(UFFINT.nomein) like '%"+lsStr+"%') ";
              } else if(!"".equals(natgiuRic)){
                  filtroIdProgramma = filtroIdProgramma + " and exists(select 1 from UFFINT where UFFINT.CODEIN=f.CODEIN and UFFINT.NATGIU="+natgiuRic+") ";
              }
          }
          
          if(!"".equals(conintRic)){
            filtroIdProgramma = filtroIdProgramma + " and f.conint = " + conintRic ;
          }
          if(!"".equals(codintRic)){
            filtroIdProgramma = filtroIdProgramma + " and i.codint = '" + codintRic + "'" ;
          }
          //////////////////////////////////////
          //FEBBRAIO 2019
          if(!"".equals(desintRic)){
            lsStr = desintRic.trim().toUpperCase();
            filtroIdProgramma = filtroIdProgramma + " and UPPER(i.desint) like '%"+lsStr+"%' ";
          }
          //////////////////////////////////////
          if(!"".equals(cuiintRic)){
            filtroIdProgramma = filtroIdProgramma + " and i.cuiint = '" + cuiintRic + "'" ;
          }
          if(!"".equals(tipoinRic)){
            filtroIdProgramma = filtroIdProgramma + " and i.tipoin = '" + tipoinRic + "'" ;
          }

          //////////////////////////////////////
          //FEBBRAIO 2019
          if(!"".equals(tiprogStr)){
            Long tiprog = new Long(tiprogStr);
            if(new Long(1).equals(tiprog)){
              filtroIdProgramma = filtroIdProgramma + " and i.tipint = 1" ;
            }else{
              if(new Long(2).equals(tiprog)){
                filtroIdProgramma = filtroIdProgramma + " and i.tipint = 2" ;
              }
            }
          }
          //////////////////////////////////////

          //////////////////////////////////////
          //FEBBRAIO 2019
          //...
          //////////////////////////////////////

          if(!"".equals(rupRic)){
            lsStr = rupRic.trim().toUpperCase();
            filtroIdProgramma = filtroIdProgramma + " and  exists(select 1 from TECNI where TECNI.CODTEC=i.CODRUP and UPPER(tecni.nomtec) like '%"+lsStr+"%') ";
          }               

          if(!"".equals(cdiRic)){
            String filtroTotint= "";
            if("Sopra_soglia".equals(cdiRic)){
              if("1".equals(tiprogStr)){
                filtroTotint= " and (i.totint >= 100000) ";
              }else{
                filtroTotint= " and (i.totint >= 40000) ";
              }
            }else if("Sotto_soglia".equals(cdiRic)){
              if("1".equals(tiprogStr)){
                filtroTotint= " and NOT(i.totint >= 100000) ";
              }else{
                filtroTotint= " and NOT(i.totint >= 40000) ";
              }
            }else if("Tutti".equals(cdiRic)){
              filtroTotint= "";
            }                   
            filtroIdProgramma = filtroIdProgramma + filtroTotint;
          }

          if(anntri != null){
            String filtroAilint= "";
            anntri = new Long(anntriStr);
            if("1".equals(tiprogStr)){
              filtroAilint = " and (i.ailint >= "+anntriStr+" and i.ailint <="+(anntri+2)+")" ;
            } else {
              filtroAilint = " and (i.ailint >= "+anntriStr+" and i.ailint <="+(anntri+1)+")" ;
            }
            filtroIdProgramma = filtroIdProgramma + filtroAilint;
          }

          //////////////////////////////////////
          
          ////////////////////////////////////////
          //AGOSTO 2019 - richiesta Ettore
          //ImportaProposte --> filtro su CODEIN modulabile on/off
          String filtroCodein= "";
          if(filtroCodeinAttivo) {
            filtroCodein = " and f.CODEIN = '" + codiceStazioneAppaltante + "'"; 
          }
          ////////////////////////////////////////
          //Ricavo la data iniziale del programma
          Date dataInizialeProgramma = new Date();
          
          if (anntri != null) {
        	  GregorianCalendar calendario= new GregorianCalendar(anntri.intValue(),1,1);
        	  dataInizialeProgramma = calendario.getTime();
          }
          //
          listaFabbisogni = this.sqlManager.getListVector(
              "select i.CONTRI, i.CONINT, i.CODINT, i.DESINT, i.CUIINT, i.TIPOIN, i.TOTINT, f.STATO"
              + " from INTTRI i, FABBISOGNI f "
              + " where i.CONTRI = ? and i.CONINT = f.CONINT"
              + " and (f.STATO = ? OR (f.STATO = ? AND f.DATAAVVPRO is not null AND f.DATAAVVPRO>= ?)) "
              + filtroCodein
              + filtroIdProgramma
              + " order by  i.CONINT asc",
              new Object[] { new Long(0), new Long(4), new Long(22), dataInizialeProgramma}); //Raccolta fabbisogni: modifiche per prototipo dicembre 2018 //vedi Raccolta fabbisogni.docx (mail da Lelio 26/11/2018)
        } else {
          ;
        }


        if (listaFabbisogni != null && listaFabbisogni.size() > 0) {
          request.setAttribute("listaFabbisogni", listaFabbisogni);
        }

        if("".equals(tipoForm)){
          request.setAttribute("tipoForm", "1");
          if(!filtroCodeinAttivo) {
        	  //ricavo la descrizione per la Natura Giuridica
              String descrNATGIU = (String) this.sqlManager.getObject("select coc_des from C0CAMPI where UPPER(coc_mne_uni)=?", new Object[] { "NATGIU.UFFINT.GENE" });
              List<?> listaNaturaGiuridica = sqlManager.getListVector(
                      "select tab1tip, tab1desc from tab1 where tab1cod = 'A2128' order by tab1tip",
                      new Object[] { });
              request.setAttribute("descrNATGIU", descrNATGIU);
              request.setAttribute("listaNaturaGiuridica", listaNaturaGiuridica);
          }
          
        }else{
          if("1".equals(tipoForm)){
            request.setAttribute("tipoForm", "2");
          }else{
            if("2".equals(tipoForm)){
              request.setAttribute("tipoForm", "3");
              request.setAttribute("fabbisogniDaImportare", fabbDaImportare);
            }else{

              Long contri = new Long(contriStr);


              String updateFABBISOGNI = "UPDATE FABBISOGNI SET STATO = ? WHERE CONINT = ?";
              String updateCUIFABBISOGNI = "UPDATE INTTRI SET CUIINT = ? WHERE CONTRI = ? AND CONINT = ?";
              
              String updateCUIIMTRAI = "UPDATE IMMTRAI SET CUIIMM = ? WHERE CONTRI = ? AND CONINT = ? AND NUMIMM = ?";

              status = this.sqlManager.startTransaction();

              String[] chiavi = new String[] {};
              chiavi = fabbDaImportare.split(";;");

              Long maxConint = (Long) sqlManager.getObject(
                  "select coalesce(max(conint),0) from inttri where contri= ?", new Object[] {contri});

              ////////////////////////////////////////////////////////////////////////
              //Nuova gestione Calcolo CUIIM (febbraio 2019)
              String maxCUIIM = ptManager.calcolaCUIImmobile(contri, codiceStazioneAppaltante);
              ////////////////////////////////////////////////////////////////////////
              
              for (int i = 0; i < chiavi.length; i++) {
                String istr_conint = chiavi[i];
                Long i_conint = new Long(istr_conint);
                this.sqlManager.update(updateFABBISOGNI, new Object[] {new Long(21), i_conint });
                //maxConint++;
                DataColumnContainer dccINTTRI = new DataColumnContainer(this.sqlManager,
                    "INTTRI", "select * from inttri where contri = ? and conint = ?",
                    new Object[] { new Long(0), i_conint });

                String cuiintOrig = dccINTTRI.getColumn("INTTRI.CUIINT").getValue().getStringValue();
                cuiintOrig = UtilityStringhe.convertiNullInStringaVuota(cuiintOrig);
                if("".equals(cuiintOrig)){
                  String cfein = (String) this.sqlManager.getObject("select CFEIN from UFFINT where CODEIN=?", new Object[] { codiceStazioneAppaltante });
                  String tipoin = dccINTTRI.getString("INTTRI.TIPOIN");
                  
                  if (StringUtils.isNotEmpty(cfein)) {
                    dccINTTRI.setValue("INTTRI.PRIMANN", anntri);
                    String newCuiInt = ptManager.calcolaCUIIntervento(contri, tipoin, cfein, anntriStr);
                    dccINTTRI.setValue("INTTRI.CUIINT", newCuiInt);
                    this.sqlManager.update(updateCUIFABBISOGNI, new Object[] {newCuiInt,new Long(0), i_conint });
                  }
                }else{
                  /////////////////
                  //marzo 2019
                  String selectCount = "SELECT COUNT(contri) from INTTRI " +
                  "where contri = ? and cuiint = ?";
                  Long occorrenze = (Long) sqlManager.getObject(selectCount, new Object[] { contri,  cuiintOrig});
                  if(occorrenze > 0){
                    inttriGiaInserite++;
                    continue;
                  }
                  /////////////////
                }
                
                maxConint++;
                
                /////////////////////////////////
                //febbraio 2019
                Long ailint = dccINTTRI.getLong("INTTRI.AILINT");
                Long annrif = null;
                if(ailint != null && anntri != null){
                  annrif = ailint - anntri + 1;
                  dccINTTRI.setValue("INTTRI.ANNRIF", annrif);
                  String annint = null;
                  if(annrif == 1){
                    annint = "1";
                  }else{
                    annint = "2";
                  }
                  dccINTTRI.setValue("INTTRI.ANNINT", annint);
                };
                ////////////////////////////////                              

                dccINTTRI.setValue("INTTRI.CONTRI", contri);
                //dccINTTRI.setValue("INTTRI.CONINT", maxConint);
                dccINTTRI.getColumn("INTTRI.CONTRI").setChiave(true);
                dccINTTRI.getColumn("INTTRI.CONINT").setChiave(true);
                dccINTTRI.setValue("INTTRI.NPROGINT", maxConint);

                AbstractGestoreEntita gestoreInttri = new DefaultGestoreEntitaChiaveNumerica(
                    "INTTRI", "CONINT", new String[] { "CONTRI" }, request);
                gestoreInttri.inserisci(status, dccINTTRI);
                //Long maxConint = dccINTTRI.getLong("INTTRI.CONINT");                   
                /////////////////////////////////////////////////////////////
                //febbraio 2019 
                //Se ANNRIF >1, i valori degli importi “annuali” del quadro delle risorse devono essere traslati.
                if(annrif > 1){
                  if("1".equals(tiprogStr)){
                    for(int r = 1; r < annrif; r++){
                      this.sqlManager.update(
                          "update INTTRI set " 
                          + " DV1TRI=null,DV2TRI=DV1TRI,DV3TRI=DV2TRI,DV9TRI=coalesce(DV3TRI,0)+coalesce(DV9TRI,0),"
                          + " MU1TRI=null,MU2TRI=MU1TRI,MU3TRI=MU2TRI,MU9TRI=coalesce(MU3TRI,0)+coalesce(MU9TRI,0),"
                          + " PR1TRI=null,PR2TRI=PR1TRI,PR3TRI=PR2TRI,PR9TRI=coalesce(PR3TRI,0)+coalesce(PR9TRI,0),"
                          + " BI1TRI=null,BI2TRI=BI1TRI,BI3TRI=BI2TRI,BI9TRI=coalesce(BI3TRI,0)+coalesce(BI9TRI,0),"
                          + " AP1TRI=null,AP2TRI=AP1TRI,AP3TRI=AP2TRI,AP9TRI=coalesce(AP3TRI,0)+coalesce(AP9TRI,0),"
                          + " IM1TRI=null,IM2TRI=IM1TRI,IM3TRI=IM2TRI,IM9TRI=coalesce(IM3TRI,0)+coalesce(IM9TRI,0),"
                          + " AL1TRI=null,AL2TRI=AL1TRI,AL3TRI=AL2TRI,AL9TRI=coalesce(AL3TRI,0)+coalesce(AL9TRI,0),"
                          + " DI1INT=null,DI2INT=DI1INT,DI3INT=DI2INT,DI9INT=coalesce(DI3INT,0)+coalesce(DI9INT,0) "
                          + " where CONTRI=? and CONINT=? ",
                          new Object[] { contri, maxConint } );
                    };
                  }else{
                    this.sqlManager.update(
                        "update INTTRI set " 
                        + " DV1TRI=null,DV2TRI=DV1TRI,DV9TRI=coalesce(DV2TRI,0)+coalesce(DV9TRI,0),"
                        + " MU1TRI=null,MU2TRI=MU1TRI,MU9TRI=coalesce(MU2TRI,0)+coalesce(MU9TRI,0),"
                        + " PR1TRI=null,PR2TRI=PR1TRI,PR9TRI=coalesce(PR2TRI,0)+coalesce(PR9TRI,0),"
                        + " BI1TRI=null,BI2TRI=BI1TRI,BI9TRI=coalesce(BI2TRI,0)+coalesce(BI9TRI,0),"
                        + " AP1TRI=null,AP2TRI=AP1TRI,AP9TRI=coalesce(AP2TRI,0)+coalesce(AP9TRI,0),"
                        + " IM1TRI=null,IM2TRI=IM1TRI,IM9TRI=coalesce(IM2TRI,0)+coalesce(IM9TRI,0),"
                        + " AL1TRI=null,AL2TRI=AL1TRI,AL9TRI=coalesce(AL2TRI,0)+coalesce(AL9TRI,0),"
                        + " DI1INT=null,DI2INT=DI1INT,DI9INT=coalesce(DI2INT,0)+coalesce(DI9INT,0),"
                        + " IV1TRI=null,IV2TRI=IV1TRI,IV9TRI=coalesce(IV2TRI,0)+coalesce(IV9TRI,0) "
                        + "  where CONTRI=? and CONINT=? ",
                        new Object[] { contri, maxConint } );
                  };
                };
                //////////////////////////////
                //????? SE ACQUISTI ?????
                if("2".equals(tiprogStr)){
                  this.sqlManager.update(
                      "update INTTRI set " 
                      + " DV3TRI=null,DV9TRI=coalesce(DV3TRI,0)+coalesce(DV9TRI,0),"
                      + " MU3TRI=null,MU9TRI=coalesce(MU3TRI,0)+coalesce(MU9TRI,0),"
                      + " PR3TRI=null,PR9TRI=coalesce(PR3TRI,0)+coalesce(PR9TRI,0),"
                      + " BI3TRI=null,BI9TRI=coalesce(BI3TRI,0)+coalesce(BI9TRI,0),"
                      + " AP3TRI=null,AP9TRI=coalesce(AP3TRI,0)+coalesce(AP9TRI,0),"
                      + " IM3TRI=null,IM9TRI=coalesce(IM3TRI,0)+coalesce(IM9TRI,0),"
                      + " AL3TRI=null,AL9TRI=coalesce(AL3TRI,0)+coalesce(AL9TRI,0),"
                      + " DI3INT=null,DI9INT=coalesce(DI3INT,0)+coalesce(DI9INT,0),"
                      + " IV3TRI=null,IV9TRI=coalesce(IV3TRI,0)+coalesce(IV9TRI,0) "
                      + "  where CONTRI=? and CONINT=? ",
                      new Object[] { contri, maxConint } );
                };
                //////////////////////////////////////////////////////////////
                

                List< ? > datiRIS_CAPITOLO = this.sqlManager.getListVector("select numcb from RIS_CAPITOLO" +
                    " where contri= ? and conint = ?", new Object[] { new Long(0),i_conint });
                if (datiRIS_CAPITOLO != null && datiRIS_CAPITOLO.size() > 0) {
                  for (int r = 0; r < datiRIS_CAPITOLO.size(); r++) {
                    Long r_numcb = (Long) SqlManager.getValueFromVectorParam(datiRIS_CAPITOLO.get(r), 0).getValue();
                    DataColumnContainer dccRIS_CAPITOLO = new DataColumnContainer(this.sqlManager,
                        "RIS_CAPITOLO", "select * from ris_capitolo where contri = ? and conint = ? and numcb = ? ",
                        new Object[] { new Long(0), i_conint, r_numcb });
                    dccRIS_CAPITOLO.setValue("RIS_CAPITOLO.CONTRI", contri);
                    dccRIS_CAPITOLO.setValue("RIS_CAPITOLO.CONINT", maxConint);
                    dccRIS_CAPITOLO.setOriginalValue("RIS_CAPITOLO.CONTRI", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(0)));
                    dccRIS_CAPITOLO.setOriginalValue("RIS_CAPITOLO.CONINT", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(0)));
                    dccRIS_CAPITOLO.setOriginalValue("RIS_CAPITOLO.NUMCB", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(0)));
                    dccRIS_CAPITOLO.getColumn("RIS_CAPITOLO.CONTRI").setChiave(true);
                    dccRIS_CAPITOLO.getColumn("RIS_CAPITOLO.CONINT").setChiave(true);
                    dccRIS_CAPITOLO.getColumn("RIS_CAPITOLO.NUMCB").setChiave(true);

                    AbstractGestoreEntita gestoreRisCapitolo = new DefaultGestoreEntitaChiaveNumerica(
                        "RIS_CAPITOLO", "NUMCB", new String[] { "CONTRI", "CONINT" }, request);
                    gestoreRisCapitolo.inserisci(status, dccRIS_CAPITOLO);
                    /////////////////////////////////////////////////////////////
                    //febbraio 2019 
                    //Se ANNRIF >1, i valori degli importi “annuali” del quadro delle risorse devono essere traslati.
                    if(annrif > 1){
                      Long numcb = dccRIS_CAPITOLO.getLong("RIS_CAPITOLO.NUMCB");   
                      if("1".equals(tiprogStr)){
                        for(int c = 1; c < annrif; c++){
                          this.sqlManager.update(
                              "update RIS_CAPITOLO set " 
                              + " DV1CB=null,DV2CB=DV1CB,DV3CB=DV2CB,DV9CB=coalesce(DV3CB,0)+coalesce(DV9CB,0),"
                              + " MU1CB=null,MU2CB=MU1CB,MU3CB=MU2CB,MU9CB=coalesce(MU3CB,0)+coalesce(MU9CB,0),"
                              + " BI1CB=null,BI2CB=BI1CB,BI3CB=BI2CB,BI9CB=coalesce(BI3CB,0)+coalesce(BI9CB,0),"
                              + " AP1CB=null,AP2CB=AP1CB,AP3CB=AP2CB,AP9CB=coalesce(AP3CB,0)+coalesce(AP9CB,0),"
                              + " AL1CB=null,AL2CB=AL1CB,AL3CB=AL2CB,AL9CB=coalesce(AL3CB,0)+coalesce(AL9CB,0),"
                              + " TO1CB=null,TO2CB=TO1CB,TO3CB=TO2CB,TO9CB=coalesce(TO3CB,0)+coalesce(TO9CB,0) "
                              + " where CONTRI=? and CONINT=? and NUMCB=?",
                              new Object[] { contri, maxConint, numcb } );
                        };
                      }else{
                        this.sqlManager.update(
                            "update RIS_CAPITOLO set " 
                            + " DV1CB=null,DV2CB=DV1CB,DV9CB=coalesce(DV2CB,0)+coalesce(DV9CB,0),"
                            + " MU1CB=null,MU2CB=MU1CB,MU9CB=coalesce(MU2CB,0)+coalesce(MU9CB,0),"
                            + " BI1CB=null,BI2CB=BI1CB,BI9CB=coalesce(BI2CB,0)+coalesce(BI9CB,0),"
                            + " AP1CB=null,AP2CB=AP1CB,AP9CB=coalesce(AP2CB,0)+coalesce(AP9CB,0),"
                            + " AL1CB=null,AL2CB=AL1CB,AL9CB=coalesce(AL2CB,0)+coalesce(AL9CB,0),"
                            + " TO1CB=null,TO2CB=TO1CB,TO9CB=coalesce(TO2CB,0)+coalesce(TO9CB,0),"
                            + " IV1CB=null,IV2CB=IV1CB,IV9CB=coalesce(IV2CB,0)+coalesce(IV9CB,0) "
                            + " where CONTRI=? and CONINT=? and NUMCB=?",
                            new Object[] { contri, maxConint, numcb } );
                      };
                    };
                    //////////////////////////////////////////////////////////////
                    //????? SE ACQUISTI ?????
                    if("2".equals(tiprogStr)){
                      this.sqlManager.update(
                          "update RIS_CAPITOLO set " 
                          + " DV3CB=null,DV9CB=coalesce(DV3CB,0)+coalesce(DV9CB,0),"
                          + " MU3CB=null,MU9CB=coalesce(MU3CB,0)+coalesce(MU9CB,0),"
                          + " BI3CB=null,BI9CB=coalesce(BI3CB,0)+coalesce(BI9CB,0),"
                          + " AP3CB=null,AP9CB=coalesce(AP3CB,0)+coalesce(AP9CB,0),"
                          + " AL3CB=null,AL9CB=coalesce(AL3CB,0)+coalesce(AL9CB,0),"
                          + " TO3CB=null,TO9CB=coalesce(TO3CB,0)+coalesce(TO9CB,0),"
                          + " IV3CB=null,IV9CB=coalesce(IV3CB,0)+coalesce(IV9CB,0) "
                          + "  where CONTRI=? and CONINT=? ",
                          new Object[] { contri, maxConint } );
                    };
                    //////////////////////////////////////////////////////////////
                  }
                }

                /////////////////////////////////
                //febbraio 2019
                List< ? > datiIMMTRAI = this.sqlManager.getListVector("select numimm from IMMTRAI" +
                    " where contri= ? and conint = ?", new Object[] { new Long(0),i_conint });
                if (datiIMMTRAI != null && datiIMMTRAI.size() > 0) {
                  for (int r = 0; r < datiIMMTRAI.size(); r++) {
                    Long imt_numimm = (Long) SqlManager.getValueFromVectorParam(datiIMMTRAI.get(r), 0).getValue();
                    DataColumnContainer dccIMMTRAI = new DataColumnContainer(this.sqlManager,
                        "IMMTRAI", "select * from IMMTRAI where contri = ? and conint = ? and numimm = ? ",
                        new Object[] { new Long(0), i_conint, imt_numimm });
                    
                    dccIMMTRAI.setValue("IMMTRAI.CONTRI", contri);
                    dccIMMTRAI.setValue("IMMTRAI.CONINT", maxConint);
                    dccIMMTRAI.setOriginalValue("IMMTRAI.CONTRI", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(0)));
                    dccIMMTRAI.setOriginalValue("IMMTRAI.CONINT", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(0)));
                    dccIMMTRAI.setOriginalValue("IMMTRAI.NUMIMM", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(0)));
                    dccIMMTRAI.getColumn("IMMTRAI.CONTRI").setChiave(true);
                    dccIMMTRAI.getColumn("IMMTRAI.CONINT").setChiave(true);
                    dccIMMTRAI.getColumn("IMMTRAI.NUMIMM").setChiave(true);
                   
                    /////////////////////////////////////////////////////////////////////////////////
                    String cuiimmOrig = dccIMMTRAI.getColumn("IMMTRAI.CUIIMM").getValue().getStringValue();
                    cuiimmOrig = UtilityStringhe.convertiNullInStringaVuota(cuiimmOrig);
                    if("".equals(cuiimmOrig)){
                      //Nuova gestione Calcolo CUIIM (febbraio 2019)
                      maxCUIIM = ptManager.incrementaCUIImmobile(maxCUIIM);
                      dccIMMTRAI.setValue("IMMTRAI.CUIIMM", maxCUIIM);
                      this.sqlManager.update(updateCUIIMTRAI, new Object[] {maxCUIIM, new Long(0), i_conint, imt_numimm });
                    }
                    /////////////////////////////////////////////////////////////////////////////////
                    
                    AbstractGestoreEntita gestoreImmtrai = new DefaultGestoreEntitaChiaveNumerica(
                        "IMMTRAI", "NUMIMM", new String[] { "CONTRI", "CONINT" }, request);
                    
                    gestoreImmtrai.inserisci(status, dccIMMTRAI);  
                    /////////////////////////////////////////////////////////////
                    //febbraio 2019 
                    //Se ANNRIF >1, i valori degli importi “annuali” del quadro delle risorse devono essere traslati.
                    if(annrif > 1){
                      Long numimm = dccIMMTRAI.getLong("IMMTRAI.NUMIMM");
                      if("1".equals(tiprogStr)){
                        for(int c = 1; c < annrif; c++){
                          this.sqlManager.update(
                              "update IMMTRAI set " 
                              + " VA1IMM=null,VA2IMM=VA1IMM,VA3IMM=VA2IMM,VA9IMM=coalesce(VA3IMM,0)+coalesce(VA9IMM,0) "
                              + " where CONTRI=? and CONINT=? and NUMIMM=?",
                              new Object[] { contri, maxConint, numimm} );
                        };
                      }
                    }
                    /////////////////////////////////////////////////////////////
                  }
                }
                /////////////////////////////////

                //Aggiornamento quadro delle risorse

                this.ptManager.aggiornaCostiPiatri(contri);

                //PTAPPROVAZIONI

                /////////////////////////////////////////
                //Raccolta fabbisogni: modifiche per prototipo dicembre 2018
                //vedi Raccolta fabbisogni.docx (mail da Lelio 26/11/2018)
                //INSERIRE IN W_GENCHIAVI?
                DataColumnContainer dccPTAPPROVAZIONI = null;
                Vector<DataColumn> elencoCampiPTAPPROVAZIONI = new Vector<DataColumn>();
                //Timestamp dataAttuale = new Timestamp(new Date().getTime());
                //Date dataAttuale = new Date();
                // Inserimento di un prodotto
                elencoCampiPTAPPROVAZIONI.add(new DataColumn("PTAPPROVAZIONI.CONINT", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, i_conint)));
                elencoCampiPTAPPROVAZIONI.add(new DataColumn("PTAPPROVAZIONI.DATAAPPR", new JdbcParametro(JdbcParametro.TIPO_DATA,
                    dataOdierna)));
                elencoCampiPTAPPROVAZIONI.add(new DataColumn("PTAPPROVAZIONI.FASEAPPR", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(3))));
                elencoCampiPTAPPROVAZIONI.add(new DataColumn("PTAPPROVAZIONI.ESITOAPPR", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(1))));
                elencoCampiPTAPPROVAZIONI.add(new DataColumn("PTAPPROVAZIONI.SYSAPPR", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, idUtente)));
                elencoCampiPTAPPROVAZIONI.add(new DataColumn("PTAPPROVAZIONI.UTENTEAPPR", new JdbcParametro(JdbcParametro.TIPO_TESTO, nomeUtente)));
                elencoCampiPTAPPROVAZIONI.add(new DataColumn("PTAPPROVAZIONI.NOTEAPPR", new JdbcParametro(JdbcParametro.TIPO_TESTO, noteApprovazione)));
                elencoCampiPTAPPROVAZIONI.add(new DataColumn("PTAPPROVAZIONI.ID_PROGRAMMA", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, contri)));
                elencoCampiPTAPPROVAZIONI.add(new DataColumn("PTAPPROVAZIONI.ID_INTERV_PROGRAMMA", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, maxConint)));
                dccPTAPPROVAZIONI = new DataColumnContainer(elencoCampiPTAPPROVAZIONI);
                /////////////////////////////////////////
                //...
                /////////////////////////////////////////
                AbstractGestoreEntita gestorePtApprovazioni = new DefaultGestoreEntitaChiaveNumerica(
                    "PTAPPROVAZIONI", "NUMAPPR", new String[] { "CONINT" }, request);
                gestorePtApprovazioni.inserisci(status, dccPTAPPROVAZIONI);

              }//for fabbisogni

              //this.sqlManager.commitTransaction(status);

              request.setAttribute("tipoForm", "");
            }
          }
        }
        request.setAttribute("dataOggi", dataOggi);
        
        commitTransaction = true;

      } catch (SQLException e) {
        target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
        logger.error("Errore nell'estrazione della lista dei fabbisogni", e);
        //this.aggiungiMessaggio(request, "errors.listaInterventiProgrammaPrecedente");
        commitTransaction = false;
      } catch (GestoreException e) {
        target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
        logger.error("Errore nell'estrazione della lista dei fabbisogni", e);
        commitTransaction = false;
      } finally {
        if (status != null) {
          try {
            if (commitTransaction) {
              this.sqlManager.commitTransaction(status);
            } else {
              this.sqlManager.rollbackTransaction(status);
            }
          } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
        }
      }


      if (logger.isDebugEnabled()) {
        logger.debug("runAction: fine metodo");
      }
      /////////////////
      //marzo 2019
      if (inttriGiaInserite > 0) {
        request.setAttribute("inttriGiaInserite", inttriGiaInserite);
        target = "inserisciInProgrammazione";
      }
      /////////////////
      return mapping.findForward(target);
    }

}
