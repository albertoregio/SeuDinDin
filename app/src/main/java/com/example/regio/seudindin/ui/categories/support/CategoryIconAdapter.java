package com.example.regio.seudindin.ui.categories.support;

import android.annotation.SuppressLint;
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
    private int color = R.color.default_color_spinner;
    private int selectedIcon = 0;


    // Construtor da classe
    public CategoryIconAdapter(Context context, ArrayList iconList) {
        this.iconList = iconList;
        this.context = context;

        // Insere os icones disponiveis para selecao
        final TypedArray imgs = context.getResources().obtainTypedArray(R.array.array_category_icon_image);
        for (int x=0; x < imgs.length(); x++) {
            iconList.add(imgs.getResourceId(x,0));
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


    // Recupera o icone selecionado
    public int getSelectedIcon() {
        return selectedIcon;
    }


    // Atribui qual o icone deve ser selecionado
    public void setSelectedIcon(int icon) {
        selectedIcon = icon;
        notifyDataSetChanged();
    }


    // Metodo responsavel pela criacao da classe que representa a tela
    @NonNull
    @Override
    public CategoryIconHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryIconHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.category_icon_layout, parent, false));
    }


    // Metodo responsavel pela alimentacao dos dados da tela
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull CategoryIconHolder holder, final int position) {

        // Evento de clique no icone da categoria
        holder.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSelectedIcon(iconList.get(position));
            }
        });

        // Recuperando a camada
        final LayerDrawable layerDrawable = (LayerDrawable) holder.icon.getDrawable();

        // Cor do icone
        int color = ContextCompat.getColor(context, this.color);
        //int color = this.color;
        final GradientDrawable iconShapeDrawable = (GradientDrawable) layerDrawable.findDrawableByLayerId(R.id.ic_category_icon_color);
        iconShapeDrawable.setColor(color);

        // Imagem do icone
        Drawable drawable = ResourcesCompat.getDrawable(context.getResources(),iconList.get(position), null);
        layerDrawable.setDrawableByLayerId(R.id.ic_category_icon_image,drawable);


        // Borda do item selecionado
        if (iconList.get(position) == selectedIcon)
            iconShapeDrawable.setStroke(10, Color.BLACK);
        else
            iconShapeDrawable.setStroke(0, Color.BLACK);

    }


    // Recupera quantos itens de dados existem
    @Override
    public int getItemCount() {
        return (iconList != null) ? iconList.size() : 0;
    }

}
