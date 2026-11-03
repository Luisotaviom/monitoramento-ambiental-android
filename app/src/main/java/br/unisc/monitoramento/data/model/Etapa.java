package br.unisc.monitoramento.data.model;

/**
 * Etapa de um projeto, com cronograma (AM-02).
 * Datas em texto ISO (AAAA-MM-DD), para não depender de java.time em minSdk 24.
 */
public class Etapa {

    private final String id;
    private final String projetoId;
    private final String nome;
    private final String inicioPrevisto;
    private final String fimPrevisto;

    public Etapa(String id, String projetoId, String nome, String inicioPrevisto, String fimPrevisto) {
        this.id = id;
        this.projetoId = projetoId;
        this.nome = nome;
        this.inicioPrevisto = inicioPrevisto;
        this.fimPrevisto = fimPrevisto;
    }

    public String getId() {
        return id;
    }

    public String getProjetoId() {
        return projetoId;
    }

    public String getNome() {
        return nome;
    }

    public String getInicioPrevisto() {
        return inicioPrevisto;
    }

    public String getFimPrevisto() {
        return fimPrevisto;
    }
}
