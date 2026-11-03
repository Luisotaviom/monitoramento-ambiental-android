package br.unisc.monitoramento.ui.home;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

import br.unisc.monitoramento.R;
import br.unisc.monitoramento.data.Callback;
import br.unisc.monitoramento.data.model.Perfil;
import br.unisc.monitoramento.data.model.Projeto;
import br.unisc.monitoramento.data.repository.MockProjetoRepository;
import br.unisc.monitoramento.data.repository.ProjetoRepository;
import br.unisc.monitoramento.databinding.ActivityHomeBinding;

/** Tela inicial: lista de projetos Muda e PSA do usuário autenticado (AM-02). */
public class HomeActivity extends AppCompatActivity {

    public static final String EXTRA_LOGIN = "extra_login";
    public static final String EXTRA_PERFIL = "extra_perfil";

    private ActivityHomeBinding binding;
    private ProjetoRepository projetoRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String login = getIntent().getStringExtra(EXTRA_LOGIN);
        Perfil perfil = Perfil.valueOf(getIntent().getStringExtra(EXTRA_PERFIL));
        binding.textoBoasVindas.setText(getString(R.string.boas_vindas, login, perfil.getRotulo()));

        binding.listaProjetos.setLayoutManager(new LinearLayoutManager(this));
        binding.listaProjetos.setAdapter(new ProjetoAdapter());

        projetoRepository = new MockProjetoRepository();
        carregarProjetos();
    }

    private void carregarProjetos() {
        binding.progresso.setVisibility(View.VISIBLE);
        binding.textoErro.setVisibility(View.GONE);

        projetoRepository.listarProjetos(new Callback<List<Projeto>>() {
            @Override
            public void onSucesso(List<Projeto> projetos) {
                binding.progresso.setVisibility(View.GONE);
                ((ProjetoAdapter) binding.listaProjetos.getAdapter()).definirItens(projetos);
            }

            @Override
            public void onErro(String mensagem) {
                binding.progresso.setVisibility(View.GONE);
                binding.textoErro.setText(mensagem);
                binding.textoErro.setVisibility(View.VISIBLE);
            }
        });
    }
}
