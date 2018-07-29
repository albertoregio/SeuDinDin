package com.example.regio.seudindin.ui.categories;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.regio.seudindin.R;
import com.example.regio.seudindin.persistence.dao.query_model.CategoryChildrenCountQuery;
import com.example.regio.seudindin.ui.categories.support.CategoryListAdapter;
import com.example.regio.seudindin.viewmodel.CategoryViewModel;

import java.util.ArrayList;
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
        CategoryListFragment fragment = new CategoryListFragment();
        Bundle args = new Bundle();
        args.putInt("category_id", category_id);
        fragment.setArguments(args);
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

        return view;
    }


    // Metodo responsavel por avisar ao activity que um item foi selecionado
    public void onCategorySelect(CategoryChildrenCountQuery category) {
        if (mListener != null) {
            mListener.onCategoryListSelect(category);
        }
    }


    //
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


    // Metodo responsavel por configurar a listagem de categorias -
    private void setupRecycler() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        categoryAdapter = new CategoryListAdapter(context, new ArrayList<>(0));
        categoryAdapter.setEditListener(categoryOnClick);

        rec_listaCategories.setLayoutManager(layoutManager);
        rec_listaCategories.setAdapter(categoryAdapter);
        rec_listaCategories.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));

    }


    // Metodo responsavel por configurar os observers do view model
    private void setupObservers() {
        final Observer<List<CategoryChildrenCountQuery>> categoryListObserver = new Observer<List<CategoryChildrenCountQuery>>() {
            @Override
            public void onChanged(@Nullable List<CategoryChildrenCountQuery> categoryModelList) {
                categoryAdapter.setCategoryList(categoryModelList);
            }
        };

        //Recupera o viewModel e atribui os observers
        categoryListViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        categoryListViewModel.getChildrenListLiveData().observe(this,categoryListObserver);
    }


    // Interface para ser implementado na atividade pai
    public interface OnCategoryListSelectListener {
        void onCategoryListSelect(CategoryChildrenCountQuery category);
    }
}
