package br.com.arsolutions.seudindin.ui.categories;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.regio.seudindin.R;
import br.com.arsolutions.seudindin.viewmodel.categories.model.CategoryModel;
import br.com.arsolutions.seudindin.viewmodel.categories.CategoryViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

// Classe responsavel por controlar o Activity da lista de categorias
public class CategoryListSelectActivity extends AppCompatActivity implements CategoryListFragment.OnCategoryListSelectListener {

    // Declaracao e alimentacao das variaveis
    @BindView(R.id.category_list_select_toolbar) Toolbar toolbar;
    private List<CategoryModel> categoryNavigation = new ArrayList<>();
    private CategoryListFragment categoryFragment;
    private CategoryViewModel categoryListViewModel;

    private final String STATE_NAVIGATION = "state_navigation";
    private final String STATE_FRAGMENT = "state_fragment";
    private int parent_id;
    private int id;


    // Metodo responsavel pela criacao do activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_category_list_select);
        ButterKnife.bind(this);

        // Configura o toolbar
        setupToolbar();

        // Configura informações do estado
        setupState(savedInstanceState);

    }


    // Evento de armazenamento do estado da instancia quando a atividade vai ser interrompida
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getSupportFragmentManager().putFragment(outState, STATE_FRAGMENT, categoryFragment);
        outState.putParcelableArrayList(STATE_NAVIGATION, (ArrayList<CategoryModel>) categoryNavigation);
        super.onSaveInstanceState(outState);
    }


    // Evento de restauracao da atividade
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        categoryNavigation = savedInstanceState.getParcelableArrayList(STATE_NAVIGATION);
        categoryFragment.setCategory_id(categoryNavigation.get(categoryNavigation.size() -1).getId());
    }


    // Configura o evento de clique em um item da lista de categorias
    @Override
    public void onCategoryListSelect(CategoryModel category) {

        // Se a categoria possuir filhos exibe a listagem com estes, caso nao possuia seleciona o item
        if (category.getChildrenCount() > 0) {

            // Adiciona um item da categoria na lista de hierarquia
            categoryNavigation.add(category);

            // Mostra a listagem das categorias filhas da categoria selecionada
            categoryFragment.setCategory_id(category.getId());
        }
        // Categoria selecionada
        else {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("category_parent_id", category.getId());
            resultIntent.putExtra("category_parent_name", category.getName());
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        }

    }


    // Configura ações dos itens do menu da toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    // Configura o evento do botão voltar
    @Override
    public void onBackPressed() {
        actionBack();
    }


    // Comando para voltar a atividade anterior
    private void actionBack() {

        // Remove um item da categoria
        if(categoryNavigation.size() > 0) {
            categoryNavigation.remove(categoryNavigation.size() - 1);
        }

        // Se a listagem já está mostrando as categorias raiz, entao finaliza a atividade
        if (categoryNavigation.size() == 0) {
            Intent resultIntent = new Intent();
            setResult(Activity.RESULT_CANCELED, resultIntent);
            finish();
        }
        // Mostra a listagem das categorias de nivel anterior
        else {
            categoryFragment.setCategory_id(categoryNavigation.get(categoryNavigation.size() -1).getId());
        }
    }


    // Configura o toolbar
    private void setupToolbar() {
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_cancel);
    }


    // Configura informações do estado
    private void setupState(Bundle savedInstanceState) {

        parent_id = getIntent().getIntExtra("category_parent_id", 0);
        id = getIntent().getIntExtra("category_id", 0);

        if (savedInstanceState == null) {

            // Controle da lista de categorias que foram selecionadas
            categoryNavigation.add(CategoryModel.getRoot());
            categoryListViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
            categoryNavigation.addAll(categoryListViewModel.getParentIdList(parent_id));

            // Configuracao do fragmento que controla a lista de categorias
            Bundle args = new Bundle();
            args.putInt("parent_id", parent_id);
            args.putInt("selected_id", id);

            categoryFragment = CategoryListFragment.newInstance(args);
            categoryFragment.setShowRoot(true);

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.category_list_select_activity_fragment_content, categoryFragment)
                    .commit();


        } else {
            categoryFragment = (CategoryListFragment) getSupportFragmentManager().getFragment(savedInstanceState, STATE_FRAGMENT);
        }
    }

}
