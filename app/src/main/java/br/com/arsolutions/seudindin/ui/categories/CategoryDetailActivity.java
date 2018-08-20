package br.com.arsolutions.seudindin.ui.categories;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.regio.seudindin.R;

import br.com.arsolutions.seudindin.viewmodel.categories.model.CategoryModel;
import butterknife.BindView;
import butterknife.ButterKnife;


public class CategoryDetailActivity extends AppCompatActivity  implements CategoryDetailFragment.OnCategoryDetailListener {

    // Declaracao e alimentacao das variaveis
    @BindView(R.id.category_detail_toolbar) Toolbar toolbar;

    private CategoryDetailFragment categoryFragment;

    private final String STATE_FRAGMENT = "state_fragment";

    public static final String EXTRA_MODEL = "extra_model";
    public static final String EXTRA_OPERATION = "extra_operation";
    public static final String EXTRA_COMMAND = "extra_command";

    public static final String COMMAND_INSERT = "command_insert";
    public static final String COMMAND_UPDATE = "command_update";
    public static final String COMMAND_DELETE = "command_delete";

    private static final int PICK_CATEGORY_SELECT = 999;
    public static final int INSERT = 0;
    public static final int UPDATE = 1;
    private int operation = INSERT;

    private CategoryModel model = new CategoryModel();


    // Criacao da atividade
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_category_detail);
        ButterKnife.bind(this);

        // Configura os parametros passados
        setupParameters();

        // Configura o toolbar
        setupToolbar();

        // Definindo o tipo de operacao do activity
        setActivityOperation();

        // Configura informações do estado
        setupState(savedInstanceState);

    }


    // Evento de armazenamento do estado da instancia quando a atividade vai ser interrompida
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getSupportFragmentManager().putFragment(outState, STATE_FRAGMENT, categoryFragment);
        super.onSaveInstanceState(outState);
    }


    // Evento de restauracao da atividade
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        categoryFragment = (CategoryDetailFragment) getSupportFragmentManager().getFragment(savedInstanceState, STATE_FRAGMENT);
    }


    // Configura o evento do botão voltar
    @Override
    public void onBackPressed() {
        actionBack();
    }


    // Configura ações dos itens do menu da toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                actionBack();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    // Comando para voltar a atividade anterior
    private void actionBack() {
        AlertDialog.Builder alert = new AlertDialog.Builder(CategoryDetailActivity.this);
        alert
                .setTitle(R.string.command_cancel_title)
                .setMessage(R.string.command_cancel_dialog)
                .setPositiveButton(R.string.command_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        setResult(Activity.RESULT_CANCELED);
                        finish();
                    }
                })
                .setNegativeButton(R.string.command_no, null)
                .show();

    }

    // Configura os parametros passados
    private void setupParameters() {
        operation = getIntent().getIntExtra(EXTRA_OPERATION,INSERT);
        model = getIntent().getParcelableExtra(EXTRA_MODEL);
    }



    // Configura o toolbar
    private void setupToolbar() {
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_cancel);
    }


    // Atribui o tipo de operacao do activity: insercao ou atualizacao
    private void setActivityOperation() {

        switch (operation) {
            case INSERT:
                toolbar.setTitle(R.string.category_detail_title_insert);
                break;

            default:
                toolbar.setTitle(R.string.category_detail_title_edit);
                break;

        }
    }

    // Configura informações do estado
    private void setupState(Bundle savedInstanceState) {

        if (savedInstanceState == null) {

            // Configuracao do fragmento que controla a lista de contas
            Bundle args = new Bundle();
            args.putParcelable(CategoryDetailFragment.EXTRA_MODEL, model);
            args.putInt(CategoryDetailFragment.EXTRA_OPERATION, operation);
            categoryFragment = CategoryDetailFragment.newInstance(args);

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.category_detail_fragment_content, categoryFragment)
                    .commit();

        } else {
            categoryFragment = (CategoryDetailFragment) getSupportFragmentManager().getFragment(savedInstanceState, STATE_FRAGMENT);
        }
    }

    // Comando para selecionar uma categoria-pai
    @Override
    public void onSelectParentCategory(CategoryModel model) {
        //Intent intent = new Intent(this, CategoryListSelectActivity.class);
        //intent.putExtra("category_parent_id", model.getId());
        //intent.putExtra("category_id", categoryFragment.getModel().getId());

        CategoryModel parent = new CategoryModel();
        parent.setId(model.getParentId());
        parent.setName(model.getParentName());

        Intent intent = new Intent(this, CategoryListActivity.class);
        intent.putExtra(CategoryListActivity.EXTRA_PARENT, parent);
        intent.putExtra(CategoryListActivity.EXTRA_SELECTED, model);
        intent.putExtra(CategoryListActivity.EXTRA_EDITABLE, false);
        intent.putExtra(CategoryListActivity.EXTRA_SHOW_INSERT, false);

        startActivityForResult(intent,PICK_CATEGORY_SELECT);
    }


    // Evento disparado ao salvar uma categoria
    @Override
    public void onSaveCategory(CategoryModel model) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(EXTRA_COMMAND, operation == INSERT ? COMMAND_INSERT : COMMAND_UPDATE);
        resultIntent.putExtra(EXTRA_MODEL, model);
        setResult(Activity.RESULT_OK, resultIntent);

        Toast.makeText(getApplicationContext(), R.string.command_save_successful, Toast.LENGTH_LONG).show();
        finish();
    }


    // Evento disparado ao excluir uma categoria
    @Override
    public void onDeleteCategory(CategoryModel model) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(EXTRA_COMMAND, COMMAND_DELETE);
        resultIntent.putExtra(EXTRA_MODEL, model);
        setResult(Activity.RESULT_OK, resultIntent);

        Toast.makeText(getApplicationContext(), R.string.command_delete_successful, Toast.LENGTH_LONG).show();
        finish();
    }


    // Recupera a categoria-pai selecionada e atualiza os campos da tela
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (PICK_CATEGORY_SELECT) : {
                if (resultCode == Activity.RESULT_OK) {
                    CategoryModel parent = data.getParcelableExtra(CategoryListActivity.EXTRA_SELECTED);
                    categoryFragment.setParentCategory(parent);
                }
                break;
            }
        }
    }


}
