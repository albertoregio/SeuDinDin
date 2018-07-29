package com.example.regio.seudindin.persistence.dao.query_model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.regio.seudindin.model.CategoryModel;

// Classe que representa os dados da categoria no banco de dados
public class CategoryChildrenCountQuery extends CategoryModel {

    // Declaracao de variaveis

    @ColumnInfo
    private int qtde;

    // Metodos GET
    public int getQtde() {
        return qtde;
    }


    // Metodos SET
    public void setQtde(int qtde) {
        this.qtde = qtde;
    }

}
