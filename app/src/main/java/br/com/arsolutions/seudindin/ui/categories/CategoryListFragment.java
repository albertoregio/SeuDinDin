package br.com.arsolutions.seudindin.ui.categories;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

// Classe responsavel por implementar um fragmento que exibe a lista de categorias cadastradas
public class CategoryListFragment extends Fragment {

    // Declaracao de variaveis
    @BindView(R.id.categories_recycleview) RecyclerView recycler;
    @BindView(R.id.fab_category_insert) FloatingActionButton insertFab;

    private Context context;
    private OnCategoryListSelectListener mListener;
    private CategoryListAdapter categoryAdapter;
    private CategoryViewModel categoryListViewModel;

    private final String STATE_PARENT = "state_parent";
    private final String STATE_SELECTED = "state_selected";
    private final String STATE_SHOW_INSERT = "state_show_insert_fab";
    private final String STATE_SHOW_ROOT = "state_show_root";
    private final String STATE_CURRENT_ITEM = "state_current_item";
    private final String STATE_NAVIGATION_ARRAY = "state_navigation_array";

    public static final String EXTRA_PARENT = "extra_parent";
    public static final String EXTRA_SELECTED = "extra_selected";
    public static final String EXTRA_SHOW_INSERT = "extra_show_insert";
    public static final String EXTRA_SHOW_ROOT = "extra_show_root";

    private CategoryModel parent = new CategoryModel();
    private CategoryModel selected = new CategoryModel();
    private List<CategoryModel> navigationArray = new ArrayList<>();

    private boolean show_insert = true;
    private boolean show_root = false;
    private int lastParentId = 0;


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
        // Required empty public constructor
        navigationArray.add(CategoryModel.getRoot());
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
        ButterKnife.bind(this, view);
        context = view.getContext();

        // Configura os parametros passados
        setupParameters();

        // Configura informações do estado
        setupState(savedInstanceState);

        // Configura os componentes da tela
        setupUi();

        // Configura os observers do view parent
        setupViewModel();

        // Retorna a view criada
        return view;
    }


    // Evento de armazenamento do estado da instancia quando a atividade vai ser interrompida
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(STATE_PARENT, parent);
        outState.putParcelable(STATE_SELECTED, selected);
        outState.putBoolean(STATE_SHOW_INSERT, show_insert);
        outState.putBoolean(STATE_SHOW_ROOT, show_root);
        outState.putInt(STATE_CURRENT_ITEM, lastParentId);
        outState.putParcelableArrayList(STATE_NAVIGATION_ARRAY, (ArrayList<CategoryModel>) navigationArray);
        super.onSaveInstanceState(outState);
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


    // Configura os parametros passados
    private void setupParameters() {
        show_insert = this.getArguments().getBoolean(EXTRA_SHOW_INSERT, true);
        show_root = this.getArguments().getBoolean(EXTRA_SHOW_ROOT, false);

        CategoryModel pParent = this.getArguments().getParcelable(EXTRA_PARENT);
        if (pParent != null)
            parent = pParent;

        CategoryModel pSelected = this.getArguments().getParcelable(EXTRA_SELECTED);
        if (pSelected != null)
            selected = pSelected;
    }


    // Configura informações do estado
    private void setupState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            parent = savedInstanceState.getParcelable(STATE_PARENT);
            selected = savedInstanceState.getParcelable(STATE_SELECTED);
            show_insert =  savedInstanceState.getBoolean(STATE_SHOW_INSERT);
            show_root = savedInstanceState.getBoolean(STATE_SHOW_ROOT);
            lastParentId = savedInstanceState.getInt(STATE_CURRENT_ITEM);
            navigationArray = savedInstanceState.getParcelableArrayList(STATE_NAVIGATION_ARRAY);
        }
    }


    // Metodo responsavel por configurar os componentes da tela
    private void setupUi() {

        // Configura o componente reclyclerview para a listagens das categorias
        setupRecycler();

        // Configura a visibilidade do botão de inserção
        if (is_show_insert())
            insertFab.setVisibility(View.VISIBLE);
        else
            insertFab.setVisibility(View.INVISIBLE);

    }


    // Metodo responsavel por configurar a listagem de categorias -
    private void setupRecycler() {
        categoryAdapter = new CategoryListAdapter(context);

        // Metodo responsavel por avisar ao activity que um item foi selecionado
        categoryAdapter.setOnClickListener(v -> {
            selectCategory();
        });

        // Configura o recycler
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recycler.setLayoutManager(layoutManager);
        recycler.setAdapter(categoryAdapter);
        recycler.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));

        int position = categoryAdapter.getIndexById(lastParentId);
        ((LinearLayoutManager) recycler.getLayoutManager()).scrollToPositionWithOffset(position, 0);
    }


    // Metodo responsavel por configurar os observers do view parent
    private void setupViewModel() {
        final Observer<List<CategoryModel>> categoryListObserver = categoryModelList -> {
            categoryAdapter.setCategoryList(categoryModelList);
            int position = categoryAdapter.getIndexById(lastParentId);
            ((LinearLayoutManager) recycler.getLayoutManager()).scrollToPositionWithOffset(position,0);
        };

        //Recupera o viewModel e atribui os observers
        categoryListViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        categoryListViewModel.setChildrenListIdInput(parent.getId());

        CategoryChildrenListLiveData childrenListLiveData = categoryListViewModel.getChildrenListLiveData();
        childrenListLiveData.setShowRoot(show_root);
        if (show_root) {
            childrenListLiveData.setSelectId(selected.getId());
            childrenListLiveData.setSelectParentId(selected.getParentId());
        }
        childrenListLiveData.observe(this,categoryListObserver);

        // Atualiza os arrays de navegação
        updateNavigationArrays();

    }


    // Metodo responsavel pelo clique do botao da inclusao de uma nova categoria
    @OnClick(R.id.fab_category_insert)
    public void insertCategory() {
        CategoryModel category = new CategoryModel();
        category.setParentId(parent.getId());
        category.setParentName(parent.getName());
        mListener.onInsertCategory(category);
    }

    private void selectCategory() {
        CategoryModel selected = categoryAdapter.getSelectedItem();
        lastParentId = selected.getId();

        // Se a categoria possuir filhos exibe a listagem com estes, caso nao possuia seleciona o item
        if (selected.getChildrenCount() > 0) {

            parent = selected;

            // Adiciona um item da categoria na lista de hierarquia
            navigationArray.add(parent);

            // Mostra a listagem das categorias filhas da categoria selecionada
            setCategory(parent);
        }
        // Categoria selecionada
        else {
            if (mListener != null && selected.isEnabled()) {
                mListener.onSelectCategory(selected);
            }
        }
    }

    public boolean backCategory() {

        // Remove um item da categoria
        if(navigationArray.size() > 0) {
            lastParentId = navigationArray.get(navigationArray.size() -1).getId();
            navigationArray.remove(navigationArray.size() - 1);
        }

        // Se a listagem ja esta mostrando as categorias raiz, entao finaliza a atividade
        if (navigationArray.size() == 0) {
            return false;
        }
        // Mostra a listagem das categorias de nivel anterior
        else {
            setCategory(navigationArray.get(navigationArray.size() -1));
            return true;
        }

    }


    // Atualiza os arrays de navegação
    public void updateNavigationArrays() {
        // Atribui o array de navegacao
        navigationArray = categoryListViewModel.getParentIdList(parent.getId());
    }


    // Atribui o id da categoria que terá a listagem de filhos exibida
    public void setCategory(CategoryModel category) {
        parent = category;
        categoryListViewModel.setChildrenListIdInput(category.getId());
    }


    // Define se deve ser exibido a categoria [Nenhuma]
    public void set_show_root(boolean show) {
        this.show_root = show;
    }


    // Verifica se é para ser mostrado o botão de inserção
    public boolean is_show_insert() {
        return show_insert;
    }


    // Interface para ser implementado na atividade pai
    public interface OnCategoryListSelectListener {
        void onInsertCategory(CategoryModel category);
        void onSelectCategory(CategoryModel category);
    }


}