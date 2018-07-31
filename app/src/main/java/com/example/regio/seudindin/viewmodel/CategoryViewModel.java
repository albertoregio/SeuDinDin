package com.example.regio.seudindin.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;

import com.example.regio.seudindin.R;
import com.example.regio.seudindin.model.CategoryModel;
import com.example.regio.seudindin.persistence.dao.query.CategoryChildrenCountQuery;
import com.example.regio.seudindin.persistence.entity.CategoryEntity;
import com.example.regio.seudindin.persistence.CategoryRepository;

// Classe responsavel pela comunicacao entre a ui e os dados do negocio
public class CategoryViewModel extends AndroidViewModel {

    // Declaracao de variaveis
    private CategoryRepository repository;
    private CategoryDetailLiveData detailLiveData;
    private CategoryChildrenListLiveData childrenListLiveData;


    // Construtor da classe
    public CategoryViewModel(Application application) {
        super(application);
        repository = new CategoryRepository(application);
        childrenListLiveData = new CategoryChildrenListLiveData(repository);
        detailLiveData = new CategoryDetailLiveData(repository);
        setChildrenListIdInput(0);
    }


    // Insere/Atualiza uma nova categoria
    public void save(CategoryEntity categoryEntity) {
        repository.insert(categoryEntity);
    }


    // Remove uma categoria pelo id
    public void delete(int id) {
        repository.delete(id);
    }


    // Remove uma determinada categoria
    public void delete(CategoryEntity categoryEntity) {
        repository.delete(categoryEntity);
    }


    // Recupera um livedata para o detalhe de uma categoria
    public LiveData<CategoryModel> getCategoryDetailLiveData() {
        return detailLiveData;
    }


    // Atribui o id da listagem de categorias filhas
    public void setCategoryDetailIdInput(Integer id) {
        detailLiveData.setId(id);
    }


    // Recupera um livedata para a lista de categorias filhas
    public CategoryChildrenListLiveData getChildrenListLiveData() {
        return childrenListLiveData;
    }


    // Atribui o id da listagem de categorias filhas
    public void setChildrenListIdInput(Integer id) {
        childrenListLiveData.resetList();
        childrenListLiveData.setId(id);
    }


}