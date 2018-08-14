package br.com.arsolutions.seudindin.viewmodel.accounts.model;

import android.os.Parcel;
import android.os.Parcelable;

import br.com.arsolutions.seudindin.App;

import java.math.BigDecimal;

// Classe que representa o modelo de uma conta na ui
public class AccountModel implements Parcelable {

    // Declaracao de variaveis
    private int id;
    private String name;
    private String initials;
    private String colorName;
    private boolean enabled = true;
    private BigDecimal openingBalance = new BigDecimal(0);


    public AccountModel() {

    }

    // Metodos GET
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getInitials() {
        return initials;
    }
    public String getColorName() {
        return colorName;
    }
    public Integer getColor() {
        if (colorName != null)
            return App.getResourceColor(colorName);
        else
            return 0;
    }
    public boolean isEnabled() {
        return enabled;
    }
    public BigDecimal getOpeningBalance() {
        return openingBalance;
    }


    // Metodos SET
    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setInitials(String initials) {
        this.initials= initials;
    }
    public void setColor(Integer color) {
        if (color != null)
            this.colorName = App.getResourceName(color);
        else
            this.colorName = null;
    }
    public void setColorName(String colorName) {
        this.colorName = colorName;
    }
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    public void setOpeningBalance(BigDecimal openingBalance) {
        this.openingBalance= openingBalance;
    }


// *******************************************************************
// Implementação do Parcelable
// *******************************************************************


    protected AccountModel(Parcel in) {
        id = in.readInt();
        name = in.readString();
        initials = in.readString();
        colorName = in.readString();
        enabled = in.readByte() != 0;
        Long value = in.readLong();
        openingBalance = ( value == null ? null : new BigDecimal(value).divide(new BigDecimal(100)) );
    }

    public static final Creator<AccountModel> CREATOR = new Creator<AccountModel>() {
        @Override
        public AccountModel createFromParcel(Parcel in) {
            return new AccountModel(in);
        }

        @Override
        public AccountModel[] newArray(int size) {
            return new AccountModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(initials);
        parcel.writeString(colorName);
        parcel.writeByte((byte) (enabled ? 1 : 0));
        parcel.writeLong(openingBalance.multiply(new BigDecimal(100)).longValue());
    }
}
