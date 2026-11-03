package br.unisc.monitoramento.data.repository;

import br.unisc.monitoramento.data.Callback;
import br.unisc.monitoramento.data.model.Perfil;
import br.unisc.monitoramento.data.model.Usuario;

/** Autenticação com JWT (AM-01). */
public interface AuthRepository {

    void entrar(String login, String senha, Perfil perfil, Callback<Usuario> callback);
}
