package br.com.arsolutions.seudindin.ui.accounts.support;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.regio.seudindin.R;

import java.util.ArrayList;
import java.util.List;

import br.com.arsolutions.seudindin.viewmodel.accounts.model.AccountModel;
import butterknife.BindView;
import butterknife.ButterKnife;

// Classe responsavel por controlar os itens que serao exibidas na tela
public class AccountListAdapter extends RecyclerView.Adapter<AccountListAdapter.AccountListHolder> {

    //Declaracao de variaveis
    private final Context mContext;
    private List<AccountModel> accountList = new ArrayList<>(0);
    private AccountModel selectedItem;
    private View.OnClickListener onClickListener;


    // Construtor da classe
    public AccountListAdapter(Context context) {
        this.mContext = context;
    }


    // Metodo responsavel pela criacao da classe que representa a tela
    @NonNull
    @Override
    public AccountListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AccountListHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_account_list, parent, false));
    }


    // Metodo responsavel pela a alimentacao dos dados da tela
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull AccountListHolder holder, final int position) {

        if (accountList.size() > 0) {
            final AccountModel account = accountList.get(position);

            if (account != null) {

                // Atribui o campo Nome
                holder.account.setText(account.getName());
                // Recuperando a camada
                final LayerDrawable layerDrawable = (LayerDrawable) holder.icon.getDrawable();

                // Cor do icone
                if (account.getColor() != null) {
                    int color = ContextCompat.getColor(mContext, account.getColor());
                    final GradientDrawable gradientDrawable = (GradientDrawable) layerDrawable.findDrawableByLayerId(R.id.ic_account_icon_color);
                    gradientDrawable.setColor(color);
                    gradientDrawable.setStroke(0, R.color.default_color_stroke);
                }

                // Evento de clique
                holder.layout.setOnClickListener(v -> {
                    if (onClickListener != null) {
                        selectedItem = account;
                        onClickListener.onClick(v);
                    }
                });

            }
        }

    }


    // Recupera quantos itens de dados existem
    @Override
    public int getItemCount() {
        return accountList.size();
    }


    // Recupera a categoria selecionada
    public AccountModel getSelectedItem() {
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


    // Atribui uma nova lista e atualiza ui
    public void setAccountList(List<AccountModel> accountList) {
        this.accountList = accountList;
        notifyDataSetChanged();
    }


    // ************************************************************
    // Classe interna que representa a tela de informacoes da conta
    class AccountListHolder extends RecyclerView.ViewHolder {

        // Declaracao e alimentacao das variaveis
        @BindView(R.id.account_list_image) ImageView icon;
        @BindView(R.id.account_list_name) TextView account;
        @BindView(R.id.account_list_layout) RelativeLayout layout;


        // Construtor da classe
        public AccountListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}
