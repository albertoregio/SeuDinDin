package br.com.arsolutions.seudindin.persistence.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import br.com.arsolutions.seudindin.persistence.dao.query.CategoryChildrenCountQuery;
import br.com.arsolutions.seudindin.persistence.entity.CategoryEntity;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

import java.util.List;


@Dao
public interface CategoryDAO {

    @Insert(onConflict = REPLACE)
    long insert(CategoryEntity category);

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
    LiveData<CategoryChildrenCountQuery> getCategoryLiveData(int id);


    @Query("select cat.parentId, parent.name as parentName, cat.id, cat.name, cat.icon, cat.color, count(children.id) as childrenCount " +
            "from category cat " +
            "  left join category children on children.parentId = cat.id " +
            "  left join category parent on parent.Id = cat.parentId " +
            "where cat.parentId = :id " +
            "group by cat.parentId, parent.name, cat.id, cat.name, cat.icon, cat.color " +
            "order by cat.name asc")
    LiveData<List<CategoryChildrenCountQuery>> getCategoryChildrenListLiveData(Integer id);

    @Query("select cat.parentId, parent.name as parentName, cat.id, cat.name, cat.icon, cat.color, count(children.id) as childrenCount " +
            "from category cat " +
            "  left join category children on children.parentId = cat.id " +
            "  left join category parent on parent.Id = cat.parentId " +
            "where cat.id = :id " +
            "group by cat.parentId, parent.name, cat.id, cat.name, cat.icon, cat.color " +
            "order by cat.name asc")
    CategoryChildrenCountQuery getCategoryChildrenCountQuery(int id);

    @Query("select parentId from category where id = :id")
    int getParentId(int id);

}
