package br.unisc.monitoramento.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;

import br.unisc.monitoramento.data.Callback;
import br.unisc.monitoramento.data.model.Perfil;
import br.unisc.monitoramento.data.model.Usuario;
import br.unisc.monitoramento.data.repository.AuthRepository;
import br.unisc.monitoramento.data.repository.MockAuthRepository;
import br.unisc.monitoramento.databinding.ActivityLoginBinding;
import br.unisc.monitoramento.ui.home.HomeActivity;
import br.unisc.monitoramento.util.Validacao;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private AuthRepository authRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        authRepository = new MockAuthRepository();

        ArrayAdapter<Perfil> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, Perfil.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerPerfil.setAdapter(adapter);

        binding.botaoEntrar.setOnClickListener(v -> tentarEntrar());
    }

    private void tentarEntrar() {
        String login = binding.campoLogin.getText() != null
                ? binding.campoLogin.getText().toString() : "";
        String senha = binding.campoSenha.getText() != null
                ? binding.campoSenha.getText().toString() : "";

        if (!Validacao.preenchido(login) || !Validacao.preenchido(senha)) {
            binding.textoErro.setText(getString(br.unisc.monitoramento.R.string.erro_campos_obrigatorios));
            binding.textoErro.setVisibility(View.VISIBLE);
            return;
        }

        binding.textoErro.setVisibility(View.GONE);
        binding.progresso.setVisibility(View.VISIBLE);
        binding.botaoEntrar.setEnabled(false);

        Perfil perfil = (Perfil) binding.spinnerPerfil.getSelectedItem();

        authRepository.entrar(login, senha, perfil, new Callback<Usuario>() {
            @Override
            public void onSucesso(Usuario usuario) {
                binding.progresso.setVisibility(View.GONE);
                abrirHome(usuario);
            }

            @Override
            public void onErro(String mensagem) {
                binding.progresso.setVisibility(View.GONE);
                binding.botaoEntrar.setEnabled(true);
                binding.textoErro.setText(mensagem);
                binding.textoErro.setVisibility(View.VISIBLE);
            }
        });
    }

    private void abrirHome(Usuario usuario) {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra(HomeActivity.EXTRA_LOGIN, usuario.getLogin());
        intent.putExtra(HomeActivity.EXTRA_PERFIL, usuario.getPerfil().name());
        startActivity(intent);
        finish();
    }
}
