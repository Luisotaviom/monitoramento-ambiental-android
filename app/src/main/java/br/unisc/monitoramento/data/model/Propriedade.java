package br.unisc.monitoramento.data.model;

/** Propriedade rural vinculada a um projeto (AM-02). */
public class Propriedade {

    private final String id;
    private final String projetoId;
    private final String nome;
    private final String municipio;

    public Propriedade(String id, String projetoId, String nome, String municipio) {
        this.id = id;
        this.projetoId = projetoId;
        this.nome = nome;
        this.municipio = municipio;
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

    public String getMunicipio() {
        return municipio;
    }
}
