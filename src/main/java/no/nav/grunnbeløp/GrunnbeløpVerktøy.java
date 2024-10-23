package no.nav.grunnbeløp;

import java.io.IOException;

/**
 * Verktøy med forskjellige hjelpemetoder til å kalkulere forskjellige grunnbeløpsverdier, som
 * bruker i prossesen for å kalkulere hvilken dagsats en person har rett på. Grunnbeløpet brukt
 * i disse metodene hente fra NAV sitt grunnebeløp API.
 *
 * @author Emil Elton Nilsen
 * @version 1.0
 */
public class GrunnbeløpVerktøy {

    private double grunnbeløp;

    public GrunnbeløpVerktøy() {
        try {
            this.grunnbeløp = new GrunnbeløpAPI().hentGrunnbeløp();
        } catch (IOException | InterruptedException exception) {
            System.out.println("Problemer med tilkobling til grunnbeløp API'et" + exception.getMessage());
        }
    }

    /**
     * Kalkulerer det totale grunnbeløpet for gitt antall år.
     * @param antallÅr antall år med grunnbeløp å kalkulere.
     * @return grunnbeløpet over gitt antall år.
     */
    public double hentTotaltGrunnbeløpForGittAntallÅr(int antallÅr) {
        return this.grunnbeløp * antallÅr;
    }

    /**
     * Kalkulerer hvor mye en person må tjene det siste året for å ha rett på dagpenger.
     * @return 1.5G basert på dagens grunnbeløp.
     */
    public double hentMinimumÅrslønnForRettPåDagpenger() {
        return this.grunnbeløp * 1.5;
    }

    /**
     * Kalkulerer hvor høyt maks årlig dagpengegrunnlag kan være.
     * @return 6G basert på dagens grunnbeløp.
     */
    public double hentMaksÅrligDagpengegrunnlag() {
        return this.grunnbeløp * 6;
    }
}
