package br.unisc.monitoramento.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import br.unisc.monitoramento.R;
import br.unisc.monitoramento.data.Callback;
import br.unisc.monitoramento.data.model.Perfil;
import br.unisc.monitoramento.data.model.Projeto;
import br.unisc.monitoramento.data.repository.Repositorios;
import br.unisc.monitoramento.databinding.ActivityHomeBinding;
import br.unisc.monitoramento.ui.comum.ItemAdapter;
import br.unisc.monitoramento.ui.comum.ItemLista;
import br.unisc.monitoramento.ui.estrutura.ListaEstruturaActivity;
import br.unisc.monitoramento.ui.indicadores.IndicadoresActivity;

/** Tela inicial: lista de projetos Muda e PSA do usuário autenticado (AM-02). */
public class HomeActivity extends AppCompatActivity {

    public static final String EXTRA_LOGIN = "extra_login";
    public static final String EXTRA_PERFIL = "extra_perfil";

    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String login = getIntent().getStringExtra(EXTRA_LOGIN);
        Perfil perfil = Perfil.valueOf(getIntent().getStringExtra(EXTRA_PERFIL));
        binding.textoBoasVindas.setText(getString(R.string.boas_vindas, login, perfil.getRotulo()));

        binding.listaProjetos.setLayoutManager(new LinearLayoutManager(this));
        binding.listaProjetos.setAdapter(new ItemAdapter(this::abrirPropriedades));

        binding.botaoIndicadores.setOnClickListener(v ->
                startActivity(new Intent(this, IndicadoresActivity.class)));

        carregarProjetos();
    }

    private void carregarProjetos() {
        binding.progresso.setVisibility(View.VISIBLE);
        binding.textoErro.setVisibility(View.GONE);

        Repositorios.projetos().listarProjetos(new Callback<List<Projeto>>() {
            @Override
            public void onSucesso(List<Projeto> projetos) {
                binding.progresso.setVisibility(View.GONE);
                List<ItemLista> itens = new ArrayList<>();
                for (Projeto p : projetos) {
                    itens.add(new ItemLista(p.getId(), p.getNome(), p.getTipo().getRotulo(), p));
                }
                ((ItemAdapter) binding.listaProjetos.getAdapter()).definirItens(itens);
            }

            @Override
            public void onErro(String mensagem) {
                binding.progresso.setVisibility(View.GONE);
                binding.textoErro.setText(mensagem);
                binding.textoErro.setVisibility(View.VISIBLE);
            }
        });
    }

    private void abrirPropriedades(ItemLista item) {
        Projeto projeto = (Projeto) item.getOrigem();
        Intent intent = new Intent(this, ListaEstruturaActivity.class);
        intent.putExtra(ListaEstruturaActivity.EXTRA_NIVEL, ListaEstruturaActivity.Nivel.PROPRIEDADES.name());
        intent.putExtra(ListaEstruturaActivity.EXTRA_PAI_ID, projeto.getId());
        intent.putExtra(ListaEstruturaActivity.EXTRA_TITULO, projeto.getNome());
        startActivity(intent);
    }
}
