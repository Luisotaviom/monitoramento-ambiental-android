package br.unisc.monitoramento.data.model;

/** Área de uma propriedade (AM-02). */
public class Area {

    private final String id;
    private final String propriedadeId;
    private final String nome;
    private final double hectares;

    public Area(String id, String propriedadeId, String nome, double hectares) {
        this.id = id;
        this.propriedadeId = propriedadeId;
        this.nome = nome;
        this.hectares = hectares;
    }

    public String getId() {
        return id;
    }

    public String getPropriedadeId() {
        return propriedadeId;
    }

    public String getNome() {
        return nome;
    }

    public double getHectares() {
        return hectares;
    }
}
