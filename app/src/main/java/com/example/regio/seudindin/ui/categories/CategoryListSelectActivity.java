package com.example.regio.seudindin.ui.categories;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.regio.seudindin.R;
import com.example.regio.seudindin.persistence.dao.query_model.CategoryChildrenCountQuery;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

// Classe responsavel por controlar o Activity da lista de categorias
public class CategoryListSelectActivity extends AppCompatActivity implements CategoryListFragment.OnCategoryListSelectListener {

    // Declaracao e alimentacao das variaveis
    private List<Integer> categoryHierarchy = new ArrayList<Integer>();
    private CategoryListFragment categorySelectFragment;


    // Metodo responsavel pela criacao do activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list_select);
        ButterKnife.bind(this);

        // Controle da lista de categorias que foram selecionadas
        categoryHierarchy.add(0);

        // Configuracao do fragmento que controla a lista de categorias
        categorySelectFragment = CategoryListFragment.newInstance(0);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.category_list_select_activity_fragment_content, categorySelectFragment)
                .commit();
    }


    // Configura o evento de clique em um item da lista de categorias
    @Override
    public void onCategoryListSelect(CategoryChildrenCountQuery category) {

        if (category.getQtde() > 0) {

            // Adiciona um item da categoria na lista de hierarquia
            categoryHierarchy.add(category.getId());

            // Mostra a listagem das categorias filhas da categoria selecionada
            categorySelectFragment.setCategory_id(category.getId());
        }
        // Categoria selecionada
        else {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("category_parent_id", category.getId());
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        }

    }

    // Configura o evento do botão voltar
    @Override
    public void onBackPressed() {

        // Remove um item da categoria
        categoryHierarchy.remove(categoryHierarchy.size() -1);

        // A listagem ja esta mostrando as categorias raiz, entao finaliza a atividade
        if (categoryHierarchy.size() == 0) {
            Intent resultIntent = new Intent();
            setResult(Activity.RESULT_CANCELED, resultIntent);
            finish();
        }
        // Mostra a listagem das categorias de nivel anterior
        else {
            categorySelectFragment.setCategory_id(categoryHierarchy.get(categoryHierarchy.size() -1));
        }
    }


}
