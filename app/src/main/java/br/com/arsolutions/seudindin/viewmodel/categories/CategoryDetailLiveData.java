package br.com.arsolutions.seudindin.viewmodel.categories;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;

import br.com.arsolutions.seudindin.viewmodel.categories.model.CategoryModel;
import br.com.arsolutions.seudindin.persistence.CategoryRepository;
import br.com.arsolutions.seudindin.persistence.dao.query.CategoryChildrenCountQuery;


public class CategoryDetailLiveData extends MediatorLiveData<CategoryModel> {

    // Declaracao de variaveis
    private CategoryRepository repository;
    private MutableLiveData<Integer> categoryDetailIdInput = new MutableLiveData<Integer>();
    private LiveData<CategoryChildrenCountQuery> categoryDetail = Transformations.switchMap(categoryDetailIdInput, (id) -> repository.getCategory(id));

    private int id = 0;


    // Construtor da classe
    public CategoryDetailLiveData(CategoryRepository repository) {

        this.repository = repository;

        // Observer do processamento da consulta parent
        this.addSource(categoryDetail, o -> {
            processValue(o);
        });
    }

    // Processa o valor do livedata
    private void processValue(CategoryChildrenCountQuery category) {
        if (category != null) {
            CategoryModel model = new CategoryModel();
            model.setParentId(category.getParentId());
            model.setParentName(category.getParentName());
            model.setId(category.getId());
            model.setName(category.getName());
            model.setColorName(category.getColor());
            model.setIconName(category.getIcon());
            model.setChildrenCount(category.getChildrenCount());
            model.setShow_symbol_name(false);
            model.setEnabled(true);
            model.setHighlighted(false);
            this.setValue(model);
        }
    }

    // Atribui o id a ser pesquisado
    public void setId(int id) {
        this.id = id;
        categoryDetailIdInput.setValue(id);
    }


}
