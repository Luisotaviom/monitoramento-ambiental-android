package br.unisc.monitoramento.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import br.unisc.monitoramento.data.model.CampoFormulario;

public class FormularioJsonTest {

    private static final String JSON = "["
            + "{\"id\":\"temp\",\"rotulo\":\"Temperatura\",\"tipo\":\"NUMERO\",\"obrigatorio\":true,\"opcoes\":[]},"
            + "{\"id\":\"tempo\",\"rotulo\":\"Tempo\",\"tipo\":\"SELECAO\",\"obrigatorio\":false,"
            + "\"opcoes\":[\"Sol\",\"Chuva\"]}"
            + "]";

    @Test
    public void leDoisCamposComTiposCorretos() {
        List<CampoFormulario> campos = FormularioJson.ler(JSON);
        assertEquals(2, campos.size());
        assertEquals(CampoFormulario.Tipo.NUMERO, campos.get(0).getTipo());
        assertTrue(campos.get(0).isObrigatorio());
        assertEquals(CampoFormulario.Tipo.SELECAO, campos.get(1).getTipo());
        assertFalse(campos.get(1).isObrigatorio());
        assertEquals(2, campos.get(1).getOpcoes().size());
    }

    @Test
    public void formularioDeExemploDoAppEhLegivel() throws Exception {
        // Mesmo conteúdo do asset app/src/main/assets/formularios/coleta-agua.json
        String json = new String(java.nio.file.Files.readAllBytes(
                java.nio.file.Paths.get("src/main/assets/formularios/coleta-agua.json")),
                java.nio.charset.StandardCharsets.UTF_8);
        List<CampoFormulario> campos = FormularioJson.ler(json);
        assertEquals(6, campos.size());
        assertEquals("data_coleta", campos.get(0).getId());
    }
}
