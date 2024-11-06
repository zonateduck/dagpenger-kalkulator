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

    /* Spesialiseringen til saksbehandleren */
    private spesialisering Spesialisering;

    public Saksbehandler(Spesialisering spesialisering) {

        this.spesialisering = spesialisering;

    }

    /**
     * Behandler resultat fra kalkulering av dagpenger til en person.
     * Sjekker om resulatet er innenfor spesialiseringen til saksbehandleren:
     * Setter godkjenning basert på den gjeldende spesialiseringen.
     * @param resultat resultatet som skal vurderes.
     * @return om resultatet er godkjent av saksbehandleren.
    */
    public boolean behandleResultat(Resultat resultat) {

        return false;

    }

    /**
     * Henter spesialiseringen saksbehandleren er satt til.
     * @return spesialiseringen.
    */
    public Spesialisering hentSpesialisering() {

        return this.spesialisering;

    }

}