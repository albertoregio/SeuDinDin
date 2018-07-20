package com.example.regio.seudindin.categories;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.regio.seudindin.R;
import com.example.regio.seudindin.categories.support.ColorSpinnerAdapter;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

// Classe responsavel por controlar o Activity do detalhe de uma categoria
public class CategoriesDetailActivity extends AppCompatActivity {

    @BindView(R.id.categoriesDetail_toolbar) Toolbar toolbar;
    @BindView(R.id.categoriesDetail_spn_select_colors) Spinner spinner;



    // Metodo responsavel pela criacao do activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_categories_detail);

        ButterKnife.bind(this);

        // Configura o toolbar
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_cancel);

        // Configura o combobox de selecao de cores
        Integer[] colorsList = {
                R.color.red_200,
                R.color.pink_200,
                R.color.purple_200,
                R.color.blue_200,
                R.color.light_green_200,
                R.color.yellow_200,
                R.color.orange_200,
                R.color.brown_200,
                R.color.gray_200};

        ColorSpinnerAdapter colorSpinnerAdapter = new ColorSpinnerAdapter(this, R.layout.spinner_color_picker, Arrays.asList(colorsList));
        spinner.setAdapter(colorSpinnerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_categories_detail_toolbar, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menuitem_categories_detail_save:
                Toast.makeText(getApplicationContext(), "TODO: command save", Toast.LENGTH_LONG).show();
                return true;

            case R.id.menuitem_categories_detail_delete:
                Toast.makeText(getApplicationContext(), "TODO: command delete", Toast.LENGTH_LONG).show();
                return true;

            case android.R.id.home:
                confirmBack();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        confirmBack();
    }

    private void confirmBack() {
        AlertDialog.Builder alert = new AlertDialog.Builder(CategoriesDetailActivity.this);
        alert
                .setTitle(R.string.command_cancel_title)
                .setMessage(R.string.command_cancel_dialog)
                .setPositiveButton(R.string.command_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .setNegativeButton(R.string.command_no, null)
                .show();

    }

}
