package br.unisc.monitoramento.data.model;

/** Projeto ambiental (Muda ou PSA), conforme o requisito AM-02. */
public class Projeto {

    public enum Tipo {
        MUDA("Muda"),
        PSA("PSA");

        private final String rotulo;

        Tipo(String rotulo) {
            this.rotulo = rotulo;
        }

        public String getRotulo() {
            return rotulo;
        }
    }

    private final String id;
    private final String nome;
    private final Tipo tipo;

    public Projeto(String id, String nome, Tipo tipo) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Tipo getTipo() {
        return tipo;
    }
}
