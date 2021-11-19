package it.eldasoft.w9.web.struts;

import java.io.Serializable;

/**
 * Bean che estende l'oggetto Account di Gene per memorizzare attributi specifici
 * dell'accesso con IDM-RAS 
 * 
 * @author Luca.Giacomazzo
 */
public class AccountIDMRAS implements Serializable {
  
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

  
  public AccountIDMRAS() {
    this.idmLogin = false;

    this.username = null;
    this.nome = null;
    this.cognome = null;
    this.email = null;
    this.telefono = null;
  }
    
  private String username;
  private String nome;
  private String cognome;
  private String email;
  private String telefono;
  private boolean idmLogin;
  
  public void setIdmLogin(boolean idmLogin) {
    this.idmLogin = idmLogin;
  }

  public boolean isIdmLogin() {
    return idmLogin;
  }

  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }

  public String getTelefono() {
    return telefono;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getUsername() {
    return username;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getNome() {
    return nome;
  }

  public void setCognome(String cognome) {
    this.cognome = cognome;
  }

  public String getCognome() {
    return cognome;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getEmail() {
    return email;
  }
  
}
