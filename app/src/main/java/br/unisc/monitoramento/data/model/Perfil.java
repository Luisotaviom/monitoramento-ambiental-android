package br.unisc.monitoramento.data.model;

/**
 * Perfis de acesso definidos no requisito AM-01:
 * administrador, equipe técnica e empresa contratante.
 */
public enum Perfil {
    ADMINISTRADOR("Administrador"),
    EQUIPE_TECNICA("Equipe técnica"),
    EMPRESA("Empresa contratante");

    private final String rotulo;

    Perfil(String rotulo) {
        this.rotulo = rotulo;
    }

    public String getRotulo() {
        return rotulo;
    }
}
