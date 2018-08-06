package com.example.regio.seudindin.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;

import com.example.regio.seudindin.model.convert.ModelConverter;
import com.example.regio.seudindin.persistence.dao.query.CategoryChildrenCountQuery;
import com.example.regio.seudindin.model.CategoryModel;
import com.example.regio.seudindin.persistence.CategoryRepository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CategoryChildrenListLiveData extends MediatorLiveData<List<CategoryModel>> {

    // Declaracao de variaveis
    private CategoryChildrenCountQuery parent;
    private List<CategoryChildrenCountQuery> children;
    private List<CategoryModel> returnValue;
    private List<Integer> parentIdListHighlight =  new ArrayList<>();
    private CategoryRepository repository;
    private boolean showRoot = false;
    private boolean parentFinished = false;
    private boolean childrenFinished = false;
    private int id = 0;
    private int selectId = 0;

    // listagem de categorias filhas
    private MutableLiveData<Integer> childrenIdInput = new MutableLiveData<Integer>();
    private LiveData<List<CategoryChildrenCountQuery>> childrenCategory = Transformations.switchMap(childrenIdInput, (id) -> repository.getCategoryChildrenList(id));


    // detalhe de uma categoria
    private MutableLiveData<Integer> parentIdInput = new MutableLiveData<Integer>();
    private LiveData<CategoryChildrenCountQuery> parentCategory = Transformations.switchMap(parentIdInput, (id) -> repository.getCategory(id));

    // Construtor da classe
    public CategoryChildrenListLiveData(CategoryRepository repository) {
        
        this.repository = repository;

        // Observer do processamento da consulta parent
        this.addSource(parentCategory, o -> {
            parentFinished = true;
            parent = o;
            processValue();
        });

        // Observer do processamento da consulta children
        this.addSource(childrenCategory, o -> {
            childrenFinished = true;
            children = o;
            processValue();
        });
    }


    // Processa o valor do livedata
    private void processValue() {

        if (parentFinished && childrenFinished) {
            returnValue = new ArrayList<>();

            // Inclui dados do root
            if (showRoot) {
                if (id == 0) {
                    CategoryModel category = CategoryModel.getRoot();

                    category.setHighlighted(parentIdListHighlight.size() == 0);
                    returnValue.add(category);
                }
            }

            // Inclui dados do parent
            if (parent != null) {
                CategoryModel category = ModelConverter.categoryChildrenToModel(parent);
                category.setChildrenCount(0);
                category.setShow_symbol_name(true);
                if (parentIdListHighlight.size() > 0)
                    category.setHighlighted(parentIdListHighlight.get(parentIdListHighlight.size()-1) == Integer.valueOf(category.getId()));
                else
                    category.setHighlighted(false);
                returnValue.add(category);
            }

            // Inclui dados das categorias filhas
            if (children != null) {

                List<CategoryModel> categoryModelList = ModelConverter.listCategoryChildrenToModel(children);

                    Iterator itr = categoryModelList.iterator();
                    while (itr.hasNext()) {
                        CategoryModel model = (CategoryModel) itr.next();
                        if (parentIdListHighlight.size() > 0) {
                            model.setHighlighted(parentIdListHighlight.contains(Integer.valueOf(model.getId())));
                            if(repository.getParentIdArray(model.getId()).contains(Integer.valueOf(selectId)) || model.getId() == Integer.valueOf(selectId))
                                model.setEnabled(false);
                            else
                                model.setEnabled(true);
                        } else {
                            model.setEnabled(model.getId() != Integer.valueOf(selectId));
                        }
                    }

                returnValue.addAll(categoryModelList);
            }

            // Atribui valor do livedata
            this.setValue(returnValue);

            //resetList();
        }

    }


    // Verifica se e para exibir a categoria-pai
    public boolean isShowRoot() {
        return showRoot;
    }


    // Define se e para exibir a categoria-pai
    public void setShowRoot(boolean showParent) {
        this.showRoot = showParent;
    }


    // Atribui o id a ser pesquisado
    public void setId(int id) {
        this.id = id;
        parentIdInput.setValue(id);
        childrenIdInput.setValue(id);
    }


    //
    public void resetList() {
        parentFinished = false;
        childrenFinished = false;

        parent = null;
        children = null;
    }

    public int getSelectId() {
        return selectId;
    }

    public void setSelectId(int selectId) {
        this.selectId = selectId;
        parentIdListHighlight = repository.getParentIdArray(selectId);


    }
}
