package no.nav.dagpenger;

/**
 * Spesialiseringskategorier som en saksbehandler kan ha.
 * En spesialisering tilsier hva slags resultater en saksbehandler kan behandle.
 * Spesialiseringene er:
 * - Avslag på grunn av for lav inntekt (Avslått - false)
 * - Innvilget (Godkjent - true)
 * - Innvilget med makssats (Godkjent - true)
 *
 * @author Mila Toneff
 * @version 1.0
 */
public enum Spesialisering {
    /* Spesialisering for avlag på grunn av for lav inntekt */
    AVSLAG_FOR_LAV_INNTEKT(false),
    /* Spesialisering for innvilgelse */
    INNVILGET(true),
    /* Spesialisering for innvilgelse ved makssats */
    INNVILGET_MED_MAKSSATS(true)

    /* Variabel som indikerer om spesialiseringen skal gi
    godkjent resultat */
    private final boolean godkjent;

    /* Konstruktør til spesialiseringsklassen */
    Spesialisering(boolean godkjent) {
        this.godkjent = godkjent;
    }

    /**
     * Metode for å hente godkjenning for spesialiseringen.
     * @return Godkjent-verdien.
     */
    public boolean erGodkjent() {
        return this.godkjent;
    }
}