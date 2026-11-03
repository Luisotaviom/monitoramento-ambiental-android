package br.unisc.monitoramento.ui.formulario;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import br.unisc.monitoramento.data.Callback;
import br.unisc.monitoramento.data.FormularioJson;
import br.unisc.monitoramento.data.model.CampoFormulario;
import br.unisc.monitoramento.data.model.RegistroCampo;
import br.unisc.monitoramento.data.repository.Repositorios;
import br.unisc.monitoramento.databinding.ActivityFormularioBinding;
import br.unisc.monitoramento.util.ValidadorFormulario;

/**
 * Formulário de campo montado a partir da definição JSON (AM-03).
 * GPS e foto ficam com a Parte 3: por enquanto o registro usa as coordenadas do ponto.
 */
public class FormularioActivity extends AppCompatActivity {

    public static final String EXTRA_PONTO_ID = "extra_ponto_id";
    public static final String EXTRA_PONTO_NOME = "extra_ponto_nome";
    public static final String EXTRA_LATITUDE = "extra_latitude";
    public static final String EXTRA_LONGITUDE = "extra_longitude";

    private static final String ARQUIVO_FORMULARIO = "formularios/coleta-agua.json";

    private ActivityFormularioBinding binding;
    private final List<CampoView> campos = new ArrayList<>();
    private String pontoId;
    private double latitude;
    private double longitude;

    /** Uma linha do formulário: rótulo, campo de entrada e área de erro. */
    private static class CampoView {
        final CampoFormulario definicao;
        final EditText edit;
        final Spinner spinner;
        final TextView erro;

        CampoView(CampoFormulario definicao, EditText edit, Spinner spinner, TextView erro) {
            this.definicao = definicao;
            this.edit = edit;
            this.spinner = spinner;
            this.erro = erro;
        }

        String valor() {
            if (spinner != null) {
                return spinner.getSelectedItemPosition() <= 0
                        ? "" : spinner.getSelectedItem().toString();
            }
            return edit.getText() == null ? "" : edit.getText().toString();
        }

        void mostrarErro(String mensagem) {
            erro.setText(mensagem);
            erro.setVisibility(mensagem == null ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFormularioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        pontoId = getIntent().getStringExtra(EXTRA_PONTO_ID);
        latitude = getIntent().getDoubleExtra(EXTRA_LATITUDE, 0);
        longitude = getIntent().getDoubleExtra(EXTRA_LONGITUDE, 0);
        setTitle(getIntent().getStringExtra(EXTRA_PONTO_NOME));

        List<CampoFormulario> definicoes;
        try {
            definicoes = FormularioJson.ler(lerAsset(ARQUIVO_FORMULARIO));
        } catch (IOException e) {
            mostrarErroGeral("Não foi possível carregar o formulário.");
            return;
        }
        montarCampos(definicoes);

        binding.botaoSalvar.setOnClickListener(v -> salvar());
    }

    private void montarCampos(List<CampoFormulario> definicoes) {
        LinearLayout container = binding.containerCampos;
        for (CampoFormulario def : definicoes) {
            TextView rotulo = new TextView(this);
            rotulo.setText(def.isObrigatorio() ? def.getRotulo() + " *" : def.getRotulo());
            rotulo.setTextAppearance(com.google.android.material.R.style.TextAppearance_Material3_LabelLarge);
            container.addView(rotulo);

            EditText edit = null;
            Spinner spinner = null;

            if (def.getTipo() == CampoFormulario.Tipo.SELECAO) {
                spinner = new Spinner(this);
                List<String> opcoes = new ArrayList<>();
                opcoes.add("Selecione");
                opcoes.addAll(def.getOpcoes());
                spinner.setAdapter(new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_dropdown_item, opcoes));
                container.addView(spinner);
            } else {
                edit = new EditText(this);
                edit.setInputType(entradaPara(def.getTipo()));
                if (def.getTipo() == CampoFormulario.Tipo.DATA) {
                    edit.setHint("AAAA-MM-DD");
                }
                container.addView(edit);
            }

            TextView erro = new TextView(this);
            erro.setTextColor(getColor(br.unisc.monitoramento.R.color.erro));
            erro.setVisibility(View.GONE);
            container.addView(erro);

            campos.add(new CampoView(def, edit, spinner, erro));
        }
    }

    private static int entradaPara(CampoFormulario.Tipo tipo) {
        switch (tipo) {
            case NUMERO:
                return android.text.InputType.TYPE_CLASS_NUMBER
                        | android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL
                        | android.text.InputType.TYPE_NUMBER_FLAG_SIGNED;
            case DATA:
            case TEXTO:
            default:
                return android.text.InputType.TYPE_CLASS_TEXT;
        }
    }

    private void salvar() {
        Map<String, String> valores = new HashMap<>();
        for (CampoView c : campos) {
            valores.put(c.definicao.getId(), c.valor());
        }

        Map<String, String> erros = ValidadorFormulario.validar(
                definicoes(), valores);
        for (CampoView c : campos) {
            c.mostrarErro(erros.get(c.definicao.getId()));
        }
        if (!erros.isEmpty()) {
            return;
        }

        binding.botaoSalvar.setEnabled(false);
        RegistroCampo registro = new RegistroCampo(
                UUID.randomUUID().toString(), pontoId, valores,
                latitude, longitude, null, System.currentTimeMillis());

        Repositorios.registros().salvar(registro, new Callback<RegistroCampo>() {
            @Override
            public void onSucesso(RegistroCampo salvo) {
                Toast.makeText(FormularioActivity.this,
                        br.unisc.monitoramento.R.string.registro_salvo, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onErro(String mensagem) {
                binding.botaoSalvar.setEnabled(true);
                mostrarErroGeral(mensagem);
            }
        });
    }

    private List<CampoFormulario> definicoes() {
        List<CampoFormulario> lista = new ArrayList<>();
        for (CampoView c : campos) {
            lista.add(c.definicao);
        }
        return lista;
    }

    private void mostrarErroGeral(String mensagem) {
        binding.textoErro.setText(mensagem);
        binding.textoErro.setVisibility(View.VISIBLE);
    }

    private String lerAsset(String caminho) throws IOException {
        try (InputStream in = getAssets().open(caminho);
             BufferedReader leitor = new BufferedReader(
                     new InputStreamReader(in, StandardCharsets.UTF_8))) {
            StringBuilder texto = new StringBuilder();
            String linha;
            while ((linha = leitor.readLine()) != null) {
                texto.append(linha).append('\n');
            }
            return texto.toString();
        }
    }
}
