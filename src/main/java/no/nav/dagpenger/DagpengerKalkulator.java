package no.nav.dagpenger;

import no.nav.grunnbeløp.GrunnbeløpVerktøy;
import no.nav.årslønn.Årslønn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Kalkulator for å beregne hvor mye dagpenger en person har rett på i Norge basert på dagens grunnbeløp (1G).
 * For at en person skal ha rett på dagpenger, må en av de to følgene kravene være møtt:
 *      De siste 3 årene må gjennomsnitslønnen være høyere enn 3G.
 *      Tjent mer det siste året enn 1.5G.
 * Hvis en person har rett på dagpenger, må følgende ting vurderes for å kalkulere dagsatsen:
 *      Hva er størst av gjennomsnittlig årslønn de 3 siste årene og siste årslønn.
 *      Hvis siste årslønn er størst, er årslønnen høyere enn 6G.
 * Antall årlige arbeidsdager i Norge er satt til å være 260, så ved beregning av dagsats må 260 dager
 * brukes og ikke 365.
 *
 * @author Emil Elton Nilsen
 * @version 1.0
 */
public class DagpengerKalkulator {

    public final GrunnbeløpVerktøy grunnbeløpVerktøy;

    public final List<Årslønn> årslønner;

    public DagpengerKalkulator() {
        this.grunnbeløpVerktøy = new GrunnbeløpVerktøy();
        this.årslønner = new ArrayList<>();
    }

    /**
     * Hvis en person har rett på dagpenger, vil den kalkulere dagsatsen en person har rett på.
     * Hvis ikke en person har rett på dagpenger, vil metoden returnere 0kr som dagsats, som en antagelse på at det
     * er det samme som å ikke ha rett på dagpenger.
     * @return dagsatsen en person har rett på.
     */
    public double kalkulerDagsats() {
        double dagsats = 0;

        int arbeidsdagerIÅret = 260;
        if (harRettigheterTilDagpenger() == true) {
            if (velgBeregningsMetode().equals("SISTE_ÅRSLØNN")) {
                dagsats = Math.ceil(hentÅrslønnVedIndeks(0).hentÅrslønn() / arbeidsdagerIÅret);
            } else if (velgBeregningsMetode().equals("GJENNOMSNITTET_AV_TRE_ÅR")) {
                dagsats = Math.ceil((summerNyligeÅrslønner(3) / 3) / arbeidsdagerIÅret);
            } else if (velgBeregningsMetode().equals("MAKS_ÅRLIG_DAGPENGERGRUNNLAG")) {
                dagsats = Math.ceil(grunnbeløpVerktøy.hentMaksÅrligDagpengegrunnlag() / arbeidsdagerIÅret);
            }
        }

        return dagsats;
    }

    /**
     * Sjekker om en person har rettighet til dagpenger eller ikke.
     * @return om personen har rett på dagpenger.
     */
    public boolean harRettigheterTilDagpenger() {
        boolean harRettigheter = false;

        if (summerNyligeÅrslønner(3) >= grunnbeløpVerktøy.hentTotaltGrunnbeløpForGittAntallÅr(3)) {
            harRettigheter = true;
        } else if (hentÅrslønnVedIndeks(0).hentÅrslønn() >= grunnbeløpVerktøy.hentMinimumÅrslønnForRettPåDagpenger()) {
            harRettigheter = true;
        }

        return harRettigheter;
    }

    /**
     * Velger hva som skal være beregnings metode for dagsats ut ifra en person sine årslønner.
     * @return beregnings metode for dagsats.
     */
    public String velgBeregningsMetode() {
        String beregningsMetode;

        if (hentÅrslønnVedIndeks(0).hentÅrslønn() > (summerNyligeÅrslønner(3) / 3)) {
           beregningsMetode = "SISTE_ÅRSLØNN";
           if (hentÅrslønnVedIndeks(0).hentÅrslønn() > grunnbeløpVerktøy.hentMaksÅrligDagpengegrunnlag()) {
               beregningsMetode = "MAKS_ÅRLIG_DAGPENGERGRUNNLAG";
           }
        } else {
            beregningsMetode = "GJENNOMSNITTET_AV_TRE_ÅR";
        }

        return beregningsMetode;
    }

    public void leggTilÅrslønn(Årslønn årslønn) {
        this.årslønner.add(årslønn);
        this.sorterÅrslønnerBasertPåNyesteÅrslønn();
    }

    /**
     * Henter årslønnen i registeret basert på dens posisjon i registeret ved gitt indeks.
     * @param indeks posisjonen til årslønnen.
     * @return årslønnen ved gitt indeks.
     */
    public Årslønn hentÅrslønnVedIndeks(int indeks) {
        return this.årslønner.get(indeks);
    }

    /**
     * Summemer sammen antall årslønner basert på gitt parameter.
     * @param antallÅrÅSummere antall år med årslønner vi vil summere.
     * @return summen av årslønner.
     */
    public double summerNyligeÅrslønner(int antallÅrÅSummere) {
        double sumAvNyligeÅrslønner = 0;

        if (antallÅrÅSummere <= this.årslønner.size()) {
            List<Årslønn> subÅrslønnListe = new ArrayList<>(this.årslønner.subList(0, antallÅrÅSummere));

            for (Årslønn årslønn : subÅrslønnListe) {
                sumAvNyligeÅrslønner += årslønn.hentÅrslønn();
            }
        }

        return sumAvNyligeÅrslønner;
    }

    /**
     * Sorterer registeret slik at den nyligste årslønnen er det først elementet i registeret.
     * Først blir årslønnene i registeret sortert ut at den eldstre årslønnen skal først i registeret,
     * deretter blir registeret reversert.
     */
    public void sorterÅrslønnerBasertPåNyesteÅrslønn() {
        this.årslønner.sort(Comparator.comparingInt(Årslønn::hentÅretForLønn));
        Collections.reverse(this.årslønner);
    }
}
