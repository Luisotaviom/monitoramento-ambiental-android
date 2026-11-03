package br.unisc.monitoramento.data;

/**
 * Retorno assíncrono das operações de dados. A mesma assinatura será usada
 * pelas implementações com Retrofit (rede) e Room (cache offline).
 */
public interface Callback<T> {

    void onSucesso(T resultado);

    void onErro(String mensagem);
}
