package br.com.arsolutions.seudindin.ui.accounts;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
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


import java.util.List;

import br.com.arsolutions.seudindin.ui.accounts.support.AccountListAdapter;
import br.com.arsolutions.seudindin.viewmodel.accounts.AccountViewModel;
import br.com.arsolutions.seudindin.viewmodel.accounts.model.AccountModel;

// Classe responsavel por implementar um fragmento que exibe a lista de contas cadastradas
public class AccountListFragment extends Fragment {

    // Declaracao de variaveis
    private OnAccountListSelectListener mListener;
    private View.OnClickListener itemOnClick;
    private AccountListAdapter accountAdapter;
    private AccountViewModel accountListViewModel;
    private Context context;
    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private boolean editableMode = true;
    private int itemPosition = 0;

    // Recupera uma nova instancia do fragmento
    public static AccountListFragment newInstance(Bundle args ) {

        // Cria um novo fragmento e atribui parametros
        AccountListFragment fragment = new AccountListFragment();
        fragment.setArguments(args);

        // Retorna o fragmento criado
        return fragment;
    }


    // Construtor da classe
    public AccountListFragment() {
        itemOnClick = view -> onAccountSelect(accountAdapter.getSelectedItem());
    }


    // Evento de criacao do fragmento
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    // Evento de criacao da view
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_list, container, false);

        recyclerView = view.findViewById(R.id.account_recycleview);
        fab = view.findViewById(R.id.fab_account_insert);
        fab.setOnClickListener(v -> {
            insertItem();
        });
        context = view.getContext();

        // Configura o componente reclyclerview para a listagens das categorias
        setupRecycler();

        // Configura os observers do view model
        setupObservers();

        // Retorna a view criada
        return view;
    }


    // Metodo responsavel por avisar ao activity que um item foi selecionado
    public void onAccountSelect(AccountModel model) {
        if (isEditableMode())
            editItem(model);
        else {
            if (mListener != null) {
                mListener.onAccountListSelect(model);
            }
        }
    }


    // Evento de vinculacao do fragmento com o atividade
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnAccountListSelectListener) {
            mListener = (OnAccountListSelectListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnAccountListSelectListener");
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
        accountAdapter = new AccountListAdapter(context);
        accountAdapter.setOnClickListener(itemOnClick);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(accountAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        ((LinearLayoutManager) recyclerView.getLayoutManager()).scrollToPositionWithOffset(itemPosition,0);
    }


    // Metodo responsavel por configurar os observers do view model
    private void setupObservers() {
        final Observer<List<AccountModel>> accountListObserver = accountModelList -> {
            accountAdapter.setAccountList(accountModelList);
            ((LinearLayoutManager) recyclerView.getLayoutManager()).scrollToPositionWithOffset(itemPosition,0);
        };

        //Recupera o viewModel e atribui os observers
        accountListViewModel = ViewModelProviders.of(this).get(AccountViewModel.class);

        LiveData<List<AccountModel>> listLiveData = accountListViewModel.getAccountListLiveData();
        listLiveData.observe(this, accountListObserver);
    }

    // Metodo responsavel pela inclusao de um novo item
    public void insertItem() {
        Intent intent = new Intent(context, AccountDetailActivity.class);
        intent.putExtra("operation", AccountDetailActivity.INSERT);
        startActivity(intent);
    }

    // Metodo responsavel pela seleção de um item
    public void editItem(AccountModel model) {
        itemPosition = accountAdapter.getSelectedIndex();
        Intent intent = new Intent(context, AccountDetailActivity.class);
        intent.putExtra("operation", AccountDetailActivity.UPDATE);
        intent.putExtra("account", model);
        startActivity(intent);
        ((LinearLayoutManager) recyclerView.getLayoutManager()).scrollToPositionWithOffset(itemPosition,0);
    }

    // Verifica se está em modo de edição
    public boolean isEditableMode() {
        return editableMode;
    }


    // Atribui o modo de edição
    public void setEditableMode(boolean editableMode) {
        this.editableMode = editableMode;
    }


    // Interface para ser implementado na atividade pai
    public interface OnAccountListSelectListener {
        void onAccountListSelect(AccountModel model);
    }


}
