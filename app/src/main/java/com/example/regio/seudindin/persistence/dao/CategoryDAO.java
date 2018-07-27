package com.example.regio.seudindin.persistence.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import com.example.regio.seudindin.model.CategoryModel;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

import java.util.List;


@Dao
public interface CategoryDAO {

    @Insert(onConflict = REPLACE)
    void insert(CategoryModel category);

    @Query("select * from category order by name asc")
    LiveData<List<CategoryModel>> getCategories();

    @Query("select * from category where id = :id")
    LiveData<CategoryModel> getCategory(int id);
}
