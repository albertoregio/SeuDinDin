package br.com.arsolutions.seudindin.persistence.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

// Classe que representa os dados da categoria no banco de dados
@Entity(tableName = "account")
public class AccountEntity {

    // Declaracao de variaveis

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    @ColumnInfo
    private String name;

    @ColumnInfo
    private String initials;

    @ColumnInfo
    private String color;

    @ColumnInfo
    private boolean enabled;

    @ColumnInfo
    private long openingBalance;


    // Construtor da classe
    public AccountEntity() {

    }

    @Ignore
    // Construtor da classe
    public AccountEntity(int id, String name, String initials, String color, String icon, boolean enabled, long openingBalance) {
        this.id = id;
        this.name = name;
        this.initials = initials;
        this.color = color;
        this.enabled = enabled;
        this.openingBalance = openingBalance;
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
    public String getColor() {
        return color;
    }
    public boolean isEnabled() {
        return enabled;
    }
    public long getOpeningBalance() {
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
    public void setColor(String color) {
        this.color = color;
    }
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    public void setOpeningBalance(long openingBalance) {
        this.openingBalance = openingBalance;
    }

}
