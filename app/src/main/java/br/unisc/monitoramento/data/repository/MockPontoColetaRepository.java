package br.unisc.monitoramento.data.repository;

import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import br.unisc.monitoramento.data.Callback;
import br.unisc.monitoramento.data.model.PontoColeta;

/** Pontos de coleta de exemplo, até a API estar disponível. */
public class MockPontoColetaRepository implements PontoColetaRepository {

    private static final long LATENCIA_MS = 400;

    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public void listarPontos(String areaId, Callback<List<PontoColeta>> callback) {
        handler.postDelayed(() -> {
            List<PontoColeta> resultado = new ArrayList<>();
            for (PontoColeta ponto : pontos()) {
                if (ponto.getAreaId().equals(areaId)) {
                    resultado.add(ponto);
                }
            }
            callback.onSucesso(resultado);
        }, LATENCIA_MS);
    }

    static List<PontoColeta> pontos() {
        return Collections.unmodifiableList(Arrays.asList(
                new PontoColeta("ponto-001", "area-001", "Nascente norte",
                        -29.6512, -52.4450, null),
                new PontoColeta("ponto-002", "area-001", "Nascente sul",
                        -29.6540, -52.4471, null),
                new PontoColeta("ponto-003", "area-002", "Margem do arroio",
                        -29.6601, -52.4390, null),
                new PontoColeta("ponto-004", "area-003", "Entrada do talhão",
                        -29.4102, -51.9720, null)
        ));
    }
}
