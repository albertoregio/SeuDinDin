package br.com.arsolutions.seudindin.persistence;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import com.example.regio.seudindin.R;

import br.com.arsolutions.seudindin.App;
import br.com.arsolutions.seudindin.persistence.dao.query.CategoryChildrenCountQuery;
import br.com.arsolutions.seudindin.persistence.entity.CategoryEntity;
import br.com.arsolutions.seudindin.persistence.dao.CategoryDAO;

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
        return categoryDAO.getCategoryChildrenListLiveData(id);
    }


    // Recupera uma categoria especifica
    public LiveData<CategoryChildrenCountQuery>  getCategory(int id) {

        LiveData<CategoryChildrenCountQuery> liveData = categoryDAO.getCategoryLiveData(id);

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

    public List<CategoryChildrenCountQuery> getParentIdArray(int id) {
        List<CategoryChildrenCountQuery> list = null;

        try {
            list = new AsyncTask<Integer, Void, List<CategoryChildrenCountQuery>>() {
                @Override
                protected List<CategoryChildrenCountQuery> doInBackground(Integer... params) {
                    List<CategoryChildrenCountQuery> list = new ArrayList<>();
                    int id = params[0];

                    while (id != 0) {
                        CategoryChildrenCountQuery query = categoryDAO.getCategoryChildrenCountQuery(id);
                        list.add(0, query);
                        id = query.getParentId();
                    }

                    return list;
                }
            }.execute(id).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return list;

    }


    // Insere uma categoria
    public int insert(CategoryEntity categoryEntity) {
        Integer value = 0;
        try {
            value = new AsyncTask<CategoryEntity,Void,Integer>() {
                @Override
                protected Integer doInBackground(final CategoryEntity... params) {
                    return Integer.valueOf((int) categoryDAO.insert(params[0]));
                }
            }.execute(categoryEntity).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return value.intValue();
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
