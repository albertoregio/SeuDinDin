package com.example.regio.seudindin.categories;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.regio.seudindin.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

// Classe responsavel por controlar o Activity da lista de categorias
public class CategoriesListActivity extends AppCompatActivity {

    // Declaracao e alimentacao das variaveis
    @BindView(R.id.fab_categories_insert) FloatingActionButton fab_insert;


    // Metodo responsavel pela criacao do activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories_list);
        ButterKnife.bind(this);
    }


    // Metodo responsavel pelo clique do botao da inclusao de uma nova categoria
    @OnClick(R.id.fab_categories_insert)
    public void editCategory() {
        Intent intent = new Intent(this, CategoriesDetailActivity.class);
        startActivity(intent);

    }

}
