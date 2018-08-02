package com.example.regio.seudindin.persistence.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.regio.seudindin.persistence.dao.query.CategoryChildrenCountQuery;
import com.example.regio.seudindin.persistence.entity.CategoryEntity;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

import java.util.List;


@Dao
public interface CategoryDAO {

    @Insert(onConflict = REPLACE)
    void insert(CategoryEntity category);

    @Insert(onConflict = REPLACE)
    void insertAll(CategoryEntity... category);

    @Delete
    void delete(CategoryEntity category);

    @Query("select cat.parentId, parent.name as parentName, cat.id, cat.name, cat.icon, cat.color, count(children.id) as childrenCount " +
            "from category cat " +
            "  left join category children on children.parentId = cat.id " +
            "  left join category parent on parent.Id = cat.parentId " +
            "where cat.id = :id " +
            "group by cat.parentId, parent.name, cat.id, cat.name, cat.icon, cat.color " +
            "order by cat.name asc")
    LiveData<CategoryChildrenCountQuery> getCategory(int id);


    @Query("select cat.parentId, parent.name as parentName, cat.id, cat.name, cat.icon, cat.color, count(children.id) as childrenCount " +
            "from category cat " +
            "  left join category children on children.parentId = cat.id " +
            "  left join category parent on parent.Id = cat.parentId " +
            "where cat.parentId = :id " +
            "group by cat.parentId, parent.name, cat.id, cat.name, cat.icon, cat.color " +
            "order by cat.name asc")
    LiveData<List<CategoryChildrenCountQuery>> getCategoryChildrenList(Integer id);


}
