package br.com.arsolutions.seudindin.viewmodel.accounts.model.converter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import br.com.arsolutions.seudindin.persistence.entity.AccountEntity;
import br.com.arsolutions.seudindin.viewmodel.accounts.model.AccountModel;

public class ModelConverter {

    public static AccountModel entityToModel(AccountEntity accountEntity) {
        AccountModel model = new AccountModel();
        model.setId(accountEntity.getId());
        model.setName(accountEntity.getName());
        model.setInitials(accountEntity.getInitials());
        model.setColorName(accountEntity.getColor());
        model.setEnabled(accountEntity.isEnabled());
        model.setOpeningBalance(BigDecimal.valueOf(accountEntity.getOpeningBalance()));
        return model;
    }

    public static List<AccountModel> entityListToModelList(List<AccountEntity> account) {
        List<AccountModel> list = new ArrayList<>();
        ListIterator<AccountEntity> itr = account.listIterator();
        while (itr.hasNext()) {
            list.add(entityToModel(itr.next()));
        }
        return list;
    }

    public static AccountEntity modelToEntity(AccountModel account) {
        AccountEntity entity = new AccountEntity();
        entity.setId(account.getId());
        entity.setName(account.getName());
        entity.setInitials(account.getInitials());
        entity.setColor(account.getColorName());
        entity.setEnabled(account.isEnabled());
        entity.setOpeningBalance(account.getOpeningBalance().longValue());
        return entity;
    }
}
