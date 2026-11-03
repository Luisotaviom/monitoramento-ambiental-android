package br.unisc.monitoramento.util;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import br.unisc.monitoramento.data.model.CampoFormulario;

/**
 * Valida os valores de um formulário contra a definição dos campos (AM-03).
 * Java puro, sem Android: testável na JVM.
 */
public final class ValidadorFormulario {

    private ValidadorFormulario() {
    }

    /**
     * Retorna os erros por identificador de campo. Mapa vazio significa formulário válido.
     */
    public static Map<String, String> validar(List<CampoFormulario> campos, Map<String, String> valores) {
        Map<String, String> erros = new LinkedHashMap<>();

        for (CampoFormulario campo : campos) {
            String valor = valores.get(campo.getId());
            String texto = valor == null ? "" : valor.trim();

            if (texto.isEmpty()) {
                if (campo.isObrigatorio()) {
                    erros.put(campo.getId(), "Campo obrigatório");
                }
                continue;
            }

            switch (campo.getTipo()) {
                case NUMERO:
                    if (!numeroValido(texto)) {
                        erros.put(campo.getId(), "Informe um número");
                    }
                    break;
                case DATA:
                    if (!dataValida(texto)) {
                        erros.put(campo.getId(), "Use o formato AAAA-MM-DD");
                    }
                    break;
                case SELECAO:
                    if (!campo.getOpcoes().contains(texto)) {
                        erros.put(campo.getId(), "Escolha uma opção da lista");
                    }
                    break;
                case TEXTO:
                default:
                    break;
            }
        }
        return erros;
    }

    static boolean numeroValido(String texto) {
        try {
            double n = Double.parseDouble(texto.replace(',', '.'));
            return Double.isFinite(n);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /** Aceita AAAA-MM-DD com mês de 1 a 12 e dia de 1 a 31 (não checa dias por mês). */
    static boolean dataValida(String texto) {
        if (!texto.matches("\\d{4}-\\d{2}-\\d{2}")) {
            return false;
        }
        int mes = Integer.parseInt(texto.substring(5, 7));
        int dia = Integer.parseInt(texto.substring(8, 10));
        return mes >= 1 && mes <= 12 && dia >= 1 && dia <= 31;
    }
}
