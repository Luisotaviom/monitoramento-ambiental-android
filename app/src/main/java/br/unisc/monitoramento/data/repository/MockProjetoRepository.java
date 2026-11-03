package br.unisc.monitoramento.data.repository;

import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import br.unisc.monitoramento.data.Callback;
import br.unisc.monitoramento.data.model.Area;
import br.unisc.monitoramento.data.model.Etapa;
import br.unisc.monitoramento.data.model.Projeto;
import br.unisc.monitoramento.data.model.Propriedade;

/** Dados de exemplo enquanto a API da plataforma não estiver disponível. */
public class MockProjetoRepository implements ProjetoRepository {

    private static final long LATENCIA_MS = 400;

    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public void listarProjetos(Callback<List<Projeto>> callback) {
        handler.postDelayed(() -> callback.onSucesso(projetos()), LATENCIA_MS);
    }

    @Override
    public void listarPropriedades(String projetoId, Callback<List<Propriedade>> callback) {
        handler.postDelayed(() -> callback.onSucesso(filtrar(propriedades(), projetoId,
                p -> p.getProjetoId())), LATENCIA_MS);
    }

    @Override
    public void listarAreas(String propriedadeId, Callback<List<Area>> callback) {
        handler.postDelayed(() -> callback.onSucesso(filtrar(areas(), propriedadeId,
                a -> a.getPropriedadeId())), LATENCIA_MS);
    }

    @Override
    public void listarEtapas(String projetoId, Callback<List<Etapa>> callback) {
        handler.postDelayed(() -> callback.onSucesso(filtrar(etapas(), projetoId,
                e -> e.getProjetoId())), LATENCIA_MS);
    }

    static List<Projeto> projetos() {
        return Collections.unmodifiableList(Arrays.asList(
                new Projeto("muda-001", "Recuperação de Mata Ciliar", Projeto.Tipo.MUDA),
                new Projeto("psa-001", "Pagamento por Serviços Ambientais - Propriedade A", Projeto.Tipo.PSA),
                new Projeto("psa-002", "Pagamento por Serviços Ambientais - Propriedade B", Projeto.Tipo.PSA)
        ));
    }

    static List<Propriedade> propriedades() {
        return Collections.unmodifiableList(Arrays.asList(
                new Propriedade("prop-001", "psa-001", "Sítio Santa Clara", "Taquari"),
                new Propriedade("prop-002", "muda-001", "Fazenda Boa Vista", "Venâncio Aires")
        ));
    }

    static List<Area> areas() {
        return Collections.unmodifiableList(Arrays.asList(
                new Area("area-001", "prop-001", "Nascente", 4.5),
                new Area("area-002", "prop-001", "Mata ciliar", 12.0),
                new Area("area-003", "prop-002", "Talhão 1", 8.2)
        ));
    }

    static List<Etapa> etapas() {
        return Collections.unmodifiableList(Arrays.asList(
                new Etapa("etapa-001", "psa-001", "Diagnóstico", "2026-01-10", "2026-03-30"),
                new Etapa("etapa-002", "psa-001", "Monitoramento", "2026-04-01", "2026-12-15")
        ));
    }

    private interface Chave<T> {
        String de(T item);
    }

    private static <T> List<T> filtrar(List<T> itens, String valor, Chave<T> chave) {
        List<T> resultado = new ArrayList<>();
        for (T item : itens) {
            if (valor != null && valor.equals(chave.de(item))) {
                resultado.add(item);
            }
        }
        return resultado;
    }
}
