package br.unisc.monitoramento.data.repository;

import java.util.List;

import br.unisc.monitoramento.data.Callback;
import br.unisc.monitoramento.data.model.PontoColeta;

/** Pontos de coleta de uma área (AM-02). */
public interface PontoColetaRepository {

    void listarPontos(String areaId, Callback<List<PontoColeta>> callback);
}
