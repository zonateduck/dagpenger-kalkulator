package no.nav.dagpenger;

/**
 * Spesialiseringskategorier som en saksbehandler kan ha.
 * En spesialisering tilsier hva slags resultater en saksbehandler kan behandle.
 * Spesialiseringene er:
 * - Avslag på grunn av for lav inntekt
 * - Innvilget
 * - Innvilget med makssats
 *
 * @author Mila Toneff
 * @version 1.0
 */
public enum Spesialisering {
    AVSLAG_FOR_LAV_INNTEKT,
    INNVILGET,
    INNVILGET_MED_MAKSSATS
}