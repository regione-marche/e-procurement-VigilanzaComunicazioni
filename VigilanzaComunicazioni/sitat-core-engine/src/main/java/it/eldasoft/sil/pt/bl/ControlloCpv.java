/**
 * 
 */
package it.eldasoft.sil.pt.bl;


import it.eldasoft.utils.utility.UtilityStringhe;

/**
 * @authors Roberto
 * 
 */
public class ControlloCpv {

  private static boolean uguale(String valore, String condizione) {
    if (valore != null
        && condizione != null
        && !valore.trim().equals("")
        && !condizione.trim().equals("")
        && valore.equals(condizione)) return true;
    return false;
  }

  private static boolean compreso(String valore, String inizio, String fine) {
    if (valore != null
        && inizio != null
        && fine != null
        && !valore.trim().equals("")
        && !inizio.trim().equals("")
        && !fine.trim().equals("")
        && Long.parseLong(UtilityStringhe.replace(valore.trim(), "-", "")) >= Long.parseLong(UtilityStringhe.replace(
            inizio.trim(), "-", ""))
        && Long.parseLong(UtilityStringhe.replace(valore.trim(), "-", "")) <= Long.parseLong(UtilityStringhe.replace(
            fine.trim(), "-", ""))) return true;
    return false;
  }

  public static boolean controlloCpv(String valore, String[] rangeCpv) {

    boolean trovato = false;
    for (int i = 0; i < rangeCpv.length; i++) {
      if (rangeCpv[i].indexOf(":") != -1) {
        // compreso tra due valori
        if (rangeCpv[i].indexOf("!") != -1) {
          if (compreso(valore, rangeCpv[i].substring(1).split(":")[0],
              rangeCpv[i].substring(1).split(":")[1])) return false;
        } else {
          if (!compreso(valore, rangeCpv[i].split(":")[0],
              rangeCpv[i].split(":")[1]))
            trovato = trovato || false;
          else
            trovato = trovato || true;
        }
      } else {
        // confronto singolo valore
        if (rangeCpv[i].indexOf("!") != -1) {
          if (uguale(valore, rangeCpv[i].substring(1))) return false;
        } else {
          if (!uguale(valore, rangeCpv[i]))
            trovato = trovato || false;
          else
            trovato = trovato || true;
        }
      }
    }
    return trovato;
  }

}
