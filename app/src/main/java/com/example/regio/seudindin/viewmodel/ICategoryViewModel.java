package com.example.regio.seudindin.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.regio.seudindin.model.CategoryModel;
import com.example.regio.seudindin.persistence.dao.CategoryArrayDAO;

import java.util.List;

// Classe responsavel pela comunicacao entre a ui e os dados do negocio
public interface ICategoryViewModel {

    // Recupera a lista de categorias
    public LiveData<List<CategoryModel>> load();


    // Recupera uma determinada categoria
    public LiveData<CategoryModel> load(int id);


    // Insere/Atualiza uma categoria
    public void save(CategoryModel categoryModel);


    // Remove uma categoria pelo id
    public void delete(int id);


    // Remove uma categoria
    public void delete(CategoryModel categoryModel);

}
