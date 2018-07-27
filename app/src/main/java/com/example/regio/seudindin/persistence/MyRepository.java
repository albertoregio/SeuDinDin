package com.example.regio.seudindin.persistence;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import com.example.regio.seudindin.R;
import com.example.regio.seudindin.model.CategoryModel;
import com.example.regio.seudindin.persistence.dao.CategoryDAO;

import java.util.List;

// Classe responsavel por recuperar as classes DAO
public class MyRepository {

    // Declaracao de variaveis
    private CategoryDAO categoryDAO;
    private LiveData<List<CategoryModel>> categories;

    // Construtor da classe
    public MyRepository(Application application) {
        MyRoomDatabase db = MyRoomDatabase.getDatabase(application);
        categoryDAO = db.categoryDAO();
        categories = categoryDAO.getCategories();
    }


    // Recupera todas as categorias
    public LiveData<List<CategoryModel>>  getCategories() {
        return categories;
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


    // Insere uma categoria
    public void insert(CategoryModel categoryModel) {
        new insertAsyncTask(categoryDAO).execute(categoryModel);
    }


    // Classe interna para execucao da insercao sem travar a thread da ui
    private static class insertAsyncTask extends AsyncTask<CategoryModel, Void, Void> {
        private CategoryDAO asyncTaskDao;

        public insertAsyncTask(CategoryDAO dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final CategoryModel... params) {
            asyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
