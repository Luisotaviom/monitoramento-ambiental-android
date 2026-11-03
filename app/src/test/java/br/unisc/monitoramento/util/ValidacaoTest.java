package br.unisc.monitoramento.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ValidacaoTest {

    @Test
    public void textoComConteudoEhPreenchido() {
        assertTrue(Validacao.preenchido("luis"));
    }

    @Test
    public void nullVazioOuEspacosNaoSaoPreenchidos() {
        assertFalse(Validacao.preenchido(null));
        assertFalse(Validacao.preenchido(""));
        assertFalse(Validacao.preenchido("   "));
    }
}
