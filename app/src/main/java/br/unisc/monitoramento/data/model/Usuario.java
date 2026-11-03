package br.unisc.monitoramento.data.model;

/** Usuário autenticado. O token JWT será preenchido quando a API estiver disponível. */
public class Usuario {

    private final String login;
    private final Perfil perfil;
    private final String token;

    public Usuario(String login, Perfil perfil, String token) {
        this.login = login;
        this.perfil = perfil;
        this.token = token;
    }

    public String getLogin() {
        return login;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public String getToken() {
        return token;
    }
}
