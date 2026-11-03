package br.unisc.monitoramento.data.repository;

/**
 * Instâncias compartilhadas dos repositórios, para que as telas enxerguem os mesmos dados.
 * Quando a API e o Room existirem, a troca de implementação acontece aqui.
 */
public final class Repositorios {

    private static ProjetoRepository projetos;
    private static PontoColetaRepository pontos;
    private static RegistroCampoRepository registros;

    private Repositorios() {
    }

    public static synchronized ProjetoRepository projetos() {
        if (projetos == null) {
            projetos = new MockProjetoRepository();
        }
        return projetos;
    }

    public static synchronized PontoColetaRepository pontos() {
        if (pontos == null) {
            pontos = new MockPontoColetaRepository();
        }
        return pontos;
    }

    public static synchronized RegistroCampoRepository registros() {
        if (registros == null) {
            registros = new MockRegistroCampoRepository();
        }
        return registros;
    }
}
