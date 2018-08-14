package br.com.arsolutions.seudindin.persistence.dao.query;

import android.arch.persistence.room.ColumnInfo;

import br.com.arsolutions.seudindin.persistence.entity.CategoryEntity;

// Classe que representa os dados da categoria no banco de dados
public class CategoryChildrenCountQuery extends CategoryEntity {

    // Declaracao de variaveis

    @ColumnInfo
    private String parentName;
    @ColumnInfo
    private int childrenCount;

    // Metodos GET
    public String getParentName() {
        return parentName;
    }
    public int getChildrenCount() {
        return childrenCount;
    }


    // Metodos SET
    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
    public void setChildrenCount(int childrenCount) {
        this.childrenCount = childrenCount;
    }

}
