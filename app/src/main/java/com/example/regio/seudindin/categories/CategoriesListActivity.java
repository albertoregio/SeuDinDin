package com.example.regio.seudindin.categories;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.regio.seudindin.R;
import com.example.regio.seudindin.categories.support.CategoryAdapter;
import com.example.regio.seudindin.categories.support.CategoryModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

// Classe responsavel por controlar o Activity da lista de categorias
public class CategoriesListActivity extends AppCompatActivity {

    // Declaracao e alimentacao das variaveis
    @BindView(R.id.fab_categories_insert) FloatingActionButton fab_insert;
    @BindView(R.id.categories_recycleview) RecyclerView rec_listaCategorias;

    private CategoryAdapter categoryAdapter;


    // Metodo responsavel pela criacao do activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories_list);
        ButterKnife.bind(this);
        setupRecycler();
    }


    // Metodo responsavel pelo clique do botao da inclusao de uma nova categoria
    @OnClick(R.id.fab_categories_insert)
    public void editCategory() {
        Intent intent = new Intent(this, CategoriesDetailActivity.class);
        startActivity(intent);

    }


    // Metodo responsavel por configurar a listagem de categorias -
    private void setupRecycler() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        categoryAdapter = new CategoryAdapter(new ArrayList<>(0));

        rec_listaCategorias.setLayoutManager(layoutManager);
        rec_listaCategorias.setAdapter(categoryAdapter);
        rec_listaCategorias.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        //Comando temporario
        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setNome("Teste");
        categoryAdapter.insertCategory(categoryModel);
    }


}
