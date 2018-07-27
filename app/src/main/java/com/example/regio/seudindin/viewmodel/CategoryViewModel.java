package com.example.regio.seudindin.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.regio.seudindin.model.CategoryModel;
import com.example.regio.seudindin.persistence.dao.CategoryArrayDAO;

import java.util.List;

// Classe responsavel pela comunicacao entre a ui e os dados do negocio
public class CategoryViewModel extends ViewModel implements ICategoryViewModel {

    // Declaracao de variaveis
    private CategoryArrayDAO categoryArrayDAO = new CategoryArrayDAO();
    private MutableLiveData<List<CategoryModel>> categoriesModelLiveData = new MutableLiveData<>();


    // Construtor da classe
    public CategoryViewModel() {
        categoriesModelLiveData.setValue(categoryArrayDAO.getCategoryList());
    }


    // Recupera a lista de categorias
    public LiveData<List<CategoryModel>> load() {
        return categoriesModelLiveData;
    }


    // Recupera uma determinada categoria
    public LiveData<CategoryModel> load(int id) {

        MutableLiveData<CategoryModel> liveData = new MutableLiveData<CategoryModel>();
        liveData.setValue(categoryArrayDAO.getCategory(id));

        return liveData;
    }


    // Insere/Atualiza uma categoria
    public void save(CategoryModel categoryModel) {
        categoryArrayDAO.insertCategory(categoryModel);
        categoriesModelLiveData.setValue(categoryArrayDAO.getCategoryList());
    }


    // Remove uma categoria
    public void delete(int id) {
        categoryArrayDAO.deleteCategory(id);
        categoriesModelLiveData.setValue(categoryArrayDAO.getCategoryList());
    }


    // Remove uma categoria
    public void delete(CategoryModel categoryModel) {
        categoryArrayDAO.deleteCategory(categoryModel);
        categoriesModelLiveData.setValue(categoryArrayDAO.getCategoryList());
    }



}
