package dagpenger;

import no.nav.dagpenger.DagpengerKalkulator;
import no.nav.årslønn.Årslønn;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DagpengerKalkulatorTester {

    @Test
    public void testSkalHaRettigheterTilDagpengerUtifraSisteTreÅrslønner()  {
        DagpengerKalkulator dagpengerKalkulator = new DagpengerKalkulator();
        dagpengerKalkulator.leggTilÅrslønn(new Årslønn(2022, 445000));
        dagpengerKalkulator.leggTilÅrslønn(new Årslønn(2024, 465000));
        dagpengerKalkulator.leggTilÅrslønn(new Årslønn(2023, 300000));
        assertTrue(dagpengerKalkulator.harRettigheterTilDagpenger());
    }

    @Test
    public void testSkalHaRetigheterTilDagpengerSisteÅrslønn() {
        DagpengerKalkulator dagpengerKalkulator = new DagpengerKalkulator();
        dagpengerKalkulator.leggTilÅrslønn(new Årslønn(2022, 0));
        dagpengerKalkulator.leggTilÅrslønn(new Årslønn(2023, 0));
        dagpengerKalkulator.leggTilÅrslønn(new Årslønn(2024, 467000));
        assertTrue(dagpengerKalkulator.harRettigheterTilDagpenger());
    }

    @Test
    public void testSkalIkkeHaRettigheterTilDagpengerSisteTreÅrslønner()  {
        DagpengerKalkulator dagpengerKalkulator = new DagpengerKalkulator();
        dagpengerKalkulator.leggTilÅrslønn(new Årslønn(2022, 44000));
        dagpengerKalkulator.leggTilÅrslønn(new Årslønn(2024, 52000));
        dagpengerKalkulator.leggTilÅrslønn(new Årslønn(2023, 100000));
        assertFalse(dagpengerKalkulator.harRettigheterTilDagpenger());
    }

    @Test
    public void testSkalIkkeHaRettigheterTilDagpengerSisteÅrslønn()  {
        DagpengerKalkulator dagpengerKalkulator = new DagpengerKalkulator();
        dagpengerKalkulator.leggTilÅrslønn(new Årslønn(2022, 0));
        dagpengerKalkulator.leggTilÅrslønn(new Årslønn(2024, 130000));
        dagpengerKalkulator.leggTilÅrslønn(new Årslønn(2023, 0));
        assertFalse(dagpengerKalkulator.harRettigheterTilDagpenger());
    }

    @Test
    public void testBeregningsMetodeBlirSattTilSisteÅrslønn() {
        DagpengerKalkulator dagpengerKalkulator = new DagpengerKalkulator();
        dagpengerKalkulator.leggTilÅrslønn(new Årslønn(2024, 550000));
        dagpengerKalkulator.leggTilÅrslønn(new Årslønn(2022, 110000));
        dagpengerKalkulator.leggTilÅrslønn(new Årslønn(2023, 24000));
        assertEquals("SISTE_ÅRSLØNN", dagpengerKalkulator.velgBeregningsMetode());
    }

    @Test
    public void testBeregningsMetodeBlirSattTilMaksÅrslønnGrunnbeløp() {
        DagpengerKalkulator dagpengerKalkulator = new DagpengerKalkulator();
        dagpengerKalkulator.leggTilÅrslønn(new Årslønn(2024, 830000));
        dagpengerKalkulator.leggTilÅrslønn(new Årslønn(2022, 110000));
        dagpengerKalkulator.leggTilÅrslønn(new Årslønn(2023, 24000));
        assertEquals("MAKS_ÅRLIG_DAGPENGERGRUNNLAG", dagpengerKalkulator.velgBeregningsMetode());
    }

    @Test
    public void testBeregningsMetodeBlirSattTilGjennomsnittetAvTreÅr() {
        DagpengerKalkulator dagpengerKalkulator = new DagpengerKalkulator();
        dagpengerKalkulator.leggTilÅrslønn(new Årslønn(2024, 330000));
        dagpengerKalkulator.leggTilÅrslønn(new Årslønn(2022, 400000));
        dagpengerKalkulator.leggTilÅrslønn(new Årslønn(2023, 334000));
        assertEquals("GJENNOMSNITTET_AV_TRE_ÅR", dagpengerKalkulator.velgBeregningsMetode());
    }

    @Test
    public void testDagsatsKalkulertUtifraSisteÅrslønn() {
        DagpengerKalkulator dagpengerKalkulator = new DagpengerKalkulator();
        dagpengerKalkulator.leggTilÅrslønn(new Årslønn(2024, 550000));
        dagpengerKalkulator.leggTilÅrslønn(new Årslønn(2022, 110000));
        dagpengerKalkulator.leggTilÅrslønn(new Årslønn(2023, 24000));
        assertEquals(2116, dagpengerKalkulator.kalkulerDagsats());
    }

    @Test
    public void testDagsatsKalkulertUtifraMaksÅrligGrunnbeløp() {
        DagpengerKalkulator dagpengerKalkulator = new DagpengerKalkulator();
        dagpengerKalkulator.leggTilÅrslønn(new Årslønn(2024, 830000));
        dagpengerKalkulator.leggTilÅrslønn(new Årslønn(2023, 24000));
        dagpengerKalkulator.leggTilÅrslønn(new Årslønn(2022, 110000));
        assertEquals(2863, dagpengerKalkulator.kalkulerDagsats());
    }

    @Test
    public void testDagsatsKalkulertUtifraTreÅrsGjennomsnitt() {

        DagpengerKalkulator dagpengerKalkulator = new DagpengerKalkulator();
        dagpengerKalkulator.leggTilÅrslønn(new Årslønn(2024, 330000));
        dagpengerKalkulator.leggTilÅrslønn(new Årslønn(2023, 334000));
        dagpengerKalkulator.leggTilÅrslønn(new Årslønn(2022, 400000));
        assertEquals(1365, dagpengerKalkulator.kalkulerDagsats());
    }

    @Test
    public void testDagsatsKalkulertIkkeRettPåDagpenger() {
        DagpengerKalkulator dagpengerKalkulator = new DagpengerKalkulator();
        dagpengerKalkulator.leggTilÅrslønn(new Årslønn(2024, 80000));
        dagpengerKalkulator.leggTilÅrslønn(new Årslønn(2023, 100000));
        dagpengerKalkulator.leggTilÅrslønn(new Årslønn(2022, 70000));
        assertEquals(0, dagpengerKalkulator.kalkulerDagsats());
    }
}
