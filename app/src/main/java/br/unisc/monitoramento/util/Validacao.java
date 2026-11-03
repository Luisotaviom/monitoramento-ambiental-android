package br.unisc.monitoramento.util;

/** Validações de preenchimento (campos obrigatórios, requisito AM-03). */
public final class Validacao {

    private Validacao() {
    }

    /** Retorna true quando o texto tem algum conteúdo além de espaços. */
    public static boolean preenchido(String texto) {
        return texto != null && !texto.trim().isEmpty();
    }
}
