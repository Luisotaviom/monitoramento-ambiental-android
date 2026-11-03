package br.unisc.monitoramento.data.repository;

import android.os.Handler;
import android.os.Looper;

import br.unisc.monitoramento.data.Callback;
import br.unisc.monitoramento.data.model.Perfil;
import br.unisc.monitoramento.data.model.Usuario;

/**
 * Implementação de teste: aceita qualquer login e senha não vazios.
 * Será substituída pela chamada real à API quando ela estiver disponível.
 */
public class MockAuthRepository implements AuthRepository {

    private static final long LATENCIA_MS = 600;

    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public void entrar(String login, String senha, Perfil perfil, Callback<Usuario> callback) {
        handler.postDelayed(() -> {
            if (login == null || login.trim().isEmpty() || senha == null || senha.isEmpty()) {
                callback.onErro("Informe login e senha.");
                return;
            }
            callback.onSucesso(new Usuario(login.trim(), perfil, "token-mock"));
        }, LATENCIA_MS);
    }
}
