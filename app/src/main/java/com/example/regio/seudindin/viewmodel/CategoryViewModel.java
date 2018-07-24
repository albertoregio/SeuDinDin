package com.example.regio.seudindin.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

// Classe responsavel pela comunicacao entre a ui e os dados do negocio
public class CategoryViewModel extends ViewModel {

    // Declaracao de variaveis
    private MutableLiveData<Integer> id;
    private MutableLiveData<Integer> id_parent;
    private MutableLiveData<Integer> color;
    private MutableLiveData<Integer> icon;
    private MutableLiveData<String> name;

    // Construtor da classe
    public CategoryViewModel() {
        color = new MutableLiveData<Integer>();
        icon = new MutableLiveData<Integer>();
        name = new MutableLiveData<String>();
    }


    // Recupera o atributo nome
    public LiveData<Integer> getColor() {
        return color;
    }


    // Recupera o atributo nome
    public LiveData<Integer> getIcon() {
        return icon;
    }


    // Recupera o atributo nome
    public LiveData<String> getName() {
        return name;
    }


    // Atribui um valor ao atributo color
    public void setColor(Integer color) {
        this.color.setValue(color);
    }


    // Atribui um valor ao atributo icon
    public void setIcon(Integer icon) {
        this.icon.setValue(icon);
    }


    // Atribui um valor ao atributo name
    public void setName(String name) {
        this.name.setValue(name);
    }


}
