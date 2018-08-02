package com.example.regio.seudindin.model.convert;

import com.example.regio.seudindin.App;
import com.example.regio.seudindin.model.CategoryModel;
import com.example.regio.seudindin.persistence.dao.query.CategoryChildrenCountQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class ModelConverter {

    public static CategoryModel categoryChildrenToModel(CategoryChildrenCountQuery children) {
        CategoryModel model = new CategoryModel();
        model.setParentId(children.getParentId());
        model.setId(children.getId());
        model.setName(children.getName());
        model.setColorName(children.getColor());
        model.setIconName(children.getIcon());
        model.setChildrenCount(children.getChildrenCount());
        return model;
    }

    public static List<CategoryModel> listCategoryChildrenToModel(List<CategoryChildrenCountQuery> children) {
        List<CategoryModel> list = new ArrayList<>();
        ListIterator<CategoryChildrenCountQuery> itr = children.listIterator();
        while (itr.hasNext()) {
            list.add(categoryChildrenToModel(itr.next()));
        }
        return list;
    }
}
