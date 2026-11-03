package br.unisc.monitoramento.data.repository;

import java.util.ArrayList;
import java.util.List;

import br.unisc.monitoramento.data.Callback;
import br.unisc.monitoramento.data.model.RegistroCampo;

/**
 * Implementação em memória, só para desenvolvimento e testes.
 * Responde de forma síncrona, para funcionar em testes JVM sem Android.
 * Os dados se perdem ao fechar o app.
 */
public class MockRegistroCampoRepository implements RegistroCampoRepository {

    private final List<RegistroCampo> registros = new ArrayList<>();

    @Override
    public void salvar(RegistroCampo registro, Callback<RegistroCampo> callback) {
        for (RegistroCampo existente : registros) {
            // Mesma chave de idempotência: não duplica.
            if (existente.getUuidLocal().equals(registro.getUuidLocal())) {
                callback.onErro("Registro já existe: " + registro.getUuidLocal());
                return;
            }
        }
        registros.add(registro);
        callback.onSucesso(registro);
    }

    @Override
    public void listarTodos(Callback<List<RegistroCampo>> callback) {
        callback.onSucesso(new ArrayList<>(registros));
    }

    @Override
    public void listarPorStatus(RegistroCampo.Status status, Callback<List<RegistroCampo>> callback) {
        List<RegistroCampo> filtrados = new ArrayList<>();
        for (RegistroCampo registro : registros) {
            if (registro.getStatus() == status) {
                filtrados.add(registro);
            }
        }
        callback.onSucesso(filtrados);
    }
}
