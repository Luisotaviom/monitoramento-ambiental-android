package br.unisc.monitoramento.ui.indicadores;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import br.unisc.monitoramento.R;
import br.unisc.monitoramento.data.Callback;
import br.unisc.monitoramento.data.model.Projeto;
import br.unisc.monitoramento.data.model.RegistroCampo;
import br.unisc.monitoramento.data.repository.Repositorios;
import br.unisc.monitoramento.databinding.ActivityIndicadoresBinding;
import br.unisc.monitoramento.util.Indicadores;

/** Indicadores gerais: projetos e registros de campo por status (AM-08). */
public class IndicadoresActivity extends AppCompatActivity {

    private ActivityIndicadoresBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIndicadoresBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Repositorios.projetos().listarProjetos(new Callback<List<Projeto>>() {
            @Override
            public void onSucesso(List<Projeto> projetos) {
                binding.textoTotalProjetos.setText(
                        getString(R.string.indicador_projetos, projetos.size()));
            }

            @Override
            public void onErro(String mensagem) {
                binding.textoTotalProjetos.setText(mensagem);
            }
        });

        Repositorios.registros().listarTodos(new Callback<List<RegistroCampo>>() {
            @Override
            public void onSucesso(List<RegistroCampo> registros) {
                mostrarRegistros(registros);
            }

            @Override
            public void onErro(String mensagem) {
                binding.textoTotalRegistros.setText(mensagem);
            }
        });
    }

    private void mostrarRegistros(List<RegistroCampo> registros) {
        int total = registros.size();
        binding.textoTotalRegistros.setText(getString(R.string.indicador_registros, total));

        binding.containerStatus.removeAllViews();
        for (RegistroCampo.Status status : RegistroCampo.Status.values()) {
            int quantidade = 0;
            for (RegistroCampo r : registros) {
                if (r.getStatus() == status) {
                    quantidade++;
                }
            }
            int percentual = Indicadores.percentual(quantidade, total);

            TextView linha = new TextView(this);
            linha.setText(getString(R.string.indicador_status_linha,
                    status.name(), quantidade, percentual));
            linha.setPadding(0, 16, 0, 4);
            binding.containerStatus.addView(linha);

            com.google.android.material.progressindicator.LinearProgressIndicator barra =
                    new com.google.android.material.progressindicator.LinearProgressIndicator(this);
            barra.setMax(100);
            barra.setProgress(percentual);
            barra.setVisibility(View.VISIBLE);
            binding.containerStatus.addView(barra);
        }
    }
}
