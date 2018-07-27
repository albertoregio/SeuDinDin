package com.example.regio.seudindin.ui.categories;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.regio.seudindin.R;
import com.example.regio.seudindin.model.CategoryModel;
import com.example.regio.seudindin.ui.categories.support.CategoryListAdapter;
import com.example.regio.seudindin.viewmodel.CategoryViewModelFactory;
import com.example.regio.seudindin.viewmodel.ICategoryViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

// Classe responsavel por controlar o Activity da lista de categorias
public class CategoryListActivity extends AppCompatActivity {

    // Declaracao e alimentacao das variaveis
    @BindView(R.id.fab_categories_insert) FloatingActionButton fab_insert;
    @BindView(R.id.categories_recycleview) RecyclerView rec_listaCategories;

    private ICategoryViewModel categoryListViewModel;
    private CategoryListAdapter categoryAdapter;
    private View.OnClickListener editOnClick;


    // Metodo responsavel pela criacao do activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);
        ButterKnife.bind(this);

        // Configura o evento de clique em um item da lista de categorias
        setupItemClick();

        // Configura o componente reclyclerview para a listagems das categorias
        setupRecycler();

        // Configura os observers do view model
        setupObservers();

    }


    // Metodo responsavel pelo clique do botao da inclusao de uma nova categoria
    @OnClick(R.id.fab_categories_insert)
    public void insertCategory() {
        Intent intent = new Intent(this, CategoryDetailActivity.class);
        intent.putExtra("operation", CategoryDetailActivity.INSERT);
        startActivity(intent);

    }

    // Metodo responsavel pela edicao de uma categoria
    public void editCategory(CategoryModel category) {
        Intent intent = new Intent(this, CategoryDetailActivity.class);
        intent.putExtra("operation", CategoryDetailActivity.UPDATE);
        intent.putExtra("category_id", category.getId());
        startActivity(intent);

    }

    // Configura o evento de clique em um item da lista de categorias
    private void setupItemClick() {
        editOnClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CategoryModel c = categoryAdapter.getSelectedItem();
                if (c != null)
                    editCategory(c);
                else
                    insertCategory();
            }
        };
    }

    // Metodo responsavel por configurar a listagem de categorias -
    private void setupRecycler() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        categoryAdapter = new CategoryListAdapter(this, new ArrayList<>(0));
        categoryAdapter.setEditListener(editOnClick);

        rec_listaCategories.setLayoutManager(layoutManager);
        rec_listaCategories.setAdapter(categoryAdapter);
        rec_listaCategories.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

    }

    // Metodo responsavel por configurar os observers do view model
    private void setupObservers() {
        final Observer<List<CategoryModel>> categoryListObserver = new Observer<List<CategoryModel>>() {
            @Override
            public void onChanged(@Nullable List<CategoryModel> categoryModelList) {
                categoryAdapter.setCategoryList(categoryModelList);
            }
        };

        //Recupera o viewModel e atribui os observers
        //categoryListViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        categoryListViewModel = CategoryViewModelFactory.getInstance(this);
        categoryListViewModel.load().observe(this,categoryListObserver);
    }

}
