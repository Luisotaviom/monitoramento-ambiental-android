package br.unisc.monitoramento.ui.estrutura;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import br.unisc.monitoramento.data.Callback;
import br.unisc.monitoramento.data.model.Area;
import br.unisc.monitoramento.data.model.PontoColeta;
import br.unisc.monitoramento.data.model.Propriedade;
import br.unisc.monitoramento.data.repository.Repositorios;
import br.unisc.monitoramento.databinding.ActivityListaEstruturaBinding;
import br.unisc.monitoramento.ui.comum.ItemAdapter;
import br.unisc.monitoramento.ui.comum.ItemLista;
import br.unisc.monitoramento.ui.formulario.FormularioActivity;

/**
 * Lista de um nível da estrutura: propriedades de um projeto, áreas de uma
 * propriedade ou pontos de coleta de uma área. O clique avança para o nível seguinte
 * ou, no último nível, abre o formulário de coleta.
 */
public class ListaEstruturaActivity extends AppCompatActivity {

    public static final String EXTRA_NIVEL = "extra_nivel";
    public static final String EXTRA_PAI_ID = "extra_pai_id";
    public static final String EXTRA_TITULO = "extra_titulo";

    public enum Nivel {
        PROPRIEDADES,
        AREAS,
        PONTOS
    }

    private ActivityListaEstruturaBinding binding;
    private Nivel nivel;
    private String paiId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListaEstruturaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        nivel = Nivel.valueOf(getIntent().getStringExtra(EXTRA_NIVEL));
        paiId = getIntent().getStringExtra(EXTRA_PAI_ID);
        binding.textoTitulo.setText(getIntent().getStringExtra(EXTRA_TITULO));

        binding.lista.setLayoutManager(new LinearLayoutManager(this));
        binding.lista.setAdapter(new ItemAdapter(this::aoClicar));

        carregar();
    }

    private void carregar() {
        binding.progresso.setVisibility(View.VISIBLE);
        binding.textoErro.setVisibility(View.GONE);

        switch (nivel) {
            case PROPRIEDADES:
                Repositorios.projetos().listarPropriedades(paiId, new Callback<List<Propriedade>>() {
                    @Override
                    public void onSucesso(List<Propriedade> itens) {
                        List<ItemLista> lista = new ArrayList<>();
                        for (Propriedade p : itens) {
                            lista.add(new ItemLista(p.getId(), p.getNome(), p.getMunicipio(), p));
                        }
                        mostrar(lista);
                    }

                    @Override
                    public void onErro(String mensagem) {
                        mostrarErro(mensagem);
                    }
                });
                break;

            case AREAS:
                Repositorios.projetos().listarAreas(paiId, new Callback<List<Area>>() {
                    @Override
                    public void onSucesso(List<Area> itens) {
                        List<ItemLista> lista = new ArrayList<>();
                        for (Area a : itens) {
                            lista.add(new ItemLista(a.getId(), a.getNome(),
                                    String.format("%.1f ha", a.getHectares()), a));
                        }
                        mostrar(lista);
                    }

                    @Override
                    public void onErro(String mensagem) {
                        mostrarErro(mensagem);
                    }
                });
                break;

            case PONTOS:
                Repositorios.pontos().listarPontos(paiId, new Callback<List<PontoColeta>>() {
                    @Override
                    public void onSucesso(List<PontoColeta> itens) {
                        List<ItemLista> lista = new ArrayList<>();
                        for (PontoColeta p : itens) {
                            lista.add(new ItemLista(p.getId(), p.getNome(),
                                    String.format("%.4f, %.4f", p.getLatitude(), p.getLongitude()), p));
                        }
                        mostrar(lista);
                    }

                    @Override
                    public void onErro(String mensagem) {
                        mostrarErro(mensagem);
                    }
                });
                break;

            default:
                break;
        }
    }

    private void mostrar(List<ItemLista> itens) {
        binding.progresso.setVisibility(View.GONE);
        if (itens.isEmpty()) {
            mostrarErro(getString(br.unisc.monitoramento.R.string.lista_vazia));
            return;
        }
        ((ItemAdapter) binding.lista.getAdapter()).definirItens(itens);
    }

    private void mostrarErro(String mensagem) {
        binding.progresso.setVisibility(View.GONE);
        binding.textoErro.setText(mensagem);
        binding.textoErro.setVisibility(View.VISIBLE);
    }

    private void aoClicar(ItemLista item) {
        switch (nivel) {
            case PROPRIEDADES:
                abrirProximo(Nivel.AREAS, item.getId(), item.getTitulo());
                break;

            case AREAS:
                abrirProximo(Nivel.PONTOS, item.getId(), item.getTitulo());
                break;

            case PONTOS:
                PontoColeta ponto = (PontoColeta) item.getOrigem();
                Intent intent = new Intent(this, FormularioActivity.class);
                intent.putExtra(FormularioActivity.EXTRA_PONTO_ID, ponto.getId());
                intent.putExtra(FormularioActivity.EXTRA_PONTO_NOME, ponto.getNome());
                intent.putExtra(FormularioActivity.EXTRA_LATITUDE, ponto.getLatitude());
                intent.putExtra(FormularioActivity.EXTRA_LONGITUDE, ponto.getLongitude());
                startActivity(intent);
                break;

            default:
                break;
        }
    }

    private void abrirProximo(Nivel proximo, String id, String titulo) {
        Intent intent = new Intent(this, ListaEstruturaActivity.class);
        intent.putExtra(EXTRA_NIVEL, proximo.name());
        intent.putExtra(EXTRA_PAI_ID, id);
        intent.putExtra(EXTRA_TITULO, titulo);
        startActivity(intent);
    }
}
