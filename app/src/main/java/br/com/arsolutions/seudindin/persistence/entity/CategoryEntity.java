package br.com.arsolutions.seudindin.persistence.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

// Classe que representa os dados da categoria no banco de dados
@Entity(tableName = "category")
public class CategoryEntity {

    // Declaracao de variaveis

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    @ColumnInfo
    @Nullable
    private Integer parentId;

    @ColumnInfo
    private String color;

    @ColumnInfo
    private String icon;

    @ColumnInfo
    private String name;


    // Construtor da classe
    public CategoryEntity() {

    }

    @Ignore
    // Construtor da classe
    public CategoryEntity(int id, String name, Integer parentId, String color, String icon) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.color = color;
        this.icon = icon;
    }


    // Metodos GET
    public int getId() {
        return id;
    }
    public Integer getParentId() {
        return parentId;
    }
    public String getColor() {
        return color;
    }
    public String getIcon() {
        return icon;
    }
    public String getName() {
        return name;
    }


    // Metodos SET
    public void setId(int id) {
        this.id = id;
    }
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }
    public void setName(String name) {
        this.name = name;
    }

}
