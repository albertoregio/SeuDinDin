package com.example.regio.seudindin.persistence.dao;

import com.example.regio.seudindin.R;
import com.example.regio.seudindin.model.CategoryModel;

import java.util.ArrayList;
import java.util.List;

public class CategoryArrayDAO {

    private List<CategoryModel> categoryModelList = new ArrayList<>();

    // Construtor da classe
    public CategoryArrayDAO() {
/*
        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setId(1);
        categoryModel.setColor(R.color.light_green_200);
        categoryModel.setIcon(R.drawable.ic_category_icon_pet);
        categoryModel.setName("AAA");
        categoryModelList.add(categoryModel);

        categoryModel = new CategoryModel();
        categoryModel.setId(2);
        categoryModel.setColor(R.color.brown_200);
        categoryModel.setIcon(R.drawable.ic_category_icon_paper);
        categoryModel.setName("BBB");
        categoryModelList.add(categoryModel);
*/
    }


    // Recupera a lista de categorias
    public List<CategoryModel> getCategoryList() {
        return categoryModelList;
    }


    // Recupera uma determinada categoria
    public CategoryModel getCategory(int id) {

        CategoryModel categoryModel;
        categoryModel = new CategoryModel();
        categoryModel.setId(categoryModelList.size()+1);
        categoryModel.setColor(R.color.red_200);
        categoryModel.setIcon(R.drawable.ic_category_icon_calculator);
        for (int i = 0; i < categoryModelList.size(); i++) {
            CategoryModel model = categoryModelList.get(i);
            if (model.getId() == id) {
                categoryModel = model;
                break;
            }
        }



        return categoryModel;

    }


    // Insere uma nova categoria
    public void insertCategory(CategoryModel categoryModel) {


        boolean flag = false;

        for (int i = 0; i < categoryModelList.size(); i++) {
            CategoryModel model = categoryModelList.get(i);
            if (model.getId() == categoryModel.getId()) {
                flag = true;
                model.setName(categoryModel.getName());
                model.setColor(categoryModel.getColor());
                model.setIcon(categoryModel.getIcon());
                model.setParent_id(categoryModel.getParent_id());
                break;
            }
        }

        if (!flag) {
            categoryModelList.add(categoryModel);
        }
    }


    // Remove uma nova categoria
    public void deleteCategory(CategoryModel categoryModel) {
        categoryModelList.remove(categoryModel);
    }


    // Remove uma nova categoria
    public void deleteCategory(int id) {
        for (int i = 0; i < categoryModelList.size(); i++) {
            CategoryModel model = categoryModelList.get(i);
            if (model.getId() == id) {
                categoryModelList.remove(model);
                break;
            }
        }
    }
}
