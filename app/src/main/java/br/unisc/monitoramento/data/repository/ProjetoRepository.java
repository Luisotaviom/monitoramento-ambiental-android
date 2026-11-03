package br.unisc.monitoramento.data.repository;

import java.util.List;

import br.unisc.monitoramento.data.Callback;
import br.unisc.monitoramento.data.model.Area;
import br.unisc.monitoramento.data.model.Etapa;
import br.unisc.monitoramento.data.model.Projeto;
import br.unisc.monitoramento.data.model.Propriedade;

/** Consulta da estrutura dos projetos Muda e PSA (AM-02). */
public interface ProjetoRepository {

    void listarProjetos(Callback<List<Projeto>> callback);

    void listarPropriedades(String projetoId, Callback<List<Propriedade>> callback);

    void listarAreas(String propriedadeId, Callback<List<Area>> callback);

    void listarEtapas(String projetoId, Callback<List<Etapa>> callback);
}
