package com.example.regio.seudindin.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;

import com.example.regio.seudindin.model.CategoryModel;
import com.example.regio.seudindin.persistence.CategoryRepository;
import com.example.regio.seudindin.persistence.dao.query_model.CategoryChildrenCountQuery;

import java.util.List;

// Classe responsavel pela comunicacao entre a ui e os dados do negocio
public class CategoryViewModel extends AndroidViewModel {

    // Declaracao de variaveis
    private CategoryRepository repository;


    // listagem de categorias filhas
    private MutableLiveData<Integer> childrenListIdInput = new MutableLiveData<Integer>();
    private LiveData<List<CategoryChildrenCountQuery>> childrenList = Transformations.switchMap(childrenListIdInput, (id) -> {
        // TODO: Consultar informacoes da categoria atual e inseri-la na lista antes de retornar variavel
        //       retornar " repository.getCategory(id) + repository.getCategoryChildrenList(id) "  em uma unica lista
        return repository.getCategoryChildrenList(id);
    });


    // detalhe de uma categoria
    private MutableLiveData<Integer> categoryDetailIdInput = new MutableLiveData<Integer>();
    private LiveData<CategoryModel> categoryDetail = Transformations.switchMap(categoryDetailIdInput, (id) -> {
        return repository.getCategory(id);
    });


    // Construtor da classe
    public CategoryViewModel(Application application) {
        super(application);
        repository = new CategoryRepository(application);
        setChildrenListIdInput(0);
    }


    // Insere/Atualiza uma nova categoria
    public void save(CategoryModel categoryModel) {
        repository.insert(categoryModel);
    }


    // Remove uma categoria pelo id
    public void delete(int id) {
        repository.delete(id);
    }


    // Remove uma determinada categoria
    public void delete(CategoryModel categoryModel) {
        repository.delete(categoryModel);
    }


    // Recupera um livedata para o detalhe de uma categoria
    public LiveData<CategoryModel> getCategoryDetailLiveData() {
        return categoryDetail;
    }


    // Recupera um livedata para a lista de categorias filhas
    public LiveData<List<CategoryChildrenCountQuery>> getChildrenListLiveData() {
        return childrenList;
    }


    // Atribui o id da listagem de categorias filhas
    public void setChildrenListIdInput(Integer id) {
        childrenListIdInput.setValue(id);
    }


    // Atribui o id da listagem de categorias filhas
    public void setCategoryDetailIdInput(Integer id) {
        categoryDetailIdInput.setValue(id);
    }



}
