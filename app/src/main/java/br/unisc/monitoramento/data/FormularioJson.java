package br.unisc.monitoramento.data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import br.unisc.monitoramento.data.model.CampoFormulario;

/**
 * Lê a definição de um formulário em JSON. Formato:
 * [{"id":"temperatura","rotulo":"Temperatura (°C)","tipo":"NUMERO","obrigatorio":true,"opcoes":[]}]
 */
public final class FormularioJson {

    private FormularioJson() {
    }

    public static List<CampoFormulario> ler(String json) {
        Type tipoLista = new TypeToken<List<CampoFormulario>>() { }.getType();
        return new Gson().fromJson(json, tipoLista);
    }
}
