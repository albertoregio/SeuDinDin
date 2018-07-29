package com.example.regio.seudindin.persistence.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import com.example.regio.seudindin.model.CategoryModel;
import com.example.regio.seudindin.persistence.dao.query_model.CategoryChildrenCountQuery;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

import java.util.List;


@Dao
public interface CategoryDAO {

    @Insert(onConflict = REPLACE)
    void insert(CategoryModel category);


    @Delete
    void delete(CategoryModel category);


    /*
    @Query("select * from category where parent_id = :id order by name asc")
    LiveData<List<CategoryModel>> getCategoryChildrenList(Integer id);
    */

    @Query("select c1.parent_id, c1.id, c1.name, c1.icon, c1.color, count(c2.id) as qtde " +
            "from category c1 " +
            "  left join category c2 on c2.parent_id = c1.id " +
            "where c1.parent_id = :id " +
            "group by c1.parent_id, c1.id, c1.name, c1.icon, c1.color " +
            "order by c1.name asc")
    LiveData<List<CategoryChildrenCountQuery>> getCategoryChildrenList(Integer id);


    @Query("select * from category where id = :id")
    LiveData<CategoryModel> getCategory(int id);


    @Query("select count(*) from category where parent_id = :id")
    int getChildrenCount(int id);


}
