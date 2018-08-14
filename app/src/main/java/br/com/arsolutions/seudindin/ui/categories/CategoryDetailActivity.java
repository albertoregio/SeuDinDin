package br.com.arsolutions.seudindin.ui.categories;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import br.com.arsolutions.seudindin.App;
import com.example.regio.seudindin.R;
import br.com.arsolutions.seudindin.viewmodel.categories.model.CategoryModel;
import br.com.arsolutions.seudindin.ui.categories.support.CategoryIconAdapter;
import br.com.arsolutions.seudindin.viewmodel.categories.CategoryViewModel;

import java.util.ArrayList;

import br.com.arsolutions.components.ColorSpinner;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

// Classe responsavel por controlar o Activity do detalhe de uma categoria
public class CategoryDetailActivity extends AppCompatActivity {

    // Declaracao e alimentacao das variaveis
    @BindView(R.id.categoriesDetail_toolbar) Toolbar toolbar;
    @BindView(R.id.categoriesDetail_btn_select_categories) Button parentButton;
    @BindView(R.id.categoriesDetail_edt_name) TextView nameTextView;
    @BindView(R.id.categoriesDetail_spn_select_colors) ColorSpinner colorSpinner;
    @BindView(R.id.category_icons_recycleview) RecyclerView iconsRecycler;
    @BindView(R.id.categoriesDetail_colors_label) TextView iconColorTextView;
    @BindView(R.id.categoriesDetail_recycleview_label) TextView iconSelectTextView;
    @BindView(R.id.categoriesDetail_spn_line_separator) View lineSeparatorView;
    @BindView(R.id.categoriesDetail_inputLayout_name) TextInputLayout nameInputLayout;

    private final String STATE_PARENT_ID = "state_parent_id";
    private final String STATE_PARENT_NAME = "state_parent_name";
    private static final int PICK_CATEGORY_SELECT = 999;
    public static final int INSERT = 0;
    public static final int UPDATE = 1;

    private int operation = INSERT;
    private int id = 0;
    private int color;
    private int childrenCount = 0;
    private int parent_id;
    private String parent_name;

    private CategoryIconAdapter categoryIconAdapter;
    private CategoryViewModel categoryViewModel;


    // Metodo responsavel pela criacao do activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_category_detail);
        ButterKnife.bind(this);

        // Configura o toolbar
        setupToolbar();

        // Configura informações do estado
        setupState(savedInstanceState);

        // Configura os observers do view model
        setupObservers();

        // Configura o combobox de selecao de cores
        setupColorSelector();

        // Configura o componente reclyclerview para selecao do icone
        setupIconSelector();

        // Definindo o tipo de operacao do activity
        setActivityOperation();

        // Define a visibilidade dos componentes da atela
        setupVisibility(parent_id == 0);
    }


    // Evento de armazenamento do estado da instancia quando a atividade vai ser interrompida
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_PARENT_ID, parent_id);
        outState.putString(STATE_PARENT_NAME, parent_name);
        super.onSaveInstanceState(outState);
    }


    // Configura o menu da toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_categories_detail_toolbar, menu);

        MenuItem itemDelete = menu.findItem(R.id.menuitem_categories_detail_delete);
        itemDelete.setVisible(operation != INSERT);

        return true;
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

            case R.id.menuitem_categories_detail_save:
                actionSave();
                return true;

            case R.id.menuitem_categories_detail_delete:
                actionDelete();
                return true;

            case android.R.id.home:
                actionBack();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    // Comando para salvar as informacoes da categoria
    @SuppressLint("StringFormatInvalid")
    private void actionSave() {
        if (!nameTextView.getText().toString().equals("")) {
            CategoryModel categoryModel = new CategoryModel();
            categoryModel.setId(id);
            categoryModel.setParentId(parent_id);
            categoryModel.setName(nameTextView.getText().toString());
            categoryModel.setColorName(App.getResourceName(color));
            categoryModel.setIconName(App.getResourceName(categoryIconAdapter.getSelectedIcon()));
            int newId = categoryViewModel.save(categoryModel);

            Intent resultIntent = new Intent();
            resultIntent.putExtra("category_id", newId);
            setResult(Activity.RESULT_OK, resultIntent);

            Toast.makeText(getApplicationContext(), R.string.command_save_successful, Toast.LENGTH_LONG).show();
            finish();
        } else {
            nameInputLayout.setError(String.format(getString(R.string.command_save_unsuccessful), getString(R.string.category_name_label)));
        }
    }


    // Comando para excluir uma categoria
    private void actionDelete() {
        if (childrenCount == 0) {

            AlertDialog.Builder alert = new AlertDialog.Builder(CategoryDetailActivity.this);
            alert
                    .setTitle(R.string.command_delete_title)
                    .setMessage(R.string.category_command_delete_dialog)
                    .setPositiveButton(R.string.command_yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                                categoryViewModel.delete(id);

                                Intent resultIntent = new Intent();
                                resultIntent.putExtra("category_id", parent_id);
                                setResult(Activity.RESULT_OK, resultIntent);

                                Toast.makeText(getApplicationContext(), R.string.command_delete_successful, Toast.LENGTH_LONG).show();
                                finish();
                        }
                    })
                    .setNegativeButton(R.string.command_no, null)
                    .show();
        } else {
            AlertDialog.Builder unsuccessful = new AlertDialog.Builder(CategoryDetailActivity.this);
            unsuccessful
                    .setTitle(R.string.command_delete_title)
                    .setMessage(R.string.category_command_delete_unsuccessful)
                    .setPositiveButton(R.string.command_ok,null)
                    .show();
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


    // Comando para selecionar uma categoria-pai
    @OnClick(R.id.categoriesDetail_btn_select_categories)
    public void actionSelectCategoryParent() {
        Intent intent = new Intent(this, CategoryListSelectActivity.class);
        intent.putExtra("category_parent_id", parent_id);
        intent.putExtra("category_id", id);
        startActivityForResult(intent,PICK_CATEGORY_SELECT);
    }


    // Recupera a categoria-pai selecionada e atualiza os campos da tela
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (PICK_CATEGORY_SELECT) : {
                if (resultCode == Activity.RESULT_OK) {
                    parent_id = data.getIntExtra("category_parent_id", 0);
                    parent_name = data.getStringExtra("category_parent_name");
                    parentButton.setText(parent_name);
                    setupVisibility(parent_id == 0);
                }
                break;
            }
        }
    }


    // Atribui o tipo de operacao do activity: insercao ou atualizacao
    private void setActivityOperation() {

        switch (operation) {
            case INSERT:
                toolbar.setTitle(R.string.category_detail_title_insert);
                break;

            default:
                toolbar.setTitle(R.string.category_detail_title_edit);
                categoryViewModel.setCategoryDetailIdInput(id);
                break;

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

        operation = getIntent().getIntExtra("operation",INSERT);

        if (savedInstanceState == null) {

            id = getIntent().getIntExtra("category_id", 0);
            parent_id = getIntent().getIntExtra("category_parent_id", 0);
            parent_name = getIntent().getStringExtra("category_parent_name");

        } else {
            parent_id = savedInstanceState.getInt(STATE_PARENT_ID, 0);
            parent_name = savedInstanceState.getString(STATE_PARENT_NAME);
        }

        if (parent_name == null) {
            parent_name = CategoryModel.getRoot().getParentName();
            //App.getContext().getString(R.string.category_parent_novalue);
        }
        parentButton.setText(parent_name);
    }


    // Metodo responsavel por configurar os observers do view model
    private void setupObservers() {

        final Observer<CategoryModel> categoryObserver = categoryModel -> {

            if (categoryModel != null) {

                // Atribui o id
                id = categoryModel.getId();

                // Atribui a categoria-pai
                parent_id = categoryModel.getParentId();
                parentButton.setText(categoryModel.getParentName());
                childrenCount = categoryModel.getChildrenCount();

                // Atribui o nome
                nameTextView.setText(categoryModel.getName());

                // Atribui a cor
                color = categoryModel.getColor();
                int index = colorSpinner.getIndex(color); //*****
                colorSpinner.setSelection(index);

                // Atribui o icone
                categoryIconAdapter.setSelectedIcon(categoryModel.getIcon());

                setupVisibility(categoryModel.isShowIcon());
            }
        };

        //Recupera o viewModel e atribui os observers
        //categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModelArray.class);
        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        categoryViewModel.getCategoryDetailLiveData().observe(this,categoryObserver);
    }


    // Metodo responsavel por configurar a lista de cores disponiveis
    private void setupColorSelector() {
        colorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                color = colorSpinner.getColor(adapterView.getSelectedItemPosition());
                categoryIconAdapter.setColor(color);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    // Metodo responsavel por configurar a lista de icones de categorias -
    private void setupIconSelector() {
        GridLayoutManager layoutManager = new GridLayoutManager(this,3,GridLayoutManager.HORIZONTAL,false);

        categoryIconAdapter = new CategoryIconAdapter(this, new ArrayList<Integer>(0));
        categoryIconAdapter.setSelectedIcon(R.drawable.ic_category_icon_empty);

        iconsRecycler.setAdapter(categoryIconAdapter);
        iconsRecycler.setLayoutManager(layoutManager);
    }


    // Configura a visibilidade dos componentes da tela
    private void setupVisibility(boolean flag) {
        if (flag) {

            // Visibilidade
            colorSpinner.setVisibility(View.VISIBLE);
            iconsRecycler.setVisibility(View.VISIBLE);
            iconColorTextView.setVisibility(View.VISIBLE);
            iconSelectTextView.setVisibility(View.VISIBLE);
            lineSeparatorView.setVisibility(View.VISIBLE);

        } else {

            // Visibilidade
            colorSpinner.setVisibility(View.INVISIBLE);
            iconsRecycler.setVisibility(View.INVISIBLE);
            iconColorTextView.setVisibility(View.INVISIBLE);
            iconSelectTextView.setVisibility(View.INVISIBLE);
            lineSeparatorView.setVisibility(View.INVISIBLE);
        }

    }


    // Metodo auxiliar para converter um tipo de array e outro
    private Integer[] convertArrays(TypedArray array) {
        Integer[] integerArray = new Integer[array.length()];
        for (int i = 0; i < array.length(); i++) {
            integerArray[i] = array.getResourceId(i,1);
        }
        return integerArray;
    }
}
