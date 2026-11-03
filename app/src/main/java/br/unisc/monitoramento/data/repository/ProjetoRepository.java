package br.unisc.monitoramento.data.repository;

import java.util.List;

import br.unisc.monitoramento.data.Callback;
import br.unisc.monitoramento.data.model.Projeto;

/** Consulta de projetos Muda e PSA (AM-02). */
public interface ProjetoRepository {

    void listarProjetos(Callback<List<Projeto>> callback);
}
