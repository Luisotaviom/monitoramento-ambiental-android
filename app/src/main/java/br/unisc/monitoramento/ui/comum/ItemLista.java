package br.unisc.monitoramento.ui.comum;

/**
 * Linha genérica para as listas do app. A tela converte o modelo de dados
 * (Projeto, Propriedade, Area ou PontoColeta) para este formato e guarda o
 * original em {@code origem}, para usar no clique.
 */
public class ItemLista {

    private final String id;
    private final String titulo;
    private final String subtitulo;
    private final Object origem;

    public ItemLista(String id, String titulo, String subtitulo, Object origem) {
        this.id = id;
        this.titulo = titulo;
        this.subtitulo = subtitulo;
        this.origem = origem;
    }

    public String getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getSubtitulo() {
        return subtitulo;
    }

    public Object getOrigem() {
        return origem;
    }
}
