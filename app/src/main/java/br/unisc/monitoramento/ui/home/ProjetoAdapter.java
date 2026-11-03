package br.unisc.monitoramento.ui.home;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.unisc.monitoramento.data.model.Projeto;
import br.unisc.monitoramento.databinding.ItemProjetoBinding;

public class ProjetoAdapter extends RecyclerView.Adapter<ProjetoAdapter.ProjetoViewHolder> {

    private final List<Projeto> itens = new ArrayList<>();

    public void definirItens(List<Projeto> novosItens) {
        itens.clear();
        itens.addAll(novosItens);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProjetoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProjetoBinding binding = ItemProjetoBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ProjetoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjetoViewHolder holder, int position) {
        holder.vincular(itens.get(position));
    }

    @Override
    public int getItemCount() {
        return itens.size();
    }

    static class ProjetoViewHolder extends RecyclerView.ViewHolder {

        private final ItemProjetoBinding binding;

        ProjetoViewHolder(ItemProjetoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void vincular(Projeto projeto) {
            binding.textoNome.setText(projeto.getNome());
            binding.textoTipo.setText(projeto.getTipo().getRotulo());
        }
    }
}
