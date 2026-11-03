package br.unisc.monitoramento.data.repository;

import java.util.List;

import br.unisc.monitoramento.data.Callback;
import br.unisc.monitoramento.data.model.RegistroCampo;

/**
 * Registros de campo. A implementação real (Room + sincronização) é da Parte 1.
 * Os métodos aqui são a fachada que a UI usa.
 */
public interface RegistroCampoRepository {

    void salvar(RegistroCampo registro, Callback<RegistroCampo> callback);

    void listarTodos(Callback<List<RegistroCampo>> callback);

    void listarPorStatus(RegistroCampo.Status status, Callback<List<RegistroCampo>> callback);
}
