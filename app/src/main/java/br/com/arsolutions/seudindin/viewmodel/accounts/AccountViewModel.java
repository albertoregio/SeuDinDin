package br.com.arsolutions.seudindin.viewmodel.accounts;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import br.com.arsolutions.seudindin.persistence.AccountRepository;
import br.com.arsolutions.seudindin.persistence.entity.AccountEntity;
import br.com.arsolutions.seudindin.viewmodel.accounts.model.AccountModel;
import br.com.arsolutions.seudindin.viewmodel.accounts.model.converter.ModelConverter;

// Classe responsavel pela comunicacao entre a ui e os dados do negocio
public class AccountViewModel extends AndroidViewModel {

    // Declaracao de variaveis
    private AccountRepository repository;
    private LiveData<List<AccountModel>> list;

    // Construtor da classe
    public AccountViewModel(Application application) {
        super(application);
        repository = new AccountRepository(application);
    }

    // Insere/Atualiza uma nova categoria
    public int save(AccountModel accountModel) {
        return repository.insert(ModelConverter.modelToEntity(accountModel));
    }


    // Remove uma categoria pelo id
    public void delete(int id) {
        repository.delete(id);
    }



    // Remove uma determinada categoria
    public void delete(AccountModel account) {
        repository.delete(ModelConverter.modelToEntity(account));
    }


    public LiveData<List<AccountModel>> getAccountListLiveData() {

        /*
        AccountModel model = new AccountModel();
        model.setId(1);
        model.setName("Cartão de Crédito");
        model.setColorName("lime_200");
        model.setEnabled(true);
        model.setInitials("Cr");
        model.setOpeningBalance(new BigDecimal(123));

        List<AccountModel> accountList = new ArrayList<>(0);
        accountList.add(model);
        mutableLiveData.setValue(accountList);

        return mutableLiveData;
        */

        //if (list == null) {
            LiveData<List<AccountEntity>> entityLiveData = repository.getAccountListLiveData();
            list = Transformations.map(entityLiveData, accounts -> {
                List<AccountModel> x = ModelConverter.entityListToModelList(accounts);
                return x;
            });

        //}

        return list;
    }
}
