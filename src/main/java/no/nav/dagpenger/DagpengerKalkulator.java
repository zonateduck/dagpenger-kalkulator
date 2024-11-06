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
 * - De siste 3 årene må gjennomsnitslønnen være høyere enn 3G.
 * - Tjent mer det siste året enn 1.5G.
 * 
 * Hvis en person har rett på dagpenger, må følgende ting vurderes for å kalkulere dagsatsen:
 * - Hva er størst av gjennomsnittlig årslønn de 3 siste årene og siste årslønn.
 * - Hvis siste årslønn er størst, er årslønnen høyere enn 6G.
 * Antall årlige arbeidsdager i Norge er satt til å være 260, så ved beregning av dagsats må 260 dager
 * brukes og ikke 365.
 *
 * @author Emil Elton Nilsen
 * refractored by Mila Toneff
 * @version 1.1
 */
public class DagpengerKalkulator {

    /* Konstanter til koden for enkel gjenbruk */
    private static final int ARBEIDSDAGER_I_ÅRET = 260;
    private static final String SISTE_ÅRSLØNN = "SISTE_ÅRSLØNN";
    private static final String GJENNOMSNITT_AV_TRE_ÅR = "GJENNOMSNITTET_AV_TRE_ÅR";
    private static final String MAKS_ÅRLIG_DAGPENGERGRUNNLAG = "MAKS_ÅRLIG_DAGPENGERGRUNNLAG";

    /* Verktøy og register av årslønn som lages
    ved oppretting av klasseinstans. */
    public final GrunnbeløpVerktøy grunnbeløpVerktøy;
    public final List<Årslønn> årslønner;

    /* Konstruktør som oppretter en kalkulator */
    public DagpengerKalkulator() {
        this.grunnbeløpVerktøy = new GrunnbeløpVerktøy();
        this.årslønner = new ArrayList<>();
    }

    /**
     * Hvis en person har rett på dagpenger, vil den kalkulere dagsatsen en person har rett på.
     * Hvis ikke en person har rett på dagpenger, vil metoden returnere 0kr som dagsats, som en antagelse på at det
     * er det samme som å ikke ha rett på dagpenger.
     * @return Dagsatsen en person har rett på.
     */
    public double kalkulerDagsats() {
        /* Sjekker først om personen har rett til dagpenger.
        Returnerer 0 hvis ikke. */
        if (!harRettigheterTilDagpenger()) {
            return 0;
        }

        /* Henter siste årslønn for beregning. */
        double sisteÅrslønn = hentÅrslønnVedIndeks(0).hentÅrslønn();
        /* Regner ut gjennomsnittet av de tre siste årslønnene. */
        double gjennomsnittTreÅr = summerNyligeÅrslønner(3) / 3;
        /* Henter maks årlig grunnlag for dagpenger til sammenligning */
        double maksÅrligDagpengegrunnlag = grunnbeløpVerktøy.hentMaksÅrligDagpengegrunnlag();

        /* Velger beregningsmetode basert på data til en person */
        String beregningsMetode = velgBeregningsMetode();

        /* Beregner dagsats basert på valgt metode
        (siste årslønn, gjennomsnitt av tre år eller arbeidsdager i året): */
        if (beregningsMetode.equals(SISTE_ÅRSLØNN)) {
            /* Returnerer dagsats basert på siste årslønn. */
            return Math.ceil(sisteÅrslønn / ARBEIDSDAGER_I_ÅRET);
        }
        
        if (beregningsMetode.equals(GJENNOMSNITT_AV_TRE_ÅR)) {
            /* Returnerer dagsats basert på gjennomsnittet av de siste tre årene. */
            return Math.ceil(gjennomsnittTreÅr / ARBEIDSDAGER_I_ÅRET);
        }
        
        if (beregningsMetode.equals(MAKS_ÅRLIG_DAGPENGERGRUNNLAG)) {
            /* Returnerer dagsats basert på maks årlig dagpengegrunnlag. */
            return Math.ceil(maksÅrligDagpengegrunnlag / ARBEIDSDAGER_I_ÅRET);
        }

        /* Returnerer 0 hvis ingen beregningsmetode passer */
        return 0;
    }

    /**
     * Sjekker om en person har rettighet til dagpenger eller ikke.
     * @return True om personen har rett på dagpenger, ellers False.
     */
    public boolean harRettigheterTilDagpenger() {
        /* Sjekker om summen av de siste tre års lønninger
        overstiger kravet til dagpenger (3G). */
        if (summerNyligeÅrslønner(3) >= grunnbeløpVerktøy.hentTotaltGrunnbeløpForGittAntallÅr(3)) {
            return true;
        }
        /* Sjekker om den siste årslønnen er høy nok (1.5G) for rett til dagpenger. */
        if (hentÅrslønnVedIndeks(0).hentÅrslønn() >= grunnbeløpVerktøy.hentMinimumÅrslønnForRettPåDagpenger()) {
            return true;
        }

        /* Returnerer false hvis ingen av betingelsene blir oppfylt
        Altså at personen ikke har rett på dagpenger */
        return false;
    }

    /**
     * Velger hva som skal være beregnings metode for dagsats ut ifra en person sine årslønner.
     * @return Beregningsmetode for dagsats.
     */
    public String velgBeregningsMetode() {
        // Henter siste årslønn og lagrer den i en variabel for å unngå gjentatte kall.
        double årslønnVedIndeks = hentÅrslønnVedIndeks(0).hentÅrslønn();
    
        // Sjekker om gjennomsnittet av de siste tre årene er større eller lik den siste årslønnen.
        if (årslønnVedIndeks <= (summerNyligeÅrslønner(3) / 3)) {
            return GJENNOMSNITT_AV_TRE_ÅR;
        }
    
        // Sjekker om den siste årslønnen overstiger det maksimale årlige dagpengegrunnlaget.
        if (årslønnVedIndeks > grunnbeløpVerktøy.hentMaksÅrligDagpengegrunnlag()) {
            return MAKS_ÅRLIG_DAGPENGERGRUNNLAG;
        }
    
        // Returnerer "SISTE_ÅRSLØNN" hvis ingen av de andre betingelsene er oppfylt.
        return SISTE_ÅRSLØNN;
    }

    /**
     * Legger til gitt årslønn i registeret av årslønner.
     * @param årslønn Årslønn til en person.
     */
    public void leggTilÅrslønn(Årslønn årslønn) {
        /* Feilhåndtering: Kaster unntak hvis årslønn er null for å sikre gyldige data. */
        if (årslønn == null) {
            throw new IllegalArgumentException("Årslønn kan ikke være uten verdi (null).");
        }
        // Legger til årslønn i listen.
        this.årslønner.add(årslønn);
        // Sorterer listen slik at den nyeste årslønnen er først.
        this.sorterÅrslønnerBasertPåNyesteÅrslønn();
    }

    /**
     * Henter årslønnen i registeret basert på dens posisjon i registeret ved gitt indeks.
     * @param indeks Posisjonen til årslønnen.
     * @return Årslønnen ved gitt indeks.
     */
    public Årslønn hentÅrslønnVedIndeks(int indeks) {
        /* Feilhåndtering:
         * Kaster unntak hvis gitt indeks er 0 eller mindre
         * eller hvis den overskrider antall eksisterende årslønner.
         * Gir feilmelding som indikerer at antallet må være større enn null
         * eller mindre enn eksisterende årslønner. */
        if (indeks < 0 || indeks >= årslønner.size()) {
            throw new IndexOutOfBoundsException("Indeks må være mellom 0 og " + (this.årslønner.size() - 1) + ".");
        }
        /* Returnerer årslønnen som ble funnet på indeksen. */
        return this.årslønner.get(indeks);
    }

    /**
     * Summemer sammen antall årslønner basert på gitt parameter.
     * @param antallÅrÅSummere Antall år med årslønner vi vil summere.
     * @return Summen av årslønner.
     */
    public double summerNyligeÅrslønner(int antallÅrÅSummere) {
        /* Feilhåndtering 1:
         * Kaster unntak hvis gitt antall 'antallÅrÅSummere' er 0 eller mindre.
         * Gir feilmelding som indikerer at antallet må være større enn null.
         */
        if (antallÅrÅSummere <= 0) {
            throw new IllegalArgumentException("Antall år må være større enn null.");
        }

        /* Utgangspunkt for sum av nylige årslønner.
        Skal oppdateres når verdiene leses av senere. */
        double sumAvNyligeÅrslønner = 0;

        /* Løsning for feilhåndtering 2:
         * Setter maks antall år å summeres til antall eksisterende årslønner
         * hvis gitt antall overskrider tilgjengelig kapasitet.
         */
        int maksÅr = Math.min(antallÅrÅSummere, this.årslønner.size());

        /* Itererer gjennom de tilgjengelige årslønnene opp til
        maks antall år og summerer dem. */
        for (int indeks = 0; indeks < maksÅr; indeks++) {  
            sumAvNyligeÅrslønner += this.årslønner.get(indeks).hentÅrslønn();
        }

        /* Alternativ løsning for feilhåndtering 2:
         * Kaster unntak hvis gitt antall overskrider tilgjengelig kapasitet.
         * Gir feilmelding som indikerer at antallet er for høyt. 
         */

         /* if (antallÅrÅSummere > this.årslønner.size()) {
            throw new IndexOutOfBoundsException("Antall år oppgitt overskrider antall mulige år å summere.");
          }
          
          for (int indeks = 0; indeks < this.antallÅrÅSummere; indeks++) {
            sumAvNyligeÅrslønner += this.årslønner.get(indeks).hentÅrslønn();
          }
          */

        /* Returnerer funnet totalsum av nylige årslønner. */
        return sumAvNyligeÅrslønner;
    }

    /**
     * Sorterer registeret slik at den nyligste årslønnen er det først elementet i registeret.
     * Først blir årslønnene i registeret sortert ut at den eldstre årslønnen skal først i registeret,
     * deretter blir registeret reversert.
     */
    public void sorterÅrslønnerBasertPåNyesteÅrslønn() {
        // Sorterer listen basert på året for hver årslønn i stigende rekkefølge (eldst til nyest).
        this.årslønner.sort(Comparator.comparingInt(Årslønn::hentÅretForLønn));
        // Reverserer listen slik at den nyeste årslønnen blir først.
        Collections.reverse(this.årslønner);
    }
}
