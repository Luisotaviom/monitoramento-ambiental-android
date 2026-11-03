package br.unisc.monitoramento.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class IndicadoresTest {

    @Test
    public void percentualArredondaEmInteiro() {
        assertEquals(33, Indicadores.percentual(1, 3));
        assertEquals(67, Indicadores.percentual(2, 3));
    }

    @Test
    public void totalZeroRetornaZeroSemDividirPorZero() {
        assertEquals(0, Indicadores.percentual(0, 0));
        assertEquals(0, Indicadores.percentual(5, 0));
    }

    @Test
    public void nuncaPassaDeCem() {
        assertEquals(100, Indicadores.percentual(4, 4));
        assertEquals(100, Indicadores.percentual(9, 4));
    }
}
