package com.example.regio.seudindin.ui.categories.support;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.regio.seudindin.R;

import butterknife.BindView;
import butterknife.ButterKnife;

// Classe que representa a tela de informacoes da categoria
public class CategoryListHolder extends RecyclerView.ViewHolder {

    // Declaracao e alimentacao das variaveis
    @BindView(R.id.category_list_image) ImageView icon;
    @BindView(R.id.category_list_name) TextView category;
    @BindView(R.id.categories_list_arrow_info) ImageView arrow;
    @BindView(R.id.categories_list_layout) RelativeLayout layout;


    // Construtor da classe
    public CategoryListHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
