package com.example.regio.seudindin.ui.categories.support;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.regio.seudindin.R;

import butterknife.BindView;
import butterknife.ButterKnife;

// Classe que representa a tela de informacoes da categoria
public class CategoryIconHolder extends RecyclerView.ViewHolder {

    // Declaracao e alimentacao das variaveis
    @BindView(R.id.category_icon_image_info) public ImageView icon;


    // Construtor da classe
    public CategoryIconHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
