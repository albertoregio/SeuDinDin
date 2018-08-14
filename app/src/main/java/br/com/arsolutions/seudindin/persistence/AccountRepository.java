package br.com.arsolutions.seudindin.persistence;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.regio.seudindin.R;

import java.util.List;
import java.util.concurrent.ExecutionException;

import br.com.arsolutions.seudindin.persistence.dao.AccountDAO;
import br.com.arsolutions.seudindin.persistence.entity.AccountEntity;

// Classe responsavel por recuperar as classes DAO
public class AccountRepository {

    // Declaracao de variaveis
    private AccountDAO accountDAO;


    // Construtor da classe
    public AccountRepository(Application application) {
        MyRoomDatabase db = MyRoomDatabase.getDatabase(application);
        accountDAO = db.accountDAO();
    }


    // Recupera a lista de contas
    public LiveData<AccountEntity> getAccountLiveData(Integer id) {
        return accountDAO.getAccountLiveData(id);
    }

    // Recupera a lista de contas
    public LiveData<List<AccountEntity>> getAccountListLiveData() {
        return accountDAO.getAccountListLiveData();
    }


    // Insere uma conta
    public int insert(AccountEntity accountEntity) {
        Integer value = 0;
        try {
            value = new AsyncTask<AccountEntity,Void,Integer>() {
                @Override
                protected Integer doInBackground(final AccountEntity... params) {
                    return Integer.valueOf((int) accountDAO.insert(params[0]));
                }
            }.execute(accountEntity).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return value.intValue();
    }


    // Remove uma categoria
    public void delete(int id) {
        AccountEntity model = new AccountEntity();
        model.setId(id);

        new AsyncTask<AccountEntity,Void,Void>() {
            @Override
            protected Void doInBackground(final AccountEntity... params) {
                accountDAO.delete(params[0]);
                return null;
            }
        }.execute(model);
    }

    // Remove uma categoria
    public void delete(AccountEntity accountEntity) {
        delete(accountEntity.getId());
    }


}
