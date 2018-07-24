package com.example.regio.seudindin.ui.categories.support;

import android.content.Context;
import android.content.res.TypedArray;
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

import java.util.ArrayList;
import java.util.List;

// Classe responsavel por controlar os itens que serao exibidas na tela
public class CategoryIconAdapter extends RecyclerView.Adapter<CategoryIconHolder> {

    //Declaracao de variaveis
    private final List<Integer> iconList;
    private final Context context;
    private int color = R.color.blue_200;
    private int selectedPosition = 0;


    // Construtor da classe
    public CategoryIconAdapter(Context context, ArrayList iconList) {
        this.iconList = iconList;
        this.context = context;

        // Insere os icones disponiveis para selecao
        final TypedArray imgs = context.getResources().obtainTypedArray(R.array.array_category_icon_image);
        for (int x=0; x < imgs.length(); x++) {
            insertIconDrawable(imgs.getResourceId(x,0));
        }

    }


    // Recupera o valor da propriedade color
    public int getColor() {
        return color;
    }


    // Atribui um valor a propriedade color
    public void setColor(int color) {
        this.color = color;
        notifyDataSetChanged();
    }

    public void setSelectedPosition(int position) {
        selectedPosition = position;
        notifyDataSetChanged();
    }


    // Metodo responsavel pela criacao da classe que representa a tela
    @NonNull
    @Override
    public CategoryIconHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryIconHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.category_icon_layout, parent, false));
    }


    // Metodo responsavel pela alimentacao dos dados da tela
    @Override
    public void onBindViewHolder(@NonNull CategoryIconHolder holder, final int position) {

        // Evento de clique no icone da categoria
        holder.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSelectedPosition(position);
            }
        });

        // Recuperando a camada
        final LayerDrawable layerDrawable = (LayerDrawable) holder.icon.getDrawable();

        // Cor do icone
        int color = ContextCompat.getColor(context, this.color);
        final GradientDrawable iconShapeDrawable = (GradientDrawable) layerDrawable.findDrawableByLayerId(R.id.ic_category_icon_color);

        // Imagem do icone
        Drawable drawable = ResourcesCompat.getDrawable(context.getResources(),iconList.get(position), null);
        layerDrawable.setDrawableByLayerId(R.id.ic_category_icon_image,drawable);
        iconShapeDrawable.setColor(color);

        // Borda do item selecionado
        if (position == selectedPosition)
            iconShapeDrawable.setStroke(10, Color.BLACK);
        else
            iconShapeDrawable.setStroke(0, Color.BLACK);

    }


    // Recupera quantos itens de dados existem
    @Override
    public int getItemCount() {
        return (iconList != null) ? iconList.size() : 0;
    }


    // Insere na tela um novo icone de categoria
    public void insertIconDrawable(Integer icon) {
        iconList.add(icon);
        notifyItemInserted(getItemCount());
    }

}
