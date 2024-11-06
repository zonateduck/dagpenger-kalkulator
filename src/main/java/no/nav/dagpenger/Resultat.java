package no.nav.dagpenger;

/**
 * Resultat fra kalkulering gjennom DagpengerKalkulator
 * Et resultat inneholder informasjon om hvis en beregning gir rett på dagpenger,
 * hvilken spesialisering resultatet tilhører, og om den har blitt godkjent av en
 * saksbehandler eller ikke.
 * 
 * Klassen inneholder informasjon om:
 * - Inntekten som ble brukt i beregningen.
 * - Status for resultatet (innvilget, innvilget med makssats, eller avslag).
 * - Om resultatet har blitt godkjent av en saksbehandler.
 *
 * @author Mila Toneff
 * @version 1.0
 */
public class Resultat {
    /* Beregnet dagsats hentet fra kalkulatoren */
    private final double beregnetDagsats;
    /* Spesialisering resultatet ligger innenfor */
    private final Spesialisering spesialisering;
    /* Hvorvidt resultatet har blitt godkjent av en saksbehandler */
    private boolean erGodkjent;

    /**
     * Oppretter et nytt resultat med beregnet dagsats og spesialisering.
     * @param beregnetDagsats Dagsatsen som er beregnet.
     * @param spesialisering Spesialiseringen for resultatet.
     */
    public Resultat(double beregnetDagsats, Spesialisering spesialisering) {
        this.beregnetDagsats = beregnetDagsats;
        this.spesialisering = spesialisering;
        this.erGodkjent = spesialisering.erGodkjent();
    }

    /**
     * Henter den beregnede dagsatsen.
     * @return Beregnet dagsats.
     */
    public double hentBeregnetDagsats() {
        return beregnetDagsats;
    }

    /**
     * Henter spesialiseringen knyttet til resultatet.
     * @return Spesialiseringen.
     */
    public Spesialisering hentSpesialisering() {
        return spesialisering;
    }

    /**
     * Sjekker om resultatet er godkjent.
     * @return True hvis resultatet er godkjent, ellers false.
     */
    public boolean erGodkjent() {
        return erGodkjent;
    }

    /**
     * Setter resultatet som godkjent eller avslått av saksbehandleren.
     * @param erGodkjent Om resultatet er godkjent.
     */
    public void settGodkjenning(boolean erGodkjent) {
        this.erGodkjent = erGodkjent;
    }
}