package com.example.regio.seudindin.ui.categories;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.regio.seudindin.R;
import com.example.regio.seudindin.model.CategoryModel;
import com.example.regio.seudindin.ui.categories.support.CategoryIconAdapter;
import com.example.regio.seudindin.ui.categories.support.ColorSpinnerAdapter;
import com.example.regio.seudindin.viewmodel.CategoryViewModel;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

// Classe responsavel por controlar o Activity do detalhe de uma categoria
public class CategoryDetailActivity extends AppCompatActivity {

    // Declaracao e alimentacao das variaveis
    @BindView(R.id.categoriesDetail_toolbar) Toolbar toolbar;
    @BindView(R.id.categoriesDetail_btn_select_categories) Button parentButton;
    @BindView(R.id.categoriesDetail_edt_name) TextView nameTextView;
    @BindView(R.id.categoriesDetail_spn_select_colors) Spinner colorSpinner;
    @BindView(R.id.category_icons_recycleview) RecyclerView iconsRecycler;

    private static final int PICK_CATEGORY_SELECT = 999;
    public static final int INSERT = 0;
    public static final int UPDATE = 1;
    private int operation = INSERT;
    private int id = 0;
    private int color;
    private int parent_id;

    private CategoryIconAdapter categoryIconAdapter;
    private ColorSpinnerAdapter colorSpinnerAdapter;
    private Integer[] colorsList;
    private CategoryViewModel categoryViewModel;


    // Metodo responsavel pela criacao do activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_category_detail);
        ButterKnife.bind(this);

        // Recupera a lista de cores disponiveis
        int[] aux = getResources().getIntArray(R.array.array_color_spinner);
        colorsList = convertArrays(aux);

        // Configura o toolbar
        setupToolbar();

        // Configura os observers do view model
        setupObservers();

        // Configura o combobox de selecao de cores
        setupColorSelector();

        // Configura o componente reclyclerview para selecao do icone
        setupIconSelector();

        // Definindo o tipo de operacao do activity
        setActivityOperation();

    }


    // Configura o botao de selecao da categoria-pai
    @OnClick(R.id.categoriesDetail_btn_select_categories)
    public void selectCategoryParent() {
        Intent intent = new Intent(this, CategoryListSelectActivity.class);
        //intent.putExtra("category_parent_id", 0);
        startActivityForResult(intent,PICK_CATEGORY_SELECT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (PICK_CATEGORY_SELECT) : {
                if (resultCode == Activity.RESULT_OK) {
                    parent_id = data.getIntExtra("category_parent_id", 0);
                    parentButton.setText(String.valueOf(parent_id));
                }
                break;
            }
        }
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
    private void actionSave() {
        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setId(id);
        categoryModel.setParent_id(parent_id);
        categoryModel.setName(nameTextView.getText().toString());
        categoryModel.setColor(color);
        categoryModel.setIcon(categoryIconAdapter.getSelectedIcon());
        categoryViewModel.save(categoryModel);

        Toast.makeText(getApplicationContext(), "TODO: command save", Toast.LENGTH_LONG).show();
        finish();
    }


    // Comando para excluir uma categoria
    private void actionDelete() {
        AlertDialog.Builder alert = new AlertDialog.Builder(CategoryDetailActivity.this);
        alert
            .setTitle(R.string.command_delete_title)
            .setMessage(R.string.command_delete_dialog)
            .setPositiveButton(R.string.command_yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    categoryViewModel.delete(id);
                    Toast.makeText(getApplicationContext(), "TODO: command delete", Toast.LENGTH_LONG).show();
                    finish();
                }
            })
            .setNegativeButton(R.string.command_no, null)
            .show();

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
                    finish();
                }
            })
            .setNegativeButton(R.string.command_no, null)
            .show();

    }


    // Atribui o tipo de operacao do activity: insercao ou atualizacao
    private void setActivityOperation() {
        this.operation = getIntent().getIntExtra("operation",INSERT);
        id = getIntent().getIntExtra("category_id", 0);

        switch (operation) {
            case INSERT:
                toolbar.setTitle(R.string.categories_detail_activity_title_insert);
                break;

            default:
                toolbar.setTitle(R.string.categories_detail_activity_title_update);
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


    // Metodo responsavel por configurar os observers do view model
    private void setupObservers() {

        final Observer<CategoryModel> categoryObserver = new Observer<CategoryModel>() {
            @Override
            public void onChanged(@Nullable CategoryModel categoryModel) {

                if (categoryModel != null) {

                    // Atribui o id
                    id = categoryModel.getId();

                    // Atribui a categoria-pai
                    if (categoryModel.getParent_id() == null) {
                        parentButton.setText(R.string.categories_parent_value);
                    } else {
                        parentButton.setText(categoryModel.getParent_id().toString());
                    }


                    // Atribui a cor
                    color = categoryModel.getColor();
                    int index = Arrays.asList(colorsList).indexOf(color);
                    colorSpinner.setSelection(index);

                    // Atribui o icone
                    categoryIconAdapter.setSelectedIcon(categoryModel.getIcon());

                    // Atribui o nome
                    nameTextView.setText(categoryModel.getName());
                }
            }
        };

        //Recupera o viewModel e atribui os observers
        //categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModelArray.class);
        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        categoryViewModel.getCategoryDetailLiveData().observe(this,categoryObserver);
    }


    // Metodo responsavel por configurar a lista de cores disponiveis
    private void setupColorSelector() {
        colorSpinnerAdapter = new ColorSpinnerAdapter(this, R.layout.spinner_color_picker, Arrays.asList(colorsList));
        colorSpinner.setAdapter(colorSpinnerAdapter);
        colorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int index = adapterView.getSelectedItemPosition();
                color = colorsList[index];
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
        categoryIconAdapter.setSelectedIcon(R.drawable.ic_category_icon_calculator);

        iconsRecycler.setAdapter(categoryIconAdapter);
        iconsRecycler.setLayoutManager(layoutManager);
    }


    // Metodo auxiliar para converter umtipo de array e outro
    private Integer[] convertArrays(int[] array) {
        Integer[] integerArray = new Integer[array.length];
        for (int i = 0; i < array.length; i++) {
            integerArray[i] = array[i];
        }
        return integerArray;
    }

}
