package br.unisc.monitoramento.data.repository;

import android.os.Handler;
import android.os.Looper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import br.unisc.monitoramento.data.Callback;
import br.unisc.monitoramento.data.model.Projeto;

/** Dados de exemplo enquanto a API da plataforma não estiver disponível. */
public class MockProjetoRepository implements ProjetoRepository {

    private static final long LATENCIA_MS = 400;

    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public void listarProjetos(Callback<List<Projeto>> callback) {
        handler.postDelayed(() -> callback.onSucesso(dadosDeExemplo()), LATENCIA_MS);
    }

    static List<Projeto> dadosDeExemplo() {
        return Collections.unmodifiableList(Arrays.asList(
                new Projeto("muda-001", "Recuperação de Mata Ciliar", Projeto.Tipo.MUDA),
                new Projeto("psa-001", "Pagamento por Serviços Ambientais - Propriedade A", Projeto.Tipo.PSA),
                new Projeto("psa-002", "Pagamento por Serviços Ambientais - Propriedade B", Projeto.Tipo.PSA)
        ));
    }
}
