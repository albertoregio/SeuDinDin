package com.example.regio.seudindin.ui.categories;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.regio.seudindin.R;
import com.example.regio.seudindin.model.CategoryModel;
import com.example.regio.seudindin.ui.categories.support.CategoryListAdapter;
import com.example.regio.seudindin.viewmodel.CategoryChildrenListLiveData;
import com.example.regio.seudindin.viewmodel.CategoryViewModel;

import java.util.List;

import butterknife.BindView;

// Classe responsavel por implementar um fragmento que exibe a lista de categorias cadastradas
public class CategoryListFragment extends Fragment {

    // Declaracao de variaveis
    private OnCategoryListSelectListener mListener;
    private CategoryListAdapter categoryAdapter;
    private View.OnClickListener categoryOnClick;
    private CategoryViewModel categoryListViewModel;
    private Context context;
    @BindView(R.id.categories_recycleview) RecyclerView rec_listaCategories;

    private boolean showRoot = false;


    // Construtor da classe
    public CategoryListFragment() {
        categoryOnClick = view -> onCategorySelect(categoryAdapter.getSelectedItem());
        /*
        categoryOnClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCategorySelect(categoryAdapter.getSelectedItem());
            }
        };*/
    }


    // Recupera uma nova instancia do fragmento
    public static CategoryListFragment newInstance(int category_id) {

        // Atribui parametros
        Bundle args = new Bundle();
        args.putInt("category_id", category_id);

        // Cria um novo fragmento e atribui parametros
        CategoryListFragment fragment = new CategoryListFragment();
        fragment.setArguments(args);

        // Retorna o fragmento criado
        return fragment;
    }


    // Evento de criacao do fragmento
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    // Evento de criacao da view
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_list, container, false);
        //ButterKnife.bind(view);
        rec_listaCategories = view.findViewById(R.id.categories_recycleview);
        context = view.getContext();

        // Configura o componente reclyclerview para a listagens das categorias
        setupRecycler();

        // Configura os observers do view model
        setupObservers();

        // Retorna a view criada
        return view;
    }


    // Metodo responsavel por avisar ao activity que um item foi selecionado
    public void onCategorySelect(CategoryModel category) {
        if (mListener != null) {
            mListener.onCategoryListSelect(category);
        }
    }


    // Atribui o id da categoria que terá a listagem de filhos exibida
    public void setCategory_id(int id) {
        categoryListViewModel.setChildrenListIdInput(id);
    }


    // Evento de vinculacao do fragmento com o atividade
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnCategoryListSelectListener) {
            mListener = (OnCategoryListSelectListener) context;
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


    // Define se deve ser exibido a categoria [Nenhuma]
    public void setShowRoot(boolean show) {
        this.showRoot = show;
    }


    // Metodo responsavel por configurar a listagem de categorias -
    private void setupRecycler() {
        categoryAdapter = new CategoryListAdapter(context);
        categoryAdapter.setOnClickListener(categoryOnClick);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        rec_listaCategories.setLayoutManager(layoutManager);
        rec_listaCategories.setAdapter(categoryAdapter);
        rec_listaCategories.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));

    }


    // Metodo responsavel por configurar os observers do view model
    private void setupObservers() {
        final Observer<List<CategoryModel>> categoryListObserver = categoryModelList -> categoryAdapter.setCategoryList(categoryModelList);

        //Recupera o viewModel e atribui os observers
        categoryListViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        CategoryChildrenListLiveData childrenListLiveData = categoryListViewModel.getChildrenListLiveData();
        childrenListLiveData.setShowRoot(showRoot);
        childrenListLiveData.observe(this,categoryListObserver);
    }


    // Interface para ser implementado na atividade pai
    public interface OnCategoryListSelectListener {
        void onCategoryListSelect(CategoryModel category);
    }
}
