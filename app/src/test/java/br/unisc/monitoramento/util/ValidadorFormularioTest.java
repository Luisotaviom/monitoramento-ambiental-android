package br.unisc.monitoramento.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import br.unisc.monitoramento.data.model.CampoFormulario;
import br.unisc.monitoramento.data.model.CampoFormulario.Tipo;

public class ValidadorFormularioTest {

    private static CampoFormulario campo(String id, Tipo tipo, boolean obrigatorio, List<String> opcoes) {
        return new CampoFormulario(id, id, tipo, obrigatorio, opcoes);
    }

    private static Map<String, String> valores(String... pares) {
        Map<String, String> m = new HashMap<>();
        for (int i = 0; i < pares.length; i += 2) {
            m.put(pares[i], pares[i + 1]);
        }
        return m;
    }

    @Test
    public void obrigatorioVazioGeraErro() {
        List<CampoFormulario> c = Collections.singletonList(campo("resp", Tipo.TEXTO, true, null));
        Map<String, String> erros = ValidadorFormulario.validar(c, valores("resp", "   "));
        assertEquals("Campo obrigatório", erros.get("resp"));
    }

    @Test
    public void opcionalVazioEhValido() {
        List<CampoFormulario> c = Collections.singletonList(campo("obs", Tipo.TEXTO, false, null));
        assertTrue(ValidadorFormulario.validar(c, valores()).isEmpty());
    }

    @Test
    public void numeroAceitaVirgulaEDecimal() {
        assertTrue(ValidadorFormulario.numeroValido("21,5"));
        assertTrue(ValidadorFormulario.numeroValido("-3.2"));
    }

    @Test
    public void numeroRejeitaTextoENaN() {
        assertEquals(false, ValidadorFormulario.numeroValido("abc"));
        assertEquals(false, ValidadorFormulario.numeroValido("NaN"));
        assertEquals(false, ValidadorFormulario.numeroValido("Infinity"));
    }

    @Test
    public void dataNoFormatoCorreto() {
        assertTrue(ValidadorFormulario.dataValida("2026-11-03"));
        assertEquals(false, ValidadorFormulario.dataValida("03/11/2026"));
        assertEquals(false, ValidadorFormulario.dataValida("2026-13-01"));
        assertEquals(false, ValidadorFormulario.dataValida("2026-01-32"));
    }

    @Test
    public void selecaoSoAceitaOpcaoDaLista() {
        List<CampoFormulario> c = Collections.singletonList(
                campo("tempo", Tipo.SELECAO, true, Arrays.asList("Sol", "Chuva")));
        assertTrue(ValidadorFormulario.validar(c, valores("tempo", "Sol")).isEmpty());
        assertEquals("Escolha uma opção da lista",
                ValidadorFormulario.validar(c, valores("tempo", "Neve")).get("tempo"));
    }

    @Test
    public void variosErrosAoMesmoTempo() {
        List<CampoFormulario> c = Arrays.asList(
                campo("resp", Tipo.TEXTO, true, null),
                campo("temp", Tipo.NUMERO, true, null),
                campo("data", Tipo.DATA, true, null));
        Map<String, String> erros = ValidadorFormulario.validar(c,
                valores("resp", "Luis", "temp", "x", "data", "ontem"));
        assertEquals(2, erros.size());
        assertTrue(erros.containsKey("temp"));
        assertTrue(erros.containsKey("data"));
    }
}
