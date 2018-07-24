package com.example.regio.seudindin.ui.categories.support;

// Classe que representa os dados da categoria
public class CategoryListModel {

    // Declaracao de variaveis
    private int id;
    private int id_parent;
    private int color;
    private int icon;
    private String nome;


    // Metodos GET
    public int getId() {
        return id;
    }
    public int getId_parent() {
        return id_parent;
    }
    public int getColor() {
        return color;
    }
    public int getIcon() {
        return icon;
    }
    public String getNome() {
        return nome;
    }


    // Metodos SET
    public void setId(int id) {
        this.id = id;
    }
    public void setId_parent(int id_parent) {
        this.id_parent = id_parent;
    }
    public void setColor(int color) {
        this.color = color;
    }
    public void setIcon(int icon) {
        this.icon = icon;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
}
