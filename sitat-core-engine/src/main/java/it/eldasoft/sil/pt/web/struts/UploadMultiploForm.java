package it.eldasoft.sil.pt.web.struts;

import it.eldasoft.gene.commons.web.struts.UploadFileForm;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

/**
 * Form bean for a Struts application.
 * Users may access 1 field on this form:
 * <ul>
 * <li>testFile - [your comment here]
 * </ul>
 * @version 	1.0
 * @author      Luca Giacomazzo
 */
public class UploadMultiploForm extends UploadFileForm {

	/**file associato al form. */
  private FormFile selFile   = null;
  /**raccolta file associato al form. */
  private HashMap  formFiles = null;
  /**indice file associato al form. */
  private int      index;

  /**
   * Constructor!
   */
  public UploadMultiploForm() {
    formFiles = new HashMap(100);
    index = 0;
  }

  /**
   * Get FormFiles.
   * 
   * @return ArrayList
   */
  public HashMap getFormFiles() {
    return formFiles;
  }

  /**
   * Get getSelFile.
   * 
   * @param in
   * 		indice del file da recuperarte
   * @return FormFile
   */
  public FormFile getSelFile(int in) {
    return selFile;
  }

  /**
   * Set testFile.
   * @param in
   * 		indice del file da recuperarte
   * @param t
   * 		Form File
   * @param <code>FormFile</code>
   */
  public void setSelFile(int in, FormFile t) {
    try {
      this.selFile = t;
      setFormFiles(t, in);
      index++;
    } catch (Exception e) {
      System.out.println("Exception in setTestFile!" + e);
    }
  }

  /**
   * Set FormFile.
   * @param i
   * 		indice del file da impostare
   * @param t
   * 		Form File
   */
  public void setFormFiles(FormFile t, int i) {
    this.formFiles.put(new Long(i), t);
  }

  /**
   * Reset.
   * @param mapping
   * 		mapping
   * @param request
   * 		HttpServletRequest
   */
  public void reset(ActionMapping mapping, HttpServletRequest request) {
    selFile = null;
  }

  /**
   * Reset.
   * @param mapping
   * 		mapping
   * @param request
   * 		HttpServletRequest
   * @return ActionErrors
   */
  public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {

    ActionErrors errors = new ActionErrors();
    return errors;
  }

}