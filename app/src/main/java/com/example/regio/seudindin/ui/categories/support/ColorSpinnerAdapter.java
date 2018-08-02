package com.example.regio.seudindin.ui.categories.support;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SpinnerAdapter;

import com.example.regio.seudindin.R;

import java.util.Arrays;
import java.util.List;

// Classe responsavel por controlar as cores disponiveis para selecao no componente spinner
public class ColorSpinnerAdapter extends ArrayAdapter<Integer> implements SpinnerAdapter {

    //Declaracao de variaveis
    private final LayoutInflater mInflater;
    private final Context mContext;
    private final int mResource;
    private final List<Integer> colorList;


    // Construtor da classe
    public ColorSpinnerAdapter(Context context, int resource, List objects) {
        super(context,resource, 0, objects);
        this.mContext = context;
        this.colorList = objects;
        this.mResource = resource;
        this.mInflater = LayoutInflater.from(context);
    }


    // Recupera a cor de acordo com o indice
    public int getColor(int index) {
        return colorList.get(index);
    }


    // Recupera o indice de acordo com a cor
    public int getIndex(int color) {
        return colorList.indexOf(color);
    }

    // Metodo responsavel pela exibicao do item selecionado no spinner
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);

    }


    // Metodo responsavel pelo controle do evento para exibicao de demais itens do spinner
    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }


    // Cria um item de cor no componente spinner
    private View createItemView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // Recupera a cor a ser exibida no shape
        int color = ContextCompat.getColor(mContext, colorList.get(position));
        //int color = colorList.get(position);

        // Atribui a cor ao objeto shape
        final View view = mInflater.inflate(mResource, parent, false);
        final ImageView colorPicker = (ImageView) view.findViewById(R.id.color_picker_circle_shape);
        final GradientDrawable shapeDrawable = (GradientDrawable) colorPicker.getDrawable();
        shapeDrawable.setStroke(0, Color.BLACK);
        shapeDrawable.setColor(color);

        return view;

    }


    // Metodo auxiliar para converter um tipo de array e outro
    private Integer[] convertArrays(int[] array) {
        Integer[] integerArray = new Integer[array.length];
        for (int i = 0; i < array.length; i++) {
            integerArray[i] = array[i];
        }
        return integerArray;
    }

}
