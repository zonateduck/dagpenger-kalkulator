package no.nav.dagpenger;

/**
 * Saksbehandler som godkjenner kalkulasjoner for dagpenger.
 * En saksbehandler har en egen spesialisering, og godkjenner eller avslår resultater innenfor sin spesialisering. 
 * Spesialiseringene er:
 * - Avslag på grunn av for lav inntekt
 * - Innvilget
 * - Innvilget med makssats
 *
 * @author Mila Toneff
 * @version 1.0
 */
public class Saksbehandler {

    public Saksbehandler() {}

    public hentSpesialisering() {}
    public behandleResultat() {}

}