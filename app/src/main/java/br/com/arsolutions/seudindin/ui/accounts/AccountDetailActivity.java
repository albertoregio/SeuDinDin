package br.com.arsolutions.seudindin.ui.accounts;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.regio.seudindin.R;

import java.util.ArrayList;

import br.com.arsolutions.seudindin.viewmodel.accounts.AccountViewModel;
import br.com.arsolutions.seudindin.viewmodel.accounts.model.AccountModel;
import br.com.arsolutions.seudindin.viewmodel.categories.model.CategoryModel;

public class AccountDetailActivity extends AppCompatActivity implements AccountDetailFragment.OnAccountDetailListener {

    private Toolbar toolbar;
    private AccountDetailFragment accountFragment;
    private AccountViewModel accountViewModel;
    private final String STATE_FRAGMENT = "state_fragment";

    public static final int INSERT = 0;
    public static final int UPDATE = 1;
    private int operation = INSERT;

    private AccountModel model = new AccountModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_account_detail);
        toolbar = findViewById(R.id.account_detail_toolbar);

        // Configura o toolbar
        setupToolbar();

        // Configura o view model
        setupViewModel();

        // Definindo o tipo de operacao do activity
        setActivityOperation();

        // Configura informações do estado
        setupState(savedInstanceState);

    }


    // Evento de armazenamento do estado da instancia quando a atividade vai ser interrompida
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getSupportFragmentManager().putFragment(outState, STATE_FRAGMENT, accountFragment);
        super.onSaveInstanceState(outState);
    }


    // Evento de restauracao da atividade
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        accountFragment = (AccountDetailFragment) getSupportFragmentManager().getFragment(savedInstanceState, STATE_FRAGMENT);
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

        AccountModel model = accountFragment.getModel();

        if (!(model.getName().equals("") || model.getInitials().equals("")) ) {
            int newId = accountViewModel.save(model);

            Intent resultIntent = new Intent();
            resultIntent.putExtra("account_id", newId);
            setResult(Activity.RESULT_OK, resultIntent);

            Toast.makeText(getApplicationContext(), R.string.command_save_successful, Toast.LENGTH_LONG).show();
            finish();
        } else {
            if (model.getName().equals(""))
                accountFragment.nameInputLayout.setError(String.format(getString(R.string.command_save_unsuccessful), getString(R.string.account_name_label)));

            if (model.getInitials().equals(""))
                accountFragment.initialsInputLayout.setError(String.format(getString(R.string.command_save_unsuccessful), getString(R.string.account_icon_initials_label)));
        }

    }


    // Comando para excluir uma categoria
    private void actionDelete() {

        AlertDialog.Builder alert = new AlertDialog.Builder(AccountDetailActivity.this);
        alert
                .setTitle(R.string.command_delete_title)
                .setMessage(R.string.command_delete_dialog)
                .setPositiveButton(R.string.command_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AccountModel model = accountFragment.getModel();
                        accountViewModel.delete(model);

                        Intent resultIntent = new Intent();
                        setResult(Activity.RESULT_OK, resultIntent);

                        Toast.makeText(getApplicationContext(), R.string.command_delete_successful, Toast.LENGTH_LONG).show();
                        finish();
                    }
                })
                .setNegativeButton(R.string.command_no, null)
                .show();

    }


    // Comando para voltar a atividade anterior
    private void actionBack() {
        AlertDialog.Builder alert = new AlertDialog.Builder(AccountDetailActivity.this);
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


    // Atribui o tipo de operacao do activity: insercao ou atualizacao
    private void setActivityOperation() {
        this.operation = getIntent().getIntExtra("operation",INSERT);

        switch (operation) {
            case INSERT:
                toolbar.setTitle(R.string.account_detail_title_insert);
                break;

            default:
                toolbar.setTitle(R.string.account_detail_title_edit);
                model = getIntent().getParcelableExtra("account");
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

        if (savedInstanceState == null) {

            // Configuracao do fragmento que controla a lista de contas
            Bundle args = new Bundle();
            args.putParcelable("account", model);
            accountFragment = AccountDetailFragment.newInstance(args);

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.account_detail_fragment_content, accountFragment)
                    .commit();

        } else {
            accountFragment = (AccountDetailFragment) getSupportFragmentManager().getFragment(savedInstanceState, STATE_FRAGMENT);
        }
    }


    // Metodo responsavel por configurar a classe view model
    private void setupViewModel() {
        //Recupera o viewModel e atribui os observers
        accountViewModel = ViewModelProviders.of(this).get(AccountViewModel.class);
    }


    @Override
    public void onAccountDetail(AccountModel model) {

    }
}
