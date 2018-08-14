package br.com.arsolutions.seudindin.ui.accounts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.regio.seudindin.R;

import br.com.arsolutions.seudindin.viewmodel.accounts.model.AccountModel;
import br.com.arsolutions.seudindin.viewmodel.categories.model.CategoryModel;

public class AccountListActivity extends AppCompatActivity implements AccountListFragment.OnAccountListSelectListener {

    private AccountListFragment accountFragment;
    private final String STATE_FRAGMENT = "state_fragment";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_account_list);

        // Configura informações do estado
        setupState(savedInstanceState);

    }


    // Configura informações do estado
    private void setupState(Bundle savedInstanceState) {

        if (savedInstanceState == null) {

            // Configuracao do fragmento que controla a lista de contas
            Bundle args = new Bundle();
            accountFragment = AccountListFragment.newInstance(args);

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.account_list_fragment_content, accountFragment)
                    .commit();

        } else {
            accountFragment = (AccountListFragment) getSupportFragmentManager().getFragment(savedInstanceState, STATE_FRAGMENT);
        }
    }


    // Configura o evento de clique em um item da lista
    @Override
    public void onAccountListSelect(AccountModel model) {
        Log.d("REGIO:", model.getName());
    }


}
