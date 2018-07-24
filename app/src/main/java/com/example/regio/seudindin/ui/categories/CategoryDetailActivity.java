package com.example.regio.seudindin.ui.categories;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.res.TypedArray;
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
import com.example.regio.seudindin.ui.categories.support.CategoryIconAdapter;
import com.example.regio.seudindin.ui.categories.support.ColorSpinnerAdapter;
import com.example.regio.seudindin.viewmodel.CategoryViewModel;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

// Classe responsavel por controlar o Activity do detalhe de uma categoria
public class CategoryDetailActivity extends AppCompatActivity {

    // Declaracao e alimentacao das variaveis
    @BindView(R.id.categoriesDetail_toolbar) Toolbar toolbar;
    @BindView(R.id.categoriesDetail_btn_select_categories) Button parentButton;
    @BindView(R.id.categoriesDetail_edt_name) TextView nameTextView;
    @BindView(R.id.categoriesDetail_spn_select_colors) Spinner colorSpinner;
    @BindView(R.id.category_icons_recycleview) RecyclerView iconsRecycler;

    public static final int INSERT = 0;
    public static final int UPDATE = 1;
    private int operation = INSERT;

    private CategoryIconAdapter categoryIconAdapter;
    private ColorSpinnerAdapter colorSpinnerAdapter;
    private Integer[] colorsList = {
            R.color.red_200,
            R.color.pink_200,
            R.color.purple_200,
            R.color.blue_200,
            R.color.light_green_200,
            R.color.yellow_200,
            R.color.orange_200,
            R.color.brown_200,
            R.color.gray_200};

    private CategoryViewModel categoryViewModel;


    // Metodo responsavel pela criacao do activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_category_detail);
        ButterKnife.bind(this);

        // Recupera a lista de cores disponiveis
        //colorsList = getResources().getIntArray(R.array.array_color_spinner);

        // Definindo o tipo de operacao do activity
        setActivityOperation(getIntent().getIntExtra("operation",INSERT));

        // Configura o toolbar
        setupToolbar();

        // Configura o combobox de selecao de cores
        setupColorSelector();

        // Configura o componente reclyclerview para selecao do icone
        setupIconSelector();

        // Cria o observer para o campo de cor
        final Observer<Integer> colorObserver = new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer newColor) {
                int index = Arrays.asList(colorsList).indexOf(newColor);
                colorSpinner.setSelection(index);
            }
        };

        // Cria o observer para o campo de icone
        final Observer<Integer> iconObserver = new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer newIcon) {
                categoryIconAdapter.setSelectedPosition(newIcon);
            }
        };

        // Cria o observer para o campo de nome
        final Observer<String> nameObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String newName) {
                nameTextView.setText(newName);
            }
        };

        //Recupera o viewModel e atribui os observers
        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        categoryViewModel.getColor().observe(this,colorObserver);
        categoryViewModel.getIcon().observe(this,iconObserver);
        categoryViewModel.getName().observe(this,nameObserver);

        // Configura o botao de selecao da categoria-pai
        parentButton = this.findViewById(R.id.categoriesDetail_btn_select_categories);
        parentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //COMANDOS DE TESTE
                int iconValue = 3;//getResources().obtainTypedArray(R.array.array_category_icon_image).getResourceId(3,0);

                categoryViewModel.setName("TESTE");
                categoryViewModel.setColor(R.color.light_green_200);
                categoryViewModel.setIcon(iconValue);

            }
        });

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
    private void setActivityOperation(int operation) {
        this.operation = operation;

        switch (operation) {
            case INSERT:
                toolbar.setTitle(R.string.categories_detail_activity_title_insert);
                break;

            default:
                toolbar.setTitle(R.string.categories_detail_activity_title_update);
                break;

        }
    }


    // Configura o toolbar
    private void setupToolbar() {
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_cancel);
    }


    // Metodo responsavel por configurar a lista de cores disponiveis
    private void setupColorSelector() {
        colorSpinnerAdapter = new ColorSpinnerAdapter(this, R.layout.spinner_color_picker, Arrays.asList(colorsList));
        colorSpinner.setAdapter(colorSpinnerAdapter);
        colorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int index = adapterView.getSelectedItemPosition();
                categoryIconAdapter.setColor(colorsList[index]);

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
        categoryIconAdapter.setColor(colorsList[1]);

        iconsRecycler.setAdapter(categoryIconAdapter);
        iconsRecycler.setLayoutManager(layoutManager);
    }

}
