package com.example.regio.seudindin.categories;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.regio.seudindin.R;

// Classe responsavel por controlar o Activity do detalhe de uma categoria
public class CategoriesDetailActivity extends AppCompatActivity {

    // Metodo responsavel pela criacao do activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories_detail);
    }
}
