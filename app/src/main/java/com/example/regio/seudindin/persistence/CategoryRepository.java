package com.example.regio.seudindin.persistence;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import com.example.regio.seudindin.R;
import com.example.regio.seudindin.persistence.dao.query.CategoryChildrenCountQuery;
import com.example.regio.seudindin.persistence.entity.CategoryEntity;
import com.example.regio.seudindin.persistence.dao.CategoryDAO;

import java.util.List;

// Classe responsavel por recuperar as classes DAO
public class CategoryRepository {

    // Declaracao de variaveis
    private CategoryDAO categoryDAO;


    // Construtor da classe
    public CategoryRepository(Application application) {
        MyRoomDatabase db = MyRoomDatabase.getDatabase(application);
        categoryDAO = db.categoryDAO();
    }


    // Recupera a lista de categorias filhas de uma determinada categoria
    public LiveData<List<CategoryChildrenCountQuery>> getCategoryChildrenList(Integer id) {
        return categoryDAO.getCategoryChildrenList(id);
    }


    // Recupera uma categoria especifica
    public LiveData<CategoryChildrenCountQuery>  getCategory(int id) {

        LiveData<CategoryChildrenCountQuery> liveData = categoryDAO.getCategory(id);

        if (liveData == null) {
            CategoryChildrenCountQuery categoryModel = new CategoryChildrenCountQuery();
            categoryModel.setId(0);
            categoryModel.setColor(R.color.red_200);
            categoryModel.setIcon(R.drawable.ic_category_icon_calculator);

            MutableLiveData<CategoryChildrenCountQuery> mutableLiveData = new MutableLiveData<CategoryChildrenCountQuery>();
            mutableLiveData.setValue(categoryModel);

            liveData = mutableLiveData;
        }

        return liveData;

    }


    // Insere uma categoria
    public void insert(CategoryEntity categoryEntity) {
        new AsyncTask<CategoryEntity,Void,Void>() {
            @Override
            protected Void doInBackground(final CategoryEntity... params) {
                categoryDAO.insert(params[0]);
                return null;
            }
        }.execute(categoryEntity);
    }


    // Remove uma categoria
    public void delete(int id) {
        CategoryEntity model = new CategoryEntity();
        model.setId(id);

        new AsyncTask<CategoryEntity,Void,Void>() {
            @Override
            protected Void doInBackground(final CategoryEntity... params) {
                categoryDAO.delete(params[0]);
                return null;
            }
        }.execute(model);
    }

    // Remove uma categoria
    public void delete(CategoryEntity categoryEntity) {
        delete(categoryEntity.getId());
    }

}
