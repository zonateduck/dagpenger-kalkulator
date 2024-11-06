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

    /**
     * Oppretter en ny saksbehandler.
     * @param spesialisering Spesialisering for saksbehandleren.
    */
    public Saksbehandler(Spesialisering spesialisering) {
        this.spesialisering = spesialisering;
    }

    /**
     * Behandler resultat fra kalkulering av dagpenger til en person.
     * Sjekker om resulatet er innenfor spesialiseringen til saksbehandleren:
     * Setter godkjenning basert på den gjeldende spesialiseringen.
     * @param resultat Resultatet som skal vurderes.
     * @return True om resultatet er godkjent av saksbehandleren, ellers False.
    */
    public boolean behandleResultat(Resultat resultat) {
        /* Sjekker om resultatet har riktig spesialisering */
        if (resultat.hentSpesialisering() == this.spesialisering) {
            /* Setter godkjenning basert på spesialiseringen og returnerer */
            resultat.settGodkjenning(spesialisering.erGodkjent());
            return resultat.erGodkjent();
        }

        /* Returnerer False hvis spesialisering ikke samsvarte */
        return false;
    }

    /**
     * Henter spesialiseringen saksbehandleren er satt til.
     * @return spesialisering Spesialiseringen til saksbehandleren.
    */
    public Spesialisering hentSpesialisering() {
        return this.spesialisering;
    }
}