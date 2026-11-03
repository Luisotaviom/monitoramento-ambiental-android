package br.unisc.monitoramento.data.model;

import java.util.Collections;
import java.util.List;

/** Definição de um campo do formulário de campo, vinda da plataforma (AM-03). */
public class CampoFormulario {

    public enum Tipo {
        TEXTO,
        NUMERO,
        DATA,
        SELECAO
    }

    private final String id;
    private final String rotulo;
    private final Tipo tipo;
    private final boolean obrigatorio;
    private final List<String> opcoes;

    public CampoFormulario(String id, String rotulo, Tipo tipo, boolean obrigatorio, List<String> opcoes) {
        this.id = id;
        this.rotulo = rotulo;
        this.tipo = tipo;
        this.obrigatorio = obrigatorio;
        this.opcoes = opcoes == null ? Collections.<String>emptyList() : opcoes;
    }

    public String getId() {
        return id;
    }

    public String getRotulo() {
        return rotulo;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public boolean isObrigatorio() {
        return obrigatorio;
    }

    /** Opções válidas quando o tipo é SELECAO. Lista vazia nos demais tipos. */
    public List<String> getOpcoes() {
        return opcoes;
    }
}
