package br.com.arsolutions.seudindin.ui.categories;

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
import br.com.arsolutions.seudindin.viewmodel.categories.model.CategoryModel;
import br.com.arsolutions.seudindin.ui.categories.support.CategoryListAdapter;
import br.com.arsolutions.seudindin.viewmodel.categories.CategoryChildrenListLiveData;
import br.com.arsolutions.seudindin.viewmodel.categories.CategoryViewModel;

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

    private final String STATE_SHOW_ROOT = "state_show_root";

    private boolean showRoot = false;


    // Recupera uma nova instancia do fragmento
    public static CategoryListFragment newInstance(Bundle args ) {

        // Cria um novo fragmento e atribui parametros
        CategoryListFragment fragment = new CategoryListFragment();
        fragment.setArguments(args);

        // Retorna o fragmento criado
        return fragment;
    }


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

        // Configura informações do estado
        setupState(savedInstanceState);

        // Configura o componente reclyclerview para a listagens das categorias
        setupRecycler();

        // Configura os observers do view model
        setupObservers();

        // Retorna a view criada
        return view;
    }


    // Evento de armazenamento do estado da instancia quando a atividade vai ser interrompida
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(STATE_SHOW_ROOT, showRoot);
        super.onSaveInstanceState(outState);
    }


    // Metodo responsavel por avisar ao activity que um item foi selecionado
    public void onCategorySelect(CategoryModel category) {
        if (mListener != null && category.isEnabled()) {
            mListener.onCategoryListSelect(category);
        }
    }


    // Atribui o id da categoria que terá a listagem de filhos exibida
    public void setCategory_id(int id) {
        categoryListViewModel.setChildrenListIdInput(id);
    }


    // Define se deve ser exibido a categoria [Nenhuma]
    public void setShowRoot(boolean show) {
        this.showRoot = show;
    }


    //
    public List<CategoryModel> getParentIdList(int id) {
        return categoryListViewModel.getParentIdList(id);
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

        int parentId = this.getArguments().getInt("parent_id", 0);
        int selectedId = this.getArguments().getInt("selected_id", 0);

        //Recupera o viewModel e atribui os observers
        categoryListViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        categoryListViewModel.setChildrenListIdInput(parentId);

        CategoryChildrenListLiveData childrenListLiveData = categoryListViewModel.getChildrenListLiveData();
        childrenListLiveData.setShowRoot(showRoot);
        childrenListLiveData.setSelectId(selectedId);
        childrenListLiveData.setSelectParentId(parentId);
        childrenListLiveData.observe(this,categoryListObserver);
    }


    // Configura informações do estado
    private void setupState(Bundle savedInstanceState) {

        if (savedInstanceState == null) {

        } else {
            showRoot = savedInstanceState.getBoolean(STATE_SHOW_ROOT);
        }
    }


    // Interface para ser implementado na atividade pai
    public interface OnCategoryListSelectListener {
        void onCategoryListSelect(CategoryModel category);
    }


}
