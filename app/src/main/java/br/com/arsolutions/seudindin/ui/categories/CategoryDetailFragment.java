package br.com.arsolutions.seudindin.ui.categories;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.regio.seudindin.R;

import java.util.ArrayList;

import br.com.arsolutions.components.ColorSpinner;
import br.com.arsolutions.seudindin.App;
import br.com.arsolutions.seudindin.ui.categories.support.CategoryIconAdapter;
import br.com.arsolutions.seudindin.viewmodel.categories.CategoryViewModel;
import br.com.arsolutions.seudindin.viewmodel.categories.model.CategoryModel;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


// Classe responsavel por controlar o fragmento do detalhe de uma categoria
public class CategoryDetailFragment extends Fragment {

    // Declaracao e alimentacao das variaveis
    @BindView(R.id.category_detail_select_parent) Button parentButton;
    @BindView(R.id.categoriesDetail_edt_name) TextView nameTextView;
    @BindView(R.id.categoriesDetail_spn_select_colors) ColorSpinner colorSpinner;
    @BindView(R.id.category_icons_recycleview) RecyclerView iconsRecycler;
    @BindView(R.id.categoriesDetail_colors_label) TextView iconColorTextView;
    @BindView(R.id.categoriesDetail_recycleview_label) TextView iconSelectTextView;
    @BindView(R.id.categoriesDetail_spn_line_separator) View lineSeparatorView;
    @BindView(R.id.categoriesDetail_inputLayout_name) TextInputLayout nameInputLayout;

    private Context context;
    private CategoryModel model;
    private CategoryDetailFragment.OnCategoryDetailListener mListener;
    private CategoryIconAdapter categoryIconAdapter;
    private CategoryViewModel categoryViewModel;

    private final String STATE_MODEL = "state_model";

    public static final String EXTRA_OPERATION = "extra_operation";
    public static final String EXTRA_MODEL = "extra_model";

    public static final int INSERT = 0;
    public static final int UPDATE = 1;
    private int operation = INSERT;


    // Recupera uma nova instancia do fragmento
    public static CategoryDetailFragment newInstance(Bundle args ) {

        // Cria um novo fragmento e atribui parametros
        CategoryDetailFragment fragment = new CategoryDetailFragment();
        fragment.setArguments(args);

        // Retorna o fragmento criado
        return fragment;
    }

    // Construtor da classe
    public CategoryDetailFragment() {
        // Required empty public constructor
    }

    // Evento de criacao do fragmento
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // Evento de criacao da view
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_category_detail, container, false);
        ButterKnife.bind(this, view);
        context = this.getContext();

        // Configura os parametros passados
        setupParameters();

        // Configura os componentes da tela
        setupUi();

        // Configura o view model
        setupViewModel();

        // Retorna a view criada
        return view;
    }


    // Atualiza as informacoes do modelo
    private void updateModel() {
        model.setName(nameTextView.getText().toString());
        model.setColor(colorSpinner.getColor(colorSpinner.getSelectedItemPosition()));
        model.setIconName(App.getResourceName(categoryIconAdapter.getSelectedIcon()));
    }

    // Retorna o modelo da conta
    public CategoryModel getModel() {
        updateModel();
        return model;
    }

    // Evento de armazenamento do estado da instancia quando a atividade vai ser interrompida
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(STATE_MODEL, getModel());
    }


    // Evento de restauracao da atividade
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            model = savedInstanceState.getParcelable(STATE_MODEL);
        }
    }


    // Configura o menu da toolbar
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_categories_detail_toolbar, menu);
        MenuItem itemDelete = menu.findItem(R.id.menuitem_categories_detail_delete);
        itemDelete.setVisible(operation != INSERT);
    }


    // Configura ações dos itens do menu da toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Atualiza o modelo
        updateModel();

        // Verifica a acao realizada
        switch (item.getItemId()) {

            case R.id.menuitem_categories_detail_save:
                actionSave();
                return true;

            case R.id.menuitem_categories_detail_delete:
                actionDelete();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Comando para salvar as informacoes da categoria
    @SuppressLint("StringFormatInvalid")
    private void actionSave() {
        if (!model.getName().equals("")) {
            int newId = categoryViewModel.save(model);
            model.setId(newId);
            mListener.onSaveCategory(model);
        } else {
            nameInputLayout.setError(String.format(getString(R.string.command_save_unsuccessful), getString(R.string.category_name_label)));
        }
    }


    // Comando para excluir uma categoria
    private void actionDelete() {
        if (model.getChildrenCount() == 0) {

            AlertDialog.Builder alert = new AlertDialog.Builder(context);
            alert
                    .setTitle(R.string.command_delete_title)
                    .setMessage(R.string.category_command_delete_dialog)
                    .setPositiveButton(R.string.command_yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            categoryViewModel.delete(model);
                            mListener.onDeleteCategory(model);
                        }
                    })
                    .setNegativeButton(R.string.command_no, null)
                    .show();
        } else {
            AlertDialog.Builder unsuccessful = new AlertDialog.Builder(context);
            unsuccessful
                    .setTitle(R.string.command_delete_title)
                    .setMessage(R.string.category_command_delete_unsuccessful)
                    .setPositiveButton(R.string.command_ok,null)
                    .show();
        }

    }

    // Comando para selecionar uma categoria-pai
    @OnClick(R.id.category_detail_select_parent)
    public void actionSelectCategoryParent() {
        /*
        CategoryModel parent = new CategoryModel();
        parent.setId(model.getParentId());
        parent.setName(model.getParentName());
        mListener.onSelectParentCategory(parent);
        */
        mListener.onSelectParentCategory(model);
    }


    // Evento de vinculacao do fragmento com o atividade
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnCategoryDetailListener) {
            mListener = (OnCategoryDetailListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnCategoryListSelectListener");
        }
    }


    // Evento de desvinculacao do fragmento a atividade
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    // Atribui uma categoria pai
    public void setParentCategory(CategoryModel parent) {
        model.setParentId(parent.getId());
        model.setParentName(parent.getName());
        parentButton.setText(parent.getName());
        setupVisibility(parent.getId() == 0);
    }


    // Configura os parametros passados
    private void setupParameters() {
        // Atribui o tipo de operacao
        this.operation = this.getArguments().getInt(EXTRA_OPERATION,INSERT);

        // Recupera o modelo atraves dos argumentos passaods
        model = this.getArguments().getParcelable(EXTRA_MODEL);
        if (model == null)
            model = new CategoryModel();

    }

    // Metodo responsavel por configurar os componentes da tela
    private void setupUi() {

        // Habilita a exibicao do menu no toolbar
        setHasOptionsMenu(true);

        // Configura o combobox de selecao de cores
        setupColorSelector();

        // Configura o componente reclyclerview para selecao do icone
        setupIconSelector();

        // Configura os outros componentes de tela
        parentButton.setText(model.getParentName());
        nameTextView.setText(model.getName());
        colorSpinner.setSelection(colorSpinner.getIndex(model.getColor()));
        categoryIconAdapter.setSelectedIcon(model.getIcon());
        setupVisibility(model.isShowIcon());

    }

    // Metodo responsavel por configurar a lista de cores disponiveis
    private void setupColorSelector() {
        colorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int color = colorSpinner.getColor(adapterView.getSelectedItemPosition());
                categoryIconAdapter.setColor(color);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    // Metodo responsavel por configurar a lista de icones de categorias -
    private void setupIconSelector() {
        GridLayoutManager layoutManager = new GridLayoutManager(context,3,GridLayoutManager.HORIZONTAL,false);

        categoryIconAdapter = new CategoryIconAdapter(context, new ArrayList<Integer>(0));
        categoryIconAdapter.setSelectedIcon(R.drawable.ic_category_icon_empty);

        iconsRecycler.setAdapter(categoryIconAdapter);
        iconsRecycler.setLayoutManager(layoutManager);
    }


    // Metodo responsavel por configurar a classe view model
    private void setupViewModel() {
        //Recupera o viewModel e atribui os observers
        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
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


    // Interface para ser implementado na atividade pai
    public interface OnCategoryDetailListener {
        void onSelectParentCategory(CategoryModel parent);
        void onSaveCategory(CategoryModel model);
        void onDeleteCategory(CategoryModel model);
    }

}
