package com.example.regio.seudindin.ui.categories.support;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.regio.seudindin.R;
import com.example.regio.seudindin.model.CategoryModel;
import com.example.regio.seudindin.persistence.dao.query_model.CategoryChildrenCountQuery;

import java.util.ArrayList;
import java.util.List;

// Classe responsavel por controlar os itens que serao exibidas na tela
public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListHolder> {

    //Declaracao de variaveis
    private final Context mContext;
    private List<CategoryChildrenCountQuery> categoryList;
    private CategoryChildrenCountQuery selectedItem;
    private View.OnClickListener editListener;


    // Construtor da classe
    public CategoryListAdapter(Context context, ArrayList categories) {
        this.categoryList = categories;
        this.mContext = context;
    }


    // Metodo responsavel pela criacao da classe que representa a tela
    @NonNull
    @Override
    public CategoryListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryListHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list_layout, parent, false));
    }


    // Metodo responsavel pela a alimentacao dos dados da tela
    @Override
    public void onBindViewHolder(@NonNull CategoryListHolder holder, final int position) {

        final CategoryChildrenCountQuery category = categoryList.get(position);

        // Campo de texto
        holder.category.setText(category.getName());

        // Recuperando a camada
        final LayerDrawable layerDrawable = (LayerDrawable) holder.icon.getDrawable();

        // Cor do icone
        //int color = ContextCompat.getColor(mContext, category.getColor());
        int color = category.getColor();
        final GradientDrawable gradientDrawable = (GradientDrawable) layerDrawable.findDrawableByLayerId(R.id.ic_category_icon_color);
        gradientDrawable.setColor(color);
        gradientDrawable.setStroke(0, Color.BLACK);

        // Imagem do icone
        Drawable drawable = ResourcesCompat.getDrawable(mContext.getResources(),category.getIcon(), null);
        layerDrawable.setDrawableByLayerId(R.id.ic_category_icon_image,drawable);

        // Imagem da seta
        if (category.getQtde() == 0) {
            holder.arrow.setVisibility(View.INVISIBLE);
        } else {
            holder.arrow.setVisibility(View.VISIBLE);
        }

        // Evento de clique
        holder.layout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                selectedItem = category;
                if (editListener != null) {
                    editListener.onClick(v);
                }
            }
        });

    }


    // Recupera quantos itens de dados existem
    @Override
    public int getItemCount() {
        return (categoryList != null) ? categoryList.size() : 0;
    }


    // Atribui uma nova lista e atualiza ui
    public void setCategoryList(List<CategoryChildrenCountQuery> categories) {
        this.categoryList = categories;
        notifyDataSetChanged();

    }


    // Recupera a categoria selecionada
    public CategoryChildrenCountQuery getSelectedItem() {
        return selectedItem;
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
