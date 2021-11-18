package it.eldasoft.w9.tags.gestori.submit;

import it.eldasoft.gene.bl.LoginManager;
import it.eldasoft.gene.commons.web.domain.CostantiGenerali;
import it.eldasoft.gene.commons.web.domain.ProfiloUtente;
import it.eldasoft.gene.db.datautils.DataColumn;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.domain.admin.Account;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.web.struts.tags.gestori.AbstractGestoreChiaveNumerica;
import it.eldasoft.gene.web.struts.tags.gestori.DefaultGestoreEntitaChiaveNumerica;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.utils.sicurezza.CriptazioneException;
import it.eldasoft.utils.spring.UtilitySpring;

import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.TransactionStatus;

public class GestoreAVVISO extends AbstractGestoreChiaveNumerica {

  @Override
  public String[] getAltriCampiChiave() {
    return new String[] { "CODEIN", "CODSISTEMA" };
  }

  @Override
  public String getCampoNumericoChiave() {
    return "IDAVVISO";
  }

  @Override
  public String getEntita() {
    return "AVVISO";
  }

  @Override
  public void preInsert(TransactionStatus status, DataColumnContainer impl)
  throws GestoreException {
	  ProfiloUtente profilo = (ProfiloUtente) this.getRequest().getSession().getAttribute(CostantiGenerali.PROFILO_UTENTE_SESSIONE);
	  impl.addColumn("AVVISO.SYSCON", new Long(profilo.getId()));

    super.preInsert(status, impl);
    //creo il rup se non esiste
    if (impl.isColumn("AVVISO.RUP") && impl.getColumn("AVVISO.RUP").getValue().getValue() == null) {
    	String codUffInt = (String) this.getRequest().getSession().getAttribute(
    	          CostantiGenerali.UFFICIO_INTESTATARIO_ATTIVO);
    	ProfiloUtente profiloUtente = (ProfiloUtente) this.getRequest().getSession().getAttribute(
    	          CostantiGenerali.PROFILO_UTENTE_SESSIONE);
        try {
        	String codTec = (String) this.sqlManager.getObject(
                    "select CODTEC from TECNI where SYSCON is null and CGENTEI=? and " +
                    "UPPER(CFTEC)=(select UPPER(SYSCF) from USRSYS where SYSCON=? and SYSCF is not null)",
                    new Object[] { codUffInt, new Long(profiloUtente.getId()) } );
        	if (StringUtils.isNotEmpty(codTec)) {
                this.sqlManager.update("update TECNI set SYSCON=? where CODTEC=?",
                    new Object[] { new Long(profiloUtente.getId()), codTec });
                impl.getColumn("AVVISO.RUP").setValue(
                        new JdbcParametro(JdbcParametro.TIPO_TESTO, codTec));
                    
              } else {
            	  LoginManager loginManager = (LoginManager) UtilitySpring.getBean("loginManager",
            	          getServletContext(), LoginManager.class);
            	  Account account = loginManager.getAccountById(profiloUtente.getId());
            	  DataColumn uffint = new DataColumn("TECNI.CGENTEI", new JdbcParametro(
            			  JdbcParametro.TIPO_TESTO, codUffInt));
            	  DataColumn nometec = new DataColumn("TECNI.NOMTEC", new JdbcParametro(
                          JdbcParametro.TIPO_TESTO, account.getNome()));
                  DataColumn syscon = new DataColumn("TECNI.SYSCON", new JdbcParametro(
                          JdbcParametro.TIPO_NUMERICO, new Long(account.getIdAccount())));
                  String pk = this.geneManager.calcolaCodificaAutomatica("TECNI", "CODTEC");

                  impl.getColumn("AVVISO.RUP").setValue(
                		  new JdbcParametro(JdbcParametro.TIPO_TESTO, pk));
                      
                  DataColumn codiceTecnico = new DataColumn("TECNI.CODTEC",
                          new JdbcParametro(JdbcParametro.TIPO_TESTO, pk));
                      
                  DataColumnContainer dcc = new DataColumnContainer(new DataColumn[] {
                          codiceTecnico, uffint, nometec, syscon });

                  if (StringUtils.isNotEmpty(account.getCodfisc())) {
                	  dcc.addColumn("TECNI.CFTEC", new JdbcParametro(
                      JdbcParametro.TIPO_TESTO, account.getCodfisc()));
                  }
                      
                  if (StringUtils.isNotEmpty(account.getEmail())) { 
                      dcc.addColumn("TECNI.EMATEC", JdbcParametro.TIPO_TESTO, account.getEmail());
                  }
                      
                  dcc.getColumn("TECNI.CODTEC").setChiave(true);
                  dcc.insert("TECNI", this.sqlManager);
              }
        } catch(CriptazioneException ce) {
          throw new GestoreException("", null, ce);
        } catch(SQLException se) {
          throw new GestoreException("", null, se);
        }
    }
  }

  @Override
  public void postInsert(DataColumnContainer impl) throws GestoreException {

  }

  @Override
  public void preUpdate(TransactionStatus status, DataColumnContainer impl)
  throws GestoreException {
	  // Gestore per il salvataggio dei documenti
	  GestoreW9DOCAVVISO gestoreW9DOCAVVISO = new GestoreW9DOCAVVISO();
	  AbstractGestoreChiaveNumerica gestore = new DefaultGestoreEntitaChiaveNumerica("W9DOCAVVISO", "NUMDOC", new String[] { "CODEIN", "IDAVVISO", "CODSISTEMA" }, this.getRequest());
	  gestoreW9DOCAVVISO.setForm(this.getForm());

	  gestoreW9DOCAVVISO.gestisciAggiornamentiRecordSchedaMultipla(status, impl, gestore, "W9DOCAVVISO",
				new DataColumn[] { impl.getColumn("AVVISO.CODEIN"), impl.getColumn("AVVISO.IDAVVISO"), impl.getColumn("AVVISO.CODSISTEMA") }, null);
  }

  @Override
  public void postUpdate(DataColumnContainer impl) throws GestoreException {

  }

  @Override
  public void preDelete(TransactionStatus status, DataColumnContainer impl)
  throws GestoreException {
	  // cancello tutti i documenti dell'avviso
	  try {
		  sqlManager.update("DELETE W9DOCAVVISO WHERE CODEIN = ? AND IDAVVISO = ? AND CODSISTEMA = ?", new Object[] { impl.getColumn("AVVISO.CODEIN"), impl.getColumn("AVVISO.IDAVVISO"), impl.getColumn("AVVISO.CODSISTEMA") });
	  } catch (SQLException e) {
		  throw new GestoreException("Errore nell'eliminazione della documentazione dell'avviso ", "", e);
	  }
  }

  @Override
  public void postDelete(DataColumnContainer impl) throws GestoreException {

  }

  @Override
  public void afterInsertEntita(TransactionStatus status, DataColumnContainer impl) throws GestoreException {
      GestoreW9DOCAVVISO gestoreW9DOCAVVISO= new GestoreW9DOCAVVISO();
      AbstractGestoreChiaveNumerica gestore = new DefaultGestoreEntitaChiaveNumerica(
          "W9DOCAVVISO", "NUMDOC", new String[] { "CODEIN", "IDAVVISO", "CODSISTEMA" }, this.getRequest());
      gestoreW9DOCAVVISO.setForm(this.getForm());
  
      gestoreW9DOCAVVISO.gestisciAggiornamentiRecordSchedaMultipla(status, impl, gestore, "W9DOCAVVISO",
          new DataColumn[] { impl.getColumn("AVVISO.CODEIN"), impl.getColumn("AVVISO.IDAVVISO"), impl.getColumn("AVVISO.CODSISTEMA") }, null);
  }

}
