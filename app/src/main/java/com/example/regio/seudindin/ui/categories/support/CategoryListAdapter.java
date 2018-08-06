package com.example.regio.seudindin.ui.categories.support;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.regio.seudindin.App;
import com.example.regio.seudindin.R;
import com.example.regio.seudindin.model.CategoryModel;

import java.util.ArrayList;
import java.util.List;

// Classe responsavel por controlar os itens que serao exibidas na tela
public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListHolder> {

    //Declaracao de variaveis
    private final Context mContext;
    private List<CategoryModel> categoryList = new ArrayList<>(0);
    private CategoryModel selectedItem;
    private View.OnClickListener onClickListener;


    // Construtor da classe
    public CategoryListAdapter(Context context) {
        this.mContext = context;
    }


    // Metodo responsavel pela criacao da classe que representa a tela
    @NonNull
    @Override
    public CategoryListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryListHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list_layout, parent, false));
    }


    // Metodo responsavel pela a alimentacao dos dados da tela
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull CategoryListHolder holder, final int position) {

        final CategoryModel category = categoryList.get(position);

        if (category != null) {

            // Atribui o campo Nome
            holder.category.setText(category.getName());

            // Exibe texto destacado se for o caso
            if (category.isHighlighted()) {
                holder.category.setTextAppearance(R.style.SelectedCategoryListText);
            } else {
                holder.category.setTextAppearance(R.style.CategoryListText);
            }

            if (category.isEnabled()) {
                holder.category.setEnabled(true);
            } else {
                holder.category.setEnabled(false);
                holder.category.setTextAppearance(R.style.DisabledCategoryListText);
                holder.layout.setClickable(false);
            }

            // Recuperando a camada
            final LayerDrawable layerDrawable = (LayerDrawable) holder.icon.getDrawable();

            // Cor do icone
            if (category.getColor() != null) {
                int color = ContextCompat.getColor(mContext, category.getColor());
                //int color = category.getColor();
                final GradientDrawable gradientDrawable = (GradientDrawable) layerDrawable.findDrawableByLayerId(R.id.ic_category_icon_color);
                gradientDrawable.setColor(color);
                gradientDrawable.setStroke(0, R.color.default_color_stroke);
            }

            // Imagem do icone
            if (category.getIcon() != null) {
                Drawable drawable = ResourcesCompat.getDrawable(mContext.getResources(), category.getIcon(), null);
                layerDrawable.setDrawableByLayerId(R.id.ic_category_icon_image, drawable);
            }

            // Visibilidade do icone
            holder.icon.setVisibility(category.isShowIcon() ? View.VISIBLE : View.INVISIBLE);

            // Visibilidade da imagem da seta
            if (category.getChildrenCount() == 0) {
                holder.arrow.setVisibility(View.INVISIBLE);
            } else {
                holder.arrow.setVisibility(View.VISIBLE);
            }

            // Evento de clique
            holder.layout.setOnClickListener(v -> {
                selectedItem = category;
                if (onClickListener != null) {
                    onClickListener.onClick(v);
                }
            });

        }

    }


    // Recupera quantos itens de dados existem
    @Override
    public int getItemCount() {
        return (categoryList != null) ? categoryList.size() : 0;
    }


    // Atribui uma nova lista e atualiza ui
    public void setCategoryList(List<CategoryModel> categories) {
        this.categoryList = categories;
        notifyDataSetChanged();

    }


    // Recupera a categoria selecionada
    public CategoryModel getSelectedItem() {
        return selectedItem;
    }


    // Recupera o listener responsavel por comandos de clique contidos na classe que instancia este objeto
    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }


    // Atribui um listener responsavel por comandos de clique contidos na classe que instancia este objeto
    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }


}
