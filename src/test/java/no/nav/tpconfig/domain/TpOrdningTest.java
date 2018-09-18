package no.nav.tpconfig.domain;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TpOrdningTest {

    @Test
    public void tpOrdningFromTpNumber_ReturnsGabler_When_TpNrIs4160() {
        String tpNrForGabler = "4160";
        String expectedTpLeverandoer = "GABLER";
        TpOrdning tpOrdning = TpOrdning.tpOrdningFromTpNumber(tpNrForGabler);
        assertEquals(expectedTpLeverandoer, tpOrdning.tpLeverandoer().name());
    }

    @Test(expected = NoTpOrdningFound.class)
    public void tpOrdningFromTpNumber_ThrowsNoTpOrdningFound_Given_NonExistingTpNr() {
        String nonExistingTpNr = "99999";
        TpOrdning tpOrdning = TpOrdning.tpOrdningFromTpNumber(nonExistingTpNr);
    }

    @Test
    public void tpOrdningFromTssNumber_ReturnsSPK_Given_CorrectTssNr() {
        String tssNrForSPK = "80000470761";
        String expectedTpOrdning = "TPORDNING_SPK_DEFAULT";
        TpOrdning tpOrdning = TpOrdning.tpOrdningFromTssNumber(tssNrForSPK);
        assertEquals(expectedTpOrdning, tpOrdning.name());
    }

    @Test(expected = NoTpOrdningFound.class)
    public void tpOrdningFromTssNumber_ThrowsNoTpOrdningFound_Given_NonExistingTssNr() {
        String nonExistingTpNr = "142124124124124124124124124";
        TpOrdning tpOrdning = TpOrdning.tpOrdningFromTpNumber(nonExistingTpNr);
    }

}
