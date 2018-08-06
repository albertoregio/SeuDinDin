package com.example.regio.seudindin.persistence;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import com.example.regio.seudindin.App;
import com.example.regio.seudindin.R;
import com.example.regio.seudindin.persistence.dao.query.CategoryChildrenCountQuery;
import com.example.regio.seudindin.persistence.entity.CategoryEntity;
import com.example.regio.seudindin.persistence.dao.CategoryDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

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
            categoryModel.setColor(App.getResourceName(R.color.red_200));
            categoryModel.setIcon(App.getResourceName(R.drawable.ic_category_icon_calculator));

            MutableLiveData<CategoryChildrenCountQuery> mutableLiveData = new MutableLiveData<CategoryChildrenCountQuery>();
            mutableLiveData.setValue(categoryModel);

            liveData = mutableLiveData;
        }

        return liveData;

    }


    // Recupera um vetor com os Ids da hierarquia de parentes

    public List<Integer> getParentIdArray(int id) {
        List<Integer> list = null;

        try {
            list = new AsyncTask<Integer, Void, List<Integer>>() {
                @Override
                protected List<Integer> doInBackground(Integer... params) {
                    List<Integer> list = new ArrayList<>();
                    int id = categoryDAO.getParentId(params[0]);

                    while (id != 0) {
                        list.add(0, id);
                        id = categoryDAO.getParentId(id);
                    }

                    return list;
                }


                @Override
                protected void onPostExecute(List<Integer> integers) {
                    super.onPostExecute(integers);
                }

            }.execute(id).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return list;

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
