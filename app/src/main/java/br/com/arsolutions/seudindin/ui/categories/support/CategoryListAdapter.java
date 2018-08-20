package br.com.arsolutions.seudindin.ui.categories.support;

import android.annotation.SuppressLint;
import android.content.Context;
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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.regio.seudindin.R;
import br.com.arsolutions.seudindin.viewmodel.categories.model.CategoryModel;
import butterknife.BindView;
import butterknife.ButterKnife;

import java.util.ArrayList;
import java.util.List;

// Classe responsavel por controlar os itens que serao exibidas na tela
public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.CategoryListHolder> {

    //Declaracao de variaveis
    private final Context mContext;
    private List<CategoryModel> categoryList = new ArrayList<>(0);
    private CategoryModel selectedItem;
    private int selectedIndex;
    private View.OnClickListener onClickListener;


    // Construtor da classe
    public CategoryListAdapter(Context context) {
        this.mContext = context;
    }


    // Metodo responsavel pela criacao da classe que representa a tela
    @NonNull
    @Override
    public CategoryListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryListHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_category_list, parent, false));
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
                holder.category.setTextAppearance(R.style.SelectedItemListText);
            } else {
                holder.category.setTextAppearance(R.style.ItemListText);
            }

            if (category.isEnabled()) {
                holder.category.setEnabled(true);
            } else {
                holder.category.setEnabled(false);
                holder.category.setTextAppearance(R.style.DisabledItemListText);
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
                selectedIndex = position;
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


    // Recupera o indice da categoria selecionada
    public int getSelectedIndex() {
        return selectedIndex;
    }


    // Recupera o indice de uma categoria pelo id
    public int getIndexById(int id) {
        int i = 0;
        boolean found = false;

        for (; i < categoryList.size(); i++) {
            if (categoryList.get(i).getId() == id) {
                found = true;
                break;
            }
        }

        return found ? i : 0;
    }


    // Recupera o listener responsavel por comandos de clique contidos na classe que instancia este objeto
    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }


    // Atribui um listener responsavel por comandos de clique contidos na classe que instancia este objeto
    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    // ****************************************************************
    // Classe interna que representa a tela de informacoes da categoria
    class CategoryListHolder extends RecyclerView.ViewHolder {

        // Declaracao e alimentacao das variaveis
        @BindView(R.id.category_list_image) ImageView icon;
        @BindView(R.id.category_list_name) TextView category;
        @BindView(R.id.categories_list_arrow_info) ImageView arrow;
        @BindView(R.id.categories_list_layout) RelativeLayout layout;


        // Construtor da classe
        public CategoryListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
