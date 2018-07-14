package com.example.regio.seudindin.categories.support;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.regio.seudindin.R;

import java.util.ArrayList;
import java.util.List;

// Classe responsavel por controlar os itens que serao exibidas na tela
public class CategoryAdapter extends RecyclerView.Adapter<CategoryHolder> {

    //Declaracao de variaveis
    private final List<CategoryModel> categories;


    // Construtor da classe
    public CategoryAdapter(ArrayList categories) {
        this.categories = categories;
    }


    // Metodo responsavel pela criacao da classe que representa a tela
    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_list_info, parent, false));
    }


    // Metodo responsavel pela a alimentacao dos dados da tela
    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, final int position) {
        holder.category.setText(categories.get(position).getNome());
    }


    // Recupera quantos itens de dados existem
    @Override
    public int getItemCount() {
        return (categories != null) ? categories.size() : 0;
    }


    // Insere na tela uma nova categoria
    public void insertCategory(CategoryModel item) {
        categories.add(item);
        notifyItemInserted(getItemCount());
    }


    // Remove da tela uma categoria na posicao determinada
    public void deleteCategory(int position) {
        categories.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, categories.size());
    }

}
