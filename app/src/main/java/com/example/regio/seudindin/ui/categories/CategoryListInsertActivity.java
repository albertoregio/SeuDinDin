package com.example.regio.seudindin.ui.categories;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.regio.seudindin.R;
import com.example.regio.seudindin.model.CategoryModel;
import com.example.regio.seudindin.persistence.dao.query_model.CategoryChildrenCountQuery;

import butterknife.ButterKnife;
import butterknife.OnClick;

// Classe responsavel por controlar o Activity da lista de categorias
public class CategoryListInsertActivity extends AppCompatActivity implements CategoryListFragment.OnCategoryListSelectListener {

    // Declaracao e alimentacao das variaveis


    // Metodo responsavel pela criacao do activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list_insert);
        ButterKnife.bind(this);

        CategoryListFragment fragment = CategoryListFragment.newInstance(0);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.category_list_insert_activity_fragment_content, fragment)
                .commit();
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
    @Override
    public void onCategoryListSelect(CategoryChildrenCountQuery category) {
        if (category != null)
            editCategory(category);
        else
            insertCategory();

    }

}
