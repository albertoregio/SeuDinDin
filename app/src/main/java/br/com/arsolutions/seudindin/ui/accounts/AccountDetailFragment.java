package br.com.arsolutions.seudindin.ui.accounts;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.blackcat.currencyedittext.CurrencyEditText;
import com.example.regio.seudindin.R;

import java.math.BigDecimal;
import java.util.ArrayList;

import br.com.arsolutions.components.ColorSpinner;
import br.com.arsolutions.seudindin.viewmodel.accounts.model.AccountModel;
import br.com.arsolutions.seudindin.viewmodel.categories.model.CategoryModel;
import butterknife.BindView;
import butterknife.ButterKnife;


public class AccountDetailFragment extends Fragment {

    private OnAccountDetailListener mListener;
    private AccountModel model;
    private Context context;

    @BindView(R.id.account_detail_name) EditText nameTextView;
    @BindView(R.id.account_detail_opening_balance) CurrencyEditText openingBalanceTextView;
    @BindView(R.id.account_icon_image_info) ImageView icon;
    @BindView(R.id.account_detail_initials) EditText initialsTextView;
    @BindView(R.id.account_detail_spn_select_colors) ColorSpinner colorSpinner;
    @BindView(R.id.account_detail_enabled_yes) RadioButton enabledYesRadioButton;
    @BindView(R.id.account_detail_enabled_no) RadioButton enabledNoRadioButton;
    @BindView(R.id.account_detail_input_layout_name) TextInputLayout nameInputLayout;
    @BindView(R.id.account_detail_input_layout_initials) TextInputLayout initialsInputLayout;


    // Recupera uma nova instancia do fragmento
    public static AccountDetailFragment newInstance(Bundle args ) {

        // Cria um novo fragmento e atribui parametros
        AccountDetailFragment fragment = new AccountDetailFragment();
        fragment.setArguments(args);

        // Retorna o fragmento criado
        return fragment;
    }


    // Construtor da classe
    public AccountDetailFragment() {
        // Required empty public constructor
    }


    // Evento de criacao do fragmento
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    // Evento de criacao da view
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        /*
        // Recupera o modelo atraves dos argumentos passaods
        model = this.getArguments().getParcelable("account");
        if (model == null)
            model = new AccountModel();

        FragmentAccountDetailBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_account_detail, container, false);
        binding.setAccount(model);
        View view = binding.getRoot();
        */

        View view = inflater.inflate(R.layout.fragment_account_detail, container, false);
        ButterKnife.bind(this, view);
        context = this.getContext();

        // Recupera o modelo atraves dos argumentos passaods
        model = this.getArguments().getParcelable("account");
        if (model == null)
            model = new AccountModel();

        // Configura o combobox de selecao de cores
        setupColorSelector();

        // Configura os componentes da tela
        setupUi();

        // Retorna a view criada
        return view;
    }


    // Metodo responsavel por configurar os componentes da tela
    private void setupUi() {
        nameTextView.setText(model.getName());
        openingBalanceTextView.setText(model.getOpeningBalance().toString());
        initialsTextView.setText(model.getInitials());
        colorSpinner.setSelection(colorSpinner.getIndex(model.getColor()));
        enabledYesRadioButton.setChecked(model.isEnabled());
        enabledNoRadioButton.setChecked(!model.isEnabled());
    }


    // Metodo responsavel por configurar a lista de cores disponiveis
    private void setupColorSelector() {
        colorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int color = colorSpinner.getColor(adapterView.getSelectedItemPosition());

                // Recuperando a camada
                final LayerDrawable layerDrawable = (LayerDrawable) icon.getDrawable();

                // Cor do icone

                final GradientDrawable iconShapeDrawable = (GradientDrawable) layerDrawable.findDrawableByLayerId(R.id.ic_account_icon_color);
                iconShapeDrawable.setColor(ContextCompat.getColor(context, color));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    // Retorna o modelo da conta
    public AccountModel getModel() {
        model.setName(nameTextView.getText().toString());
        model.setOpeningBalance(BigDecimal.valueOf(openingBalanceTextView.getRawValue()));
        model.setInitials(initialsTextView.getText().toString());
        model.setColor(colorSpinner.getColor(colorSpinner.getSelectedItemPosition()));
        model.setEnabled(enabledYesRadioButton.isChecked());
        return model;
    }


    // Evento de armazenamento do estado da instancia quando a atividade vai ser interrompida
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("account", getModel());
    }


    // Evento de restauracao da atividade
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            model = savedInstanceState.getParcelable("account");
        }
    }


    // Metodo responsavel por avisar ao activity que um item foi selecionado
    public void onAccountSelect(AccountModel model) {
        if (mListener != null) {
            mListener.onAccountDetail(model);
        }
    }

    // Evento de vinculacao do fragmento com o atividade
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnAccountDetailListener) {
            mListener = (OnAccountDetailListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnAccountListSelectListener");
        }
    }


    // Evento de desvinculacao do fragmento a atividade
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    // Interface para ser implementado na atividade pai
    public interface OnAccountDetailListener {
        void onAccountDetail(AccountModel model);
    }

}
