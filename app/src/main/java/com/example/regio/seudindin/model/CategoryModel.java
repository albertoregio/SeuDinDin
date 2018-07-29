package com.example.regio.seudindin.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

// Classe que representa os dados da categoria no banco de dados
@Entity(tableName = "category")
public class CategoryModel {

    // Declaracao de variaveis

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    @ColumnInfo
    @Nullable
    private Integer parent_id;

    @ColumnInfo
    private int color;

    @ColumnInfo
    private int icon;

    @ColumnInfo
    private String name;


    // Metodos GET
    public int getId() {
        return id;
    }
    public Integer getParent_id() {
        return parent_id;
    }
    public int getColor() {
        return color;
    }
    public int getIcon() {
        return icon;
    }
    public String getName() {
        return name;
    }


    // Metodos SET
    public void setId(int id) {
        this.id = id;
    }
    public void setParent_id(Integer parent_id) {
        this.parent_id = parent_id;
    }
    public void setColor(int color) {
        this.color = color;
    }
    public void setIcon(int icon) {
        this.icon = icon;
    }
    public void setName(String name) {
        this.name = name;
    }

}
