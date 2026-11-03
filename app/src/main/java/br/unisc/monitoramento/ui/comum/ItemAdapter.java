package br.unisc.monitoramento.ui.comum;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.unisc.monitoramento.databinding.ItemProjetoBinding;

/** Adapter único para as listas de projetos, propriedades, áreas e pontos. */
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    public interface OnClique {
        void onClique(ItemLista item);
    }

    private final List<ItemLista> itens = new ArrayList<>();
    private final OnClique onClique;

    public ItemAdapter(OnClique onClique) {
        this.onClique = onClique;
    }

    public void definirItens(List<ItemLista> novosItens) {
        itens.clear();
        itens.addAll(novosItens);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProjetoBinding binding = ItemProjetoBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        ItemLista item = itens.get(position);
        holder.binding.textoNome.setText(item.getTitulo());
        holder.binding.textoTipo.setText(item.getSubtitulo());
        holder.itemView.setOnClickListener(v -> onClique.onClique(item));
    }

    @Override
    public int getItemCount() {
        return itens.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        final ItemProjetoBinding binding;

        ItemViewHolder(ItemProjetoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
