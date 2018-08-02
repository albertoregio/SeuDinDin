package com.example.regio.seudindin.model;

import android.content.res.Resources;

import com.example.regio.seudindin.App;
import com.example.regio.seudindin.R;

// Classe que representa o modelo de uma categoria na ui
public class CategoryModel {

    // Declaracao de variaveis
    private Integer parentId;
    private String parentName;
    private int id;
    private String colorName;
    private String iconName;
    private String name;

    private boolean highlighted = false;
    private boolean enabled = true;
    private boolean show_symbol_name = false;
    private int childrenCount = 0;

    // Metodos GET
    public Integer getParentId() {
        return parentId;
    }
    public String getParentName() {
        return getParentId() == 0 ? App.getContext().getString(R.string.categories_parent_novalue) : parentName;
    }
    public int getId() {
        return id;
    }
    public String getColorName() {
        return colorName;
    }
    public Integer getColor() {
        if (colorName != null)
            return App.getResourceColor(colorName);
        else
            return null;
    }
    public String getIconName() {
        return iconName;
    }
    public Integer getIcon() {
        if (iconName != null)
            return App.getResourceDrawable(iconName);
        else
            return null;
    }
    public String getName() {
        return isShow_symbol_name() ? ("[ " + name + " ]") : name;
    }
    public boolean isHighlighted() {
        return highlighted;
    }
    public boolean isEnabled() {
        return enabled;
    }
    public boolean isShow_icon() {
        return getParentId() == 0;
    }
    public boolean isShow_symbol_name() {
        return show_symbol_name;
    }
    public int getChildrenCount() {
        return childrenCount;
    }


    // Metodos SET
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setColor(Integer color) {
        if (color != null)
            this.colorName = App.getResourceName(color);
        else
            this.colorName = null;
    }
    public void setColorName(String colorName) {
        this.colorName = colorName;
    }
    public void setIcon(Integer icon) {
        if (icon != null)
            this.iconName = App.getResourceName(icon);
        else
            this.iconName = null;
    }
    public void setIconName(String iconName) {
        this.iconName = iconName;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setHighlighted(boolean highlighted) {
        this.highlighted = highlighted;
    }
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    public void setShow_symbol_name(boolean show_symbol_name) {
        this.show_symbol_name = show_symbol_name;
    }
    public void setChildrenCount(int childrenCount) {
        this.childrenCount = childrenCount;
    }


    // Retorna uma instancia da categoria root
    public static CategoryModel getRoot() {
        CategoryModel model = new CategoryModel();
        model.setParentId(0);
        model.setParentName(App.getContext().getString(R.string.categories_parent_novalue));
        model.setId(0);
        model.setName(App.getContext().getString(R.string.categories_parent_novalue));
        model.setColor(R.color.default_color_spinner);
        model.setIcon(R.drawable.ic_category_icon_calculator);
        model.setShow_symbol_name(true);
        return  model;
    }

}
