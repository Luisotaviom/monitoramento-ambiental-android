package br.unisc.monitoramento.util;

/** Cálculos dos indicadores por projeto (AM-08). Java puro. */
public final class Indicadores {

    private Indicadores() {
    }

    /** Percentual inteiro de parte sobre total, de 0 a 100. Retorna 0 quando total é zero. */
    public static int percentual(int parte, int total) {
        if (total <= 0 || parte <= 0) {
            return 0;
        }
        int p = (int) Math.round(100.0 * parte / total);
        return Math.min(100, p);
    }
}
