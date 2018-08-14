package br.com.arsolutions.seudindin.persistence.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import br.com.arsolutions.seudindin.persistence.entity.AccountEntity;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;


@Dao
public interface AccountDAO {

    @Insert(onConflict = REPLACE)
    long insert(AccountEntity account);

    @Insert(onConflict = REPLACE)
    void insertAll(AccountEntity... account);

    @Delete
    void delete(AccountEntity account);

    @Query("select id, name, initials, color, enabled, openingBalance " +
            "from account " +
            "where id = :id " +
            "order by name asc")
    LiveData<AccountEntity> getAccountLiveData(int id);

    @Query("select id, name, initials, color, enabled, openingBalance " +
            "from account " +
            "order by name asc")
    LiveData<List<AccountEntity>> getAccountListLiveData();

}
