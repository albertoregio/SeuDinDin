package com.example.regio.seudindin.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.example.regio.seudindin.model.CategoryModel;
import com.example.regio.seudindin.persistence.MyRepository;

import java.util.List;

// Classe responsavel pela comunicacao entre a ui e os dados do negocio
public class CategoryViewModelTest extends AndroidViewModel implements ICategoryViewModel {

    // Declaracao de variaveis
    private MyRepository repository;
    private LiveData<List<CategoryModel>> categories;

    // Construtor da classe
    public CategoryViewModelTest(Application application) {
        super(application);
        repository = new MyRepository(application);
        categories = repository.getCategories();
    }


    // Recupera a lista de categorias
    public LiveData<List<CategoryModel>> load() {
        return categories;
    }

    @Override
    public LiveData<CategoryModel> load(int id) {
        return repository.getCategory(id);
    }


    // Insere/Atualiza uma nova categoria
    public void save(CategoryModel categoryModel) {
        repository.insert(categoryModel);
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void delete(CategoryModel categoryModel) {

    }

}
