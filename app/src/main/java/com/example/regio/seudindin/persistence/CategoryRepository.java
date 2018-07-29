package com.example.regio.seudindin.persistence;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import com.example.regio.seudindin.R;
import com.example.regio.seudindin.model.CategoryModel;
import com.example.regio.seudindin.persistence.dao.CategoryDAO;
import com.example.regio.seudindin.persistence.dao.query_model.CategoryChildrenCountQuery;

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
    public LiveData<CategoryModel>  getCategory(int id) {

        LiveData<CategoryModel> liveData = categoryDAO.getCategory(id);

        if (liveData == null) {
            CategoryModel categoryModel = new CategoryModel();
            categoryModel.setId(0);
            categoryModel.setColor(R.color.red_200);
            categoryModel.setIcon(R.drawable.ic_category_icon_calculator);

            MutableLiveData<CategoryModel> mutableLiveData = new MutableLiveData<CategoryModel>();
            mutableLiveData.setValue(categoryModel);

            liveData = mutableLiveData;
        }

        return liveData;

    }


    // Recupera a quantidade de filhos de uma determinada categoria
    public int getChildrenCount(int id) {
        return categoryDAO.getChildrenCount(id);
    }


    // Insere uma categoria
    public void insert(CategoryModel categoryModel) {
        new AsyncTask<CategoryModel,Void,Void>() {
            @Override
            protected Void doInBackground(final CategoryModel... params) {
                categoryDAO.insert(params[0]);
                return null;
            }
        }.execute(categoryModel);
    }


    // Remove uma categoria
    public void delete(int id) {
        CategoryModel model = new CategoryModel();
        model.setId(id);

        new AsyncTask<CategoryModel,Void,Void>() {
            @Override
            protected Void doInBackground(final CategoryModel... params) {
                categoryDAO.delete(params[0]);
                return null;
            }
        }.execute(model);
    }

    // Remove uma categoria
    public void delete(CategoryModel categoryModel) {
        delete(categoryModel.getId());
    }

}
