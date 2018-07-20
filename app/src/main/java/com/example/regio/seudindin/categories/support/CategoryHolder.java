package com.example.regio.seudindin.categories.support;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.regio.seudindin.R;

import butterknife.BindBitmap;
import butterknife.BindView;
import butterknife.ButterKnife;

// Classe que representa a tela de informacoes da categoria
public class CategoryHolder extends RecyclerView.ViewHolder {

    // Declaracao e alimentacao das variaveis
    @BindView(R.id.categories_list_image_info) public ImageView icon;
    @BindView(R.id.categories_list_name_info) public TextView category;


    // Construtor da classe
    public CategoryHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
