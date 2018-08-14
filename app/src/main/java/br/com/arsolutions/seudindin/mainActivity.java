package br.com.arsolutions.seudindin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.regio.seudindin.R;

import br.com.arsolutions.seudindin.ui.accounts.AccountListActivity;
import br.com.arsolutions.seudindin.ui.categories.CategoryListInsertActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

// Classe responsavel por controlar o Activity principal da aplicacao
public class mainActivity extends AppCompatActivity {

    // Metodo responsavel pela criacao do activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }


    // Metodo responsavel pelo clique do botao para exibicao das categorias
    @OnClick(R.id.main_btn_show_categories)
    public void showCategories(View view) {
        Intent intent = new Intent(this, CategoryListInsertActivity.class);
        startActivity(intent);
    }

    // Metodo responsavel pelo clique do botao para exibicao das categorias
    @OnClick(R.id.main_btn_show_accounts)
    public void showAccounts(View view) {
        Intent intent = new Intent(this, AccountListActivity.class);
        startActivity(intent);
    }

}
