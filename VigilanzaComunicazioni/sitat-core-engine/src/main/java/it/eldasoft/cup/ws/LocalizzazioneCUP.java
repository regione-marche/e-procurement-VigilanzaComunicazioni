/**
 * Localizzazione.java
 *
 */

package it.eldasoft.cup.ws;

public class LocalizzazioneCUP  implements java.io.Serializable {

    private java.lang.String stato;
    private java.lang.String regione;
    private java.lang.String provincia;
    private java.lang.String comune;




    public LocalizzazioneCUP() {
    }

    public LocalizzazioneCUP(
        java.lang.String stato,
        java.lang.String regione,
        java.lang.String provincia,
        java.lang.String comune
        )
    {
           this.stato = stato;
           this.regione = regione;
           this.provincia = provincia;
           this.comune = comune;
    }


    /**
     * Gets the stato value for this LocalizzazioneCUP.
     *
     * @return stato
     */
    public java.lang.String getStato() {
        return stato;
    }


    /**
     * Sets the stato value for this DettaglioCUP.
     *
     * @param stato
     */
    public void setStato(java.lang.String stato) {
        this.stato = stato;
    }

    /**
     * Gets the regione value for this LocalizzazioneCUP.
     *
     * @return regione
     */
    public java.lang.String getRegione() {
        return regione;
    }


    /**
     * Sets the regione value for this DettaglioCUP.
     *
     * @param regione
     */
    public void setRegione(java.lang.String regione) {
        this.regione = regione;
    }
    /**
     * Gets the provincia value for this LocalizzazioneCUP.
     *
     * @return provincia
     */
    public java.lang.String getProvincia() {
        return provincia;
    }


    /**
     * Sets the provincia value for this DettaglioCUP.
     *
     * @param provincia
     */
    public void setProvincia(java.lang.String provincia) {
        this.provincia = provincia;
    }


    /**
     * Gets the comune value for this LocalizzazioneCUP.
     *
     * @return comune
     */
    public java.lang.String getComune() {
        return comune;
    }


    /**
     * Sets the comune value for this DettaglioCUP.
     *
     * @param comune
     */
    public void setComune(java.lang.String comune) {
        this.comune = comune;
    }


}
