package com.example.regio.seudindin.ui.categories.support;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.regio.seudindin.R;

import java.util.ArrayList;
import java.util.List;

// Classe responsavel por controlar os itens que serao exibidas na tela
public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListHolder> {

    //Declaracao de variaveis
    private final List<CategoryListModel> categories;
    private final Context mContext;
    private CategoryListModel selectedItem;
    private View.OnClickListener editListener;

    // Recupera a categoria selecionada
    public CategoryListModel getSelectedItem() {
        return selectedItem;
    }

    // Construtor da classe
    public CategoryListAdapter(Context context, ArrayList categories) {
        this.categories = categories;
        this.mContext = context;
    }


    // Metodo responsavel pela criacao da classe que representa a tela
    @NonNull
    @Override
    public CategoryListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryListHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list_info_layout, parent, false));
    }


    // Metodo responsavel pela a alimentacao dos dados da tela
    @Override
    public void onBindViewHolder(@NonNull CategoryListHolder holder, final int position) {

        // Campo de texto
        holder.category.setText(categories.get(position).getNome());

        // Cor do icone
        int color = ContextCompat.getColor(mContext, categories.get(position).getColor());
        final LayerDrawable shapeDrawable = (LayerDrawable) holder.icon.getDrawable();
        final GradientDrawable gradientDrawable = (GradientDrawable) shapeDrawable.findDrawableByLayerId(R.id.ic_category_icon_color);
        gradientDrawable.setColor(color);

        // Evento de clique
        holder.layout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                selectedItem = categories.get(position);
                if (editListener != null) {
                    editListener.onClick(v);
                }
            }
        });

    }


    // Recupera quantos itens de dados existem
    @Override
    public int getItemCount() {
        return (categories != null) ? categories.size() : 0;
    }


    // Insere na tela uma nova categoria
    public void insertCategory(CategoryListModel item) {
        categories.add(item);
        notifyItemInserted(getItemCount());
    }


    // Remove da tela uma categoria na posicao determinada
    public void deleteCategory(int position) {
        categories.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, categories.size());
    }


    // Recupera o listener responsavel por comandos de clique contidos na classe que instancia este objeto
    public View.OnClickListener getEditListener() {
        return editListener;
    }


    // Atribui um listener responsavel por comandos de clique contidos na classe que instancia este objeto
    public void setEditListener(View.OnClickListener editListener) {
        this.editListener = editListener;
    }

}
