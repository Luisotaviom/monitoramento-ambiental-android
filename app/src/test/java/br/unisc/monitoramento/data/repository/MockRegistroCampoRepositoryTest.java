package br.unisc.monitoramento.data.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import br.unisc.monitoramento.data.Callback;
import br.unisc.monitoramento.data.model.RegistroCampo;

public class MockRegistroCampoRepositoryTest {

    private MockRegistroCampoRepository repo;

    @Before
    public void setUp() {
        repo = new MockRegistroCampoRepository();
    }

    @Test
    public void novoRegistroComeçaPendenteEsemDataDeSincronizacao() {
        RegistroCampo r = novo("uuid-1");
        assertEquals(RegistroCampo.Status.PENDENTE, r.getStatus());
        assertNull(r.getSincronizadoEm());
    }

    @Test
    public void salvarEListarRetornaORegistro() {
        repo.salvar(novo("uuid-1"), new Callback<RegistroCampo>() {
            @Override public void onSucesso(RegistroCampo r) { assertNotNull(r); }
            @Override public void onErro(String m) { throw new AssertionError(m); }
        });

        final int[] tamanho = {-1};
        repo.listarTodos(new Callback<List<RegistroCampo>>() {
            @Override public void onSucesso(List<RegistroCampo> lista) { tamanho[0] = lista.size(); }
            @Override public void onErro(String m) { throw new AssertionError(m); }
        });
        assertEquals(1, tamanho[0]);
    }

    @Test
    public void mesmoUuidLocalNaoDuplica() {
        final String[] erro = {null};
        repo.salvar(novo("uuid-1"), vazio());
        repo.salvar(novo("uuid-1"), new Callback<RegistroCampo>() {
            @Override public void onSucesso(RegistroCampo r) { }
            @Override public void onErro(String m) { erro[0] = m; }
        });
        assertNotNull(erro[0]);
    }

    @Test
    public void mudarParaSincronizadoGuardaAHora() {
        RegistroCampo r = novo("uuid-1");
        r.mudarStatus(RegistroCampo.Status.SINCRONIZADO, 999L);
        assertEquals(RegistroCampo.Status.SINCRONIZADO, r.getStatus());
        assertEquals(Long.valueOf(999L), r.getSincronizadoEm());
    }

    @Test
    public void camposSaoImutaveisAposCriacao() {
        Map<String, String> origem = new HashMap<>();
        origem.put("temperatura", "21");
        RegistroCampo r = new RegistroCampo("uuid-1", "ponto-1", origem, -29.8, -52.3, null, 1L);
        origem.put("temperatura", "99");
        assertEquals("21", r.getCampos().get("temperatura"));
        assertEquals(Collections.emptyMap(), new RegistroCampo("u", "p", null, 0, 0, null, 1L).getCampos());
    }

    private static RegistroCampo novo(String uuid) {
        return new RegistroCampo(uuid, "ponto-1", Collections.<String, String>emptyMap(),
                -29.8, -52.3, null, 1L);
    }

    private static Callback<RegistroCampo> vazio() {
        return new Callback<RegistroCampo>() {
            @Override public void onSucesso(RegistroCampo r) { }
            @Override public void onErro(String m) { }
        };
    }
}
